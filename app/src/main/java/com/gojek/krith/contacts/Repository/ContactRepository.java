package com.gojek.krith.contacts.Repository;

import com.gojek.krith.contacts.models.Contact;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by krith on 01/02/17.
 */

public class ContactRepository implements ContactRepositoryContract.ContactMainRepository {

    private final ContactLocalRepository localRepository;
    private final ContactRemoteRepository remoteRepository;

    @Inject
    public ContactRepository(ContactLocalRepository localRepository, ContactRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    private Map<Integer, Contact> mCachedContacts;

    @Override
    public Observable<List<Contact>> getAllContacts(boolean forceUpdate) {
        if (mCachedContacts != null && mCachedContacts.size() != 0 && !forceUpdate)
            return Observable.from(mCachedContacts.values()).toList();
        mCachedContacts = new LinkedHashMap<>();
        if (!forceUpdate)
            return getContactsFromLocalRepository();
        return getContactsFromRemoteRepository();
    }

    private Observable<List<Contact>> getContactsFromLocalRepository() {
        return localRepository.getAllContacts()
                .flatMap(new Func1<List<Contact>, Observable<List<Contact>>>() {
                    @Override
                    public Observable<List<Contact>> call(List<Contact> contacts) {
                        return Observable.from(contacts)
                                .doOnNext(new Action1<Contact>() {
                                    @Override
                                    public void call(Contact contact) {
                                        mCachedContacts.put(contact.getId(), contact);
                                    }
                                }).toList();
                    }
                });
    }

    private Observable<List<Contact>> getContactsFromRemoteRepository() {
        return remoteRepository.getAllContacts()
                .doOnNext(new Action1<List<Contact>>() {
                    @Override
                    public void call(List<Contact> contacts) {
                        localRepository.deleteAllContacts();
                    }
                })
                .flatMap(new Func1<List<Contact>, Observable<List<Contact>>>() {
                    @Override
                    public Observable<List<Contact>> call(List<Contact> contacts) {
                        return localRepository.addContacts(contacts);
                    }
                })
                .flatMap(new Func1<List<Contact>, Observable<List<Contact>>>() {
                    @Override
                    public Observable<List<Contact>> call(List<Contact> contacts) {
                        return Observable.from(contacts)
                                .doOnNext(new Action1<Contact>() {
                                    @Override
                                    public void call(Contact contact) {
                                        mCachedContacts.put(contact.getId(), contact);
                                    }
                                }).toList();
                    }
                });
    }

    @Override
    public Observable<Contact> getContact(int id) {
        if (mCachedContacts != null
                && mCachedContacts.size() != 0
                && mCachedContacts.get(id).getCreatedAt() != null)
            return Observable.just(mCachedContacts.get(id));
        return findContactFromLocalRepository(id);
    }

    private Observable<Contact> findContactFromLocalRepository(final int id) {
        return localRepository.getContact(id)
                .flatMap(new Func1<Contact, Observable<Contact>>() {
                    @Override
                    public Observable<Contact> call(Contact contact) {
                        if (contact.getCreatedAt() != null)
                            return Observable.just(contact).first();
                        else
                            return findContactFromRemoteRepository(id);
                    }
                });
    }

    private Observable<Contact> findContactFromRemoteRepository(final int id) {
        return remoteRepository.getContact(id)
                .doOnNext(new Action1<Contact>() {
                    @Override
                    public void call(Contact contact) {
                        if (contact.getFavorite() == null)
                            contact.setFavorite(false);
                        localRepository.updateContact(contact);
                    }
                }).first();
    }

    @Override
    public Observable<Contact> markFavorite(final int id) {
        return localRepository.getContact(id)
                .flatMap(new Func1<Contact, Observable<Contact>>() {
                    @Override
                    public Observable<Contact> call(Contact contact) {
                        contact.setFavorite(!contact.getFavorite());
                        return markFavoriteRemotely(contact);
                    }
                });
    }

    private Observable<Contact> markFavoriteRemotely(Contact contact) {
        return remoteRepository.markFavorite(contact)
                .doOnNext(new Action1<Contact>() {
                    @Override
                    public void call(Contact contact) {
                        localRepository.markFavorite(contact);
                    }
                });
    }

    @Override
    public Observable<Contact> addContact(Contact contact) {
        return remoteRepository.addContact(contact).doOnNext(new Action1<Contact>() {
            @Override
            public void call(Contact contact) {
                if (contact.getFavorite() == null)
                    contact.setFavorite(false);
                localRepository.addContact(contact);
                if (mCachedContacts != null)
                    mCachedContacts.clear();
                else
                    mCachedContacts = new LinkedHashMap<>();
                getContactsFromLocalRepository();
            }
        });
    }
}

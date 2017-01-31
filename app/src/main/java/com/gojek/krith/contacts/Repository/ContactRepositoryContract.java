package com.gojek.krith.contacts.Repository;

import com.gojek.krith.contacts.models.Contact;

import java.util.List;

import rx.Observable;

/**
 * Created by krith on 01/02/17.
 */

public interface ContactRepositoryContract {
    public interface ContactMainRepository {
        Observable<List<Contact>> getAllContacts(boolean forceUpdate, boolean isCacheDirty);

        Observable<Contact> getContact(int serverId);

        Observable<Contact> markFavorite(Contact contact);

        Observable<List<Contact>> addContacts(List<Contact> contact);

        Observable<Contact> addContact(Contact contact);
    }

    public interface ContactLocalRepository {
        Observable<List<Contact>> getAllContacts();

        Observable<Contact> getContact(int serverId);

        void markFavorite(Contact contact);

        void addContacts(List<Contact> contact);

        void addContact(Contact contact);
    }

    public interface ContactRemoteRepository {
        Observable<List<Contact>> getAllContacts();

        Observable<Contact> getContact(int serverId);

        Observable<Contact> markFavorite(Contact contact);

        Observable<Contact> addContact(Contact contact);
    }
}

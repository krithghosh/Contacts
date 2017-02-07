package com.gojek.krith.contacts.Repository;

import android.database.sqlite.SQLiteDatabase;

import com.gojek.krith.contacts.application.App;
import com.gojek.krith.contacts.database.ContactTable;
import com.gojek.krith.contacts.models.Contact;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.gojek.krith.contacts.database.ContactTable.ID;

/**
 * Created by krith on 01/02/17.
 */

public class ContactLocalRepository implements ContactRepositoryContract.ContactLocalRepository {

    @Inject
    BriteDatabase briteDatabase;

    public ContactLocalRepository() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<List<Contact>> getAllContacts() {
        return briteDatabase.createQuery(ContactTable.TABLE, ContactTable.QUERY_ALL_CONTACTS)
                .mapToList(ContactTable.MAPPER);
    }

    @Override
    public Observable<Contact> getContact(int serverId) {
        return briteDatabase.createQuery(ContactTable.TABLE, ContactTable.QUERY_ALL_CONTACTS)
                .mapToOne(ContactTable.MAPPER);
    }

    @Override
    public void markFavorite(Contact contact) {
        briteDatabase.update(ContactTable.TABLE, new ContactTable.Builder()
                .setId(contact.getId())
                .setEmail(contact.getEmail())
                .setPhoneNumber(contact.getPhoneNumber())
                .setFavorite(contact.getFavorite())
                .setCreatedAt(contact.getCreatedAt())
                .setUpdatedAt(contact.getUpdatedAt())
                .build(), ID + "=?", contact.getId().toString());
    }

    @Override
    public Observable<List<Contact>> addContacts(List<Contact> contacts) {
        BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (Contact contact : contacts) {
                boolean fav = contact.getFavorite() != null ? contact.getFavorite() : false;
                briteDatabase.insert(ContactTable.TABLE, new ContactTable.Builder()
                        .setId(contact.getId())
                        .setFirstName(contact.getFirstName())
                        .setLastName(contact.getLastName())
                        .setEmail(contact.getEmail())
                        .setPhoneNumber(contact.getPhoneNumber())
                        .setUrl(contact.getUrl())
                        .setFavorite(fav)
                        .setProfilePic(contact.getProfilePic())
                        .setCreatedAt(contact.getCreatedAt())
                        .setUpdatedAt(contact.getUpdatedAt())
                        .build(), SQLiteDatabase.CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
        return getAllContacts();
    }

    @Override
    public void addContact(Contact contact) {
        briteDatabase.insert(ContactTable.TABLE, new ContactTable.Builder()
                .setId(contact.getId())
                .setFirstName(contact.getFirstName())
                .setLastName(contact.getLastName())
                .setEmail(contact.getEmail())
                .setPhoneNumber(contact.getPhoneNumber())
                .setUrl(contact.getUrl())
                .setFavorite(contact.getFavorite())
                .setProfilePic(contact.getProfilePic())
                .setCreatedAt(contact.getCreatedAt())
                .setUpdatedAt(contact.getUpdatedAt())
                .build(), SQLiteDatabase.CONFLICT_REPLACE);
    }
}

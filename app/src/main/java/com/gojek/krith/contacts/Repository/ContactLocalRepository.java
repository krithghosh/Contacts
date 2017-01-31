package com.gojek.krith.contacts.Repository;

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

    @Override
    public Observable<List<Contact>> getAllContacts() {
        return briteDatabase.createQuery(ContactTable.TABLE, ContactTable.QUERY_ALL_CONTACTS, null)
                .mapToList(ContactTable.MAPPER);
    }

    @Override
    public Observable<Contact> getContact(int serverId) {
        return briteDatabase.createQuery(ContactTable.TABLE, ContactTable.QUERY_ALL_CONTACTS, null)
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
                .build(), ID + "=?", new String[]{String.valueOf(contact.getId())});
    }

    @Override
    public void addContacts(List<Contact> contacts) {
        BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (Contact contact : contacts) {
                briteDatabase.insert(ContactTable.TABLE, new ContactTable.Builder()
                        .setId(contact.getId())
                        .setFirstName(contact.getFirstName())
                        .setLastName(contact.getLastName())
                        .setEmail(contact.getEmail())
                        .setPhoneNumber(contact.getPhoneNumber())
                        .setUrl(contact.getUrl())
                        .setFavorite(contact.getFavorite())
                        .setCreatedAt(contact.getCreatedAt())
                        .setUpdatedAt(contact.getUpdatedAt())
                        .build());
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
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
                .setCreatedAt(contact.getCreatedAt())
                .setUpdatedAt(contact.getUpdatedAt())
                .build());
    }
}

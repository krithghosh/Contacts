package com.gojek.krith.contacts.Repository;

import com.gojek.krith.contacts.Network.RestApiService;
import com.gojek.krith.contacts.application.App;
import com.gojek.krith.contacts.models.Contact;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by krith on 01/02/17.
 */

public class ContactRemoteRepository implements ContactRepositoryContract.ContactRemoteRepository {

    @Inject
    Retrofit retrofit;

    public ContactRemoteRepository() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<List<Contact>> getAllContacts() {
        return retrofit.create(RestApiService.class).getAllContacts();
    }

    @Override
    public Observable<Contact> getContact(int id) {
        return retrofit.create(RestApiService.class).getContact(id);
    }

    @Override
    public Observable<Contact> markFavorite(Contact contact) {
        return retrofit.create(RestApiService.class).updateContact(contact.getId(), contact);
    }

    @Override
    public Observable<Contact> addContact(Contact contact) {
        return retrofit.create(RestApiService.class).addContact(contact);
    }
}

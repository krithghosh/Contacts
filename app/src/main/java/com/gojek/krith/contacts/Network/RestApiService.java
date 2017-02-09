package com.gojek.krith.contacts.Network;

import com.gojek.krith.contacts.models.Contact;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by krith on 01/02/17.
 */

public interface RestApiService {

    @GET("/contacts.json")
    Observable<List<Contact>> getAllContacts();

    @GET("/contacts/{id}.json")
    Observable<Contact> getContact(@Path("id") int id);

    @POST("/contacts.json")
    Observable<Contact> addContact(@Body Contact contact);

    @PUT("/contacts/{id}.json")
    Observable<Contact> updateContact(@Path("id") int id, @Body Contact contact);
}

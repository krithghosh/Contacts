package com.gojek.krith.contacts.application;

import com.gojek.krith.contacts.Network.NetModule;
import com.gojek.krith.contacts.Repository.ContactLocalRepository;
import com.gojek.krith.contacts.Repository.ContactRemoteRepository;
import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.Repository.ContactRepositoryModule;
import com.gojek.krith.contacts.add_contact.AddContactActivity;
import com.gojek.krith.contacts.all_contacts.AllContactsActivity;
import com.gojek.krith.contacts.contact_details.ContactDetailsActivity;
import com.gojek.krith.contacts.database.DbModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by krith on 01/02/17.
 */

@Singleton
@Component(modules = {AppModule.class, DbModule.class, NetModule.class,
        ContactRepositoryModule.class, SharedPreferenceModule.class})
public interface AppComponent {

    void inject(AllContactsActivity allContactsActivity);

    void inject(AddContactActivity addContactActivity);

    void inject(ContactDetailsActivity contactDetailsActivity);

    void inject(SharedPreferenceManager sharedPreferenceManager);

    void inject(ContactRepository contactRepository);

    void inject(ContactLocalRepository contactLocalRepository);

    void inject(ContactRemoteRepository contactRemoteRepository);
}

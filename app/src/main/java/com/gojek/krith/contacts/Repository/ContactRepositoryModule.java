package com.gojek.krith.contacts.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krith on 01/02/17.
 */
@Module
public class ContactRepositoryModule {
    @Singleton
    @Provides
    ContactLocalRepository providesLocalRepository() {
        return new ContactLocalRepository();
    }

    @Singleton
    @Provides
    ContactRemoteRepository providesRemoteRepository() {
        return new ContactRemoteRepository();
    }
}

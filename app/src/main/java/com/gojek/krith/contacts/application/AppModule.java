package com.gojek.krith.contacts.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.gojek.krith.contacts.database.DbModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krith on 31/01/17.
 */

@Module(includes = {DbModule.class})
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    Resources providesResource(Context context) {
        return context.getResources();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    SharedPreferenceManager providesSharedPreferenceManager(SharedPreferences sharedPreferences) {
        return new SharedPreferenceManager(sharedPreferences);
    }
}

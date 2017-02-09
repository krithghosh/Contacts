package com.gojek.krith.contacts.application;

import android.app.Application;

import com.gojek.krith.contacts.Network.NetModule;
import com.gojek.krith.contacts.Repository.ContactRepositoryModule;

/**
 * Created by krith on 01/02/17.
 */

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        String BASE_URL = "http://gojek-contacts-app.herokuapp.com/";
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .contactRepositoryModule(new ContactRepositoryModule())
                .build();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }
}

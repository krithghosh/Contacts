package com.gojek.krith.contacts.application;

import android.app.Application;

import com.gojek.krith.contacts.Network.NetModule;
import com.gojek.krith.contacts.Repository.ContactRepositoryModule;
import com.gojek.krith.contacts.database.DbModule;
import com.gojek.krith.contacts.utils.Toaster;

/**
 * Created by krith on 01/02/17.
 */

public class App extends Application {

    private static AppComponent appComponent;
    private final String BASE_URL = "http://gojek-contacts-app.herokuapp.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        Toaster.init(this);
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

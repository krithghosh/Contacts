package com.gojek.krith.contacts.database;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * Created by krith on 31/01/17.
 */

@Module
public class DbModule {

    @Provides
    @Singleton
    public SQLiteOpenHelper provideOpenHelper(Application application) {
        return new DatabaseHelper(application);
    }

    @Provides
    @Singleton
    public SqlBrite provideSqlBrite() {
        return SqlBrite.create();
    }

    @Provides
    @Singleton
    public BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        return sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
    }
}

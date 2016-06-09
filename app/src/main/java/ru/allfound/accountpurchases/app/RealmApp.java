package ru.allfound.accountpurchases.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.allfound.accountpurchases.realm.SimpleRealmModule;

/*
 * RealmApp.java    v.1.0 12.05.2016
 *
 * Copyright (c) 2015-2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class RealmApp extends Application {

    private static RealmApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .setModules(new SimpleRealmModule()).build();
        Realm.setDefaultConfiguration(config);
    }

    public static RealmApp getInstance() {
        return instance;
    }
}

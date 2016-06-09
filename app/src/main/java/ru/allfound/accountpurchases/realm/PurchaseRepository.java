package ru.allfound.accountpurchases.realm;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import ru.allfound.accountpurchases.app.RealmApp;
import ru.allfound.accountpurchases.model.Purchase;

/*
 * PurchaseRepository.java    v.1.0 12.05.2016
 *
 * Copyright (c) 2015-2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class PurchaseRepository implements IPurchaseRepository {

    public PurchaseRepository() {
    }

    @Override
    public void addPurchase(Purchase purchase) {
        Realm realm = Realm.getInstance(RealmApp.getInstance());
        realm.beginTransaction();
        Purchase p = realm.createObject(Purchase.class);
        p.setId(UUID.randomUUID().toString());
        p.setCategory(purchase.getCategory());
        p.setDescription(purchase.getDescription());
        p.setPrice(purchase.getPrice());
        p.setTime(purchase.getTime());
        p.setDate(purchase.getDate());
        realm.commitTransaction();
    }

    @Override
    public void update(Purchase purchase) {
        Realm realm = Realm.getInstance(RealmApp.getInstance());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(purchase);
        realm.commitTransaction();
    }

    @Override
    public RealmResults<Purchase> fetchPurchases() {
        RealmApp realmApp = RealmApp.getInstance();
        Realm realm = Realm.getInstance(realmApp);
        RealmQuery<Purchase> query = realm.where(Purchase.class);
        return query.findAll();
    }

    @Override
    public Purchase findById(String id) {
        Realm realm = Realm.getInstance(RealmApp.getInstance());
        return realm.where(Purchase.class).equalTo(ID, id).findFirst();
    }

    @Override
    public void deletePurchaseById(String id) {
        Realm realm = Realm.getInstance(RealmApp.getInstance());
        realm.beginTransaction();
        Purchase purchase = realm.where(Purchase.class).equalTo(ID, id).findFirst();
        purchase.removeFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void deletePurchases() {
        Realm realm = Realm.getInstance(RealmApp.getInstance());
        realm.beginTransaction();
        RealmResults<Purchase> results = realm.where(Purchase.class).findAll();
        results.clear();
        realm.commitTransaction();
    }
}

package ru.allfound.accountpurchases.realm;

import io.realm.RealmResults;
import ru.allfound.accountpurchases.model.Purchase;

/*
 * IPurchaseRepository.java    v.1.0 12.05.2016
 *
 * Copyright (c) 2015-2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public interface IPurchaseRepository {
    String ID = "id";

    void addPurchase(Purchase purchase);
    void update(Purchase purchase);
    public RealmResults<Purchase> fetchPurchases();
    Purchase findById(String id);
    void deletePurchaseById(String id);
    void deletePurchases();
}

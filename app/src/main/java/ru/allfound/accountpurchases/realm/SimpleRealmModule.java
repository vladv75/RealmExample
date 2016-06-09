package ru.allfound.accountpurchases.realm;

import io.realm.annotations.RealmModule;
import ru.allfound.accountpurchases.model.Purchase;

/*
 * PurchaseRepository.java    v.1.0 12.05.2016
 *
 * Copyright (c) 2015-2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

@RealmModule(classes = {Purchase.class})
public class SimpleRealmModule {

}

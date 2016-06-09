package ru.allfound.accountpurchases.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

import java.util.ArrayList;

import io.realm.RealmResults;
import ru.allfound.accountpurchases.model.Purchase;
import ru.allfound.accountpurchases.adapters.PurchaseRecyclerAdapter;
import ru.allfound.accountpurchases.R;
import ru.allfound.accountpurchases.realm.IPurchaseRepository;
import ru.allfound.accountpurchases.realm.PurchaseRepository;
import ru.allfound.accountpurchases.tools.XmlParser;

/*
 * MainActivity.java    v.1.1 10.05.2016
 *
 * Copyright (c) 2015-2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RealmResults<Purchase> purchases;
    private Purchase purchase;
    private RecyclerView recyclerView;
    private PurchaseRepository repository;
    PurchaseRecyclerAdapter purchaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPurchaseActivity.class);
                intent.putExtra("id", "0");
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        repository = new PurchaseRepository();
        registerForContextMenu(recyclerView);

        RecyclerItemClickSupport.addTo(recyclerView).setOnItemClickListener(new RecyclerItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                purchase = purchases.get(position);
                Intent intent = new Intent(getApplication(), AddPurchaseActivity.class);
                intent.putExtra("id", purchase.getId());
                startActivity(intent);
            }
        });

        purchases = repository.fetchPurchases();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        purchaseRecyclerAdapter = new PurchaseRecyclerAdapter(purchases, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(purchaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_load) {
            XmlParser xmlParser = new XmlParser(getApplicationContext(), repository);
            xmlParser.parser();
            setupRecyclerView();
            return true;
        } else if (id == R.id.action_clear) {
            repository.deletePurchases();
            setupRecyclerView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = purchaseRecyclerAdapter.getPosition();
        } catch (Exception e) {
            e.getLocalizedMessage();
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.ctx_menu_delete:
                repository.deletePurchaseById(purchases.get(position).getId().toString());
                setupRecyclerView();
                break;
            case R.id.ctx_menu_copy:
                Purchase purchaseNew = Purchase.newInstance(purchases.get(position));
                repository.addPurchase(purchaseNew);
                setupRecyclerView();
                break;
        }
        return super.onContextItemSelected(item);
    }
}

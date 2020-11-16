package com.example.cosmeticreview.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.cosmeticreview.model.CosmeticReviewData;
import com.example.cosmeticreview.R;
import com.example.cosmeticreview.adapters.DealAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ArrayList<CosmeticReviewData> deals = new ArrayList<>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
//        FirebaseUtil.openFbReference("travelDeal");
//        mFirebaseDatabase =  FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference().child("travelDeal");

        RecyclerView rvDeals = (RecyclerView) findViewById(R.id.rvDeals);
       DealAdapter adapter = new DealAdapter(this);
        rvDeals.setAdapter(adapter);
        LinearLayoutManager dealsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDeals.setLayoutManager(dealsLayoutManager);
//        mChildListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//
//                TravelDeal td=dataSnapshot.getValue(TravelDeal.class);
////                Log.d("Deals: ", td.getTitle());
//                td.setId(dataSnapshot.getKey());
//                deals.add(td);
//                Log.d("DealAdapter", "onChildAdded: ");
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Log.d("DealAdapter", "onChildChanged: ");
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };


//        mDatabaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//
//                TravelDeal td=dataSnapshot.getValue(TravelDeal.class);
////                Log.d("Deals: ", td.getTitle());
//                td.setId(dataSnapshot.getKey());
//                deals.add(td);
//                Log.d("DealAdapter", "onChildAdded: "+td);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                TravelDeal td=snapshot.getValue(TravelDeal.class);
////                Log.d("Deals: ", td.getTitle());
//                td.setId(snapshot.getKey());
//                deals.add(td);
//
//                Log.d("DealAdapter", "onChildChanged: ");
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        MenuItem insertMenu = menu.findItem(R.id.insert_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_menu:
                Intent intent = new Intent(this, InsertActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu:
//                AuthUI.getInstance()
//                        .signOut(this)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            public void onComplete(@NonNull Task<Void> task) {
//
//                                // ...
//                                Log.d("logout","User Logged Out");
//                                FirebaseUtil.attachListener();
//                            }
//                        });
//                FirebaseUtil.detachListener();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        FirebaseUtil.openFbReference("TravelDeal");
//        RecyclerView rvDeals = (RecyclerView) findViewById(R.id.rvDeals);
//        final DealAdapter adapter = new DealAdapter();
//        rvDeals.setAdapter(adapter);
//        LinearLayoutManager dealsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        rvDeals.setLayoutManager(dealsLayoutManager);
//
//    }
}
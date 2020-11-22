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

import static com.example.cosmeticreview.utils.FirebaseUtil.mFirebaseAuth;

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
//        DealAdapter adapter = new DealAdapter();
        DealAdapter adapter = new DealAdapter(this);
        rvDeals.setAdapter(adapter);
        LinearLayoutManager dealsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDeals.setLayoutManager(dealsLayoutManager);
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
                mFirebaseAuth.signOut();
                Intent loginscreen = new Intent(ListActivity.this, LoginActivity.class);
                loginscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginscreen);
                finish();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
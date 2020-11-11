package com.example.cosmeticreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle;
    EditText txtDescription;
    TravelDeal deal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        mFirebaseDatabase=FirebaseUtil.mFirebaseDatabase;
//     FirebaseUtil.openFbReference("travelDeal");
        mDatabaseReference=FirebaseUtil.mDatabaseReference;
        txtTitle=(EditText)findViewById(R.id.txtTitle);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //we use switch to get item that was clicked
        switch (item.getItemId()){
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this,"comment saved",Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;
            case R.id.delete_menu:
                deletedDeal();
                Toast.makeText(this,"deal deleted",Toast.LENGTH_LONG).show();
                //we call this method because we want to be back to the list after deleteing
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);

        return true;
    }
    private void saveDeal() {
        deal.setTitle(txtTitle.getText().toString());
        deal.setDescription(txtDescription.getText().toString());
        deal.setTitle(txtTitle.getText().toString());
        deal.setDescription(txtDescription.getText().toString());
        if (deal.getId()==null){
            mDatabaseReference.push().setValue(deal);

        }else {
            mDatabaseReference.child(deal.getId()).setValue(deal);
        }
    }

    private void clean(){
        txtTitle.setText("");
        txtDescription.setText("");
        txtTitle.requestFocus();

    }
    private  void backToList(){
        Intent intent=new Intent(this,ListActivity.class);
        startActivity(intent);

    }
    private void deletedDeal(){
        if (deal==null){
            Toast.makeText(this,"please save the deal before deleting",Toast.LENGTH_LONG).show();
            return;
        }
        mDatabaseReference.child(deal.getId()).removeValue();

    }



}

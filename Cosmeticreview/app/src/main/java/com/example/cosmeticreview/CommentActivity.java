package com.example.cosmeticreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    TextView tvProduct;
    ImageView imageView;
    RatingBar tvRating;
    EditText tvComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mFirebaseDatabase=FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference=FirebaseUtil.mDatabaseReference;
        tvProduct=(TextView)findViewById(R.id.tvProduct);
        imageView=(ImageView)findViewById(R.id.imageView);
        tvRating=(RatingBar)findViewById(R.id.tvRating);
        tvComment=(EditText)findViewById(R.id.tvComment);
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

    private void saveDeal(){

    }
    private void clean(){

    }
    private void deletedDeal(){

    }
    private void backToList(){
        
    }

}

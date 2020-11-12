package com.example.cosmeticreview.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosmeticreview.model.Comments;
import com.example.cosmeticreview.model.CosmeticReviewData;
import com.example.cosmeticreview.model.RatingsComments;
import com.example.cosmeticreview.utils.FirebaseUtil;
import com.example.cosmeticreview.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private CosmeticReviewData cosmeticReviewData = null;

    private ArrayList<RatingsComments> commentsList;
    private double userRating = 0.0;
    TextView tvProduct;
    ImageView imageView;
    RatingBar tvRating;
    EditText tvComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        cosmeticReviewData = (CosmeticReviewData) intent.getSerializableExtra("CosmeticReview");

        FirebaseUtil.openFbReference("travelDeal");
        mFirebaseDatabase= FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference=FirebaseUtil.mDatabaseReference.child(cosmeticReviewData.getId()).child("RatingsAndComments");

        commentsList = new ArrayList<>();

        tvProduct=(TextView)findViewById(R.id.tvProduct);
        imageView=(ImageView)findViewById(R.id.imageView);
        tvRating=(RatingBar)findViewById(R.id.tvRating);
        tvComment=(EditText)findViewById(R.id.tvComment);

        tvProduct.setText(cosmeticReviewData.getTitle());

        tvRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userRating = rating;
                Log.d("RATING", "onRatingChanged: "+rating);
            }
        });



        Log.d("COSMETIC DATA", "onCreate: "+cosmeticReviewData.getTitle());
    }

    private void observeRatingsAndCommentsData(){
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RatingsComments ratingsComments = snapshot.getValue(RatingsComments.class);
                commentsList.add(ratingsComments);
                Log.d("COMMENTS", "onChildAdded: "+ratingsComments);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void addComment(){
        if(userRating == 0.0){
            Toast.makeText(this,"Put your rating", Toast.LENGTH_LONG).show();
            return;
        }

        if(tvComment.getText().toString().length()==0){
            Toast.makeText(this,"Write your comment first", Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("RATINGS_COMME", "addComment: "+userRating+"SIZE="+tvComment.getText().toString().length());
        if(cosmeticReviewData != null) {
            RatingsComments ratingsComments = new RatingsComments(tvComment.getText().toString(), userRating);
            mDatabaseReference.push().setValue(ratingsComments);
        }

        Toast.makeText(this,"comment saved",Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //we use switch to get item that was clicked
        switch (item.getItemId()){
            case R.id.save_menu:
                addComment();
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

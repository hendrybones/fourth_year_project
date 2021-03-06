package com.example.cosmeticreview.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


import com.example.cosmeticreview.adapters.CommentAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.cosmeticreview.model.Comments;
import com.example.cosmeticreview.model.CosmeticReviewData;
import com.example.cosmeticreview.model.RatingsComments;
import com.example.cosmeticreview.utils.FirebaseUtil;
import com.example.cosmeticreview.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static android.app.PendingIntent.getActivity;

public class CommentActivity extends AppCompatActivity {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    private CosmeticReviewData cosmeticReviewData = null;

    private ArrayList<RatingsComments> commentsList;
    private double userRating = 0.0;
    private double averageRating = 0.0;
    private int count = 0;
    private String currentUser="Anonymous";
    TextView tvProduct;
    ImageView imageView;
    RatingBar tvRating;
    EditText tvComment;
    private RecyclerView comment_recy;
    private CommentAdapter commentAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        comment_recy = (RecyclerView) findViewById(R.id.comment_r);

        Intent intent = getIntent();
        cosmeticReviewData = (CosmeticReviewData) intent.getSerializableExtra("CosmeticReview");

        FirebaseUtil.openFbReference("travelDeal");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                currentUser = snapshot.getValue().toString();
                Log.d("CURRENT_USER", "onChildAdded: USER = "+currentUser);
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
        mDatabaseReference = FirebaseUtil.mDatabaseReference.child(cosmeticReviewData.getId()).child("RatingsAndComments");

        commentsList = new ArrayList<>();

        tvProduct = (TextView) findViewById(R.id.tvProduct);
        imageView = (ImageView) findViewById(R.id.imageView);
        tvRating = (RatingBar) findViewById(R.id.tvRating);
        tvComment = (EditText) findViewById(R.id.tvComment);

        String id = cosmeticReviewData.getId();

        tvProduct.setText(cosmeticReviewData.getTitle());

        Log.d("USER", "USERNAME = "+mAuth.getCurrentUser().getDisplayName());

        Log.d("IMAGE_URL", "onCreate: " + cosmeticReviewData.getImageUrl());
        Log.d("AVERAGE_RATING", "onCreate: " + cosmeticReviewData.getAverageRating());
        showImageWithGlide(cosmeticReviewData.getImageUrl());

        observeRatingsAndCommentsData();

        tvRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userRating = rating;
                Log.d("RATING", "onRatingChanged: " + rating);
            }
        });


        Log.d("COSMETIC DATA", "onCreate: " + cosmeticReviewData.getTitle());
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        commentAdapter = new CommentAdapter(id);
        comment_recy.setAdapter(commentAdapter);
        comment_recy.setLayoutManager(linearLayoutManager);

    }

    private void observeRatingsAndCommentsData() {
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RatingsComments ratingsComments = snapshot.getValue(RatingsComments.class);
                averageRating = averageRating + ratingsComments.getRating();
                count++;
                Log.d("RatingsComments", "onChildAdded: "+ratingsComments.getRating());
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

    private void addComment() {
        if (userRating == 0.0) {
            Toast.makeText(this, "Put your rating", Toast.LENGTH_LONG).show();
            return;
        }

        if (tvComment.getText().toString().length() == 0) {
            Toast.makeText(this, "Write your comment first", Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("RATINGS_COMME", "addComment: " + userRating + "SIZE=" + tvComment.getText().toString().length());

        if (cosmeticReviewData != null) {
            SharedPreferences sharedPref = CommentActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            String username = sharedPref.getString("username", "username");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
            String currentDate = sdf.format(new Date());

            RatingsComments ratingsComments = new RatingsComments(currentUser, tvComment.getText().toString(), userRating, currentDate);
            mDatabaseReference.push().setValue(ratingsComments);
        }
//rating logic
        int aver = (int) (averageRating/count);
        mDatabaseReference = FirebaseUtil.mDatabaseReference.child(cosmeticReviewData.getId()).child("averageRating");
        Log.d("AVERAGE_RATING", "addComment: NEW AVERAGE = "+averageRating);
        mDatabaseReference.setValue(aver);

        Toast.makeText(this, "comment saved", Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //we use switch to get item that was clicked
        switch (item.getItemId()) {
            case R.id.save_menu:
                addComment();
                clean();
                backToList();
                return true;
            case R.id.delete_menu:
                deletedDeal();
                Toast.makeText(this, "deal deleted", Toast.LENGTH_LONG).show();
                //we call this method because we want to be back to the list after deleteing
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    private void saveDeal() {
    }

    private void clean() {
        tvComment.setText("");
    }

    private void deletedDeal() {

    }

    private void backToList() {
    }

    private void showImageWithGlide(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .apply(new RequestOptions().override(600, 200))
                .into(imageView);
    }
}

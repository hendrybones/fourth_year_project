package com.example.cosmeticreview.utils;

import android.app.Activity;

import com.example.cosmeticreview.model.CosmeticReviewData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseStorage mStorage;
    public static StorageReference mStorageREf;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseUtil firebaseUtil;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static ArrayList<CosmeticReviewData> mDeals;
    private static Activity caller;
    private static int RC_SIGN_IN = 123;


    private FirebaseUtil() {
    }


    public static void openFbReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();

//            mAuthListener = new FirebaseAuth.AuthStateListener() {
//                @Override
//                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                    if (firebaseAuth.getCurrentUser() == null) {
//                        FirebaseUtil.signIn();
//                    }
//                    Toast.makeText(callerActivity.getBaseContext(), "welcome back", Toast.LENGTH_LONG).show();
//
//
//                }
//            };
            connectStorage();


        }
        mDeals = new ArrayList<CosmeticReviewData>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
        //implement firebase storage instance and references.
    }
    public static  void connectStorage(){
        mStorage =FirebaseStorage.getInstance();
        mStorageREf=mStorage.getReference().child("product_pictures");

    }


}

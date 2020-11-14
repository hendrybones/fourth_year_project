package com.example.cosmeticreview.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cosmeticreview.utils.FirebaseUtil;
import com.example.cosmeticreview.R;
import com.example.cosmeticreview.model.CosmeticReviewData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class InsertActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private static final int PICTURE_RESULTS=42;
    EditText txtTitle;
    EditText txtDescription;
    ImageView imageView;
    CosmeticReviewData deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        mFirebaseDatabase= FirebaseUtil.mFirebaseDatabase;
//     FirebaseUtil.openFbReference("travelDeal");
        mDatabaseReference=FirebaseUtil.mDatabaseReference;
        txtTitle=(EditText)findViewById(R.id.txtTitle);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
        imageView=(ImageView)findViewById(R.id.imageView);

        // for testing
        Intent intent=getIntent();
        CosmeticReviewData deal=(CosmeticReviewData)intent.getSerializableExtra("Deal");
        if (deal==null){
            deal=new CosmeticReviewData();
        }
        this.deal=deal;
        showImage(deal.getImageUrl());
//i created a button to add image  first step
        //go to firebase util add firebase storage variable

        Button btn_Upload=(Button)findViewById(R.id.btn_Upload);
        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,
                        "insert picture"),42);
            }
        });

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
    //adding to the storAGE

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICTURE_RESULTS && resultCode==RESULT_OK);
        Uri imageUri=data.getData();
        StorageReference ref=FirebaseUtil.mStorageREf.child(imageUri.getLastPathSegment());
        ref.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // setting url of the image to the title and description
                String url=taskSnapshot.getUploadSessionUri().toString();
                deal.setImageUrl(url);
                showImage(url);
            }
        });
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
        Intent intent=new Intent(this, ListActivity.class);
        startActivity(intent);

    }
    private void deletedDeal(){
        if (deal==null){
            Toast.makeText(this,"please save the deal before deleting",Toast.LENGTH_LONG).show();
            return;
        }
        mDatabaseReference.child(deal.getId()).removeValue();

    }
    //displayying image
    private void showImage(String url){
        if (url !=null && url.isEmpty()==false){
            int width= Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.get()
                    .load(url)
                    .resize(160,160)
                    .centerCrop()
                    .into(imageView);
        }


    }



}

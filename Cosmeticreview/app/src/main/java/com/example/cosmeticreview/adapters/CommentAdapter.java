package com.example.cosmeticreview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticreview.R;
import com.example.cosmeticreview.model.CommentData;
import com.example.cosmeticreview.model.Comments;
import com.example.cosmeticreview.model.RatingsComments;
import com.example.cosmeticreview.utils.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    ArrayList<RatingsComments> data;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    String id;
public CommentAdapter( String id){
    this.id = id;


    FirebaseUtil.openFbReference("travelDeal");
    data = FirebaseUtil.mData;
    mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
    mDatabaseReference = FirebaseUtil.mDatabaseReference.child(id).child("RatingsAndComments");
    mChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            RatingsComments ratings = snapshot.getValue(RatingsComments.class);
            ratings.setId(snapshot.getKey());
            data.add(ratings);
            notifyItemInserted(data.size() - 1);
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
    };
    mDatabaseReference.addChildEventListener(mChildListener);

};



    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.comment_list, parent, false);
        return new CommentViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        RatingsComments rate = data.get(position);
        holder.bind(rate);

    }

    @Override
    public int getItemCount() {
        if (data !=null){
            return  data.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView comment_txt;
        TextView person_txt;
        TextView date_txt;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_txt = itemView.findViewById(R.id.comment_txt);
            person_txt = itemView.findViewById(R.id.person_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
        }

        public void bind(RatingsComments ratings){
            comment_txt.setText(ratings.getComment());
            person_txt.setText(ratings.getUsername());
            date_txt.setText(ratings.getDate());
        }
    }


}

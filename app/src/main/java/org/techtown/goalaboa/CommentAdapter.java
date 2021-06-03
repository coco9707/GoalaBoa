package org.techtown.goalaboa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CommentAdapter extends FirestoreRecyclerAdapter<CommentData, CommentAdapter.MyViewHolder> {
    private Context mContext;
    private OnItemClickListener listener;

    public CommentAdapter(FirestoreRecyclerOptions<CommentData> options, Context context){
        super(options);
        this.mContext = context;
    }

    @Override
    protected void onBindViewHolder(MyViewHolder holder, int position,@NonNull CommentData model) {
        //holder.imageView.setImageDrawable(mDataset.get(position).getPicture());
        holder.textID.setText(model.getmEmail());
        holder.date.setText(model.getMdate());
        holder.comment.setText(model.getMcomment());
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textID;
        TextView comment;
        TextView date;
        public MyViewHolder(View view) {
            super(view);
            textID = view.findViewById(R.id.comment_id);
            comment = view.findViewById(R.id.comment_text);
            date = view.findViewById(R.id.comment_time);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }
                }
            });


        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}

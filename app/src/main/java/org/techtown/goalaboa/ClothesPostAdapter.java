package org.techtown.goalaboa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ClothesPostAdapter extends FirestoreRecyclerAdapter<ClothesPostData, ClothesPostAdapter.ClothesPostHolder> {

    private OnItemClickListener listener;

    public ClothesPostAdapter(@NonNull FirestoreRecyclerOptions<ClothesPostData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ClothesPostHolder holder, int position, @NonNull ClothesPostData model) {
        holder.textViewTitle.setText(model.getCtitle());
        holder.dateText.setText(model.getCdate());
    }

    @NonNull
    @Override
    public ClothesPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothespost_item, parent, false);
        return new ClothesPostHolder(v);
    }

    class ClothesPostHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView dateText;

        public ClothesPostHolder(View itemView){
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.cp_item_title);
            dateText = itemView.findViewById(R.id.cp_item_date);

            itemView.setOnClickListener(new View.OnClickListener() {
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

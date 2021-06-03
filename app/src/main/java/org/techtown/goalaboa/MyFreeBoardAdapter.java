package org.techtown.goalaboa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class MyFreeBoardAdapter extends FirestoreRecyclerAdapter<FreeBoardData, MyFreeBoardAdapter.FreeBoardHolder> {

    private OnItemClickListener listener;
    private OnItemLongClickListener onItemLongClickListener;
    private Context mContext;

    public MyFreeBoardAdapter(@NonNull FirestoreRecyclerOptions<FreeBoardData> options, Context context) {
        super(options);
        this.mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FreeBoardHolder holder, int position, @NonNull FreeBoardData model) {
        holder.textViewTitle.setText(model.getMtitle());
        holder.dateText.setText(model.getMdate());

    }

    @NonNull
    @Override
    public FreeBoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.free_title_item,parent,false);
        return new FreeBoardHolder(v);
    }

    class FreeBoardHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView dateText;

        public FreeBoardHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.free_view_title);
            dateText = itemView.findViewById(R.id.free_view_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        onItemLongClickListener.onItemLongClick(getSnapshots().getSnapshot(position), position);
                    }
                    return false;
                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(DocumentSnapshot documentSnapshot , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){this.onItemLongClickListener = listener;}
}


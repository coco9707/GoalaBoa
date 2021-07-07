package org.techtown.goalaboa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Top extends Fragment {

    private Button btn1;
    private Button btn2;
    private ImageButton imageButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ClothesPostAdapter madapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_top, container, false);

        btn1 = (Button) view.findViewById(R.id.top_up); // 맨위로 버튼
        btn2 = (Button) view.findViewById(R.id.top_write); // 글쓰기 버튼
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);

        Query query = db.collection("Shopping").whereEqualTo("ccategory", "Top").orderBy("cdate",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ClothesPostData> options = new FirestoreRecyclerOptions.Builder<ClothesPostData>()
                .setQuery(query, ClothesPostData.class)
                .build();
        madapter = new ClothesPostAdapter(options);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.top_recyclerview);
        // 아이템 항목의 크기가 변경되지 않에 하려면 true
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(madapter);

        madapter.setOnItemClickListener(new ClothesPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ClothesPostData info = documentSnapshot.toObject(ClothesPostData.class);
                String id = documentSnapshot.getId(); // 데이터 아이디
                String title = info.getCtitle();
                String day = info.getCdate();

                Intent intent = new Intent(getActivity(), ClothesPost.class);

                intent.putExtra("free_docuId",id);
                intent.putExtra("free_title",title);
                intent.putExtra("free_day",day);

                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent project = new Intent(getActivity(), ClothesPostActivity.class);
                startActivity(project);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.frag_top, new Frag4()).addToBackStack(null).commit();
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        madapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        madapter.stopListening();
    }


}


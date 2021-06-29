package org.techtown.goalaboa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Frag2_FreeBoard extends Fragment {

    private Button btn1;
    private Button btn2;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference freeRef = db.collection("FreeBoard");

    private FreeBoardAdapter madapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_freeboard, container, false);

        btn1 = (Button) view.findViewById(R.id.free_up); // 맨위로 버튼
        btn2 = (Button) view.findViewById(R.id.free_write); // 글쓰기 버튼

        Query query = freeRef.orderBy("mdate",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<FreeBoardData> options = new FirestoreRecyclerOptions.Builder<FreeBoardData>()
                .setQuery(query,FreeBoardData.class)
                .build();
        madapter = new FreeBoardAdapter(options);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.free_recyclerview);
        // 아이템 항목의 크기가 변경되지 않에 하려면 true
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(madapter);

        madapter.setOnItemClickListener(new FreeBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                FreeBoardData info = documentSnapshot.toObject(FreeBoardData.class);
                String id = documentSnapshot.getId(); // 데이터 아이디
                String title = info.getMtitle();
                String day = info.getMdate();

                Intent intent = new Intent(getActivity(), FreeBoard.class);

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
                Intent project = new Intent(getActivity(), FreeBoardActivity.class);
                startActivity(project);
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

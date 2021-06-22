package org.techtown.goalaboa;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyFreeBoard extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference freeRef = db.collection("Contacts");
    FirebaseAuth mAuth;
    private MyFreeBoardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freeboard_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        final String email = muser.getEmail().toString();


        Query query = freeRef.whereEqualTo("memail", email).orderBy("mdate", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<FreeBoardData> options = new FirestoreRecyclerOptions.Builder<FreeBoardData>()
                .setQuery(query, FreeBoardData.class)
                .build();
        adapter = new MyFreeBoardAdapter(options, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myfree_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyFreeBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                FreeBoardData info = documentSnapshot.toObject(FreeBoardData.class);
                String id = documentSnapshot.getId(); // 데이터 아이디

                Intent intent = new Intent(MyFreeBoard.this, FreeBoard.class);

                intent.putExtra("free_docuId", id);


                startActivity(intent);
            }
        });
    }
}

package org.techtown.goalaboa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FreeBoard extends AppCompatActivity {
    private CommentAdapter mAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("Contacts");

    private static final String KEY_DATE = "mdate";
    private static final String KEY_TITLE = "mtitle";
    private static final String KEY_PURPOSE = "mtext";
    private static final String KEY_EMAIL = "memail";
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freeboard_contents);

        final Intent intent = getIntent();
        final TextView muserid = findViewById(R.id.free_id);
        final TextView mtitle = findViewById(R.id.free_title);
        final TextView mday = findViewById(R.id.free_time);
        final TextView mpur = findViewById(R.id.free_textdata);

        final String docuId = intent.getStringExtra("free_docuId");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        final String email = muser.getEmail().toString();
        final String userID = muser.getUid().toString();

        Query query = db.collection("Contacts").document(docuId).collection("comment").orderBy("mdate");
        FirestoreRecyclerOptions<CommentData> options = new FirestoreRecyclerOptions.Builder<CommentData>()
                .setQuery(query,CommentData.class)
                .build();
        mAdapter = new CommentAdapter(options,this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.freecomment_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);


        db.collection("Contacts").document(docuId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String title = documentSnapshot.getString(KEY_TITLE);
                    String day = documentSnapshot.getString(KEY_DATE);
                    String pur = documentSnapshot.getString(KEY_PURPOSE);
                    String mail = documentSnapshot.getString(KEY_EMAIL);

                    //나머지 키값 셋해주기.
                    mtitle.setText(title);
                    mday.setText(day);
                    mpur.setText(pur);
                    muserid.setText(mail);

                }else{
                    Toast.makeText(FreeBoard.this, "Not Data", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        //final TextView comment = findViewById(R.id.free_comment_text);
        Button send = findViewById(R.id.freecomment_send);
        final EditText editText = findViewById(R.id.freecomment_edit_message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long time = System.currentTimeMillis();
                Date day2 = new Date(time);
                String text = editText.getText().toString();
                CommentData commentData = new CommentData(userID,text,mFormat.format(day2),email);
                db.collection("Contacts").document(docuId).collection("comment").document().set(commentData);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);


            }
        });

        mAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final DocumentSnapshot documentSnapshot, int position) {

                CommentData commentData = documentSnapshot.toObject(CommentData.class);
                String getKeyEmail = commentData.getmEmail().toString();

                if (email.equals(getKeyEmail)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FreeBoard.this);
                    dialog.setTitle("댓글 삭제")
                            .setMessage("댓글을 삭제하시겠습니까?")
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.collection("Contacts").document(docuId).collection("comment").document(documentSnapshot.getId()).delete();
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                    Toast.makeText(FreeBoard.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    dialog.create();
                    dialog.show();

                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

}

package org.techtown.goalaboa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FreeBoardActivity extends AppCompatActivity {

    Button btn1;
    private EditText titletext;
    private EditText text;
    FirebaseAuth mAuth;


    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference freeRef = db.collection("Contacts");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freeboard_write);

        titletext = findViewById(R.id.free_titleText);
        text = findViewById(R.id.free_text);
        btn1 = findViewById(R.id.free_Button);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        final String email = muser.getEmail().toString();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(v,email);
            }
        });
    }

    public void add(View v,String email){
        String title = titletext.getText().toString();
        String tx = text.getText().toString();
        long time = System.currentTimeMillis();
        Date day = new Date(time);
        if (title.isEmpty() || tx.isEmpty()){
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            FreeBoardData freeBoradData = new FreeBoardData(title, mFormat.format(day), tx, email);
            freeRef.add(freeBoradData);

            Toast.makeText(FreeBoardActivity.this, "글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FreeBoardActivity.this,MainActivity.class);
            startActivity(intent);

        }
    }

}

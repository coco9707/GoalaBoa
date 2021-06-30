package org.techtown.goalaboa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClothesPostActivity extends AppCompatActivity{

    String[] items = {"Outer", "Top", "Bottoms", "Shoes"};
    private String sp;

    Button btn1;
    private EditText titletext;
    private EditText text;
    private TextView sptext;
    FirebaseAuth mAuth;
    Spinner spinner;

    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference clothesRef = db.collection("Shopping");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothespost_write);

        titletext = findViewById(R.id.cp_titleText);
        text = findViewById(R.id.cp_text);
        sptext = findViewById(R.id.cp_spinner_text);
        btn1 = findViewById(R.id.cp_Button);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        final String email = muser.getEmail().toString();

        // simple_spinner_item은 레이아웃 즉 화면에 보이는 모양
        spinner = findViewById(R.id.cp_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items
        );
        // 스피너가 선택됐을 때 보여주기 위한 레이아웃
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 스피너의 하나의 아이템을 선택했을 때 아래 메소드가 호출
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                sptext.setText(items[position]);
                sp = items[position];
            }

            @Override
            // 선택한 것을 선택하지 않은 상태로 만들어주기
            public void onNothingSelected(AdapterView<?> adapterView) {
                sptext.setText("");
            }
        });

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
            ClothesPostData clothesPostData = new ClothesPostData(title, mFormat.format(day), tx, email, sp);
            clothesRef.add(clothesPostData);

            Toast.makeText(ClothesPostActivity.this, "글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ClothesPostActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

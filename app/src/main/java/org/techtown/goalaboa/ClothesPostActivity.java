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

public class ClothesPostActivity extends AppCompatActivity {



    Button btn1;
    private EditText titletext;
    private EditText text;
    private TextView sptext;
    FirebaseAuth mAuth;
    private Spinner spinner_category1, spinner_category2;
    private ArrayAdapter<String> arrayAdapter;


    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference clothesRef = db.collection("Shopping");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        spinner_category1 = findViewById(R.id.cp_spinner);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.spinner_category));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category1.setAdapter(arrayAdapter);

        spinner_category2 = findViewById(R.id.cp_spinner2);
        initAddressSpinner();

        /*
        // 스피너의 하나의 아이템을 선택했을 때 아래 메소드가 호출
        spinner_category1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long l) {
                sptext.setText(items[position]);
                sp = items[position];
            }
        });
        */

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
              add(v, email);
            }
        });
}

    public void add(View v, String email) {
        String title = titletext.getText().toString();
        String tx = text.getText().toString();
        long time = System.currentTimeMillis();
        Date day = new Date(time);
        String sp = spinner_category1.getSelectedItem().toString();
        String sp2 = spinner_category2.getSelectedItem().toString();
        if (title.isEmpty() || tx.isEmpty()) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            ClothesPostData clothesPostData = new ClothesPostData(title, mFormat.format(day), tx, email, sp, sp2);
            clothesRef.add(clothesPostData);

            Toast.makeText(ClothesPostActivity.this, "글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ClothesPostActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void initAddressSpinner() {
        spinner_category1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setCategory2AdapterItem(R.array.spinner_outer);
                        break;
                    case 1:
                        setCategory2AdapterItem(R.array.spinner_top);
                        break;
                    case 2:
                        setCategory2AdapterItem(R.array.spinner_bottom);
                        break;
                    case 3:
                        setCategory2AdapterItem(R.array.spinner_shoes);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setCategory2AdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            spinner_category2.setAdapter(null);
            arrayAdapter = null;
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category2.setAdapter(arrayAdapter);
    }

}

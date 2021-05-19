package org.techtown.goalaboa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;      // 회원가입 입력필드
    private Button mBtnRegister;            // 회원가입 버튼
    private RadioButton mPerson, mCompany;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.useAppLanguage();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("LoginTestVersion");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_register);
        mPerson = findViewById(R.id.r_person);
        mCompany = findViewById(R.id.r_company);
        mRadioGroup = findViewById(R.id.register_radio);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                final String strEmail = mEtEmail.getText().toString();
                final String strPwd = mEtPwd.getText().toString();
                final boolean checkPerson = mPerson.isChecked();
                final boolean checkCompany = mCompany.isChecked();

                // Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    // 가입 되었을 때 처리
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // 회원가입된 현재 유저 가져오기
                            final FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {                         //해당 이메일에 확인메일을 보냄 진짜 사용하는 이메일인지 확인 용도...
                                        Log.d("Email sent.", "이메일 보냄");
                                        Toast.makeText(RegisterActivity.this, "인증 메일을 발송합니다. " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                                    } else {                                             //메일 보내기 실패
                                        Log.e("sendEmailVerification", "이메일 보내기 실패", task.getException());
                                        Toast.makeText(RegisterActivity.this,
                                                "인증 메일 발송에 실패했습니다.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            if(checkPerson) {
                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);
                                account.setPerson(true);
                                // LoginTestVersion의 child
                                // setValue : database에 insert (삽입)행위
                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                            }else if(checkCompany){
                                CompanyAccount caccount = new CompanyAccount();
                                caccount.setIdToken(firebaseUser.getUid());
                                caccount.setEmailId(firebaseUser.getEmail());
                                caccount.setPassword(strPwd);
                                caccount.setCompany(true);
                                mDatabaseRef.child("CUserAccount").child(firebaseUser.getUid()).setValue(caccount);
                            } else{
                                Log.d("Register", "라디오 버튼 에러");
                            }
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else{
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
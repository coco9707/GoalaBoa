package org.techtown.goalaboa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // password -> a~z, A~Z, 0~9, !@.#$%^&*?_~ 4~16자리
    // nickname -> a~z, A~Z, 가~힣 1~8자리
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣]{1,8}$");

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mEtNick;      // 회원가입 입력필드
    private Button mBtnRegister;            // 회원가입 버튼
    private RadioButton mPerson, mCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.useAppLanguage();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("LoginTestVersion");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtNick = findViewById(R.id.et_nickname);
        mBtnRegister = findViewById(R.id.btn_register);
        mPerson = findViewById(R.id.r_person);
        mCompany = findViewById(R.id.r_company);
        // mRadioGroup = findViewById(R.id.register_radio);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                final String strEmail = mEtEmail.getText().toString();
                isValidEmail(strEmail);
                final String strPwd = mEtPwd.getText().toString();
                isValidPasswd(strPwd);
                final String strNickname = mEtNick.getText().toString();
                isValidNick(strNickname);

                final boolean checkPerson = mPerson.isChecked();
                final boolean checkCompany = mCompany.isChecked();


                // Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    // 가입 되었을 때 처리
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
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

                            if (checkPerson) {
                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);
                                account.setNickname(strNickname);
                                account.setPerson(true);
                                // LoginTestVersion의 child
                                // setValue : database에 insert (삽입)행위
                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                            } else if (checkCompany) {
                                CompanyAccount caccount = new CompanyAccount();
                                caccount.setIdToken(firebaseUser.getUid());
                                caccount.setEmailId(firebaseUser.getEmail());
                                caccount.setPassword(strPwd);
                                caccount.setNickname(strNickname);
                                caccount.setCompany(true);
                                mDatabaseRef.child("CompanyAccount").child(firebaseUser.getUid()).setValue(caccount);
                            } else {
                                Log.d("Register", "라디오 버튼 에러");
                            }
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private boolean isValidEmail(String strEmail) {
        if (strEmail.isEmpty()) {
            // 이메일 공백
            Toast.makeText(RegisterActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            Toast.makeText(RegisterActivity.this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPasswd(String strPwd) {
        if (strPwd.isEmpty()) {
            // 비밀번호 공백
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(strPwd).matches()) {
            Toast.makeText(this, "비밀번호 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidNick(String strNickname) {
        if (strNickname.isEmpty()) {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!NICKNAME_PATTERN.matcher(strNickname).matches()) {
            Toast.makeText(this, "닉네임 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }
    }

}
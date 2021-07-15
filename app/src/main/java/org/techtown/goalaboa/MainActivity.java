package org.techtown.goalaboa;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawView;
    private Button categori_outer;
    private NavigationView nav;

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1_ClothesPost frag1_ClothesPost;
    private Frag2_FreeBoard frag2_FreeBoard;
    // 테스트 후 바텀 네비 아우터를 드로워로 바꿔야함
    private Frag3 frag3;
    private Frag4 frag4;
    private Frag5 frag5;

    private FirebaseAuth mFirebaseAuth;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // FirebaseAuth 초기화
        mFirebaseAuth = FirebaseAuth.getInstance();

        // 로그아웃 버튼
        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃 하기
                mFirebaseAuth.signOut();

                // Main Activity에서 Login Activity로
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 탈퇴 처리
        // mFirebaseAuth.getCurrentUser().delete();

        //카테고리 네비게이션바
        nav = findViewById(R.id.nav);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //drawView = (View)findViewById(R.id.drawer);
       //categori_outer = findViewById(R.id.categori_outer);

        ImageView btn_open = (ImageView)findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Drawer 탭 카테고리 눌렀을 때
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.category_outer:
                        Toast.makeText(MainActivity.this, "outer", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.category_pants:
                        Toast.makeText(MainActivity.this, "pants", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.category_shoes:
                        Toast.makeText(MainActivity.this, "shoes", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.category_top:
                        Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
                        break;
                }

                //Drawer를 닫기...
                drawerLayout.closeDrawer(nav);

                return false;
            }
        });

        /* 카테고리 네비게이션바 버튼 닫기
        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });*/

        drawerLayout.setDrawerListener(listener);

        /*
        drawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }

        });
    */

        // 바텀네비
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_addcloth:
                        setFrag(0);
                        break;
                    case R.id.action_review:
                        setFrag(1);
                        break;
                    case R.id.action_home:
                        setFrag(2);
                        break;
                    case R.id.action_favorites:
                        setFrag(3);
                        break;
                    case R.id.action_mypage:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });

        frag1_ClothesPost = new Frag1_ClothesPost();
        frag2_FreeBoard = new Frag2_FreeBoard();
        frag3 = new Frag3();
        frag4 = new Frag4();
        frag5 = new Frag5();
        setFrag(0);//첫 프레그먼트 화면 지정

    }

    //카테고리 드로우리스너, 설정 안함, 필요하면 할 것
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    //바텀네비 프레그먼트 교체 일어나는 실행문
    public void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n) {
            case 0:
                ft.replace(R.id.main_frame, frag1_ClothesPost).commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2_FreeBoard).commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3).commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4).commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, frag5).commit();
                break;
        }
    }
}

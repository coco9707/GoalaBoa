package org.techtown.goalaboa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag4 extends Fragment {

    private View view;
    private Button outer_all, cardigan, hood_zip, jacket, coat, padding, outer_others;
    private Button top_all, short_sleeve, long_sleeve, shirt, mantoman_hood, top_others;
    private Button bottom_all, denim, slacks, cotton_pants, training, buttom_others;
    private Button shoes_all,sneakers, sport_shoes, loafer, sandal_slipper, shoes_others;
    private Categorization1 categorization1;
    private Categorization2 categorization2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag4, container, false);

        categorization1 = new Categorization1();
        categorization2 = new Categorization2();

        // 아우터
        outer_all = view.findViewById(R.id.outer_all);
        cardigan = view.findViewById(R.id.cardigan);
        hood_zip = view.findViewById(R.id.hood_zip);
        jacket = view.findViewById(R.id.jacket);
        coat = view.findViewById(R.id.coat);
        padding = view.findViewById(R.id.padding);
        outer_others = view.findViewById(R.id.outer_others);

        top_all = view.findViewById(R.id.top_all);
        short_sleeve = view.findViewById(R.id.short_sleeve);
        long_sleeve = view.findViewById(R.id.long_sleeve);
        shirt = view.findViewById(R.id.shirt);
        mantoman_hood = view.findViewById(R.id.mantoman_hood);
        top_others = view.findViewById(R.id.top_others);

        bottom_all = view.findViewById(R.id.bottom_all);
        denim = view.findViewById(R.id.denim);
        slacks = view.findViewById(R.id.slacks);
        cotton_pants = view.findViewById(R.id.cotton_pants);
        training = view.findViewById(R.id.training);
        buttom_others = view.findViewById(R.id.buttom_others);

        shoes_all = view.findViewById(R.id.shoes_all);
        sneakers = view.findViewById(R.id.sneakers);
        sport_shoes = view.findViewById(R.id.sport_shoes);
        loafer = view.findViewById(R.id.loafer);
        sandal_slipper = view.findViewById(R.id.sandal_slipper);
        shoes_others = view.findViewById(R.id.shoes_others);

        //클릭 이벤트
        outer_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory1", "아우터");
            }
        });
        cardigan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "가디건");
            }
        });
        hood_zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "후드집업");
            }
        });
        jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setCategory("ccategory2", "자켓");
            }
        });
        coat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "코트");
            }
        });
        padding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "패딩");
            }
        });
        outer_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryOthers("ccategory1", "아우터", "ccategory2", "기타");
            }
        });

        top_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory1", "상의");
            }
        });
        short_sleeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "반팔 티셔츠");
            }
        });
        long_sleeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "긴팔 티셔츠");
            }
        });
        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "셔츠");
            }
        });
        mantoman_hood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "맨투맨/후드");
            }
        });
        top_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryOthers("ccategory1", "상의", "ccategory2", "기타");
            }
        });

        bottom_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory1", "하의");
            }
        });
        denim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "데님");
            }
        });
        slacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "슬랙스");
            }
        });
        cotton_pants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "면바지");
            }
        });
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "트레이닝");
            }
        });
        buttom_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryOthers("ccategory1", "하의", "ccategory2", "기타");
            }
        });

        shoes_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory1", "신발");
            }
        });
        sneakers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "스니커즈");
            }
        });
        sport_shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "운동화");
            }
        });
        loafer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "로퍼");
            }
        });
        sandal_slipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategory("ccategory2", "샌들/슬리퍼");
            }
        });
        shoes_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryOthers("ccategory1", "신발", "ccategory2", "기타");
            }
        });
        return view;
    }

    private void setCategory(String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString("category", key);
        bundle.putString("field", value);
        categorization1.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.main_frame, categorization1).addToBackStack(null).commit();
    }

    private void setCategoryOthers(String key1, String value1, String key2, String value2) {
        Bundle bundle = new Bundle();
        bundle.putString("category1", key1);
        bundle.putString("field1", value1);
        bundle.putString("category2", key2);
        bundle.putString("field2", value2);
        categorization2.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.main_frame, categorization2).addToBackStack(null).commit();
    }
}

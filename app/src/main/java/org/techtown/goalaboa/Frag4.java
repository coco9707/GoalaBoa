package org.techtown.goalaboa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Stack;

public class Frag4 extends Fragment {


    private View view;
    private Button outer_all;
    private Button top_all;
    private Outer outer;
    private Top top;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag4, container, false);

        outer = new Outer();
        top = new Top();
        outer_all = view.findViewById(R.id.outer_all);
        top_all = view.findViewById(R.id.top_all);

        //클릭 이벤트
        outer_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.main_frame, outer).addToBackStack(null).commit();
            }
        });

        top_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.main_frame, top).addToBackStack(null).commit();
            }
        });


        return view;
    }


}

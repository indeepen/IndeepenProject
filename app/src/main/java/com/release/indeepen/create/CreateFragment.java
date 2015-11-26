package com.release.indeepen.create;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.release.indeepen.MainTab;
import com.release.indeepen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment implements MainTab {
    Boolean tag = false;

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);
       /* LinearLayout btn = (LinearLayout) view.findViewById(R.id.btn_create_image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MediaMultiChoiceActivity.class);
                startActivity(intent);
            }
        });

        btn = (LinearLayout) view.findViewById(R.id.btn_create_picture);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateInputCultureActivity.class);
                startActivity(intent);
            }
        });

        final LinearLayout btn_create_music_add = (LinearLayout) view.findViewById(R.id.btn_create_music_add);
        final LinearLayout btn_music = (LinearLayout) view.findViewById(R.id.btn_create_music);
        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tag == false) {
                    btn_create_music_add.setVisibility(View.VISIBLE);
                    btn_music.setVisibility(View.GONE);
                    tag = true;
                } else {
                    btn_create_music_add.setVisibility(View.GONE);
                    btn_music.setVisibility(View.VISIBLE);
                    tag = false;
                }
                tag = false;
            }
        });

        btn = (LinearLayout) view.findViewById(R.id.btn_create_media);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateInputCultureActivity.class);
                startActivity(intent);
            }
        });

        btn = (LinearLayout) view.findViewById(R.id.btn_create_culture);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateInputCultureActivity.class);
                startActivity(intent);
            }
        });*/
        view.setBackgroundColor(Color.BLACK);
        Intent intent = new Intent(getContext(), CreateActivity.class);
        startActivity(intent);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getContainer() {
        return 0;
    }


}

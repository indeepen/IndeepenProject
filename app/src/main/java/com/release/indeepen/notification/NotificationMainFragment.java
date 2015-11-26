package com.release.indeepen.notification;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationMainFragment extends Fragment {

    NotiAdapter mNotiAdapter;
    ListView vNotiList;

    public NotificationMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_notification, container, false);
        vNotiList = (ListView) view.findViewById(R.id.list_noti);

        mNotiAdapter = new NotiAdapter();
        vNotiList.setAdapter(mNotiAdapter);

        vNotiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (CallbackListener.mFragnetListener instanceof NotificationFragment) {
               //     CallbackListener.mFragnetListener.onReplaceFragment(new ContentDetailFragment(), DefineContentType.CALLBACK_TO_BLOG);
               // }
                switch(((PushData)mNotiAdapter.getItem((int) vNotiList.getItemIdAtPosition(position))).nContentType) {
                    case DefineContentType.SINGLE_ART_TYPE_PAINT:
                    case DefineContentType.SINGLE_ART_TYPE_PICTURE:
                    case DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE:{
                        Intent mIntent = new Intent(getContext(), MainActivity.class);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_DETAIL_IMGAE);
                        //mIntent.putExtra(DefineContentType.KEY_ON_NEW_GET_DATA_URL, ); // 이동시 다시 받아올 Data URL
                        startActivity(mIntent);
                        break;
                    }
                    case DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO:{
                        Intent mIntent = new Intent(getContext(), MainActivity.class);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_DETAIL_MUSIC_VIDEO);
                        //mIntent.putExtra(DefineContentType.KEY_ON_NEW_GET_DATA_URL, ); // 이동시 다시 받아올 Data URL
                        startActivity(mIntent);
                    }
                    case DefineContentType.SINGLE_ART_TYPE_MUSIC:{
                        Intent mIntent = new Intent(getContext(), MainActivity.class);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_DETAIL_MUSIC);
                        //mIntent.putExtra(DefineContentType.KEY_ON_NEW_GET_DATA_URL, ); // 이동시 다시 받아올 Data URL
                        startActivity(mIntent);
                    }
                    case DefineContentType.SINGLE_ART_TYPE_YOUTUBE:{
                        Intent mIntent = new Intent(getContext(), MainActivity.class);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_DETAIL_YOUTUBE);
                        //mIntent.putExtra(DefineContentType.KEY_ON_NEW_GET_DATA_URL, ); // 이동시 다시 받아올 Data URL
                        startActivity(mIntent);
                    }
                    case DefineContentType.SINGLE_ART_TYPE_CULTURE:{
                        Intent mIntent = new Intent(getContext(), MainActivity.class);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                        mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_DETAIL_CULTURE);
                        //mIntent.putExtra(DefineContentType.KEY_ON_NEW_GET_DATA_URL, ); // 이동시 다시 받아올 Data URL
                        startActivity(mIntent);
                    }
                }
            }
        });

        init();
        return view;
    }


    private void init() {

        for (int idx = 0; idx < 8; idx++) {
            PushData mData = new PushData();
            mData.nPushType = 1;
            mData.nContentType = DefineContentType.SINGLE_ART_TYPE_PICTURE;
            //mData.thProfile = DefineTest.ARR_IMG[idx];
            mData.sMSG = idx + "";
            mNotiAdapter.add(mData);
        }
    }


}

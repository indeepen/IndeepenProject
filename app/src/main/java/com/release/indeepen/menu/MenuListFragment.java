package com.release.indeepen.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.release.indeepen.R;

/**
 * Created by Lady on 2015. 11. 16..
 */
public class MenuListFragment extends Fragment {

    ListView vList;
    MenuAdapter mAdapter;

    public MenuListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.view_add_space_list, container, false);

        vList = (ListView)view.findViewById(R.id.add_art_space_list);
        mAdapter = new MenuAdapter();
        vList.setAdapter(mAdapter);
        initData();

        return view;
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            MenuItemData mData = new MenuItemData();
            mData.ImgArtSpace = getResources().getDrawable(R.drawable.emo_love);
            mData.sArtSpaceName = ""+i;

            mAdapter.add(mData);
        }
    }

}

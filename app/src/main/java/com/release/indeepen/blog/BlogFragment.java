package com.release.indeepen.blog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.release.indeepen.MainActivity;
import com.release.indeepen.MainTab;
import com.release.indeepen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment implements MainTab, MainActivity.OnKeyBackPressedListener {

    FragmentManager mFM;
    boolean isFirst = false;
    ImageView actionSearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirst = true;
        mFM = getChildFragmentManager();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        actionSearch = (ImageView) getActivity().findViewById(R.id.img_main_search);
        if (isFirst) {
            init();
            isFirst = false;
        }

        mFM.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                checkBackStack();
            }
        });
        return view;
    }

    private void init() {
        mFM.beginTransaction().replace(R.id.container_blog, new BlogMainFragment()).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setOnKeyBackPressedListener(this);
        checkBackStack();
    }

    private void checkBackStack(){
        int stackHeight = mFM.getBackStackEntryCount();
        if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
            ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actionSearch.setVisibility(View.GONE);
        } else {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
            actionSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBack() {
        if (((MainActivity) getActivity()).getOnKeyBackPressedListener() instanceof BlogFragment) {
            if (mFM.getBackStackEntryCount() > 0) {
                mFM.popBackStack();
            } else {
                MainActivity activity = (MainActivity) getActivity();
                activity.setOnKeyBackPressedListener(null);
                activity.onBackPressed();
            }
        }
    }

    public int getContainer(){
        return R.id.container_blog;
    }

}

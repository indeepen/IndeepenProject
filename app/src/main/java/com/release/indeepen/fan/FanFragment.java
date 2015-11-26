package com.release.indeepen.fan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.MainActivity;
import com.release.indeepen.MainTab;
import com.release.indeepen.R;
import com.release.indeepen.search.SearchTripleFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FanFragment extends Fragment implements MainTab, MainActivity.OnKeyBackPressedListener {

    FragmentManager mFM;
    boolean isFirst = false;
    ImageView actionSearch;
    ImageView actionRealSearch;

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
        View view = inflater.inflate(R.layout.fragment_fan, container, false);
        actionSearch = (ImageView) getActivity().findViewById(R.id.img_main_search);
        actionRealSearch = (ImageView) getActivity().findViewById(R.id.img_main_real_search);
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
        Bundle b = new Bundle();
        b.putInt(DefineContentType.KEY_ON_NEW_FROM, DefineContentType.FROM_FAN);
        FanMainFragment fanMainFragment = new FanMainFragment();
        fanMainFragment.setArguments(b);
        mFM.beginTransaction().replace(R.id.container_fan, fanMainFragment, DefineContentType.FRAGMENT_TAG_FAN).commit();
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
        }else {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
            actionSearch.setVisibility(View.VISIBLE);
            actionRealSearch.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBack() {
        boolean isPop = false;
        Fragment fan = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_FAN);
        Fragment search = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_SEARCH);
        if(null != fan){
            if(null != ((FanMainFragment) fan).emotion  && ((FanMainFragment) fan).emotion.isShowing()){
                ((FanMainFragment) fan).emotion.dismiss();
                if(((FanMainFragment) fan).nEmotion == -2){
                    ((FanMainFragment) fan).vBtnEmo.setSelected(false);
                }
                isPop =true;
            }
            if(null != ((FanMainFragment) fan).category  &&((FanMainFragment) fan).category.isShowing()){
                ((FanMainFragment) fan).category.dismiss();
                if(((FanMainFragment) fan).nCategory == -2){
                    ((FanMainFragment) fan).vBtnCategory.setSelected(false);
                }
                isPop =true;
            }
        }

        if(null != search){
            if(null != ((SearchTripleFragment) search).emotion  &&  ((SearchTripleFragment) search).emotion.isShowing()){
                ((SearchTripleFragment) search).emotion.dismiss();
                if(((FanMainFragment) fan).nEmotion == -2){
                    ((FanMainFragment) fan).vBtnEmo.setSelected(false);
                }
                isPop =true;
            }
            if(null != ((SearchTripleFragment) search).category  &&((SearchTripleFragment) search).category.isShowing()){
                ((SearchTripleFragment) search).category.dismiss();
                if(((FanMainFragment) fan).nCategory == -2){
                    ((FanMainFragment) fan).vBtnCategory.setSelected(false);
                }
                isPop =true;
            }
        }

        if(false == isPop) {
            if (((MainActivity) getActivity()).getOnKeyBackPressedListener() instanceof FanFragment) {
                popFragmentStack();
            }
        }
    }

    private void popFragmentStack(){

        if (mFM.getBackStackEntryCount() > 0) {
            mFM.popBackStack();
        } else {
            MainActivity activity = (MainActivity) getActivity();
            activity.setOnKeyBackPressedListener(null);
            activity.onBackPressed();
        }

    }

    @Override
    public int getContainer() {
        return R.id.container_fan;
    }
}

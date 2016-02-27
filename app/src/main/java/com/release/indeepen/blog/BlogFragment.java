package com.release.indeepen.blog;


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
import com.release.indeepen.culture.CultureListFragment;
import com.release.indeepen.fan.FanMainFragment;
import com.release.indeepen.search.SearchTripleFragment;
import com.release.indeepen.space.SpaceFragment;

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
        BlogMainFragment blogMainFragment = new BlogMainFragment();
        blogMainFragment.setArguments(getArguments());
        mFM.beginTransaction().replace(R.id.container_blog, blogMainFragment, DefineContentType.FRAGMENT_TAG_MY_BLOG).commit();
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
        boolean isPop = false;
        Fragment fan = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_FAN);
        Fragment search = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_SEARCH);
        Fragment blog = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_MY_BLOG);
        Fragment space = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_SPACE);
        Fragment culture = mFM.findFragmentByTag(DefineContentType.FRAGMENT_TAG_CULTURE);
        // Fragment culture_local = mFM.findFragmentByTag(DefineContentType.FRAGMENT_TAG_CULTURE_LOCAL);

        if (null != culture) {
            if (null != ((CultureListFragment) culture).popup_type && ((CultureListFragment) culture).popup_type.isShowing()) {
                ((CultureListFragment) culture).popup_type.dismiss();
                ((CultureListFragment) culture).btn_type.setSelected(false);

                isPop = true;
            }
            if (null != ((CultureListFragment) culture).popup_date && ((CultureListFragment) culture).popup_date.isShowing()) {
                ((CultureListFragment) culture).popup_date.dismiss();
                ((CultureListFragment) culture).btn_date.setSelected(false);
                isPop = true;
            }
            if(((CultureListFragment) culture).closeOptionsPop()){
                isPop = true;
            }
        }
        if(null != space) {
            if (null != ((SpaceFragment) space).popup_profile && ((SpaceFragment) space).popup_profile.isShowing()) {
                ((SpaceFragment) space).popup_profile.dismiss();
                isPop = true;
            }
        }

        if(null != blog) {
            if (null != ((BlogMainFragment) blog).popup_profile && ((BlogMainFragment) blog).popup_profile.isShowing()) {
                ((BlogMainFragment) blog).popup_profile.dismiss();
                isPop = true;
            }
        }
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
            if(((FanMainFragment) fan).closeOptionsPop()){
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
            if (((MainActivity) getActivity()).getOnKeyBackPressedListener() instanceof BlogFragment) {
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

    public int getContainer(){
        return R.id.container_blog;
    }

}

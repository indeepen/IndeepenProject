package com.release.indeepen.culture;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.MainActivity;
import com.release.indeepen.MainTab;
import com.release.indeepen.R;
import com.release.indeepen.blog.BlogMainFragment;
import com.release.indeepen.fan.FanMainFragment;
import com.release.indeepen.space.SpaceFragment;

import static com.release.indeepen.MainActivity.OnKeyBackPressedListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CultureFragment extends Fragment
        implements OnKeyBackPressedListener, MainTab {

    CultureListFragment mListF;
    FragmentManager mFM;
    CultureHeaderView vHeader;
    boolean isFirst = false;
    boolean isStart;
    CultureLocalFragment mLocalF;
    TypePopupWindow popup_type;
    DatePopupWindow popup_date;
    int nLocal;
    int nDate;
    int nType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirst = true;
        mFM = getChildFragmentManager();

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
        //WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_culture, container, false);
        mListF = new CultureListFragment();
        vHeader = new CultureHeaderView(getContext());


        if (isFirst) {
            init();
            isFirst = false;
        }

        return view;
    }

    private void init() {
        Bundle b = new Bundle();
        b.putInt(DefineContentType.KEY_ON_NEW_FROM, DefineContentType.FROM_CULTURE);
        CultureListFragment mLFragment = new CultureListFragment();
        mLFragment.setArguments(b);
        mFM.beginTransaction().replace(R.id.container_culture, mLFragment, DefineContentType.FRAGMENT_TAG_CULTURE).commit();

    }


    @Override
    public void onResume() {
        isStart = true;

        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setOnKeyBackPressedListener(this);
        }
    }

    @Override
    public void onBack() {
        boolean isPop = false;
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
        Fragment fan = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_FAN);
        Fragment search = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_SEARCH);
        Fragment blog = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_MY_BLOG);
        Fragment space = mFM.findFragmentByTag( DefineContentType.FRAGMENT_TAG_SPACE);

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

        }


        if (false == isPop) {
            if (((MainActivity) getActivity()).getOnKeyBackPressedListener() instanceof CultureFragment) {
                popFragmentStack();
            }
        }
    }

    private void popFragmentStack() {
        if (((MainActivity) getActivity()).getOnKeyBackPressedListener() instanceof CultureFragment) {
            if (mFM.getBackStackEntryCount() > 0) {
                mFM.popBackStack();
            } else {
                MainActivity activity = (MainActivity) getActivity();
                activity.setOnKeyBackPressedListener(null);
                activity.onBackPressed();
            }
        }
    }

    @Override
    public int getContainer() {
        return R.id.container_culture;
    }


}

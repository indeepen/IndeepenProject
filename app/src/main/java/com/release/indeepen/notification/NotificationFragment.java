package com.release.indeepen.notification;


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
public class NotificationFragment extends Fragment implements MainTab, MainActivity.OnKeyBackPressedListener/*, CallbackListener.OnGoActivityListener*/ {
    FragmentManager mFM;
    boolean isFirst = false;
    ImageView actionSearch;

    public NotificationFragment() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

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
        mFM.beginTransaction().replace(R.id.container_notify, new NotificationMainFragment()).commit();
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
        if (((MainActivity) getActivity()).getOnKeyBackPressedListener() instanceof NotificationFragment) {
            if (mFM.getBackStackEntryCount() > 0) {
                mFM.popBackStack();
            } else {
                MainActivity activity = (MainActivity) getActivity();
                activity.setOnKeyBackPressedListener(null);
                activity.onBackPressed();
            }
        }
    }

    /*@Override
    public void onReplaceFragment(Fragment fragment, int Type) {
        switch (Type) {
            case DefineContentType.CALLBACK_TO_SINGLE_LIST: {
                mFM.beginTransaction().addToBackStack(null).replace(R.id.container_notify, fragment).commitAllowingStateLoss();
                break;
            }
            case DefineContentType.CALLBACK_TO_BLOG: {
                mFM.beginTransaction().addToBackStack(null).replace(R.id.container_notify, fragment).commitAllowingStateLoss();
                break;
            }
        }
    }*/

   /* @Override
    public void onGoActivity(Intent intent, int type) {
        switch (type) {

            case DefineContentType.CALLBACK_TO_BLOG: {
                startActivity(intent);
                break;
            }
        }
    }*/

    @Override
    public int getContainer() {
        return R.id.container_notify;
    }
}

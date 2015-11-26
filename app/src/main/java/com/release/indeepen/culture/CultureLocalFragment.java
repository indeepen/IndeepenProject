package com.release.indeepen.culture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.release.indeepen.MainActivity;
import com.release.indeepen.R;

public class CultureLocalFragment extends Fragment implements MainActivity.OnKeyBackPressedListener {
    private static final String LOCAL = "local";

    private String name;
    FragmentManager mFM;

    public static CultureLocalFragment newInstance(String name) {
        CultureLocalFragment fragment = new CultureLocalFragment();
        Bundle args = new Bundle();
        args.putString(LOCAL, name);
        fragment.setArguments(args);
        return fragment;
    }

    public CultureLocalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFM = getChildFragmentManager();

        if (getArguments() != null) {
            name = getArguments().getString(LOCAL);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_culture_local, container, false);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBack() {
        if (((MainActivity) getActivity()).getOnKeyBackPressedListener() instanceof CultureLocalFragment) {
            getChildFragmentManager().popBackStack();
            mFM = getChildFragmentManager();
            Fragment fragment = this.getParentFragment();
            fragment.getChildFragmentManager().popBackStack();
        }
    }

}

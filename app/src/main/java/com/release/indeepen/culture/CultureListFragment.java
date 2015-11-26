package com.release.indeepen.culture;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResultList;
import com.release.indeepen.management.networkManager.netCulture.CultureController;
import com.release.indeepen.management.networkManager.netCulture.CultureListRequest;

import java.util.List;

/**
 * Created by Lady on 2015. 11. 5..
 */
public class CultureListFragment extends Fragment {

    ListView vList;
    CultureAdapter mAdapter;
    CultureHeaderView vHeader;
    boolean isLastItem;
    boolean isStart;
    int nLocal;
    int nDate;
    int nType;
    FragmentManager mFM;
    CultureLocalFragment mLocalF;
    TypePopupWindow popup_type;
    DatePopupWindow popup_date;
    Button btn_local, btn_type, btn_date;

    public CultureListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_sing_list, container, false);


        vList = (ListView) view.findViewById(R.id.list_single);
        vHeader = new CultureHeaderView(getContext());

        mAdapter = new CultureAdapter();
        vList.getHeaderViewsCount();
        //vList.addHeaderView(vHeader);
        vList.setHeaderDividersEnabled(false);
        vList.setAdapter(mAdapter);

        vList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    getMoreItem();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });

        initData();

        btn_local = (Button) vHeader.findViewById(R.id.btn_local);
        btn_date = (Button) vHeader.findViewById(R.id.btn_date);
        btn_type = (Button) vHeader.findViewById(R.id.btn_type);


        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(getActivity(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_CULTURE_LOCAL);
                getContext().startActivity(mIntent);

                //   btn_local.setSelected(!btn_local.isSelected());
                btn_local.setSelected(true);
                btn_date.setSelected(false);
                btn_type.setSelected(false);


            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_date.setSelected(true);
                btn_local.setSelected(false);
                btn_type.setSelected(false);
                onDatePopupWindow(v);


            }
        });

        btn_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btn_date.setSelected(false);
                btn_local.setSelected(false);
                btn_type.setSelected(true);

                onTypePopupWindow(v);

            }
        });

        return view;
    }


    private void initData() {
        isStart = true;
        nLocal = -1;
        nDate = -1;
        nType = -1;
        if (null != getArguments()) {
            if (DefineContentType.FROM_CULTURE == getArguments().getInt(DefineContentType.KEY_ON_NEW_FROM)) {
                vList.removeHeaderView(vHeader);
                vList.addHeaderView(vHeader);
            } else {
                vList.removeHeaderView(vHeader);
            }

        } else {
            vList.removeHeaderView(vHeader);
        }
        getMoreItem();
    }

    private void getMoreItem() {
        CultureListRequest request = new CultureListRequest();
        if (null != getArguments() && DefineContentType.FROM_CULTURE == getArguments().getInt(DefineContentType.KEY_ON_NEW_FROM)){
            if (isStart) {
                request.setURL(DefineNetwork.CULTURE_LIST);

            } else {
                request.setURL(DefineNetwork.CULTURE_LIST_MORE);
            }
        }else{
            if (isStart) {
                request.setURL(getArguments().getString(DefineNetwork.REQUEST_URL));

            } else {
                request.setURL(getArguments().getString(DefineNetwork.REQUEST_URL_MORE));
            }
        }
        CultureController.getInstance().getCultureList(request, new NetworkProcess.OnResultListener<ContentResultList>() {
            @Override
            public void onSuccess(NetworkRequest<ContentResultList> request, ContentResultList result) {
                if (isStart) {
                    if (0 < mAdapter.getCount()) {
                        mAdapter.clear();
                    }
                    isStart = false;
                }
                List<CultureItemData> list = CultureController.getInstance().getRealContentList(result);
                mAdapter.addList(list);

            }

            @Override
            public void onFail(NetworkRequest<ContentResultList> request, int code) {
                if (isStart) {
                    mAdapter.clear();
                    isStart = false;
                }
            }
        });
    }



    public CultureAdapter getAdapter() {
        return mAdapter;
    }

    private void onTypePopupWindow(final View view) {
        popup_type = new TypePopupWindow(getContext());
        popup_type.setOutsideTouchable(true);
        popup_type.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup_type.showAtLocation(view, Gravity.CENTER, 0, 0);


        popup_type.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_type.setBackground(getResources().getDrawable(R.drawable.category_all));
                popup_type.dismiss();
            }
        });

        popup_type.btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_type.setBackground(getResources().getDrawable(R.drawable.category_display));
                popup_type.dismiss();
            }
        });

        popup_type.btnPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_type.setBackground(getResources().getDrawable(R.drawable.category_performance));
                popup_type.dismiss();
            }
        });

        popup_type.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_type.setBackground(getResources().getDrawable(R.drawable.category_show));
                popup_type.dismiss();
            }
        });

        popup_type.btnArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_type.setBackground(getResources().getDrawable(R.drawable.category_art));
                popup_type.dismiss();
            }
        });

        popup_type.btnFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_type.setBackground(getResources().getDrawable(R.drawable.category_festival));
                popup_type.dismiss();
            }
        });

    }

    private void onDatePopupWindow(View view) {
        popup_date = new DatePopupWindow(getContext());
        popup_date.setOutsideTouchable(true);
        popup_date.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup_date.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


}

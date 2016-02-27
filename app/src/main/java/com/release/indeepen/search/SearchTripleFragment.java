package com.release.indeepen.search;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.blog.tripleGrid.HeaderGridView;
import com.release.indeepen.blog.tripleGrid.TripleGridAdapter;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.fan.FanHeaderView;
import com.release.indeepen.fan.PopupCategory;
import com.release.indeepen.fan.PopupEmotion;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogContentRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogController;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogContentList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTripleFragment extends Fragment {

    public PopupEmotion emotion;
    public PopupCategory category;
    HeaderGridView vGrid;
    FanHeaderView vHeader;
    TripleGridAdapter mAdapter;
    ImageView actionRealSearch;
    int nSaveIdx = -1;
    int nSaveTop = 0;
    int nEmotion;
    int nCategory;
    boolean isLastItem;
    boolean isStart;
    String sKeyword;

    public SearchTripleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        actionRealSearch = (ImageView) getActivity().findViewById(R.id.img_main_real_search);

        vGrid = (HeaderGridView) view.findViewById(R.id.grid_search);
        vHeader = new FanHeaderView(getContext());
        vGrid.addHeaderView(vHeader);

        // mAdapter = new TripleGridAdapter(getContext());
        //vGrid.setAdapter(mAdapter);

        Button btn = (Button) vHeader.findViewById(R.id.btn_fan_emotion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPopupEmotion(v);
            }
        });

        btn = (Button) vHeader.findViewById(R.id.btn_fan_category);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPopupCategory(v);
            }
        });

        vGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_FAN_LIST);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_FROM, DefineContentType.FROM_SEARCH);

                mIntent.putExtra(DefineNetwork.LIST_POSITION, (int) (vGrid.getItemIdAtPosition(position)));

                if(TextUtils.isEmpty(sKeyword)) {
                    mIntent.putExtra(DefineNetwork.REQUEST_URL, DefineNetwork.RECOMMEND_SINGLE_LIST);
                    mIntent.putExtra(DefineNetwork.REQUEST_URL_MORE, DefineNetwork.RECOMMEND_SINGLE_LIST_MORE);
                }else{
                    try {
                        mIntent.putExtra(DefineNetwork.REQUEST_URL, String.format(DefineNetwork.HASHTAG_SINGLE_LIST,URLEncoder.encode(sKeyword, DefineNetwork.CHARACTER_SET)));
                        mIntent.putExtra(DefineNetwork.REQUEST_URL_MORE, String.format(DefineNetwork.HASHTAG_SINGLE_LIST_MORE,URLEncoder.encode(sKeyword, DefineNetwork.CHARACTER_SET)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

                startActivity(mIntent);
            }
        });

        vGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mLastFirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                    getMoreItem();

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                isLastItem = totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
            }
        });

        if (nSaveIdx != -1) {
            vGrid.setAdapter(mAdapter);
            vGrid.setSelection(nSaveIdx);
        } else {
            mAdapter = new TripleGridAdapter(getContext());
            vGrid.setAdapter(mAdapter);

            init();
        }
        return view;
    }

    private void init() {
        isStart = true;
        nEmotion = -2;
        nCategory = -2;

        getMoreItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        actionRealSearch.setVisibility(View.VISIBLE);
    }

    private void onPopupEmotion(View view) {
        emotion = new PopupEmotion(getContext());
        emotion.setOutsideTouchable(true);
        emotion.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        emotion.showAtLocation(view, Gravity.CENTER, 0, 0);

        emotion.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nEmotion = -1;
                isStart = true;
                getMoreItem();

                emotion.dismiss();
            }
        });
        emotion.btnNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nEmotion = 0;
                isStart = true;
                getMoreItem();

                emotion.dismiss();
            }
        });
        emotion.btnHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nEmotion = 1;
                isStart = true;
                getMoreItem();

                emotion.dismiss();
            }
        });
        emotion.btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nEmotion = 2;
                isStart = true;
                getMoreItem();

                emotion.dismiss();
            }
        });
        emotion.btnSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nEmotion = 3;
                isStart = true;
                getMoreItem();

                emotion.dismiss();
            }
        });
        emotion.btnAngry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nEmotion = 4;
                isStart = true;
                getMoreItem();

                emotion.dismiss();
            }
        });

    }


    private void onPopupCategory(View view) {
        category = new PopupCategory(getContext());
        category.setOutsideTouchable(false);
        category.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        category.showAtLocation(view, Gravity.CENTER, 0, 0);


        category.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nCategory = -1;
                isStart = true;
                getMoreItem();

                category.dismiss();
            }
        });
        category.btnPaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nCategory = DefineContentType.SINGLE_ART_TYPE_PAINT;
                isStart = true;
                getMoreItem();

                category.dismiss();
            }
        });
        category.btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nCategory = DefineContentType.SINGLE_ART_TYPE_PICTURE;
                isStart = true;
                getMoreItem();

                category.dismiss();
            }
        });
        category.btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nCategory = DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE;
                isStart = true;
                getMoreItem();

                category.dismiss();
            }
        });
        category.btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nCategory = DefineContentType.SINGLE_ART_TYPE_YOUTUBE;
                isStart = true;
                getMoreItem();

                category.dismiss();
            }
        });
        category.btnCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nCategory = DefineContentType.SINGLE_ART_TYPE_CULTURE;
                nEmotion = -1;
                isStart = true;
                getMoreItem();

                category.dismiss();
            }
        });
    }


    private void getMoreItem() {
        MyBlogContentRequest contentRequest = new MyBlogContentRequest();
        String url = "";

        if(null != getArguments()) {
            sKeyword = getArguments().getString(DefineContentType.BUNDLE_DATA_REQUEST);
            if(!TextUtils.isEmpty(sKeyword)) {
                if (isStart) {
                    //request.setURL(String.format(DefineNetwork.FNA_LIST_FILTER, nEmotion < 0 || nCategory == DefineContentType.SINGLE_ART_TYPE_CULTURE ? "" : nEmotion + "", nCategory < 0 ? "" : nCategory + ""));

                    try {
                        contentRequest.setURL(String.format(DefineNetwork.HASHTAG_TRIPLE_LIST, URLEncoder.encode(sKeyword, DefineNetwork.CHARACTER_SET)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                } else {
                    //request.setURL(String.format(DefineNetwork.FNA_LIST_FILTER_MORE, nEmotion < 0 || nCategory == DefineContentType.SINGLE_ART_TYPE_CULTURE ? "" : nEmotion + "", nCategory < 0 ? "" : nCategory + ""));
                    try {
                        contentRequest.setURL(String.format(DefineNetwork.HASHTAG_TRIPLE_LIST_MORE, URLEncoder.encode(sKeyword, DefineNetwork.CHARACTER_SET)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }
        }else {
            if (isStart) {
                //request.setURL(String.format(DefineNetwork.FNA_LIST_FILTER, nEmotion < 0 || nCategory == DefineContentType.SINGLE_ART_TYPE_CULTURE ? "" : nEmotion + "", nCategory < 0 ? "" : nCategory + ""));
                url = DefineNetwork.RECOMMEND_TRIPLE_LIST;
                contentRequest.setURL(url);

            } else {
                //request.setURL(String.format(DefineNetwork.FNA_LIST_FILTER_MORE, nEmotion < 0 || nCategory == DefineContentType.SINGLE_ART_TYPE_CULTURE ? "" : nEmotion + "", nCategory < 0 ? "" : nCategory + ""));
                url = DefineNetwork.RECOMMEND_TRIPLE_LIST_MORE;
                contentRequest.setURL(url);
            }
        }

        MyBlogController.getInstance().getMyBlogContent(contentRequest, new NetworkProcess.OnResultListener<BlogContentList>() {
            @Override
            public void onSuccess(NetworkRequest<BlogContentList> request, BlogContentList result) {
                if (isStart) {
                    if (0 < mAdapter.getCount()) {
                        mAdapter.clear();
                    }
                    isStart = false;
                }
                List<ContentData> arrData = MyBlogController.getInstance().getContentList(result);
                mAdapter.addList(arrData);
            }

            @Override
            public void onFail(NetworkRequest<BlogContentList> request, int code) {
                if (isStart) {
                    mAdapter.clear();
                    isStart = false;
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        nSaveIdx = vGrid.getFirstVisiblePosition();
        View v = vGrid.getChildAt(0);
        nSaveTop = (v == null) ? 0 : (v.getTop() - vGrid.getPaddingTop());
    }


}

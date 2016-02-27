package com.release.indeepen.fan;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.OptionView;
import com.release.indeepen.content.art.singleList.ContentSingleListAdapter;
import com.release.indeepen.content.art.singleList.SingleImageView;
import com.release.indeepen.content.art.singleList.SingleMusicView;
import com.release.indeepen.content.art.singleList.SingleYoutubeView;
import com.release.indeepen.culture.CultureItemView;
import com.release.indeepen.management.musicManager.MusicManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.ArtController;
import com.release.indeepen.management.networkManager.netArt.ArtListRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResultList;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FanMainFragment extends Fragment {

    public PopupEmotion emotion;
    public PopupCategory category;
    public int nEmotion;
    public int nCategory;
    public Button vBtnEmo, vBtnCategory;
    ListView vList;
    ContentSingleListAdapter mAdapter;
    FanHeaderView vHeader;
    boolean isLastItem;
    boolean isStart;
    int nSaveIdx = -1;
    int nSaveTop = 0;
    int postion;
    ProgressDialog dialog;

    public FanMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        nSaveIdx =vList.getFirstVisiblePosition();
        View v = vList.getChildAt(0);
        nSaveTop = (v == null) ? 0 : (v.getTop() - vList.getPaddingTop());

        outState.putInt("index", nSaveIdx);
        outState.putInt("top", nSaveTop);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fan_main, container, false);
        vList = (ListView) view.findViewById(R.id.list_fan);
        vHeader = new FanHeaderView(getContext());
        vList.getHeaderViewsCount();
        vList.addHeaderView(vHeader);
        vList.setHeaderDividersEnabled(false);
        postion = 0;
        //참고
        vBtnEmo = (Button) vHeader.findViewById(R.id.btn_fan_emotion);
        vBtnEmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vBtnEmo.setSelected(true);
                vBtnCategory.setSelected(false);
                onPopupEmotion(v);
            }
        });

        vBtnCategory = (Button) vHeader.findViewById(R.id.btn_fan_category);
        vBtnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vBtnCategory.setSelected(true);
                vBtnEmo.setSelected(false);
                onPopupCategory(v);
            }
        });


        vList.setOnScrollListener(new AbsListView.OnScrollListener() {
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

            vList.setAdapter(mAdapter);
            vList.setSelectionFromTop(nSaveIdx, nSaveTop);
        } else {
            mAdapter = new ContentSingleListAdapter();
            mAdapter.setFragment(this);
            vList.setAdapter(mAdapter);

            init();
        }

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            nSaveIdx = savedInstanceState.getInt("index", -1);
            nSaveTop = savedInstanceState.getInt("top", 0);
            if (nSaveIdx != -1) {
                vList.setSelectionFromTop(nSaveIdx, nSaveTop);
            }
        }

        return view;

    }

    private void init() {

        isStart = true;
        nEmotion = -2;
        nCategory = -2;
        if (null != getArguments()) {
            if (DefineContentType.FROM_FAN == getArguments().getInt(DefineContentType.KEY_ON_NEW_FROM) || DefineContentType.FROM_SEARCH == getArguments().getInt(DefineContentType.KEY_ON_NEW_FROM)) {
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

        ArtListRequest request = new ArtListRequest();
        if (DefineContentType.FROM_FAN == getArguments().getInt(DefineContentType.KEY_ON_NEW_FROM)) {
            if (isStart) {
                request.setURL(String.format(DefineNetwork.FNA_LIST_FILTER, nEmotion < 0 || nCategory == DefineContentType.SINGLE_ART_TYPE_CULTURE ? "" : nEmotion + "", nCategory < 0 ? "" : nCategory + ""));

            } else {
                request.setURL(String.format(DefineNetwork.FNA_LIST_FILTER_MORE, nEmotion < 0 || nCategory == DefineContentType.SINGLE_ART_TYPE_CULTURE ? "" : nEmotion + "", nCategory < 0 ? "" : nCategory + ""));
            }
        } else {
            if (isStart) {
                request.setURL(getArguments().getString(DefineNetwork.REQUEST_URL));

            } else {

                request.setURL(getArguments().getString(DefineNetwork.REQUEST_URL_MORE));
            }


        }
        ArtController.getInstance().getArtList(request, new NetworkProcess.OnResultListener<ContentResultList>() {
            @Override
            public void onSuccess(NetworkRequest<ContentResultList> request, ContentResultList result) {
                postion = getArguments().getInt(DefineNetwork.LIST_POSITION, 0);

                if (-1 == nSaveIdx && 0 != postion) {
                    dialog = new ProgressDialog(getContext());
                    dialog.setIcon(android.R.drawable.ic_dialog_info);
                    dialog.setMessage("...Loading");
                    dialog.show();
                }
                if (isStart) {

                    if (0 < mAdapter.getCount()) {
                        mAdapter.clear();
                    }
                }

                List<ContentData> list = ArtController.getInstance().getContentList(result);
                mAdapter.addList(list);

                if (isStart) {

                    if (-1 != nSaveIdx) {
                        vList.setSelectionFromTop(nSaveIdx, nSaveTop);

                    } else if (0 != postion) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (0 == vList.getHeaderViewsCount()) {
                                    vList.setSelection(postion);
                                } else {
                                    vList.setSelection(postion + 1);
                                }
                                if (null != dialog) {
                                    dialog.dismiss();
                                    dialog = null;
                                }
                            }
                        }, 500);

                    }
                    isStart = false;
                }

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
        category.setOutsideTouchable(true);
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


    @Override
    public void onPause() {
        super.onPause();
        MusicManager.getMusicManager().pause();

        nSaveIdx = vList.getFirstVisiblePosition();
        View v = vList.getChildAt(0);
        nSaveTop = (v == null) ? 0 : (v.getTop() - vList.getPaddingTop());

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isStart) {
            isStart = true;
            getMoreItem();

        }
        mAdapter.notifyDataSetChanged();
    }

    public void setChageData(int type, int positon, ContentData data) {
        switch (type) {
            case DefineContentType.DELETE: {
                mAdapter.removeData(positon);
                break;
            }
            case DefineContentType.LIKE:
            case DefineContentType.COMMENT: {
                mAdapter.changeData(positon, data);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    public void setPosition() {
        if (-1 != nSaveIdx) {
            vList.setSelectionFromTop(nSaveIdx, nSaveTop);
        } else if (0 != postion) {
            vList.setSelection(postion);
        }
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public boolean closeOptionsPop() {
        boolean result = false;
        View view = getViewByPosition(vList.getFirstVisiblePosition(), vList);
        if(view instanceof SingleMusicView || view instanceof SingleImageView || view instanceof SingleYoutubeView || view instanceof CultureItemView){
            result = ((OptionView)view).closePopup();
        }

        view = getViewByPosition(vList.getFirstVisiblePosition()+1, vList);
        if(view instanceof SingleMusicView || view instanceof SingleImageView || view instanceof SingleYoutubeView || view instanceof CultureItemView){
            if(((OptionView)view).closePopup() && !result){
                result = true;
            }
        }
        return result;
    }

}

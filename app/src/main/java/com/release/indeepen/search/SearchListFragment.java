package com.release.indeepen.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.ArtController;
import com.release.indeepen.management.networkManager.netArt.ArtDetailRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResult;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;
import com.release.indeepen.management.networkManager.netSearch.SearchAllRequest;
import com.release.indeepen.management.networkManager.netSearch.SearchController;
import com.release.indeepen.management.networkManager.netSearch.SearchKeywordRequest;
import com.release.indeepen.management.networkManager.netSearch.data.HashTagResult;
import com.release.indeepen.management.networkManager.netSearch.data.SearchAllResult;
import com.release.indeepen.management.networkManager.netSearch.data.SearchResult;
import com.release.indeepen.space.SpaceInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchListFragment extends Fragment {

    public String sKeyword;
    ListView vList;
    int nType;
    SearchAdapter mAdapter;

    public SearchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_main, container, false);
        vList = (ListView) view.findViewById(R.id.list_search);

        mAdapter = new SearchAdapter();
        vList.setAdapter(mAdapter);

        nType = getArguments().getInt(DefineNetwork.REQUEST_TYPE);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchKeyword(sKeyword);
    }

    public void searchKeyword(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            mAdapter.clear();
            switch (nType) {
                case DefineNetwork.TYPE_SEARCH_ALL: {
                    SearchAllRequest request = new SearchAllRequest();

                    try {
                        request.setURL(String.format(DefineNetwork.SEARCH_ALL, URLEncoder.encode(keyword, DefineNetwork.CHARACTER_SET)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    SearchController.getInstance().getSeachAll(request, new NetworkProcess.OnResultListener<SearchResult>() {
                        @Override
                        public void onSuccess(NetworkRequest<SearchResult> request, SearchResult result) {
                            if (null != result) {
                                for (HashTagResult data : result.mResult.arrHashTag) {
                                    Log.e("arrHashTag", data.sHashTag);
                                    mAdapter.add(data);
                                }
                                for (BlogInfo data : result.mResult.arrArtist) {
                                    Log.e("arrArtist", data.sArtist);
                                    mAdapter.add(data);
                                }
                                for (SpaceInfo data : result.mResult.arrSpace) {
                                    Log.e("arrSpace", data.sSpaceName);
                                    mAdapter.add(data);
                                }
                            }

                        }

                        @Override
                        public void onFail(NetworkRequest<SearchResult> request, int code) {
                            if (0 < mAdapter.getCount()) {
                                mAdapter.clear();
                            }
                        }
                    });
                    break;
                }
                case DefineNetwork.TYPE_SEARCH_HASHTAG: {
                    SearchKeywordRequest request = new SearchKeywordRequest();

                    try {
                        request.setURL(String.format(DefineNetwork.SEARCH_HASHTAG, URLEncoder.encode(keyword, DefineNetwork.CHARACTER_SET)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    SearchController.getInstance().getSeachKeyword(request, new NetworkProcess.OnResultListener<SearchAllResult>() {
                        @Override
                        public void onSuccess(NetworkRequest<SearchAllResult> request, SearchAllResult result) {
                            if (null != result) {
                                for (HashTagResult data : result.arrHashTag) {
                                    Log.e("arrHashTag", data.sHashTag);
                                    mAdapter.add(data);
                                }
                            }
                        }

                        @Override
                        public void onFail(NetworkRequest<SearchAllResult> request, int code) {
                            if (0 < mAdapter.getCount()) {
                                mAdapter.clear();
                            }
                        }
                    });
                    break;
                }
                case DefineNetwork.TYPE_SEARCH_ARTIST: {
                    SearchKeywordRequest request = new SearchKeywordRequest();

                    try {
                        request.setURL(String.format(DefineNetwork.SEARCH_ARTIST, URLEncoder.encode(keyword, DefineNetwork.CHARACTER_SET)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    SearchController.getInstance().getSeachKeyword(request, new NetworkProcess.OnResultListener<SearchAllResult>() {
                        @Override
                        public void onSuccess(NetworkRequest<SearchAllResult> request, SearchAllResult result) {
                            if (null != result.arrArtist) {
                                for (BlogInfo data : result.arrArtist) {
                                    Log.e("arrArtist", data.sArtist);
                                    mAdapter.add(data);
                                }
                            }
                        }

                        @Override
                        public void onFail(NetworkRequest<SearchAllResult> request, int code) {
                            if (0 < mAdapter.getCount()) {
                                mAdapter.clear();
                            }
                        }
                    });
                    break;
                }
                case DefineNetwork.TYPE_SEARCH_SPACE: {

                    SearchKeywordRequest request = new SearchKeywordRequest();

                    try {
                        request.setURL(String.format(DefineNetwork.SEARCH_SPACE, URLEncoder.encode(keyword, DefineNetwork.CHARACTER_SET)));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    SearchController.getInstance().getSeachKeyword(request, new NetworkProcess.OnResultListener<SearchAllResult>() {
                        @Override
                        public void onSuccess(NetworkRequest<SearchAllResult> request, SearchAllResult result) {
                            if (null != result) {
                                for (SpaceInfo data : result.arrSpace) {
                                    Log.e("arrSpace", data.sSpaceName);
                                    mAdapter.add(data);
                                }
                            }
                        }

                        @Override
                        public void onFail(NetworkRequest<SearchAllResult> request, int code) {
                            if (0 < mAdapter.getCount()) {
                                mAdapter.clear();
                            }
                        }
                    });
                    break;
                }
            }
        } else {
            mAdapter.clear();
        }

    }
}

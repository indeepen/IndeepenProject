/*
 * Copyright (c) 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.release.indeepen.youtube.uploadManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.release.indeepen.R;
import com.release.indeepen.youtube.util.VideoData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author Ibrahim Ulukaya <ulukaya@google.com>
 *         <p/>
 *         Left side fragment showing user's uploaded YouTube videos.
 */
public class UploadsListFragment extends Fragment implements ConnectionCallbacks,
        OnConnectionFailedListener {

    private static final String TAG = UploadsListFragment.class.getName();
    private static Context mContext;
    UploadedVideoAdapter adapter;
    private Callbacks mCallbacks;
    private GoogleApiClient mGoogleApiClient;
    private GridView mGridView;

    public UploadsListFragment() {
    }

    @SuppressLint("ValidFragment")
    public UploadsListFragment(Context context) {
        mContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.list_fragment, container, false);
        mGridView = (GridView) listView.findViewById(R.id.grid_view);
        TextView emptyView = (TextView) listView.findViewById(android.R.id.empty);
        mGridView.setEmptyView(emptyView);
        mGridView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        return listView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setVideos(List<VideoData> videos) {
        if (!isAdded()) {
            return;
        }
        adapter = new UploadedVideoAdapter(videos);
        mGridView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mGridView.getAdapter() != null) {
            ((UploadedVideoAdapter) mGridView.getAdapter()).notifyDataSetChanged();
        }

        //setProfileInfo();
        mCallbacks.onConnected(Plus.AccountApi.getAccountName(mGoogleApiClient));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            Toast.makeText(getActivity(),
                    R.string.connection_to_google_play_failed, Toast.LENGTH_SHORT)
                    .show();

            Log.e(TAG,
                    String.format(
                            "Connection to Play Services Failed, error: %d, reason: %s",
                            connectionResult.getErrorCode(),
                            connectionResult.toString()));
            try {
                connectionResult.startResolutionForResult(getActivity(), 0);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, e.toString(), e);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new ClassCastException("Activity must implement callbacks.");
        }

        mCallbacks = (Callbacks) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public String getSelectedItme() {
        if (-1 == mGridView.getCheckedItemPosition()) {
            return "";
        }
        return ((VideoData) adapter.getItem(mGridView.getCheckedItemPosition())).getYouTubeId();
    }

    public interface Callbacks {

        void onVideoSelected(VideoData video);

        void onConnected(String connectedAccountName);
    }

    private class UploadedVideoAdapter extends BaseAdapter {
        private List<VideoData> mVideos;

        private UploadedVideoAdapter(List<VideoData> videos) {
            mVideos = videos;
        }

        @Override
        public int getCount() {
            return mVideos.size();
        }

        @Override
        public Object getItem(int i) {
            return mVideos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mVideos.get(i).getYouTubeId().hashCode();
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup container) {
            if (convertView == null) {
               /* convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.list_item, container, false);*/
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.view_youtube_checked_item, container, false);
            }

            VideoData video = mVideos.get(position);
            ((TextView) convertView.findViewById(R.id.text_title))
                    .setText(video.getTitle());
            String uri = video.getThumbUri();
            Picasso.with(getActivity()).load(video.getThumbUri()).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(getActivity().getResources().getDisplayMetrics().widthPixels, 0).into((ImageView) convertView.findViewById(R.id.image_icon));

            return convertView;
        }
    }

}

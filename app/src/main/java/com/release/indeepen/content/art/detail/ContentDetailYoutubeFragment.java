package com.release.indeepen.content.art.detail;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.release.indeepen.comment.CommentActivity;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.release.indeepen.youtube.MyYoutubeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentDetailYoutubeFragment extends Fragment implements View.OnClickListener{
    ContentYoutubeData mData;
    ImageView vThPro, vThEmotion;
    TextView vTextArtist, vTextDate, vText, vTextOption, vTextComment, vTextLike;
    public ContentDetailYoutubeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_detail_youtube, container, false);

        vThPro = (ImageView) view.findViewById(R.id.img_detail_image_thPro);
        vThEmotion = (ImageView) view.findViewById(R.id.img_detail_image_emotion);
        vTextArtist = (TextView) view.findViewById(R.id.text_detail_image_artist);
        vTextDate = (TextView) view.findViewById(R.id.text_detail_image_date);
        vText = (TextView) view.findViewById(R.id.text_detail_image_text);
        vTextComment = (TextView) view.findViewById(R.id.text_detail_image_comm_count);
        vTextLike = (TextView) view.findViewById(R.id.text_detail_image_like_count);
        vTextOption = (TextView) view.findViewById(R.id.text_detail_image_option);

        vThPro.setOnClickListener(this);
        vTextArtist.setOnClickListener(this);
        vTextComment.setOnClickListener(this);
        vTextLike.setOnClickListener(this);
        vTextOption.setOnClickListener(this);


        init();
        return view;
    }

    private void init() {
        MyYoutubeFragment f = new MyYoutubeFragment();
        f.setPath("https://youtu.be/YQHsXMglC9A");
        getFragmentManager().beginTransaction().replace(R.id.container_detail_youtub, f).commit();
        if (null != getArguments()) {
            mData = (ContentYoutubeData) getArguments().getSerializable(DefineContentType.BUNDLE_DATA_REQUEST);
            setData(mData);
        }

    }
    private void setData(ContentYoutubeData data) {
        //vThPro.setImageResource(data.thProfile);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_detail_image_thPro:
            case R.id.text_detail_image_artist:
            case R.id.text_detail_image_date: {
                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineNetwork.BLOG_KEY, mData.sBlogKey);
                if(DefineContentType.BLOG_TYPE_MYBLOG == mData.nBlogType) {
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_BLOG);
                }else{
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_SPACE);
                }
                startActivity(mIntent);
                getActivity().finish();
                break;
            }
            case R.id.text_detail_image_comm_count: {
                Intent mIntent = new Intent(getContext(), CommentActivity.class);
                startActivity(mIntent);

                break;
            }
            case R.id.text_detail_image_like_count: {
                Toast.makeText(getContext(), "좋다", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.text_detail_image_option: {
                onOptionDialog();
                break;
            }
        }
    }

    public void onOptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        // builder.setTitle("List Dialog");
        builder.setItems(new String[]{"싫어요", "공유", "수정", "삭제"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent;
                switch (which) {
                    case 0: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        //builder.setIcon(android.R.drawable.ic_dialog_alert);
                        //builder.setTitle("Alert Dialog");
                        builder.setMessage("이 게시물을 정말 싫어요 하시겠습니까?");
                        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "봐줌", Toast.LENGTH_SHORT).show();

                            }
                        });
                        builder.setNeutralButton("싫어요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "넌 신고", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setCancelable(false);
                        builder.create().show();
                        break;
                    }
                    case 1: {

                        break;
                    }
                    case 2: {
                        //mIntent = new Intent(getContext(), MediaSingleChoiceActivity.class);
                        //mIntent.putExtra(DefineContentType.SELECT_IMAGE, DefineContentType.ACTIVITY_TYPE_PROFILE_IMG);
                        //startActivity(mIntent);
                        break;
                    }
                    case 3: {
                        // mIntent = new Intent(getContext(), MediaSingleChoiceActivity.class);
                        //mIntent.putExtra(DefineContentType.SELECT_IMAGE, DefineContentType.ACTIVITY_TYPE_PROFILE_IMG);
                        // startActivity(mIntent);
                        break;
                    }
                }

            }
        });
        builder.create().show();
    }


}

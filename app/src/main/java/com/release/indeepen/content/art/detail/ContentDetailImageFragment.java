package com.release.indeepen.content.art.detail;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.comment.CommentActivity;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.dateManager.DateManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.ArtController;
import com.release.indeepen.management.networkManager.netArt.ArtDetailRequest;
import com.release.indeepen.management.networkManager.netArt.DELETEContentRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResult;
import com.release.indeepen.management.networkManager.netCommon.CommonController;
import com.release.indeepen.management.networkManager.netCommon.PUTLikeRequest;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentDetailImageFragment extends Fragment implements View.OnClickListener {

    LinearLayout like, comment;
    RelativeLayout option;
    ContentImageData mData;
    ListView vList;
    ImageView vThPro, vIMGEmotion, vTextOption;
    ImageView vImg_like, vImg_comment, vImg_option;
    TextView vTextArtist, vTextDate, vText, vTextComment, vTextLike;
    SimpleImageAdapter mAdapter;
    ContentDetailHeaderView vHeader;
    String sContentKey;

    public ContentDetailImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_detail_image, container, false);

        vHeader = new ContentDetailHeaderView(getContext());
        vList = (ListView) view.findViewById(R.id.list_detail_imgs);

        vThPro = (ImageView) vHeader.findViewById(R.id.img_detail_image_thPro);
        vIMGEmotion = (ImageView) vHeader.findViewById(R.id.img_detail_image_emotion);
        vTextArtist = (TextView) vHeader.findViewById(R.id.text_detail_image_artist);
        vTextDate = (TextView) vHeader.findViewById(R.id.text_detail_image_date);
        vText = (TextView) vHeader.findViewById(R.id.text_detail_image_text);
        vTextComment = (TextView) vHeader.findViewById(R.id.text_detail_image_comm_count);
        vTextLike = (TextView) vHeader.findViewById(R.id.text_detail_image_like_count);
        vTextOption = (ImageView) vHeader.findViewById(R.id.text_detail_image_option);

        vImg_like = (ImageView) vHeader.findViewById(R.id.image_single_like);
        vImg_comment = (ImageView) vHeader.findViewById(R.id.image_single_comm);
        like = (LinearLayout) vHeader.findViewById(R.id.like);
        comment = (LinearLayout) vHeader.findViewById(R.id.comment);
        option = (RelativeLayout) vHeader.findViewById(R.id.option);


        mAdapter = new SimpleImageAdapter();
        vList.addHeaderView(vHeader);
        vList.setAdapter(mAdapter);

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (0 == position) return;
                Intent mIntent = new Intent(getContext(), ExpandImageActivity.class);
               /* Drawable d = ((SimpleImageView)view).getView().getDrawable();
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
                bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
                byte[] byteArray = stream.toByteArray();
*/
                mIntent.putExtra(DefineContentType.EXPAND_IMG, (String) mAdapter.getItem((int) vList.getItemIdAtPosition(position)));
                startActivity(mIntent);
            }
        });

        vThPro.setOnClickListener(this);
        vIMGEmotion.setOnClickListener(this);
        vTextArtist.setOnClickListener(this);
        vTextDate.setOnClickListener(this);
        vText.setOnClickListener(this);
        vTextComment.setOnClickListener(this);
        vTextLike.setOnClickListener(this);
        vTextOption.setOnClickListener(this);


        vImg_like.setOnClickListener(this);
        vImg_comment.setOnClickListener(this);
        like.setOnClickListener(this);
        comment.setOnClickListener(this);
        option.setOnClickListener(this);


        init();
        return view;
    }

    private void init() {

        if (null != getArguments()) {
            sContentKey  = getArguments().getString(DefineContentType.BUNDLE_DATA_REQUEST);

            if(TextUtils.isEmpty(sContentKey)) getActivity().finish();

            ArtDetailRequest request = new ArtDetailRequest();
            request.setURL(DefineNetwork.ART_CONTENT + sContentKey);
            ArtController.getInstance().getDetailContent(request, new NetworkProcess.OnResultListener<ContentResult>() {
                @Override
                public void onSuccess(NetworkRequest<ContentResult> request, ContentResult result) {
                    mData = (ContentImageData) ArtController.getInstance().getContent(result.mResult);
                    setData(mData);
                }

                @Override
                public void onFail(NetworkRequest<ContentResult> request, int code) {

                }
            });
        }
    }

    private void setData(ContentImageData data) {
        if (null == data) return;
        vImg_like.setSelected(false);
        String user = PropertyManager.getInstance().mUser.sBlogKey;
        if(mData.arrLikes.contains(user)){
            vImg_like.setSelected(true);
        }
        Picasso.with(getContext()).load(mData.mUserData.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vThPro);
        vTextArtist.setText(mData.mUserData.sArtist);
        vTextDate.setText(DateManager.getInstance().getPastTime(mData.sWriteDate));
        vTextLike.setText(mData.nLikeCount+"");
        vTextComment.setText(mData.nCommentCount+"");

        switch (data.nEmotion){
            case DefineContentType.EMO_NONE:{
                vIMGEmotion.setImageResource(R.drawable.icon_nofeeling);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_nofeeling, vIMGEmotion);
                break;
            }
            case DefineContentType.EMO_HAPPY:{
                vIMGEmotion.setImageResource(R.drawable.icon_happy);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_happy, vIMGEmotion);
                break;
            }
            case DefineContentType.EMO_LOVE:{
                vIMGEmotion.setImageResource(R.drawable.icon_love);
                // ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_love, vIMGEmotion);
                break;
            }
            case DefineContentType.EMO_SAD:{
                vIMGEmotion.setImageResource(R.drawable.icon_sad);
                // ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_sad, vIMGEmotion);
                break;
            }
            case DefineContentType.EMO_ANGRY:{
                vIMGEmotion.setImageResource(R.drawable.icon_angry);
                // ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_angry, vIMGEmotion);
                break;
            }
        }

        if(TextUtils.isEmpty(mData.sText) || mData.sText.equalsIgnoreCase("null")){
            // vText.setVisibility(GONE);
            vText.setText("");
        }else{

            vText.setText(mData.sText);
        }
        mAdapter.clear();
        for(String img :mData.arrIMGs){
            mAdapter.add(img);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_detail_image_thPro:
            case R.id.text_detail_image_artist:
            case R.id.text_detail_image_date:{
                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineNetwork.BLOG_KEY, mData.sBlogKey);
                if(DefineContentType.BLOG_TYPE_MYBLOG == mData.nBlogType) {
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_BLOG);
                }else{
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_SPACE);
                }
                //mIntent.putExtra(DefineContentType.KEY_ON_NEW_GET_DATA_URL, ); // 이동시 다시 받아올 Data URL
                startActivity(mIntent);
                getActivity().finish();
                break;
            }
            case R.id.comment:
            case R.id.image_single_comm:
            case R.id.text_detail_image_comm_count:{
                Intent mIntent = new Intent(getContext(), CommentActivity.class);
                mIntent.putExtra(DefineNetwork.CONTENT_KEY, mData.sContentKey);
                mIntent.putExtra(DefineNetwork.CONTENT_DATA, mData);
                startActivity(mIntent);

                break;
            }
            case R.id.like:
            case R.id.image_single_like:
            case R.id.text_detail_image_like_count:{
                if(vImg_like.isSelected()){
                    PUTLikeRequest request = new PUTLikeRequest();
                    request.setURL(String.format(DefineNetwork.LIKE, mData.sContentKey, "unlike"));

                    CommonController.getInstance().like(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            Toast.makeText(getContext(), "좋아요가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                            int nCount = Integer.parseInt(vTextLike.getText().toString());
                            vTextLike.setText(nCount-1+"");
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            Toast.makeText(getContext(), "잠시 후 다시 시도해 주세요. "+code, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    PUTLikeRequest request = new PUTLikeRequest();
                    request.setURL(String.format(DefineNetwork.LIKE, mData.sContentKey, "like"));

                    CommonController.getInstance().like(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            Toast.makeText(getContext(), "좋아요", Toast.LENGTH_SHORT).show();
                            int nCount = Integer.parseInt(vTextLike.getText().toString());
                            vTextLike.setText(nCount + 1 + "");
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            Toast.makeText(getContext(), "잠시 후 다시 시도해 주세요. "+code, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                vImg_like.setSelected(!vImg_like.isSelected());
                break;
            }
            case R.id.option:
            case R.id.text_detail_image_option:{
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
                        builder.setMessage("이 게시물을 정말 싫어요 하시겠습니까?");
                        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                        builder.setNeutralButton("싫어요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "해당 게시물을 신고 하였습니다.", Toast.LENGTH_SHORT).show();
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
                        DELETEContentRequest request = new DELETEContentRequest();
                        request.setURL(String.format(DefineNetwork.DELETE_CONTENT, mData.sContentKey));
                        CommonController.getInstance().deleteContent(request, new NetworkProcess.OnResultListener<String>() {
                            @Override
                            public void onSuccess(NetworkRequest<String> request, String result) {
                                Toast.makeText(getContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }

                            @Override
                            public void onFail(NetworkRequest<String> request, int code) {
                                Toast.makeText(getContext(), code + "- error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }
                }

            }
        });
        builder.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

}
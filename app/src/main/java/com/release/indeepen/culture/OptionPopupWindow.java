package com.release.indeepen.culture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.release.indeepen.R;

/**
 * Created by Lady on 2015. 11. 9..
 */
public class OptionPopupWindow extends PopupWindow{
    Context mContext;
    Button btn;


    public final static int UNLIKE = 0;
    public final static int SHARE = 1;
    public final static int EDIT = 2;
    public final static int DELETE = 3;


    public interface OptionClickListener{
        void onClickEvent(int mode);
    }

    OptionClickListener mListener;

    public void setOnOptionClick(OptionClickListener listener){
        mListener = listener;
    }

    public OptionPopupWindow(Context context) {
        super(context);
        mContext = context;
        final View view = LayoutInflater.from(context).inflate(R.layout.popup_option, null);


        btn = (Button)view.findViewById(R.id.btn_dislike);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                //builder.setIcon(android.R.drawable.ic_dialog_alert);
                //builder.setTitle("Alert Dialog");
                builder.setMessage("이 게시물을 정말 싫어요 하시겠습니까?");
                builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(mContext, "봐줌", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNeutralButton("싫어요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(mContext, "넌 신고", Toast.LENGTH_SHORT).show();
                        if(null != mListener){
                            mListener.onClickEvent(UNLIKE);
                        }
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        });

        btn = (Button)view.findViewById(R.id.btn_share);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "공유하기", Toast.LENGTH_SHORT).show();
                if(null != mListener){
                    mListener.onClickEvent(SHARE);
                }
            }
        });

        btn = (Button)view.findViewById(R.id.btn_modify);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "수정하기", Toast.LENGTH_SHORT).show();
                if(null != mListener){
                    mListener.onClickEvent(EDIT);
                }
            }
        });

        btn = (Button) view.findViewById(R.id.btn_delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                //builder.setIcon(android.R.drawable.ic_dialog_alert);
                //builder.setTitle("Alert Dialog");
                builder.setMessage("이 게시물을 정말 삭제 하시겠습니까?");
                builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(mContext, "봐줌", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(null != mListener){
                            mListener.onClickEvent(DELETE);
                        }
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        });

        setContentView(view);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

    }
    /* public void onOptionDialog() {
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
*/


}

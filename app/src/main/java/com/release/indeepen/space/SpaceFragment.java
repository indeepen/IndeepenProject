package com.release.indeepen.space;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.blog.BlogInCultureActivity;
import com.release.indeepen.blog.BlogIntroView;
import com.release.indeepen.blog.simpleList.SimpleSingleUserListActivity;
import com.release.indeepen.blog.simpleList.SimpleTabUserListActivity;
import com.release.indeepen.blog.tripleGrid.HeaderGridView;
import com.release.indeepen.blog.tripleGrid.TripleGridAdapter;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.detail.ExpandImageActivity;
import com.release.indeepen.create.selectMedia.MediaSingleChoiceActivity;
import com.release.indeepen.user.UserInfoActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpaceFragment extends Fragment implements View.OnClickListener {


    public SpaceFragment() {
        // Required empty public constructor
    }

    HeaderGridView vTripleGrid;
    TripleGridAdapter mAdapter;
    ImageView vIMGProBack, vthPro;
    Button vBtnArt, vBtnLocation, vBtnMyCulture, vBtnCollabo;
    BlogIntroView vIntroView;
    boolean isClick = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_space, container, false);
        vTripleGrid = (HeaderGridView) view.findViewById(R.id.grid_space_item);
        mAdapter = new TripleGridAdapter(getContext());
        vIntroView = new BlogIntroView(getContext());
        vTripleGrid.addHeaderView(vIntroView);
        vTripleGrid.setAdapter(mAdapter);

        vIMGProBack = (ImageView) vIntroView.findViewById(R.id.img_blog_background);
        vthPro = (ImageView) vIntroView.findViewById(R.id.img_blog_thProfile);
        vBtnArt = (Button) vIntroView.findViewById(R.id.btn_tab_art);
        vBtnLocation = (Button) vIntroView.findViewById(R.id.btn_tab_favorite);
        vBtnMyCulture = (Button) vIntroView.findViewById(R.id.btn_tab_culture);
        vBtnCollabo = (Button) vIntroView.findViewById(R.id.btn_tab_collabo);

        vBtnLocation.setText(R.string.space_location);

        vBtnArt.setOnClickListener(this);
        vBtnLocation.setOnClickListener(this);
        vBtnMyCulture.setOnClickListener(this);
        vBtnCollabo.setOnClickListener(this);

        Button btn = (Button) vIntroView.findViewById(R.id.btn_fanList);
        btn.setOnClickListener(this);


        btn = (Button) vIntroView.findViewById(R.id.btn_imissyou);
        btn.setOnClickListener(this);

        btn = (Button) vIntroView.findViewById(R.id.btn_space_revome);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(this);

        init();

        return view;
    }

    private void init() {
        test1();
        vthPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileDialog();
            }
        });

        vIMGProBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackgroundDialog();
            }
        });

        vTripleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_FAN_LIST);
                //mIntent.putExtra(DefineContentType.KEY_ON_NEW_GET_DATA_URL, ); // 서버에 Data 받아올 URL
                startActivity(mIntent);

            }
        });
    }

    public void onBackgroundDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        // builder.setTitle("List Dialog");
        builder.setItems(new String[]{"블로그 배경 등록"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent mIntent = new Intent(getContext(), MediaSingleChoiceActivity.class);
                mIntent.putExtra(DefineContentType.SELECT_IMAGE, DefineContentType.ACTIVITY_TYPE_PROFILE_BACKGROUND); // 사진 선택 구별을 위한 타입
                startActivity(mIntent);

            }
        });
        builder.create().show();
    }

    public void onProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        // builder.setTitle("List Dialog");
        builder.setItems(new String[]{"공간정보 수정", "프로필 사진 보기", "프로필 사진 등록"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent;
                switch (which) {
                    case 0: {
                        mIntent = new Intent(getContext(), UserInfoActivity.class);
                       /* if(null != CallbackListener.mActivityListener) {
                            CallbackListener.mActivityListener.onGoActivity(mIntent, DefineContentType.ACTIVITY_TYPE_FIXD_INFO);
                        }*/
                        startActivity(mIntent);
                        break;
                    }
                    case 1: {
                        mIntent = new Intent(getContext(), ExpandImageActivity.class);
                       /* if(null != CallbackListener.mActivityListener) {
                            CallbackListener.mActivityListener.onGoActivity(mIntent, DefineContentType.ACTIVITY_TYPE_EXPANED_IMG);
                        }*/
                        startActivity(mIntent);
                        break;
                    }
                    case 2: {
                        mIntent = new Intent(getContext(), MediaSingleChoiceActivity.class);
                        mIntent.putExtra(DefineContentType.SELECT_IMAGE, DefineContentType.ACTIVITY_TYPE_PROFILE_IMG);
                       /* if(null != CallbackListener.mActivityListener) {
                            CallbackListener.mActivityListener.onGoActivity(mIntent, DefineContentType.ACTIVITY_TYPE_PROFILE_IMG);
                        }*/
                        startActivity(mIntent);
                        break;
                    }
                }

            }
        });
        builder.create().show();
    }

    public void onConfirmDialog(View view) {
       /* PopupIMissU popup = new PopupIMissU(getContext());
        popup.setOutsideTouchable(true);
        popup.showAtLocation(getView(), Gravity.CENTER, 0, 0);*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("응원 하시겠습니까?");
        // builder.setMessage("응원 하시겠습니까?");
        builder.setPositiveButton("아니", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "안함", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNeutralButton("응원한다", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "사랑한다", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    public interface OnFragmentActionListener {
        void onFragmentActionListener(Fragment fragment, Intent intent, int type);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_tab_art:
                test1();
                break;
            case R.id.btn_tab_favorite:

                test2();

                break;
            case R.id.btn_tab_culture: {
                Intent mIntent = new Intent(getContext(), BlogInCultureActivity.class);
                startActivity(mIntent);
                //CallbackListener.mActivityListener.onGoActivity(mIntent, DefineContentType.ACTIVITY_TYPE_FIXD_INFO);
                break;
            }
            case R.id.btn_tab_collabo:
                Toast.makeText(getContext(), "서비스 준비중입니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_fanList: {
                Intent mIntent = new Intent(getContext(), SimpleTabUserListActivity.class);
                startActivity(mIntent);
                break;
            }
            case R.id.btn_imissyou: {
                if (isClick) {
                    Intent mIntent = new Intent(getContext(), SimpleSingleUserListActivity.class);
                    startActivity(mIntent);
                } else {
                    onConfirmDialog(v);
                    isClick = true;
                }
                break;
            }
            case R.id.btn_space_revome:{
                onConfirmSpaceRemove();
            }
            default:
                return;
        }
    }

    void test1() {
        mAdapter.removeAll();
        for (int idx = 0; idx < 20; idx++) {
            ContentData data = new ContentData();
            //data.thIMG = DefineTest.ARR_IMG[((int) (Math.random() * 10) % 8)];
            mAdapter.add(data);
        }
    }

    void test2() {
        mAdapter.removeAll();
        vTripleGrid.setNumColumns(1);

    }

    private void onConfirmSpaceRemove(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        //builder.setTitle("Alert Dialog");
        builder.setMessage("이 공간을 정말로 삭제 하시겠습니까?");
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "안지움", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "서버야 지우래", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }



}

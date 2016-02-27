package com.release.indeepen.comment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netComment.CommentController;
import com.release.indeepen.management.networkManager.netComment.CommentListRequest;
import com.release.indeepen.management.networkManager.netComment.POSTCommentRequest;
import com.release.indeepen.management.networkManager.netComment.data.CommentResult;


public class CommentActivity extends AppCompatActivity {
    ListView listView;
    CommentAdapter mCommentAdapter;
    EditText input_text;
    String sContentKey;
    boolean isLastItem;
    boolean isStart;
    ContentData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setTitle("댓글");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        isStart = true;

        listView = (ListView) findViewById(R.id.list_comment);


        listView = (ListView) findViewById(R.id.list_comment);
        mCommentAdapter = new CommentAdapter();
        listView.setAdapter(mCommentAdapter);
        Button btn = (Button) findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input_text.getText().toString())) {
                    POSTCommentRequest request = new POSTCommentRequest();
                    request.setURL(String.format(DefineNetwork.ADD_COMMENT, sContentKey));
                    request.setData(input_text.getText().toString(), PropertyManager.getInstance().mUser.sActiveBlogKey);
                    CommentController.getInstance().postComment(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            //댓글 수정
                            /*Comments comments = new Comments();
                            comments.sComm = input_text.getText().toString();
                            Writer writer = new Writer();
                            writer.sArtist = PropertyManager.getInstance().mUser.sArtist;
                            comments.mWriter =*/
                             mData.nCommentCount+=1;

                            input_text.setText("");
                            Toast.makeText(CommentActivity.this, "등록 되었습니다.", Toast.LENGTH_SHORT).show();
                            isStart = true;
                            getMoreItem();
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            Toast.makeText(CommentActivity.this, "실패." + code, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String text = (String) listView.getItemAtPosition(position);
                //Toast.makeText(CommentActivity.this, text + "블로그로 이동하기", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    getMoreItem();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                isLastItem = firstVisibleItem == 0 && visibleItemCount > 8 && totalItemCount > 19;
            }
        });

        input_text = (EditText)findViewById(R.id.text_input);


        initData();
        return;

    }

    private void getMoreItem(){

        if(!TextUtils.isEmpty(sContentKey)){
            CommentListRequest request = new CommentListRequest();
            if(isStart){
                request.setURL(String.format(DefineNetwork.COMMENT_LIST, sContentKey));

            }else {
                request.setURL(String.format(DefineNetwork.COMMENT_LIST_MORE, sContentKey));
            }

            CommentController.getInstance().getCommentList(request, new NetworkProcess.OnResultListener<CommentResult>() {
                @Override
                public void onSuccess(NetworkRequest<CommentResult> request, CommentResult result) {


                    if(isStart){
                        if(0 < mCommentAdapter.getCount()){
                            mCommentAdapter.clear();

                        }
                        mCommentAdapter.setList(result.arrComments);
                        listView.smoothScrollToPosition(20);
                        isStart = false;
                    }
                }

                @Override
                public void onFail(NetworkRequest<CommentResult> request, int code) {

                }
            });
        }
    }

    private void initData() {
        sContentKey = getIntent().getStringExtra(DefineNetwork.CONTENT_KEY);
        mData = (ContentData) getIntent().getSerializableExtra(DefineNetwork.CONTENT_DATA);
        getMoreItem();


       /* for (int item = 0; item < 20; item++) {
            CommentItemData mData = new CommentItemData();
            mData.userimage = getResources().getDrawable(DefineTest.USER_IMG[item % 5]);
            mData.username = "Name: " + item + 1;
       //     String text = input_text.getText().toString();
            mData.comment = "text";

            mCommentAdapter.add(mData);
        }*/
    }

    public CommentAdapter getCommentAdapter() {
        return mCommentAdapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            switch (item.getItemId()) {
                case android.R.id.home:
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

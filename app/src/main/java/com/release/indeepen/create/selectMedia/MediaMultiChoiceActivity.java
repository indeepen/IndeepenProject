package com.release.indeepen.create.selectMedia;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.culture.CultureItemData;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaMultiChoiceActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    static MediaMultiChoiceActivity mediaMultiChoiceActivity;
    String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
    String sortOrder = MediaStore.Images.Media.DATE_ADDED + " ASC";
    GridView vGridView;
    SimpleCursorAdapter mAdapter;
    //int nCase;
    ContentData mData;
    int dataColumnIndex = -1;
    List<String> arrSaveImgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_single_choice);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_select));
        getSupportActionBar().setTitle("이미지 선택");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mediaMultiChoiceActivity = this;
        if(DefineContentType.SINGLE_ART_TYPE_CULTURE == getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1)){
            mData = new CultureItemData();
        }else {
            mData = new ContentImageData();
        }
        arrSaveImgs = new ArrayList<String>();

        vGridView = (GridView) findViewById(R.id.grid_single_choice);
        //nCase = getIntent().getIntExtra(DefineContentType.SELECT_IMAGE, -1);
        // Button btn = (Button) findViewById(R.id.btn_select);

        getSupportLoaderManager().initLoader(0, null, this);

        String[] from = {MediaStore.Images.Media.DATA};
        int[] to = {R.id.image_icon};

        mAdapter = new SimpleCursorAdapter(this, R.layout.view_checked_item, null, from, to, 0);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == dataColumnIndex) {
                    ImageView iv = (ImageView) view;

                    String path = cursor.getString(columnIndex);
                   /* Uri uri = null;
                    uri = Uri.fromFile(new File(path));*/
                    Picasso.with(MediaMultiChoiceActivity.this)
                            .load(new File(path))// optional
                            .fit().centerCrop()                       // optional
                            .into(iv);
                    //ImageLoader.getInstance().displayImage(uri.toString(), iv);
                    /*try {
                       //uri = Uri.fromFile(new File(URLEncoder.encode(path, "UTF-8").replaceAll("%2F", "/")));

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }*/


                    return true;
                }
                return false;
            }
        });

        vGridView.setAdapter(mAdapter);

        vGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        vGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 String selected = getSingleImage(position);
                                                 if (arrSaveImgs.size() < 6) {
                                                     if (((CheckItemView) view).isChecked()) {

                                                         if (!TextUtils.isEmpty(selected)) {
                                                             arrSaveImgs.add(selected);
                                                         }

                                                     } else {

                                                         for (int idx = 0; idx < arrSaveImgs.size(); idx++) {
                                                             if (arrSaveImgs.get(idx).equalsIgnoreCase(selected)) {
                                                                 arrSaveImgs.remove(idx);
                                                             }
                                                         }
                                                     }
                                                 }

                                                 if (6 == arrSaveImgs.size()) {
                                                     int SSS = arrSaveImgs.size();
                                                     vGridView.setItemChecked(position, false);
                                                     arrSaveImgs.remove(SSS - 1);
                                                     Toast.makeText(MediaMultiChoiceActivity.this, "한번에 최대 5장까지 선택 가능합니다", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }

        );

    }

    private String getSingleImage(int position) {
        String sPath = "";
        Cursor c = (Cursor) vGridView.getItemAtPosition(position);
        if (null != c) {
            sPath = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        return sPath;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        return new CursorLoader(this, uri, projection, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataColumnIndex = data.getColumnIndex(MediaStore.Images.Media.DATA);
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_next) {

            if (0 == arrSaveImgs.size()) {
                Toast.makeText(this, "사진을 선택하세요", Toast.LENGTH_SHORT).show();
            } else {

                if(DefineContentType.SINGLE_ART_TYPE_CULTURE == getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1)){
                    ((CultureItemData)mData).arrIMGs = arrSaveImgs;

                    Intent mIntent = new Intent(MediaMultiChoiceActivity.this, CreateInputCultureActivity.class);
                    mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData);
                    mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1));

                    startActivity(mIntent);

                }else {
                    Intent mIntent = new Intent(MediaMultiChoiceActivity.this, CreateImageContentActivity.class);
                    ((ContentImageData)mData).arrIMGs = arrSaveImgs;
                    mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData);

                    mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1));

                    startActivity(mIntent);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

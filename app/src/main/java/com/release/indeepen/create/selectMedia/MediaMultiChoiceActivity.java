package com.release.indeepen.create.selectMedia;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;
import com.release.indeepen.content.art.ContentImageData;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MediaMultiChoiceActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    static MediaMultiChoiceActivity mediaMultiChoiceActivity;
    String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
    String sortOrder = MediaStore.Images.Media.DATE_ADDED + " ASC";
    GridView vGridView;
    SimpleCursorAdapter mAdapter;
    //int nCase;
    ContentImageData mData;
    int dataColumnIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_single_choice);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_select));
        getSupportActionBar().setTitle("이미지 선택");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mediaMultiChoiceActivity = this;

        mData = new ContentImageData();

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
                            .fit()                       // optional
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
                     if (mData.arrIMGs.size() < 6) {
                         if (((CheckItemView) view).isChecked()) {

                             if(!TextUtils.isEmpty(selected)) {
                                 mData.arrIMGs.add(selected);
                             }

                         } else {

                             for (int idx = 0; idx < mData.arrIMGs.size(); idx++) {
                                 if (mData.arrIMGs.get(idx).equalsIgnoreCase(selected)) {
                                     mData.arrIMGs.remove(idx);
                                 }
                             }
                         }
                     }

                     if (6 == mData.arrIMGs.size()){
                         int SSS = mData.arrIMGs.size();
                         vGridView.setItemChecked(position , false);
                         mData.arrIMGs.remove(SSS - 1);
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

            Intent mIntent = new Intent(MediaMultiChoiceActivity.this, CreateImageContentActivity.class);

            mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData);

            mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1));
            /*switch (nCase) {
                case DefineContentType.SINGLE_ART_TYPE_PAINT: {
                    mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_PAINT);
                    break;
                }
                case DefineContentType.SINGLE_ART_TYPE_PICTURE: {
                    mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_PICTURE);
                    break;
                }
                case DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE: {
                    mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE);
                    break;
                }

            }*/
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}

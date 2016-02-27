package com.release.indeepen.create.selectMedia;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.R;
import com.release.indeepen.content.art.ContentMusicData;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogController;
import com.release.indeepen.management.networkManager.netMyBlog.PUTBackgoundIMGRequest;
import com.release.indeepen.management.networkManager.netMyBlog.PUTProfileIMGRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.ChangeIMGData;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MediaSingleChoiceActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    static MediaSingleChoiceActivity mediaSingleChoiceActivity;

    String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
    String sortOrder = MediaStore.Images.Media.DATE_ADDED + " ASC";
    GridView vGridView;
    SimpleCursorAdapter mAdapter;
    int nCase;
    final static int REQUEST_IMAGE_CROP = 10;

    int dataColumnIndex = -1;
    File cropImageFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_single_choice);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_select));
        getSupportActionBar().setTitle("배경사진 선택");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mediaSingleChoiceActivity = this;

        vGridView = (GridView) findViewById(R.id.grid_single_choice);
        nCase = getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1);
        Button btn = (Button) findViewById(R.id.btn_select);

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
                    //Uri uri = Uri.fromFile(new File(path));
                    //ImageLoader.getInstance().displayImage(uri.toString(), iv);

                    Picasso.with(MediaSingleChoiceActivity.this)
                            .load(new File(path))// optional
                            .fit().centerCrop()                        // optional
                            .into(iv);
                    return true;
                }
                return false;
            }
        });

        vGridView.setAdapter(mAdapter);
        vGridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);

    }

    private String getSingleImage() {
        String sPath ="";
        Cursor c = (Cursor) vGridView.getItemAtPosition(vGridView.getCheckedItemPosition());
        if(null != c){
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
    private String SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {

        File fileCacheItem = new File(Environment.getExternalStorageDirectory(), strFilePath);
        OutputStream out = null;

        try
        {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return fileCacheItem.getAbsolutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK) {
            Bitmap bm = BitmapFactory.decodeFile(cropImageFile.getAbsolutePath());
            Bitmap bm2 = cropCircle(bm);

            String sPath = SaveBitmapToFileCache(bm2, "temp_" + (int)System.currentTimeMillis()/100000);


            final ChangeIMGData mChangeData = new ChangeIMGData();
            mChangeData.sChangePath =  sPath;
            mChangeData.sChangeBlogKey = getIntent().getStringExtra(DefineNetwork.BLOG_KEY);

            PUTProfileIMGRequest request = new PUTProfileIMGRequest();
            request.setURL(String.format(DefineNetwork.PROFILE_IMG, mChangeData.sChangeBlogKey));
            request.setData(mChangeData);

            MyBlogController.getInstance().putFilePOST(request, new NetworkProcess.OnResultListener<String>() {
                @Override
                public void onSuccess(NetworkRequest<String> request, String result) {
                    if (result.equalsIgnoreCase(DefineNetwork.RESULT_SUCCESS)) {
                        finish();
                    } else {
                        Toast.makeText(MediaSingleChoiceActivity.this, DefineNetwork.RESULT_FAIL, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFail(NetworkRequest<String> request, int code) {
                    Toast.makeText(MediaSingleChoiceActivity.this, DefineNetwork.RESULT_FAIL, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public Bitmap cropCircle(Bitmap bitmap) {
       /* Bitmap output = Bitmap	.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        int size = (bitmap.getWidth() / 2);
        canvas.drawCircle(size, size, size, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;*/
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_next) {
            switch (nCase) {
                case DefineContentType.ACTIVITY_TYPE_PROFILE_IMG: {

                    if(!TextUtils.isEmpty(getSingleImage())) {

                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(Uri.fromFile(new File(getSingleImage())), "image");
                        intent.putExtra("outputX", 150);
                        intent.putExtra("outputY", 150);
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("scale", true);
                        intent.putExtra("circleCrop", new String(""));
                        intent.putExtra("return-data", false);
                        cropImageFile = new File(Environment.getExternalStorageDirectory(),"temp_" + (int)System.currentTimeMillis()/1000);
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropImageFile));
                        startActivityForResult(intent, REQUEST_IMAGE_CROP);

                       /* final ChangeIMGData mChangeData = new ChangeIMGData();
                        mChangeData.sChangePath = getSingleImage();
                        mChangeData.sChangeBlogKey = getIntent().getStringExtra(DefineNetwork.BLOG_KEY);

                        PUTProfileIMGRequest request = new PUTProfileIMGRequest();
                        request.setURL(String.format(DefineNetwork.PROFILE_IMG, mChangeData.sChangeBlogKey));
                        request.setData(mChangeData);

                        MyBlogController.getInstance().putFilePOST(request, new NetworkProcess.OnResultListener<String>() {
                            @Override
                            public void onSuccess(NetworkRequest<String> request, String result) {
                                if (result.equalsIgnoreCase(DefineNetwork.RESULT_SUCCESS)) {
                                    finish();
                                } else {
                                    Toast.makeText(MediaSingleChoiceActivity.this, DefineNetwork.RESULT_FAIL, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFail(NetworkRequest<String> request, int code) {
                                Toast.makeText(MediaSingleChoiceActivity.this, code+"", Toast.LENGTH_SHORT).show();

                            }
                        });*/
                    }else{
                        Toast.makeText(MediaSingleChoiceActivity.this, "사진을 선택하세요", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case DefineContentType.ACTIVITY_TYPE_PROFILE_BACKGROUND: {

                    if (!TextUtils.isEmpty(getSingleImage())) {

                        final ChangeIMGData mChangeData = new ChangeIMGData();

                        mChangeData.sChangePath = getSingleImage();
                        mChangeData.sChangeBlogKey = getIntent().getStringExtra(DefineNetwork.BLOG_KEY);
                        PUTBackgoundIMGRequest request = new PUTBackgoundIMGRequest();
                        request.setURL(String.format(DefineNetwork.BACKGROUD_IMG, mChangeData.sChangeBlogKey));
                        request.setData(mChangeData);

                        MyBlogController.getInstance().putFilePOST(request, new NetworkProcess.OnResultListener<String>() {
                            @Override
                            public void onSuccess(NetworkRequest<String> request, String result) {
                                if (result.equalsIgnoreCase(DefineNetwork.RESULT_SUCCESS)) {
                                    finish();
                                } else {
                                    Toast.makeText(MediaSingleChoiceActivity.this, DefineNetwork.RESULT_FAIL, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFail(NetworkRequest<String> request, int code) {
                                finish();
                            }
                        });
                    }else{
                        Toast.makeText(MediaSingleChoiceActivity.this, "사진을 선택하세요", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case DefineContentType.SINGLE_ART_TYPE_MUSIC: {
                    ContentMusicData mData = (ContentMusicData) getIntent().getSerializableExtra(DefineContentType.BUNDLE_DATA_REQUEST);
                    String sIMGPath = getSingleImage();
                    if (null != mData && !TextUtils.isEmpty(sIMGPath)) {
                        mData.sMusicBackIMG = getSingleImage();
                        Intent mIntent = new Intent(MediaSingleChoiceActivity.this, CreateMusicContentActivity.class);
                        mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData);
                        mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1));
                        startActivity(mIntent);
                        //Toast.makeText(MediaSingleChoiceActivity.this, sIMGPath +"|||\n|||"+ mData.sMusicPath, Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(MediaSingleChoiceActivity.this, "사진을 선택하세요", Toast.LENGTH_SHORT).show();
                    }


                    break;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

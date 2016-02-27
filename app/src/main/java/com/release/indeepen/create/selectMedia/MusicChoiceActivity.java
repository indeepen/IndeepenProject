package com.release.indeepen.create.selectMedia;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;
import com.release.indeepen.content.art.ContentMusicData;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MusicChoiceActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>{
    static MusicChoiceActivity musicChoiceActivity;
    ListView vList;
    SimpleCursorAdapter mAdapter;
    int titleColumnIndex, albumColumnIndex, artistColumnIndex;
    ContentMusicData mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_choice);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_select));
        getSupportActionBar().setTitle("음원 선택");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        musicChoiceActivity = this;

        Activity aaaa = getParent();

        mData = new ContentMusicData();
        mData.nArtType = DefineContentType.SINGLE_ART_TYPE_MUSIC;
        vList = (ListView) findViewById(R.id.list_create_music);


        String[] from = { MediaStore.Audio.Media.TITLE,  MediaStore.Audio.Media.ARTIST};
        int[] to = { R.id.text_music_select_title, R.id.text_music_select_artist};

        mAdapter = new SimpleCursorAdapter(this, R.layout.view_select_music_real_item, null, from , to, 0);

        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                /*if(columnIndex == albumColumnIndex){
                    ImageView iv = (ImageView) view;
                    String albumID = cursor.getString(columnIndex);
                    Bitmap bitmap = getArtworkQuick(MusicChoiceActivity.this, Integer.parseInt(albumID), 50, 50);

                    if(null != bitmap){
                        iv.setVisibility(View.VISIBLE);
                        iv.setImageBitmap(bitmap);
                    }

                }*/

                if (columnIndex == titleColumnIndex) {
                    //정의  Uri uri = Uri.fromFile(new File(path))
                    TextView tv = (TextView) view;
                    String name = cursor.getString(columnIndex);
                    tv.setText(name);
                }
                if (columnIndex == artistColumnIndex) {
                    //정의  Uri uri = Uri.fromFile(new File(path))
                    TextView tv = (TextView) view;
                    String name = cursor.getString(columnIndex);
                    tv.setText(name);
                }

                return false;
            }
        });

        vList.setAdapter(mAdapter);
        vList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vList.setItemChecked(position, true);
                view.setBackground(getResources().getDrawable(R.drawable.mymusic_selected));

            }
    }

    );

    getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[] {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        titleColumnIndex = data.getColumnIndex(MediaStore.Audio.Media.TITLE);
        artistColumnIndex = data.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        albumColumnIndex = data.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
    @Override
    public void onClick(View v) {

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
        }else if (id == R.id.action_next) {
            Cursor cursor = (Cursor)vList.getItemAtPosition(vList.getCheckedItemPosition());
            // name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            if(null != cursor) {
                mData.sMusicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                Intent mIntent = new Intent(MusicChoiceActivity.this, MediaSingleChoiceActivity.class);

                mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData);

                mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, getIntent().getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1));

                startActivity(mIntent);
            }else{
                Toast.makeText(MusicChoiceActivity.this, "음악을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private static Bitmap getArtworkQuick(Context context, int album_id, int w, int h) {
        final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
        final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(artworkUri, album_id);
        ContentResolver cr = context.getContentResolver();
        InputStream in = null;
        try {
            in = cr.openInputStream(uri);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap artwork = BitmapFactory.decodeStream(in);
        return artwork;

    }
}

package com.release.indeepen;

import android.*;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    TMapView mapView;
    LocationManager mLM;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    EditText search;
    ListView vList;
    Button btnSearch;
    ArrayAdapter<POIItem> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        // TMapView tmapview = new TMapView(this);
        mapView = (TMapView) findViewById(R.id.Tmap);
        new RegisterTask().execute();

        mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mAdapter = new ArrayAdapter<POIItem>(this, android.R.layout.simple_list_item_1);
        search = (EditText) findViewById(R.id.map_search);
        vList = (ListView) findViewById(R.id.listView);
        vList.setAdapter(mAdapter);

        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = search.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    TMapData data = new TMapData();
                    data.findAllPOI(keyword, new TMapData.FindAllPOIListenerCallback() {
                        @Override
                        public void onFindAllPOI(final ArrayList<TMapPOIItem> arrayList) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // clearAll();
                                    for (TMapPOIItem item : arrayList) {
                                        POIItem poi = new POIItem();
                                        poi.poi = item;
                                        mAdapter.add(poi);
                                    }
//                                    mapView.addTMapPOIItem(arrayList);
                                /*    addMarkerPOI(arrayList);
                                    if (arrayList.size() > 0) {
                                        TMapPOIItem first = arrayList.get(0);
                                        moveMap(first.getPOIPoint().getLatitude(), first.getPOIPoint().getLongitude());
                                    }*/
                                }
                            });
                        }
                    });
                }
            }
        });

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                POIItem item = (POIItem) vList.getItemAtPosition(position);
                moveMap(item.poi.getPOIPoint().getLatitude(), item.poi.getPOIPoint().getLongitude());
            }
        });
    }


    boolean isInitialized = false;


    class RegisterTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            mapView.setSKPMapApiKey("9e670e63-cefd-39ea-b3a8-d3aa55672ecd");
            mapView.setLanguage(TMapView.LANGUAGE_KOREAN);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            isInitialized = true;
            setupMap();
        }
    }


    Location cacheLocation;
    LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (isInitialized) {
                moveMap(location.getLatitude(), location.getLongitude());
                setMyLocation(location.getLatitude(), location.getLongitude());
            } else {
                cacheLocation = location;
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void moveMap(double lat, double lng) {
        mapView.setCenterPoint(lng, lat);
        mapView.setZoomLevel(15);
    }

    private void setMyLocation(double lat, double lng) {
        mapView.setLocationPoint(lng, lat);
        Bitmap bm = ((BitmapDrawable) getResources().getDrawable(R.drawable.mylocation_icon)).getBitmap();
        mapView.setIcon(bm);
        mapView.setIconVisibility(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* Intent intent = new Intent();
        PendingIntent pi = PendingIntent.getService(MapActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      */
        gps_enabled = mLM.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = mLM.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gps_enabled && !network_enabled) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "nothing is enabled", duration);
            toast.show();

        }
        if (Build.VERSION.SDK_INT > 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (gps_enabled) {
            mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mListener);
        }
        if (network_enabled) {
            mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mListener);
        }
       /* timer=new Timer();
        timer.schedule(new GetLastLocation(), 20000);*/


        //  mLM.requestSingleUpdate(LocationManager.GPS_PROVIDER, mListener, null);

        // mLM.requestSingleUpdate(LocationManager.KEY_PROXIMITY_ENTERING, pendi;
        // ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLM.removeUpdates(mListener);
    }

    private void setupMap() {
        if (cacheLocation != null) {
            moveMap(cacheLocation.getLatitude(), cacheLocation.getLongitude());
            setMyLocation(cacheLocation.getLatitude(), cacheLocation.getLongitude());
            cacheLocation = null;
        }
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

package com.release.indeepen.menu;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.release.indeepen.R;

public class MenuActivity extends AppCompatActivity {

    ListView vList;
    MenuAdapter mAdapter;
    MenuListFragment mListF;
    FragmentManager mFM;
    boolean isFirst = false;
    RelativeLayout add_art_space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        mListF = new MenuListFragment();
        add_art_space = (RelativeLayout)findViewById(R.id.add_art_space);


       /* if (isFirst) {
            init();
            isFirst = false;
        }
        */
        add_art_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "예술 공간 추", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        mFM.beginTransaction().add(R.id.art_space_container, mListF).commit();
    }


    public int getContainer() {
        return R.id.art_space_container;
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

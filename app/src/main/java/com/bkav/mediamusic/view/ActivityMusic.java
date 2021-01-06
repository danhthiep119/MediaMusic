package com.bkav.mediamusic.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bkav.mediamusic.IGetPlayMusic;
import com.bkav.mediamusic.R;
import com.bkav.mediamusic.adapter.ListMusicAdapter;
import com.bkav.mediamusic.model.Music;
import com.bkav.mediamusic.view.fragment.AllsongsFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ActivityMusic extends AppCompatActivity implements IGetPlayMusic {
    private final int REQUEST_CODE = 1;
    private LinearLayout mLayoutBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        getSupportFragmentManager().beginTransaction().add(R.id.container, new AllsongsFragment()).commit();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        mLayoutBottomSheet = findViewById(R.id.bottom_sheet_layout);
        mBottomSheetBehavior = BottomSheetBehavior.from(mLayoutBottomSheet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getData(Music music) {
        System.out.println("has been click at position");
        if (!music.getName().equals("")) {
            if (mBottomSheetBehavior.getState() != mBottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(mBottomSheetBehavior.STATE_EXPANDED);
            } else {
                mBottomSheetBehavior.setState(mBottomSheetBehavior.STATE_COLLAPSED);
            }
        }
        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

}
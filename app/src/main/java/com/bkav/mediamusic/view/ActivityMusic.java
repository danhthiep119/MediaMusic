package com.bkav.mediamusic.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bkav.mediamusic.IChangeFragment;
import com.bkav.mediamusic.IGetPlayMusic;
import com.bkav.mediamusic.R;
import com.bkav.mediamusic.adapter.ListMusicAdapter;
import com.bkav.mediamusic.model.Music;
import com.bkav.mediamusic.view.fragment.AllsongsFragment;
import com.bkav.mediamusic.view.fragment.MediaPlaybackFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ActivityMusic extends AppCompatActivity implements ListMusicAdapter.IGetData {
    private final int REQUEST_CODE = 1;
    private TextView txtNameBottom,txtAuthorBottom;
    private CardView cvInfomation;
    private ImageButton btnPlay;
    private boolean isPlay = false;
    private Music mMusic;
    private MediaPlaybackFragment mediaPlaybackFragment = new MediaPlaybackFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        getSupportFragmentManager().beginTransaction().add(R.id.container, new AllsongsFragment()).commit();
        addControls();
        addEvents();

    }

    private void addEvents() {

    }


    private void addControls() {
        txtNameBottom = findViewById(R.id.txtNameBottom);
        txtAuthorBottom = findViewById(R.id.txtAuthorBottom);
        btnPlay = findViewById(R.id.btnPlayBottom);
        cvInfomation = findViewById(R.id.cvBottom);
        txtNameBottom.setText("");
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
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getData(Music mMusic) {
        txtNameBottom.setText(mMusic.getName());
//        if (mMusic.getAuthor().equals("")){
//            txtAuthorBottom.setText("");
//        } else txtAuthorBottom.setText(mMusic.getAuthor());
        cvInfomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlaybackFragment.getInformation(mMusic.getName());
                getSupportFragmentManager().beginTransaction().add(R.id.container, mediaPlaybackFragment).commit();
            }
        });
    }
}

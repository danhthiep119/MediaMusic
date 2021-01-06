package com.bkav.mediamusic.view.fragment;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bkav.mediamusic.R;
import com.bkav.mediamusic.adapter.ListMusicAdapter;
import com.bkav.mediamusic.model.Music;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllsongsFragment extends Fragment {
    private RecyclerView lvListMusic;
    private List<Music> mMusicList = new ArrayList<>();
    private List<File> mFile = new ArrayList<>();
    private ListMusicAdapter listMusicAdapter;
    private final String TAG = "AllSongsFragment";
    
    public AllsongsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.allsongs_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addControls(view);

        super.onViewCreated(view, savedInstanceState);
    }

    private void addControls(View view) {
        lvListMusic = view.findViewById(R.id.lvListMusic);
        mMusicList.clear();
        mMusicList.addAll(getListMusic());
//        Collections.sort(mMusicList, new Comparator<Music>() {
//            @Override
//            public int compare(Music music, Music t1) {
//                return music.getName().compareTo(t1.getName());
//            }
//        });
        LinearLayoutManager mllm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lvListMusic.setLayoutManager(mllm);
        lvListMusic.setHasFixedSize(true);
        listMusicAdapter = new ListMusicAdapter(getContext(), mMusicList);
        lvListMusic.setAdapter(listMusicAdapter);
    }

    private List<Music> getListMusic() {
        List<Music> mTemp = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            getFileMusic(file);
        }
        MediaPlayer mMediaPlayer = new MediaPlayer();
        try {
            for (int i = 0; i < mFile.size(); i++) {
                try {
                    mMediaPlayer.setDataSource(mFile.get(i).getAbsolutePath());
                    mTemp.add(new Music(mFile.get(i).getName(), "", mMediaPlayer.getDuration(),mFile.get(i).getAbsolutePath()));
                } catch (IOException e) {
                    Log.e(TAG, "" + e);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
        return mTemp;
    }

    //Lấy file .mp3 tr máy
    private void getFileMusic(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                getFileMusic(f);
            } else {
                if (f.getName().endsWith(".mp3")) {
                    mFile.add(f);
                }
            }
        }
    }
}

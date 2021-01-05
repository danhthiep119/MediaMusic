package com.bkav.mediamusic.view.fragment;

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
import java.util.List;

public class AllsongsFragment extends Fragment {
    private RecyclerView lvListMusic;
    private List<Music> mMusicList = new ArrayList<>();
    private List<File> mFile = new ArrayList<>();
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private ListMusicAdapter listMusicAdapter;
    private final String TAG = "AllSongsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.allsongs_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lvListMusic = view.findViewById(R.id.lvListMusic);
        mMusicList.addAll(getListMusic());
        LinearLayoutManager mllm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lvListMusic.setLayoutManager(mllm);
        lvListMusic.setHasFixedSize(true);
        listMusicAdapter = new ListMusicAdapter(getContext(),mMusicList);
        lvListMusic.setAdapter(listMusicAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    private List<Music> getListMusic(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            getFileMusic(file);
        }
        try {
            for (int i = 0; i < mFile.size(); i++) {
                try {
                    mMediaPlayer.setDataSource(mFile.get(i).getAbsolutePath());
                    mMusicList.add(new Music(mFile.get(i).getName().split(".mp3").toString(), "", mMediaPlayer.getDuration()));
                } catch (IOException e) {
                    Log.e(TAG, "" + e);
                }
            }
        }
        catch (Exception e){
            Log.e(TAG,""+e);
        }
        return mMusicList;
    }
    //Lấy file .mp3 tr máy
    private void getFileMusic(File file) {
        File[] files = file.listFiles();
        for ( File f : files ){
            if(f.isDirectory()){
                getFileMusic(new File(f.getAbsolutePath()));
            }
            else {
                if(f.getName().endsWith(".mp3")){
                    mFile.add(f);
                }
            }
        }
    }
}

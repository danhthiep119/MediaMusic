package com.bkav.mediamusic.view.fragment;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import java.util.concurrent.TimeUnit;

public class AllsongsFragment extends Fragment {
    private RecyclerView lvListMusic;
    private List<Music> mMusicList = new ArrayList<>();
    private List<File> mFile = new ArrayList<>();
    private ListMusicAdapter listMusicAdapter;
    private final String TAG = "AllSongsFragment";

    public AllsongsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.allsongs_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addControls(view);
        addEvents();
        super.onViewCreated(view, savedInstanceState);
    }

    private void addEvents() {
        lvListMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment mBSF = new BottomSheetFragment();
                mBSF.show(getChildFragmentManager(), "BottomSheet");
            }
        });
    }

    private void addControls(View view) {
        lvListMusic = view.findViewById(R.id.lvListMusic);
        mMusicList.clear();
        mMusicList.addAll(getListMusic());
        Collections.sort(mMusicList, new Comparator<Music>() {
            @Override
            public int compare(Music music, Music t1) {
                return music.getName().compareTo(t1.getName());
            }
        });
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
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                for (int i = 0; i < mFile.size(); i++) {
                    try {
                        mMediaPlayer.setDataSource(mFile.get(i).getAbsolutePath());
                        mMediaPlayer.prepare();
                        mTemp.add(new Music(mFile.get(i).getName(), "", mMediaPlayer.getDuration(), mFile.get(i).getAbsolutePath()));
                        mMediaPlayer.reset();
                    } catch (IOException e) {
                        Log.e(TAG, "" + e);
                    }
                }
            } else {
                String[] projection = new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.AUTHOR,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.RELATIVE_PATH
                };
                String selection = MediaStore.Video.Media.DURATION +
                        " >= ?";
                String[] selectionArgs = new String[]{
                        String.valueOf(TimeUnit.MILLISECONDS.convert(0, TimeUnit.MINUTES))
                };
                String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";
                ;

                Cursor cursor = getContext().getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder
                );

                while (cursor.moveToNext()) {
                    // Use an ID column from the projection to get
                    // a URI representing the media item itself.
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                    String author = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.AUTHOR));
                    long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RELATIVE_PATH));
                    mMusicList.add(new Music(name, author, duration, path));
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

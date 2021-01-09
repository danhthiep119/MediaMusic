package com.bkav.mediamusic.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bkav.mediamusic.R;
import com.bkav.mediamusic.adapter.ListMusicAdapter;
import com.bkav.mediamusic.model.Music;

public class MediaPlaybackFragment extends Fragment {
    private TextView txtName, txtAuthor, txtCurrentDuration, txtEndDuration;
    private ImageButton btnBack, btnPlay, btnNext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public MediaPlaybackFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mediaplayback_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addControls(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void addControls(View view) {
        txtName = view.findViewById(R.id.txtNameMedia);
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtCurrentDuration = view.findViewById(R.id.txtCurrentDuration);
        txtEndDuration = view.findViewById(R.id.txtEndDuration);
        btnBack = view.findViewById(R.id.btnBack);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnNext = view.findViewById(R.id.btnNext);

    }

    public void getInformation(CharSequence text){
        System.out.println(text);
//        txtName.setText(text);
//        txtAuthor.setText(music.getAuthor());
    }
}


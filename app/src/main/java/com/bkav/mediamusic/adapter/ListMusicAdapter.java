package com.bkav.mediamusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bkav.mediamusic.IGetPlayMusic;
import com.bkav.mediamusic.R;
import com.bkav.mediamusic.model.Music;
import com.bkav.mediamusic.service.MediaPlaybackService;

import java.util.ArrayList;
import java.util.List;

public class ListMusicAdapter extends RecyclerView.Adapter<ListMusicAdapter.ViewHolder> implements IGetPlayMusic {
    private List<Music> mMusicList = new ArrayList<>();
    private Context mContext;

    public ListMusicAdapter(Context mContext, List<Music> mMusicList) {
        this.mContext = mContext;
        this.mMusicList = mMusicList;
    }

    @NonNull
    @Override
    public ListMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.music_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListMusicAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(mMusicList.get(position).getName());
        holder.txtDuration.setText(MilisecondsToTimer(mMusicList.get(position).getDuration()/1000));
        holder.btnStatus.setText("" + (position + 1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("has been click at "+position+"position");
                getData(mMusicList.get(position));
                Intent intent = new Intent(mContext, MediaPlaybackService.class);
                intent.putExtra("path",mMusicList.get(position).getPath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.startService(intent);
                    holder.btnStatus.setCompoundDrawables(mContext.getDrawable(R.drawable.ic_baseline_bar_chart_24),null,null,null);
                    holder.btnStatus.setText("");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMusicList.isEmpty() ? 0 : mMusicList.size();
    }

    @Override
    public void getData(Music music) {
        System.out.println(music.getName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnStatus;
        private TextView txtName, txtDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            btnStatus = itemView.findViewById(R.id.btnStatus);
        }
    }

    private String MilisecondsToTimer(long milisec) {
        String finalTimerString = "";
        String secondString;
        String minuteString;
        int seconds = (int) milisec / 60 %60;
        int minutes = (int) (milisec /60 / 60);
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else secondString = "" + seconds;
        if (minutes < 10) {
            minuteString = "0" + minutes;
        } else minuteString = "" + minutes;
        finalTimerString = finalTimerString + minuteString + ":" + secondString;
        return finalTimerString;
    }

}

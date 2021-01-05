package com.bkav.mediamusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bkav.mediamusic.R;
import com.bkav.mediamusic.model.Music;

import java.util.List;

public class ListMusicAdapter extends RecyclerView.Adapter<ListMusicAdapter.ViewHolder> {
    private List<Music> mMusicList;
    private Context mContext;

    public ListMusicAdapter(Context mContext, List<Music> mMusicList) {
        this.mContext = mContext;
        this.mMusicList = mMusicList;
    }

    @NonNull
    @Override
    public ListMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext,R.layout.music_item,parent));
    }

    @Override
    public void onBindViewHolder(@NonNull ListMusicAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(mMusicList.get(position).getName());
        holder.txtDuration.setText(MilisecondsToTimer(mMusicList.get(position).getDuration()));
        holder.btnStatus.setText(position);
    }

    @Override
    public int getItemCount() {
        return mMusicList.isEmpty()?0:mMusicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnStatus;
        private TextView txtName,txtDuration;
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
        int seconds = (int) milisec % 60;
        int minutes = seconds % 60;
        int hours = (int) milisec / (60 * 60);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
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

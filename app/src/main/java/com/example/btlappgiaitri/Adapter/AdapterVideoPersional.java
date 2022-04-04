package com.example.btlappgiaitri.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlappgiaitri.Models.DataVideo;
import com.example.btlappgiaitri.R;

import java.util.ArrayList;

public class AdapterVideoPersional extends RecyclerView.Adapter<AdapterVideoPersional.VideoHolder> {
    ArrayList<DataVideo> dataVideoList;
    private Context context;


    public AdapterVideoPersional(Context context){
        this.context = context;
    }
    public void setDataVideoList(ArrayList<DataVideo> dataVideoList) {
        this.dataVideoList = dataVideoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videopersional,parent,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        DataVideo dataVideo = dataVideoList.get(position);
        if(dataVideo==null){
            return;
        }

        holder.data.setVideoURI(Uri.parse(dataVideo.getVideourl()));
        holder.data.start();


    }

    @Override
    public int getItemCount() {
        if(dataVideoList!=null){
            dataVideoList.size();
        }
        return 0;
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        VideoView data;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.videoView1);
        }
    }
}

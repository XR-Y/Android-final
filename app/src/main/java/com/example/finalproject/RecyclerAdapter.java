package com.example.finalproject;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Uri> urls = new ArrayList<>();

    private final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Movies";

    public RecyclerAdapter(){
        loadValue();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selfvideo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = urls.get(position).toString();
        int index = name.lastIndexOf('/');
        name = name.substring(index+1, name.length()-4);
        holder.videoName.setText(name);
        holder.videoView.setVideoURI(urls.get(position));
        holder.videoView.start();


        holder.videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.start();
        });
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public VideoView videoView;
        public TextView videoName;
        public ViewHolder(View view){
            super(view);
            videoView = view.findViewById(R.id.self_video);
            videoName = view.findViewById(R.id.video_name);
        }
    }

    private void loadValue() {
        File file = new File(path);
        File[] files = file.listFiles();
        for(int i = 0; i< Objects.requireNonNull(files).length; i++){
            String url = files[i].getAbsolutePath();
            if(url.contains("VID")){
                urls.add(Uri.parse(url));
            }
        }
    }

}

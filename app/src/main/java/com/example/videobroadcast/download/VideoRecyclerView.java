package com.example.videobroadcast.download;

import android.media.session.MediaController;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videobroadcast.R;

import java.util.List;

public class VideoRecyclerView extends RecyclerView.Adapter<VideoRecyclerView.ViewHolder> {
    private List<VideoData> videos;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        public ImageView thumbnail;
        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            thumbnail = layout.findViewById(R.id.thumbnail);
            title = layout.findViewById(R.id.title);
        }
    }

    public VideoRecyclerView(List<VideoData> videos) {
        this.videos = videos;
    }

    public void add(int position, VideoData video) {
        videos.add(position, video);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        videos.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_video, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoData video = videos.get(position);
        holder.title.setText(video.getTitle());
        holder.thumbnail.setImageDrawable(video.getThumbnail());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}

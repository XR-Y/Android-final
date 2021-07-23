package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {
    private Context curActivity;
    public ViewPagerAdapter(Context context){
        curActivity = context;
    }
    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPagerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_video, parent,false));
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        FetchVideos.curVideoId = position;
        VideoData videoData = FetchVideos.videos.get(FetchVideos.curVideoId);

        holder.videoView.setVideoPath(videoData.getFeedUrl());
        Glide.with(this.curActivity)
                .load(videoData.getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.avatarView);
        double x = (videoData.getLikeCount() + 1)/10000.0;
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String tmp = df.format(x);
        holder.mLikeNum.setText(tmp + "w");
        holder.mNickView.setText(videoData.getNickname());
        holder.mDesView.setText(videoData.getDescription());
        holder.textView.setText("最新评论：送我上热评");
        Glide.with(curActivity).load(R.drawable.ic_button_like_red).into(holder.imageView);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FetchVideos.getHttpBitmap(videoData.getFeedUrl()).compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(curActivity).load(bytes).into(holder.backgroundView);

        holder.backgroundView.setVisibility(View.VISIBLE);
        holder.backgroundView.bringToFront();
        holder.animationView.setVisibility(View.VISIBLE);
        holder.animationView.bringToFront();
        holder.animationView.playAnimation();

        holder.videoView.start();
        Log.d("123", String.valueOf(holder.videoView.isPlaying()));

        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holder.backgroundView.setVisibility(View.INVISIBLE);
                holder.animationView.setVisibility(View.INVISIBLE);
            }
        });
        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                holder.videoView.start();
            }
        });

        holder.mCommentBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                holder.ensure.setVisibility(View.VISIBLE);
                holder.getComment.setVisibility(View.VISIBLE);
                holder.getComment.bringToFront();
            }
        });

        holder.ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = holder.getComment.getText().toString();
                VideoActivity.hideKeyboard((Activity) curActivity, holder.getComment);
                holder.ensure.setVisibility(View.INVISIBLE);
                holder.getComment.setVisibility(View.INVISIBLE);
                holder.getComment.setText("");
                holder.textView.setText("最新评论：" + comment);
                Toast.makeText((Activity) curActivity, "评论成功！", Toast.LENGTH_SHORT).show();
            }
        });


        holder.videoView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector mGesture;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mGesture == null) {
                    mGesture = new GestureDetector(curActivity, new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent e) {
                            if (holder.videoView.isPlaying()) {
                                holder.videoView.pause();
                            } else {
                                holder.videoView.start();
                            }
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            super.onLongPress(e);
                        }

                        @Override
                        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                            return super.onScroll(e1, e2, distanceX, distanceY);
                        }
                    });
                    mGesture.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            if(videoData.isLiked()){
                                videoData.setLiked(false);
                                Glide.with(curActivity).load(R.drawable.ic_button_like).into(holder.likeButton);
                                double x = (videoData.getLikeCount() - 1)/10000.0;
                                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                                String tmp = df.format(x);
                                videoData.setLikeCount(videoData.getLikeCount() - 1);
                                holder.mLikeNum.setText(tmp + "w");
                            }
                            else{
                                videoData.setLiked(true);
                                holder.imageView.setVisibility(View.VISIBLE);
                                holder.imageView.bringToFront();
                                Glide.with(curActivity).load(R.drawable.ic_button_like_red).into(holder.likeButton);
                                double x = (videoData.getLikeCount() + 1)/10000.0;
                                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                                String tmp = df.format(x);
                                videoData.setLikeCount(videoData.getLikeCount() + 1);
                                holder.mLikeNum.setText(tmp + "w");
                                v.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 这里会在 1s 后执行
                                        holder.imageView.setVisibility(View.INVISIBLE);
                                    }
                                }, 1000);
                            }
                            return true;
                        }

                        @Override
                        public boolean onDoubleTapEvent(MotionEvent e) {
                            return false;
                        }
                    });
                }

                return mGesture.onTouchEvent(event);
//                return false;
            }


        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewPagerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.backgroundView.setVisibility(View.VISIBLE);
        holder.backgroundView.bringToFront();
        holder.animationView.setVisibility(View.VISIBLE);
        holder.animationView.bringToFront();
        holder.animationView.playAnimation();
        holder.videoView.start();
    }

    @Override
    public int getItemCount() {
        return FetchVideos.videos.size();
    }


    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public VideoView videoView;
        public TextView mNickView;
        public TextView mDesView;
        public TextView mLikeNum;
        public ImageView imageView;
        public ImageView avatarView;
        public ImageButton likeButton;
        public ImageView backgroundView;
        public LottieAnimationView animationView;
        public ImageButton mCommentBtn;
        public Button ensure;
        public EditText getComment;
        public TextView textView;

        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
            mNickView = itemView.findViewById(R.id.text_nickname);
            mDesView = itemView.findViewById(R.id.description);
            mLikeNum = itemView.findViewById(R.id.number_likecount);
            imageView = itemView.findViewById(R.id.image_view);
            avatarView = itemView.findViewById(R.id.ic_avatar);
            likeButton = itemView.findViewById(R.id.like_button);
            backgroundView = itemView.findViewById(R.id.background_view);
            animationView = itemView.findViewById(R.id.animation_view);
            ensure = itemView.findViewById(R.id.comment_btn);
            getComment = itemView.findViewById(R.id.comment_view);
            mCommentBtn = itemView.findViewById(R.id.comment);
            textView = itemView.findViewById(R.id.text_song);
        }
    }
}


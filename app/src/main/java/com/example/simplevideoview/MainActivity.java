package com.example.simplevideoview;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private static final String VIDEO_SAMPLE = "shinhan";
    private VideoView mVideoView;



    private int mCurrentPosition = 0;

    private static final String PLAYBACK_TIME = "play_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoView = findViewById(R.id.videoview);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

    }

    @Override
    protected void onStart() {
        super.onStart();

        initializePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition()); //현재 재생 위치
    }
    private void initializePlayer() {



        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);

        mVideoView.start();

    }


    private void releasePlayer() {
        mVideoView.stopPlayback();
    }


    private Uri getMedia(String mediaName) {
        if (mCurrentPosition > 0) { //영상이 재생 중임
            mVideoView.seekTo(mCurrentPosition);
        } else {
            mVideoView.seekTo(1); //0이면 영상 첫 번째 프레임으로 표시
        }
        return Uri.parse("android.resource://" + getPackageName() +
                "/raw/" + mediaName);
    }
}
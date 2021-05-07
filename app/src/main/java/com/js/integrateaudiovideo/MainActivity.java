package com.js.integrateaudiovideo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private VideoView videoView;
    private Button button,play_music,pause_music;
    private SeekBar volumeSeekbar,music_seekbar;

    MediaController mediaController;

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView=findViewById(R.id.my_video_view);
        volumeSeekbar =findViewById(R.id.seekBar);
        music_seekbar=findViewById(R.id.seekBar3);
        button=findViewById(R.id.play_button);
        button.setOnClickListener(MainActivity.this);
        pause_music=findViewById(R.id.pause);
        play_music=findViewById(R.id.play);
        play_music.setOnClickListener(MainActivity.this);
        pause_music.setOnClickListener(MainActivity.this);

        mediaController=new MediaController(MainActivity.this);


        mediaPlayer=MediaPlayer.create(this,R.raw.sample_audio);
        audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);

        int max_volume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current_volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeSeekbar.setMax(max_volume);
        volumeSeekbar.setProgress(current_volume);


        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    Toast.makeText(MainActivity.this, progress+"", Toast.LENGTH_SHORT).show();
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,1);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {



            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        music_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser){
                        mediaPlayer.seekTo(progress);
                    }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });

        music_seekbar.setMax(mediaPlayer.getDuration());



    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.play_button:
                Uri video_uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sample_video);

                videoView.setVideoURI(video_uri);
                videoView.start();
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
//        button.setText("pause video");
                break;

            case R.id.play:
                mediaPlayer.start();
                timer=new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        music_seekbar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                },0,1000);
                break;


            case R.id.pause:
                Toast.makeText(this, "tapped", Toast.LENGTH_SHORT).show();
                    mediaPlayer.pause();
                    timer.cancel();
                    break;






        }




    }
}
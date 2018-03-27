package com.example.meiriyiwen.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.meiriyiwen.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 李木白 on 2018/3/26.
 */

public class RadioPlayer extends LinearLayout{
    
    private TextView titleView;
    private TextView descriptionView;
    private TextView currentView;
    private TextView endView;
    private Button playbutton;
    private AppCompatSeekBar seekBar;
    private String url;
    private String title;
    private String description;
    private int currentTime;
    private int endTime;
    //private MediaPlayer player;
    private RadioService.RadioBinder binder;
    private boolean isPlaying = false;
    private boolean canPlay = true;
    private ProgressBar progressBar;
    private Boolean isDraging = false;
    Intent intent = new Intent(getContext(),RadioService.class);
    private Timer timer = new Timer();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0) afterPrepare();
            if(msg.what == 1 && !isDraging) showTime();
        }
    };

    public RadioPlayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.radioplayer,this);
        
        titleView = findViewById(R.id.radioplayer_title_textview);
        descriptionView = findViewById(R.id.radioplayer_description_textview);
        currentView = findViewById(R.id.radioplayer_current_textview);
        endView = findViewById(R.id.radioplayer_end_textview);
        playbutton = findViewById(R.id.raioplayer_playorpause_button);
        seekBar = findViewById(R.id.radioplayer_seekbar);
        progressBar = findViewById(R.id.loading_player_progressbar);
        playbutton.setClickable(false);

        playbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying) {
                    pause();
                }   else {
                    if(canPlay) play();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isDraging = true;
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isDraging = false;
                binder.seekTo(seekBar.getProgress());
                if(isPlaying) {
                    binder.play();
                }
                currentTime = binder.getCurrentPositon();
            }
        });
    }

    public void prepare() {
        Log.d("RadioPrepare: ","begin");
        progressBar.setVisibility(ProgressBar.VISIBLE);
        titleView.setText(title);
        descriptionView.setText(description);

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("RadioService: ","connected");
                binder = (RadioService.RadioBinder) iBinder;
                binder.prepare(url,title,description);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(binder.canPlay()) {
                            handler.sendEmptyMessage(0);
                            cancel();
                        }
                    }
                },200,200);
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        getContext().bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }

    private void afterPrepare() {
        playbutton.setClickable(true);
        playbutton.setBackgroundResource(R.drawable.play);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        canPlay = true;
        endTime = binder.getDuration();
        endView.setText(transformTimeFormat(endTime)+"");
        seekBar.setMax(endTime);
        currentTime = 0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isPlaying) {
                    currentTime = binder.getCurrentPositon();
                    handler.sendEmptyMessage(1);
                }
            }
        },0,100);
    }

    private void pause() {
        playbutton.setBackgroundResource(R.drawable.play);
        isPlaying = false;
        binder.pause();
    }

    private void play() {
        playbutton.setBackgroundResource(R.drawable.pause);
        isPlaying = true;
        binder.play();
    }

    public void end() {
        getContext().stopService(intent);
        timer.cancel();
    }

    private void showTime() {
        seekBar.setProgress(currentTime);
        currentView.setText(transformTimeFormat(currentTime)+"");
    }

    private String transformTimeFormat(int miles) {
        int minute,second;
        minute = miles/60000;
        second = (miles - (minute * 60000))/1000;
        String first = (minute/10 != 0)?(minute+""):("0"+minute);
        String last = (second/10 != 0)?(second+""):("0"+second);
        return first+":"+last;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }


}

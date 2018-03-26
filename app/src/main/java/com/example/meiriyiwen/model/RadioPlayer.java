package com.example.meiriyiwen.model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meiriyiwen.R;

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
    private String currentTime;
    private String endTime;

    public RadioPlayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.radioplayer,this,false);
        
        titleView = findViewById(R.id.radioplayer_title_textview);
        descriptionView = findViewById(R.id.radioplayer_description_textview);
        currentView = findViewById(R.id.radioplayer_current_textview);
        endView = findViewById(R.id.radioplayer_end_textview);
        playbutton = findViewById(R.id.raioplayer_playorpause_button);
        seekBar = findViewById(R.id.radioplayer_seekbar);
    }


    public void play() {

    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getEndTime() {
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

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

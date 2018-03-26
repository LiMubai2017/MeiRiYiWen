package com.example.meiriyiwen.view;

import android.animation.IntEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.meiriyiwen.MainView;
import com.example.meiriyiwen.R;
import com.example.meiriyiwen.model.RecyclerClickListner;
import com.example.meiriyiwen.model.Tag;
import com.example.meiriyiwen.presenter.RadioRacyclerViewAdapter;
import com.example.meiriyiwen.presenter.Utilities;

import java.util.List;

/**
 * Created by 李木白 on 2018/3/24.
 */

public class SecondFragment extends Fragment {
    View view;
    private List<Tag> tagList;
    RecyclerView recyclerView ;
    RadioRacyclerViewAdapter adapter;
    String url = "http://voice.meiriyiwen.com/";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initres();
            Log.d("second","message handled");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.second_fragment,container,false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initContent();
                handler.sendEmptyMessage(0);
                Log.d("second","message sent");
            }
        }).start();
        return view;
    }

    private void initContent() {
        String resources = Utilities.requestResources(url);
        tagList = Utilities.pickUpTagList(resources);
    }

    public void initres() {
        recyclerView = view.findViewById(R.id.radio_recyclerview);
        adapter = new RadioRacyclerViewAdapter(tagList);
        adapter.setContext(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setmListner(new RecyclerClickListner<RadioRacyclerViewAdapter.ViewHolder>() {
            @Override
            public void onClickListner(final RadioRacyclerViewAdapter.ViewHolder viewHolder, int position) {

            }
        });

    }

}

package com.example.meiriyiwen.view;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meiriyiwen.R;
import com.example.meiriyiwen.presenter.ContentAdapter;
import com.example.meiriyiwen.presenter.Utilities;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.zip.Inflater;

/**
 * Created by 李木白 on 2018/3/24.
 */

public class FirstFragment extends Fragment {
    RecyclerView recyclerView;
    ContentAdapter adapter;
    View view;
    String[] content;
    ProgressBar progressBar;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) initView();
            else {
                if(msg.what == 1) {
                    adapter.setList(Arrays.asList(content));
                    adapter.refreshFinished();
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_fragment,container,false);
        initContent(0);
        return view;
    }

    public void initContent(final int msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url;
                if(msg == 0) {
                    url = "https://meiriyiwen.com/";
                }   else {
                    url = "https://meiriyiwen.com/random";
                }
                String resources = Utilities.requestResources(url);
                content = Utilities.pickUpTextString(resources);
                handler.sendEmptyMessage(msg);
            }
        }).start();
    }

    public void initView() {
        recyclerView = view.findViewById(R.id.content_recyclerview);
        adapter = new ContentAdapter(Arrays.asList(content));
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initContent(1);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void show(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

}

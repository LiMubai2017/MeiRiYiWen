package com.example.meiriyiwen.view;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meiriyiwen.R;
import com.example.meiriyiwen.model.Tag;
import com.example.meiriyiwen.presenter.BookShelfAdapter;
import com.example.meiriyiwen.presenter.Utilities;

import java.util.List;

/**
 * Created by 李木白 on 2018/3/24.
 */

public class ThirdFragment extends Fragment {
    private View view;
    private List<Tag> list;
    RecyclerView recyclerView;
    BookShelfAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initRes();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.third_fragment,container,false);
        initContent();
        return view;
    }

    public void initContent() {
        final String url = "http://book.meiriyiwen.com/";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String resources = Utilities.requestResources(url);
                list = Utilities.pickUpBookshelf(resources);
                Log.d("THIRD", list.size()+"");
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    public void initRes() {
        recyclerView = view.findViewById(R.id.bookshelf_recyclerview);
        adapter = new BookShelfAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}

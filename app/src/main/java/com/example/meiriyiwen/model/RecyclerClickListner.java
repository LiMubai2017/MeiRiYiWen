package com.example.meiriyiwen.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.meiriyiwen.presenter.BookShelfAdapter;

/**
 * Created by 李木白 on 2018/3/25.
 */

public interface RecyclerClickListner<type> {
    public abstract void onClickListner(type viewHolder, int position);
}

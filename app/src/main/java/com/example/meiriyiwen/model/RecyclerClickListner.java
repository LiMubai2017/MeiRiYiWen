package com.example.meiriyiwen.model;

/**
 * Created by 李木白 on 2018/3/25.
 */

public interface RecyclerClickListner<type> {
    void onClick(type viewHolder, int position);
    void onLongClick(type viewHolder, int position);
}

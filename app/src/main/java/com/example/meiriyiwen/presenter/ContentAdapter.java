package com.example.meiriyiwen.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.meiriyiwen.R;

import java.util.List;

/**
 * Created by 李木白 on 2018/3/25.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private List<String> list;
    private static final int BUTTON = 1;
    private static final int LINE = 2;
    public View.OnClickListener clickListener;
    public Button button;
    public ProgressBar progressBar;

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ContentAdapter(List<String> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    public class ButtonHolder extends ViewHolder {

        public ButtonHolder (View view) {
            super(view);
            button = view.findViewById(R.id.refresh_button);
            progressBar = view.findViewById(R.id.loadContent_progressbar);

            if(clickListener != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setClickable(false);
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                        clickListener.onClick(view);
                    }
                });
            }

        }
    }

    public void refreshFinished() {
        button.setClickable(true);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == list.size()-1)
            return BUTTON;
        else {
            return LINE;
        }
    }

    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LINE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_layout, parent, false);
            return new ButtonHolder(view);
        }
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position != list.size()-1) {
            String current = list.get(position);
            if (position == 0) {
                holder.textView.setTextSize(20);
            }
            holder.textView.setText(current);
        }
    }
}

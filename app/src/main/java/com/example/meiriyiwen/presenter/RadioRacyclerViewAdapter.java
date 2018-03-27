package com.example.meiriyiwen.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meiriyiwen.R;
import com.example.meiriyiwen.model.RecyclerClickListner;
import com.example.meiriyiwen.model.Tag;

import java.util.List;

/**
 * Created by 李木白 on 2018/3/24.
 */

public class RadioRacyclerViewAdapter extends RecyclerView.Adapter<RadioRacyclerViewAdapter.ViewHolder> {
    private List<Tag> tagList;
    private Context context;
    private RecyclerClickListner<RadioRacyclerViewAdapter.ViewHolder> mListner;

    public void setmListner(RecyclerClickListner<ViewHolder> mListner) {
        this.mListner = mListner;
    }

    public RadioRacyclerViewAdapter(List<Tag> list) {
        tagList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        public ImageView imageView;
        TextView title;
        TextView authorAndAnchor;
        TextView issuse;

        public ViewHolder(View tagView) {
            super(tagView);
            view = tagView;
            imageView = view.findViewById(R.id.radio_imageview);
            title = view.findViewById(R.id.title_textview);
            issuse = view.findViewById(R.id.issuse_textview);
            authorAndAnchor = tagView.findViewById(R.id.authorAndAnchor_textview);
    }

}


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_view,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.authorAndAnchor.setText("作者： "+tag.getAuthor()+"  "+"主播： "+tag.getAnchor());
        holder.title.setText(tag.getTitle());
        holder.issuse.setText(tag.getIssuse()+"期");
        Glide.with(context).load(tag.getImageURL()).into(holder.imageView);

        if(mListner != null && !holder.view.hasOnClickListeners()) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListner.onClickListner(holder,holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

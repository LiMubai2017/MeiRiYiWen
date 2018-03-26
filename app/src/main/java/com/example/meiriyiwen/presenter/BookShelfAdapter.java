package com.example.meiriyiwen.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meiriyiwen.R;
import com.example.meiriyiwen.model.Tag;

import java.util.List;

/**
 * Created by 李木白 on 2018/3/25.
 */


public class BookShelfAdapter extends RecyclerView.Adapter<BookShelfAdapter.ViewHolder> {
    private List<Tag> list;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView title;
        public TextView author;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.bookshelf_imageview);
            title = view.findViewById(R.id.bookshelf_title_textview);
            author = view.findViewById(R.id.bookshelf_author_textview);
        }

    }

    public BookShelfAdapter(List<Tag> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tag book = list.get(position);
        holder.author.setText(book.getAuthor());
        holder.title.setText(book.getTitle());
        if(position % 2 != 0) holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.black_bg));
        Glide.with(context).load(book.getImageURL()).into(holder.imageView);
    }
}

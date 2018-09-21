package com.example.rypta.imagen;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InstaFeed extends RecyclerView.Adapter<InstaFeed.InstaFeedViewHolder>
{

    ArrayList<Profile> std;
    Context mctx;

    public InstaFeed(Context ctx,ArrayList<Profile>s)
    {
        mctx=ctx;
        std=s;
    }

    @NonNull
    @Override
    public InstaFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mctx);
        View v=inflater.inflate(R.layout.insta_feed,null);
        return new InstaFeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InstaFeedViewHolder instaFeedViewHolder, int i) {
        //BitmapDrawable ob = new BitmapDrawable(mctx.getResources(), std.get(i).getId());
        //instaFeedViewHolder.img.setBackground(ob);
        instaFeedViewHolder.img.setImageBitmap(std.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return std.size();
    }

    class InstaFeedViewHolder extends RecyclerView.ViewHolder
    {
        TextView fn,ln;
        ImageView img;
        RelativeLayout rl;
        public InstaFeedViewHolder(View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.imageView);
        }
    }
}

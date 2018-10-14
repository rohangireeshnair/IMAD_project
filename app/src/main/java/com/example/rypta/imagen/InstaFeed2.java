package com.example.rypta.imagen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class InstaFeed2 extends RecyclerView.Adapter<InstaFeed2.InstaFeedViewHolder>
{

    ArrayList<Student> std;
    Context mctx;

    public InstaFeed2(Context ctx, ArrayList<Student>s)
    {
        mctx=ctx;
        std=s;
    }

    @NonNull
    @Override
    public InstaFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(mctx);
        View v=inflater.inflate(R.layout.insta_feed2,null);
        return new InstaFeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InstaFeedViewHolder instaFeedViewHolder, int i) {
        //BitmapDrawable ob = new BitmapDrawable(mctx.getResources(), std.get(i).getId());
        //instaFeedViewHolder.img.setBackground(ob);
        instaFeedViewHolder.img.setImageBitmap(std.get(i).getProfilepic());
        instaFeedViewHolder.uname.setText(std.get(i).getUsername());
        instaFeedViewHolder.name.setText(std.get(i).getname());
    }

    @Override
    public int getItemCount() {
        return std.size();
    }

    class InstaFeedViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView img;
        TextView uname;
        public InstaFeedViewHolder(View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.profilepic);
            uname=itemView.findViewById(R.id.uname1);
            name=itemView.findViewById(R.id.name1);
        }
    }
}

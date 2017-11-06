package com.example.sam.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.myapplication.R;
import com.example.sam.myapplication.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sam on 06.11.2017.
 */

public class MovieAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Movie> mDataSource;

    public MovieAdapter(Context mContext, List<Movie> mDataSource) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_movie, parent, false);

        // Get title element
        TextView titleTextView = (TextView) rowView.findViewById(com.example.sam.myapplication.R.id.movie_list_title);

        TextView detailTextView = (TextView) rowView.findViewById(com.example.sam.myapplication.R.id.movie_list_detail);

        ImageView thumbnailImageView = (ImageView) rowView.findViewById(com.example.sam.myapplication.R.id.movie_list_thumbnail);

        Movie movie = (Movie) getItem(position);

        titleTextView.setText(movie.getName());
        detailTextView.setText(movie.getDescription());

        Picasso.Builder builder = new Picasso.Builder(this.mContext);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                exception.printStackTrace();
            }
        });
        builder.build().load(movie.getImage()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView,
                new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("true");
                    }

                    @Override
                    public void onError() {
                        System.out.println("false");
                    }
                });
        return rowView;
    }
}

package com.example.sam.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sam.myapplication.R;
import com.example.sam.myapplication.model.Movie;

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
        View rowView = mInflater.inflate(R.layout.list_item_movie, parent, false);

        TextView titleTextView = rowView.findViewById(com.example.sam.myapplication.R.id.movie_list_title);

        TextView detailTextView = rowView.findViewById(com.example.sam.myapplication.R.id.movie_list_detail);

        Movie movie = (Movie) getItem(position);

        titleTextView.setText(movie.getName());
        detailTextView.setText(movie.getScore().toString());

        return rowView;
    }

    public void updateList(List<Movie> newlist) {
        mDataSource = newlist;
        this.notifyDataSetChanged();
    }
}

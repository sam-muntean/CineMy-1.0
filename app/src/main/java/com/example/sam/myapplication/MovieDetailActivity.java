package com.example.sam.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.myapplication.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");
        String score = this.getIntent().getExtras().getString("score");
        String desc = this.getIntent().getExtras().getString("description");

        TextView titleTextView = (TextView) this.findViewById(R.id.movie_title);
        TextView scoreTextView = (TextView) this.findViewById(R.id.movie_score);
        TextView descTextView = (TextView) this.findViewById(R.id.movie_description);
        ImageView thumbnailImageView = (ImageView) this.findViewById(R.id.movie_image);

        titleTextView.setText(title);
        scoreTextView.setHint(score);
        descTextView.setText(desc);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                exception.printStackTrace();
            }
        });
        builder.build().load(url).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
    }

    public void saveScore(View view){
        EditText editText = (EditText) findViewById(R.id.movie_score);
        String message = editText.getText().toString();

        Intent data = new Intent();
        data.putExtra("message", message);
        data.putExtra("name", this.getIntent().getExtras().getString("title"));
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}

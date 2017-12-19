package com.example.sam.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");
        String score = this.getIntent().getExtras().getString("score");
        String desc = this.getIntent().getExtras().getString("description");

        TextView titleTextView =  this.findViewById(R.id.movie_title);
        TextView scoreTextView =  this.findViewById(R.id.movie_score);
        TextView descTextView =  this.findViewById(R.id.movie_description);
        //ImageView thumbnailImageView = this.findViewById(R.id.movie_image);

        titleTextView.setText(title);
        scoreTextView.setHint(score);
        descTextView.setText(desc);

        Integer scores = Integer.parseInt(score);

        PieChart pieChart = findViewById(R.id.chart);

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(scores, 1));
        yvalues.add(new Entry(100 - scores, 2));

        PieDataSet dataSet = new PieDataSet(yvalues, "Score analyse");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<String> entries = new ArrayList<>();

        entries.add("Positive");
        entries.add("Negative");

        PieData data = new PieData(entries, dataSet);
        pieChart.setData(data);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);


//        Picasso.Builder builder = new Picasso.Builder(this);
//        builder.listener(new Picasso.Listener()
//        {
//            @Override
//            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
//            {
//                exception.printStackTrace();
//            }
//        });
//        builder.build().load(url).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
    }

    public void saveScore(View view){
        EditText editText = findViewById(R.id.movie_score);
        String message = editText.getText().toString();

        Intent data = new Intent();
        String title = this.getIntent().getExtras().getString("title");
        String desc = this.getIntent().getExtras().getString("description");
        TextView scoreTextView =  this.findViewById(R.id.movie_score);

        data.putExtra("title", title);
        data.putExtra("desc", desc);
        data.putExtra("score", scoreTextView.getText().toString());

        setResult(Activity.RESULT_OK, data);
        finish();
    }
}

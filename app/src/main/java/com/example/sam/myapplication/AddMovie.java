package com.example.sam.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class AddMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie_activity);
        NumberPicker np = findViewById(R.id.numberPicker);


        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(100);
        //Gets whether the selector wheel wraps when reaching the min/max value.

        String[] nums = new String[101];
        for(int i=0; i<nums.length; i++)
            nums[i] = Integer.toString(i);

        np.setWrapSelectorWheel(false);
        np.setDisplayedValues(nums);
        np.setValue(1);
    }

    public void addMovie(View view) {

        TextView titleView = findViewById(R.id.movieTitle);
        String title = titleView.getText().toString();
        TextView authorView = findViewById(R.id.movieDirector);
        String author = authorView.getText().toString();
        TextView descriptionView = findViewById(R.id.movieDesc);
        String description = descriptionView.getText().toString();

        NumberPicker np = findViewById(R.id.numberPicker);

        Intent intent = new Intent();

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                System.out.println(newVal);

            }
        });


        //System.out.println("scoreeeeeeee++++++++++++" + np.getValue());
        intent.putExtra("score", Integer.toString(np.getValue()));
        intent.putExtra("title", title);
        intent.putExtra("director", author);
        intent.putExtra("description", description);


        setResult(RESULT_OK,intent);
        finish();
    }
}

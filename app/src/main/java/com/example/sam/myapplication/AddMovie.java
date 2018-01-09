package com.example.sam.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

public class AddMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(0);
        np.setMaxValue(100);

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

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}

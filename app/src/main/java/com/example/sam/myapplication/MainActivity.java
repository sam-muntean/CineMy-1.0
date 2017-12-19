package com.example.sam.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sam.myapplication.adapter.MovieAdapter;
import com.example.sam.myapplication.database.Movie;
import com.example.sam.myapplication.repository.MovieRepository;
import com.example.sam.myapplication.repository.Repository;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private MovieRepository movieRepository;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateAndroidSecurityProvider(this);
        mListView = findViewById(R.id.movie_list_view);

        movieRepository = new MovieRepository(getApplicationContext());
        movieRepository.init();
        adapter = new MovieAdapter(this, movieRepository.getMovies());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie= movieRepository.getMovies().get(position);
                Intent detailIntent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                detailIntent.putExtra("title", selectedMovie.getName());
                detailIntent.putExtra("score", selectedMovie.getScore().toString());
                detailIntent.putExtra("url", selectedMovie.getImage());
                detailIntent.putExtra("description", selectedMovie.getDescription());
                System.out.println("herererere");
                startActivityForResult(detailIntent, 1);
            }
        });

        Button addMovieButton = findViewById(R.id.addMovie);
        addMovieButton.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, AddMovie.class);
            startActivityForResult(intent,2);
        });
    }

    public void sendMail(View view){
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","sam.muntean@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void deleteMovie(View view) {
        final int position = mListView.getPositionForView((View) view.getParent());
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Delete this movie?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        System.out.println("deleteeeeee " + position);
                        Movie movie = new Movie();
                        int idx  = movieRepository.findOne(position).getId();
                        movie.setId(idx);
                        movieRepository.delete(movie);
                        adapter.updateList(movieRepository.getMovies());
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            if (resultCode == RESULT_OK){
                String title = data.getStringExtra("title");
                String score = data.getStringExtra("score");

                Movie movie = movieRepository.getMovieByTitle(title);
                movie.setScore(Integer.parseInt(score));
                movieRepository.updateMovie(movie);
                adapter.updateList(movieRepository.getMovies());
            }
        }
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                System.out.println("add movie");
                String title = data.getStringExtra("title");
                String director = data.getStringExtra("director");
                String description = data.getStringExtra("description");
                String score = data.getStringExtra("score");
                if (score == null)
                    score = "10";
                movieRepository.insert(title, director, description, Integer.parseInt(score));
                adapter.updateList(movieRepository.getMovies());
            }
        }
    }


    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("SecurityException", "Google Play Services not available.");
        }
    }
}

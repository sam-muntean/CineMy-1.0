package com.example.sam.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sam.myapplication.adapter.MovieAdapter;
import com.example.sam.myapplication.model.Movie;
import com.example.sam.myapplication.repository.Repository;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateAndroidSecurityProvider(this);

        mListView = (ListView) findViewById(R.id.movie_list_view);
        final Repository repo = new Repository();
        MovieAdapter adapter = new MovieAdapter(this, repo.getMovies());
        mListView.setAdapter(adapter);

        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie= repo.getMovies().get(position);

                Intent detailIntent = new Intent(context, MovieDetailActivity.class);
                detailIntent.putExtra("title", selectedMovie.getName());
                detailIntent.putExtra("score", selectedMovie.getScore().toString());
                detailIntent.putExtra("url", selectedMovie.getImage());
                detailIntent.putExtra("description", selectedMovie.getDescription());
                startActivityForResult(detailIntent, 0);
            }
        });
    }

    public void sendMail(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","sam.muntean@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " " + resultCode);
        switch(requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {
                    String name = data.getStringExtra("name");
                    Integer score = Integer.parseInt(data.getStringExtra("message"));
                    System.out.println(name + score);
                    Repository repo = new Repository();
                    repo.setScore(score, name);
                    System.out.println(repo.getMovies().get(1).getName() + repo.getMovies().get(1).getScore());
                    MovieAdapter adapter = new MovieAdapter(this, repo.getMovies());
                    mListView.setAdapter(adapter);
                }
                break;
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

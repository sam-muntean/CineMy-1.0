package com.example.sam.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sam.myapplication.adapter.MovieAdapter;
import com.example.sam.myapplication.model.Movie;
import com.example.sam.myapplication.repository.FDatabase;
import com.example.sam.myapplication.repository.MovieRepository;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private MovieRepository movieRepository;
    private MovieAdapter adapter;

    private static Context c;

    private FirebaseDatabase db;

    private DatabaseReference movieDb;

    private DatabaseReference userDb;

    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateAndroidSecurityProvider(this);
        mListView = findViewById(R.id.movie_list_view);
        c = getApplicationContext();

        db = FDatabase.getDatabase();

        movieDb = db.getReference().child("movies");
        movieDb.keepSynced(true);
        movieRepository = new MovieRepository(movieDb);

        userDb = db.getReference().child("roles");
        //System.out.println("2222222222222222222222" + userDb.toString() + "3333333333" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        userDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userRole = dataSnapshot.getValue(String.class);
                System.out.println("#########################"+userRole);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        movieDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Count ",""+ dataSnapshot.getChildrenCount());
                int id = 0;
                if(dataSnapshot.getChildrenCount() > 0) {
                    movieRepository.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Movie movie = postSnapshot.getValue(Movie.class);
                        movie.setId(++id);
                        movieRepository.addMovie(movie);
                        Log.d("Get Data", movie.toString());
                    }
                    movieRepository.setId(++id);
                    adapter.updateList(movieRepository.getMovies());
                } else {
                    movieRepository.setId(1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        adapter = new MovieAdapter(this, movieRepository.getMovies());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie= movieRepository.getMovies().get(position);
                Intent detailIntent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                detailIntent.putExtra("title", selectedMovie.getName());
                detailIntent.putExtra("score", selectedMovie.getScore().toString());
                detailIntent.putExtra("description", selectedMovie.getDescription());
                startActivityForResult(detailIntent, 1);
            }
        });

        Button addMovieButton = findViewById(R.id.addMovie);
        addMovieButton.setOnClickListener((v)->{
            if (userRole.equals("admin")) {
                Intent intent = new Intent(MainActivity.this, AddMovie.class);
                startActivityForResult(intent, 2);
            } else {
                addMovieButton.setEnabled(false);
                Toast.makeText(this, "Only admins can add new movies!", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent i = new Intent(MainActivity.this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    public static void showRepositoryUpdated() {
        Toast.makeText(c, "Repository updated!", Toast.LENGTH_SHORT).show();
    }

    public void sendMail(View view){
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        Button sendB = findViewById(R.id.button);
        if (userRole.equals("admin")) {
            Toast.makeText(this, "Only users request you movies!", Toast.LENGTH_LONG).show();
            sendB.setEnabled(false);
        } else {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "sam.muntean@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
    }


    public void deleteMovie(View view) {
        final int position = mListView.getPositionForView((View) view.getParent());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this movie?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Movie movie = movieRepository.findOne(position);
                        movieRepository.delete(movie);
                        adapter.updateList(movieRepository.getMovies());
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(c)
                                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                                        .setContentTitle("Cinema manager")
                                        .setContentText( "Movie '" + movie.getName() + "' was removed!" );
                        int mNotificationId = 001;
                        NotificationManager mNotifyMgr =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        mNotifyMgr.notify(mNotificationId, mBuilder.build());
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        if (userRole.equals("user")){

            Toast.makeText(this, "Only admins can delete!", Toast.LENGTH_LONG).show();
            return;
        }
        if (userRole.equals("admin")) {
            alert.show();
        }
    }

    // 1 - update
    // 2 - insert
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            if (resultCode == RESULT_OK){
                String title = data.getStringExtra("title");
                String score = data.getStringExtra("score");
                if (score == null) {
                   score = "0";
                }
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
                String description = data.getStringExtra("description");
                String score = data.getStringExtra("score");
                movieRepository.insert(title, description, Integer.parseInt(score));
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

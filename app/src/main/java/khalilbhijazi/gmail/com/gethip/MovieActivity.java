package khalilbhijazi.gmail.com.gethip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MovieActivity extends AppCompatActivity {

    private EditText movieGenreEditText;
    private EditText movieNameEditText;
    private Button addMovieBtn;
    private Button removeMovieBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //connecting to database

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        //getting the UI elements

        movieGenreEditText = (EditText) findViewById(R.id.movieGenreEditText);
        movieNameEditText = (EditText) findViewById(R.id.movieNameEditText);
        addMovieBtn = (Button) findViewById(R.id.addMovieBtn);
        removeMovieBtn = (Button) findViewById(R.id.removeMovieBtn);


        //detecting when the user wants to add a movie

        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieGenre = movieGenreEditText.getText().toString();
                String movieName = movieNameEditText.getText().toString();

                addMovie(movieGenre, movieName);
                sendToMain();
            }
        });

        //detecting when the user wants to delete a day's movie

        removeMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieGenre = movieGenreEditText.getText().toString();
                String movieName = movieNameEditText.getText().toString();
                removeMovie(movieGenre, movieName);
                sendToMain();
            }
        });
    }

    //this method removes a movie from under a user's date stamp

    private void removeMovie(String movieGenre, String movieName) {

        //getting the current user from the database reference

        FirebaseUser currUser = mAuth.getCurrentUser();

        //getting the date the user is deleting from

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM:dd:yyyy");
        String date = dateFormat.format(calendar.getTime());

        //movie to be removed

        final Movie movieToRemove = new Movie(movieGenre, movieName);

        //finding the user's movie section

        final DatabaseReference mReference = mDatabase.getReference().child("users").child(currUser.getUid()).child(date).child("movies");

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String movieKey = "";

                //looping through the user's day's movies

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Movie movie = snapshot.getValue(Movie.class);

                    //checking for matched movie

                    if (movie.equals(movieToRemove)) {
                        movieKey = snapshot.getKey();
                        break;
                    }

                }

                //movieKey string is still empty, then the movie wasn't found. otherwise, movie
                //gets removed from the database

                if (movieKey.equals("")) {
                    Toast.makeText(getApplicationContext(), "Movie not found!", Toast.LENGTH_LONG).show();
                } else {
                    mReference.child(movieKey).removeValue();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String error = databaseError.getMessage();
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });



    }

    //this method adds a movie under a user's date stamp

    private void addMovie(String movieGenre, String movieName) {

        //getting the current user from the database reference

        DatabaseReference mReference = mDatabase.getReference();
        FirebaseUser currUser = mAuth.getCurrentUser();

        //getting the date the user is posting on

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM:dd:yyyy");
        String date = dateFormat.format(calendar.getTime());
        DatabaseReference copy = mReference.child("users").child(currUser.getUid()).child(date).child("movies");
        String movieID = copy.push().getKey();

        //creating movie object and pushing it to the database

        Movie movie = new Movie(movieGenre, movieName);
        copy.child(movieID).setValue(movie);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //checking if user exists or not

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            sendToMain();
        }
    }

    //this method sends the user to the main activity and kills this activity off the stack

    private void sendToMain() {
        Intent mainIntent = new Intent(MovieActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}

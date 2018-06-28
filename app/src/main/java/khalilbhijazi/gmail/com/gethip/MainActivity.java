package khalilbhijazi.gmail.com.gethip;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private String [] options;
    private ListView optionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        optionsListView = (ListView) findViewById(R.id.optionsListView);
        Resources res = getResources();

        options = res.getStringArray(R.array.category_options);
        OptionsAdapter optionsAdapter = new OptionsAdapter(this, options);
        optionsListView.setAdapter(optionsAdapter);

        optionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent categoryIntent = new Intent(MainActivity.this, MovieActivity.class);
                startActivity(categoryIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // checking if user is logged in or not

        if (mAuth.getCurrentUser() == null) {

            //send user to login activity since he's not logged in

            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}

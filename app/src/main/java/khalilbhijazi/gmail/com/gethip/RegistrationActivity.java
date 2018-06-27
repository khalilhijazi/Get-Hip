package khalilbhijazi.gmail.com.gethip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private Button registrationBtn;
    private Button backToLoginBtn;
    private EditText registrationEmailEditText;
    private EditText registrationPasswordEditText;
    private EditText registrationConfPasswordEditText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registrationBtn = (Button) findViewById(R.id.registrationBtn);
        backToLoginBtn = (Button) findViewById(R.id.backToLoginBtn);
        registrationEmailEditText = (EditText) findViewById(R.id.registrationEmailEditText);
        registrationPasswordEditText = (EditText) findViewById(R.id.registrationPasswordEditText);
        registrationConfPasswordEditText = (EditText) findViewById(R.id.registrationConfPasswordEditText);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        //sending the user back to login screen if they already have an account and want to sign in

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });


        //processing registration info on submission

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //retrieving text entered in text fields

                String email = registrationEmailEditText.getText().toString();
                String password = registrationPasswordEditText.getText().toString();
                String confPassword = registrationConfPasswordEditText.getText().toString();

                //checking if any of the text fields is empty

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
                } else {

                    //checking that the password text is the same as the confirmed password text

                    if (password.equals(confPassword)) {

                        //adding user to database

                        addUser(email, password);
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void addUser(String email, String password) {

        final User newUser = new User(email, password);



        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //checking whether the user has been added successfully or not

                if (task.isSuccessful()) {
                    DatabaseReference mDatabaseReference = mDatabase.getReference();
                    FirebaseUser currUser = mAuth.getCurrentUser();

                    //adding a child user object under the "users" section

                    mDatabaseReference.child("users").child(currUser.getUid()).setValue(newUser);

                    Toast.makeText(RegistrationActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();
                    sendToMain();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(RegistrationActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //this method sends the user back to the main screen and removes this screen off the stack frame

    private void sendToMain() {
        Intent mainIntent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currUser = mAuth.getCurrentUser();

        //checking if user is logged in or not. If logged in, then user is sent to main activity

        if (currUser != null) {
            sendToMain();
        }
    }
}

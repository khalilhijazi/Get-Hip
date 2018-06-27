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

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailEditText;
    private EditText loginPasswordEditText;
    private Button loginBtn;
    private Button backToRegistrationBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailEditText = (EditText) findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = (EditText) findViewById(R.id.loginPasswordEditText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        backToRegistrationBtn = (Button) findViewById(R.id.backToRegistrationBtn);

        mAuth = FirebaseAuth.getInstance();


        //sending user back to registration screen if they haven't created an account and want to
        //create one

        backToRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);
                finish();
            }
        });

        //signing in to user's account with the credentials provided

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting user credentials

                String email = loginEmailEditText.getText().toString();
                String password = loginPasswordEditText.getText().toString();

                //checking if any of the fields is empty

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields.", Toast.LENGTH_LONG).show();
                } else {

                    //attempting to sign in to user's account

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //checking whether the login was successful or not

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "You have successfully logged in.", Toast.LENGTH_LONG).show();
                                sendToMain();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currUser = mAuth.getCurrentUser();

        //checking if user is logged in or not. if logged in, then he is sent to the main activity

        if (currUser != null) {
            sendToMain();
        }
    }

    //this method sends the user to the main activity and removes this activity off the stack frame

    private void sendToMain() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}

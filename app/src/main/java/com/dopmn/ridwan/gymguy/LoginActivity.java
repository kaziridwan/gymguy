package com.dopmn.ridwan.gymguy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginRedButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //for testing
//                Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(goToNextActivity);
                // Perform action on click
                EditText email = (EditText) findViewById(R.id.email);
                final String emailString = email.getText().toString();

                EditText pass = (EditText) findViewById(R.id.pass);
                final String passString = pass.getText().toString();
                Firebase ref = new Firebase("https://gymguy.firebaseio.com");

                // Create a handler to handle the result of the authentication
                Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authenticated successfully with payload authData

                        Context context = getApplicationContext();
                        CharSequence text = "Logged in";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(goToNextActivity);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                        Context context = getApplicationContext();
//                        CharSequence text = firebaseError.getMessage() + " : for " + emailString + passString + firebaseError.getDetails();
                        CharSequence text = firebaseError.getMessage() + " : " + firebaseError.getDetails();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                };

                // Authenticate users with a custom Firebase token
//                ref.authWithCustomToken("<token>", authResultHandler);
                // Or with an email/password combination
                ref.authWithPassword(emailString, passString, authResultHandler);
            }
        });

        Button signupRedButton = (Button) findViewById(R.id.signupRedButton);
        signupRedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //for testing
                Intent goToNextActivity = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(goToNextActivity);
            }
        });
        
    }
}

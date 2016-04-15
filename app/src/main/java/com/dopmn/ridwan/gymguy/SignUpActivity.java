package com.dopmn.ridwan.gymguy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.content_sign_up);

        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.signUpEmail);
                final String emailString = email.getText().toString();

                EditText pass = (EditText) findViewById(R.id.signUpPass);
                final String passString = pass.getText().toString();
                Firebase ref = new Firebase("https://gymguy.firebaseio.com");
                ref.createUser(emailString, passString, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        System.out.println("Successfully created user account with uid: " + result.get("uid"));
                        String uid = result.get("uid").toString();
                        // there was an error
                        Context context = getApplicationContext();
                        CharSequence text = "Registration successful";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        //save user data in firebase and persistent storage
                        Firebase ref = new Firebase("https://gymguy.firebaseio.com");
                        ref.child("users").child(uid).child("count").setValue(0);

                        SharedPreferences sharedPref = context.getSharedPreferences(
                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("uid", uid);
                        editor.commit();



                        Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(goToNextActivity);
                        
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // there was an error
                        Context context = getApplicationContext();
                        CharSequence text = "There was an error : "+firebaseError.getMessage();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

            }
        });
        Button loginRedButton = (Button) findViewById(R.id.loginRedButton);
        loginRedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //for testing
                Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goToNextActivity);
            }
        });

    }
}


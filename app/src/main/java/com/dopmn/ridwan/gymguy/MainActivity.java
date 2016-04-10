package com.dopmn.ridwan.gymguy;

import android.app.Activity;
import android.speech.RecognitionListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class MainActivity extends ListeningActivity {

    private LinearLayout content;
    private TextView textView;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize firbase
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);

        content = (LinearLayout)findViewById(R.id.speechText);

        textView = (TextView) findViewById(R.id.textView_1);

        // The following 3 lines are needed in every onCreate method of a ListeningActivity
        context = getApplicationContext(); // Needs to be set
        VoiceRecognitionListener.getInstance().setListener(this); // Here we set the current listener
        startListening(); // starts listening
    }

    // Here is where the magic happens
    @Override
    public void processVoiceCommands(String... voiceCommands) {
        content.removeAllViews();
        for (String command : voiceCommands) {
            TextView txt = new TextView(getApplicationContext());
            txt.setText(command);
            txt.setTextSize(20);
            txt.setTextColor(Color.BLACK);
            txt.setGravity(Gravity.CENTER);
            content.addView(txt);
        }

        count++;
        textView.setText(Integer.toString(count));

        restartListeningService();
    }
}

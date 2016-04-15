package com.dopmn.ridwan.gymguy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.speech.RecognitionListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.orm.SugarDb;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends ListeningActivity {

    private LinearLayout content;
    private TextView textView;
    private int count = 0;
    private boolean counting = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize firbase
        Firebase.setAndroidContext(this);
        //init sugar orm
//        SugarDb sugarDb = new SugarDb(getApplicationContext());
//        new File(sugarDb.getDB().getPath()).delete();
//        UserHistory.findById(UserHistory.class, (long) 1); // Perform this for each SugarRecord  model


        setContentView(R.layout.activity_main);

//        content = (LinearLayout)findViewById(R.id.speechText);

        textView = (TextView) findViewById(R.id.textView_1);

        // The following 3 lines are needed in every onCreate method of a ListeningActivity
        context = getApplicationContext(); // Needs to be set
        VoiceRecognitionListener.getInstance().setListener(this); // Here we set the current listener
        startListening(); // starts listening

        Button historyRedButton = (Button) findViewById(R.id.RedButton);
        historyRedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //for testing
                Intent goToNextActivity = new Intent(getApplicationContext(), TabActivity.class);
                startActivity(goToNextActivity);
            }
        });
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //for testing
                Firebase ref = new Firebase("https://gymguy.firebaseio.com");

                ref.unauth();

                //remove uid
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("uid", "-");
                editor.commit();

                Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goToNextActivity);
            }
        });
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //for testing
//                Firebase ref = new Firebase("https://gymguy.firebaseio.com");

//                ref.unauth();


//                Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(goToNextActivity);
//
                //  save on db
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                UserHistory newEntry = new UserHistory(count, timeStamp );
                newEntry.save();

                //  if highest, save on firebase
                ArrayList<UserHistory> UserHistories = (ArrayList<UserHistory>) UserHistory.listAll(UserHistory.class);

                int[] records = new int[UserHistories.size()];
                 // for (UserHistory uh : UserHistories) {
                for (int i = 0; i < UserHistories.size(); i++) {
                    records[i] = UserHistories.get(i).count;
                }

                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String  defaultValue = "-";
                String uid = sharedPref.getString("uid", defaultValue);

                Firebase ref = new Firebase("https://gymguy.firebaseio.com");
                Arrays.sort(records);
                int highest = records[records.length-1];
                System.out.println("the highest is : " + highest);
                if(highest<=count){
                    ref.child("users").child(uid).child("count").setValue(count);
                }


            }
        });

    }

    // Here is where the magic happens
    @Override
    public void processVoiceCommands(String... voiceCommands) {
//        content.removeAllViews();
        for (String command : voiceCommands) {
//            TextView txt = new TextView(getApplicationContext());
//            txt.setText(command);
//            txt.setTextSize(20);
//            txt.setTextColor(Color.BLACK);
//            txt.setGravity(Gravity.CENTER);
//            content.addView(txt);

//            if (command == "stop"){
//                listeningState = "stop";
//            }
//            if (command == "start"){
//                listeningState = "running";
//            }
//        }
            if (command.equals("stop")) {
                this.counting = false;
                System.out.println("the counting is turned off == " + this.counting);
            } else if (command.equals("start")) {
                this.counting = true;
                System.out.println("the counting is turned on == " + this.counting);
            } else if (command.equals("reset")) {
                this.count = -1;
                System.out.println("the counting is reset == " + this.counting);
            }
        }
        System.out.println("the listening state is : <"+this.counting+ "> with command : "+voiceCommands[0]);
        if(this.counting) {
            this.count++;
            textView.setText(Integer.toString(this.count));
        }
        restartListeningService();
    }
}

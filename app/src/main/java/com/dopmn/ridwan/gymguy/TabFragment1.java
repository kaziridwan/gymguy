package com.dopmn.ridwan.gymguy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TabFragment1 extends Fragment {
    static String testString = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View tabOneView  = inflater.inflate(R.layout.tab_fragment_1, container, false);
//        TextView textView = (TextView) tabOneView.findViewById(R.id.historyText);


        ArrayList <UserHistory> UserHistories = (ArrayList<UserHistory>) UserHistory.listAll(UserHistory.class);

        String[] values = new String[UserHistories.size()];
//        for (UserHistory uh : UserHistories) {
            for (int i = 0; i < UserHistories.size(); i++) {

            testString = testString+UserHistories.get(i).count+", ";
            values[i] = Integer.toString(UserHistories.get(i).count);
        }
//        textView.setText(testString);

        final ListView listView = (ListView) tabOneView.findViewById(R.id.historyListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity() ,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                // Show Alert
                Toast.makeText(getActivity().getApplicationContext(),
                        "clicked", Toast.LENGTH_LONG)
                        .show();

            }

        });
        return tabOneView;
    }
}

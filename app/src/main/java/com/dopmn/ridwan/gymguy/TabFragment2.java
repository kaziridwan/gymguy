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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.Collections;

public class TabFragment2 extends Fragment {

    ArrayAdapter<String> adapter;
    ListView listView;
    ArrayList<String> values;
    ArrayList<String> valuesProcessed;
    ArrayList<String> keysRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        values = new ArrayList<>();
        valuesProcessed = new ArrayList<>();
        keysRef = new ArrayList<>();
        View tabOneView  = inflater.inflate(R.layout.tab_fragment_2, container, false);
//        TextView textView = (TextView) tabOneView.findViewById(R.id.historyText);

        Firebase ref = new Firebase("https://gymguy.firebaseio.com/users");
        Query queryRef = ref.orderByChild("negativeCount").limitToLast(5);


        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                System.out.println("The " + snapshot.getKey() + " dinosaur's score is " + snapshot.getValue());
                //values.push() = " - "+snapshot.getVa;
                values.add(snapshot.getValue().toString());
                keysRef.add(snapshot.getKey().toString());

                updateListView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String changedKey = dataSnapshot.getKey();
                int changedIndex = keysRef.indexOf(changedKey);
                values.set(changedIndex, dataSnapshot.getValue().toString());

                updateListView();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String deletedKey = dataSnapshot.getKey();
                int removedIndex = keysRef.indexOf(deletedKey);
                keysRef.remove(removedIndex);
                values.remove(removedIndex);

                updateListView();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError e){}
            // ....
        });

        listView = (ListView) tabOneView.findViewById(R.id.leadersListView);

        adapter = new ArrayAdapter<String>(this.getActivity() ,
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
    private void updateListView(){
        adapter.notifyDataSetChanged();
        listView.invalidate();
    }
}
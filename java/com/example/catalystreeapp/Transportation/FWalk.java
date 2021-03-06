package com.example.catalystreeapp.Transportation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.catalystreeapp.Main.MainActivity;
import com.example.catalystreeapp.R;
import com.example.catalystreeapp.Users.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FWalk extends Fragment {

    public FWalk() {
    }

    WalkDataBaseAdapter walkDataBaseAdapter;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        inflating the view
        final View rootView = inflater.inflate(R.layout.fragment_walk, container, false);
//        declaring stuff, not sure why it has to be final
        final EditText editTextDistance, editTextMin;
        Button buttonSubmitWalk;

        editTextDistance = (EditText) rootView.findViewById(R.id.editTextWalkDistance);
        editTextMin = (EditText) rootView.findViewById(R.id.editTextWalkMin);

//      create new car database
        walkDataBaseAdapter = new WalkDataBaseAdapter(getActivity());
        walkDataBaseAdapter = walkDataBaseAdapter.open();

        session = new SessionManagement(getActivity().getApplicationContext());

//      identifying the button
        buttonSubmitWalk = (Button) rootView.findViewById(R.id.button_walk_submit);
        buttonSubmitWalk.setOnClickListener(new View.OnClickListener() {

                                            public void onClick(View v) {

//              take username entry from login / sign up form via method in FProfile
//                String currentUser = getSingleEntry();
                                                String username = session.getUsername();
//                convert distance to integer
                                                SimpleDateFormat formata = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
                                                String date = formata.format(new Date());

                                                int distance = 0;
                                                try {
                                                    distance = Integer.parseInt(editTextDistance.getText().toString());
                                                } catch (NumberFormatException nfe) {
                                                    System.out.println("Could not parse distance " + nfe);
                                                }
//                convert minutes to integer
                                                int time = 0;
                                                try {
                                                    time = Integer.parseInt(editTextMin.getText().toString());
                                                } catch (NumberFormatException nfe) {
                                                    System.out.println("Could not parse minutes" + nfe);
                                                }

                                                // Save the Data in Database
                                                walkDataBaseAdapter.insertEntry(username, date, distance, time);
                                                Toast.makeText(getActivity().getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
//                                              link back to input menu
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                intent.putExtra("caller", "Input");
                                                startActivity(intent);
                                            }
                                        }
        );
        return rootView;
    }
    }

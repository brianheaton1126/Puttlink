package com.golftronics.golfball.ble;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static double madePutts;
    public static double allMadePutts;
    public static double allMadePuttsMinDist;
    public static double allToday;
    public static int allOverMinDistPutts;
    public static int overMinDistPuttsDateRange;
    public static double madePuttsDateRange;
    public static int overMinDistPuttsByDate;
    public static int overMinDistPuttsDateRangeWithSlope;
    public static int madePuttsDateRangeWithSlope;
    public static int madePuttsDateRangeWithSlopeWithStimp;
    public static int overMinDistPuttsDateRangeWithSlopeWithStimp;
    public static int minDist;
    public static int maxDist;
    public static final long DAYMILLI = 86400000;
    public static final long WEEKMILLI = 604800000;
    public static final long MONTHMILLI = 2592000000L;
    public static final long YEARMILLI = 31556952000L;
    public long currentMilli;
    public static long timeRange;
    public static String slopeDirection;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private PuttViewModel puttViewModel;

    private ProgressBar prg;

    private ProgressBar prg1;

    private TextView makePercentageText;

    private TextView makePercentageTextAll;

    private EditText minSetDistText;

    private NumberPicker picker1;

    public int valuePicker1 = 1;

    public int valuePicker2= 0;

    public static double stimpMin;

    public static double stimpMax;

    private NumberPicker picker2;

    private Button todayButton;

    private Button weekButton;

    private Button monthButton;

    private TextView dateMenu;

    private TextView dateSelected;

    private TextView slopeSelect;

    private TextView stimpSelect;

    private TextView number_made;

    private TextView number_attempted;

    private TextView firebaseValue;

    private EditText enterSession;
    private EditText enterPercent;
    private Button storeValue;

    private int lastMadePuttFlag;

    public CharSequence signedInAccountFragment;

    public ListView friendList;

    public String playerSelected;

    List<String> friends = new ArrayList<String>();
    String [] startingList = {"Bob", "Mike", "Tome"};

    ArrayAdapter ad;

    String firebasePlayer;

    String friendInviteID;

    FirebaseDatabase firebaseDatabaseFragment;
    private DatabaseReference mPlayersFragment;
    private DatabaseReference mFriendsFragment;
    private DatabaseReference mPlayerStatus;
    private DatabaseReference mNewMatch;
    private DatabaseReference mFriendInvite;
    private DatabaseReference mInviteFrom;
    private FirebaseAuth mAuthFragment;

    private CoordinatorLayout coordinatorLayout;

    public FriendsFragment() {
        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        currentMilli = System.currentTimeMillis();

        timeRange = 0;

        slopeDirection = "All";

        stimpMin = 8;
        stimpMax = 13;


        mAuthFragment = FirebaseAuth.getInstance();
        firebaseDatabaseFragment = FirebaseDatabase.getInstance();
        mPlayersFragment =  FirebaseDatabase.getInstance().getReference("players");
        mFriendsFragment = FirebaseDatabase.getInstance().getReference("friends");
        //mDistance1Fragment = mPuttsFragment.child("3 feet");
        //mSession1Fragment = mPuttsFragment.child("all sessions");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        coordinatorLayout = view.findViewById(R.id.coordinator_layout);

        friendList = (ListView) view.findViewById(R.id.friend_list);

        friends = new ArrayList<String>(Arrays.asList(startingList));

        ad = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,friends);

        friendList.setAdapter(ad);

        timeRange = 0;

        slopeDirection = "All";

        valuePicker2 = 25;

        stimpMin = 8;
        stimpMax = 13;

        valuePicker1 = 1;



        puttViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(PuttViewModel.class);


        puttViewModel.getFragmentSignedInAccount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                signedInAccountFragment = string;

                firebasePlayer = string;

                fbDatabase();

                friends.add(firebasePlayer);

                ad.notifyDataSetChanged();
                
                //mPlayersFragment =  FirebaseDatabase.getInstance().getReference("players/" + firebasePlayer +"/friends");

                //setRoll(rollDistanceFragment);

                //mPlayersFragment =  FirebaseDatabase.getInstance().getReference("players/RedSquadLeader7/friends");
                //mFriendsFragment = mPlayersFragment.child("RedSquadLeader7");


            }
        });

        //mPlayersFragment =  FirebaseDatabase.getInstance().getReference("players/" + firebasePlayer+ "/friends");





















        /*todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeRange = (System.currentTimeMillis() - DAYMILLI);
                refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);

            }
        });


        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeRange = (System.currentTimeMillis() - WEEKMILLI);
                refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);

            }
        });


        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //timeRange = (System.currentTimeMillis() - MONTHMILLI);
                timeRange = 0;
                refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);

            }
        });

        */


        /*minDist = Integer.parseInt(minSetDistText.getText().toString());*/

        //String minSetDistString = minSetDistText.getText().toString();

        //minDist = Integer.parseInt(minSetDistString);

        /*minDist = Integer.parseInt(minSetDistTextString);*/

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showSnackbar();

                playerSelected = friends.get(position);

            }
        });



        return view;





        }


    public void showSnackbar(){

        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Type chat message", Snackbar.LENGTH_INDEFINITE).
                setAction("SEND", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message sent", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        startMatch();
                    }
                });

        //View snackView = snackbar.getView();

        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        TextView texview = layout.findViewById(com.google.android.material.R.id.snackbar_text);

        texview.setTextSize(18);

        texview.setText("Invite to New Game?");

        //texview.setVisibility(View.INVISIBLE);

        //View snackView = LayoutInflater.from(getContext()).inflate(R.layout.snacktemplate, null);
        //layout.addView(snackView,0);

        snackbar.show();

    }


    private void startMatch(){

        mNewMatch = FirebaseDatabase.getInstance().getReference("games/private/matches");

        String id = mNewMatch.push().getKey();
        mNewMatch.child(id).child("status").setValue("invited");
        mNewMatch.child(id).child("players").child(firebasePlayer).child("status").setValue("invite sent");
        mNewMatch.child(id).child("players").child(playerSelected).child("status").setValue("invited received");

        mFriendInvite = FirebaseDatabase.getInstance().getReference("players/" + playerSelected + "/game invites");
        String inviteID = mFriendInvite.push().getKey();
        mFriendInvite.child(inviteID).child("from").setValue(firebasePlayer);
        mFriendInvite.child(inviteID).child("status").setValue("pending");
    }



    private void fbDatabase(){

        mPlayersFragment =  FirebaseDatabase.getInstance().getReference("players/" + firebasePlayer+ "/friends"); // Read friend list from the database
        mPlayersFragment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //ArrayList<String> friendList = new ArrayList<>();
                friends.clear();

                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                        final String friendData = myDataSnapshot.getKey();
                        mPlayerStatus = FirebaseDatabase.getInstance().getReference("players/" + friendData + "/online");

                        mPlayerStatus.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                if (dataSnapshot.getValue().toString().equals("yes")) {

                                    friends.add(friendData);
                                    ad.notifyDataSetChanged();

                                }





                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w(TAG, "Failed to read value.", error.toException());
                            }
                        });


                        //friends.add(friendData);
                        //ad.notifyDataSetChanged();
                    }





                }}

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mFriendInvite = FirebaseDatabase.getInstance().getReference("players/" + firebasePlayer + "/game invites");

        mFriendInvite.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                friendInviteID = snapshot.child("from").getValue().toString();
                showInviteSnackbar();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });









        for (int i=0; i<friends.size(); i++) {

            mPlayerStatus = FirebaseDatabase.getInstance().getReference("players/" + friends.get(i)+ "/online");


        }



    }


    public void showInviteSnackbar(){
        /*
        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Game invite recieved from " + friendInviteID, Snackbar.LENGTH_INDEFINITE).
                setAction("ACCEPT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Starting game", Snackbar.LENGTH_SHORT);
                        snackbar1.show();

                    }
                });


        snackbar.show();
        */

        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Type chat message", Snackbar.LENGTH_INDEFINITE).
                setAction("", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message sent", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        startMatch();
                    }

                });


        //View snackView = snackbar.getView();

        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        TextView texview = layout.findViewById(com.google.android.material.R.id.snackbar_text);

        texview.setTextSize(18);

        texview.setText("Invite to New Game?");

        texview.setVisibility(View.INVISIBLE);

        View snackView = LayoutInflater.from(getContext()).inflate(R.layout.snacktemplate, null);
        layout.addView(snackView,0);

        TextView snackText = snackView.findViewById(R.id.text_invite);
        Button acceptBtn = snackView.findViewById(R.id.btnAccept);

            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar snackbar2 = Snackbar.make(coordinatorLayout, "Starting game", Snackbar.LENGTH_SHORT);

                    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar2.getView();

                    TextView texview = layout.findViewById(com.google.android.material.R.id.snackbar_text);

                    texview.setTextSize(18);
                    texview.setText("Starting Game");
                    snackbar2.show();

                }
            });

        Button declineBtn = snackView.findViewById(R.id.btnDecline);

            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar snackbar3 = Snackbar.make(coordinatorLayout, "Game declined", Snackbar.LENGTH_SHORT);

                    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar3.getView();

                    TextView texview = layout.findViewById(com.google.android.material.R.id.snackbar_text);

                    texview.setTextSize(18);
                    texview.setText("Game Declined");
                    snackbar3.show();

                }
            });


        snackText.setText("Game invite recieved from " + friendInviteID);

        snackbar.show();

    }


    /*
    private void showChart(ArrayList<BarEntry> dataVals) {
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("Session #1");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();

    }*/
    //for bar chart
    /*
    private void showChart(ArrayList<BarEntry> dataVals) {

        BarDataSet barDataSet = new BarDataSet(dataVals, "%");
        //barDataSet.setValues(dataVals);
        barDataSet.setLabel("Session #1");
        iBarDataSets.clear();
        iBarDataSets.add(barDataSet);
        barData = new BarData(iBarDataSets);
        barChart.clear();
        barChart.setData(barData);

        barChart.animateXY(3000, 3000);
        barChart.invalidate();

    }
*/





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);











        /*puttViewModel.getAllMadePutts(minDist, maxDist).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer allMadePuttsLive) {
                //prg.setProgress(makePercentageToday);

                allMadePuttsMinDist= allMadePuttsLive;
                refreshBarAggregate();
            }

        });




        puttViewModel.getAllPuttsToday().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer allPuttsToday) {
                //prg.setProgress(makePercentageToday);

                allToday=allPuttsToday;
                //refreshBar();
            }

        });


        puttViewModel.getAllPuttsOverMinDist(minDist, maxDist).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer allPuttsOverMinDist) {

                allOverMinDistPutts = allPuttsOverMinDist;
                //overMinDistPuttsDateRange = allPuttsOverMinDist;
                refreshBarAggregate();
            }
        });


        puttViewModel.getPuttsOverMinDistByDate(timeRange, minDist, maxDist).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDistByDate) {

                overMinDistPuttsByDate = puttsMinDistByDate;
                //overMinDistPuttsDateRange = puttsMinDistByDate;
                refreshBarAggregate();
            }
        });


        puttViewModel.getPuttsOverMinDistByDateWithSlope(timeRange, minDist, maxDist, slopeDirection).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDistByDate) {

                overMinDistPuttsDateRangeWithSlope = puttsMinDistByDate;
                 //overMinDistPuttsDateRange = puttsMinDistByDate;
                refreshBarAggregate();
            }
        });


        puttViewModel.getMadePuttsWithSlope(timeRange, minDist, maxDist, slopeDirection).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer madePuttsLive) {
                //prg.setProgress(makePercentageToday);

                madePuttsDateRangeWithSlope= madePuttsLive;
                refreshBar();
            }

        });


        */












        /*double makePercentageToday = (madeToday/allToday)*100;

        int makePercentageTodayRound = (int)(Math.round(makePercentageToday*100)/100D);



        String makePercentageTodayText = Integer.toString(makePercentageTodayRound);

        //makePercentageText.setText(makePercentageTodayText);

        makePercentageText.setText(makePercentageTodayText + "%");

        int progBar = (int) makePercentageTodayRound;

        prg.setProgress(progBar);*/

    }


























    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;



        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

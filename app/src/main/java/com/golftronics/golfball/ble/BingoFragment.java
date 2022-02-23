package com.golftronics.golfball.ble;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BingoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BingoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BingoFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String message = "message";

    public static double madeToday;
    public static double allToday;
    public static int overMinDistPutts;
    public static int minDist = 10;
    public static final long DAYMILLI = 86400000;
    public static final long WEEKMILLI = 604800000;
    public static final long MONTHMILLI = 2592000000L;
    public static final long YEARMILLI = 31556952000L;
    public long currentMilli;
    public long timeRange;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private BingoFragment.OnFragmentInteractionListener mListener;

    private PuttViewModel puttViewModel;

    private ProgressBar prg;

    private ProgressBar prg1;

    private TextView makePercentageText;

    private TextView makePercentageTextAll;

    private EditText minSetDistText;

    private NumberPicker picker1;

    private static int valuePicker1;

    private NumberPicker picker2;

    private Button todayButton;

    private Button weekButton;

    private Button monthButton;

    private SeekBar sBar;

    private View button;

    private TextView cup;

    private TextView cellOne;
    private TextView cellTwo;
    private TextView cellThree;
    private TextView cellFour;
    private TextView cellFive;
    private TextView cellSix;
    private TextView cellSeven;
    private TextView cellEight;
    private TextView cellNine;
    private TextView cellTen;
    private TextView cellEleven;
    private TextView cellTwelve;
    private TextView cellThirteen;
    private TextView cellFourteen;
    private TextView cellFifteen;
    private TextView cellSixteen;

    public static int getCellInt;
    private int cellTwoInt;
    private int cellThreeInt;
    private int cellFourInt;
    private int cellFiveInt;
    private int cellSixInt;
    private int cellSevenInt;
    private int cellEightInt;
    private int cellNineInt;
    private int cellTenInt;
    private int cellElevenInt;
    private int cellTwelveInt;
    private int cellThirteenInt;
    private int cellFourteenInt;
    private int cellFifteenInt;
    private int cellSixteenInt;

    public Double rollDistanceFragment;

    public int rollStoppedFlag;

    private TextView rollDistanceBingo;

    private String myValue;

    private RecyclerView leaderboardRecyclerView;
    private RecyclerView.Adapter leaderboardAdapter;
    private RecyclerView.LayoutManager leaderboardLayoutManager;

    ArrayList<LeaderboardItem> fragmentleaderboardlist = ControlActivity.leaderboardlist;

    public BingoFragment() {


        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static BingoFragment newInstance() {
        BingoFragment fragment3 = new BingoFragment();
        Bundle args = new Bundle();

        return fragment3;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);

            Bundle b = getArguments();
            mParam3 = b.getString(message);


            //leaderboardlist.add(new LeaderboardItem("1", "Jack Sparrow", "100"));
            //leaderboardlist.add(new LeaderboardItem("2", "Tiger Woods", "99"));
            //leaderboardlist.add(new LeaderboardItem("3", "Brian Heaton", "32"));








        }




        currentMilli = System.currentTimeMillis();

        timeRange = (System.currentTimeMillis() - DAYMILLI);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        View view3 = inflater.inflate(R.layout.bingo, container, false);




        rollDistanceBingo = (TextView)view3.findViewById(R.id.roll_distance_bingo);

        //leaderboardlist.add(new LeaderboardItem(1, "Jack Sparrow", 100));
        //leaderboardlist.add(new LeaderboardItem(2, "Tiger Woods", 99));
        //leaderboardlist.add(new LeaderboardItem(3, "Brian Heaton", 32));



        leaderboardRecyclerView = (RecyclerView)view3.findViewById(R.id.recyclerView);
        leaderboardRecyclerView.setHasFixedSize(true);
        leaderboardLayoutManager = new LinearLayoutManager(getContext());
        leaderboardAdapter = new LeaderboardAdapter(fragmentleaderboardlist);

        leaderboardRecyclerView.setLayoutManager(leaderboardLayoutManager);
        leaderboardRecyclerView.setAdapter(leaderboardAdapter);











        return view3;


    }

     /*public void updateRollDistance(CharSequence rollDistanceFragment){

        rollDistanceBingo.setText(rollDistanceFragment);

     }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        leaderboardlist.add(new LeaderboardItem("1", "Jack Sparrow", "100"));
        leaderboardlist.add(new LeaderboardItem("2", "Tiger Woods", "99"));
        leaderboardlist.add(new LeaderboardItem("3", "Brian Heaton", "80"));
        leaderboardlist.add(new LeaderboardItem("4", "Luke Skywalker", "75"));
        leaderboardlist.add(new LeaderboardItem("5", "Puppy", "65"));
        leaderboardlist.add(new LeaderboardItem("6", "Donald Duck", "0"));

        */

        // update 3-23:  to observe the roll distance live data from view model

        puttViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(PuttViewModel.class);


        puttViewModel.getFragmentRollDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                rollDistanceFragment = value;
                setRoll(rollDistanceFragment);

            }
        });

        puttViewModel.getFragmentPuttStoppedFlag().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                rollStoppedFlag = integer;
                setRollStoppedColor(rollStoppedFlag);
            }
        });


/*
        puttViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(PuttViewModel.class);

        puttViewModel.getMadePuttsToday().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer madePuttsToday) {
                //prg.setProgress(makePercentageToday);

                 madeToday= madePuttsToday;
            }

        });

        puttViewModel.getAllPuttsToday().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer allPuttsToday) {
                //prg.setProgress(makePercentageToday);

                allToday=allPuttsToday;
                refreshBar();
            }

        });


        puttViewModel.getPuttsOverMinDist(minDist, timeRange).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDist) {

                overMinDistPutts = puttsMinDist;
                refreshBar();
            }
        });



        double makePercentageToday = (madeToday/allToday)*100;

        int makePercentageTodayRound = (int)(Math.round(makePercentageToday*100)/100D);



        String makePercentageTodayText = Integer.toString(makePercentageTodayRound);

        //makePercentageText.setText(makePercentageTodayText);

        makePercentageText.setText(makePercentageTodayText + "%");

        int progBar = (int) makePercentageTodayRound;

        prg.setProgress(progBar);

    }

    public void refreshBar(){

        double makePercentageToday = (madeToday/allToday)*100;

        int makePercentageTodayRound = (int)(Math.round(makePercentageToday*100)/100D);



        String makePercentageTodayText = Integer.toString(makePercentageTodayRound);

        //makePercentageText.setText(makePercentageTodayText);

        //String makePercentageTodayText = Integer.toString(overMinDistPutts);

        makePercentageText.setText(makePercentageTodayText + "%");



        int progBar = (int) makePercentageTodayRound;


    }

    public void refreshBarAggregate(){

       String makePercentageAllText = Integer.toString(overMinDistPutts);


    }




    public void refreshQuery(int picker){

        puttViewModel.getPuttsOverMinDist(picker, timeRange).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDist) {

                overMinDistPutts = puttsMinDist;
                refreshBarAggregate();
            }
        });

    }

    public void refreshQueryDateRange(int picker, long dateRange) {

        puttViewModel.getPuttsOverMinDist(picker, dateRange).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDist) {

                overMinDistPutts = puttsMinDist;
                refreshBarAggregate();
            }
        });
*/




    }

    public void setRoll(Double distance){


        rollDistanceBingo.setText(Double.toString(distance));

    }

    public void setRollStoppedColor(int flag){

        if(flag ==1 ) {
            rollDistanceBingo.setTextColor(Color.parseColor("#FAE104"));
        }

        else {

            rollDistanceBingo.setTextColor(Color.parseColor("#000000"));
        }
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
        if (context instanceof BingoFragment.OnFragmentInteractionListener) {
            mListener = (BingoFragment.OnFragmentInteractionListener) context;



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

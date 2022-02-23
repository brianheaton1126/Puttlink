package com.golftronics.golfball.ble;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShotClockFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShotClockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShotClockFragment extends Fragment {



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

    private ShotClockFragment.OnFragmentInteractionListener mListener;

    private PuttViewModel puttViewModel;

    private ProgressBar shotClockBar;

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

    public int puttMadeDistanceFragment;

    public int rollStoppedFlag;

    private TextView rollDistanceBingo;

    private String myValue;

    int timeShotClock;

    private TextView countdownTime;

    private TextView highScoreReset;

    public int counter;

    private Button startCounter;

    private TextView currentScore;

    private TextView highScoreText;

    public int previousScore;

    public int newScore;

    public int myHighScore;



    public ShotClockFragment() {


        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static ShotClockFragment newInstance() {
        ShotClockFragment fragment4 = new ShotClockFragment();
        Bundle args = new Bundle();

        return fragment4;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);

            Bundle b = getArguments();
            mParam3 = b.getString(message);
        }

        previousScore = 0;



        currentMilli = System.currentTimeMillis();

        timeRange = (System.currentTimeMillis() - DAYMILLI);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        View view4 = inflater.inflate(R.layout.shotclock, container, false);


        shotClockBar = (ProgressBar)view4.findViewById(R.id.shot_clock_bar);

        startCounter = (Button) view4.findViewById(R.id.countdown_start);

        shotClockBar.setMax(60);
        //shotClockBar.setMin(0);

        currentScore = (TextView)view4.findViewById(R.id.shot_clock_current_score);

        highScoreText = (TextView)view4.findViewById(R.id.shot_clock_high_score);

        highScoreReset = (TextView)view4.findViewById(R.id.high_score_reset);

        myHighScore = readHighScore();
        highScoreText.setText(Integer.toString(myHighScore));



        countdownTime = (TextView)view4.findViewById(R.id.countdown_text);

        startCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view4) {

                startCounter.setVisibility(view4.INVISIBLE);

                new CountDownTimer (60000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        if((millisUntilFinished/1000) >= 10) {
                            countdownTime.setText(":" + String.valueOf(millisUntilFinished / 1000));
                        }

                        else{
                            countdownTime.setText(":0" + String.valueOf(millisUntilFinished / 1000));
                        }
                        counter++;

                        shotClockBar.setProgress(counter);

                    }




                    @Override
                    public void onFinish() {
                        countdownTime.setText(":00");

                        startCounter.setVisibility(view4.VISIBLE);

                        shotClockBar.setProgress(60);

                        counter = 0;

                    }




                }.start();





            }


        });

        highScoreReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view4) {

                setNewHighScore(0);

            }

        });








                return view4;


    }

     /*public void updateRollDistance(CharSequence rollDistanceFragment){

        rollDistanceBingo.setText(rollDistanceFragment);

     }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // update 3-23:  to observe the roll distance live data from view model

        puttViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(PuttViewModel.class);


        puttViewModel.getFragmentRollDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                rollDistanceFragment = value;
                //setRoll(rollDistanceFragment);

            }
        });

        puttViewModel.getFragmentPuttStoppedFlag().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                rollStoppedFlag = integer;
                //setRollStoppedColor(rollStoppedFlag);
            }
        });

        puttViewModel.getFragmentPuttMadeDistance().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                puttMadeDistanceFragment = integer;
                countUpScore(puttMadeDistanceFragment);


            }
        });


        /*puttViewModel.getShotClockHighScore().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                myHighScore = integer;
                //highScoreText.setText(Integer.toString(myHighScore));


            }
        });*/


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

    public void setRoll(CharSequence distance){


       // rollDistanceBingo.setText(distance);

    }

    public void setRollStoppedColor(int flag){

        if(flag ==1 ) {
           // rollDistanceBingo.setTextColor(Color.parseColor("#FAE104"));
        }

        else {

            //rollDistanceBingo.setTextColor(Color.parseColor("#000000"));
        }
    }


    public void countUpScore(int madeDistance){
        newScore = previousScore + madeDistance;
        ValueAnimator anim = ValueAnimator.ofInt(previousScore, newScore);
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                String current = (valueAnimator.getAnimatedValue().toString());
                currentScore.setText(current);
            }
        });

        anim.start();

        if (newScore > myHighScore){



            highScoreText.setText(Integer.toString(newScore));

            myHighScore = newScore;

            setNewHighScore(myHighScore);
        }
        previousScore = newScore;
    }

    public void setNewHighScore(int newHighScore){

        SharedPreferences sharedPref = getActivity().getSharedPreferences("shotclock", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("high_score_key", newHighScore);
        editor.apply();
        highScoreText.setText(Integer.toString(newHighScore));
        puttViewModel.setShotClockHighScore(newHighScore);



    }


    public int readHighScore(){

        SharedPreferences sharedPref = getActivity().getSharedPreferences("shotclock", getActivity().MODE_PRIVATE);
        int highScore = sharedPref.getInt("high_score_key", 0);

        return highScore;




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
        if (context instanceof ShotClockFragment.OnFragmentInteractionListener) {
            mListener = (ShotClockFragment.OnFragmentInteractionListener) context;



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

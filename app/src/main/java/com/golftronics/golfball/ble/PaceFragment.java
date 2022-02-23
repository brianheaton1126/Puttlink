package com.golftronics.golfball.ble;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaceFragment extends Fragment {



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

    private PaceFragment.OnFragmentInteractionListener mListener;

    private PuttViewModel puttViewModel;

    private ProgressBar shotClockBar;

    private ProgressBar prg1;

    private TextView makePercentageText;

    private TextView makePercentageTextAll;

    private EditText minSetDistText;

    private TextView lastMadeVelocity;

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

    public int fragmentVelocity = 0;

    public int rollStoppedFlag;

    private TextView rollDistanceBingo;

    private String myValue;

    int timeShotClock;

    private TextView countdownTime;

    public int counter;

    private Button startCounter;

    private String madeVelocity;

    private TextView paceArrow;

    private float averageVelocityMade;

    private TextView averageVelocity;

    private String averageVelocityText;

    private TextView continuationDistance;

    private SeekBar weight;

    private TextView weightbar;

    public int animWeightProgress;

    public int animWeightBarProgress;

    private int weightPos;

    private float weightBarRot;



    public PaceFragment() {


        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static PaceFragment newInstance() {
        PaceFragment fragment5 = new PaceFragment();
        Bundle args = new Bundle();

        return fragment5;
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




        currentMilli = System.currentTimeMillis();

        timeRange = (System.currentTimeMillis() - DAYMILLI);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        View view4 = inflater.inflate(R.layout.putt_weight, container, false);

        lastMadeVelocity = (TextView)view4.findViewById(R.id.last_made_velocity);

        paceArrow = (TextView)view4.findViewById(R.id.pace_arrow);

        averageVelocity = (TextView)view4.findViewById(R.id.average_velocity_made);

        continuationDistance = (TextView)view4.findViewById(R.id.continuation_distance);

        weight = (SeekBar)view4.findViewById(R.id.weight);

        weightbar = (TextView)view4.findViewById(R.id.weightbar);




        //lastMadeVelocity.setY(100);








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


        puttViewModel.getAverageVelocityPuttsMade().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float avgVel) {
                averageVelocityMade = Math.round(avgVel * 100) / 100F;
                averageVelocityText = String.valueOf(averageVelocityMade);
                averageVelocity.setText(averageVelocityText + " ft/sec");

                Float avgVelMeter = (averageVelocityMade/3.28F);

                Float contDistMeter = (avgVelMeter*avgVelMeter)/0.911F;

                Float contDistInch = Math.round((contDistMeter * 3.28F * 12F)*1)/1F;

                int test =  Math.round(contDistInch);

                String continuationDistanceText = String.valueOf(test);

                continuationDistance.setText(continuationDistanceText + " inches");

            }
        });







        puttViewModel.getFragmentVelocityEnd().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double velocity) {
                if (velocity != null) {
                    fragmentVelocity = (int) Math.round(velocity);
                    String newVelocity = Integer.toString(fragmentVelocity);
                    lastMadeVelocity.setText(newVelocity + "ft/sec");
                    weightPos = weight.getProgress();
                    weightBarRot = weightbar.getRotation();

                    if (fragmentVelocity < 1) {
                        //weight.setProgress(10);


                        ValueAnimator anim = ValueAnimator.ofInt((int)(weightPos), 15);
                        anim.setDuration(1000);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                animWeightProgress = (Integer) animation.getAnimatedValue();
                                weight.setProgress(animWeightProgress);
                            }
                        });

                        ValueAnimator anim1 = ValueAnimator.ofInt((int)(weightBarRot), -20);
                        anim1.setDuration(1000);
                        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation1) {
                                animWeightBarProgress = (Integer) animation1.getAnimatedValue();
                                weightbar.setRotation(animWeightBarProgress);
                                weight.setRotation(animWeightBarProgress);
                            }
                        });

                        anim.start();
                        anim1.start();




                        paceArrow.setX(50);
                        lastMadeVelocity.setX(50);
                        //weight.setRotation(-20);
                        //weightbar.setRotation(-20);
                    } else if (fragmentVelocity < 2) {

                        ValueAnimator anim = ValueAnimator.ofInt((int)(weightPos), 25);
                        anim.setDuration(1000);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                animWeightProgress = (Integer) animation.getAnimatedValue();
                                weight.setProgress(animWeightProgress);
                            }
                        });



                        ValueAnimator anim1 = ValueAnimator.ofInt((int)(weightBarRot), -10);
                        anim1.setDuration(1000);
                        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation1) {
                                animWeightBarProgress = (Integer) animation1.getAnimatedValue();
                                weightbar.setRotation(animWeightBarProgress);
                                weight.setRotation(animWeightBarProgress);
                            }
                        });

                        anim.start();
                        anim1.start();




                        //weight.setRotation(-10);
                        //weight.setProgress(25);
                        lastMadeVelocity.setX(150);
                        paceArrow.setX(150);
                        //weightbar.setRotation(-10);
                    } else if (fragmentVelocity < 3) {

                        ValueAnimator anim = ValueAnimator.ofInt((weightPos), 50);
                        anim.setDuration(1000);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                animWeightProgress = (Integer) animation.getAnimatedValue();
                                weight.setProgress(animWeightProgress);

                            }
                        });



                        ValueAnimator anim1 = ValueAnimator.ofInt((int)(weightBarRot), 0);
                        anim1.setDuration(1000);
                        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation1) {
                                animWeightBarProgress = (Integer) animation1.getAnimatedValue();
                                weightbar.setRotation(animWeightBarProgress);
                                weight.setRotation(animWeightBarProgress);
                            }
                        });

                        anim.start();
                        anim1.start();



                        //weight.setProgress(50);

                        lastMadeVelocity.setX(250);
                        paceArrow.setX(250);
                        //weight.setRotation(0);
                        //weightbar.setRotation(0);
                    } else if (fragmentVelocity < 4) {

                        ValueAnimator anim = ValueAnimator.ofInt((weightPos), 75);
                        anim.setDuration(1000);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                animWeightProgress = (Integer) animation.getAnimatedValue();
                                weight.setProgress(animWeightProgress);
                            }
                        });



                        ValueAnimator anim1 = ValueAnimator.ofInt((int)(weightBarRot), 10);
                        anim1.setDuration(1000);
                        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation1) {
                                animWeightBarProgress = (Integer) animation1.getAnimatedValue();
                                weightbar.setRotation(animWeightBarProgress);
                                weight.setRotation(animWeightBarProgress);
                            }
                        });

                        anim.start();
                        anim1.start();





                        //weight.setProgress(75);

                        lastMadeVelocity.setX(350);
                        paceArrow.setX(350);
                        //weight.setRotation(10);
                        //weightbar.setRotation(10);
                    } else if (fragmentVelocity < 5) {

                        ValueAnimator anim = ValueAnimator.ofInt((weightPos), 85);
                        anim.setDuration(1000);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                animWeightProgress = (Integer) animation.getAnimatedValue();
                                weight.setProgress(animWeightProgress);
                            }
                        });



                        ValueAnimator anim1 = ValueAnimator.ofInt((int)(weightBarRot), 20);
                        anim1.setDuration(1000);
                        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation1) {
                                animWeightBarProgress = (Integer) animation1.getAnimatedValue();
                                weightbar.setRotation(animWeightBarProgress);
                                weight.setRotation(animWeightBarProgress);
                            }
                        });

                        anim.start();
                        anim1.start();

                        lastMadeVelocity.setX(400);
                        paceArrow.setX(400);

                    }










                    //setVelocity(fragmentVelocity);
                }
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

   public void setVelocity(int madevelocity) {


        String madeVelocityText = Integer.toString(madevelocity);

            lastMadeVelocity.setText(madeVelocityText);
        }

    /*

        if (velocity < 1) {
            lastMadeVelocity.setX(100);
        } else if (velocity < 2) {
            lastMadeVelocity.setX(200);
        } else if (velocity < 3) {
            lastMadeVelocity.setX(300);
        } else if (velocity < 4) {
            lastMadeVelocity.setX(400);
        } else if (velocity < 5) {
            lastMadeVelocity.setX(500);
        }


    }*/
    /*public void setRollStoppedColor(int flag){

        if(flag ==1 ) {
           // rollDistanceBingo.setTextColor(Color.parseColor("#FAE104"));
        }

        else {

            //rollDistanceBingo.setTextColor(Color.parseColor("#000000"));
        }
    }*/


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PaceFragment.OnFragmentInteractionListener) {
            mListener = (PaceFragment.OnFragmentInteractionListener) context;



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

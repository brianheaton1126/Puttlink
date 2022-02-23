package com.golftronics.golfball.ble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment implements View.OnClickListener {



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
    public static final long  YEARMILLI = 31556952000L;
    public long currentMilli;
    public long timeRange;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private StatsFragment.OnFragmentInteractionListener mListener;

    private PuttViewModel puttViewModel;

    private ProgressBar shotClockBar;

    private ProgressBar prg1;

    private TextView makePercentageText;

    private TextView makePercentageTextAll;

    private EditText minSetDistText;

    private TextView lastMadeVelocity;

    private NumberPicker picker1;

    private static int valuePicker1;

    private static int valuePicker2;

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
    public Boolean puttMadeFlag = false;

    public int fragmentVelocity = 0;
    public int fragmentPuttLaunchAngle = 0;
    public String fragmentPuttLaunchDirection = "";

    public int rollStoppedFlag;

    private TextView rollDistancePractice;
    private TextView rollDistancePracticeExpanded;
    private TextView entrySpeedPractice;
    private TextView entrySpeedPracticeExpanded;
    private TextView launchAngle;
    private TextView launchDirection;
    private TextView pureRollPercentage;
    private TextView allUserAvg;
    private String pureRoll;
    private TextView timeMaxtoStop;
    private TextView pureRollPercent;
    public Double fragmentAccelYAverage;

    public int makePercDistSel;

    public double sumMade = 0;
    public double sumAttempt = 0;
    public double thisSessionMade = 0;
    public double thisSessionAttempt = 0;
    public int lastSession = 0;
    public double myAverage;
    public double sessionAverage;
    public int myAverageInt;
    public int allAverageInt;
    public int sessionAverageInt;
    public String fbDistRange;
    public String fbDistSessionID;

    public Boolean newSession;

    public long numSessions;
    public long numDistSessions;

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

    private NumberPicker picker4;

    public double targetDistance=1;

    public int targetSelectDistance;

    private Animator currentAnimator;

    private int shortAnimationDuration;

    private View view4;

    private int resID;
    private int card;
    public int cardpressed;

    private long startTime;


    private CardView cardOne;
    private ConstraintLayout cardBig;
    private ConstraintLayout cardSmall;
    private LinearLayout cardOneExpanded;
    public View cardExpanded;
    private LinearLayout cardTwoExpanded;
    private LinearLayout cardThreeExpanded;
    private LinearLayout cardFourExpanded;
    private LinearLayout cardFiveExpanded;
    private LinearLayout cardSixExpanded;
    public LinearLayout cardNum;
    private ImageView closeButton;
    private View ThumbView1;
    private Button cardOneButton;
    private TextView distanceGoal;
    public Button makePercDist;
    public Button selectDist;
    public String distanceString;

    public TextView myMakePercentageAvg;
    public TextView myMakePercentageSession;



    public CharSequence signedInAccountFragment;
    String firebasePlayer;

    private CombinedChart distancechart;


        //data.setData(generateLineData());
        //data.setData(generateBarData());
    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    BarChart barChart;
    BarChart distBarChart;
    BarDataSet barDataSet;
    BarDataSet distBarDataSet;
    ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();
    ArrayList<IBarDataSet> iDistBarDataSets = new ArrayList<>();


    BarData barData;
    BarData distBarData;


    FirebaseDatabase firebaseDatabaseFragment;
    private DatabaseReference mSession1Fragment, mSession2Fragment, mPutts, mScore, mSession, mDistance1,
    mDistance2, mDistance3, mDistance4, mDistance5, mDistance6,
    mDistance7, mDistance8, mDistance9, mDistance10, mDistance11,
    mDistance12, mDistance13, mDistance14, mDistance15, mDistance16,
    mDistance17, mDistance18, mDistance19, mDistance20, mDistance21,
    mDistance22, mDistance23, mAttempts1, mAttempts2, mAttempts3,
    mAttempts4, mAttempts5, mAttempts6, mAttempts7, mAttempts8,
    mAttempts9, mAttempts10, mAttempts11, mAttempts12, mAttempts13,
    mAttempts14, mAttempts15, mAttempts16, mAttempts17, mAttempts18,
    mAttempts19, mAttempts20, mAttempts21, mAttempts22, mAttempts23,
    mMakes1, mMakes2, mMakes3, mMakes4, mMakes5, mMakes6, mMakes7,
    mMakes8, mMakes9, mMakes10, mMakes11, mMakes12, mMakes13, mMakes14,
    mMakes15, mMakes16, mMakes17, mMakes18, mMakes19, mMakes20, mMakes21,
    mMakes22, mMakes23;
    private DatabaseReference mDistance1Fragment;
    private DatabaseReference mMakes1Fragment;
    private DatabaseReference mPlayerMakePercentDistRef;
    private DatabaseReference mPlayerRef;
    private DatabaseReference mPlayerStatRef, mPlayerSessionRef;
    private DatabaseReference mPlayerMakePercentRef;
    private DatabaseReference mPuttsFragment;
    private FirebaseAuth mAuthFragment;
    private DatabaseReference mPuttsMakeAvg;
    private DatabaseReference mPlayerDistRef, mPlayerSessionDistRef, mPlayerSessionDistRef2,
            mPlayerDistChartRef;

    public String makeSessionID1, makeSessionID2, makeSessionID3, makeSessionID4, makeSessionID5,
                makeSessionID6, distanceSessionID1, distanceSessionID2, distanceSessionID3,
                distanceSessionID4, distanceSessionID5, distanceSessionID6, distanceSessionID7,
                distanceSessionID8;

    private String key;

    ArrayList<BarEntry> dataVals;
    ArrayList<BarEntry> dataVals1;

    int[] colorClassArray = new int[]{Color.BLUE, Color.BLACK, Color.RED};

    public int distMin;
    public int distMax;
    public int distGoal;

    public float distRangeMin;
    public float distRangeMax;



    public StatsFragment() {


        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static StatsFragment newInstance() {
        StatsFragment fragment5 = new StatsFragment();
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

        mAuthFragment = FirebaseAuth.getInstance();
        firebaseDatabaseFragment = FirebaseDatabase.getInstance();

        fbDatabase();

        mPuttsFragment =  FirebaseDatabase.getInstance().getReference("all putts");
        mDistance1Fragment = mPuttsFragment.child("3 feet");
        mSession1Fragment = mPuttsFragment.child("all sessions");
        mSession2Fragment = mPuttsFragment.child("test sessions");
        mPutts =  FirebaseDatabase.getInstance().getReference("all putts");
        mDistance1 = mPutts.child("3 to 5 feet");
        mDistance4 = mPutts.child("6 to 8 feet");
        mDistance7 = mPutts.child("9 to 11 feet");
        mDistance10 = mPutts.child("12 to 15 feet");
        mDistance14 = mPutts.child("16 to 20 feet");
        mDistance19 = mPutts.child("21 to 25 feet");
        mAttempts1 = mDistance1.child("attempted");

        mAttempts4 = mDistance4.child("attempted");

        mAttempts7 = mDistance7.child("attempted");

        mAttempts10 = mDistance10.child("attempted");

        mAttempts14 = mDistance14.child("attempted");

        mAttempts19 = mDistance19.child("attempted");


        mMakes1 = mDistance1.child("made");

        mMakes4 = mDistance4.child("made");

        mMakes7 = mDistance7.child("made");

        mMakes10 = mDistance10.child("made");

        mMakes14 = mDistance14.child("made");

        mMakes19 = mDistance19.child("made");







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        view4 = inflater.inflate(R.layout.practice_layout, container, false);

        cardOne = view4.findViewById(R.id.card_one);
        cardBig = view4.findViewById(R.id.cardbig);
        cardSmall = view4.findViewById(R.id.cardsmall);
        closeButton = view4.findViewById(R.id.close_button);
        cardOneExpanded = view4.findViewById(R.id.cardoneexpanded);
        cardTwoExpanded = view4.findViewById(R.id.cardtwoexpanded);
        cardThreeExpanded = view4.findViewById(R.id.cardthreeexpanded);
        cardFourExpanded = view4.findViewById(R.id.cardfourexpanded);
        cardFiveExpanded = view4.findViewById(R.id.cardfiveexpanded);
        cardSixExpanded = view4.findViewById(R.id.cardsixexpanded);



        view4 = inflater.inflate(R.layout.statistics_layout, container, false);

        distanceGoal = (TextView)view4.findViewById(R.id.distanceGoal);

        myMakePercentageAvg = view4.findViewById(R.id.makepercentageaverage);
        myMakePercentageSession = view4.findViewById(R.id.makepercentagesession);
        allUserAvg = view4.findViewById(R.id.alluseraverage);




        resID = view4.getId();

        //picker4 = (NumberPicker)view4.findViewById(R.id.number_picker3);
        //picker4.setMaxValue(25);
        //picker4.setMinValue(1);
        //picker4.setValue(1);

        /*

        picker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                targetSelectDistance = picker4.getValue();
                //minDist = valuePicker1;

                puttViewModel.setFragmentTargetDistance(targetSelectDistance);
                distanceGoal.setText(Integer.toString(targetSelectDistance));
                picker4.setWrapSelectorWheel(true);
            }
        });



         */
        makePercDist = (Button)view4.findViewById(R.id.make_perc_dist);

        makePercDist.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {


                                                showMenu(view);




                                            }
                                        }
        );


        selectDist = (Button)view4.findViewById(R.id.card2_select_dist);

        selectDist.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                distanceGoal.setText("");
                                                distanceGoal.setTextSize(72);

                                                showMenu(view);




                                            }
                                        }
        );


        //registerForContextMenu(makePercDist);





        rollDistancePractice = (TextView)view4.findViewById(R.id.roll_distance_practice);
        rollDistancePracticeExpanded = (TextView)view4.findViewById(R.id.distance_personal_actual);
        //entrySpeedPractice = (TextView)view4.findViewById(R.id.entry_speed_practice);
        //entrySpeedPracticeExpanded = (TextView)view4.findViewById(R.id.entry_speed_practice_expanded);
        //launchAngle = (TextView)view4.findViewById(R.id.putt_launch_angle);
        //launchDirection = (TextView)view4.findViewById(R.id.putt_launch_direction);
        pureRollPercentage = (TextView)view4.findViewById(R.id.pure_roll_practice);
        //timeMaxtoStop = (TextView)view4.findViewById(R.id.time_max_velocity_to_stop);
        //pureRollPercent = (TextView)view4.findViewById(R.id.pureroll);
        barChart = view4.findViewById(R.id.make_bar_chart);
        distancechart = view4.findViewById(R.id.distance_bar_chart);
        //lineChart = view4.findViewById(R.id.make_line_chart);

        CombinedData distanceData = new CombinedData();

        distancechart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });



        /*
        // Read from the database to make bar chart
        mSession1Fragment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                ArrayList<BarEntry> dataVals = new ArrayList<>();

                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                        SessionData sessionData = myDataSnapshot.getValue(SessionData.class);
                        dataVals.add(new BarEntry(sessionData.getsessionNum(), sessionData.getmakeFB()));
                    }

                    showChart(dataVals);

                } else {
                    barChart.clear();
                    barChart.invalidate();

                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/









        final View thumb1View = view4.findViewById(R.id.cardonebutton);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //expandCard();

                cardpressed = 1;
                zoomImageFromThumb(thumb1View, R.drawable.button2, cardOneExpanded);
            }
        });


        final View thumb2View = view4.findViewById(R.id.cardtwobutton);
        thumb2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //expandCard();

                cardpressed = 2;
                zoomImageFromThumb(thumb2View, R.drawable.button2, cardTwoExpanded);
            }
        });



        final View thumb3View = view4.findViewById(R.id.cardthreebutton);
        thumb3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //expandCard();
                cardpressed=3;

                zoomImageFromThumb(thumb3View, R.drawable.button2, cardThreeExpanded);
            }
        });


        final View thumb4View = view4.findViewById(R.id.cardfourbutton);
        thumb4View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //expandCard();

                cardpressed = 4;
                zoomImageFromThumb(thumb4View, R.drawable.button2, cardFourExpanded);
            }
        });


        final View thumb5View = view4.findViewById(R.id.cardfivebutton);
        thumb5View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //expandCard();

                cardpressed = 5;
                zoomImageFromThumb(thumb5View, R.drawable.button2, cardFiveExpanded);
            }
        });


        final View thumb6View = view4.findViewById(R.id.cardsixbutton);
        thumb6View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //expandCard();

                cardpressed = 6;
                zoomImageFromThumb(thumb6View, R.drawable.button2, cardSixExpanded);
            }
        });




        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        //lastMadeVelocity.setY(100);











        return view4;


    }

     /*public void updateRollDistance(CharSequence rollDistanceFragment){

        rollDistanceBingo.setText(rollDistanceFragment);

     }*/

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId()==R.id.make_perc_dist) {


            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.range_filter_menu, menu);

        }

    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.three_five:
                //dateSelected.setText("Today");
                timeRange = System.currentTimeMillis()-DAYMILLI;
                valuePicker1 = 3;
                valuePicker2 = 5;
                //refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                break;
            //return true;
            case R.id.six_eight:
                //dateSelected.setText("This Week");
                timeRange = System.currentTimeMillis()-WEEKMILLI;
                valuePicker1 = 6;
                valuePicker2 = 8;
                //refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                break;
            //return true;
            case R.id.nine_eleven:
                //dateSelected.setText("This Month");
                timeRange = System.currentTimeMillis()-MONTHMILLI;
                valuePicker1 = 9;
                valuePicker2 = 11;
                //refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                break;
            //return true;
            case R.id.twelve_fifteen:
                //dateSelected.setText("This Year");
                timeRange = System.currentTimeMillis()-YEARMILLI;
                valuePicker1 = 12;
                valuePicker2 = 15;
                //refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                break;
            //return true;
            case R.id.sixteen_twenty:
                //slopeSelect.setText("All");
                valuePicker1 = 16;
                valuePicker2 = 20;
                // refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                //return true;
            case R.id.dist_fifteen_twenty:
                selectDist.setText("15 to 20 FT");
                valuePicker1 = 15;
                valuePicker2 = 20;
                //refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                break;
            //return true;
            case R.id.twentyone_twentyfive:
                selectDist.setText("21 to 25 FT");
                valuePicker1 = 21;
                valuePicker2 = 25;
                //refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                break;
            //return true;

            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // update 3-23:  to observe the roll distance live data from view model

        puttViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(PuttViewModel.class);


        puttViewModel.getFragmentSignedInAccount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                signedInAccountFragment = string;

                firebasePlayer = string;
                fbDatabase();
            }
        });

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

                //setRollStoppedColor(rollStoppedFlag);
            }
        });


        puttViewModel.getFragmentPuttMadeFlag().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                puttMadeFlag = flag;
                if (puttMadeFlag.equals(true)){
                recordMakeEvent(rollDistanceFragment);}
            }
        });


        puttViewModel.getFragmentPuttAttempt().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (integer == 1){


                recordAttemptEvent(rollDistanceFragment);}
            }
        });










        puttViewModel.getFragmentVelocityEnd().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double velocity) {
                if (velocity != null) {
                    fragmentVelocity = (int) Math.round(velocity);
                    String newVelocity = Integer.toString(fragmentVelocity);
                    setVelocity(newVelocity);
                }
            }
        });


        puttViewModel.getFragmentAccelYAverage().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double angle) {
                if (angle != null) {
                    fragmentAccelYAverage = angle;

                    if (rollStoppedFlag == 1){

                        String value = Double.toString( Math.round(angle*100)/100D);


                        setAngle(value);}
                }
            }
        });



        /*

        puttViewModel.getFragmentPuttLaunchAngle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer angle) {
                if (angle != null) {
                    fragmentPuttLaunchAngle = angle;
                    String newAngle = Integer.toString(fragmentPuttLaunchAngle);
                    setAngle(newAngle);
                }
            }
        });


         */

        puttViewModel.getFragmentPuttLaunchDirection().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence direction) {
                if (direction != null) {
                    fragmentPuttLaunchDirection = (String)(direction);
                    String newDirection = fragmentPuttLaunchDirection;
                    setDirection(newDirection);
                }
            }
        });



        puttViewModel.getFragmentPureRollPercentage().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long percentage) {
                if (percentage != null) {
                    pureRoll = Long.toString(percentage);
                    if (rollStoppedFlag == 1){
                    setPureRoll(pureRoll);}
                }
            }
        });



        puttViewModel.getFragmentTimeMaxtoStop().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long time) {
                if (time != null) {

                    if(rollStoppedFlag == 1){
                    setTimeMaxToStop(Long.toString(time));}
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



    private void showMenu(View v){
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();

        if (v == selectDist){
            inflater.inflate(R.menu.distance_filter_menu, popup.getMenu());}

        if (v == makePercDist ){
            inflater.inflate(R.menu.range_filter_menu, popup.getMenu());}

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                makePercDistSel = item.getItemId();

                switch (item.getItemId()) {
                    case R.id.three_five:
                        makePercDist.setText("3 - 5 FT");
                        timeRange = System.currentTimeMillis()-DAYMILLI;
                        valuePicker1 = 3;
                        valuePicker2 = 5;
                        distanceString = "3 to 5 feet";
                        getChartData(distanceString);
                        getLastSessionNum(distanceString);


                        mPlayerMakePercentDistRef= mPlayerMakePercentRef.child(distanceString).
                                child("sessions").push();
                        makeSessionID1 = mPlayerMakePercentDistRef.getKey();

                        mPlayerMakePercentRef.child(makeSessionID1).child("attempted").setValue(0);
                        mPlayerMakePercentRef.child(makeSessionID1).child("made").setValue(0);
                        mPlayerMakePercentRef.child(makeSessionID1).child("time").setValue(System.currentTimeMillis());



                        getAverage(distanceString);
                        //refreshQueryDateRange(timeRange, valuePicker1, valuePicker2)
                        //break;
                    return true;
                    case R.id.six_eight:
                        makePercDist.setText("6 - 8 FT");
                        timeRange = System.currentTimeMillis()-WEEKMILLI;
                        valuePicker1 = 6;
                        valuePicker2 = 8;
                        distanceString = "6 to 8 feet";
                        makeSessionID2 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                        mPlayerMakePercentRef.child(makeSessionID2).child("attempted").setValue(0);
                        mPlayerMakePercentRef.child(makeSessionID2).child("made").setValue(0);
                        getLastSessionNum(distanceString);
                        mPlayerMakePercentRef.child(makeSessionID2).child("sessionNum").setValue((numSessions+1));
                        getChartData(distanceString);
                        //refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                        //break;
                    return true;
                    case R.id.nine_eleven:
                        makePercDist.setText("9 - 11 FT");
                        timeRange = System.currentTimeMillis()-MONTHMILLI;
                        valuePicker1 = 9;
                        valuePicker2 = 11;
                        distanceString = "9 to 11 feet";
                        makeSessionID3 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                        mPlayerMakePercentRef.child(makeSessionID3).child("attempted").setValue(0);
                        mPlayerMakePercentRef.child(makeSessionID3).child("made").setValue(0);
                        getLastSessionNum(distanceString);
                        mPlayerMakePercentRef.child(makeSessionID3).child("sessionNum").setValue((numSessions+1));
                        getChartData(distanceString);
                        //refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                        //break;
                    return true;
                    case R.id.twelve_fifteen:
                        makePercDist.setText("12 - 15 FT");
                        timeRange = System.currentTimeMillis()-YEARMILLI;
                        valuePicker1 = 12;
                        valuePicker2 = 15;
                        distanceString = "12 to 15 feet";
                        makeSessionID4 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                        mPlayerMakePercentRef.child(makeSessionID4).child("attempted").setValue(0);
                        mPlayerMakePercentRef.child(makeSessionID4).child("made").setValue(0);
                        getLastSessionNum(distanceString);
                        mPlayerMakePercentRef.child(makeSessionID4).child("sessionNum").setValue((numSessions+1));
                        getChartData(distanceString);
                        //refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                        //break;
                    return true;
                    case R.id.sixteen_twenty:
                        makePercDist.setText("16 - 20 FT");
                        valuePicker1 = 16;
                        valuePicker2 = 20;
                        distanceString = "16 to 20 feet";
                        makeSessionID5 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                        mPlayerMakePercentRef.child(makeSessionID5).child("attempted").setValue(0);
                        mPlayerMakePercentRef.child(makeSessionID5).child("made").setValue(0);
                        getLastSessionNum(distanceString);
                        mPlayerMakePercentRef.child(makeSessionID5).child("sessionNum").setValue((numSessions+1));
                        getChartData(distanceString);
                        // refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                        return true;
                    case R.id.twentyone_twentyfive:
                        makePercDist.setText("21 - 25 FT");
                        valuePicker1 = 21;
                        valuePicker2 = 25;
                        distanceString = "21 to 25 feet";
                        makeSessionID6 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                        mPlayerMakePercentRef.child(makeSessionID6).child("attempted").setValue(0);
                        mPlayerMakePercentRef.child(makeSessionID6).child("made").setValue(0);
                        getLastSessionNum(distanceString);
                        mPlayerMakePercentRef.child(makeSessionID6).child("sessionNum").setValue((numSessions+1));
                        getChartData(distanceString);
                        //refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                        //break;
                        return true;
                    case R.id.dist_three_eight:
                        selectDist.setText("3 to 8 FT");

                        distMin = 3;
                        distMax = 8;
                        distGoal = new Random().nextInt((distMax - distMin) + 1) + distMin;
                        distanceGoal.setText(Integer.toString(distGoal)+" ft");


                        String distanceString1 = "3 to 5 feet";
                        mPlayerSessionDistRef = mPlayerDistRef.child("3 to 5 feet").child("sessions");
                        distanceSessionID1 = mPlayerSessionDistRef.push().getKey();
                        mPlayerSessionDistRef.child(distanceSessionID1).child("attempted").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID1).child("min").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID1).child("max").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID1).child("totalDist").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID1).child("sessionNum").setValue(0);




                        mPlayerSessionDistRef2 = mPlayerDistRef.child("6 to 8 feet").child("sessions");
                        distanceSessionID2 = mPlayerSessionDistRef2.push().getKey();
                        mPlayerSessionDistRef2.child(distanceSessionID2).child("attempted").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID2).child("min").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID2).child("max").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID2).child("total dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID2).child("sessionNum").setValue(0);
                        String distanceString2 = "6 to 8 feet";

                        distRangeMin = 3.0f;
                        distRangeMax = 5.0f;
                        getDistanceChartData("3 to 5 feet");
                        //getAverageDistance();

                        return true;
                    case R.id.dist_nine_fourteen:
                        selectDist.setText("9 to 14 FT");
                        distMin = 9;
                        distMax = 14;
                        distGoal = new Random().nextInt((distMax - distMin) + 1) + distMin;
                        distanceGoal.setText(Integer.toString(distGoal));

                        mPlayerSessionDistRef = mPlayerDistRef.child("9 to 11 feet").child("sessions");
                        distanceSessionID3 = mPlayerSessionDistRef.push().getKey();
                        mPlayerSessionDistRef.child(distanceSessionID3).child("attempted").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID3).child("min").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID3).child("max").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID3).child("totalDist").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID3).child("sessionNum").setValue(0);


                        mPlayerSessionDistRef = mPlayerDistRef.child("12 to 14 feet").child("sessions");
                        distanceSessionID4 = mPlayerSessionDistRef.push().getKey();
                        mPlayerSessionDistRef2.child(distanceSessionID4).child("attempted").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID4).child("min dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID4).child("max dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID4).child("total dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID4).child("sessionNum").setValue(0);

                        //getDistanceSessionData();
                        //getAverageDistance();

                        return true;
                    case R.id.dist_fifteen_twenty:
                        selectDist.setText("15 to 20 FT");
                        distMin = 15;
                        distMax = 20;
                        distGoal = new Random().nextInt((distMax - distMin) + 1) + distMin;
                        distanceGoal.setText(Integer.toString(distGoal));

                        mPlayerSessionDistRef = mPlayerDistRef.child("15 to 17 feet").child("sessions");
                        distanceSessionID5 = mPlayerSessionDistRef.push().getKey();
                        mPlayerSessionDistRef.child(distanceSessionID5).child("attempted").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID5).child("min dist").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID5).child("max dist").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID5).child("total dist").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID5).child("sessionNum").setValue(0);


                        mPlayerSessionDistRef = mPlayerDistRef.child("18 to 20 feet").child("sessions");
                        distanceSessionID6 = mPlayerSessionDistRef.push().getKey();
                        mPlayerSessionDistRef2.child(distanceSessionID6).child("attempted").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID6).child("min dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID6).child("max dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID6).child("total dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID6).child("sessionNum").setValue(0);

                        //getDistanceSessionData();
                        //getAverageDistance();

                        return true;
                    case R.id.dist_twentyone_twentyfive:
                        selectDist.setText("21 to 25 FT");
                        distMin = 21;
                        distMax = 25;
                        distGoal = new Random().nextInt((distMax - distMin) + 1) + distMin;
                        distanceGoal.setText(Integer.toString(distGoal));

                        mPlayerSessionDistRef = mPlayerDistRef.child("21 to 23 feet").child("sessions");
                        distanceSessionID7 = mPlayerSessionDistRef.push().getKey();
                        mPlayerSessionDistRef.child(distanceSessionID7).child("attempted").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID7).child("min dist").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID7).child("max dist").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID7).child("total dist").setValue(0);
                        mPlayerSessionDistRef.child(distanceSessionID7).child("sessionNum").setValue(0);


                        mPlayerSessionDistRef = mPlayerDistRef.child("24 to 25 feet").child("sessions");
                        distanceSessionID8 = mPlayerSessionDistRef.push().getKey();
                        mPlayerSessionDistRef2.child(distanceSessionID8).child("attempted").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID8).child("min dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID8).child("max dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID8).child("total dist").setValue(0);
                        mPlayerSessionDistRef2.child(distanceSessionID8).child("sessionNum").setValue(0);

                        //getDistanceSessionData();
                        //getAverageDistance();


                        return true;

                    default:
                        return true;
                }
            }
        });
        popup.show();
    }






    public void setRoll(Double distance){

        rollDistancePractice.setText(Double.toString(distance));
        rollDistancePracticeExpanded.setText(Double.toString(distance));


    }


    public void setVelocity(String velocity){

        entrySpeedPractice.setText(velocity);

    }


    public void setAngle(String angle){

        launchAngle.setText(angle);


    }


    public void setDirection(String direction){

        launchDirection.setText(direction);

    }


    public void setPureRoll(String percentage){


        pureRollPercentage.setText(percentage);
        distGoal = new Random().nextInt((distMax - distMin) + 1) + distMin;
        distanceGoal.setText(Integer.toString(distGoal));
    }


    public void setTimeMaxToStop(String time){

        timeMaxtoStop.setText(time);

    }


    private void fbDatabase() {

        mPlayerRef = FirebaseDatabase.getInstance().getReference("players/RedSquadLeader7");
        mPlayerRef.keepSynced(true);
        mPlayerStatRef = mPlayerRef.child("stats");
        mPlayerMakePercentRef = mPlayerStatRef.child("make percentage");
        mPlayerDistRef = mPlayerStatRef.child("distance control");







    }

    private void checkForNewSession(){




    }


    public void recordMakeEvent(Double distance){


        switch (makePercDistSel) {
            case R.id.three_five:
                if (distance > 2.5 && distance <= 5.5) {
                    mMakes1.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {

                        //String id = mSession1Fragment.push().getKey();

                        //makeSessionID = mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").push().getKey();

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("made").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("sessionNum").setValue((numSessions+1));


                    }
                }
                break;
            case R.id.six_eight:
                if (distance >5.5 && distance <=8.5) {
                    mMakes4.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;
            case R.id.nine_eleven:
                if (distance >8.5 && distance <=11.5){
                mMakes7.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("9 to 11 feet").child("sessions").
                                child(makeSessionID3).child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;
            case R.id.twelve_fifteen:
                if (distance >11.5 && distance <=15.5){
                mMakes10.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("12 to 15 feet").child("sessions").
                                child(makeSessionID4).child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;
            case R.id.sixteen_twenty:
                if (distance >15.5 && distance <=20.5){
                mMakes14.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("16 to 20 feet").child("sessions").
                                child(makeSessionID5).child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;
            case R.id.twentyone_twentyfive:
                if (distance >20.5 && distance <=26.5){
                mMakes19.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("21 to 25 feet").child("sessions").
                                child(makeSessionID6).child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;
            default:
                break;}
        puttViewModel.setFragmentPuttMadeFlag(false);

    }


    public void recordAttemptEvent(Double distance){
        if(cardpressed == 1) {
            switch (makePercDistSel) {
                case R.id.three_five:
                    mAttempts1.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {


                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("sessionNum").setValue((numSessions));

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("time").setValue((currentMilli));

                    }

                    break;
                case R.id.six_eight:
                    mAttempts4.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("sessionNum").setValue((numSessions));

                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("time").setValue((currentMilli));
                    }
                    break;
                case R.id.nine_eleven:
                    mAttempts7.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("9 to 11 feet").child("sessions").
                                child(makeSessionID3).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("9 to 11 feet").child("sessions").
                                child(makeSessionID3).child("sessionNum").setValue((numSessions));

                        mPlayerMakePercentRef.child("9 to 11 feet").child("sessions").
                                child(makeSessionID3).child("time").setValue((currentMilli));

                    }
                    break;
                case R.id.twelve_fifteen:
                    mAttempts10.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("12 to 15 feet").child("sessions").
                                child(makeSessionID4).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("12 to 15 feet").child("sessions").
                                child(makeSessionID4).child("sessionNum").setValue((numSessions));

                        mPlayerMakePercentRef.child("12 to 15 feet").child("sessions").
                                child(makeSessionID4).child("time").setValue((currentMilli));
                    }
                    break;
                case R.id.sixteen_twenty:
                    mAttempts14.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("16 to 20 feet").child("sessions").
                                child(makeSessionID5).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("16 to 20 feet").child("sessions").
                                child(makeSessionID5).child("sessionNum").setValue((numSessions));

                        mPlayerMakePercentRef.child("16 to 20 feet").child("sessions").
                                child(makeSessionID5).child("time").setValue((currentMilli));
                    }
                    break;
                case R.id.twentyone_twentyfive:
                    mAttempts19.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("21 to 25 feet").child("sessions").
                                child(makeSessionID6).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("21 to 25 feet").child("sessions").
                                child(makeSessionID6).child("sessionNum").setValue((numSessions));

                        mPlayerMakePercentRef.child("21 to 25 feet").child("sessions").
                                child(makeSessionID6).child("time").setValue((currentMilli));
                    }
                    break;
                default:
                    break;
            }
            puttViewModel.setFragmentPuttAttempt(0);
        }

        if(cardpressed == 3){

            switch (makePercDistSel) {
                case R.id.dist_three_eight:

                    if(distGoal >= 3 && distGoal <=5) {

                        String distRange = "3 to 5 feet";
                        String sessionID = distanceSessionID1;
                        getLastDistSessionNum("3 to 5 feet");
                        setFBDistResults(distRange, sessionID, numDistSessions, 3);

                    }

                    if(distGoal >= 6 && distGoal <=8){

                        String distRange = "6 to 8 feet";
                        String sessionID = distanceSessionID2;
                        getLastDistSessionNum("6 to 8 feet");
                        setFBDistResults(distRange, sessionID, numDistSessions, 6);


                    }
                break;

                case R.id.dist_nine_fourteen:

                    if(distGoal >= 9 && distGoal <=11) {

                        String distRange = "9 to 11 feet";
                        String sessionID = distanceSessionID3;

                        getLastDistSessionNum("9 to 11 feet");
                        setFBDistResults(distRange, sessionID, numDistSessions, 9);

                    }

                    if(distGoal >= 12 && distGoal <=14) {

                        String distRange = "12 to 14 feet";
                        String sessionID = distanceSessionID4;

                        getLastDistSessionNum("12 to 14 feet");
                        setFBDistResults(distRange, sessionID, numDistSessions, 12);


                    }
                    break;

                case R.id.dist_fifteen_twenty:

                    if(distGoal >= 15 && distGoal <=17) {

                        String distRange = "15 to 17 feet";
                        String sessionID = distanceSessionID5;

                        getLastDistSessionNum("15 to 17 feet");
                        setFBDistResults(distRange, sessionID, numDistSessions, 15);

                    }

                    if(distGoal >= 18 && distGoal <=20) {

                        String distRange = "18 to 20 feet";
                        String sessionID = distanceSessionID6;

                        getLastDistSessionNum("18 to 20 feet");
                        setFBDistResults(distRange, sessionID, numDistSessions, 18);


                    }
                    break;


                case R.id.dist_twentyone_twentyfive:

                    if(distGoal >= 21 && distGoal <=23) {

                        String distRange = "21 to 23 feet";
                        String sessionID = distanceSessionID7;

                        getLastDistSessionNum("21 to 23 feet");
                        setFBDistResults(distRange, sessionID, numDistSessions, 21);

                    }

                    if(distGoal >= 24 && distGoal <=25) {

                        String distRange = "24 to 25 feet";
                        String sessionID = distanceSessionID8;

                        getLastDistSessionNum("24 to 25 feet");
                        setFBDistResults(distRange, sessionID, numDistSessions, 24);


                    }
                    break;

                default:
                    break;}







        }

    }


    private void setFBDistResults(String range, String sessionID, long sesssionNum, final int minRange){
        fbDistRange = range;
        fbDistSessionID = sessionID;
        mPlayerDistRef.child(fbDistRange).child("sessions").
                child(fbDistSessionID).child("attempted").setValue(ServerValue.increment(1));
        mPlayerDistRef.child(fbDistRange).child("sessions").
                child(fbDistSessionID).child("totalDist").setValue(ServerValue.increment(rollDistanceFragment));
        mPlayerDistRef.child(fbDistRange).child("sessions").
                child(fbDistSessionID).child("sessionNum").setValue((sesssionNum));




        mPlayerDistRef.child(fbDistRange).child("sessions").
                child(fbDistSessionID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {


                                MinMaxDist minMaxDist = dataSnapshot.getValue(MinMaxDist.class);

                                double maxDist = minMaxDist.getMax();
                                if (maxDist < rollDistanceFragment) {
                                    mPlayerDistRef.child(fbDistRange).child("sessions").
                                            child(fbDistSessionID).child("max").
                                            setValue(rollDistanceFragment);
                                }
                                double minDist = minMaxDist.getMin();
                                if (minDist > rollDistanceFragment) {
                                    mPlayerDistRef.child(fbDistRange).child("sessions").
                                            child(fbDistSessionID).child("min").
                                            setValue(rollDistanceFragment);
                                }
                                if (minDist == 0){
                                    mPlayerDistRef.child(fbDistRange).child("sessions").
                                            child(fbDistSessionID).child("min").
                                            setValue(minRange);
                                }

                            }
                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }








    private void expandCard(){



        //cardOneExpanded = view4.findViewById(R.id.cardoneexpanded);

        TransitionManager.beginDelayedTransition(cardOneExpanded,new AutoTransition());
        cardOneExpanded.setVisibility(view4.VISIBLE);

    }



    private void getAvgChart() {
        lineDataSet.setValues(dataVals3());
        lineDataSet.setLabel("Session #1");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(false);
        //lineDataSet.setFillColor(Color.BLACK);
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setCircleHoleColor(Color.BLACK);
        //lineChart.clear();
        //lineChart.setData(lineData);
        //lineChart.invalidate();

    }
    //for bar chart


    private ArrayList<Entry> dataVals3(){

     ArrayList<Entry> dataVals3 = new ArrayList<Entry>();
        dataVals3.add(new Entry(1, (float) 1.5));
        dataVals3.add(new Entry(2, (float) 0.5));
        dataVals3.add(new Entry(3, (float) 0.2));
        dataVals3.add(new Entry(4, (float) -0.8));
        dataVals3.add(new Entry(5, (float) 0.0));
        dataVals3.add(new Entry(6, (float) 0.3));
        dataVals3.add(new Entry(7, (float) 1.6));
        dataVals3.add(new Entry(8, (float) 1.8));
        dataVals3.add(new Entry(9, (float) 0.4));

    return dataVals3;

    }


    private void getChartData(String distance){

        // Read from the database to make line chart
        if (mPlayerRef != null) {
            mPlayerSessionRef = mPlayerMakePercentRef.child(distance).child("sessions");

            mPlayerSessionRef.limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    dataVals = new ArrayList<BarEntry>();

                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            SessionData1 sessionData = myDataSnapshot.getValue(SessionData1.class);
                            //double makePer = Math.round((sessionData.getMade()/sessionData.getAttempted())*100)/100D;
                            double att = sessionData.getAttempted();
                            double m = sessionData.getMade();
                            long time = sessionData.getTime();
                            if((System.currentTimeMillis() - DAYMILLI) > sessionData.getTime()){
                                newSession = true;
                            }
                            if((System.currentTimeMillis() - DAYMILLI) < sessionData.getTime()){
                                newSession = false;
                                key = dataSnapshot.getKey();
                            }
                            double makePer = (m / att) * 100;
                            int MP = (int) (makePer);

                            sumMade = sumMade + m;
                            sumAttempt = sumAttempt + att;
                            thisSessionMade = sessionData.getMade();
                            thisSessionAttempt = sessionData.getAttempted();
                            dataVals.add(new BarEntry(sessionData.getSessionNum(), MP));
                        }

                        myAverage = (sumMade/sumAttempt)*100;
                        myAverageInt = (int)(myAverage);
                        myMakePercentageAvg.setText(Integer.toString(myAverageInt)+ "%");

                        if(thisSessionAttempt != 0) {
                            sessionAverage = (thisSessionMade / thisSessionAttempt) * 100;
                            sessionAverageInt = (int) (sessionAverage);
                            myMakePercentageSession.setText(Integer.toString(sessionAverageInt)+"%");
                        }


                        if (cardpressed == 1) {
                            showChart(dataVals);
                        }

                    } else {
                        barChart.clear();
                        barChart.invalidate();

                        //lineChart.clear();
                        //lineChart.invalidate();

                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }



    }


    private void getDistanceChartData(String distance){

        // Read from the database to make line chart
        if (mPlayerRef != null) {
            mPlayerDistChartRef = mPlayerDistRef.child(distance).child("sessions");

            mPlayerDistChartRef.limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    dataVals1 = new ArrayList<BarEntry>();

                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            MinMaxDist distSessionData = myDataSnapshot.getValue(MinMaxDist.class);
                            //double makePer = Math.round((sessionData.getMade()/sessionData.getAttempted())*100)/100D;
                            /*
                            if((System.currentTimeMillis() - DAYMILLI) > MinMaxDist.getTime()){
                                newSession = true;
                            }
                            if((System.currentTimeMillis() - DAYMILLI) < MinMaxDist.getTime()){
                                newSession = false;
                                key = dataSnapshot.getKey();
                            }
                            */

                            float minRange = (0f - (distRangeMin - distSessionData.getMin()));
                            float maxRange = distRangeMax - distRangeMin;
                            float maxValue = distSessionData.getMax() - distRangeMax - 0.2f;

                            if (distSessionData.getSessionNum() != 0) {
                                dataVals1.add(new BarEntry(distSessionData.getSessionNum(), new float[]
                                        {minRange, 0.2f, maxValue}));
                            }
                            //dataVals1.add(new BarEntry(distSessionData.getSessionNum(), distSessionData.getMax(),
                            //        3, 5, distSessionData.getMax()));
                        }

                        //myAverage = (sumMade/sumAttempt)*100;
                        //myAverageInt = (int)(myAverage);
                        //myMakePercentageAvg.setText(Integer.toString(myAverageInt)+ "%");
                        /*
                        if(thisSessionAttempt != 0) {
                            sessionAverage = (thisSessionMade / thisSessionAttempt) * 100;
                            sessionAverageInt = (int) (sessionAverage);
                            myMakePercentageSession.setText(Integer.toString(sessionAverageInt)+"%");
                        }
                        */

                        if (cardpressed == 3) {
                            getAvgChart();
                            showDistChart(dataVals1);

                        }

                    } else {
                        distBarChart.clear();
                        distBarChart.invalidate();

                        //lineChart.clear();
                        //lineChart.invalidate();

                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }



    }


    private void getAverage(String distance){

        if (mPlayerRef != null) {
            mPuttsMakeAvg = mPuttsFragment.child(distance);

            mPuttsMakeAvg.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    AverageData averageData = dataSnapshot.getValue(AverageData.class);



                            double att = averageData.getAttempted();
                            double m = averageData.getMade();
                            double makePer = (m / att) * 100;
                            int MP = (int) (makePer);
                            allAverageInt = MP;
                            allUserAvg.setText("All Users Avg:  " + (allAverageInt) + "%");


                        }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            }



    }


    private void getDistanceSessionData(){


    }


    private void getAverageDistance(){


    }














    private void showChart(ArrayList<BarEntry> dataVals) {

        BarDataSet barDataSet = new BarDataSet(dataVals, "%");
        //barDataSet.setValues(dataVals);
        barDataSet.setLabel("Session Make Percentages");
        iBarDataSets.clear();
        iBarDataSets.add(barDataSet);
        barData = new BarData(iBarDataSets);
        barChart.clear();
        barData.setBarWidth(1f);
        barChart.fitScreen();
        //barChart.setVisibleXRangeMaximum(8);
        //barChart.moveViewToX(5);
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateXY(0, 1000);
        barChart.invalidate();
        XAxis topAxis = barChart.getXAxis();
        topAxis.setDrawLabels(false);
        topAxis.setDrawGridLines(false);
        Legend l = barChart.getLegend();
        l.setEnabled(false);
        l.setTextSize(20f);




    }


    private void showDistChart(ArrayList<BarEntry> dataVals1) {

        BarDataSet distBarDataSet = new BarDataSet(dataVals1, "ft");
        iDistBarDataSets.clear();
        iDistBarDataSets.add(distBarDataSet);
        distBarDataSet.setColors(colorClassArray);

        distBarDataSet.setDrawValues(false);

        distBarData = new BarData(iDistBarDataSets);
        //distBarData.setBarWidth(.5f);
        CombinedData distanceData = new CombinedData();
        distanceData.setData(distBarData);
        distanceData.setData(lineData);
        distancechart.setData(distanceData);
        distancechart.invalidate();

        distancechart.fitScreen();

        distancechart.animateXY(1000, 1000);
        XAxis topAxis = distancechart.getXAxis();
        topAxis.setDrawLabels(false);
        topAxis.setDrawGridLines(false);
        topAxis.setAxisMinimum(0.5f);
        topAxis.setAxisMaximum(9.5f);
        Legend l = distancechart.getLegend();
        l.setEnabled(false);

        distancechart.getDescription().setText("Last 10 sessions");
        distancechart.getDescription().setTextSize(18f);
        YAxis yAxis = distancechart.getAxisLeft();
        YAxis yAxis2 = distancechart.getAxisRight();
        yAxis2.setEnabled(false);
        yAxis.setTextSize(18f);




        /*
        distBarDataSet.setColors(colorClassArray);
        distBarDataSet.setDrawValues(false);
        distBarDataSet.setLabel("Session Make Percentages");
        iDistBarDataSets.clear();
        distBarChart.clear();
        distBarData.setBarWidth(0.25f);

        distBarChart.fitScreen();
        //barChart.setVisibleXRangeMaximum(8);
        //barChart.moveViewToX(5);
        //barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        distBarChart.setData(distBarData);
        distBarChart.getDescription().setEnabled(false);
        distBarChart.animateXY(0, 1000);
        distBarChart.invalidate();
        XAxis topAxis = distBarChart.getXAxis();
        topAxis.setDrawLabels(false);
        topAxis.setDrawGridLines(false);
        Legend l = distBarChart.getLegend();
        l.setEnabled(false);
        l.setTextSize(20f);

        */


    }

    private void getLastSessionNum(String string) {
        mPlayerMakePercentRef.child(string).child("sessions").
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numSessions = dataSnapshot.getChildrenCount();
                if (newSession == true){
                    numSessions++;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

    }


    private void getLastDistSessionNum(String string){
        mPlayerDistRef.child(string).child("sessions").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        numDistSessions = datasnapshot.getChildrenCount();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    /*
    //for line chart
    private void showChart(ArrayList<Entry> dataVals) {


        LineDataSet lineDataSet = new LineDataSet(dataVals, "%");
        //barDataSet.setValues(dataVals);
        lineDataSet.setLabel("Session #1");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineDataSet.setLineWidth(4f);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.BLACK);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setColor(Color.GREEN);

        //set the transparency
        lineDataSet.setFillAlpha(80);

        //set the gradiant then the above draw fill color will be replace
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.line_chart_gradient);
        lineDataSet.setFillDrawable(drawable);

        lineDataSet.setDrawCircles(false);

        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.getXAxis().setEnabled(false);
        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.setTouchEnabled(true);
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setMaxHighlightDistance(200);

        lineChart.animateX(2000);
        lineChart.invalidate();

    }


    */







    private void zoomImageFromThumb(final View thumbView, int imageResId, final LinearLayout card) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.

        cardNum = card;

        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) view4.findViewById(
                R.id.expanded_image);

        expandedImageView.setImageResource(imageResId);




        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        view4.findViewById(R.id.fragment_container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;

                if (cardNum == cardOneExpanded) {
                    cardExpanded = view4.findViewById(R.id.cardoneexpanded);

                    cardExpanded.setVisibility(view4.VISIBLE);
                }

                if (cardNum == cardTwoExpanded) {
                   cardExpanded = view4.findViewById(R.id.cardtwoexpanded);

                    cardExpanded.setVisibility(view4.VISIBLE);
                }

                if (cardNum == cardThreeExpanded) {
                    cardExpanded = view4.findViewById(R.id.cardthreeexpanded);

                    cardExpanded.setVisibility(view4.VISIBLE);
                }

                if (cardNum == cardFourExpanded) {
                    cardExpanded = view4.findViewById(R.id.cardfourexpanded);

                    cardExpanded.setVisibility(view4.VISIBLE);
                }

                if (cardNum == cardFiveExpanded) {
                    cardExpanded = view4.findViewById(R.id.cardfiveexpanded);

                    cardExpanded.setVisibility(view4.VISIBLE);
                }

                if (cardNum == cardSixExpanded) {
                    cardExpanded = view4.findViewById(R.id.cardsixexpanded);

                    cardExpanded.setVisibility(view4.VISIBLE);
                }


                if(dataVals != null){
                showChart(dataVals);}








            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;







        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        //cardExpanded = null;
        if (cardNum == cardOneExpanded){
            cardExpanded = view4.findViewById(R.id.cardoneexpanded);

        };

        if (cardNum == cardTwoExpanded){
            cardExpanded = view4.findViewById(R.id.cardtwoexpanded);

        };

        if (cardNum == cardThreeExpanded){
            cardExpanded = view4.findViewById(R.id.cardthreeexpanded);

        };

        if (cardNum == cardFourExpanded){
            cardExpanded = view4.findViewById(R.id.cardfourexpanded);

        };

        if (cardNum == cardFiveExpanded){
            cardExpanded = view4.findViewById(R.id.cardfiveexpanded);

        };

        if (cardNum == cardSixExpanded){
            cardExpanded = view4.findViewById(R.id.cardsixexpanded);

        };


        final View finalCardExpanded = cardExpanded;
        //final View closeButton = view4.findViewById(R.id.close_button2);
        finalCardExpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                cardExpanded.setVisibility(view4.INVISIBLE);

                // Animate the four positioning/sizing proper ties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;

            }
        });


    }



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
        if (context instanceof StatsFragment.OnFragmentInteractionListener) {
            mListener = (StatsFragment.OnFragmentInteractionListener) context;



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

    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()) {
            case R.id.cardonebutton:
                zoomImageFromThumb(ThumbView1, R.drawable.button2);
                break;
            case R.id.cardtwoexpanded:
                zoomImageFromThumb(cardTwoExpanded, R.drawable.button2);
                break;

            default:
                break;
        }

        */

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

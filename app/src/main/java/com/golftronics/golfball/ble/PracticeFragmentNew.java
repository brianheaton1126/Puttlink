package com.golftronics.golfball.ble;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;

import java.lang.reflect.Field;
import java.util.Locale;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.EventsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.inappmessaging.display.internal.FiamAnimator;
import com.tomer.fadingtextview.FadingTextView;

import android.net.Uri;
import android.os.Environment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.File;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PracticeFragmentNew.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PracticeFragmentNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeFragmentNew extends Fragment  {



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

    private PracticeFragmentNew.OnFragmentInteractionListener mListener;

    private PuttViewModel puttViewModel;

    PlayerView playerView;

    Button playerViewClose;

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

    public String pace;

    private int distanceTarget = 25;
    private int distanceRange1;
    private int distanceRange2;
    private TextView distanceTargetText1;
    private TextView distanceTargetText2;
    private ProgressBar targetProgressBar;
    private LinearLayout targetBarLayout;
    private LinearLayout rollDataText;
    private ProgressBar rollDistanceProgressBar;
    private ImageView distanceBarHole;
    private ImageView distanceBarCup;
    private int progressBarHeight;
    private ImageView holeView;
    private long puttPlotSession;
    private TextView puttPlotSessionText;
    private Button puttPlotForward;
    private Button puttPlotBack;
    private long accuracyScore = 0;
    private long accuracyScoreOld  = 0;
    private TextView accuracyScoreText;

    private Button stimpInstructionsDismiss;
    private Button stimpMeasureClose;


    private FrameLayout vGrid;
    private FrameLayout vGridSelected;
    private FrameLayout grid;
    private ImageView v2;
    private ImageView vselected;
    private FrameLayout parentGrid;

    int intArray[];
    int intArray2[];
    int increment = -1;




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
    public double trueRoll;
    public long rollTime;
    public double maximumVelocity;

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
    public long numAccuracySessions;
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
    private CardView puttData1Card;
    private CardView puttData2Card;
    private CardView readStimpCard;
    private CardView distanceDrillsCard;
    private CardView challengesCard;
    private CardView distanceCard;
    private CardView drillsCard;
    private CardView madeAttCard;
    private CardView makePercCard;
    private CardView velocityCard;
    private CardView pureRollCard;
    private CardView pastHoleCard;
    private CardView upslopeStimpCard;
    private CardView downslopeStimpCard;
    private CardView slopeCorrectedStimpCard;
    private CardView puttLinkChallengesCard;
    private CardView friendChallengesCard;
    private CardView drill1Expanded;
    private CardView drill2Expanded;
    private CardView drill3Expanded;
    private CardView drill4Expanded;
    private CardView drill5Expanded;
    private CardView drill1Minimized;
    private CardView drill2Minimized;
    private CardView drill3Minimized;
    private CardView drill4Minimized;
    private CardView drill5Minimized;

    private CardView stimpInstructions;
    private CardView stimpMeasurements;

    private int stimpOpen;

    private int myAverageHomeInt;

    private Button drill1CheckBox;
    private Button drill2CheckBox;
    private Button drill3CheckBox;
    private Button drill4CheckBox;
    private Button drill5CheckBox;

    private ImageView golfball;
    private TextView decreaseDistance;
    private TextView increaseDistance;
    private ImageView distanceLine;
    private TextView rollBarDistanceText;
    private TextView rollBarDistanceZero;
    private TextView rollBarDistanceThirty;


    ArrayList<Integer> viewArrayList = new ArrayList<>();

    public int point = 0;

    private TextView puttData1Plus;
    private TextView puttData2Plus;
    private TextView readStimpPlus;
    private TextView drillsPlus;
    private TextView challengesPlus;
    private TextView drill5Data;

    private ScrollView scrollView;

    private TextView valueActual;

    private TextView drill5Instructions;

    private Boolean animationEnd = false;

    private String instruction1 = "Putt a distance up to 30 ft.";
    private String instruction2 = "Then say the distance";
           // "After a beep is heard, speak your distance estimate near this device or using ear bud microphone",
           // "The actual distance will be displayed after recording your estimate",
           // "Pro Tip:  Practice with your eyes closed"};



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
    private TextView homeVelocity;
    private TextView homeMade;
    private TextView homeMadeAttempt;
    private TextView homeAttempt;
    private TextView homeMakePerc;
    public Button makePercDist;
    public Button selectDist;
    public String distanceString;
    private int numberMade = 0;
    private int numberAttempt = 0;
    private Double upSlopeStimp;
    private TextView upSlopeStimpText;
    private Double downSlopeStimp;
    private TextView downSlopeStimpText;
    private Double slopeCorrectedStimp;
    private TextView slopeCorrectedStimpText;
    private Double pastHoleDistance;
    private TextView pastHoleDistanceText;
    private TextView speechText;

    private int drillsMenuOpen;

    private FrameLayout gridPointData;
    private TextView gridPointDataDistance;
    private TextView gridPointDataResult;
    private TextView gridPointDataTrueRoll;
    private TextView gridPointDataMaxVelocity;
    private TextView gridPointDataRollTime;
    private TextView gridPointDataMiss;
    private TextView gridPointDataSlope;
    private TextView gridPointDataPace;


    public int gridPoint = 0;
    public int gridPoint2 = 0;

    public int plotVideoFlag = 0;
    public Button puttVideoPlay;

    int vId;

    public TextView myMakePercentageAvg;
    public TextView myMakePercentageSession;



    public CharSequence signedInAccountFragment;
    String firebasePlayer;
    String userID;

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


    private AchievementsClient mAchievementsClient;
    private LeaderboardsClient mLeaderboardsClient;
    private EventsClient mEventsClient;
    private PlayersClient mPlayersClient;
    public String mDisplayName = "";
    private static final int RC_LEADERBOARD_UI = 9004;
    private static GoogleSignInAccount signedInAccountGoogle;


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
    private DatabaseReference mPlayerStatRef, mPlayerSessionRef, mPlayerSessionRefHome;
    private DatabaseReference mPlayerMakePercentRef;
    private DatabaseReference mPlayerMakeAccuracyRef;
    private DatabaseReference mPlayerMakeAccuracyRefSession;
    private DatabaseReference mPlayerMakeAccuracyRefGrid;
    private DatabaseReference mPlayerAccuracyRefGrid;

    private FirebaseAuth mAuth;

    private DatabaseReference accuracyRefSessionXValuesRef;
    private DatabaseReference accuracyRefSessionYValuesRef;
    private DatabaseReference mPuttsFragment;
    private FirebaseAuth mAuthFragment;
    private DatabaseReference mPuttsMakeAvg;
    private DatabaseReference mPlayerDistRef, mPlayerSessionDistRef, mPlayerSessionDistRef2,
            mPlayerDistChartRef;

    public String makeSessionID1, makeSessionID2, makeSessionID3, makeSessionID4, makeSessionID5,
                makeSessionID6, distanceSessionID1, distanceSessionID2, distanceSessionID3,
                distanceSessionID4, distanceSessionID5, distanceSessionID6, distanceSessionID7,
                distanceSessionID8, accuracyRefSessionID;

    private String key;

    ArrayList<BarEntry> dataVals;
    ArrayList<String> gridPointNumArray = new ArrayList<String>();
    ArrayList<Float> gridPointXArray = new ArrayList<Float>();
    ArrayList<Float> gridPointYArray = new ArrayList<Float>();
    ArrayList<String> gridPointResultArray = new ArrayList<String>();
    ArrayList<Double> gridPointDistanceArray = new ArrayList<Double>();
    ArrayList<Double> gridPointVelocityArray = new ArrayList<Double>();
    ArrayList<Double> gridPointTrueRollArray = new ArrayList<Double>();
    ArrayList<Integer> gridPointTargetDistanceArray = new ArrayList<Integer>();
    ArrayList<String> gridPointPaceArray = new ArrayList<String>();
    ArrayList<Integer> gridPointVideoArray = new ArrayList<Integer>();
    ArrayList<Double> makeArray = new ArrayList<Double>();
    ArrayList<Double> attemptArray = new ArrayList<Double>();

    long totalPlotSessions;


    String videoDirectoryPath;


    double sumMakeArray;
    double sumAttemptArray;





    int[] colorClassArray = new int[]{Color.BLUE, Color.BLACK, Color.RED};

    public int distMin;
    public int distMax;
    public int distGoal;

    public float distRangeMin;
    public float distRangeMax;

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private EditText editText;
    private ImageView micButton;

    public PracticeFragmentNew() {


        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static PracticeFragmentNew newInstance() {
        PracticeFragmentNew fragment5 = new PracticeFragmentNew();
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




        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());








    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        view4 = inflater.inflate(R.layout.home_practice_layout2, container, false);




        resID = view4.getId();

        fbDatabase();

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }



        distanceTargetText1 = view4.findViewById(R.id.distance_target);
        distanceTargetText2 = view4.findViewById(R.id.distance_target2);
        targetProgressBar = view4.findViewById(R.id.target_distance_progress_bar);
        increaseDistance = view4.findViewById(R.id.increase_distance_button);
        decreaseDistance = view4.findViewById(R.id.decrease_distance_button);
        targetBarLayout = view4.findViewById(R.id.targetprogressbars);
        rollDistancePractice = (TextView)view4.findViewById(R.id.roll_distance_practice);
        rollDataText = view4.findViewById(R.id.roll_data_text);
        rollDistanceProgressBar = view4.findViewById(R.id.actual_distance_progress_bar);
        distanceBarCup = view4.findViewById(R.id.distance_bar_cup);
        distanceBarHole = view4.findViewById(R.id.distance_bar_hole);
        distanceLine = view4.findViewById(R.id.distance_line);
        progressBarHeight = targetBarLayout.getHeight();
        golfball = view4.findViewById(R.id.golfball);
        holeView = view4.findViewById(R.id.distance_bar_hole);
        rollBarDistanceText = view4.findViewById(R.id.roll_bar_distance_text);
        rollBarDistanceZero = view4.findViewById(R.id.progressbarzero);
        rollBarDistanceThirty = view4.findViewById(R.id.progressbarthirty);
        gridPointData = view4.findViewById(R.id.gridpoint_data);
        gridPointDataDistance = view4.findViewById(R.id.popup_distance);
        gridPointDataResult = view4.findViewById(R.id.popup_result);
        gridPointDataMaxVelocity = view4.findViewById(R.id.popup_max_velocity);
        gridPointDataTrueRoll = view4.findViewById(R.id.popup_true_roll);
        gridPointDataSlope = view4.findViewById(R.id.popup_slope);
        puttPlotForward = view4.findViewById(R.id.putt_plot_forward);
        puttPlotBack = view4.findViewById(R.id.putt_plot_back);
        puttPlotSessionText= view4.findViewById(R.id.putt_plot_session);
        accuracyScoreText = view4.findViewById(R.id.home_score);
        myMakePercentageAvg = view4.findViewById(R.id.make_percentage_avg);
        stimpInstructions = view4.findViewById(R.id.stimp_instructions);
        stimpMeasurements = view4.findViewById(R.id.stimp_measurements);
        stimpInstructionsDismiss = view4.findViewById(R.id.stimp_instructions_dismiss);
        stimpMeasureClose = view4.findViewById(R.id.stimp_close);

        stimpMeasurements.setVisibility(View.GONE);
        stimpInstructions.setVisibility(View.GONE);


        grid = view4.findViewById(R.id.grid);
        vGrid = view4.findViewById(R.id.parentgrid);
        vGridSelected = view4.findViewById(R.id.parentgrid);
        //parentGrid = view4.findViewById(R.id.parentgrid);

        puttVideoPlay = view4.findViewById(R.id.putt_video_play);

        playerView = view4.findViewById(R.id.video_view);

        playerViewClose = view4.findViewById(R.id.video_close);

        for(int i = 0; i < 100; i++) {
            grid.addView(new ImageView(getContext()), i);
            vGrid.addView(new ImageView(getContext()), i);
            vGridSelected.addView(new ImageView(getContext()), i);

        }



        //setMakeMissPoints();


        intArray = new int[]{3,6,9,12,16,21};
        intArray2 = new int[]{5,8,11,15,20,25};
        //String[] stringArray = new String[]{"1","2","3"};
        ArrayList<String> stringArray = new ArrayList<String>();
        stringArray.add("1");
        stringArray.add("2");

        Spinner mapSpinner = view4.findViewById(R.id.spinner1);
        Spinner slopeSpinner = view4.findViewById(R.id.spinner2);
        Spinner stimpSpinner = view4.findViewById(R.id.spinner3);

        stimpSpinner.setDropDownHorizontalOffset(90);
        slopeSpinner.setDropDownHorizontalOffset(90);



        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, stringArray);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapSpinner.setAdapter(spinnerAdapter1);



        mapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> spinnerAdapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.puttslope, android.R.layout.simple_spinner_item);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slopeSpinner.setAdapter(spinnerAdapter2);


        slopeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> spinnerAdapter3 = ArrayAdapter.createFromResource(getContext(),
                R.array.puttstimp, android.R.layout.simple_spinner_item);
        spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stimpSpinner.setAdapter(spinnerAdapter3);


        stimpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



                //getChartData2("3 to 5 feet");


                //rollDistancePracticeExpanded = (TextView)view4.findViewById(R.id.distance_personal_actual);
                //entrySpeedPractice = (TextView)view4.findViewById(R.id.entry_speed_practice);
                //entrySpeedPracticeExpanded = (TextView)view4.findViewById(R.id.entry_speed_practice_expanded);
                //launchAngle = (TextView)view4.findViewById(R.id.putt_launch_angle);
                //launchDirection = (TextView)view4.findViewById(R.id.putt_launch_direction);
                //pureRollPercentage = (TextView)view4.findViewById(R.id.pure_roll_practice);
                //timeMaxtoStop = (TextView)view4.findViewById(R.id.time_max_velocity_to_stop);
                //pureRollPercent = (TextView)view4.findViewById(R.id.pureroll);

                //lineChart = view4.findViewById(R.id.make_line_chart);

                //puttData1Card = view4.findViewById(R.id.putt_data1);
                //puttData2Card = view4.findViewById(R.id.putt_data_2);
                //readStimpCard = view4.findViewById(R.id.read_stimp);
                //drillsCard = view4.findViewById(R.id.drills);
                //challengesCard = view4.findViewById(R.id.challenges);
                //distanceCard = view4.findViewById(R.id.distance_card);
                //drillsCard = view4.findViewById(R.id.drills);
                //madeAttCard = view4.findViewById(R.id.made_att_card);
                //makePercCard = view4.findViewById(R.id.make_perc_card);
                //velocityCard = view4.findViewById(R.id.velocity_card);
                //pureRollCard = view4.findViewById(R.id.pure_roll_card);
                //pastHoleCard = view4.findViewById(R.id.past_hole_card);
                //upslopeStimpCard = view4.findViewById(R.id.upslope_stimp_card);
                //downslopeStimpCard = view4.findViewById(R.id.downslope_stimp_card);
                //slopeCorrectedStimpCard = view4.findViewById(R.id.slope_corrected_stimp_card);
                //puttLinkChallengesCard = view4.findViewById(R.id.puttlink_challenges_card);
                //friendChallengesCard = view4.findViewById(R.id.friend_challenges_card);
                //drill1Expanded = view4.findViewById(R.id.drill1_expanded);
                //drill2Expanded = view4.findViewById(R.id.drill2_expanded);
                //drill3Expanded = view4.findViewById(R.id.drill3_expanded);
                //drill4Expanded = view4.findViewById(R.id.drill4_expanded);
                //drill5Expanded = view4.findViewById(R.id.drill5_expanded);
                //drill1Minimized = view4.findViewById(R.id.drill1_minimized);
                //drill2Minimized = view4.findViewById(R.id.drill2_minimized);
                //drill3Minimized = view4.findViewById(R.id.drill3_minimized);
                //drill4Minimized = view4.findViewById(R.id.drill4_minimized);
                //drill5Minimized = view4.findViewById(R.id.drill5_minimized);
                //drill1CheckBox = view4.findViewById(R.id.make_accuracy_start_btn1);
                //drill2CheckBox = view4.findViewById(R.id.distance_accuracy_start_btn1);
                //drill3CheckBox = view4.findViewById(R.id.pace_control_start_btn1);
                //drill4CheckBox = view4.findViewById(R.id.pressure_putts_start_btn1);
                //drill5CheckBox = view4.findViewById(R.id.over_under_start_btn1);

                //puttData1Plus = view4.findViewById(R.id.putt_data1_plus);
                //puttData2Plus = view4.findViewById(R.id.putt_data2_plus);
                //readStimpPlus = view4.findViewById(R.id.read_stimp_plus);
                //drillsPlus = view4.findViewById(R.id.drills_plus);
                //challengesPlus = view4.findViewById(R.id.challenges_plus);

                //drill1Expanded.setVisibility(view4.GONE);
                //drill2Expanded.setVisibility(view4.GONE);
                //drill3Expanded.setVisibility(view4.GONE);
                //drill4Expanded.setVisibility(view4.GONE);
                //drill5Expanded.setVisibility(view4.GONE);
                //puttLinkChallengesCard.setVisibility(view4.GONE);
                //friendChallengesCard.setVisibility(view4.GONE);
                //drillsCard.setTranslationY(0);


                //drill1CheckBox.setBackgroundResource(R.drawable.checkbox_outline);
                //drill2CheckBox.setBackgroundResource(R.drawable.checkbox_outline);
                //drill3CheckBox.setBackgroundResource(R.drawable.checkbox_outline);
                //drill4CheckBox.setBackgroundResource(R.drawable.checkbox_outline);
                //drill5CheckBox.setBackgroundResource(R.drawable.checkbox_outline);

                //scrollView = view4.findViewById(R.id.scrollview);
                //speechText = view4.findViewById(R.id.speech_text);
                //valueActual = view4.findViewById(R.id.over_under_value);
                //drill5Instructions = view4.findViewById(R.id.drill5_instructions);
                //drill5Data = view4.findViewById(R.id.drill5_data);

                homeVelocity = view4.findViewById(R.id.home_velocity);
        //homeMade = view4.findViewById(R.id.home_made);
        homeMadeAttempt = view4.findViewById(R.id.home_made_attempt);
        //homeAttempt = view4.findViewById(R.id.home_attempt);
        upSlopeStimpText = view4.findViewById(R.id.upslope_stimp);
        downSlopeStimpText = view4.findViewById(R.id.downslope_stimp);
        slopeCorrectedStimpText = view4.findViewById(R.id.slope_corrected);
        //pastHoleDistanceText = view4.findViewById(R.id.past_hole);
        //homeMakePerc = view4.findViewById(R.id.home_make_per);

        golfball.setTranslationY(100f);
        distanceTarget = 3;
        distanceRange1 = 3;
        distanceRange2 = 5;



        puttPlotBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (puttPlotSession > 0){
                    puttPlotSession--;
                }
                puttPlotSessionText.setText(Long.toString(puttPlotSession));
                clearGridPoints();
                getLastSessionPoints();

            }
        });

        puttPlotForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getLastSessionPoints();

                if (numAccuracySessions > puttPlotSession){

                puttPlotSession++;

                clearGridPoints();
                getLastSessionPoints();

                puttPlotSessionText.setText(Long.toString(puttPlotSession));}
            }
        });


        puttVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playVideo();

            }
        });






        increaseDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (distanceRange2 <25) {
                    increment++;
                    rollBarDistanceText.setVisibility(view4.INVISIBLE);
                    distanceTarget = intArray[increment];
                    distanceRange1 = intArray[increment];
                    distanceRange2 = intArray2[increment];
                    distanceTargetText1.setText(Integer.toString(distanceRange1));
                    distanceTargetText2.setText(Integer.toString(distanceRange2));


                    String distanceRange1Text = String.valueOf(distanceRange1);
                    String distanceRange2Text = String.valueOf(distanceRange2);
                    String rangeString = distanceRange1Text + " to " + distanceRange2Text + " feet";
                    getChartData2(rangeString);

                    targetProgressBar.setProgress(distanceTarget);
                    float targetBarHeight = targetBarLayout.getHeight();
                    float ballPositionX = distanceBarCup.getX();
                    float distanceBarLength = rollDistanceProgressBar.getWidth();
                    //distanceTargetText.setTranslationY(((targetBarHeight-60)/25)*(25 - distanceTarget));
                    //distanceBarCup.setTranslationY(30 + ((targetBarHeight-90)/25)*(25 - distanceTarget));
                    //distanceBarHole.setTranslationY(30 + ((targetBarHeight-90)/25)*(25 - distanceTarget));

                    float lineTranslation = (5 + (distanceBarLength/30)*distanceTarget);
                    ObjectAnimator animation = ObjectAnimator.ofFloat(distanceLine, "translationX", lineTranslation);
                    if(distanceTarget<=6){
                        animation.setDuration(250);
                    }
                    else {
                        animation.setDuration(500);
                    }
                    animation.start();
                        rollBarDistanceText.setText(Integer.toString(distanceTarget));
                        rollBarDistanceText.setTranslationX(lineTranslation);
                        rollBarDistanceText.setVisibility(view4.VISIBLE);

                    //distanceLine.setTranslationX(5 + (distanceBarLength/30)*distanceTarget);
                    golfball.setX(ballPositionX+10);
                    //golfball.setTranslationY(10 + (targetBarHeight/28)*distanceTarget);
                    float ballTranslateY = (10 + (targetBarHeight/28)*distanceTarget);
                    ObjectAnimator animation2 = ObjectAnimator.ofFloat(golfball, "translationY", ballTranslateY);
                    if(distanceTarget<=6){
                        animation2.setDuration(750);
                    }
                    else {
                        animation2.setDuration(1000);
                    }
                    animation2.start();

                    //setNewSession(distanceTarget);
                }

            }});

        decreaseDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (distanceTarget >3) {
                    increment--;
                    //rollBarDistanceText.setVisibility(View.INVISIBLE);
                    distanceTarget = intArray[increment];
                    distanceRange1 = intArray[increment];
                    distanceRange2 = intArray2[increment];
                    distanceTargetText1.setText(Integer.toString(distanceRange1));
                    distanceTargetText2.setText(Integer.toString(distanceRange2));

                    String distanceRange1Text = String.valueOf(distanceRange1);
                    String distanceRange2Text = String.valueOf(distanceRange2);
                    String rangeString = distanceRange1Text + " to " + distanceRange2Text + " feet";
                    getChartData2(rangeString);

                    targetProgressBar.setProgress(distanceTarget);
                    float ballPositionX = distanceBarCup.getX();
                    float targetBarHeight = targetBarLayout.getHeight();
                    float distanceBarLength = rollDistanceProgressBar.getWidth();
                    //distanceTargetText.setTranslationY(((targetBarHeight-60)/25)*(25 - distanceTarget));
                    //distanceBarCup.setTranslationY(30 + ((targetBarHeight-90)/25)*(25 - distanceTarget));
                    //distanceBarHole.setTranslationY(30 + ((targetBarHeight-90)/25)*(25 - distanceTarget));
                    //distanceLine.setTranslationX(5 + (distanceBarLength/30)*distanceTarget);
                    golfball.setX(ballPositionX+10);
                    float lineTranslation = (5 + (distanceBarLength/30)*distanceTarget);
                    ObjectAnimator animation = ObjectAnimator.ofFloat(distanceLine, "translationX", lineTranslation);
                    if(distanceTarget<=6){
                        animation.setDuration(250);
                    }
                    else {
                        animation.setDuration(500);
                    }
                    animation.start();
                        rollBarDistanceText.setText(Integer.toString(distanceTarget));
                        rollBarDistanceText.setTranslationX(lineTranslation);
                        rollBarDistanceText.setVisibility(view4.VISIBLE);

                    float ballTranslateY = (10 + (targetBarHeight/28)*distanceTarget);
                    ObjectAnimator animation2 = ObjectAnimator.ofFloat(golfball, "translationY", ballTranslateY);
                    if(distanceTarget<=6){
                        animation2.setDuration(750);
                    }
                    else {
                        animation2.setDuration(1000);
                    }
                    animation2.start();
                    //golfball.setTranslationY(10 + (targetBarHeight/28)*distanceTarget);
                    // setNewSession(distanceTarget);
                }

            }});




        //puttData1Card.setOnClickListener(this);
        //puttData2Card.setOnClickListener(this);
        //readStimpCard.setOnClickListener(this);
        //drillsCard.setOnClickListener(this);
        //challengesCard.setOnClickListener(this);
        //drill1CheckBox.setOnClickListener(this);
        //drill2CheckBox.setOnClickListener(this);
        //drill3CheckBox.setOnClickListener(this);
        //drill4CheckBox.setOnClickListener(this);
        //drill5CheckBox.setOnClickListener(this);

        /*

        makePercDist = (Button)view4.findViewById(R.id.make_perc_dist);

        makePercDist.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {


                                                showMenu(view);




                                            }
                                        }
        );*/





        return view4;


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

                //firebasePlayer = string;
                //fbDatabase();
            }
        });


        puttViewModel.getFragmentGoogleSignedInAccount().observe(getViewLifecycleOwner(), new Observer<GoogleSignInAccount>() {
            @Override
            public void onChanged(GoogleSignInAccount string) {
                signedInAccountGoogle = string;


            }
        });


        puttViewModel.getFragmentFirebaseUID().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                //userID = string;
               // fbDatabase();


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
                if (rollStoppedFlag == 1){
                    //listenForSpeech();
                }
                //setRollStoppedColor(rollStoppedFlag);
            }
        });



        puttViewModel.getFragmentPuttMadeFlag().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                puttMadeFlag = flag;
                if (puttMadeFlag.equals(true)){

                    setMakePoints(rollDistanceFragment);

                    setMake();

                    recordMakeEvent(rollDistanceFragment);

                }
            }
        });


        puttViewModel.getFragmentStimpCardOpen().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {

                stimpOpen = value;

                if(stimpOpen == 1){
                    stimpInstructions.setVisibility(View.VISIBLE);

                stimpInstructionsDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        stimpInstructions.setVisibility(View.GONE);
                        stimpMeasurements.setVisibility(View.VISIBLE);

                        stimpMeasureClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                stimpMeasurements.setVisibility(View.GONE);
                                puttViewModel.setFragmentStimpCardOpen(0);

                            }
                        });

                    }
                });



                }



            }

        });


        puttViewModel.getFragmentDrillsMenuOpen().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                drillsMenuOpen = value;

                if(drillsMenuOpen == 1) {
                    clearGridPoints();
                }
                if(drillsMenuOpen == 0){
                }

            }

        });


        puttViewModel.getFragmentPastHoleDist().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double distance) {
                pastHoleDistance = distance;

                //setPastHole();

                }

        });


        puttViewModel.getFragmentStimpUpslope().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double stimp) {
                upSlopeStimp = stimp;

                setUpSlopeStimp();

                }

        });


        puttViewModel.getFragmentStimpDownslope().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double stimp) {
                downSlopeStimp = stimp;

                setDownSlopeStimp();

            }

        });


        puttViewModel.getFragmentStimpSlopeCorrected().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double stimp) {
                slopeCorrectedStimp = stimp;

                setSlopeCorrectedStimp();

            }

        });


        puttViewModel.getFragmentTimePuttTotal().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long time) {
                rollTime = time;


            }

        });


        puttViewModel.getFragmentMaxVelocity().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double velocity) {
                maximumVelocity = velocity;


            }

        });



        puttViewModel.getFragmentPlotSessionVideoFlag().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer flag) {
                plotVideoFlag = flag;


            }

        });



        puttViewModel.getFragmentVideoDirectoryPath().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String path) {
                videoDirectoryPath = path;


            }

        });




        puttViewModel.getFragmentPuttAttempt().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (integer == 1){



                    setMissPoints(rollDistanceFragment);

                    setAttempt();

                    recordAttemptEvent(rollDistanceFragment);

                ;}
            }
        });










        puttViewModel.getFragmentVelocityEnd().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double velocity) {
                if (velocity != null) {
                    fragmentVelocity = (int) Math.round(velocity);
                    if (fragmentVelocity <=2){
                        pace = "gentle";
                    }
                    else if (fragmentVelocity <=3){
                        pace = "medium";
                    }
                    else if (fragmentVelocity >3){
                        pace = "firm";
                    }
                    String newVelocity = Integer.toString(fragmentVelocity);
                    //setVelocity(newVelocity);
                }
            }
        });


        puttViewModel.getFragmentVelocity().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double velocity) {
                if (velocity != null) {
                    fragmentVelocity = (int) Math.round(velocity);
                    String newVelocity = Double.toString(velocity);
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


                        ;}
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
        /*
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

        */

        puttViewModel.getFragmentPureRollPercentage().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long percentage) {
                if (percentage != null) {
                    trueRoll = (double)percentage;
                    pureRoll = Long.toString(percentage);
                    if (rollStoppedFlag == 1)
                    {

                    //    setPureRoll(pureRoll);
                    }
                }
            }
        });



        puttViewModel.getFragmentTimeMaxtoStop().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long time) {
                if (time != null) {

                    if(rollStoppedFlag == 1){
                    //setTimeMaxToStop(Long.toString(time));
                    }
                }
            }
        });










    }





    public void setMakePoints(double distance){


        //for(int i = 0; i < 20; i++) {

            gridPoint++;
            ImageView v = (ImageView) grid.getChildAt(gridPoint);
            v.setX(getRandomX());
            v.setY(getRandomY(distance));
            point++;
            puttViewModel.setFragmentGridPoint(point);
            setGridPoint(v.getX(), v.getY(), "made", "right", pace, rollTime);


            //double xRadius = v.getX() - (targetBarLayout.getX() + targetBarLayout.getWidth()/2 + 30);
            //double xRadius = v.getX() - (150);
            //float y =  (float)(Math.sqrt(Math.pow(distance,2) - Math.pow(xRadius,2)));
            //v.setY(y);
            v.getLayoutParams().height = 30;
            v.getLayoutParams().width = 30;

            if(plotVideoFlag == 1){
                v.setImageResource(R.drawable.madeputtspotvideo);
                puttViewModel.setFragmentPlotSessionVideoFlag(0);
            }
            else {
                v.setImageResource(R.drawable.madeputtspot);
            }
        //}



    }


    public void setMissPoints(double distance){


        //for(int i = 0; i < 20; i++) {

        gridPoint++;
        ImageView v = (ImageView) grid.getChildAt(gridPoint);
        v.setX(getRandomX());
        v.setY(getRandomY(distance));
        point++;
        puttViewModel.setFragmentGridPoint(point);
        setGridPoint(v.getX(), v.getY(), "missed", "right", pace, rollTime);
        //double xRadius = v.getX() - (targetBarLayout.getX() + targetBarLayout.getWidth()/2 + 30);
        //double xRadius = v.getX() - (150);
        //float y =  (float)(Math.sqrt(Math.pow(distance,2) - Math.pow(xRadius,2)));
        //v.setY(y);
        v.getLayoutParams().height = 30;
        v.getLayoutParams().width = 30;

        if(plotVideoFlag == 1){
            v.setImageResource(R.drawable.missedputtspotvideo);
            puttViewModel.setFragmentPlotSessionVideoFlag(0);
        }
        else {
            v.setImageResource(R.drawable.missedputtspot);
        }




        //}



    }

    public int getRandomX(){

        int min = 0;
        float minXF = targetBarLayout.getX() + targetBarLayout.getWidth()/2 - 120f;
        int minX = (int)(minXF);
        float maxXF = targetBarLayout.getX() + targetBarLayout.getWidth()/2 + 230f;
        int maxX = (int)(maxXF);
        int max = 300;
        int RandomX;
        RandomX = new Random().nextInt((max - min) + 1) + min;
        return RandomX;
    }

    public int getRandomY(double distance){

        int min = (int)((targetBarLayout.getHeight()/28)*distance) - 10;
        int minY = 0;
        int maxY = 400;
        int max = (int)((targetBarLayout.getHeight()/28)*distance) + 10;
        int RandomY;
        RandomY = new Random().nextInt((max - min) + 1) + min;
        return RandomY;
    }


    public void setRoll(Double distance) {
        String distanceString = Double.toString(distance);
        rollDistancePractice.setText(distanceString + " ft");
        //rollDistanceProgressBar.setX(holeView.getX());
        rollDistanceProgressBar.setAlpha(0.5f);
        //rollDistanceProgressBar.getLayoutParams().width = 100;
        double barHeight = targetBarLayout.getHeight();

        if(distance >= 1 && distance <=25) {
            int integerDistance = distance.intValue();
            if(distance <= distanceTarget){
                rollDistanceProgressBar.setProgress(integerDistance);}
            if(distance > distanceTarget){
                rollDistanceProgressBar.setProgress(distanceTarget);
                rollDistanceProgressBar.setSecondaryProgress(distanceTarget + (integerDistance - distanceTarget) );}
            //rollDataText.setTranslationY((float) (((-barHeight)/25)*(distance)));

        }

        else if (distance > 25) {
            rollDistanceProgressBar.setProgress(25);
            //rollDataText.setTranslationY(0);

        }

        else{

            rollDistanceProgressBar.setProgress(0);
            rollDistanceProgressBar.setSecondaryProgress(0);
            //rollDataText.setTranslationY((float) (barHeight - 200));
        }

    }


    public void setNewSession(Integer distance){

        switch (distance) {
            case 3:

            case 4:

            case 5:

                distanceString = "3 to 5 feet";
                //getChartData(distanceString);

                getLastSessionNum(distanceString);


                mPlayerMakePercentDistRef= mPlayerMakePercentRef.child(distanceString).
                        child("sessions").push();
                makeSessionID1 = mPlayerMakePercentDistRef.getKey();

                mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").child(makeSessionID1).child("attempted").setValue(0);
                mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").child(makeSessionID1).child("made").setValue(0);
                mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").child(makeSessionID1).child("time").setValue(System.currentTimeMillis());






                return;



            case 6:

            case 7:

            case 8:


                distanceString = "6 to 8 feet";
                makeSessionID2 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                mPlayerMakePercentRef.child(makeSessionID2).child("attempted").setValue(0);
                mPlayerMakePercentRef.child(makeSessionID2).child("made").setValue(0);
                getLastSessionNum(distanceString);
                mPlayerMakePercentRef.child(makeSessionID2).child("sessionNum").setValue((numSessions+1));

                return;

            case 9:

            case 10:

            case 11:


                distanceString = "9 - 11 feet";
                makeSessionID3 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                mPlayerMakePercentRef.child(makeSessionID3).child("attempted").setValue(0);
                mPlayerMakePercentRef.child(makeSessionID3).child("made").setValue(0);
                getLastSessionNum(distanceString);
                mPlayerMakePercentRef.child(makeSessionID3).child("sessionNum").setValue((numSessions+1));

                return;

            case 12:

            case 13:

            case 14:

            case 15:

                distanceString = "12 - 15 feet";
                makeSessionID4 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                mPlayerMakePercentRef.child(makeSessionID4).child("attempted").setValue(0);
                mPlayerMakePercentRef.child(makeSessionID4).child("made").setValue(0);
                getLastSessionNum(distanceString);
                mPlayerMakePercentRef.child(makeSessionID4).child("sessionNum").setValue((numSessions+1));

                return;

            case 16:

            case 17:

            case 18:

            case 19:

            case 20:

                distanceString = "16 - 20 feet";
                makeSessionID5 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                mPlayerMakePercentRef.child(makeSessionID5).child("attempted").setValue(0);
                mPlayerMakePercentRef.child(makeSessionID5).child("made").setValue(0);
                getLastSessionNum(distanceString);
                mPlayerMakePercentRef.child(makeSessionID5).child("sessionNum").setValue((numSessions+1));

                return;

            case 21:

            case 22:

            case 23:

            case 24:

            case 25:

                distanceString = "21 - 25 feet";
                makeSessionID2 = mPlayerMakePercentRef.child(distanceString).child("sessions").push().getKey();
                mPlayerMakePercentRef.child(makeSessionID5).child("attempted").setValue(0);
                mPlayerMakePercentRef.child(makeSessionID5).child("made").setValue(0);
                getLastSessionNum(distanceString);
                mPlayerMakePercentRef.child(makeSessionID5).child("sessionNum").setValue((numSessions+1));

                return;





            default:
                return;


    }}



    /*
    public void listenForSpeech(){

        if(drill5Expanded.getVisibility() == view4.VISIBLE){

            final long listenStartTime = System.currentTimeMillis();

            final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {
                    speechText.setText("");
                    speechText.setHint("Listening...");
                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int i) {

                }

                @Override
                public void onResults(Bundle bundle) {

                    ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    speechText.setText(data.get(0));
                    valueActual.setText(Double.toString(rollDistanceFragment));

                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });



            speechRecognizer.startListening(speechRecognizerIntent);

            if((System.currentTimeMillis() - listenStartTime) > 3000){

                speechRecognizer.stopListening();

            }

        }

        //rollDistancePracticeExpanded.setText(Double.toString(distance));


    }
    */

    public void setVelocity(String velocity){

        homeVelocity.setText(velocity + " ft/s");

    }

    /*
    public void setAngle(String angle){

        launchAngle.setText(angle);


    }
    */
    /*
    public void setDirection(String direction){

        launchDirection.setText(direction);

    }
    */

    /*
    public void setPureRoll(String percentage){


        pureRollPercentage.setText(percentage);
        //distGoal = new Random().nextInt((distMax - distMin) + 1) + distMin;
        //distanceGoal.setText(Integer.toString(distGoal));
    }

    */


    public void setTimeMaxToStop(String time){

       // timeMaxtoStop.setText(time);

    }


    private void fbDatabase() {

        mPlayerRef = FirebaseDatabase.getInstance().getReference("players/" + signedInAccountFragment);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser user = mAuthFragment.getCurrentUser();
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){

                    String userName = userProfile.username;
                    Toast.makeText(getContext(), "Hello" + userName, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //below line should be enabled for a single user device to store data when in offline mode
        //mPlayerRef.keepSynced(true);


        //mPlayerStatRef = mPlayerRef.child("stats");
        //mPlayerMakePercentRef = mPlayerStatRef.child("make percentage");
        //mPlayerMakeAccuracyRef = mPlayerStatRef.child("make accuracy");
        //mPlayerDistRef = mPlayerStatRef.child("distance control");

        mPlayerStatRef = reference.child(userID).child("stats");
        mPlayerMakePercentRef = mPlayerStatRef.child("make percentage");
        mPlayerMakeAccuracyRef = mPlayerStatRef.child("make accuracy");
        mPlayerDistRef = mPlayerStatRef.child("distance control");
        getLastAccuracySessionNum();
        getLastSessionPoints();
        //plotGridPoints();





    }

    private void checkForNewSession(){




    }

    /*
    private void getChartData(String distance){

        // Read from the database to make line chart

        makeArray.clear();
        attemptArray.clear();
        sumMakeArray = 0;
        sumAttemptArray = 0;
        if (mPlayerRef != null) {
            mPlayerSessionRef = mPlayerMakePercentRef.child(distance).child("sessions");

            mPlayerSessionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    //dataVals = new ArrayList<BarEntry>();

                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            SessionData1 sessionData = myDataSnapshot.getValue(SessionData1.class);
                            //double makePer = Math.round((sessionData.getMade()/sessionData.getAttempted())*100)/100D;
                            double att = sessionData.getAttempted();
                            double m = sessionData.getMade();
                            long time = sessionData.getTime();
                            makeArray.add(m);
                            attemptArray.add(att);

                        }
                            for(int i = 0; i < makeArray.size(); i++)
                            {
                                sumMakeArray += makeArray.get(i);
                            }

                            for(int i = 0; i < attemptArray.size(); i++)
                            {
                                sumAttemptArray += attemptArray.get(i);
                            }



                            if((System.currentTimeMillis() - DAYMILLI) > sessionData.getTime()){
                                newSession = true;
                            }
                            if((System.currentTimeMillis() - DAYMILLI) < sessionData.getTime()){
                                newSession = false;
                                key = dataSnapshot.getKey();
                            }





                        myAverage = (sumMakeArray/sumAttemptArray)*100;
                        myAverageInt = (int)(myAverage);
                        myMakePercentageAvg.setText(Integer.toString(myAverageInt)+ "%");

                        if(thisSessionAttempt != 0) {
                            sessionAverage = (thisSessionMade / thisSessionAttempt) * 100;
                            sessionAverageInt = (int) (sessionAverage);
                            //myMakePercentageSession.setText(Integer.toString(sessionAverageInt)+"%");
                        }




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

    */
    private void getChartData2(String distance) {
        mPlayerSessionRefHome = mPlayerMakePercentRef.child(distance);
        mPlayerSessionRefHome.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SessionData1 sessionData = snapshot.getValue(SessionData1.class);
                if (sessionData != null) {

                    double att = sessionData.getAttempted();
                    double m = sessionData.getMade();
                    long time = sessionData.getTime();


                    
                    double makePer = (m / att) * 100;
                    int MP = (int) (makePer);


                    myAverageHomeInt = (int) (makePer);
                    myMakePercentageAvg.setText(Integer.toString(myAverageHomeInt)+ "%");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }



    private void getLastSessionNum(String string) {
        mPlayerMakePercentRef.child(string).child("sessions").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        numSessions = dataSnapshot.getChildrenCount();

                        //
                        //if (newSession == true){
                        //    numSessions++;
                        // }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });
    }

    private void getLastAccuracySessionNum(){
        mPlayerMakeAccuracyRef.child("sessions").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        numAccuracySessions = dataSnapshot.getChildrenCount();
                        puttPlotSession = numAccuracySessions;
                        puttPlotSessionText.setText(Long.toString(puttPlotSession));

                        puttViewModel.setFragmentPuttPlotSession(numAccuracySessions);

                        //
                        //if (newSession == true){
                        //    numSessions++;
                        // }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });

    }


    private void getLastSessionPoints() {
        if (mPlayerRef != null) {
            mPlayerMakeAccuracyRefGrid = mPlayerMakeAccuracyRef.child("sessions").child(Long.toString(puttPlotSession)).child("x values");



            mPlayerMakeAccuracyRefGrid.limitToLast(20).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.



                    if (dataSnapshot.hasChildren()) {



                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {


                            GridPointData gridPointData = myDataSnapshot.getValue(GridPointData.class);


                            //double makePer = Math.round((sessionData.getMade()/sessionData.getAttempted())*100)/100D;

                            String putt_num = myDataSnapshot.getKey();
                            float x = gridPointData.getX();
                            float y = gridPointData.getY();
                            String result = gridPointData.getResult();
                            double distance = gridPointData.getDistance();
                            double maxVelocity = gridPointData.getMax_velocity();
                            double true_roll = gridPointData.getPure_roll();
                            int target_distance = gridPointData.getTarget_distance();
                            String pace = gridPointData.getPace();
                            int video = gridPointData.getVideo();

                            gridPointNumArray.add(putt_num);
                            gridPointXArray.add(x);
                            gridPointYArray.add(y);
                            gridPointResultArray.add(result);
                            gridPointDistanceArray.add(distance);
                            gridPointVelocityArray.add(maxVelocity);
                            gridPointTrueRollArray.add(true_roll);
                            gridPointTargetDistanceArray.add(target_distance);
                            gridPointPaceArray.add(pace);
                            gridPointVideoArray.add(video);

                            //thisSessionMade = sessionData.getMade();
                            //thisSessionAttempt = sessionData.getAttempted();
                            //dataVals.add(new BarEntry(sessionData.getSessionNum(), MP));
                        }





                }
                plotGridPoints();

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }}


        public void plotGridPoints() {


            for (gridPoint2 = 0; gridPoint2 < gridPointXArray.size(); gridPoint2++) {
                //gridPoint2++;

                v2 = (ImageView) vGrid.getChildAt(gridPoint2);
                if (v2 != null) {
                v2.setId(gridPoint2);
                v2.setX(gridPointXArray.get(gridPoint2));
                v2.setY(gridPointYArray.get(gridPoint2));

                    v2.getLayoutParams().height = 30;
                    v2.getLayoutParams().width = 30;
                    v2.setElevation(0);
                    if (gridPointResultArray.get(gridPoint2) != null) {
                        if (gridPointResultArray.get(gridPoint2).equals("made") && gridPointVideoArray.get(gridPoint2) == 1) {
                            v2.setImageResource(R.drawable.madeputtspotvideo);
                        }

                        if (gridPointResultArray.get(gridPoint2).equals("made") && gridPointVideoArray.get(gridPoint2) == 0){
                            v2.setImageResource(R.drawable.madeputtspot);
                        }

                        //v2.setElevation(6);
                        //vselected.setImageResource(R.drawable.selectedputtspot);
                        //vselected.setElevation(6);

                        if (gridPointResultArray.get(gridPoint2).equals("missed") && gridPointVideoArray.get(gridPoint2) == 1) {
                            v2.setImageResource(R.drawable.missedputtspotvideo);
                        }
                        if (gridPointResultArray.get(gridPoint2).equals("missed")&& gridPointVideoArray.get(gridPoint2) == 0) {
                            v2.setImageResource(R.drawable.missedputtspot);
                        }
                    }}

                if(v2!=null) {
                    v2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vId = v.getId();
                            if (gridPointData.getVisibility() == View.GONE) {
                                v.setScaleX(2);
                                v.setScaleY(2);
                                v.setElevation(2);

                                gridPointData.setX(v.getX() - 5);
                                gridPointData.setY(v.getY() + 40);
                                gridPointData.setVisibility(View.VISIBLE);
                                gridPointData.setElevation(4);
                                gridPointDataResult.setText(gridPointResultArray.get(vId));
                                gridPointDataDistance.setText(Double.toString(gridPointDistanceArray.get(vId)));
                                gridPointDataMaxVelocity.setText(Double.toString(gridPointVelocityArray.get(vId)));
                                gridPointDataSlope.setText(gridPointNumArray.get(vId));
                                //gridPointDataTrueRoll.setText(Double.toString(gridPointVelocityArray.get(vId)));

                                //Toast.makeText(getContext(),Long.toString(puttPlotSession), Toast.LENGTH_SHORT).show();

                                shrinkDataPoint(v);

                            }
                        }
                    });
                }
            }

        }







    public void clearGridPoints() {


        for (gridPoint2 = 0; gridPoint2 < gridPointXArray.size(); gridPoint2++) {
            v2 = (ImageView) vGrid.getChildAt(gridPoint2);
            v2.setVisibility(view4.GONE);
        }

    }


    public void displayGridPoint(){

        for (gridPoint2 = 0; gridPoint2 < gridPointXArray.size(); gridPoint2++) {
            v2 = (ImageView) vGrid.getChildAt(gridPoint2);
            v2.setVisibility(view4.VISIBLE);
        }


    }
    private void shrinkDataPoint(View v){

        final View dataView = v;
        gridPointData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridPointData.setVisibility(View.GONE);
                dataView.setScaleX(1);
                dataView.setScaleY(1);
                dataView.setElevation(0);

            }
        });


    }


    public void recordAttemptEvent(Double distance){


        switch (distanceTarget) {
            case 3:

            case 4:

            case 5:

                    mAttempts1.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {

                        //String id = mSession1Fragment.push().getKey();

                        //makeSessionID = mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").push().getKey();

                        getLastSessionNum("3 to 5 feet");

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("time").setValue(System.currentTimeMillis());

                        mPlayerMakePercentRef.child("3 to 5 feet").child("attempted").setValue(ServerValue.increment(1));



                }
                break;

            case 6:

            case 7:

            case 8:

                    mAttempts4.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {

                        getLastSessionNum("3 to 5 feet");
                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("time").setValue(System.currentTimeMillis());

                        mPlayerMakePercentRef.child("6 to 8 feet").child("attempted").setValue(ServerValue.increment(1));
                    }

                break;

            case 9:

            case 10:

            case 11:


                    mAttempts7.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("9 to 11 feet").child("sessions").
                                child(makeSessionID3).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("9 to 11 feet").child("sessions").
                                child(makeSessionID3).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("9 to 11 feet").child("attempted").setValue(ServerValue.increment(1));
                    }

                break;

            case 12:

            case 13:

            case 14:

            case 15:


                mAttempts10.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("12 to 15 feet").child("sessions").
                                child(makeSessionID4).child("attempted").setValue(ServerValue.increment(1));
                        mPlayerMakePercentRef.child("12 to 15 feet").child("sessions").
                                child(makeSessionID4).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("12 to 15 feet").child("attempted").setValue(ServerValue.increment(1));
                    }

                break;

            case 16:

            case 17:

            case 18:

            case 19:

            case 20:


                mAttempts14.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("16 to 20 feet").child("sessions").
                                child(makeSessionID5).child("attempted").setValue(ServerValue.increment(1));
                        mPlayerMakePercentRef.child("16 to 20 feet").child("sessions").
                                child(makeSessionID5).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("16 to 20 feet").child("attempted").setValue(ServerValue.increment(1));
                    }

                break;

            case 21:

            case 22:

            case 23:

            case 24:

            case 25:


                mAttempts19.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("21 to 25 feet").child("sessions").child(makeSessionID6).child("attempted").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("20 to 25 feet").child("sessions").
                                child(makeSessionID6).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("21 to 25 feet").child("attempted").setValue(ServerValue.increment(1));
                    }

                break;
            default:
                break;}
        puttViewModel.setFragmentPuttAttempt(0);

    }


    public void recordMakeEvent(Double distance){


        switch (distanceTarget) {
            case 3:

            case 4:

            case 5:
                if (distance > 2.5 && distance <= 5.5) {
                    mMakes1.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {

                        //String id = mSession1Fragment.push().getKey();

                        //makeSessionID = mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").push().getKey();

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("made").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("3 to 5 feet").child("sessions").
                                child(makeSessionID1).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("3 to 5 feet").child("made").setValue(ServerValue.increment(1));



                    }
                }
                break;

            case 6:

            case 7:

            case 8:
                if (distance >5.5 && distance <=8.5) {
                    mMakes4.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("made").setValue(ServerValue.increment(1));

                        mPlayerMakePercentRef.child("6 to 8 feet").child("sessions").
                                child(makeSessionID2).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("6 to 8 feet").child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;

            case 9:

            case 10:

            case 11:

                if (distance >8.5 && distance <=11.5){
                    mMakes7.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("9 to 11 feet").child("sessions").
                                child(makeSessionID3).child("made").setValue(ServerValue.increment(1));
                        mPlayerMakePercentRef.child("9 to 11 feet").child("sessions").
                                child(makeSessionID3).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("9 to 11 feet").child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;

            case 12:

            case 13:

            case 14:

            case 15:

                if (distance >11.5 && distance <=15.5){
                    mMakes10.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("12 to 15 feet").child("sessions").
                                child(makeSessionID4).child("made").setValue(ServerValue.increment(1));
                        mPlayerMakePercentRef.child("12 to 15 feet").child("sessions").
                                child(makeSessionID4).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("12 to 15 feet").child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;

            case 16:

            case 17:

            case 18:

            case 19:

            case 20:

                if (distance >15.5 && distance <=20.5){
                    mMakes14.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("16 to 20 feet").child("sessions").
                                child(makeSessionID5).child("made").setValue(ServerValue.increment(1));
                        mPlayerMakePercentRef.child("16 to 20 feet").child("sessions").
                                child(makeSessionID5).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("16 to 20 feet").child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;

            case 21:

            case 22:

            case 23:

            case 24:

            case 25:

                if (distance >20.5 && distance <=26.5){
                    mMakes19.setValue(ServerValue.increment(1));
                    if (mPlayerRef != null) {
                        mPlayerMakePercentRef.child("21 to 25 feet").child("sessions").
                                child(makeSessionID6).child("made").setValue(ServerValue.increment(1));
                        mPlayerMakePercentRef.child("21 to 25 feet").child("sessions").
                                child(makeSessionID6).child("sessionNum").setValue((numSessions+1));

                        mPlayerMakePercentRef.child("21 to 25 feet").child("made").setValue(ServerValue.increment(1));
                    }
                }
                break;
            default:
                break;}



        puttViewModel.setFragmentPuttMadeFlag(false);

    }




    private void setMake(){

        numberMade++;
        numberAttempt++;
        accuracyScore = accuracyScoreOld + Double.valueOf(rollDistanceFragment).longValue();
        accuracyScoreOld = accuracyScore;
        accuracyScoreText.setText(Long.toString(accuracyScore));
        homeMadeAttempt.setText((Integer.toString(numberMade)) +"/" + Integer.toString(numberAttempt));
        //pastHoleDistanceText.setText(Double.toString(pastHoleDistance));
        if (numberAttempt == 1){
            setNewSession(5);
            setNewSession(8);
            setNewSession(11);
            setNewSession(15);
            setNewSession(20);
            setNewSession(25);
        }

        if(numberAttempt !=0){
            double perc = (numberMade/numberAttempt)*100;
        //homeMakePerc.setText(Double.toString(Math.round((perc)*10)/10D));
        }
        puttViewModel.setFragmentPuttMadeFlag(false);
    }


    public void setGridPoint(float x, float y, String result, String missedSide, String makePace, long time) {
        if (mPlayerRef != null) {

            mPlayerMakeAccuracyRefSession = mPlayerMakeAccuracyRef.child("sessions").
                    child(String.valueOf(numAccuracySessions+1)).child("x values");
            mPlayerMakeAccuracyRef.child("sessions").child(String.valueOf(numAccuracySessions+1)).
                    child("x values");

            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("x").setValue(x);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("y").setValue(y);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("result").setValue(result);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("distance").setValue(rollDistanceFragment);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("target_distance").setValue(distanceTarget);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("miss_side").setValue("right");
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("pace").setValue(makePace);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("slope").setValue("none");
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("roll_time").setValue(time);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("pure_roll").setValue(trueRoll);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("max_velocity").setValue(maximumVelocity);
            mPlayerMakeAccuracyRefSession.child(String.valueOf(point)).child("video").setValue(plotVideoFlag);



        }
    }


    /*
    private void setPastHole(){

        pastHoleDistanceText.setText(Double.toString(pastHoleDistance));

    }


    */
    private void setAttempt(){

        numberAttempt++;
        homeMadeAttempt.setText((Integer.toString(numberMade)) +"/" + Integer.toString(numberAttempt));;
        if(numberAttempt !=0){
            double perc = (numberMade/numberAttempt)*100;
            //homeMakePerc.setText(Double.toString(Math.round((perc)*10)/10D));
        }

        if (numberAttempt == 1){
            setNewSession(3);
            setNewSession(6);
            setNewSession(9);
            setNewSession(12);
            setNewSession(16);
            setNewSession(21);
        }
        puttViewModel.setFragmentPuttAttempt(0);

    }







    private void setUpSlopeStimp(){

        upSlopeStimpText.setText(Double.toString(upSlopeStimp));

    }


    private void setDownSlopeStimp(){

        downSlopeStimpText.setText(Double.toString(downSlopeStimp));


    }




    private void setSlopeCorrectedStimp(){

        slopeCorrectedStimpText.setText(Double.toString(slopeCorrectedStimp));

    }




    private void playVideo(){

        playerView.setVisibility(View.VISIBLE);
        playerView.setElevation(10);
        playerViewClose.setVisibility(View.VISIBLE);
        playerViewClose.setElevation(10);
        rollDistanceProgressBar.setVisibility(View.INVISIBLE);
        distanceLine.setVisibility(View.INVISIBLE);
        rollBarDistanceText.setVisibility(View.INVISIBLE);
        rollBarDistanceZero.setVisibility(View.INVISIBLE);
        rollBarDistanceThirty.setVisibility(View.INVISIBLE);



        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL );
        playerViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.stop();
                player.release();
                playerView.setVisibility(View.GONE);
                playerViewClose.setVisibility(View.GONE);
                rollDistanceProgressBar.setVisibility(View.VISIBLE);
                distanceLine.setVisibility(View.VISIBLE);
                rollBarDistanceText.setVisibility(View.VISIBLE);
                rollBarDistanceZero.setVisibility(View.VISIBLE);
                rollBarDistanceThirty.setVisibility(View.VISIBLE);

            }
        });

        //Toast.makeText(getContext(), Long.toString(puttPlotSession), Toast.LENGTH_SHORT).show();

        String combine = puttPlotSession + gridPointNumArray.get(vId);

        //String combine = "72";

        File directory = new File(getActivity().getFilesDir(), combine);


        //String videodirectorypath = (String)(directory.getAbsolutePath());

        //String file = "/data/data/com.golftronics.golfball.ble/files/" + combine;

        String file = videoDirectoryPath + "/" + combine;



        //   /data/data/com.golftronics.golfball.ble/files/72

        Uri uri = Uri.parse(String.valueOf(directory));

        ExtractorMediaSource audioSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(getContext(), "MyExoplayer"),
                new DefaultExtractorsFactory(),
                null,
                null
        );

        player.prepare(audioSource);
        playerView.setPlayer(player);
        player.setPlayWhenReady(true);

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
        if (context instanceof PracticeFragmentNew.OnFragmentInteractionListener) {
            mListener = (PracticeFragmentNew.OnFragmentInteractionListener) context;



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
    /*
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.putt_data1:

                if (puttData1Plus.getText().equals("+")) {


                    puttData1Plus.setText("-");

                    distanceCard.setTranslationX(-700);
                    distanceCard.setVisibility(View.VISIBLE);
                    distanceCard.animate().translationX(0).setDuration(300);





                break;
                }

                if (puttData1Plus.getText().equals("-")) {


                    puttData1Plus.setText("+");

                    distanceCard.animate().translationX(-700).setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                }
                            });

                    distanceCard.setVisibility(View.GONE);


                break;}

                break;

            case R.id.putt_data_2:

                if (velocityCard.getVisibility() == View.GONE){



                    velocityCard.setTranslationX(-700);
                    velocityCard.setVisibility(View.VISIBLE);
                    velocityCard.animate().translationX(0).setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    pureRollCard.setTranslationX(-700);
                                    pureRollCard.setVisibility(View.VISIBLE);
                                    pureRollCard.animate().translationX(0).setDuration(300)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    pastHoleCard.setTranslationX(-700);
                                                    pastHoleCard.setVisibility(View.VISIBLE);
                                                    pastHoleCard.animate().translationX(0).setDuration(300);

                                                }
                                            });

                                }
                            });



                    puttData2Plus.setText("-");

                break;}


                if (velocityCard.getVisibility() == View.VISIBLE){


                    puttData2Plus.setText("+");
                    velocityCard.setVisibility(View.GONE);
                    pureRollCard.setVisibility(View.GONE);
                    pastHoleCard.setVisibility(View.GONE);

                break;}

                break;

            case R.id.read_stimp:



                if(upslopeStimpCard.getVisibility() == View.GONE) {

                    readStimpPlus.setText("-");
                    upslopeStimpCard.setVisibility(View.VISIBLE);
                    upslopeStimpCard.setTranslationX(-700);

                    upslopeStimpCard.animate().translationX(0).setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    downslopeStimpCard.setVisibility(View.VISIBLE);
                                    downslopeStimpCard.setTranslationX(-700);
                                    downslopeStimpCard.animate().translationX(0).setDuration(300)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    slopeCorrectedStimpCard.setVisibility(View.VISIBLE);
                                                    slopeCorrectedStimpCard.setTranslationX(-700);
                                                    slopeCorrectedStimpCard.animate().translationX(0).setDuration(300);

                                                }
                                            });

                                }
                            });

                    puttViewModel.setFragmentStimpCardOpen(1);


                break;}


                if(upslopeStimpCard.getVisibility() == View.VISIBLE){

                    upslopeStimpCard.setVisibility(View.GONE);
                    downslopeStimpCard.setVisibility(View.GONE);
                    slopeCorrectedStimpCard.setVisibility(View.GONE);
                    readStimpPlus.setText("+");



                    puttViewModel.setFragmentStimpCardOpen(0);

                break;}

                break;

            case R.id.drills:

                if(drill1Minimized.getVisibility() == View.GONE){


                    drill1Minimized.setTranslationX(-700);
                    drill1Minimized.setVisibility(View.VISIBLE);
                    drill1Minimized.animate().translationX(0).setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            drill2Minimized.setTranslationX(-700);
                            drill2Minimized.setVisibility(View.VISIBLE);
                            drill2Minimized.animate().translationX(0).setDuration(300)
                                    .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    drill3Minimized.setTranslationX(-700);
                                    drill3Minimized.setVisibility(View.VISIBLE);
                                    drill3Minimized.animate().translationX(0).setDuration(300)
                                            .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            drill4Minimized.setTranslationX(-700);
                                            drill4Minimized.setVisibility(View.VISIBLE);
                                            drill4Minimized.animate().translationX(0).setDuration(300).
                                                    setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    drill5Minimized.setTranslationX(-700);
                                                    drill5Minimized.setVisibility(View.VISIBLE);
                                                    drill5Minimized.animate().translationX(0).setDuration(300);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                    //drill2Minimized.setVisibility(View.VISIBLE);
                    //drill3Minimized.setVisibility(View.VISIBLE);
                    //drill4Minimized.setVisibility(View.VISIBLE);
                    //drill5Minimized.setVisibility(View.VISIBLE);

                    drillsPlus.setText("-");

                    break;}

                if(drill1Minimized.getVisibility() == View.VISIBLE){
                    drill1Minimized.setVisibility(View.GONE);
                    drill1Minimized.setVisibility(View.GONE);
                    drill2Minimized.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.GONE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill4Expanded.setVisibility(View.GONE);
                    drill5Expanded.setVisibility(View.GONE);

                    drillsPlus.setText("+");

                    break;}


                break;



            case R.id.challenges:

                if(puttLinkChallengesCard.getVisibility() == View.GONE){



                    //drillsCard.setTranslationY(0);
                    //drillsCard.setVisibility(View.VISIBLE);

                            puttLinkChallengesCard.setTranslationX(-700);
                            puttLinkChallengesCard.setVisibility(View.VISIBLE);
                            puttLinkChallengesCard.animate().translationX(0).setDuration(300)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            friendChallengesCard.setTranslationX(-700);
                                            friendChallengesCard.setVisibility(View.VISIBLE);
                                            friendChallengesCard.animate().translationX(0).setDuration(300);

                            drillsCard.setTranslationY(0);
                                        }
                                    });



                    challengesPlus.setText("-");


                break;}

                if(puttLinkChallengesCard.getVisibility() == View.VISIBLE){
                    puttLinkChallengesCard.setVisibility(View.GONE);
                    friendChallengesCard.setVisibility(View.GONE);
                    //drillsCard.animate().translationY(0).setDuration(1000);
                    challengesPlus.setText("+");

                break;}

                break;


            case R.id.make_accuracy_start_btn1:

                if(drill1Expanded.getVisibility() == View.GONE){
                    drill1Expanded.setVisibility(View.VISIBLE);
                    drill1CheckBox.setBackgroundResource(R.drawable.check_box_);

                    drill2Minimized.setVisibility(View.GONE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.GONE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.GONE);
                    drill4Expanded.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.GONE);
                    drill5Expanded.setVisibility(View.GONE);

                    scrolldown(drillsCard);

                    break;}

                if(drill1Expanded.getVisibility() == View.VISIBLE){
                    drill1Expanded.setVisibility(View.GONE);

                    drill2Minimized.setVisibility(View.VISIBLE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.VISIBLE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.VISIBLE);
                    drill4Expanded.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.VISIBLE);
                    drill5Expanded.setVisibility(View.GONE);

                    drill1CheckBox.setBackgroundResource(R.drawable.checkbox_outline);

                    scrollup(drillsCard);

                    break;}


                break;

            case R.id.distance_accuracy_start_btn1:

                if(drill2Expanded.getVisibility() == View.GONE){
                    drill2Expanded.setVisibility(View.VISIBLE);
                    drill2CheckBox.setBackgroundResource(R.drawable.check_box_);

                    drill1Minimized.setVisibility(View.GONE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.GONE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.GONE);
                    drill4Expanded.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.GONE);
                    drill5Expanded.setVisibility(View.GONE);

                    scrolldown(drillsCard);

                    break;}

                if(drill2Expanded.getVisibility() == View.VISIBLE){
                    drill2Expanded.setVisibility(View.GONE);
                    drill2CheckBox.setBackgroundResource(R.drawable.checkbox_outline);

                    drill1Minimized.setVisibility(View.VISIBLE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.VISIBLE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.VISIBLE);
                    drill4Expanded.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.VISIBLE);
                    drill5Expanded.setVisibility(View.GONE);

                    scrollup(drillsCard);

                    break;}


                break;


            case R.id.pace_control_start_btn1:

                if(drill3Expanded.getVisibility() == View.GONE){
                    drill3Expanded.setVisibility(View.VISIBLE);
                    drill3CheckBox.setBackgroundResource(R.drawable.check_box_);

                    drill1Minimized.setVisibility(View.GONE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill2Minimized.setVisibility(View.GONE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.GONE);
                    drill4Expanded.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.GONE);
                    drill5Expanded.setVisibility(View.GONE);

                    scrolldown(drillsCard);

                    break;}

                if(drill3Expanded.getVisibility() == View.VISIBLE){
                    drill3Expanded.setVisibility(View.GONE);
                    drill3CheckBox.setBackgroundResource(R.drawable.checkbox_outline);

                    drill1Minimized.setVisibility(View.VISIBLE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill2Minimized.setVisibility(View.VISIBLE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.VISIBLE);
                    drill4Expanded.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.VISIBLE);
                    drill5Expanded.setVisibility(View.GONE);

                    scrollup(drillsCard);

                    break;}


                break;

            case R.id.pressure_putts_start_btn1:

                if(drill4Expanded.getVisibility() == View.GONE){
                    drill4Expanded.setVisibility(View.VISIBLE);
                    drill4CheckBox.setBackgroundResource(R.drawable.check_box_);

                    drill1Minimized.setVisibility(View.GONE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill2Minimized.setVisibility(View.GONE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.GONE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.GONE);
                    drill5Expanded.setVisibility(View.GONE);

                    scrolldown(drillsCard);

                    break;}

                if(drill4Expanded.getVisibility() == View.VISIBLE){
                    drill4Expanded.setVisibility(View.GONE);
                    drill4CheckBox.setBackgroundResource(R.drawable.checkbox_outline);

                    drill1Minimized.setVisibility(View.VISIBLE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill2Minimized.setVisibility(View.VISIBLE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.VISIBLE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill5Minimized.setVisibility(View.VISIBLE);
                    drill5Expanded.setVisibility(View.GONE);

                    scrollup(drillsCard);

                    break;}


                break;

            case R.id.over_under_start_btn1:

                if(drill5Expanded.getVisibility() == View.GONE){
                    drill5Expanded.setVisibility(View.VISIBLE);
                    //drill5Data.setVisibility(View.GONE);
                    drill5Instructions.setVisibility(View.VISIBLE);
                    drill5CheckBox.setBackgroundResource(R.drawable.check_box_);
                    puttViewModel.setFragmentOverUnderCardOpen(1);


                    drill1Minimized.setVisibility(View.GONE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill2Minimized.setVisibility(View.GONE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.GONE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.GONE);
                    drill4Expanded.setVisibility(View.GONE);

                    drill5Instructions.setAlpha(0f);
                    drill5Instructions.setText(instruction1);
                    drill5Instructions.animate().alpha(1.0f).setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            drill5Instructions.animate().alpha(0f).setDuration(1000)
                                    .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    drill5Instructions.setText(instruction2);
                                    drill5Instructions.animate().alpha(1.0f).setDuration(1000).
                                            setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            //drill5Instructions.setVisibility(View.GONE);
                                        }
                                    });


                                }
                            });

                        }
                    });




                    scrolldown(drillsCard);




                    break;}

                if(drill5Expanded.getVisibility() == View.VISIBLE){
                    drill5Expanded.setVisibility(View.GONE);
                    drill5CheckBox.setBackgroundResource(R.drawable.checkbox_outline);
                    puttViewModel.setFragmentOverUnderCardOpen(0);

                    drill1Minimized.setVisibility(View.VISIBLE);
                    drill1Expanded.setVisibility(View.GONE);
                    drill2Minimized.setVisibility(View.VISIBLE);
                    drill2Expanded.setVisibility(View.GONE);
                    drill3Minimized.setVisibility(View.VISIBLE);
                    drill3Expanded.setVisibility(View.GONE);
                    drill4Minimized.setVisibility(View.VISIBLE);
                    drill4Expanded.setVisibility(View.GONE);

                    scrollup(drillsCard);

                    break;}


                break;




            default:
                break;
        }



    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        //speechRecognizer.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
        }
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

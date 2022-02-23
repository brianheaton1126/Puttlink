/*
Copyright (c) 2020, Golftronics, LLC
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification is not permitted.


THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.



 */

package com.golftronics.golfball.ble;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.camera2.CameraCaptureSession;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;


import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.camera2.internal.CameraCaptureSessionStateCallbacks;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.widget.Toast;

import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.EventsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerLevelInfo;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.games.VideosClient;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.common.api.ApiException;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import android.net.Uri;
import android.os.Bundle;

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

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.StringValue;

import java.util.concurrent.ExecutionException;
import androidx.camera.core.*;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.camera.core.CameraX.getContext;
import static com.golftronics.golfball.ble.BuildConfig.DEBUG;

/**
 * This Activity provides the user interface to control the robot.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */







public class ControlActivity extends AppCompatActivity
        implements BlankFragment.OnFragmentInteractionListener,BlankFragment2.OnFragmentInteractionListener,
        BingoFragment.OnFragmentInteractionListener,ShotClockFragment.OnFragmentInteractionListener,
        PaceFragment.OnFragmentInteractionListener, StatsFragment.OnFragmentInteractionListener, StatisticsFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener, FriendsFragment.OnFragmentInteractionListener, GameFragment.OnFragmentInteractionListener,
        HomeFragmentNew.OnFragmentInteractionListener, PracticeFragmentNew.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener,
        LeaderBoardFragment.OnFragmentInteractionListener, VideoFragment.OnFragmentInteractionListener{



    GoogleSignInClient signInClient;
    GoogleSignInOptions gso;

    PlayerView playerView;

    SimpleExoPlayer player;

    PlayerView startVideo;

    private PuttViewModel puttViewModel;
    private StatViewModel statViewModel;
    private PuttSwingViewModel puttswingViewModel;

    // Objects to access the layout items for Tach, Buttons, and Seek bars
    public static TextView mLastMissedText;
    private static TextView mTachRightText;
    private static TextView mTachMiddleText;
    private static TextView mTachBottomText;
    private static TextView mVelocityText;
    private static TextView mContactAngleText;
    private static TextView mContactDirectionText;
    private static TextView tempo_value;
    private static TextView backswing_length;
    private static TextView mVelocityTextOld;
    private static TextView enterSpeedValue;
    private static Switch mled_switch;
    private static TextView arrow1;
    private static TextView arrow2;
    private static TextView arrow3;
    private static TextView arrow4;
    private static TextView loggedInDisplayName;

    public static TextView mrollDistanceText;
    private static TextView mputtMadeText;
    private static TextView mputtMadeTextOld;
    private static TextView mPowerText;
    private static TextView mVelocityFallOff;
    private static TextView mvelocityMax;
    public static double Velocity2;
    public static double maxAccelx;
    public static double maxAccely;
    public static double maxAccelz;
    public static double xzVector;
    public static double xyVector;

    public static double puttVelocity;
    public static double puttVelocityOld;
    public static int tempoOld;
    public static Button ready_button;
    public static Button stats_button, practice_button, leaderboard_button, events_button;
    public static Button scan_button;
    public static Button target_distance;
    public static Button shotClock;
    public static Button pace_button;
    public static Button read_data_button;
    public static Button graph_button;
    public static Button sign_out;
    public static ToggleButton recordButton;
    public static int puttMadeOld;
    public static double puttRollDistanceOld = 0.0;
    public static int playingSound = 0;
    public int numberRollsOld;
    public static int playingBluetoothDisconnected = 0;
    public static int playingSoundStroke = 0;
    public static int playingReadySound = 0;
    public static int bluetoothDiconnectFlag = 0;
    public static int playingBluetoothConnected = 0;
    public static double longestMadeDistanceRoundOld = 0.0;
    public static double longestMadeDistanceRound;
    public static double puttRollDistanceCompensated;
    public static int playingBadReadingSound = 0;
    public static int puttMadeFlag = 0;
    public static int badReadingFlag = 0;
    public static int ballStoppedFlag = 0;
    public static int puttRollDistanceRoundforSpeech = 0;
    public static TextView totalDistanceMade;
    public String displayName;

    public static double distancePuttsMade = 0;
    public static double distancePuttsMadeOld = 0;

    private static int clock_direction;
    private static int arrowLocation;
    private static int stationComplete;
    private static double clockStationDistance;

    private static int firstBallDistance;
    private static long contactTime = 0;
    private static int backswingAccel;
    private static long backswingTime;
    private static int followthroughAccel;
    private static int contactAccel;
    private static int contactId;
    private static long firstDistanceTime;
    public static long timeToImpact = 0;
    private int directionBLEOld;
    private String directionText;
    private int directionBLE;
    private int directionFlag;
    private int accelY;
    public double accelYAvg;
    private String average_text;
    public double stimpUpSlope = 0.0;
    public double stimpDownSlope = 0.0;
    public double stimpSlopeCorrected;
    public double startStimpDist;
    public double stimpDist;
    public int stimpCardOpen;
    public int overUnderCardOpen;
    public int stimpFlag;

    private boolean screenLockFlag;

    private FrameLayout fragmentContainer;
    private FrameLayout homeLayout;
    public DrawerLayout drillsDrawer;
    public Button drillsNavButton;
    public Button challengeNavButton;
    private ImageView drillsOpenArrow;
    private NavigationView drillsNavView;

    public int puttNumber = 0;
    public double longestMadeDistanceOld = 0.0;
    public Boolean puttIsMade = false;
    public double longestMadeDistanceMax = 0.0;
    public double velocityChangePerFoot = 0.0;
    public double slope = 0.0;
    public String slopeSelected;
    public double velocityFallOff = 0;


    public double madePercentage = 0;
    public int allPuttsToday;
    public int madePuttsVar;
    public static int minDist;
    public static int maxDist;
    public static int stimpMin;
    public static int stimpMax;
    public static final long DAYMILLI = 86400000;
    public static final long WEEKMILLI = 604800000;
    public static final long MONTHMILLI = 2592000000L;
    public static final long YEARMILLI = 31556952000L;
    public long currentMilli;
    public long timeRange;
    public TextView slopeDirection;
    public TextView slopeChoice;
    public String slopeDir;
    public TextView stimpSelected;
    public TextView stimpChosen;
    public double stimpValue;
    private NumberPicker picker3;
    public double valuePicker3=1;
    public static int lastPuttMadeFlag;
    public double puttRollDistanceCompensatedOld;
    public double stimpRoll;
    public double stimpRollRound;
    private int puttStrikeFlag = 0;
    public int puttMadeCount = 0;
    public TextView greetingText;
    public TextView ft;
    public TextView leaderBoard;
    public TextView firebaseAverage;
    public TextView firebaseAverage2;

    public long myrank1 = 0L;
    public long leaderboardScore = 0L;

    public long timeMaxVelocity;
    public long timePuttStart;
    public long timePuttTotal;
    public long timePuttStopped;
    public long pureRollPercentage;
    public String pureRollPercentageText;
    public long pureRollFraction;
    public long roundOff;
    public long timeMaxToStop;
    public int maxVelFlag = 0;
    public int accelBle;
    public double distanceAtMake;
    public double pastHoleDistance;
    public int puttNumberOld;

    public boolean bluetoothConnectFlag = false;


    public int targetSelectDistance;

    public TextView drillSelectionBar;

    public int currentSessionNum;
    public int lastSessionNum;

    public int videoPlotPoint;
    public long videoPlotSession;

    public int recordSwitch;

    public BlankFragment blankFragment;
    public BlankFragment2 blankFragment2;
    public BingoFragment BingoFragment;
    public VideoFragment VideoFragment;
    public ShotClockFragment ShotClockFragment;
    public PaceFragment PaceFragment;
    public LeaderBoardFragment LeaderBoardFragment;
    public FriendsFragment FriendsFragment;


    private View view4;
    PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;



    public String sessionId;

        // This tag is used for debug messages
    private static final String TAG = ControlActivity.class.getSimpleName();

    private static String mDeviceAddress;
    private static BleGolfballService mBleGolfballService;


    public static SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private boolean signInFlag;
    private AchievementsClient mAchievementsClient;
    private LeaderboardsClient mLeaderboardsClient;
    private EventsClient mEventsClient;
    private PlayersClient mPlayersClient;
    public String mDisplayName = "";
    private static final int RC_LEADERBOARD_UI = 9004;
    private static GoogleSignInAccount signedInAccount;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    private static final int RC_VIDEO_OVERLAY = 9011;

    static final int REQUEST_VIDEO_CAPTURE = 1;
    private VideoCapture videoCapture;

    BottomNavigationView navView;



    public static ArrayList<LeaderboardItem> leaderboardlist = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private DatabaseReference mPutts, mScore, mSession, mDistance1,
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
            mMakes22, mMakes23, mPlayer;





    /**
     * This manages the lifecycle of the BLE service.
     * When the service starts we get the service object, initialize the service, and connect.
     */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            mBleGolfballService = ((BleGolfballService.LocalBinder) service).getService();
            if (!mBleGolfballService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the ball upon successful start-up initialization.
            mBleGolfballService.connect(mDeviceAddress);


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBleGolfballService = null;
        }
    };






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        sessionId = getIntent().getStringExtra("1");


        blankFragment = new BlankFragment();
        blankFragment2 = new BlankFragment2();
        BingoFragment = new BingoFragment();
        VideoFragment = new VideoFragment();
        ShotClockFragment = new ShotClockFragment();
        PaceFragment = new PaceFragment();
        LeaderBoardFragment = new LeaderBoardFragment();
        FriendsFragment = new FriendsFragment();


        currentSessionNum = getPreviousSession() + 1;
        setCurrentSession(currentSessionNum);


        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        mAuth = FirebaseAuth.getInstance();


        firebaseDatabase = firebaseDatabase.getInstance();



        myRef = firebaseDatabase.getReference("values");
        mSession = FirebaseDatabase.getInstance().getReference("session");
        mScore =  mSession.child("score");
        mPutts =  FirebaseDatabase.getInstance().getReference("all putts");
        mPlayer = FirebaseDatabase.getInstance().getReference("players");
        mDistance1 = mPutts.child("3 to 5 feet");
        mDistance2 = mPutts.child("3 to 5 feet");
        mDistance3 = mPutts.child("3 to 5 feet");
        mDistance4 = mPutts.child("6 to 8 feet");
        mDistance5 = mPutts.child("6 to 8 feet");
        mDistance6 = mPutts.child("6 to 8 feet");
        mDistance7 = mPutts.child("9 to 11 feet");
        mDistance8 = mPutts.child("9 to 11 feet");
        mDistance9 = mPutts.child("9 to 11 feet");
        mDistance10 = mPutts.child("12 to 15 feet");
        mDistance11 = mPutts.child("12 to 15 feet");
        mDistance12 = mPutts.child("12 to 15 feet");
        mDistance13 = mPutts.child("12 to 15 feet");
        mDistance14 = mPutts.child("16 to 20 feet");
        mDistance15 = mPutts.child("16 to 20 feet");
        mDistance16 = mPutts.child("16 to 20 feet");
        mDistance17 = mPutts.child("16 to 20 feet");
        mDistance18 = mPutts.child("16 to 20 feet");
        mDistance19 = mPutts.child("21 to 25 feet");
        mDistance20 = mPutts.child("21 to 25 feet");
        mDistance21 = mPutts.child("21 to 25 feet");
        mDistance22 = mPutts.child("21 to 25 feet");
        mDistance23 = mPutts.child("21 to 25 feet");
        mAttempts1 = mDistance1.child("attempted");
        mAttempts2 = mDistance2.child("attempted");
        mAttempts3 = mDistance3.child("attempted");
        mAttempts4 = mDistance4.child("attempted");
        mAttempts5 = mDistance5.child("attempted");
        mAttempts6 = mDistance6.child("attempted");
        mAttempts7 = mDistance7.child("attempted");
        mAttempts8 = mDistance8.child("attempted");
        mAttempts9 = mDistance9.child("attempted");
        mAttempts10 = mDistance10.child("attempted");
        mAttempts11 = mDistance11.child("attempted");
        mAttempts12 = mDistance1.child("attempted");
        mAttempts13 = mDistance2.child("attempted");
        mAttempts14 = mDistance3.child("attempted");
        mAttempts15 = mDistance4.child("attempted");
        mAttempts16 = mDistance5.child("attempted");
        mAttempts17 = mDistance6.child("attempted");
        mAttempts18 = mDistance7.child("attempted");
        mAttempts19 = mDistance8.child("attempted");
        mAttempts20 = mDistance9.child("attempted");
        mAttempts21 = mDistance10.child("attempted");
        mAttempts22 = mDistance11.child("attempted");
        mAttempts23 = mDistance11.child("attempted");

        mMakes1 = mDistance1.child("made");
        mMakes2 = mDistance2.child("made");
        mMakes3 = mDistance3.child("made");
        mMakes4 = mDistance4.child("made");
        mMakes5 = mDistance5.child("made");
        mMakes6 = mDistance6.child("made");
        mMakes7 = mDistance7.child("made");
        mMakes8 = mDistance8.child("made");
        mMakes9 = mDistance9.child("made");
        mMakes10 = mDistance10.child("made");
        mMakes11 = mDistance11.child("made");
        mMakes12 = mDistance1.child("made");
        mMakes13 = mDistance2.child("made");
        mMakes14 = mDistance3.child("made");
        mMakes15 = mDistance4.child("made");
        mMakes16 = mDistance5.child("made");
        mMakes17 = mDistance6.child("made");
        mMakes18 = mDistance7.child("made");
        mMakes19 = mDistance8.child("made");
        mMakes20 = mDistance9.child("made");
        mMakes21 = mDistance10.child("made");
        mMakes22 = mDistance11.child("made");
        mMakes23 = mDistance11.child("made");





        

        currentMilli = System.currentTimeMillis();

        setContentView(R.layout.activity_control);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        homeLayout = findViewById(R.id.home_layout);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        navView = findViewById(R.id.nav_view);
        navView.setVisibility(GONE);
        fragmentContainer.setVisibility(GONE);
        homeLayout.setVisibility(VISIBLE);



        startVideo = findViewById(R.id.video_view);





        //final RelativeLayout layout = findViewById(R.id.relative_layout);



        previewView = findViewById(R.id.previewView);

        loggedInDisplayName = (TextView)findViewById(R.id.Display_name);

        drillSelectionBar = findViewById(R.id.drill_selection);

        slopeDirection = (TextView) findViewById(R.id.slope_direction);
        registerForContextMenu(slopeDirection);

        slopeChoice = (TextView) findViewById(R.id.slope_choice);

        stimpSelected = (TextView) findViewById(R.id.stimp_select);
        registerForContextMenu(stimpSelected);

        stimpChosen = (TextView) findViewById(R.id.stimp_chosen);


        mLastMissedText = (TextView) findViewById(R.id.last_missed);

        enterSpeedValue = (TextView) findViewById(R.id.enter_speed_value);

        arrow1 = (TextView) findViewById(R.id.arrow1);

        greetingText = (TextView) findViewById(R.id.greeting);

        // Set the dimensions of the sign-in button.
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        recordButton = findViewById(R.id.record_button);

        sign_out = findViewById(R.id.sign_out_button);

        drillsDrawer = findViewById(R.id.drawer_layout);

        drillsNavButton = findViewById(R.id.drills_nav_button);

        drillsOpenArrow = findViewById(R.id.drills_open_arrow);

        drillsNavView = findViewById(R.id.drill_drawer);

        challengeNavButton = findViewById(R.id.challenges_nav_button);

        leaderBoard = (TextView) findViewById(R.id.leaderboard);

        practice_button = (Button) findViewById(R.id.practice_button);
        stats_button = (Button) findViewById(R.id.stats_button);
        leaderboard_button = (Button) findViewById(R.id.leaderboard_button);
        events_button = (Button) findViewById(R.id.events_button);


        navView.setOnNavigationItemSelectedListener(navListener);

        setSupportActionBar(myToolbar);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playVideo();


        //firebaseAverage = (TextView) findViewById(R.id.firebase_average);
        // 2 = (TextView) findViewById(R.id.firebase_average2);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInIntent();
            }
        });


        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        recordButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startPreview();
                    recordButton.setAlpha(1);
                } else {
                    previewView.setVisibility(GONE);
                    recordButton.setAlpha(.5f);
                }
            }
        });


        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLeaderboard();
            }
        });

        drillsNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!drillsDrawer.isDrawerOpen(GravityCompat.END)) {
                    drillsDrawer.openDrawer(GravityCompat.END);
                    drillsOpenArrow.setRotation(180);
                    puttViewModel.setFragmentDrillMenuOpen(1);
                    disableNavigationViewScrollbars(drillsNavView );
                    //drillsDrawer.setElevation(2);


                } else {
                    drillsDrawer.closeDrawer(GravityCompat.END);
                    drillsOpenArrow.setRotation(0);
                    drillsDrawer.setElevation(0);
                    puttViewModel.setFragmentDrillMenuOpen(0);
                }
            }
        });

        drillsNavView.setNavigationItemSelectedListener(this);



        arrowLocation = 1;


        /*
        leaderboardlist.add(new LeaderboardItem("1", "Jack Sparrow", "100"));
        leaderboardlist.add(new LeaderboardItem("2", "Tiger Woods", "99"));
        leaderboardlist.add(new LeaderboardItem("3", "Brian Heaton", "80"));
        leaderboardlist.add(new LeaderboardItem("4", "Luke Skywalker", "75"));
        leaderboardlist.add(new LeaderboardItem("5", "Puppy", "65"));
        leaderboardlist.add(new LeaderboardItem("6", "Donald Duck", "0"));

        */

        puttViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(PuttViewModel.class);


        statViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(StatViewModel.class);

        puttswingViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(PuttSwingViewModel.class);



        StatData statData = new StatData(System.currentTimeMillis(), 1,"Isabella", 20, 10, 5, 1.5, "home");

        statViewModel.insert(statData);


        PuttSwingData swingData = new PuttSwingData(System.currentTimeMillis(), 1, "Michael", -1, 0, 0);

        puttswingViewModel.insert(swingData);


        /*puttViewModel.getAllPutts().observe(this, new Observer<List<PuttData>>() {
            @Override
            public void onChanged(List<PuttData> puttData) {


            }




        });*/



        puttViewModel.getAllPuttsOverMinDist(minDist, maxDist).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer allPuttsOverMinDist) {

                    //mLastMissedText.setText(String.valueOf(allPutts));

                    puttViewModel.setAllPuttsOverMinDist(allPuttsOverMinDist);



                }
            });




        /*puttViewModel.getAllMadePutts(minDist, timeRange).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePutts) {

                puttViewModel.setMadePuttsToday(madePutts);


            }
        });*/

        puttViewModel.getMadePutts(timeRange, minDist, maxDist).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePutts) {

                puttViewModel.setMadePutts(madePutts);


            }
        });


        puttViewModel.getAllMadePutts(minDist, maxDist).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer allMadePutts) {

                puttViewModel.setAllMadePutts(allMadePutts);


            }
        });





        puttViewModel.getPuttsOverMinDistByDate(timeRange, minDist, maxDist).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePuttsOverMinByDate) {

                puttViewModel.setPuttsOverMinDistByDate(madePuttsOverMinByDate);


            }
        });


        // read database for distance, time range, and slope

        puttViewModel.getPuttsOverMinDistByDateWithSlope(timeRange, minDist, maxDist, slopeDir).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePuttsOverMinByDate) {

                puttViewModel.setPuttsOverMinDistByDateWithSlope(madePuttsOverMinByDate);


            }
        });

        puttViewModel.getMadePuttsWithSlope(timeRange, minDist, maxDist, slopeDir).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePutts) {

                puttViewModel.setMadePuttsWithSlope(madePutts);


            }
        });



        //read database for distance, time range, and stimp


        puttViewModel.getPuttsOverMinDistByDateWithStimp(timeRange, minDist, maxDist, stimpMin, stimpMax).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePuttsOverMinByDateWithStimp) {

                puttViewModel.setPuttsOverMinDistByDateWithStimp(madePuttsOverMinByDateWithStimp);


            }
        });

        puttViewModel.getMadePuttsWithStimp(timeRange, minDist, maxDist, stimpMin, stimpMax).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePutts) {

                puttViewModel.setMadePuttsWithStimp(madePutts);


            }
        });






        // read database for distance, time range, slope, and stimp

        puttViewModel.getPuttsOverMinDistByDateWithSlopeWithStimp(timeRange, minDist, maxDist, slopeDir, stimpMin, stimpMax).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePuttsOverMinByDate) {

                puttViewModel.setPuttsOverMinDistByDateWithSlopeWithStimp(madePuttsOverMinByDate);


            }
        });

        puttViewModel.getMadePuttsWithSlopeWithStimp(timeRange, minDist, maxDist, slopeDir, stimpMin, stimpMax).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer madePutts) {

                puttViewModel.setMadePuttsWithSlopeWithStimp(madePutts);


            }
        });



        //read database for Velocity of last made

        puttViewModel.getVelocityLastMade().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double velocityLastMade) {

                puttViewModel.setFragmentVelocity(velocityLastMade);


            }
        });



        //read database for Average Velocity of putts made

        puttViewModel.getAverageVelocityPuttsMade().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float averageVelocityPuttsMade) {

                puttViewModel.setAverageVelocityPuttsMade(averageVelocityPuttsMade);


            }
        });


        //read database for last session number then increment one for new session


        puttViewModel.getLastSessionNum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer lastSession) {



                puttViewModel.setLastSessionNum(lastSessionNum);


            }
        });


        puttViewModel.getFragmentPuttPlotSession().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long session) {

                videoPlotSession = session + 1;

            }
        });



        puttViewModel.getFragmentGridPoint().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer point) {

                videoPlotPoint = point;

            }
        });





        // read database for made status of last entry


        /*puttViewModel.getLastEntryMadeStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer lastEntryMadeStatus) {

                puttViewModel.setLastEntryMadeStatus(lastEntryMadeStatus);

                lastPuttMadeFlag = lastEntryMadeStatus;

            }
        });*/



        // read database for distance, time range, and slope







        /*puttViewModel.getAllLongPutts().observe(this , new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {


                mLastMissedText.setText(integer);

            }
        });*/

        /*mLastMissedText.setText(String.valueOf(allPuttsVar));*/

        /*puttViewModel.(allPuttsToday);*/






        // Assign the various layout objects to the appropriate variables
        mLastMissedText = (TextView) findViewById(R.id.last_missed);
        mTachRightText = (TextView) findViewById(R.id.tach_right);
        mTachMiddleText = (TextView) findViewById(R.id.tach_middle);
        mTachBottomText = (TextView) findViewById(R.id.tach_bottom);
        mVelocityText = (TextView) findViewById(R.id.Velocity);
        mputtMadeText = (TextView) findViewById(R.id.puttMade);
        mPowerText = (TextView) findViewById(R.id.Power);
        mrollDistanceText = (TextView) findViewById(R.id.rollDistance);
        mVelocityFallOff = (TextView) findViewById(R.id.velocity_falloff);
        mvelocityMax = (TextView) findViewById(R.id.velocity_max);
        mContactAngleText = (TextView) findViewById(R.id.contact_angle);
        mContactDirectionText = (TextView) findViewById(R.id.contact_direction);
        totalDistanceMade = (TextView) findViewById(R.id.total_made_distance);

        mled_switch = (Switch) findViewById(R.id.led_switch);
        ready_button = (Button) findViewById(R.id.ready_button);
        practice_button = (Button) findViewById(R.id.practice_button);
        stats_button = (Button) findViewById(R.id.stats_button);
        leaderboard_button = (Button) findViewById(R.id.leaderboard_button);
        events_button = (Button) findViewById(R.id.events_button);
        scan_button = (Button) findViewById(R.id.scan);
        target_distance = (Button) findViewById(R.id.target_distance);
        shotClock = (Button) findViewById(R.id.shot_clock_button);
        pace_button = (Button) findViewById(R.id.pace_button);
        read_data_button = (Button) findViewById(R.id.readData);
        graph_button = (Button) findViewById(R.id.graph_button);

        tempo_value = (TextView) findViewById(R.id.tempo_value);
        backswing_length = (TextView) findViewById(R.id.backswing_length);

        final Intent intent = getIntent();
        mDeviceAddress = intent.getStringExtra(ScanActivity.EXTRAS_BLE_ADDRESS);



        //fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container2);





        picker3 = (NumberPicker) findViewById(R.id.number_picker3);
        picker3.setMaxValue(25);
        picker3.setMinValue(1);
        picker3.setValue(1);

        picker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                valuePicker3 = (double) picker3.getValue();
                //minDist = valuePicker1;

                picker3.setWrapSelectorWheel(false);
            }
        });






        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.slope_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.getSelectedItem();


        

        AudioManager audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0);






        // Bind to the BLE service
        Log.i(TAG, "Binding Service");
        Intent RobotServiceIntent = new Intent(this, BleGolfballService.class);
        bindService(RobotServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        /* This will be called when the LED On/Off switch is touched */
        mled_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                mBleGolfballService.writeLedCharacteristic(isChecked);
            }
        });

        ready_button.setOnClickListener(new View.OnClickListener()
        {



            public void onClick(View v){


                if(bluetoothConnectFlag){
                mBleGolfballService.writeReadyCharacteristic(true);}
                else{
                    openScanActivity();
                }

            }
        });


        stats_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_control);
                openFragment7();
            }
        });

        practice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.release();
                homeLayout.setVisibility(GONE);
                fragmentContainer.setVisibility(VISIBLE);
                navView.setVisibility(VISIBLE);
                openFragment8();
            }
        });

        leaderboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_control);
                openFragment10();
            }
        });

        events_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        target_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment3();
            }
        });

        shotClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment4();
            }
        });

        pace_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment5();
            }
        });

        graph_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFragment6();
            }
        });

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScanActivity();
            }
        });


        read_data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importDataFile();
            }
        });


        if(puttMadeFlag == 1){


            Toast.makeText(this, "test",Toast.LENGTH_SHORT).show();

        }


    startClockDrill();



    } /* End of onCreate method */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.drill_1:

                //drillSelectionBar.setText("Accuracy Drill");
                drillsNavButton.setText("A\nC\nC\nU\nR\nA\nC\nY");
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        //new MessageFragment()).commit();
                break;
            case R.id.drill_2:

                //drillSelectionBar.setText("Distance Drill");
                drillsNavButton.setText("D\nI\nS\nT\nA\nN\nC\nE");
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        //new ChatFragment()).commit();
                break;
            case R.id.drill_3:
                //drillSelectionBar.setText("Pace Drill");
                drillsNavButton.setText("P\nA\nC\nE");
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        //new ProfileFragment()).commit();
                break;
            case R.id.drill_4:
                //drillSelectionBar.setText("Pressure Drill");
                drillsNavButton.setText("P\nR\nE\nS\nS\nU\nR\nE");
                //Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drill_5:
                //drillSelectionBar.setText("Feel Drill");
                drillsNavButton.setText("F\nE\nE\nL");
                //Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }

        drillsDrawer.closeDrawer(GravityCompat.END);
        drillsOpenArrow.setRotation(0);


        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.navigation_stats:


                            ready_button.setVisibility(View.VISIBLE);
                            sign_out.setVisibility(GONE);
                            openFragment7();
                            break;
                        case R.id.navigation_stimp:
                            openStimp();
                            break;
                        case R.id.navigation_leaderboards:
                            //showLeaderboard();
                            openFragment10();
                            break;
                        case R.id.navigation_practice:
                            ready_button.setVisibility(View.VISIBLE);
                            sign_out.setVisibility(View.VISIBLE);


                            openFragment8();
                            break;
                        case R.id.navigation_notifications:
                            ready_button.setVisibility(View.INVISIBLE);
                            fragmentContainer.setVisibility(GONE);
                            homeLayout.setVisibility(VISIBLE);
                            navView.setVisibility(GONE);
                            player = ExoPlayerFactory.newSimpleInstance(
                                    new DefaultRenderersFactory(getApplicationContext()),
                                    new DefaultTrackSelector(), new DefaultLoadControl());
                            playVideo();
                            //openFragment9();

                            break;



                         // add cases for other menu items here


                    }



                    return true;

                }
            };


     @Override
    protected void onStart() {



         //openFragment11();
         //ready_button.setVisibility(View.VISIBLE);

        super.onStart();



        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.

        /*
         signedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signedInAccount != null){
            //signInButton.setVisibility(View.GONE);
            loggedInDisplayName.setText(signedInAccount.getDisplayName());
            Log.d(TAG, "Google login" + signedInAccount.getDisplayName());
        }
        */
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                signedInAccount = account;

                puttViewModel.setFragmentGoogleSignedInAccount(signedInAccount);



                onConnected(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(displayName != null){
                            mPlayer.child(displayName);
                            setupFB();
                            puttViewModel.setFragmentSignedInAccount(displayName);
                            mPlayer.child(displayName).child("online").setValue("yes");}


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast toast = Toast.makeText(getApplicationContext(),"Sign-in Failed",Toast.LENGTH_SHORT);

                            toast.show();




                        }
                    }
                });
    }


    public void setupFB(){

        mPlayer.child(displayName).child("stats");
        mPlayer.child(displayName).child("stats").child("distance control");
        mPlayer.child(displayName).child("stats").child("make accuracy");
        mPlayer.child(displayName).child("stats").child("make percentage");
        mPlayer.child(displayName).child("friends");
        mPlayer.child(displayName).child("game invites");
    }


/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                signedInAccount = result.getSignInAccount();
                onConnected(signedInAccount);

            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }*/


    private void onConnected(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "onConnected(): connected to Google APIs");

        mAchievementsClient = Games.getAchievementsClient(this, googleSignInAccount);
        mLeaderboardsClient = Games.getLeaderboardsClient(this, googleSignInAccount);
        mEventsClient = Games.getEventsClient(this, googleSignInAccount);
        mPlayersClient = Games.getPlayersClient(this, googleSignInAccount);

        //loadScoreOfLeaderBoard();

        // Set the greeting appropriately on main menu
        mPlayersClient.getCurrentPlayer()
                .addOnCompleteListener(new OnCompleteListener<Player>() {
                    @Override
                    public void onComplete(@NonNull Task<Player> task) {

                        if (task.isSuccessful()) {
                            displayName = task.getResult().getDisplayName();

                        } else {
                            Exception e = task.getException();
                            //handleException(e, getString(R.string.players_exception));
                            displayName = "???";
                        }
                        mDisplayName = displayName;

                        //Toast toast = Toast.makeText(getApplicationContext(),"Hello " + displayName + " !",Toast.LENGTH_LONG);
                        //toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.START,90,100);
                        //toast.show();

                        loggedInDisplayName.setText(displayName);

                        signInButton.setVisibility(GONE);
                        sign_out.setVisibility(View.VISIBLE);

                        //mrollDistanceText.setVisibility(View.VISIBLE);
                        //ft.setVisibility(View.VISIBLE);


                        mAttempts1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                    //firebaseAverage.setText(String.valueOf(task.getResult().getValue()));
                                }
                            }
                        });


                        // Read from the database
                        mAttempts1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                String value = String.valueOf(dataSnapshot.getValue());
                                Log.d(TAG, "Value is: " + value);


                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w(TAG, "Failed to read value.", error.toException());
                            }
                        });
                    }
                });




    }












    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        if(view.getId()==R.id.slope_direction) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.slope_menu_main, menu);
        }

        if(view.getId()==R.id.stimp_select) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.stimp_menu_main, menu);

        }

    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.level:
                slopeChoice.setText("Level");
                slopeDir = "Level";
                return true;
            case R.id.up:
                slopeChoice.setText("UP");
                slopeDir = "UP";
                return true;
            case R.id.down:
                slopeChoice.setText("Down");
                slopeDir = "DN";
                return true;
            case R.id.LR:
                slopeChoice.setText("Left-to-right");
                slopeDir = "LR";
                return true;
            case R.id.RL:
                slopeChoice.setText("Right-to-left");
                slopeDir = "RL";
                return true;
            case R.id.stimp_8_0:
                stimpChosen.setText("8.0");
                stimpValue = 8.0;
                return true;
            case R.id.stimp_8_5:
                stimpChosen.setText("8.5");
                stimpValue = 8.5;
                return true;
            case R.id.stimp_9_0:
                stimpChosen.setText("9.0");
                stimpValue = 9.0;
                return true;
            case R.id.stimp_9_5:
                stimpChosen.setText("9.5");
                stimpValue = 9.5;
                return true;
            case R.id.stimp_10_0:
                stimpChosen.setText("10.0");
                stimpValue = 10.0;
                return true;
            case R.id.stimp_10_5:
                stimpChosen.setText("10.5");
                stimpValue = 10.5;
                return true;
            case R.id.stimp_11_0:
                stimpChosen.setText("11.0");
                stimpValue = 11.0;
                return true;
            case R.id.stimp_11_5:
                stimpChosen.setText("11.5");
                stimpValue = 11.5;
                return true;
            case R.id.stimp_12_0:
                stimpChosen.setText(  "12.0");
                stimpValue = 12.5;
                return true;
            case R.id.stimp_12_5:
                stimpChosen.setText("12.5");
                stimpValue = 12.5;
                return true;
            case R.id.stimp_13_0:
                stimpChosen.setText("13.0");
                stimpValue = 13.0;
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

        public void openScanActivity(){
        Intent scanIntent = new Intent(this, ScanActivity.class);
        startActivity(scanIntent);
    }

    public void openFragment(){
        BlankFragment fragment = BlankFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); //
        transaction.commit();



    }

    public void openFragment2(){
        BlankFragment2 fragment2 = BlankFragment2.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment2);
        transaction.addToBackStack(null); //
        transaction.commit();



    }

    public void openFragment3(){
        BingoFragment fragment3 = BingoFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment3);
        transaction.addToBackStack(null); //
        transaction.commit();



    }


    public void openFragment4(){
        ShotClockFragment fragment4 = ShotClockFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment4);
        transaction.addToBackStack(null); //
        transaction.commit();



    }

    public void openFragment5(){
        PaceFragment fragment5 = PaceFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment5);
        transaction.addToBackStack(null); //
        transaction.commit();



    }


    public void openFragment6(){
        StatsFragment fragment6 = StatsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment6);
        transaction.addToBackStack(null); //
        transaction.commit();



    }

    public void openStimp(){

        puttViewModel.setFragmentStimpCardOpen(1);



    }


    public void openFragment7(){
        navView.setVisibility(VISIBLE);
        StatisticsFragment fragment7 = StatisticsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment7);
        transaction.addToBackStack(null); //
        transaction.commit();



    }


    public void openFragment8(){
        navView.setVisibility(VISIBLE);

        PracticeFragmentNew fragment8 = PracticeFragmentNew.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment8);
        drillsNavButton.setText("D\nR\nI\nL\nL\nS");
        transaction.addToBackStack(null); //
        transaction.commit();



    }


    public void openFragment9(){
        FriendsFragment fragment9 = FriendsFragment.newInstance();
        mPlayer.child(displayName);
        puttViewModel.setFragmentSignedInAccount(displayName);
        mPlayer.child(displayName).child("online").setValue("yes");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment9);
        transaction.addToBackStack(null); //
        transaction.commit();



    }

    public void openFragment10(){
        navView.setVisibility(VISIBLE);
        LeaderBoardFragment fragment10 = LeaderBoardFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment10);
        transaction.addToBackStack(null); //
        transaction.commit();



    }


    public void openFragment11(){
        navView.setVisibility(GONE);
        HomeFragment fragment11 = HomeFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment11);
        transaction.addToBackStack(null); //
        transaction.commit();



    }








    @Override
    public void onFragmentInteraction(Uri uri) {
        onBackPressed();
    }






    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mRobotUpdateReceiver, makeRobotUpdateIntentFilter());
        if (mBleGolfballService != null) {
            final boolean result = mBleGolfballService.connect(mDeviceAddress);
            Log.i(TAG, "Connect request result=" + result);

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playVideo();
        }



    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
        player.release();
        unregisterReceiver(mRobotUpdateReceiver);
    }









    /**
     * Handle broadcasts from the ball service object. The events are:
     * ACTION_CONNECTED: connected to the ball.
     * ACTION_DISCONNECTED: disconnected from the ball.
     * ACTION_DATA_AVAILABLE: received data from the ball.  This can be a result of a read
     * or notify operation.
     */
    private final BroadcastReceiver mRobotUpdateReceiver = new BroadcastReceiver() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            switch (action) {
                case BleGolfballService.ACTION_CONNECTED:
                    // No need to do anything here. Service discovery is started by the service.
                    playingBluetoothDisconnected = 0;
                    bluetoothConnectFlag = true;
                    if (bluetoothDiconnectFlag == 1 && playingBluetoothConnected == 0) {
                        final MediaPlayer bluetoothconnected = MediaPlayer.create(getApplicationContext(), R.raw.bluetoothconnected);


                        if (!bluetoothconnected.isPlaying()) {
                            bluetoothconnected.start();
                        }

                        playingBluetoothConnected = 1;
                        bluetoothDiconnectFlag = 0;
                    }



                    break;
                case BleGolfballService.ACTION_DISCONNECTED:
                    /**mPSoCBleRobotService.close();*/

                    bluetoothDiconnectFlag = 1;
                    bluetoothConnectFlag = false;
                    ready_button.setText("Ball not connected");
                    if (playingBluetoothDisconnected == 0) {
                        final MediaPlayer bluetoothdisconnected = MediaPlayer.create(getApplicationContext(), R.raw.bluetoothdisconnected);
                        playingBluetoothDisconnected = 1;
                        playingBluetoothConnected = 0;

                        if (!bluetoothdisconnected.isPlaying()) {
                            bluetoothdisconnected.start();
                        }
                    }
                    mBleGolfballService.connect(mDeviceAddress);
                    break;
                case BleGolfballService.ACTION_DATA_AVAILABLE:
                    // This is called after a Notify completes
                    //mLastMissedText.setText(String.format("%d", (BleGolfballService.getTach(BleGolfballService.Motor.LEFT) / 2)));
                    //mTachRightText.setText(String.format("%d", BleGolfballService.getTach(BleGolfballService.Motor.RIGHT)));


                    puttViewModel.getFragmentTargetDistance().observe(ControlActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer value) {
                            targetSelectDistance = value;

                        }
                    });

                    stimpRoll = ((double) BleGolfballService.getTach(BleGolfballService.Motor.RIGHT))*0.44;
                    stimpRollRound = Math.round(stimpRoll * 10) / 10D;
                    String stimpRollRoundText = Double.toString(stimpRollRound);
                    //mTachRightText.setText(stimpRollRoundText);

                    puttViewModel.getFragmentStimpCardOpen().observe(ControlActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer state) {
                            stimpCardOpen = state;
                            if (stimpCardOpen == 1){
                                drillSelectionBar.setText("Stimp");
                            }
                            if (stimpCardOpen == 0){
                                drillSelectionBar.setText("Accuray Drill");
                            }

                        }
                        });


                    puttViewModel.getFragmentOverUnderCardOpen().observe(ControlActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer state) {
                            overUnderCardOpen = state;

                        }
                    });





                    directionBLE = (int)(BleGolfballService.getTach(BleGolfballService.Motor.MIDDLE));
                    accelY = (int)(BleGolfballService.getTach(BleGolfballService.Motor.TEMPO));
                    //accelBle = (BleGolfballService.getTach((BleGolfballService.Motor.TEMPO)));
                    //tempo_value.setText(Integer.toString(accelBle));
                    //mContactAngleText.setText(Integer.toString(directionBLE));
                    //recordPuttData();

                    if  (directionBLE == 0) {

                        mContactDirectionText.setText("");
                        //mContactAngleText.setText(("   " + "\u00B0"));



                    }

                    if (directionBLE != directionBLEOld) {

                        //mContactAngleText.setText(Integer.toString(directionBLE));
                        //recordPuttData();


                        if (directionBLE > 90) {
                            directionFlag = 0;
                            directionText = "Left";
                            mContactDirectionText.setText(directionText);
                            //mContactAngleText.setText((Integer.toString(directionBLE - 90)) + "\u00B0");
                            timePuttStart = System.currentTimeMillis();
                            //puttViewModel.setFragmentPuttLaunchAngle(directionBLE-90);
                            puttViewModel.setFragmentPuttLaunchDirection("L");
                            puttStrikeFlag = 1;
                            recordPuttData();
                            //recordPuttSwingData();


                        }
                        if (directionBLE < 90 && directionBLE != 0) {
                            directionFlag = 1;
                            directionText = "Right";
                            mContactDirectionText.setText(directionText);
                            //mContactAngleText.setText((Integer.toString(90 - directionBLE)) + "\u00B0");
                            timePuttStart = System.currentTimeMillis();
                            //puttViewModel.setFragmentPuttLaunchAngle(90 - directionBLE);
                            puttViewModel.setFragmentPuttLaunchDirection("R");

                            puttStrikeFlag = 1;
                            recordPuttData();
                            //recordPuttSwingData();


                        }

                        if (directionBLE == 90) {
                            String directionText = "";
                            mContactDirectionText.setText(directionText);
                            //mContactAngleText.setText("0" + "\u00B0");

                            //puttViewModel.setFragmentPuttLaunchAngle(0);

                            puttStrikeFlag = 1;
                            recordPuttData();
                            //recordPuttSwingData();

                        }

                        directionBLEOld = directionBLE;

                    }



                    //mContactAngleText.setText(String.format("%d", BleGolfballService.getTach(BleGolfballService.Motor.MIDDLE)));


                    double puttRollDistance = (double) BleGolfballService.getTach(BleGolfballService.Motor.LEFT) * 0.22;
                    double puttRollDistanceRound = Math.round(puttRollDistance * 10) / 10D;
                    puttRollDistanceCompensated = Math.round(puttRollDistance * 10 * 1.1)/ 10D;
                    puttRollDistanceRoundforSpeech = (int) Math.round(puttRollDistanceRound);
                    String textputtRollDistance = Double.toString(puttRollDistanceRound);
                    String textputtRollDistanceCompensated = Double.toString(puttRollDistanceCompensated);

                    int numberRolls = (BleGolfballService.getTach(BleGolfballService.Motor.LEFT));



                    double longestMadeDistance = (double) BleGolfballService.getTach(BleGolfballService.Motor.BOTTOM)/10.0;
                    longestMadeDistanceRound = Math.round(longestMadeDistance * 100) / 100D;

                    puttViewModel.setFragmentVelocity(longestMadeDistanceRound);

                    if(longestMadeDistanceRound != longestMadeDistanceOld && numberRolls > 2 && puttMadeFlag == 0){

                        ready_button.setText("Rolling...");

                        if (longestMadeDistanceRound > 5 && longestMadeDistanceRound <= 7.1){
                            startStimpDist = puttRollDistanceCompensated;
                        }

                        if (longestMadeDistanceRound > longestMadeDistanceOld && maxVelFlag == 0){
                            longestMadeDistanceMax = longestMadeDistanceRound;
                            timeMaxVelocity = System.currentTimeMillis();
                        }
                        else{
                            maxVelFlag = 1;
                        }

                        recordPuttData();

                        /*
                          PuttData puttData = new PuttData(System.currentTimeMillis(),"Michael", puttNumber, longestMadeDistanceRound, puttRollDistanceCompensated, 0, stimpValue,
                                 false,0.0, valuePicker3, stimpRollRound, slopeDir);

                        puttViewModel.insert(puttData);

                        */



                        longestMadeDistanceOld = longestMadeDistanceRound;
                    }

                    /*
                    if( (BleGolfballService.getTach(BleGolfballService.Motor.TEMPO)) != tempoOld && (ballStoppedFlag == 0
                        || puttRollDistance == 0) && puttRollDistance <= 3){



                            recordPuttSwingData();


                    }

                    */

                    String textlongestMadeDistance = Double.toString(longestMadeDistanceRound);
                    mTachBottomText.setText(textlongestMadeDistance);

                    String textlongestMadeDistanceMax = Double.toString(longestMadeDistanceMax);
                    mvelocityMax.setText(textlongestMadeDistanceMax);

                    puttViewModel.setFragmentMaxVelocity(longestMadeDistanceMax);



                   /** mputtMadeText.setText(String.format("%d", BleGolfballService.getTach(BleGolfballService.Motor.PUTTMADE)));*/
                    int ballStartRoll =  (BleGolfballService.getTach(BleGolfballService.Motor.PUTTMADE));
                    if (ballStartRoll == 0 && longestMadeDistance == 0.0){
                        ballStoppedFlag = 1;

                        puttViewModel.setFragmentPuttStoppedFlag(ballStoppedFlag);



                    }
                    else{
                        ballStoppedFlag = 0;




                        puttViewModel.setFragmentPuttStoppedFlag(ballStoppedFlag);

                    }
                    if (ballStoppedFlag == 1 ){
                        playingSoundStroke = 0;
                    }

                    mPowerText.setText(String.format("%d", BleGolfballService.getTach(BleGolfballService.Motor.POWER)));

                    mTachMiddleText.setText(String.format("%d", BleGolfballService.getTach(BleGolfballService.Motor.TEMPO)));

                    maxAccelx = (Math.abs(102.0 - (double) BleGolfballService.getTach(BleGolfballService.Motor.MIDDLE)) / 19.6);
                    maxAccely = (Math.abs(102.0 - (double) BleGolfballService.getTach(BleGolfballService.Motor.RIGHT)) / 19.6);
                    maxAccelz = (Math.abs(125.0 - (double) BleGolfballService.getTach(BleGolfballService.Motor.BOTTOM)) / 20.5);

                    xzVector = Math.sqrt((maxAccelx * maxAccelx) + (maxAccelz * maxAccelz));





                    if (ballStoppedFlag == 0 && numberRolls >2) {

                        playingReadySound = 0;




                    }

                    mrollDistanceText.setText(textputtRollDistanceCompensated);


                    // added 3-23:  send roll distance to puttviewmodel to be observed in fragments

                    if(puttMadeFlag == 0) {
                        puttViewModel.setFragmentRollDistance(puttRollDistanceCompensated);
                    }
                    //else  {
                    //    puttViewModel.setFragmentRollDistance(distanceAtMake);
                    //}



                    /**final MediaPlayer threeftsix = MediaPlayer.create(getApplicationContext(), R.raw.threeftsix);
                    final MediaPlayer twofeet = MediaPlayer.create(getApplicationContext(), R.raw.twofeet);
                    final MediaPlayer fourfeet = MediaPlayer.create(getApplicationContext(), R.raw.fourfeet);
                    final MediaPlayer fivefeet = MediaPlayer.create(getApplicationContext(), R.raw.fivefeet);
                    final MediaPlayer sixfeet = MediaPlayer.create(getApplicationContext(), R.raw.sixfeet);
                    final MediaPlayer sevenfeet = MediaPlayer.create(getApplicationContext(), R.raw.sevenfeet);
                    final MediaPlayer eightfeet = MediaPlayer.create(getApplicationContext(), R.raw.eightfeet);
                    final MediaPlayer ninefeet = MediaPlayer.create(getApplicationContext(), R.raw.ninefeet);
                    final MediaPlayer tenfeet = MediaPlayer.create(getApplicationContext(), R.raw.tenfeet);*/


                    if (puttRollDistance == 0) {
                        playingSound = 0;

                        if (playingReadySound == 0) {
                            final MediaPlayer ballready = MediaPlayer.create(getApplicationContext(), R.raw.ballready);

                            ballready.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mp = null;
                                }});


                            if (!ballready.isPlaying()) {


                                ballready.start();
                                recordVideo();

                            }

                            badReadingFlag = 0;
                            //puttMadeFlag = 0;

                            puttNumber++;

                            playingReadySound = 1;

                            /*checkLastPuttIfMadeMade();

                            if (lastPuttMadeFlag == 1 && puttMadeFlag == 1) {

                                puttViewModel.updateLastPuttMade();

                                puttMadeFlag = 0;
                            }*/

                            puttMadeFlag = 0;


                            ready_button.setBackgroundResource(R.drawable.readybutton);
                            ready_button.setText("Ready");
                            Toast.makeText(getApplicationContext(), "READY",Toast.LENGTH_SHORT).show();

                            puttViewModel.setFragmentPuttEnd(0);
                        }
                    }

                    if ((  (((ballStoppedFlag == 1 && longestMadeDistanceRoundOld > 0.9) || (ballStoppedFlag == 0 && longestMadeDistanceRoundOld > 0.9))&& playingBadReadingSound ==0)&& puttMadeFlag == 0)&& numberRolls > 2){

                        final MediaPlayer badreading = MediaPlayer.create(getApplicationContext(), R.raw.badreading);

                        playingBadReadingSound = 1;
                        badReadingFlag = 1;

                        if (!badreading.isPlaying()) {
                            badreading.start();
                        }

                    }



                    /**if (puttRollDistanceCompensated > 1.5 && puttRollDistanceCompensated <= 2.5 && playingSound == 0 && ballStoppedFlag == 1) {

                        final MediaPlayer twofeet = MediaPlayer.create(getApplicationContext(), R.raw.twofeet);

                        playingSound = 1;

                        if (!twofeet.isPlaying()) {
                            twofeet.start();
                        }

                    }*/

                    if (badReadingFlag == 0) {

                        if (puttRollDistanceCompensated > 2.5 && puttRollDistanceCompensated <= 3.5 && playingSound == 0 && (ballStoppedFlag == 1 || puttMadeFlag == 1 )) {

                            timeStop();

                            final MediaPlayer threefeet = MediaPlayer.create(getApplicationContext(), R.raw.threefeet);

                            threefeet.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                           @Override
                                                           public void onCompletion(MediaPlayer mp) {
                                                               mp.reset();
                                                               mp.release();
                                                               mp = null;
                                                           }});

                            playingSound = 1;


                            mAttempts1.setValue(ServerValue.increment(1));
                            puttViewModel.setFragmentPuttAttempt(1);

                            
                            if (!threefeet.isPlaying() && overUnderCardOpen==0) {
                                threefeet.start();
                            }

                            updateAttemptEvent(puttRollDistanceCompensated);

                            readSwingData();


                        }



                        if (puttRollDistanceCompensated > 3.5 && puttRollDistanceCompensated <= 4.5 && playingSound == 0 && (ballStoppedFlag == 1 || puttMadeFlag ==1)) {

                            timeStop();

                            final MediaPlayer fourfeet = MediaPlayer.create(getApplicationContext(), R.raw.fourfeet);

                            playingSound = 1;

                            mAttempts2.setValue(ServerValue.increment(1));
                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!fourfeet.isPlaying()&& overUnderCardOpen==0) {
                                fourfeet.start();
                            }

                            updateAttemptEvent(puttRollDistanceCompensated);

                            readSwingData();


                        }


                        if (puttRollDistanceCompensated > 4.5 && puttRollDistanceCompensated <= 5.5 && playingSound == 0 && (ballStoppedFlag == 1 || puttMadeFlag == 1)) {

                            timeStop();

                            final MediaPlayer fivefeet = MediaPlayer.create(getApplicationContext(), R.raw.fivefeet);

                            playingSound = 1;

                            mAttempts3.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!fivefeet.isPlaying()&& overUnderCardOpen==0) {
                                fivefeet.start();
                            }

                            updateAttemptEvent(puttRollDistanceCompensated);

                            readSwingData();

                        }


                        if (puttRollDistanceCompensated > 5.5 && puttRollDistanceCompensated <= 6.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer sixfeet = MediaPlayer.create(getApplicationContext(), R.raw.sixfeet);

                            playingSound = 1;

                            mAttempts4.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!sixfeet.isPlaying()&& overUnderCardOpen==0) {
                                sixfeet.start();
                            }

                            readSwingData();


                        }


                        if (puttRollDistanceCompensated > 6.5 && puttRollDistanceCompensated <= 7.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer sevenfeet = MediaPlayer.create(getApplicationContext(), R.raw.sevenfeet);

                            playingSound = 1;

                            mAttempts5.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!sevenfeet.isPlaying()&& overUnderCardOpen==0) {
                                sevenfeet.start();
                            }

                            readSwingData();

                        }

                        if (puttRollDistanceCompensated > 7.5 && puttRollDistanceCompensated <= 8.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer eightfeet = MediaPlayer.create(getApplicationContext(), R.raw.eightfeet);

                            playingSound = 1;

                            mAttempts6.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!eightfeet.isPlaying()&& overUnderCardOpen==0) {
                                eightfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 8.5 && puttRollDistanceCompensated <= 9.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer ninefeet = MediaPlayer.create(getApplicationContext(), R.raw.ninefeet);

                            playingSound = 1;

                            mAttempts7.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!ninefeet.isPlaying()&& overUnderCardOpen==0) {
                                ninefeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 9.5 && puttRollDistanceCompensated <= 10.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer tenfeet = MediaPlayer.create(getApplicationContext(), R.raw.tenfeet);

                              playingSound = 1;

                            mAttempts8.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!tenfeet.isPlaying()&& overUnderCardOpen==0) {
                                tenfeet.start();
                            }

                            readSwingData();
                        }


                        if (puttRollDistanceCompensated > 10.5 && puttRollDistanceCompensated <= 11.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer elevenfeet = MediaPlayer.create(getApplicationContext(), R.raw.elevenfeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!elevenfeet.isPlaying()&& overUnderCardOpen==0) {
                                elevenfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 11.5 && puttRollDistanceCompensated <= 12.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twelvefeet = MediaPlayer.create(getApplicationContext(), R.raw.twelvefeet);

                            playingSound = 1;

                            mAttempts9.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!twelvefeet.isPlaying()&& overUnderCardOpen==0) {
                                twelvefeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 12.5 && puttRollDistanceCompensated <= 13.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer thirteenfeet = MediaPlayer.create(getApplicationContext(), R.raw.thirteenfeet);

                            playingSound = 1;


                            mAttempts9.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!thirteenfeet.isPlaying()&& overUnderCardOpen==0) {
                                thirteenfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 13.5 && puttRollDistanceCompensated <= 14.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer fourteenfeet = MediaPlayer.create(getApplicationContext(), R.raw.fourteenfeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!fourteenfeet.isPlaying()&& overUnderCardOpen==0) {
                                fourteenfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 14.5 && puttRollDistanceCompensated <= 15.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer fifteenfeet = MediaPlayer.create(getApplicationContext(), R.raw.fifteenfeet);

                            playingSound = 1;

                            mAttempts10.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!fifteenfeet.isPlaying()&& overUnderCardOpen==0) {
                                fifteenfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 15.5 && puttRollDistanceCompensated <= 16.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer sixteenfeet = MediaPlayer.create(getApplicationContext(), R.raw.sixteenfeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!sixteenfeet.isPlaying()&& overUnderCardOpen==0) {
                                sixteenfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 16.5 && puttRollDistanceCompensated <= 17.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer seventeenfeet = MediaPlayer.create(getApplicationContext(), R.raw.seventeenfeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!seventeenfeet.isPlaying()) {
                                seventeenfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 17.5 && puttRollDistanceCompensated <= 18.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer eighteenfeet = MediaPlayer.create(getApplicationContext(), R.raw.eighteenfeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!eighteenfeet.isPlaying()) {
                                eighteenfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 18.5 && puttRollDistanceCompensated <= 19.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer nineteenfeet = MediaPlayer.create(getApplicationContext(), R.raw.nineteenfeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!nineteenfeet.isPlaying()) {
                                nineteenfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 19.5 && puttRollDistanceCompensated <= 20.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentyfeet = MediaPlayer.create(getApplicationContext(), R.raw.twentyfeet);

                            playingSound = 1;

                            mAttempts11.setValue(ServerValue.increment(1));

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!twentyfeet.isPlaying()) {
                                twentyfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 20.5 && puttRollDistanceCompensated <= 21.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentyonefeet = MediaPlayer.create(getApplicationContext(), R.raw.twentyonefeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!twentyonefeet.isPlaying()) {
                                twentyonefeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 21.5 && puttRollDistanceCompensated <= 22.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentytwofeet = MediaPlayer.create(getApplicationContext(), R.raw.twentytwofeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!twentytwofeet.isPlaying()) {
                                twentytwofeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 22.5 && puttRollDistanceCompensated <= 23.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentythreefeet = MediaPlayer.create(getApplicationContext(), R.raw.twentythreefeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!twentythreefeet.isPlaying()) {
                                twentythreefeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 23.5 && puttRollDistanceCompensated <= 24.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentyfourfeet = MediaPlayer.create(getApplicationContext(), R.raw.twentyfourfeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!twentyfourfeet.isPlaying()) {
                                twentyfourfeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 24.5 && puttRollDistanceCompensated <= 25.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentyfivefeet = MediaPlayer.create(getApplicationContext(), R.raw.twentyfivefeet);

                            playingSound = 1;

                            puttViewModel.setFragmentPuttAttempt(1);

                            if (!twentyfivefeet.isPlaying()) {
                                twentyfivefeet.start();
                            }

                            readSwingData();
                        }

                        if (puttRollDistanceCompensated > 25.5 && puttRollDistanceCompensated <= 26.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentysixfeet = MediaPlayer.create(getApplicationContext(), R.raw.twentysixfeet);

                            playingSound = 1;

                            if (!twentysixfeet.isPlaying()) {
                                twentysixfeet.start();
                            }

                        }

                        if (puttRollDistanceCompensated > 26.5 && puttRollDistanceCompensated <= 27.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentysevenfeet = MediaPlayer.create(getApplicationContext(), R.raw.twentysevenfeet);

                            playingSound = 1;

                            if (!twentysevenfeet.isPlaying()) {
                                twentysevenfeet.start();
                            }

                        }

                        if (puttRollDistanceCompensated > 27.5 && puttRollDistanceCompensated <= 28.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentyeightfeet = MediaPlayer.create(getApplicationContext(), R.raw.twentyeightfeet);

                            playingSound = 1;

                            if (!twentyeightfeet.isPlaying()) {
                                twentyeightfeet.start();
                            }

                        }

                        if (puttRollDistanceCompensated > 28.5 && puttRollDistanceCompensated <= 29.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer twentyninefeet = MediaPlayer.create(getApplicationContext(), R.raw.twentyninefeet);

                            playingSound = 1;

                            if (!twentyninefeet.isPlaying()) {
                                twentyninefeet.start();
                            }

                        }

                        if (puttRollDistanceCompensated > 29.5 && puttRollDistanceCompensated <= 30.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer thirtyfeet = MediaPlayer.create(getApplicationContext(), R.raw.thirtyfeet);

                            playingSound = 1;

                            if (!thirtyfeet.isPlaying()) {
                                thirtyfeet.start();
                            }

                        }

                        if (puttRollDistanceCompensated > 30.5 && puttRollDistanceCompensated <= 31.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer thirtyonefeet = MediaPlayer.create(getApplicationContext(), R.raw.thirtyonefeet);

                            playingSound = 1;

                            if (!thirtyonefeet.isPlaying()) {
                                thirtyonefeet.start();
                            }

                        }

                        if (puttRollDistanceCompensated > 31.5 && puttRollDistanceCompensated <= 32.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer thirtytwofeet = MediaPlayer.create(getApplicationContext(), R.raw.thirtytwofeet);

                            playingSound = 1;

                            if (!thirtytwofeet.isPlaying()) {
                                thirtytwofeet.start();
                            }
                        }

                        if (puttRollDistanceCompensated > 32.5 && puttRollDistanceCompensated <= 33.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer thirtythreefeet = MediaPlayer.create(getApplicationContext(), R.raw.thirtythreefeet);

                            playingSound = 1;

                            if (!thirtythreefeet.isPlaying()) {
                                thirtythreefeet.start();
                            }

                        }

                        if (puttRollDistanceCompensated > 33.5 && puttRollDistanceCompensated <= 34.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer thirtyfourfeet = MediaPlayer.create(getApplicationContext(), R.raw.thirtyfourfeet);

                            playingSound = 1;

                            if (!thirtyfourfeet.isPlaying()) {
                                thirtyfourfeet.start();
                            }

                        }

                        if (puttRollDistanceCompensated > 34.5 && puttRollDistanceCompensated <= 35.5 && playingSound == 0 && ballStoppedFlag == 1) {

                            timeStop();

                            final MediaPlayer thirtyfivefeet = MediaPlayer.create(getApplicationContext(), R.raw.thirtyfivefeet);

                            playingSound = 1;

                            if (!thirtyfivefeet.isPlaying()) {
                                thirtyfivefeet.start();
                            }

                        }


                    }




                    numberRollsOld = numberRolls;





                    puttVelocity = (double)(BleGolfballService.getTach(BleGolfballService.Motor.VELOCITY)/1);
                    /*double puttVelocityRound = Math.round(puttVelocity*100)/100D;
                    String textputtVelocity = Double.toString(puttVelocityRound);
                    mVelocityText.setText(puttVelocity);*/

                    //mVelocityText.setText(String.format("%d", BleGolfballService.getTach(BleGolfballService.Motor.VELOCITY)));

                    int puttMade = (BleGolfballService.getTach(BleGolfballService.Motor.VELOCITY));


                    if (puttMade != puttMadeOld && puttMadeFlag ==0){

                        puttMadeCount ++;

                        distanceAtMake = puttRollDistanceCompensated;

                        //videoCapture.stopRecording();

                        mVelocityText.setText(Integer.toString(puttMadeCount));


                        //the below line calls a function to report longest made distance to
                        //google play games leaderboard.  Causes crash if phone is not
                        //connected, so disabled until solution found
                        updateLeaderboard((long)(puttRollDistanceCompensated*10));

                        distancePuttsMade = (puttRollDistanceCompensated + distancePuttsMadeOld);

                        totalDistanceMade.setText((Double.toString(distancePuttsMade)));

                        puttViewModel.setFragmentSessionPuttMadeDistance(distancePuttsMade);


                        distancePuttsMadeOld = distancePuttsMade;


                        //the below line calls a function to report total made distance to
                        //google play games leaderboard.  Causes crash if phone is not
                        //connected, so disabled until solution found
                        //updateLeaderboardDistancePuttsMade((long)(distancePuttsMade*10));
                        //ready_button.setText("Reset Ball");

                        timeStopMade();



                        //the below line calls a function to retrieve leaders from
                        //google play games leaderboard.  Causes crash if phone is not
                        //connected, so disabled until solution found
                        //loadScoreOfLeaderBoard();




                        //the below line calls a function to report a make event to
                        //google play games leaderboard.  Causes crash if phone is not
                        //connected, so disabled until solution found
                        //updateMakeEvent(puttRollDistanceCompensated);

                        final MediaPlayer cheersound = MediaPlayer.create(getApplicationContext(), R.raw.cheerloud);
                        cheersound.start();



                        if (puttRollDistanceCompensated == 0){

                            puttViewModel.updateLastPuttMade();

                        }

                        puttMadeFlag = 1;
                        /*puttIsMade = true;*/

                        PuttData puttData = new PuttData(System.currentTimeMillis(),"Michael", currentSessionNum, puttNumber, longestMadeDistanceRound, puttRollDistanceCompensated, 0, stimpValue,
                                true, accelY, valuePicker3,0, slopeDir);

                        double newScore = puttRollDistanceCompensatedOld + puttRollDistanceCompensated;

                        puttRollDistanceCompensatedOld = puttRollDistanceCompensated;

                        mScore.setValue(newScore);



                        puttViewModel.insert(puttData);

                        /*
                        switch ((int) Math.round(puttRollDistanceCompensated)) {
                            case 3:
                                mMakes1.setValue(ServerValue.increment(1));
                                break;
                            case 4:
                                mMakes2.setValue(ServerValue.increment(1));
                                break;
                            case 5:
                                mMakes3.setValue(ServerValue.increment(1));
                                break;
                            case 6:
                                mMakes4.setValue(ServerValue.increment(1));
                                break;
                            case 7:
                                mMakes5.setValue(ServerValue.increment(1));
                                break;
                            case 8:
                                mMakes6.setValue(ServerValue.increment(1));
                                break;
                            case 9:
                                mMakes7.setValue(ServerValue.increment(1));
                                break;
                            case 10:
                                mMakes8.setValue(ServerValue.increment(1));
                                break;
                            case 12:
                                mMakes9.setValue(ServerValue.increment(1));
                                break;
                            case 15:
                                mMakes10.setValue(ServerValue.increment(1));
                                break;
                            case 20:
                                mMakes11.setValue(ServerValue.increment(1));
                                break;
                            default:
                                break;

                        }
                        */
                        puttViewModel.setFragmentVelocityEnd(longestMadeDistanceRound);

                        puttViewModel.setFragmentPuttMadeFlag(true);
                        puttViewModel.setFragmentPuttMadeDistance((int)(Math.round(puttRollDistanceCompensated)));

                        entrySpeed();

                    }

                    puttMadeOld = (BleGolfballService.getTach(BleGolfballService.Motor.VELOCITY));

                    if (ballStoppedFlag == 1){
                        velocityFallOff = (longestMadeDistanceMax/puttRollDistanceCompensated);
                        double velocityFallOffRound = Math.round(velocityFallOff * 100) / 100D;
                        if(puttRollDistanceCompensated == 0){
                            velocityFallOffRound = 0;
                        }

                        String velocityFallOffText = Double.toString(velocityFallOffRound);


                        mVelocityFallOff.setText((velocityFallOffText));











                    }









                    puttVelocityOld = puttVelocity;
                    double longestMadeDistanceRoundOld = longestMadeDistanceRound;

                    break;
            }

        }
    };

    public void readSwingData(){



        puttswingViewModel.accelerationAtTopOfBackswing().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer acceleration) {
                 backswingAccel = acceleration;
            }
        });

        puttswingViewModel.timeAtTopOfBackswing().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long backswingtime) {
                backswingTime = backswingtime;
            }
        });

        puttswingViewModel.timeAtContact().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long time) {
                contactTime = time;
            }
        });

        /*
        puttswingViewModel.accelerationAtTopOfFollowThrough().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer followthruacceleration) {
                followthroughAccel = followthruacceleration;
            }
        });
        */
        puttswingViewModel.accelerationAtContact().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer accelerationatcontact) {
                contactAccel = accelerationatcontact;
            }
        });


        timeToImpact = contactTime - backswingTime;

        double backswingAngle = Math.asin(((double)(backswingAccel)/100));

        double backswingDistance = (backswingAngle * 24);

        double backswingDistanceRound = Math.round(backswingDistance*100)/100D;

        String backswingLengthText = Double.toString(backswingDistanceRound);

        backswing_length.setText(backswingLengthText);

        String tempoTimeText = Long.toString(timeToImpact);

        //tempo_value.setText(tempoTimeText);







    }



    public void entrySpeed(){

        puttViewModel.getFragmentVelocityEnd().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double velocity) {
                if (velocity != null) {
                    int entrySpeed = (int) Math.round(velocity);
                    String newVelocity = Integer.toString(entrySpeed);
                    enterSpeedValue.setText(newVelocity);


                }
            }}
            );
    }


    @SuppressLint("RestrictedApi")
    public void timeStop(){

        //puttViewModel.setFragmentPuttLaunchAngle(directionBLE);



        if (directionFlag == 1) {
            //directionText = "Right";
            //mContactDirectionText.setText(directionText);
            //mContactAngleText.setText((Integer.toString(90 - directionBLE)) + "\u00B0");
            //timePuttStart = System.currentTimeMillis();
            //puttViewModel.setFragmentPuttLaunchAngle(90 - directionBLE);
            //puttViewModel.setFragmentPuttLaunchDirection("R");

            //puttStrikeFlag = 1;
            //recordPuttData();
            //recordPuttSwingData();


        }

        if (directionFlag == 0) {
            //directionText = "Left";
            //mContactDirectionText.setText(directionText);
            //mContactAngleText.setText((Integer.toString(directionBLE - 90)) + "\u00B0");
            //timePuttStart = System.currentTimeMillis();
            //puttViewModel.setFragmentPuttLaunchAngle(directionBLE-90);
            //puttViewModel.setFragmentPuttLaunchDirection("L");
            //puttStrikeFlag = 1;
            //recordPuttData();
            //recordPuttSwingData();


        }


        puttViewModel.setFragmentPuttEnd(1);

        pastHoleDistance = Math.round((puttRollDistanceCompensated - distanceAtMake)*10)/10D;

        puttViewModel.setFragmentPastHoleDist(pastHoleDistance);

        if(startStimpDist >0){
        stimpDist = Math.round((puttRollDistanceCompensated - startStimpDist)*10) /10D;}
        mTachRightText.setText(Double.toString(stimpDist));
        startStimpDist = 0;

        if (stimpCardOpen == 1) {


            if (stimpUpSlope == 0.0 && stimpDownSlope == 0.0) {

                stimpUpSlope = stimpDist;

                puttViewModel.setFragmentStimpUpslope(stimpUpSlope);
            }

            if(stimpUpSlope != 0.0 && stimpFlag == 0){
                puttNumberOld = puttNumber;

                stimpFlag = 1;}

            if (stimpDownSlope == 0.0 && puttNumber > puttNumberOld) {

                stimpDownSlope = stimpDist;
                puttViewModel.setFragmentStimpDownslope(stimpDownSlope);

                stimpSlopeCorrected = Math.round((2 * stimpUpSlope * stimpDownSlope / (stimpUpSlope + stimpDownSlope))*10)/10D;
                puttViewModel.setFragmentStimpSlopeCorrected(stimpSlopeCorrected);

            }

            if (stimpDownSlope != 0.0){

                stimpFlag = 0;
            }

        }
        ready_button.setBackgroundResource(R.drawable.notreadybutton);
        ready_button.setText("Reset Ball");

        maxVelFlag = 0;

        puttViewModel.getTimePuttStart().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long timeStart) {

                if (timeStart != null) {
                    puttViewModel.setTimePuttEnd(timeStart);

                    //timePuttStart = timeStart;

                }

                //lastPuttMadeFlag = lastEntryMadeStatus;

            }
        });


        puttViewModel.getTimeMaxVelocity(timePuttStart).observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long time) {
                if (time != null) {
                    //puttViewModel.setTimeMaxVelocity(time);

                    //timeMaxVelocity = time;
                }
                // lastPuttMadeFlag = lastEntryMadeStatus;

            }
        });




        puttViewModel.getTimePuttEnd().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long timeEnd) {

                if (timeEnd != null) {
                    puttViewModel.setTimePuttEnd(timeEnd);

                    timePuttStopped = timeEnd;

                    //lastPuttMadeFlag = lastEntryMadeStatus;

                    timePuttTotal = timePuttStopped - timePuttStart;
                    timeMaxToStop = timePuttStopped - timeMaxVelocity;
                    if (timePuttTotal != 0) {
                        pureRollFraction = ((timeMaxToStop * 100) / timePuttTotal);
                    }
                    roundOff = Math.round(pureRollFraction * 100) / 100;

                    pureRollPercentage = roundOff * 100;
                    //pureRollPercentageText = Long.toString(pureRollPercentage);
                    puttViewModel.setFragmentPureRollPercentage(roundOff);
                    puttViewModel.setFragmentTimeMaxtoStop(timeMaxToStop);
                    puttViewModel.setFragmentTimePuttTotal(timePuttTotal);

                    //timePuttTotal = 0L;
                    //timePuttStart = 0L;
                    //timeMaxVelocity = 0L;
                    //timePuttStopped = 0L;


                }



            }
        });




        puttViewModel.getAccelYAverage(timePuttStart).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float average) {
                if (average != null) {
                    puttViewModel.setAccelYAverage(average);

                    //timeMaxVelocity = time;
                }
                // lastPuttMadeFlag = lastEntryMadeStatus;

                accelYAvg = (double)average;
                average_text = String.format("%.2f", average);
                mContactAngleText.setText(String.valueOf(average));

                puttViewModel.setFragmentAccelYAverage(accelYAvg);


            }
        });


        PuttData puttData = new PuttData(System.currentTimeMillis(),"Michael", currentSessionNum, puttNumber, longestMadeDistanceRound, puttRollDistanceCompensated, 0, stimpValue,
                false, accelY, valuePicker3, 0, slopeDir);

        puttViewModel.insert(puttData);

        if(previewView.getVisibility() == View.VISIBLE) {
            videoCapture.stopRecording();
        }

    }



    @SuppressLint("RestrictedApi")
    public void timeStopMade(){

        //puttViewModel.setFragmentPuttLaunchAngle(directionBLE);






        puttViewModel.setFragmentPuttEnd(1);

        pastHoleDistance = Math.round((puttRollDistanceCompensated - distanceAtMake)*10)/10D;

        puttViewModel.setFragmentPastHoleDist(pastHoleDistance);

        if(startStimpDist >0){
            stimpDist = Math.round((puttRollDistanceCompensated - startStimpDist)*10) /10D;}
        mTachRightText.setText(Double.toString(stimpDist));
        startStimpDist = 0;

        if (stimpCardOpen == 1) {


            if (stimpUpSlope == 0.0 && stimpDownSlope == 0.0) {

                stimpUpSlope = stimpDist;

                puttViewModel.setFragmentStimpUpslope(stimpUpSlope);
            }

            if(stimpUpSlope != 0.0 && stimpFlag == 0){
                puttNumberOld = puttNumber;

                stimpFlag = 1;}

            if (stimpDownSlope == 0.0 && puttNumber > puttNumberOld) {

                stimpDownSlope = stimpDist;
                puttViewModel.setFragmentStimpDownslope(stimpDownSlope);

                stimpSlopeCorrected = Math.round((2 * stimpUpSlope * stimpDownSlope / (stimpUpSlope + stimpDownSlope))*10)/10D;
                puttViewModel.setFragmentStimpSlopeCorrected(stimpSlopeCorrected);

            }

            if (stimpDownSlope != 0.0){

                stimpFlag = 0;
            }

        }
        ready_button.setBackgroundResource(R.drawable.notreadybutton);
        ready_button.setText("Reset Ball");

        maxVelFlag = 0;

        puttViewModel.getTimePuttStart().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long timeStart) {

                if (timeStart != null) {
                    puttViewModel.setTimePuttEnd(timeStart);

                    //timePuttStart = timeStart;

                }

                //lastPuttMadeFlag = lastEntryMadeStatus;

            }
        });


        puttViewModel.getTimeMaxVelocity(timePuttStart).observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long time) {
                if (time != null) {
                    //puttViewModel.setTimeMaxVelocity(time);

                    //timeMaxVelocity = time;
                }
                // lastPuttMadeFlag = lastEntryMadeStatus;

            }
        });




        puttViewModel.getTimePuttEnd().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long timeEnd) {

                if (timeEnd != null) {
                    puttViewModel.setTimePuttEnd(timeEnd);

                    timePuttStopped = timeEnd;

                    //lastPuttMadeFlag = lastEntryMadeStatus;

                    timePuttTotal = timePuttStopped - timePuttStart;
                    timeMaxToStop = timePuttStopped - timeMaxVelocity;
                    if (timePuttTotal != 0) {
                        pureRollFraction = ((timeMaxToStop * 100) / timePuttTotal);
                    }
                    roundOff = Math.round(pureRollFraction * 100) / 100;

                    pureRollPercentage = roundOff * 100;
                    //pureRollPercentageText = Long.toString(pureRollPercentage);
                    puttViewModel.setFragmentPureRollPercentage(roundOff);
                    puttViewModel.setFragmentTimeMaxtoStop(timeMaxToStop);
                    puttViewModel.setFragmentTimePuttTotal(timePuttTotal);

                    //timePuttTotal = 0L;
                    //timePuttStart = 0L;
                    //timeMaxVelocity = 0L;
                    //timePuttStopped = 0L;


                }



            }
        });




        puttViewModel.getAccelYAverage(timePuttStart).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float average) {
                if (average != null) {
                    puttViewModel.setAccelYAverage(average);

                    //timeMaxVelocity = time;
                }
                // lastPuttMadeFlag = lastEntryMadeStatus;

                accelYAvg = (double)average;
                average_text = String.format("%.2f", average);
                mContactAngleText.setText(String.valueOf(average));

                puttViewModel.setFragmentAccelYAverage(accelYAvg);


            }
        });


        PuttData puttData = new PuttData(System.currentTimeMillis(),"Michael", currentSessionNum, puttNumber, longestMadeDistanceRound, puttRollDistanceCompensated, 0, stimpValue,
                false, accelY, valuePicker3, 0, slopeDir);

        puttViewModel.insert(puttData);

        if(previewView.getVisibility() == View.VISIBLE) {
            videoCapture.stopRecording();
        }

    }



    public void checkLastPuttIfMadeMade(){

        puttViewModel.getLastEntryMadeStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer lastEntryMadeStatus) {

                puttViewModel.setLastEntryMadeStatus(lastEntryMadeStatus);

                lastPuttMadeFlag = lastEntryMadeStatus;

            }
        });

    }


    private void startSignInIntent() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this,
                gso);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    private void signOut() {

        signInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        //updateUI(null);
                        // [END_EXCLUDE]
                    }
                });



        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(this,gso);
        mPlayer.child(displayName).child("online").setValue("no");
        googleSignInClient.signOut();
        signInButton.setVisibility(View.VISIBLE);






    }



    public void updateLeaderboard(long madeDistance){


        if(signedInAccount != null){
        Games.getLeaderboardsClient(this, signedInAccount )
                .submitScore("CgkIt4SJiNUSEAIQEA", (madeDistance));}
    }

    public void updateLeaderboardDistancePuttsMade(long totalDistanceMade){

        Games.getLeaderboardsClient(this, signedInAccount )
                .submitScore("CgkIt4SJiNUSEAIQFw", (totalDistanceMade));
    }


    private void showLeaderboard() {
        /*
        Games.getLeaderboardsClient(this, signedInAccount).getLeaderboardIntent("CgkIt4SJiNUSEAIQEA")
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {

                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });


         */



        Games.getLeaderboardsClient(this, signedInAccount)
                .getAllLeaderboardsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {

                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });


    }


    private void loadScoreOfLeaderBoard() {

        Games.getLeaderboardsClient(this, signedInAccount).loadPlayerCenteredScores ("CgkIt4SJiNUSEAIQFw", LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_FRIENDS,5,true)
                .addOnSuccessListener(new OnSuccessListener<AnnotatedData<LeaderboardsClient.LeaderboardScores>>() {
                    private String PlayerID;
                    private long myrank;


                    @Override

            public void onSuccess(AnnotatedData<LeaderboardsClient.LeaderboardScores> leaderboardScoreAnnotatedData) {

                LeaderboardScoreBuffer score;
                PlayerLevelInfo rank;
                //long myrank1 = 0L;

                if (leaderboardScoreAnnotatedData != null) {
                    if (leaderboardScoreAnnotatedData.get() != null) {
                        score = leaderboardScoreAnnotatedData.get().getScores();
                        Iterator<LeaderboardScore> it = score.iterator();
                        while (it.hasNext()) {
                            LeaderboardScore temp = it.next();
                            Log.d("PlayGames", "player" + temp.getScoreHolderDisplayName() + " id:" + temp.getRawScore() + " Rank: " + temp.getRank());

                            String scoreName = temp.getScoreHolderDisplayName();

                            leaderboardlist.add(new LeaderboardItem(Long.toString(temp.getRank()), temp.getScoreHolderDisplayName(), Long.toString(temp.getRawScore())));

                            if (temp.getRank() != 0 && (temp.getScoreHolderDisplayName().equals(displayName))){
                                myrank1 = temp.getRank();
                                leaderboardScore = temp.getRawScore();

                            }
                            else{
                                Log.d("PlayGames", "get rank error" );
                            }









                            //Toast.makeText(ControlActivity.this, Long.toString( temp.getRank()), Toast.LENGTH_SHORT).show();

                        }

                        //leaderboardlist.add(new LeaderboardItem(Long.toString(myrank1), displayName, Long.toString(leaderboardScore)));

                        Toast.makeText(ControlActivity.this, Long.toString(myrank1) + " " + displayName + " " + Long.toString(leaderboardScore), Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "LeaderBoard: " + score);

                    }

                        //Player user = leaderboardScoreAnnotatedData.get().getScoreHolder();

                        //PlayerID = user.getPlayerId();
                        //rank = user.getLevelInfo();
                        //long myscore = leaderboardScoreAnnotatedData.get().getRawScore();
                        //myrank = score.getRank();
                        //LeaderboardScore test = leaderboardScoreAnnotatedData.get();
                        //boolean test1 = Boolean.parseBoolean(null);
                        //final long test1;






                    }
                    else {
                        Toast.makeText(ControlActivity.this, "no data at .get()", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "LeaderBoard: .get() is null");
                    }

            }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ControlActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "LeaderBoard: FAILURE");
                    }




        });
    }






    public void updateMakeEvent(double madeDistance) {

        int madeDistanceInt = (int) Math.round(madeDistance);

        switch (madeDistanceInt){
            case 3:
            case 4:
            case 5:
                Games.getEventsClient(this, signedInAccount)
                        .increment("CgkIt4SJiNUSEAIQFg", 1);
                break;

            default:
                break;

        }

    }


    public void updateAttemptEvent(double attemptDistance) {

        int attemptDistanceInt = (int) Math.round(attemptDistance);

        switch (attemptDistanceInt){
            case 3:
            case 4:
            case 5:
                //Games.getEventsClient(this, signedInAccount).increment("CgkIt4SJiNUSEAIQFQ", 1);
                break;

            default:
                break;

        }

    }


    public void disableSignInButton(){

            signInButton.setVisibility(GONE);

    }

    private List<StatSample> statSamples = new ArrayList<>();

    public void importDataFile(){

            InputStream is = getResources().openRawResource(R.raw.data);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );

            String line = "";

                try {
                    while ((line = reader.readLine()) != null){

                        String[] tokens = line.split(",");
                        StatSample statSample = new StatSample();
                        statSample.setTimeStamp(Long.parseLong(tokens[0]));
                        statSample.setSession(Integer.parseInt(tokens[1]));
                        statSample.setUserID(tokens[2]);
                        statSample.setNumberOfPutts(Integer.parseInt(tokens[3]));
                        statSample.setDistance(Double.parseDouble(tokens[4]));
                        statSample.setTotalMade(Integer.parseInt(tokens[5]));
                        statSample.setPracticeTime(Double.parseDouble(tokens[6]));
                        statSample.setLocation(tokens[7]);

                        StatData statData = new StatData(statSample.getTimeStamp(), statSample.getSession(),
                                statSample.getUserID(), statSample.getNumberOfPutts(), statSample.getDistance(),
                                statSample.getTotalMade(), statSample.getPracticeTime(), statSample.getLocation());

                        statViewModel.insert(statData);


                        //statSamples.add(statSample);

                        //Log.d("MyActivity","just created: " + statSample);
                    }
                } catch (IOException e) {
                    Log.wtf("MyActivity","Error reading file");
                    e.printStackTrace();
                }

            }

    public void recordPuttSwingData(){

        PuttSwingData puttswingData = new PuttSwingData(System.currentTimeMillis(), puttNumber, "Michael", ((BleGolfballService.getTach(BleGolfballService.Motor.TEMPO)-90))
                , puttRollDistanceRoundforSpeech, puttStrikeFlag);

        puttswingViewModel.insert(puttswingData);


        tempoOld = (BleGolfballService.getTach(BleGolfballService.Motor.TEMPO));

        puttStrikeFlag = 0;
    }


    public void recordPuttData(){

        PuttData puttData = new PuttData(System.currentTimeMillis(),"Michael", currentSessionNum, puttNumber, longestMadeDistanceRound, puttRollDistanceCompensated, accelBle, stimpValue,
                false, accelY, valuePicker3, directionBLE, slopeDir);

        puttViewModel.insert(puttData);

     }


    public void setCurrentSession(int currentSession){

        SharedPreferences sharedPref = getSharedPreferences("session", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("session_key", currentSession);
        editor.apply();




    }


    public int getPreviousSession(){

        SharedPreferences sharedPref = getSharedPreferences("session", this.MODE_PRIVATE);
        lastSessionNum = sharedPref.getInt("session_key", 0);

        return lastSessionNum;



    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }



    @SuppressLint("RestrictedApi")
    void bindPreview(ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        //recordVideo();
        //puttViewModel.setFragmentPlotSessionVideoFlag(1);


        /*

        Games.getVideosClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getCaptureOverlayIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_VIDEO_OVERLAY);
                    }
                });

        */


        // Video capture use case
        videoCapture = new VideoCapture.Builder()
                .setVideoFrameRate(30)
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, videoCapture);


    }

    @SuppressLint("RestrictedApi")
    private void recordVideo() {
        if (videoCapture != null) {

                long timestamp = System.currentTimeMillis();

                String combine = videoPlotSession + Integer.toString(videoPlotPoint);


                File directory = this.getFilesDir();
                File videofilepath = new File(directory, combine);

                String videodirectorypath = String.valueOf(directory.getAbsolutePath());

                puttViewModel.setFragmentVideoDirectoryPath(videodirectorypath);

                Uri videoUri = Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + 491);



                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, combine);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");

                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    videoCapture.startRecording(new VideoCapture.OutputFileOptions.Builder(videofilepath)
                                .build(), ContextCompat.getMainExecutor(this),
                            new VideoCapture.OnVideoSavedCallback() {
                        @Override
                        public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                            Toast.makeText(ControlActivity.this, "Video has been saved successfully.", Toast.LENGTH_SHORT).show();

                            puttViewModel.setFragmentPlotSessionVideoFlag(1);

                        }

                        @Override
                        public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                            Toast.makeText(ControlActivity.this, "Error saving video: " + message, Toast.LENGTH_SHORT).show();
                        }
                    });

                    /*
                    videoCapture.startRecording(
                            new VideoCapture.OutputFileOptions.Builder(
                                    getContentResolver(),
                                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                    contentValues
                            ).build(), ContextCompat.getMainExecutor(this),
                            new VideoCapture.OnVideoSavedCallback() {
                                @Override
                                public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                                    Toast.makeText(ControlActivity.this, "Video has been saved successfully.", Toast.LENGTH_SHORT).show();

                                    puttViewModel.setFragmentPlotSessionVideoFlag(1);

                                }

                                @Override
                                public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                                    Toast.makeText(ControlActivity.this, "Error saving video: " + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                    );*/


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }












    @SuppressLint("ResourceType")
    public void startPreview(){


        previewView.setVisibility(View.VISIBLE);
        Toast toast = Toast.makeText(this, "Recording will start after first putt", Toast.LENGTH_LONG);
        toast.show();
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                cameraProvider.unbindAll();
                bindPreview(cameraProvider);
            } catch (InterruptedException | ExecutionException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));

    }


    public void playVideo(){

        startVideo.setVisibility(View.VISIBLE);



        //startVideo.setElevation(10);






        startVideo.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);


        String videoPath = RawResourceDataSource.buildRawResourceUri(R.raw.startvideo).toString();

        Uri uri = RawResourceDataSource.buildRawResourceUri(R.raw.startvideo);

        ExtractorMediaSource audioSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(this, "MyExoplayer"),
                new DefaultExtractorsFactory(),
                null,
                null
        );

        player.prepare(audioSource);
        startVideo.setPlayer(player);
        player.setRepeatMode(player.REPEAT_MODE_ONE);
        player.setPlayWhenReady(true);

    }








    public void startClockDrill() {

        arrow1.setBackgroundResource(R.drawable.arrow_yellow);

        stationComplete = 0;

        clockStationDistance = valuePicker3;

        return;
    }

    public int getClockDrillArrowLocation(){



     return arrowLocation;
    }




    public void advanceClockDrill(int clock_direction) {

        switch (clock_direction) {
            case 1:
                //do something
                //change arrow2 color to yellow
                //change arrow1 to white
                //change slope
                //set arrowLocation = 2

                break;
            case 2:
                //do something
                //change arrow3 color to yellow
                //change arrow1 to white
                //change slope
                //set arrowLocation = 3
                break;
            case 3:
                //do something
                //change arrow4 color to yellow
                //change arrow3 color to white
                //change slope
                //set arrowLocation = 4
                break;
            case 4:
                //do something
                //change arrow1 color to yellow
                //change arrow4 color to white
                //change slope
                //set arrowLocation = 1
                break;
            default:
                //so something

        }


    }





    /**
     * This sets up the filter for broadcasts that we want to be notified of.
     * This needs to match the broadcast receiver cases.
     *
     * @return intentFilter
     */
    private static IntentFilter makeRobotUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BleGolfballService.ACTION_CONNECTED);
        intentFilter.addAction(BleGolfballService.ACTION_DISCONNECTED);
        intentFilter.addAction(BleGolfballService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }


    public abstract class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            // An item was selected. You can retrieve the selected item using

            //parent.getItemAtPosition(position);



        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }





    }



}
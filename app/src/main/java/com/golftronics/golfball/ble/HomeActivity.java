package com.golftronics.golfball.ble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.EventsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

import static android.view.View.GONE;

import android.net.Uri;
import android.widget.Toast;


import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;




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
import com.google.firebase.database.ValueEventListener;

import java.io.File;


public class HomeActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener,BlankFragment2.OnFragmentInteractionListener,
        BingoFragment.OnFragmentInteractionListener,ShotClockFragment.OnFragmentInteractionListener,
        PaceFragment.OnFragmentInteractionListener, LeaderBoardFragment.OnFragmentInteractionListener, StatisticsFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener, FriendsFragment.OnFragmentInteractionListener,
        HomeFragmentNew.OnFragmentInteractionListener{



    GoogleSignInClient signInClient;
    GoogleSignInOptions gso;

    private PuttViewModel puttViewModel;
    private static TextView loggedInDisplayName, new_profile;
    public static Button scan_button, practice_button, login, login_button, submit;
    public EditText nameEntry, emailEntry, passwordEntry, usernameEntry, handicapEntry;
    public EditText userEmailAddress, userPassword;
    public String displayName;
    private FrameLayout fragmentContainer;
    private ConstraintLayout loginSplash;
    private ConstraintLayout emailPasswordUI;
    private ConstraintLayout newRegistration;
    private static final String TAG = ControlActivity.class.getSimpleName();
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
    public String sessionId;

    public static ArrayList<LeaderboardItem> leaderboardlist = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private DatabaseReference mPlayer;
    private String userID;

    SimpleExoPlayer player;

    PlayerView startVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);



        mAuth = FirebaseAuth.getInstance();



        firebaseDatabase = firebaseDatabase.getInstance();

        loggedInDisplayName = (TextView)findViewById(R.id.Display_name);
        scan_button = (Button)findViewById(R.id.scan);
        //practice_button = (Button)findViewById(R.id.practice);
        login = findViewById(R.id.login);
        login_button = findViewById(R.id.login_button);
        userEmailAddress = findViewById(R.id.email_address);
        userPassword = findViewById(R.id.password);
        nameEntry = findViewById(R.id.register_name);
        emailEntry = findViewById(R.id.new_email_address);
        passwordEntry = findViewById(R.id.new_password);
        usernameEntry = findViewById(R.id.display_name);
        handicapEntry = findViewById(R.id.handicap);
        new_profile = (Button)findViewById(R.id.new_profile);
        submit = findViewById(R.id.submit);


        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        loginSplash = findViewById(R.id.login_splash);
        emailPasswordUI = findViewById(R.id.email_password_UI);
        newRegistration = findViewById(R.id.new_registration);
        signInButton = findViewById(R.id.sign_in_button);


        startVideo = findViewById(R.id.video_view);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                player.release();
                openScanActivity();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSplash.setVisibility(GONE);
                emailPasswordUI.setVisibility(View.VISIBLE);
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();

            }
        });


        new_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailPasswordUI.setVisibility(GONE);
                newRegistration.setVisibility(View.VISIBLE);


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newRegistration.setVisibility(GONE);
                registerUser();
                loginSplash.setVisibility(View.VISIBLE);


            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInIntent();
            }
        });

        puttViewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(PuttViewModel.class);

        playVideo();

    }

    private void startSignInIntent() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, gso);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
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
                            if(displayName !=null){
                            mPlayer.child(displayName);
                            puttViewModel.setFragmentSignedInAccount(displayName);
                            mPlayer.child(displayName).child("online").setValue("yes");}

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());





                        }
                    }
                });
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.navigation_notifications:
                            openFragment8();
                            break;
                        case R.id.navigation_stats:
                            openFragment8();
                            break;
                        case R.id.navigation_leaderboards:
                            openFragment8();
                            showLeaderboard();
                            break;
                        case R.id.navigation_practice:
                            openFragment8();
                            break;


                        // add cases for other menu items here


                    }



                    return true;

                }
            };


    private void playVideo(){

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


    public void openScanActivity(){
        player.stop();
        player.release();
        Intent scanIntent = new Intent(this,ScanActivity.class);

        startActivity(scanIntent);
    }

    public void loginUser(){

        String email = userEmailAddress.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(HomeActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    mPlayer = FirebaseDatabase.getInstance().getReference("Users");
                    userID = user.getUid();


                    mPlayer.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userProfile = snapshot.getValue(User.class);

                            if(userProfile!=null){
                                emailPasswordUI.setVisibility(GONE);
                                loginSplash.setVisibility(View.VISIBLE);
                                String userName = userProfile.username;
                                Toast.makeText(HomeActivity.this, "Hello" + userName, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }else {
                    Toast.makeText(HomeActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





    public void registerUser(){
        String name = nameEntry.getText().toString().trim();
        String email = emailEntry.getText().toString().trim();
        String password = passwordEntry.getText().toString().trim();
        String username = usernameEntry.getText().toString().trim();
        String handicap = handicapEntry.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        User user = new User(name, email, password, username, handicap);

                        FirebaseDatabase.getInstance().getReference("Users").
                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(HomeActivity.this, "User Registered", Toast.LENGTH_LONG).show();


                                    emailPasswordUI.setVisibility(GONE);
                                    loginSplash.setVisibility(View.VISIBLE);

                                }
                                else{
                                    Toast.makeText(HomeActivity.this, "Error with registration", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                    }
                    else{
                        Toast.makeText(HomeActivity.this, "Error with registration", Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void openPracticeActivity(){
        Intent scanIntent = new Intent(this,ControlActivity.class);

        startActivity(scanIntent);
    }








    public void openFragment6(){
        LeaderBoardFragment fragment6 = LeaderBoardFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment6);
        transaction.addToBackStack(null); //
        transaction.commit();



    }


    public void openFragment8(){

        final Intent intent = new Intent(this, ControlActivity.class);

        startActivity(intent);

        /*

        PracticeFragment fragment7 = PracticeFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment7);
        transaction.addToBackStack(null); //
        transaction.commit();
        */


    }


    public void openFragment7(){
        FriendsFragment fragment8 = FriendsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment8);
        transaction.addToBackStack(null); //
        transaction.commit();



    }


    private void showLeaderboard() {
        Games.getLeaderboardsClient(this, signedInAccount)
                .getAllLeaderboardsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        onBackPressed();
    }






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

                        }
                        else {
                            Exception e = task.getException();
                            //handleException(e, getString(R.string.players_exception));
                            displayName = "???";
                        }
                        mDisplayName = displayName;

                        //Toast toast = Toast.makeText(getApplicationContext(), "Hello " + displayName + " !", Toast.LENGTH_LONG);
                        //toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.START, 90, 100);
                        //toast.show();

                        //loggedInDisplayName.setText(displayName);

                        signInButton.setVisibility(GONE);


                    }

                });
    }
}
package com.golftronics.golfball.ble;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.games.Games;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {



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

    private GameFragment.OnFragmentInteractionListener mListener;

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

    public CharSequence signedInAccountFragment;

    public ListView friendList;

    List<String> friends = new ArrayList<String>();
    String [] startingList = {"Bob", "Mike", "Tome"};

    ArrayAdapter ad;

    String firebasePlayer;

    FirebaseDatabase firebaseDatabaseFragment;
    private DatabaseReference mPlayersFragment;
    private DatabaseReference mFriendsFragment;
    private DatabaseReference mPlayerStatus;
    private DatabaseReference mGameRef;
    private DatabaseReference mPlayerRef;
    private DatabaseReference mGameScoreRef;
    private DatabaseReference mMatch, mRound, mPlayer1, mPlayer2, mHolePlayer1, mHolePlayer2,
            mDistancePlayer1, mDistancePlayer2, mScorePlayer1, mScorePlayer2, mStrokesPlayer1,
            mStrokesPlayer2;
    private FirebaseAuth mAuthFragment;

    private TextView hole1Dist, hole2Dist, hole3Dist, hole4Dist, hole5Dist, hole6Dist, hole7Dist,
            hole8Dist, hole9Dist, hole10Dist, hole11Dist, hole12Dist, hole13Dist, hole14Dist,
            hole15Dist, hole16Dist, hole17Dist, hole18Dist, hole1Score, hole2Score, hole3Score,
            hole4Score, hole5Score, hole6Score, hole7Score, hole8Score, hole9Score, hole10Score,
            hole11Score, hole12Score, hole13Score, hole14Score, hole15Score, hole16Score,
            hole17Score, hole18Score, totalScore, hole1Result, hole2Result, hole3Result, hole4Result,
            hole5Result, hole6Result, hole7Result, hole8Result, hole9Result, hole10Result,
            hole11Result, hole12Result, hole13Result, hole14Result, hole15Result, hole16Result,
            hole17Result, hole18Result, hole1Num, hole2Num, hole3Num, hole4Num, hole5Num, hole6Num,
            hole7Num, hole8Num, hole9Num, hole10Num, hole11Num, hole12Num, hole13Num, hole14Num,
            hole15Num, hole16Num, hole17Num, hole18Num;

    private int hole1Length, hole2Length, hole3Length, hole4Length, hole5Length, hole6Length,
            hole7Length, hole8Length, hole9Length, hole10Length, hole11Length, hole12Length,
            hole13Length, hole14Length, hole15Length, hole16Length, hole17Length, hole18Length;

    private int currentHoleDistance;

    private int currentHoleScore;

    private int totalScoreValue;

    private TextView myScore;

    private String gameID;

    private int hole = 1;

    private int reset = 0;

    private CoordinatorLayout coordinatorLayout;



    public GameFragment() {


        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance() {
        GameFragment fragment4 = new GameFragment();
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

        mAuthFragment = FirebaseAuth.getInstance();
        firebaseDatabaseFragment = FirebaseDatabase.getInstance();
        mPlayersFragment =  FirebaseDatabase.getInstance().getReference("players");


        //myRef = firebaseDatabase.getReference("values");
        mMatch = FirebaseDatabase.getInstance().getReference("games/private/match 1");
        mRound = FirebaseDatabase.getInstance().getReference("games/private/match 1/Round1");
        mPlayer1 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player1");









    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        View view4 = inflater.inflate(R.layout.gamefragment, container, false);


        hole1Num = view4.findViewById(R.id.hole1);
        hole2Num = view4.findViewById(R.id.hole2);
        hole3Num = view4.findViewById(R.id.hole3);
        hole4Num = view4.findViewById(R.id.hole4);
        hole5Num = view4.findViewById(R.id.hole5);
        hole6Num = view4.findViewById(R.id.hole6);
        hole7Num = view4.findViewById(R.id.hole7);
        hole8Num = view4.findViewById(R.id.hole8);
        hole9Num = view4.findViewById(R.id.hole9);
        hole10Num = view4.findViewById(R.id.hole10);
        hole11Num = view4.findViewById(R.id.hole11);
        hole12Num = view4.findViewById(R.id.hole12);
        hole13Num = view4.findViewById(R.id.hole13);
        hole14Num = view4.findViewById(R.id.hole14);
        hole15Num = view4.findViewById(R.id.hole15);
        hole16Num = view4.findViewById(R.id.hole16);
        hole17Num = view4.findViewById(R.id.hole17);
        hole18Num = view4.findViewById(R.id.hole18);





        hole1Dist = view4.findViewById(R.id.gamehole1dist);
        hole2Dist = view4.findViewById(R.id.gamehole2dist);
        hole3Dist = view4.findViewById(R.id.gamehole3dist);
        hole4Dist = view4.findViewById(R.id.gamehole4dist);
        hole5Dist = view4.findViewById(R.id.gamehole5dist);
        hole6Dist = view4.findViewById(R.id.gamehole6dist);
        hole7Dist = view4.findViewById(R.id.gamehole7dist);
        hole8Dist = view4.findViewById(R.id.gamehole8dist);
        hole9Dist = view4.findViewById(R.id.gamehole9dist);
        hole10Dist = view4.findViewById(R.id.gamehole10dist);
        hole11Dist = view4.findViewById(R.id.gamehole11dist);
        hole12Dist = view4.findViewById(R.id.gamehole12dist);
        hole13Dist = view4.findViewById(R.id.gamehole13dist);
        hole14Dist = view4.findViewById(R.id.gamehole14dist);
        hole15Dist = view4.findViewById(R.id.gamehole15dist);
        hole16Dist = view4.findViewById(R.id.gamehole16dist);
        hole17Dist = view4.findViewById(R.id.gamehole17dist);
        hole18Dist = view4.findViewById(R.id.gamehole18dist);

        hole1Score = view4.findViewById(R.id.gamehole1score);
        hole2Score = view4.findViewById(R.id.gamehole2score);
        hole3Score = view4.findViewById(R.id.gamehole3score);
        hole4Score = view4.findViewById(R.id.gamehole4score);
        hole5Score = view4.findViewById(R.id.gamehole5score);
        hole6Score = view4.findViewById(R.id.gamehole6score);
        hole7Score = view4.findViewById(R.id.gamehole7score);
        hole8Score = view4.findViewById(R.id.gamehole8score);
        hole9Score = view4.findViewById(R.id.gamehole9score);
        hole10Score = view4.findViewById(R.id.gamehole10score);
        hole11Score = view4.findViewById(R.id.gamehole11score);
        hole12Score = view4.findViewById(R.id.gamehole12score);
        hole13Score = view4.findViewById(R.id.gamehole13score);
        hole14Score = view4.findViewById(R.id.gamehole14score);
        hole15Score = view4.findViewById(R.id.gamehole15score);
        hole16Score = view4.findViewById(R.id.gamehole16score);
        hole17Score = view4.findViewById(R.id.gamehole17score);
        hole18Score = view4.findViewById(R.id.gamehole18score);

        hole1Result = view4.findViewById(R.id.gamehole1result);
        hole2Result = view4.findViewById(R.id.gamehole2result);
        hole3Result = view4.findViewById(R.id.gamehole3result);
        hole4Result = view4.findViewById(R.id.gamehole4result);
        hole5Result = view4.findViewById(R.id.gamehole5result);
        hole6Result = view4.findViewById(R.id.gamehole6result);
        hole7Result = view4.findViewById(R.id.gamehole7result);
        hole8Result = view4.findViewById(R.id.gamehole8result);
        hole9Result = view4.findViewById(R.id.gamehole9result);
        hole10Result = view4.findViewById(R.id.gamehole10result);
        hole11Result = view4.findViewById(R.id.gamehole11result);
        hole12Result = view4.findViewById(R.id.gamehole12result);
        hole13Result = view4.findViewById(R.id.gamehole13result);
        hole14Result = view4.findViewById(R.id.gamehole14result);
        hole15Result = view4.findViewById(R.id.gamehole15result);
        hole16Result = view4.findViewById(R.id.gamehole16result);
        hole17Result = view4.findViewById(R.id.gamehole17result);
        hole18Result = view4.findViewById(R.id.gamehole18result);

        myScore = view4.findViewById(R.id.mygamescore);












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
                //setRoll(rollDistanceFragment);

            }
        });


        puttViewModel.getFragmentPuttEnd().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1){

                    setRollStopped(rollDistanceFragment);
                    reset = 1;

                }



            }
        });



        puttViewModel.getFragmentPuttStoppedFlag().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0 && reset == 1){
                    hole++;
                    reset = 0;

                };



            }
        });




        Integer[] intArray1 = {3, 6, 8, 10, 12, 15, 18, 21, 24};

        List<Integer> intList1 = Arrays.asList(intArray1);

        Collections.shuffle(intList1);

        intList1.toArray(intArray1);

        final Integer[] intArray2 = {3, 6, 8, 10, 12, 15, 18, 21, 24};

        List<Integer> intList2 = Arrays.asList(intArray2);

        Collections.shuffle(intList2);

        intList2.toArray(intArray2);



        mGameRef =  FirebaseDatabase.getInstance().getReference("players/" + firebasePlayer + "/games"); // Read friend list from the database
        gameID = mGameRef.push().getKey();



        hole1Length = intArray1[0];


        hole2Length = intArray1[1];
        //mGameRef.child(gameID).child("hole 2").setValue(hole2Length);

        hole3Length = intArray1[2];
        //mGameRef.child(gameID).child("hole 3").setValue(hole3Length);

        hole4Length = intArray1[3];
        //mGameRef.child(gameID).child("hole 4").setValue(hole4Length);

        hole5Length = intArray1[4];
        //mGameRef.child(gameID).child("hole 5").setValue(hole5Length);

        hole6Length = intArray1[5];
        //mGameRef.child(gameID).child("hole 6").setValue(hole6Length);

        hole7Length = intArray1[6];
        //mGameRef.child(gameID).child("hole 7").setValue(hole7Length);

        hole8Length = intArray1[7];
        //mGameRef.child(gameID).child("hole 8").setValue(hole8Length);

        hole9Length = intArray1[8];
        //mGameRef.child(gameID).child("hole 9").setValue(hole9Length);

        hole10Length = intArray2[0];
        //mGameRef.child(gameID).child("hole 10").setValue(hole10Length);

        hole11Length = intArray2[1];
        //mGameRef.child(gameID).child("hole 11").setValue(hole11Length);

        hole12Length = intArray2[2];
        //mGameRef.child(gameID).child("hole 12").setValue(hole12Length);

        hole13Length = intArray2[3];
        //mGameRef.child(gameID).child("hole 13").setValue(hole13Length);

        hole14Length = intArray2[4];
        //mGameRef.child(gameID).child("hole 14").setValue(hole14Length);

        hole15Length = intArray2[5];
        //mGameRef.child(gameID).child("hole 15").setValue(hole15Length);

        hole16Length = intArray2[6];
        //mGameRef.child(gameID).child("hole 16").setValue(hole6Length);

        hole17Length = intArray2[7];
        //mGameRef.child(gameID).child("hole 17").setValue(hole17Length);

        hole18Length = intArray2[8];
        //mGameRef.child(gameID).child("hole 18").setValue(hole18Length);





        saveGameHoleLengths();







        hole1Dist.setText(Integer.toString(intArray1[0]));
        hole2Dist.setText(Integer.toString(intArray1[1]));
        hole3Dist.setText(Integer.toString(intArray1[2]));
        hole4Dist.setText(Integer.toString(intArray1[3]));
        hole5Dist.setText(Integer.toString(intArray1[4]));
        hole6Dist.setText(Integer.toString(intArray1[5]));
        hole7Dist.setText(Integer.toString(intArray1[6]));
        hole8Dist.setText(Integer.toString(intArray1[7]));
        hole9Dist.setText(Integer.toString(intArray1[8]));
        hole10Dist.setText(Integer.toString(intArray2[0]));
        hole11Dist.setText(Integer.toString(intArray2[1]));
        hole12Dist.setText(Integer.toString(intArray2[2]));
        hole13Dist.setText(Integer.toString(intArray2[3]));
        hole14Dist.setText(Integer.toString(intArray2[4]));
        hole15Dist.setText(Integer.toString(intArray2[5]));
        hole16Dist.setText(Integer.toString(intArray2[6]));
        hole17Dist.setText(Integer.toString(intArray2[7]));
        hole18Dist.setText(Integer.toString(intArray2[8]));


        //storeGame();
















        return view4;


    }


            private void fbDatabase(){

                mGameRef =  FirebaseDatabase.getInstance().getReference("players/" + firebasePlayer + "/games"); // Read friend list from the database
                gameID = mGameRef.push().getKey();



                mGameRef.child(gameID).child("hole 1").setValue(hole1Length);
                mGameRef.child(gameID).child("hole 2").setValue(hole2Length);
                mGameRef.child(gameID).child("hole 3").setValue(hole3Length);
                mGameRef.child(gameID).child("hole 4").setValue(hole4Length);
                mGameRef.child(gameID).child("hole 5").setValue(hole5Length);
                mGameRef.child(gameID).child("hole 6").setValue(hole6Length);
                mGameRef.child(gameID).child("hole 7").setValue(hole7Length);
                mGameRef.child(gameID).child("hole 8").setValue(hole8Length);
                mGameRef.child(gameID).child("hole 9").setValue(hole9Length);
                mGameRef.child(gameID).child("hole 10").setValue(hole10Length);
                mGameRef.child(gameID).child("hole 11").setValue(hole11Length);
                mGameRef.child(gameID).child("hole 12").setValue(hole12Length);
                mGameRef.child(gameID).child("hole 13").setValue(hole13Length);
                mGameRef.child(gameID).child("hole 14").setValue(hole14Length);
                mGameRef.child(gameID).child("hole 15").setValue(hole15Length);
                mGameRef.child(gameID).child("hole 16").setValue(hole16Length);
                mGameRef.child(gameID).child("hole 17").setValue(hole17Length);
                mGameRef.child(gameID).child("hole 18").setValue(hole18Length);


            }


    private void storeGame(){


        mGameRef =  FirebaseDatabase.getInstance().getReference("players/" + firebasePlayer + "/games");

        mHolePlayer1 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player1/hole1");
        mDistancePlayer1 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player1/hole1/distance");
        mScorePlayer1 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player1/hole1/score");
        mStrokesPlayer1 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player1/hole1/strokes");
        mPlayer2 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player2");
        mHolePlayer2 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player2/hole1");
        mDistancePlayer2 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player2/hole1/distance");
        mScorePlayer2 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player2/hole1/score");
        mStrokesPlayer2 = FirebaseDatabase.getInstance().getReference("games/private/match 1/players/player2/hole1/strokes");


        //String id = mGameRef.push().getKey();




    }

    private void setRollStopped(Double distance){

        switch (hole){
            case 1:
                hole2Num.setBackgroundResource(R.drawable.valuecellborder);
                hole2Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole2Result.setBackgroundResource(R.drawable.valuecellborder);
                hole2Score.setBackgroundResource(R.drawable.valuecellborder);
                hole1Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole1Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole1Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole1Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole1Result.setText(Double.toString(distance));
                double hole1miss = distance - (double)(hole1Length);
                if (hole1miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole1Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                    }
                else if (hole1miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole1Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                    }
                else if (hole1miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole1Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                    }
                else if (hole1miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole1Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                    }
                else if (hole1miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole1Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                    }
                else if (hole1miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole1Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                    }
                else if (hole1miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole1Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                    }
                else {totalScoreValue = totalScoreValue - 1;
                    hole1Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                    }

                mGameRef.child(gameID).child("hole 1").child("score").setValue(hole1Score.getText());
                mGameRef.child(gameID).child("hole 1").child("total score").setValue(totalScoreValue);
                mGameRef.child(gameID).child("status").setValue("started");

                break;
            case 2:
                hole3Num.setBackgroundResource(R.drawable.valuecellborder);
                hole3Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole3Result.setBackgroundResource(R.drawable.valuecellborder);
                hole3Score.setBackgroundResource(R.drawable.valuecellborder);
                hole2Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole2Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole2Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole2Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole2Result.setText(Double.toString(distance));
                double hole2miss = distance - (double)(hole2Length);
                if (hole2miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole2Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole2miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole2Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole2miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole2Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole2miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole2Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole2miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole2Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole2miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole2Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole2miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole2Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole2Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 2").child("score").setValue(hole2Score.getText());
                mGameRef.child(gameID).child("hole 2").child("total score").setValue(totalScoreValue);
                break;
            case 3:
                hole4Num.setBackgroundResource(R.drawable.valuecellborder);
                hole4Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole4Result.setBackgroundResource(R.drawable.valuecellborder);
                hole4Score.setBackgroundResource(R.drawable.valuecellborder);
                hole3Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole3Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole3Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole3Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole3Result.setText(Double.toString(distance));
                double hole3miss = distance - (double)(hole3Length);
                if (hole3miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole3Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole3miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole3Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole3miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole3Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole3miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole3Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole3miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole3Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole3miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole3Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole3miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole3Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole3Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 3").child("score").setValue(hole3Score.getText());
                mGameRef.child(gameID).child("hole 3").child("total score").setValue(totalScoreValue);

                break;
            case 4:
                hole5Num.setBackgroundResource(R.drawable.valuecellborder);
                hole5Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole5Result.setBackgroundResource(R.drawable.valuecellborder);
                hole5Score.setBackgroundResource(R.drawable.valuecellborder);
                hole4Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole4Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole4Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole4Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole4Result.setText(Double.toString(distance));
                double hole4miss = distance - (double)(hole4Length);
                if (hole4miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole4Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole4miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole4Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole4miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole4Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole4miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole4Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole4miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole4Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole4miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole4Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole4miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole4Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole4Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 4").child("score").setValue(hole4Score.getText());
                mGameRef.child(gameID).child("hole 4").child("total score").setValue(totalScoreValue);
                break;
            case 5:
                hole6Num.setBackgroundResource(R.drawable.valuecellborder);
                hole6Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole6Result.setBackgroundResource(R.drawable.valuecellborder);
                hole6Score.setBackgroundResource(R.drawable.valuecellborder);
                hole5Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole5Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole5Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole5Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole5Result.setText(Double.toString(distance));
                double hole5miss = distance - (double)(hole5Length);
                if (hole5miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole5Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole5miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole2Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole5miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole5Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole5miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole5Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                 }
                else if (hole5miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole5Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole5miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole5Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole5miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole5Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole5Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 5").child("score").setValue(hole5Score.getText());
                mGameRef.child(gameID).child("hole 5").child("total score").setValue(totalScoreValue);
                break;
            case 6:
                hole7Num.setBackgroundResource(R.drawable.valuecellborder);
                hole7Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole7Result.setBackgroundResource(R.drawable.valuecellborder);
                hole7Score.setBackgroundResource(R.drawable.valuecellborder);
                hole6Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole6Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole6Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole6Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole6Result.setText(Double.toString(distance));
                double hole6miss = distance - (double)(hole6Length);
                if (hole6miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole6Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole6miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole6Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole6miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole6Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole6miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole6Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole6miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole6Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole6miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole6Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole6miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole6Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole6Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 6").child("score").setValue(hole6Score.getText());
                mGameRef.child(gameID).child("hole 6").child("total score").setValue(totalScoreValue);
                break;
            case 7:
                hole8Num.setBackgroundResource(R.drawable.valuecellborder);
                hole8Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole8Result.setBackgroundResource(R.drawable.valuecellborder);
                hole8Score.setBackgroundResource(R.drawable.valuecellborder);
                hole7Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole7Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole7Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole7Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole7Result.setText(Double.toString(distance));
                double hole7miss = distance - (double)(hole7Length);
                if (hole7miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole7Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole7miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole7Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole7miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole7Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole7miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole7Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole7miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole7Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole7miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole7Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole7miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole7Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole7Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 7").child("score").setValue(hole7Score.getText());
                mGameRef.child(gameID).child("hole 7").child("total score").setValue(totalScoreValue);
                break;
            case 8:
                hole9Num.setBackgroundResource(R.drawable.valuecellborder);
                hole9Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole9Result.setBackgroundResource(R.drawable.valuecellborder);
                hole9Score.setBackgroundResource(R.drawable.valuecellborder);
                hole8Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole8Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole8Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole8Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole8Result.setText(Double.toString(distance));
                double hole8miss = distance - (double)(hole8Length);
                if (hole8miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole8Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole8miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole8Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole8miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole8Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole8miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole8Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole8miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole8Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole8miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole8Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole8miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole8Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole8Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 8").child("score").setValue(hole8Score.getText());
                mGameRef.child(gameID).child("hole 8").child("total score").setValue(totalScoreValue);
                break;
            case 9:
                hole10Num.setBackgroundResource(R.drawable.valuecellborder);
                hole10Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole10Result.setBackgroundResource(R.drawable.valuecellborder);
                hole10Score.setBackgroundResource(R.drawable.valuecellborder);
                hole9Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole9Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole9Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole9Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole9Result.setText(Double.toString(distance));
                double hole9miss = distance - (double)(hole9Length);
                if (hole9miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole9Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole9miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole9Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole9miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole9Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole9miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole9Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole9miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole9Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole9miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole9Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole9miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole9Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole9Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 9").child("score").setValue(hole9Score.getText());
                mGameRef.child(gameID).child("hole 9").child("total score").setValue(totalScoreValue);
                break;
            case 10:
                hole11Num.setBackgroundResource(R.drawable.valuecellborder);
                hole11Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole11Result.setBackgroundResource(R.drawable.valuecellborder);
                hole11Score.setBackgroundResource(R.drawable.valuecellborder);
                hole10Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole10Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole10Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole10Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole10Result.setText(Double.toString(distance));
                double hole10miss = distance - (double)(hole10Length);
                if (hole10miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole10Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole10miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole10Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole10miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole10Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole10miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole10Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole10miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole10Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole10miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole10Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole10miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole10Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole10Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 10").child("score").setValue(hole11Score.getText());
                mGameRef.child(gameID).child("hole 11").child("total score").setValue(totalScoreValue);
                break;
            case 11:
                hole12Num.setBackgroundResource(R.drawable.valuecellborder);
                hole12Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole12Result.setBackgroundResource(R.drawable.valuecellborder);
                hole12Score.setBackgroundResource(R.drawable.valuecellborder);
                hole11Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole11Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole11Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole11Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole11Result.setText(Double.toString(distance));
                double hole11miss = distance - (double)(hole11Length);
                if (hole11miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole11Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole11miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole11Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole11miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole11Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole11miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole11Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole11miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole11Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole11miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole11Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole11miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole11Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole11Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 11").child("score").setValue(hole11Score.getText());
                mGameRef.child(gameID).child("hole 11").child("total score").setValue(totalScoreValue);
                break;
            case 12:
                hole13Num.setBackgroundResource(R.drawable.valuecellborder);
                hole13Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole13Result.setBackgroundResource(R.drawable.valuecellborder);
                hole13Score.setBackgroundResource(R.drawable.valuecellborder);
                hole12Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole12Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole12Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole12Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole12Result.setText(Double.toString(distance));
                double hole12miss = distance - (double)(hole12Length);
                if (hole12miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole12Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole12miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole12Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole12miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole12Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole12miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole12Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole12miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole12Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole12miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole12Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole12miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole12Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole12Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 12").child("score").setValue(hole12Score.getText());
                mGameRef.child(gameID).child("hole 12").child("total score").setValue(totalScoreValue);
                break;
            case 13:
                hole14Num.setBackgroundResource(R.drawable.valuecellborder);
                hole14Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole14Result.setBackgroundResource(R.drawable.valuecellborder);
                hole14Score.setBackgroundResource(R.drawable.valuecellborder);
                hole13Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole13Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole13Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole13Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole13Result.setText(Double.toString(distance));
                double hole13miss = distance - (double)(hole13Length);
                if (hole13miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole13Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole13miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole13Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole13miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole13Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole13miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole13Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole13miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole13Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole13miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole13Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole13miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole13Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole13Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 13").child("score").setValue(hole13Score.getText());
                mGameRef.child(gameID).child("hole 13").child("total score").setValue(totalScoreValue);
                break;
            case 14:
                hole15Num.setBackgroundResource(R.drawable.valuecellborder);
                hole15Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole15Result.setBackgroundResource(R.drawable.valuecellborder);
                hole15Score.setBackgroundResource(R.drawable.valuecellborder);
                hole14Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole14Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole14Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole14Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole14Result.setText(Double.toString(distance));
                double hole14miss = distance - (double)(hole14Length);
                if (hole14miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole14Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole14miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole14Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole14miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole14Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole14miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole14Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole14miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole14Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole14miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole14Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole14miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole14Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole14Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 14").child("score").setValue(hole14Score.getText());
                mGameRef.child(gameID).child("hole 14").child("total score").setValue(totalScoreValue);
                break;
            case 15:
                hole16Num.setBackgroundResource(R.drawable.valuecellborder);
                hole16Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole16Result.setBackgroundResource(R.drawable.valuecellborder);
                hole16Score.setBackgroundResource(R.drawable.valuecellborder);
                hole15Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole15Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole15Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole15Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole15Result.setText(Double.toString(distance));
                double hole15miss = distance - (double)(hole15Length);
                if (hole15miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole15Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole15miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole15Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole15miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole15Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole15miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole15Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole15miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole15Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole15miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole15Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole15miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole15Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole15Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 15").child("score").setValue(hole15Score.getText());
                mGameRef.child(gameID).child("hole 15").child("total score").setValue(totalScoreValue);
                break;
            case 16:
                hole17Num.setBackgroundResource(R.drawable.valuecellborder);
                hole17Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole17Result.setBackgroundResource(R.drawable.valuecellborder);
                hole17Score.setBackgroundResource(R.drawable.valuecellborder);
                hole16Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole16Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole16Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole16Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole16Result.setText(Double.toString(distance));
                double hole16miss = distance - (double)(hole16Length);
                if (hole16miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole16Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole16miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole16Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole16miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole16Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole16miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole16Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole16miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole16Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole16miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole16Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole16miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole16Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole16Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                mGameRef.child(gameID).child("hole 16").child("score").setValue(hole16Score.getText());
                mGameRef.child(gameID).child("hole 16").child("total score").setValue(totalScoreValue);
                break;
            case 17:
                hole18Num.setBackgroundResource(R.drawable.valuecellborder);
                hole18Dist.setBackgroundResource(R.drawable.valuecellborder);
                hole18Result.setBackgroundResource(R.drawable.valuecellborder);
                hole18Score.setBackgroundResource(R.drawable.valuecellborder);
                hole17Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole17Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole17Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole17Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole13Result.setText(Double.toString(distance));
                double hole17miss = distance - (double)(hole17Length);
                if (hole17miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole17Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole17miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole17Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole17miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole17Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole17miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole17Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole17miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole17Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole17miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole17Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole17miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole17Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole17Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }

                mGameRef.child(gameID).child("hole 17").child("score").setValue(hole17Score.getText());
                mGameRef.child(gameID).child("hole 17").child("total score").setValue(totalScoreValue);
                break;

            case 18:
                hole18Num.setBackgroundResource(R.drawable.valuecellborderempty);
                hole18Dist.setBackgroundResource(R.drawable.valuecellborderempty);
                hole18Result.setBackgroundResource(R.drawable.valuecellborderempty);
                hole18Score.setBackgroundResource(R.drawable.valuecellborderempty);
                hole18Result.setText(Double.toString(distance));
                double hole18miss = distance - (double)(hole18Length);
                if (hole18miss < -3.0){
                    totalScoreValue = totalScoreValue + 3;
                    hole18Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole18miss < -2){
                    totalScoreValue = totalScoreValue + 2;
                    hole18Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole18miss < -1){
                    totalScoreValue = totalScoreValue + 1;
                    hole18Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole18miss > 4){
                    totalScoreValue = totalScoreValue + 3;
                    hole18Score.setText("+3");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole18miss > 3){
                    totalScoreValue = totalScoreValue + 2;
                    hole18Score.setText("+2");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole18miss > 2){
                    totalScoreValue = totalScoreValue + 1;
                    hole18Score.setText("+1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else if (hole18miss > 1){
                    totalScoreValue = totalScoreValue;
                    hole18Score.setText("-");
                    myScore.setText(Integer.toString(totalScoreValue));
                }
                else {totalScoreValue = totalScoreValue - 1;
                    hole18Score.setText("-1");
                    myScore.setText(Integer.toString(totalScoreValue));
                }

                mGameRef.child(gameID).child("hole 18").child("score").setValue(hole18Score.getText());
                mGameRef.child(gameID).child("hole 18").child("total score").setValue(totalScoreValue);
                mGameRef.child(gameID).child("status").setValue("completed");
                break;

            default:
                break;

        }





    }


    private void saveGameHoleLengths(){








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
            public void onChanged(Double integer) {
                rollDistanceFragment = integer
                ;
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
                //countUpScore(puttMadeDistanceFragment);


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









    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GameFragment.OnFragmentInteractionListener) {
            mListener = (GameFragment.OnFragmentInteractionListener) context;



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

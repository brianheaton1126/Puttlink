package com.golftronics.golfball.ble;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
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

    private int lastMadePuttFlag;

    FirebaseDatabase firebaseDatabaseFragment;
    private DatabaseReference mAttempts1Fragment;
    private DatabaseReference mDistance1Fragment;
    private DatabaseReference mMakes1Fragment;
    private DatabaseReference mPuttsFragment;
    private FirebaseAuth mAuthFragment;

    public BlankFragment() {
        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
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
        mPuttsFragment =  FirebaseDatabase.getInstance().getReference("all putts");
        mDistance1Fragment = mPuttsFragment.child("3 feet");
        mAttempts1Fragment = mDistance1Fragment.child("attempted");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        View view = inflater.inflate(R.layout.fragment_blank, container, false);



        timeRange = 0;

        slopeDirection = "All";

        valuePicker2 = 25;

        stimpMin = 8;
        stimpMax = 13;

        valuePicker1 = 1;

        prg = (ProgressBar)view.findViewById(R.id.progressBar3);

        prg1 = (ProgressBar)view.findViewById(R.id.progressBar4);

        makePercentageText = (TextView)view.findViewById(R.id.makePercentage);

        makePercentageTextAll = (TextView)view.findViewById(R.id.makePercentageAll);

        number_attempted = (TextView)view.findViewById(R.id.number_attempted);

        number_made = (TextView)view.findViewById(R.id.number_made);

        todayButton = (Button) view.findViewById(R.id.today_button);

        weekButton = (Button) view.findViewById(R.id.week_button);

        monthButton = (Button) view.findViewById(R.id.month_button);

        dateMenu = (TextView) view.findViewById(R.id.textView17);

        dateSelected = (TextView) view.findViewById(R.id.date_selected);
        slopeSelect = (TextView) view.findViewById(R.id.slope_selected);
        stimpSelect = (TextView) view.findViewById(R.id.stimp_selected);

        firebaseValue = (TextView) view.findViewById(R.id.firebase_value);

        // Read from the database
        mAttempts1Fragment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = String.valueOf(dataSnapshot.getValue());
                Log.d(TAG, "Value is: " + value);
                firebaseValue.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        todayButton.setText("Date");
        monthButton.setText("Slope");
        monthButton.setText("Stimp");


        registerForContextMenu(todayButton);
        registerForContextMenu(weekButton);
        registerForContextMenu(monthButton);



        picker1 = (NumberPicker)view.findViewById(R.id.number_picker1);
        picker1.setMaxValue(25);
        picker1.setMinValue(1);
        picker1.setValue(1);

        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                valuePicker1 = picker1.getValue();
                //minDist = valuePicker1;
                picker2.setMinValue(valuePicker1);
                picker1.setWrapSelectorWheel(false);
                refreshQuery(valuePicker1, valuePicker2);
                if(slopeDirection == "All"){
                    refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);}
                else{
                    refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                }
            }
        });

        picker2 = (NumberPicker)view.findViewById(R.id.number_picker);
        picker2.setMaxValue(25);
        picker2.setValue(25);

        picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                valuePicker2 = picker2.getValue();
                picker1.setMaxValue(valuePicker2);
                picker2.setWrapSelectorWheel(false);
                //maxDist = valuePicker2;
                refreshQuery(valuePicker1, valuePicker2);
                if(slopeDirection == "All"){
                    refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);}
                else{
                    refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                }

            }
        });


        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });






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





        return view;


        }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId()==R.id.today_button) {


            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.range_filter_menu, menu);
        }
        if(v.getId()==R.id.week_button) {


            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.slope_menu, menu);
        }
        if(v.getId()==R.id.month_button) {


            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.stimp_menu, menu);
        }

    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.slope_all:
                slopeSelect.setText("All");
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                // refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2,slopeDirection,stimpMin,stimpMax);
                return true;
            case R.id.slope_level:
                slopeSelect.setText("Level");
                slopeDirection = "Level";
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                //refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection,stimpMin,stimpMax);
                slopeDirection = "Level";
                break;
                //return true;
            case R.id.slope_up:
                slopeSelect.setText("Uphill");
                slopeDirection = "UP";
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                break;
                //return true;
            case R.id.slope_down:
                slopeSelect.setText("Downhill");
                slopeDirection = "DN";
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                break;
                //return true;
            case R.id.slope_LR:
                slopeSelect.setText("Left-Right");
                slopeDirection = "LR";
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                //refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);

                break;
                //return true;
            case R.id.slope_RL:
                slopeSelect.setText("Right-Left");
                slopeDirection = "RL";
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
                break;
                //return true;
            case R.id.stimp_all:
                stimpSelect.setText("All");
                stimpMin = 8;
                stimpMax = 13;
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);
                return true;
            case R.id.stimp8_9:
                stimpSelect.setText("8 to 9");
                stimpMin = 8;
                stimpMax = 9;
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);
                return true;
            case R.id.stimp9_10:
                stimpSelect.setText("9 to 10");
                stimpMin = 9;
                stimpMax = 10;
                valuePicker1 = picker1.getValue();
                valuePicker2 =picker2.getValue();
                if (slopeDirection == "All"){
                    refreshQueryDateRangeStimp(timeRange,valuePicker1, valuePicker2,stimpMin,stimpMax);
                    }
                else {
                    refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);
                     }
                return true;
            case R.id.stimp10_11:
                stimpSelect.setText("10 to 11");
                stimpMin = 10;
                stimpMax = 11;
                valuePicker1 = picker1.getValue();
                valuePicker2 = picker2.getValue();
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);
                return true;
            case R.id.stimp11_12:
                stimpSelect.setText("11 to 12");
                stimpMin = 11;
                stimpMax = 12;
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);
                return true;
            case R.id.stimp12_13:
                stimpSelect.setText("12 to 13");
                stimpMin = 12;
                stimpMax = 13;
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);
                return true;
            case R.id.over11:
                stimpSelect.setText("Over 11");
                stimpMin = 11;
                stimpMax = 13;
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);
                return true;
            case R.id.under11:
                stimpSelect.setText("Under < 11");
                stimpMin = 8;
                stimpMax = 11;
                refreshQueryDateRangeSlopeDirWithStimp(timeRange, valuePicker1, valuePicker2, slopeDirection, stimpMin, stimpMax);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
        if(slopeDirection == "All"){
            refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);}
        else {
            refreshQueryDateRangeSlopeDir(timeRange, valuePicker1, valuePicker2, slopeDirection);
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        puttViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(PuttViewModel.class);




        refreshQueryDateRange(timeRange, valuePicker1, valuePicker2);

        refreshQuery(valuePicker1, valuePicker2);

        puttViewModel.getLastEntryMadeStatus().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer lastEntryMadeStatus) {


                 lastMadePuttFlag = lastEntryMadeStatus;

                if (lastMadePuttFlag == 1) {


                    refreshBar();
                }
            }

        });


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




        public void refreshBar(){

        double makePercentage;

        if (overMinDistPuttsDateRange == 0){
            makePercentage = 0;
        }
        else {
            makePercentage = (madePuttsDateRange / overMinDistPuttsDateRange) * 100;
        }

        int makePercentageRound = (int)(Math.round(makePercentage*100)/100D);

        //int makePercentageInt = (int)(madePuttsDateRange);

        //String makePercentageTodayText = Integer.toString(makePercentageTodayRound);

        //makePercentageText.setText(makePercentageTodayText);

        String makePercentageTextString = Integer.toString(makePercentageRound);

        makePercentageText.setText(makePercentageTextString + "%");



        //int progBar = (int) makePercentageRound;

        prg1.setProgress(makePercentageRound);

    }

    public void refreshBarAggregate(){

        double makePercentageAggregate = ((allMadePuttsMinDist/allOverMinDistPutts)*100);

        int makePercentageAggregateRound = (int)(Math.round(makePercentageAggregate*100)/100D);

        //int makePercentageAggregateRound = (int)(allMadePuttsMinDist);

        String makePercentageAllText = Integer.toString(makePercentageAggregateRound);

       makePercentageTextAll.setText(makePercentageAllText + "%");

       prg.setProgress(makePercentageAggregateRound);

       int allMadePuttsMinDistInt = (int) allMadePuttsMinDist;

       number_attempted.setText(Integer.toString(allOverMinDistPutts));

       number_made.setText(Integer.toString(allMadePuttsMinDistInt));

    }

    public void refreshBarWithSlope(){

        double makePercentageWithSlope;

        if (overMinDistPuttsDateRangeWithSlope == 0){
            makePercentageWithSlope = 0;
        }
        else {
            makePercentageWithSlope = (madePuttsDateRangeWithSlope / overMinDistPuttsDateRangeWithSlope) * 100;
        }

        int makePercentageWithSlopeRound = (int)(Math.round(makePercentageWithSlope*100)/100D);

        //int makePercentageInt = (int)(overMinDistPuttsDateRangeWithSlope);

        //String makePercentageTodayText = Integer.toString(makePercentageTodayRound);

        //makePercentageText.setText(makePercentageTodayText);

        String makePercentageWithSlopeTextString = Integer.toString(makePercentageWithSlopeRound);

        makePercentageText.setText(makePercentageWithSlopeTextString + "%");



        //int progBar = (int) makePercentageRound;

        prg1.setProgress(makePercentageWithSlopeRound);

    }


    public void refreshBarWithSlopeWithStimp(){

        double makePercentageWithSlopeWithStimp;

        if (madePuttsDateRangeWithSlopeWithStimp == 0){
            makePercentageWithSlopeWithStimp = 0;
        }

        else {
            makePercentageWithSlopeWithStimp = (madePuttsDateRangeWithSlopeWithStimp / overMinDistPuttsDateRangeWithSlopeWithStimp) * 100;
        }


        int makePercentageWithSlopeWithStimpRound = (int)(Math.round(makePercentageWithSlopeWithStimp*100)/100D);

        //int makePercentageInt = (int)(overMinDistPuttsDateRangeWithSlopeWithStimp);

        //String makePercentageTodayText = Integer.toString(makePercentageTodayRound);

        //makePercentageText.setText(makePercentageTodayText);

        String makePercentageWithSlopeWithStimpTextString = Integer.toString(makePercentageWithSlopeWithStimpRound);

        makePercentageText.setText(makePercentageWithSlopeWithStimpTextString + "%");



        //int progBar = (int) makePercentageRound;

        prg1.setProgress(makePercentageWithSlopeWithStimpRound);

    }





    public void refreshQuery(int picker1, int picker2) {

        puttViewModel.getAllPuttsOverMinDist(picker1, picker2).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer allPuttsMinDist) {

                allOverMinDistPutts = allPuttsMinDist;
                refreshBarAggregate();
            }
        });

        puttViewModel.getAllMadePutts(picker1, picker2).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer allPuttsMadeMinDist) {

                allMadePuttsMinDist = allPuttsMadeMinDist;
                refreshBarAggregate();
            }
        });




    }




    public void refreshQueryDateRange(long timeRange, int picker1, int picker2) {

        puttViewModel.getPuttsOverMinDistByDate(timeRange, picker1, picker2).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDistByDate) {

                overMinDistPuttsDateRange = puttsMinDistByDate;
                refreshBar();
            }
        });

        puttViewModel.getMadePutts(timeRange, picker1, picker2).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMadeByDate) {

                madePuttsDateRange = puttsMadeByDate;
                refreshBar();
            }
        });


    }

    public void refreshQueryDateRangeSlopeDir(long timeRange, int picker1, int picker2, String slopeDirection) {


        puttViewModel.getPuttsOverMinDistByDateWithSlope(timeRange, picker1, picker2, slopeDirection).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDistByDateWithSlope) {

                //overMinDistPuttsDateRangeWithSlope = puttsMinDistByDateWithSlope;
                overMinDistPuttsDateRange = puttsMinDistByDateWithSlope;

                //refreshBarWithSlope();
                refreshBar();
            }
        });

        puttViewModel.getMadePuttsWithSlope(timeRange, picker1, picker2, slopeDirection);

         puttViewModel.getMadePuttsWithSlope(timeRange, picker1, picker2, slopeDirection).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMadeByDateWithSlope) {

                //madePuttsDateRangeWithSlope = puttsMadeByDateWithSlope;
                madePuttsDateRange = puttsMadeByDateWithSlope;
                // refreshBarWithSlope();
                refreshBar();
            }
        });

    }



    //method to read database for distance, time range, and stimp

    public void refreshQueryDateRangeStimp(long timeRange, int picker1, int picker2, double stimpMin, double stimpMax) {


        puttViewModel.getPuttsOverMinDistByDateWithStimp(timeRange, picker1, picker2, stimpMin, stimpMax).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDistByDateWithStimp) {

                //overMinDistPuttsDateRangeWithSlope = puttsMinDistByDateWithSlope;
                overMinDistPuttsDateRange = puttsMinDistByDateWithStimp;

                //refreshBarWithSlope();
                refreshBar();
            }
        });

        puttViewModel.getMadePuttsWithStimp(timeRange, picker1, picker2, stimpMin, stimpMax);

        puttViewModel.getMadePuttsWithStimp(timeRange, picker1, picker2, stimpMin, stimpMax).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMadeByDateWithStimp) {

                //madePuttsDateRangeWithSlope = puttsMadeByDateWithSlope;
                madePuttsDateRange = puttsMadeByDateWithStimp;
                // refreshBarWithSlope();
                refreshBar();
            }
        });

    }








    public void refreshQueryDateRangeSlopeDirWithStimp(long timeRange, int picker1, int picker2, String slopeDirection, double stimpMin, double stimpMax) {

        puttViewModel.getPuttsOverMinDistByDateWithSlopeWithStimp(timeRange, picker1, picker2, slopeDirection, stimpMin, stimpMax).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMinDistByDateWithSlopeWithStimp) {

                //overMinDistPuttsDateRangeWithSlopeWithStimp = puttsMinDistByDateWithSlopeWithStimp;
                overMinDistPuttsDateRange = puttsMinDistByDateWithSlopeWithStimp;

                //refreshBarWithSlopeWithStimp();
                refreshBar();
            }
        });

        puttViewModel.getMadePuttsWithSlopeWithStimp(timeRange, picker1, picker2, slopeDirection, stimpMin, stimpMax).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer puttsMadeByDateWithSlopeWithStimp) {

                //madePuttsDateRangeWithSlopeWithStimp = puttsMadeByDateWithSlopeWithStimp;
                madePuttsDateRange = puttsMadeByDateWithSlopeWithStimp;


                //refreshBarWithSlopeWithStimp();
                refreshBar();
            }
        });








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

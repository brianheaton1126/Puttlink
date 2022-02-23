package com.golftronics.golfball.ble;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragmentNew.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragmentNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragmentNew extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static Button scan_button;



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

    private Button stimpButton;

    private Button gamesButton;

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

    public HomeFragmentNew() {
        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static HomeFragmentNew newInstance() {
        HomeFragmentNew fragment = new HomeFragmentNew();
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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        View view = inflater.inflate(R.layout.home_practice_layout, container, false);
        /*
        scan_button = (Button)view.findViewById(R.id.scan);

                scan_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openScanActivity();
                    }
                });

        stimpButton = (Button)view.findViewById(R.id.measureStimpButton);

        stimpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStimpFragment();
            }
        });

        gamesButton = (Button)view.findViewById(R.id.contest_button);

        gamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGamesFragment();
            }
        });

        */
        /*
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
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
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


    public void openScanActivity(){
        Intent scanIntent = new Intent(getContext(),ScanActivity.class);
        startActivity(scanIntent);
    }

    public void openStimpFragment(){

        Stimp StimpFragment= new Stimp();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, StimpFragment)
                .addToBackStack(null)
                .commit();




    }

    public void openGamesFragment(){

        GameFragment gameFragment= new GameFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, gameFragment)
                .addToBackStack(null)
                .commit();




    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);



    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

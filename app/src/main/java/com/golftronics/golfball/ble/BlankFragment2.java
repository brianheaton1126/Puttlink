package com.golftronics.golfball.ble;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    private BlankFragment2.OnFragmentInteractionListener mListener;

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






    public BlankFragment2() {
        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static BlankFragment2 newInstance() {
        BlankFragment2 fragment2 = new BlankFragment2();
        Bundle args = new Bundle();

        return fragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        currentMilli = System.currentTimeMillis();

        timeRange = (System.currentTimeMillis() - DAYMILLI);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        View view2 = inflater.inflate(R.layout.target_distance, container, false);

        /*
        cellOne = (TextView)view2.findViewById(R.id.button1);
        cellTwo = (TextView)view2.findViewById(R.id.button2);
        cellThree = (TextView)view2.findViewById(R.id.button3);
        cellFour = (TextView)view2.findViewById(R.id.button4);
        cellFive = (TextView)view2.findViewById(R.id.button5);
        cellSix = (TextView)view2.findViewById(R.id.button6);
        cellSeven = (TextView)view2.findViewById(R.id.button7);
        cellEight = (TextView)view2.findViewById(R.id.button8);
        cellNine = (TextView)view2.findViewById(R.id.button9);
        cellTen = (TextView)view2.findViewById(R.id.button10);
        cellEleven = (TextView)view2.findViewById(R.id.button11);
        cellTwelve = (TextView)view2.findViewById(R.id.button12);
        cellThirteen = (TextView)view2.findViewById(R.id.button13);
        cellFourteen = (TextView)view2.findViewById(R.id.button14);
        cellFifteen = (TextView)view2.findViewById(R.id.button15);
        cellSixteen = (TextView)view2.findViewById(R.id.button16);

        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        List<Integer> intList = Arrays.asList(intArray);

        Collections.shuffle(intList);

        intList.toArray(intArray);


        cellOne.setText(intArray[0]);
        cellTwo.setText(intArray[1]);
        cellTwo.setText(intArray[2]);
        cellTwo.setText(intArray[3]);
        cellTwo.setText(intArray[4]);
        cellTwo.setText(intArray[5]);
        cellTwo.setText(intArray[6]);
        cellTwo.setText(intArray[7]);
        cellTwo.setText(intArray[8]);
        cellTwo.setText(intArray[9]);
        cellTwo.setText(intArray[10]);
        cellTwo.setText(intArray[11]);
        cellTwo.setText(intArray[12]);
        cellTwo.setText(intArray[13]);
        cellTwo.setText(intArray[14]);
        cellTwo.setText(intArray[15]);
        cellTwo.setText(intArray[16]);

        */




        sBar = (SeekBar) view2.findViewById(R.id.seekBar);

        button = (View) view2.findViewById(R.id.button);

        cup = (TextView) view2.findViewById(R.id.cup);


        sBar.setMax(10);



        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            float pval = 3;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                pval = (float)(double) ((progress * 0.25)+3);
                button.setScaleX(pval);
                button.setScaleY(pval);



            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






        return view2;


        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);






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
        if (context instanceof BlankFragment2.OnFragmentInteractionListener) {
            mListener = (BlankFragment2.OnFragmentInteractionListener) context;



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

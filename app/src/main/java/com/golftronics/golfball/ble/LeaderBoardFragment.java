package com.golftronics.golfball.ble;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeaderBoardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeaderBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderBoardFragment extends Fragment {
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

    private EditText enterSession;
    private EditText enterPercent;
    private Button storeValue;

    private int lastMadePuttFlag;

    private View view5;

    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    BarChart barChart;
    BarDataSet barDataSet;
    ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();

    BarData barData;


    FirebaseDatabase firebaseDatabaseFragment;
    private DatabaseReference mSession1Fragment;
    private DatabaseReference mDistance1Fragment;
    private DatabaseReference mMakes1Fragment;
    private DatabaseReference mPuttsFragment;
    private FirebaseAuth mAuthFragment;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static LeaderBoardFragment newInstance() {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_blank, container, false);*/

        view5 = inflater.inflate(R.layout.leaderboard_fragment, container, false);

        return view5;

    }



        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        puttViewModel = new ViewModelProvider(getActivity(), ViewModelProvider
                .AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(PuttViewModel.class);





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

package com.golftronics.golfball.ble;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Stimp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stimp extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Double rollDistanceFragment;


    public int fragmentVelocityStimp = 0;

    public int rollStoppedFlagStimp;

    private TextView rollDistanceStimp;

    private PuttViewModel puttViewModel;

    public Stimp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Stimp.
     */
    // TODO: Rename and change types and number of parameters
    public static Stimp newInstance(String param1, String param2) {
        Stimp fragment = new Stimp();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view4 = inflater.inflate(R.layout.fragment_stimp, container, false);
        return view4;

    }

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
                //rollStoppedFlag = integer;
                //setRollStoppedColor(rollStoppedFlag);
            }
        });










        puttViewModel.getFragmentVelocityEnd().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double velocity) {
                if (velocity != null) {
                    //fragmentVelocity = (int) Math.round(velocity);
                    //String newVelocity = Integer.toString(fragmentVelocity);





                    //setVelocity(fragmentVelocity);
                }
            }
        });

    }}
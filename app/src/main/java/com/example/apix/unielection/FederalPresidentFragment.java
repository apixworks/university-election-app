package com.example.apix.unielection;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FederalPresidentFragment extends Fragment implements SelectedPositionInterface {

    RecyclerView FederalPresidentRecycler;
    CandidateAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    MainInterface mainInterface;
    Activity mActivity;

    List<Candidate> FederalPresidentList;

    private static String TAG = FederalPresidentFragment.class.getSimpleName();

    public FederalPresidentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_federal_president, container, false);
        FederalPresidentRecycler = (RecyclerView)view.findViewById(R.id.FederalPresidentRecycler);

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new CandidateAdapter(getContext(),this,FederalPresidentList);

        FederalPresidentRecycler.setLayoutManager(layoutManager);
        FederalPresidentRecycler.setAdapter(adapter);


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mainInterface = (MainInterface) mActivity;
    }

    @Override
    public void onItemClick(CandidateAdapter.CandidateViewHolder holder, int position) {
        mainInterface.fpChoice(position);
    }

    interface MainInterface{
        void fpChoice(int data);
    }

    public void getList(List<Candidate> list){
        FederalPresidentList = list;
    }

}



package com.example.apix.unielection;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FederalVicePresidentFragment extends Fragment implements SelectedPositionInterface {

    RecyclerView recyclerView;
    CandidateAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    MainInterface mainInterface;
    Activity mActivity;

    List<Candidate> FederalVicePresidentList;

    public FederalVicePresidentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_federal_vice_president, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.FederalVicePresidentRecycler);

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new CandidateAdapter(getContext(),this,FederalVicePresidentList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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
//        Toast.makeText(getContext(),position+"",Toast.LENGTH_SHORT).show();
        mainInterface.fvpChoice(position);
    }

    interface MainInterface{
        void fvpChoice(int data);
    }

    public void getList(List<Candidate> list){
        FederalVicePresidentList = list;
    }
}

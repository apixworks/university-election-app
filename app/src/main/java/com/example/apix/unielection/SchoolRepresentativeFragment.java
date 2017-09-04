package com.example.apix.unielection;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolRepresentativeFragment extends Fragment implements SelectedPositionInterface {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CandidateAdapter adapter;

    MainInterface mainInterface;
    Activity mActivity;

    List<Candidate> SchoolRepresentativeList;

    public SchoolRepresentativeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_school_representative, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.SchoolRepresentativeRecycler);

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new CandidateAdapter(getContext(),this,SchoolRepresentativeList);

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
        mainInterface.srChoice(position);
    }

    interface MainInterface{
        void srChoice(int data);
    }

    public void getList(List<Candidate> list){
        SchoolRepresentativeList = list;
    }
}

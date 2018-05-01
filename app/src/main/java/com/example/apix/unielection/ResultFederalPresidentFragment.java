package com.example.apix.unielection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


public class ResultFederalPresidentFragment extends Fragment {

    ImageView img;
    ImageView backImg;
    TextView name;
    TextView program;
    TextView yos;
    TextView gender;
    TextView votes;
    RecyclerView recyclerView;

    CandidateResultAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    List<CandidateResult> FederalPresidentList;
    List<CandidateResult> mFederalPresidentList;

    RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.prof)
            .error(R.drawable.ic_error)
            .fallback(R.drawable.prof);

    public ResultFederalPresidentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_federal_president, container, false);

        img = (ImageView)view.findViewById(R.id.img);
        backImg = (ImageView)view.findViewById(R.id.backImg);
        name = (TextView)view.findViewById(R.id.name);
        program = (TextView)view.findViewById(R.id.program);
        yos = (TextView)view.findViewById(R.id.yosFederalPresident);
        gender = (TextView)view.findViewById(R.id.gender);
        votes = (TextView)view.findViewById(R.id.votesFederalPresident);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        adapter = new CandidateResultAdapter(getContext(),mFederalPresidentList);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        GlideApp.with(getContext()).load("http://192.168.43.125/uni-election/img/candidates/"+FederalPresidentList.get(0).img_url).apply(requestOptions).into(img);
        GlideApp.with(getContext()).load("http://192.168.43.125/uni-election/img/candidates/"+FederalPresidentList.get(0).img_url).apply(requestOptions).into(backImg);
        name.setText(FederalPresidentList.get(0).name);
        program.setText(FederalPresidentList.get(0).program);
        yos.setText("Year Of Study: "+FederalPresidentList.get(0).yos);
        gender.setText(FederalPresidentList.get(0).gender);
        votes.setText(String.valueOf(FederalPresidentList.get(0).votes));

        return view;
    }

    public void getList(List<CandidateResult> list){
        FederalPresidentList = list;
        mFederalPresidentList = new ArrayList<>();
        for(int i=1;i<FederalPresidentList.size();i++){
            mFederalPresidentList.add(FederalPresidentList.get(i));
        }
    }


}

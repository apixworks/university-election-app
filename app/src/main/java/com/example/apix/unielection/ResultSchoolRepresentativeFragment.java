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


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultSchoolRepresentativeFragment extends Fragment {

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

    List<CandidateResult> SchoolRepresentativeList;
    List<CandidateResult> mSchoolRepresentativeList;

    RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.prof)
            .error(R.drawable.ic_error)
            .fallback(R.drawable.prof);

    public ResultSchoolRepresentativeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_school_representative, container, false);

        img = (ImageView)view.findViewById(R.id.img);
        backImg = (ImageView)view.findViewById(R.id.backImg);
        name = (TextView)view.findViewById(R.id.name);
        program = (TextView)view.findViewById(R.id.program);
        yos = (TextView)view.findViewById(R.id.yosSR);
        gender = (TextView)view.findViewById(R.id.gender);
        votes = (TextView)view.findViewById(R.id.votesSR);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        adapter = new CandidateResultAdapter(getContext(),mSchoolRepresentativeList);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        GlideApp.with(getContext()).load("http://192.168.43.125/uni-election/img/candidates/"+SchoolRepresentativeList.get(0).img_url).apply(requestOptions).into(img);
        GlideApp.with(getContext()).load("http://192.168.43.125/uni-election/img/candidates/"+SchoolRepresentativeList.get(0).img_url).apply(requestOptions).into(backImg);
        name.setText(SchoolRepresentativeList.get(0).name);
        program.setText(SchoolRepresentativeList.get(0).program);
        yos.setText("Year Of Study: "+SchoolRepresentativeList.get(0).yos);
        gender.setText(SchoolRepresentativeList.get(0).gender);
        votes.setText(String.valueOf(SchoolRepresentativeList.get(0).votes));

        return view;
    }

    public void getList(List<CandidateResult> list){
        SchoolRepresentativeList = list;
        mSchoolRepresentativeList = new ArrayList<>();
        for(int i=1;i<SchoolRepresentativeList.size();i++){
            mSchoolRepresentativeList.add(SchoolRepresentativeList.get(i));
        }
    }


}

package com.example.apix.unielection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by Apix on 28/08/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int num_of_fragments;
    List<Candidate> FederalPresidentList;
    List<Candidate> FederalVicePresidentList;
    List<Candidate> collegePresidentList;
    List<Candidate> collegeVicePresidentList;
    List<Candidate> SchoolRepresentativeList;
    List<Candidate> MemberOfParliamentList;

    public PagerAdapter(FragmentManager fm, int num_of_frags,List<Candidate> FederalPresidentList,List<Candidate> FederalVicePresidentList,
                        List<Candidate> collegePresidentList, List<Candidate> collegeVicePresidentList, List<Candidate> SchoolRepresentativeList,
                        List<Candidate> MemberOfParliamentList) {
        super(fm);
        num_of_fragments = num_of_frags;
        this.FederalPresidentList = FederalPresidentList;
        this.FederalVicePresidentList = FederalVicePresidentList;
        this.collegePresidentList = collegePresidentList;
        this.collegeVicePresidentList = collegeVicePresidentList;
        this.SchoolRepresentativeList = SchoolRepresentativeList;
        this.MemberOfParliamentList = MemberOfParliamentList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FederalPresidentFragment federalPresidentFragment = new FederalPresidentFragment();
                federalPresidentFragment.getList(FederalPresidentList);
                return federalPresidentFragment;
            case 1:
                FederalVicePresidentFragment federalVicePresidentFragment = new FederalVicePresidentFragment();
                federalVicePresidentFragment.getList(FederalVicePresidentList);
                return federalVicePresidentFragment;
            case 2:
                CollegePresidentFragment collegePresidentFragment = new CollegePresidentFragment();
                collegePresidentFragment.getList(collegePresidentList);
                return collegePresidentFragment;
            case 3:
                CollegeVicePresidentFragment collegeVicePresidentFragment = new CollegeVicePresidentFragment();
                collegeVicePresidentFragment.getList(collegeVicePresidentList);
                return collegeVicePresidentFragment;
            case 4:
                SchoolRepresentativeFragment schoolRepresentativeFragment = new SchoolRepresentativeFragment();
                schoolRepresentativeFragment.getList(SchoolRepresentativeList);
                return schoolRepresentativeFragment;
            case 5:
                MemberOfParliamentFragment memberOfParliamentFragment = new MemberOfParliamentFragment();
                memberOfParliamentFragment.getList(MemberOfParliamentList);
                return memberOfParliamentFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num_of_fragments;
    }
}

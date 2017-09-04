package com.example.apix.unielection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Apix on 30/08/2017.
 */

public class ResultsPagerAdapter extends FragmentPagerAdapter {

    int num_of_fragments;

    public ResultsPagerAdapter(FragmentManager fm,int num_of_frags) {
        super(fm);
        num_of_fragments = num_of_frags;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ResultFederalPresidentFragment federalPresidentFragment = new ResultFederalPresidentFragment();
//                federalPresidentFragment.getList(FederalPresidentList);
                return federalPresidentFragment;
            case 1:
                ResultFederalVicePresidentFragment federalVicePresidentFragment = new ResultFederalVicePresidentFragment();
//                federalVicePresidentFragment.getList(FederalVicePresidentList);
                return federalVicePresidentFragment;
            case 2:
                ResultCollegePresidentFragment collegePresidentFragment = new ResultCollegePresidentFragment();
//                collegePresidentFragment.getList(collegePresidentList);
                return collegePresidentFragment;
            case 3:
                ResultCollegeVicePresidentFragment collegeVicePresidentFragment = new ResultCollegeVicePresidentFragment();
//                collegeVicePresidentFragment.getList(collegeVicePresidentList);
                return collegeVicePresidentFragment;
            case 4:
                ResultSchoolRepresentativeFragment schoolRepresentativeFragment = new ResultSchoolRepresentativeFragment();
//                schoolRepresentativeFragment.getList(SchoolRepresentativeList);
                return schoolRepresentativeFragment;
            case 5:
                ResultMemberParliamentFragment memberOfParliamentFragment = new ResultMemberParliamentFragment();
//                memberOfParliamentFragment.getList(MemberOfParliamentList);
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

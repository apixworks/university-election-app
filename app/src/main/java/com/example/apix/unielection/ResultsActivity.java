package com.example.apix.unielection;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    ViewPager viewPager;
    ResultsPagerAdapter pagerAdapter;
    TextView loadingTxt;
    AVLoadingIndicatorView avi;

    private static String TAG = ResultsActivity.class.getSimpleName();

    private static String url = "http://192.168.43.125/uni-election/result_json.php";

    List<CandidateResult> FederalPresidentList;
    List<CandidateResult> FederalVicePresidentList;
    List<CandidateResult> CollegePresidentList;
    List<CandidateResult> CollegeVicePresidentList;
    List<CandidateResult> SchoolRepresentativeList;
    List<CandidateResult> MemberOfParliamentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        getSupportActionBar().setTitle("Federal President Results");
        loadingTxt = (TextView)findViewById(R.id.loadingTxt);
        avi = (AVLoadingIndicatorView)findViewById(R.id.avi);

        new ResultsTask().execute();

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                if(position==0){
                    getSupportActionBar().setTitle("Federal President Results");
                }else if(position==1){
                    getSupportActionBar().setTitle("Federal Vice President Results");
                }else if(position==2){
                    getSupportActionBar().setTitle("College President Results");
                }else if(position==3){
                    getSupportActionBar().setTitle("College Vice President Results");
                }else if(position==4){
                    getSupportActionBar().setTitle("School Representative Results");
                }else if(position==5){
                    getSupportActionBar().setTitle("Member Of Parliament Results");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.log_out){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ResultsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.show();
            loadingTxt.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(url);

////            candidatesList = new ArrayList<>();
            FederalPresidentList = new ArrayList<>();
            FederalVicePresidentList = new ArrayList<>();
            CollegePresidentList = new ArrayList<>();
            CollegeVicePresidentList = new ArrayList<>();
            SchoolRepresentativeList = new ArrayList<>();
            MemberOfParliamentsList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("candidate_details");

                Log.d(TAG, "doInBackground: "+jsonArray);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonCandidate = jsonArray.getJSONObject(i);

                    int id = Integer.parseInt(jsonCandidate.getString("cand_id"));
                    String name = jsonCandidate.getString("lname")+", "+jsonCandidate.getString("fname")+" "+jsonCandidate.getString("mname");
                    String program = jsonCandidate.getString("pro_name");
                    int yos = Integer.parseInt(jsonCandidate.getString("yos"));
                    int votes = Integer.parseInt(jsonCandidate.getString("votes"));
                    int position_id = Integer.parseInt(jsonCandidate.getString("p_id"));
                    String gender = jsonCandidate.getString("gender");
                    String img_url = jsonCandidate.getString("picha");

//                    candidatesList.add(new Candidate(id,name,program,yos,gender,img_url,position_id));

                    if(position_id==1){
                        FederalPresidentList.add(new CandidateResult(position_id,name,program,yos,votes,gender,img_url,id));
                    }else if(position_id==2){
                        FederalVicePresidentList.add(new CandidateResult(position_id,name,program,yos,votes,gender,img_url,id));
                    }else if(position_id==3){
                        CollegePresidentList.add(new CandidateResult(position_id,name,program,yos,votes,gender,img_url,id));
                    }else if(position_id==4){
                        CollegeVicePresidentList.add(new CandidateResult(position_id,name,program,yos,votes,gender,img_url,id));
                    }else if(position_id==5){
                        MemberOfParliamentsList.add(new CandidateResult(position_id,name,program,yos,votes,gender,img_url,id));
                    }else if(position_id==6){
                        SchoolRepresentativeList.add(new CandidateResult(position_id,name,program,yos,votes,gender,img_url,id));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            avi.hide();
            loadingTxt.setVisibility(View.INVISIBLE);
            pagerAdapter = new ResultsPagerAdapter(getSupportFragmentManager(),6,FederalPresidentList,FederalVicePresidentList,CollegePresidentList,
                    CollegeVicePresidentList,MemberOfParliamentsList,SchoolRepresentativeList);
            viewPager.setAdapter(pagerAdapter);
        }
    }
}

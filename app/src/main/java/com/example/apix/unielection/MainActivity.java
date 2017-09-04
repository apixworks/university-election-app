package com.example.apix.unielection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FederalPresidentFragment.MainInterface,FederalVicePresidentFragment.MainInterface,
        CollegePresidentFragment.MainInterface,CollegeVicePresidentFragment.MainInterface,SchoolRepresentativeFragment.MainInterface,
        MemberOfParliamentFragment.MainInterface{

    private static String TAG = MainActivity.class.getSimpleName();

    private static String url = "http://192.168.43.202/uni-election1/data_json.php";

    String vote_url = "http://192.168.43.202/uni-election1/app_votes.php";


    //    List<Candidate> candidatesList;
    List<Candidate> FederalPresidentList = new ArrayList<>();
    List<Candidate> FederalVicePresidentList = new ArrayList<>();
    List<Candidate> CollegePresidentList = new ArrayList<>();
    List<Candidate> CollegeVicePresidentList = new ArrayList<>();
    List<Candidate> SchoolRepresentativeList = new ArrayList<>();
    List<Candidate> MemberOfParliamentsList = new ArrayList<>();

    ViewPager viewPager;
    FloatingActionButton fab;
    PagerAdapter pagerAdapter;
    View.OnClickListener onClickListener;
    StepperIndicator stepperIndicator;
    Handler handler;

    int federal_president = -1,
        federal_vice_president = -1,
        college_president = -1,
        college_vice_president = -1,
        school_representative = -1,
        member_of_parliament = -1;

    String reg_no;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CandidatesTask().execute();

        reg_no = getIntent().getStringExtra("reg_no");
        user_id = getIntent().getStringExtra("user_id");

        getSupportActionBar().setTitle("Federal President");

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vote();
            }
        };

        handler = new Handler();

        viewPager = (ViewPager)findViewById(R.id.viewPger);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.hide();
        stepperIndicator = (StepperIndicator)findViewById(R.id.stepper);
//        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),6);
//        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(5);
//        stepperIndicator.setViewPager(viewPager, viewPager.getAdapter().getCount() - 1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                stepperIndicator.setCurrentStep(position);
                if(position==0){
                    getSupportActionBar().setTitle("Federal President");
//                    fab.setImageResource(R.drawable.ic_arrow_forward);
                }else if(position==1){
                    getSupportActionBar().setTitle("Federal Vice President");
//                    fab.setImageResource(R.drawable.ic_arrow_forward);
                }else if(position==2){
                    getSupportActionBar().setTitle("College President");
//                    fab.setImageResource(R.drawable.ic_arrow_forward);
                }else if(position==3){
                    getSupportActionBar().setTitle("College Vice President");
//                    fab.setImageResource(R.drawable.ic_arrow_forward);
                }else if(position==4){
                    getSupportActionBar().setTitle("School Representative");
//                    fab.setImageResource(R.drawable.ic_arrow_forward);
                    fab.animate().translationY(fab.getHeight() + 25).setInterpolator(new AccelerateInterpolator(2)).start();
                }else if(position==5){
                    getSupportActionBar().setTitle("Member Of Parliament");
//                    fab.setImageResource(R.drawable.ic_done);
                    fab.show();
                    fab.setTranslationY(fab.getHeight() + 25);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                        }
                    }, 300);
                    fab.setOnClickListener(onClickListener);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.results){
            Intent intent = new Intent(getApplicationContext(),ResultsActivity.class);
            startActivity(intent);
        }else if(id==R.id.log_out){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void fpChoice(int data) {
        Toast.makeText(getApplicationContext(),"Federal President "+data,Toast.LENGTH_SHORT).show();
        federal_president = data;
    }

    @Override
    public void fvpChoice(int data) {
        Toast.makeText(getApplicationContext(),"Federal Vice President "+data,Toast.LENGTH_SHORT).show();
        federal_vice_president = data;
    }

    @Override
    public void cpChoice(int data) {
        Toast.makeText(getApplicationContext(),"College President "+data,Toast.LENGTH_SHORT).show();
        college_president = data;
    }

    @Override
    public void cvpChoice(int data) {
        Toast.makeText(getApplicationContext(),"College Vice President "+data,Toast.LENGTH_SHORT).show();
        college_vice_president = data;
    }

    @Override
    public void srChoice(int data) {
        Toast.makeText(getApplicationContext(),"School Representative "+data,Toast.LENGTH_SHORT).show();
        school_representative = data;
    }

    @Override
    public void mopChoice(int data) {
        Toast.makeText(getApplicationContext(),"Member Of Parliament "+data,Toast.LENGTH_SHORT).show();
        member_of_parliament = data;
    }

    private void vote(){
        Log.d(TAG, "votes: \n"+"federal president: "+federal_president+"\n"+"federal vice president: "+federal_vice_president+"\n"
            +"college president: "+college_president+"\n"+"college vice president "+college_vice_president+"\n"
            +"school representative: "+school_representative+"\n"+"member of parliament "+member_of_parliament);

        new AlertDialog.Builder(this)
                .setTitle("Vote Confirmation")
                .setMessage("Are you sure you want to submit your vote?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        new VoteTask().execute(String.valueOf(federal_president),String.valueOf(federal_vice_president),String.valueOf(college_president),
                                String.valueOf(college_vice_president),String.valueOf(school_representative),String.valueOf(member_of_parliament),user_id,reg_no);
                        startActivity(new Intent(getApplicationContext(),ResultsActivity.class));
                        finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    private class CandidatesTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(url);

////            candidatesList = new ArrayList<>();
//            FederalPresidentList = new ArrayList<>();
//            FederalVicePresidentList = new ArrayList<>();
//            CollegePresidentList = new ArrayList<>();
//            CollegeVicePresidentList = new ArrayList<>();
//            SchoolRepresentativeList = new ArrayList<>();
//            MemberOfParliamentsList = new ArrayList<>();

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
                    int position_id = Integer.parseInt(jsonCandidate.getString("p_id"));
                    String gender = jsonCandidate.getString("gender");
                    String img_url = jsonCandidate.getString("picha");

//                    candidatesList.add(new Candidate(id,name,program,yos,gender,img_url,position_id));

                    if(position_id==1){
                        FederalPresidentList.add(new Candidate(id,name,program,yos,gender,img_url,position_id));
                    }else if(position_id==2){
                        FederalVicePresidentList.add(new Candidate(id,name,program,yos,gender,img_url,position_id));
                    }else if(position_id==3){
                        CollegePresidentList.add(new Candidate(id,name,program,yos,gender,img_url,position_id));
                    }else if(position_id==4){
                        CollegeVicePresidentList.add(new Candidate(id,name,program,yos,gender,img_url,position_id));
                    }else if(position_id==5){
                        MemberOfParliamentsList.add(new Candidate(id,name,program,yos,gender,img_url,position_id));
                    }else if(position_id==6){
                        SchoolRepresentativeList.add(new Candidate(id,name,program,yos,gender,img_url,position_id));
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
            pagerAdapter = new PagerAdapter(getSupportFragmentManager(),6,FederalPresidentList,FederalVicePresidentList,CollegePresidentList,
                    CollegeVicePresidentList,MemberOfParliamentsList,SchoolRepresentativeList);
            viewPager.setAdapter(pagerAdapter);
        }
    }

   private class VoteTask extends AsyncTask<String, Void,String>{

       @Override
       protected String doInBackground(String... params) {

           String federal_president = params[0];
           String federal_vice_president = params[1];
           String college_president = params[2];
           String college_vice_president = params[3];
           String school_representative = params[4];
           String member_of_parliament = params[5];
           String user_id = params[6];
           String reg_no = params[7];

           Log.d(TAG, "doInBackground: "+federal_president+" "+federal_vice_president+" "+college_president+" "+college_vice_president+" "+reg_no);

           try {
               URL url = new URL(vote_url);
               HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
               httpURLConnection.setRequestMethod("POST");
               httpURLConnection.setDoOutput(true);
               httpURLConnection.setDoInput(true);
               OutputStream outputStream = httpURLConnection.getOutputStream();
               BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
               String data = URLEncoder.encode("vote-btn","UTF-8")+"="+URLEncoder.encode("vote-btn","UTF-8")+"&"+
                       URLEncoder.encode("federal","UTF-8")+"="+URLEncoder.encode(federal_president,"UTF-8")+"&"+
                       URLEncoder.encode("vpfederal","UTF-8")+"="+ URLEncoder.encode(federal_vice_president,"UTF-8")+"&"+
                       URLEncoder.encode("collegepres","UTF-8")+"="+ URLEncoder.encode(college_president,"UTF-8")+"&"+
                       URLEncoder.encode("vpcollegepres","UTF-8")+"="+ URLEncoder.encode(college_vice_president,"UTF-8")+"&"+
                       URLEncoder.encode("schoolrep","UTF-8")+"="+ URLEncoder.encode(school_representative,"UTF-8")+"&"+
                       URLEncoder.encode("memberofperli","UTF-8")+"="+ URLEncoder.encode(member_of_parliament,"UTF-8")+"&"+
                       URLEncoder.encode("user_id","UTF-8")+"="+ URLEncoder.encode(user_id,"UTF-8")+"&"+
                       URLEncoder.encode("reg_no","UTF-8")+"="+ URLEncoder.encode(reg_no,"UTF-8");
               bufferedWriter.write(data);
               bufferedWriter.flush();
               bufferedWriter.close();
               outputStream.close();
               InputStream inputStream = httpURLConnection.getInputStream();
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
               String response = "";
               String line;
               while ((line = bufferedReader.readLine())!=null)
               {
                   response+= line;
               }
               bufferedReader.close();
               inputStream.close();
               httpURLConnection.disconnect();
               Log.d(TAG, "doInBackground: "+response);
//               return response;
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
           return null;
       }
   }
}

package com.example.apix.unielection;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ResultsActivity extends AppCompatActivity {

    ViewPager viewPager;
    ResultsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(5);
        pagerAdapter = new ResultsPagerAdapter(getSupportFragmentManager(),6);
        viewPager.setAdapter(pagerAdapter);
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
        getMenuInflater().inflate(R.menu.menu1,menu);
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
}

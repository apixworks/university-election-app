package com.example.apix.unielection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MaiActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private static String url = "http://192.168.56.1/demo/Callfederalpres.php";

    ArrayList<HashMap<String, String>> candidatesList;

   // JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        candidatesList = new ArrayList<>();

        new ValueTask().execute();

    }

    private class ValueTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            Log.d(TAG, "doInBackground: "+"Imefika");

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.d(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray candidates = jsonObj.getJSONArray("candidates");

                    // looping through All Contacts
                    for (int i = 0; i < candidates.length(); i++) {

                        JSONObject c = candidates.getJSONObject(i);

                        Log.d(TAG, "wangapi: "+i+" "+c);

                        String P_RegNom = c.getString("P_RegNom");
                        String Fname = c.getString("Fname");
                        String Mname = c.getString("Mname");
                        String Lname = c.getString("Lname");
                        String Yos = c.getString("Yos");
                        String Gender = c.getString("Gender");
                        String image = c.getString("image");
                        String College_IDFK = c.getString("College_IDFK");
                        String Position_IDFK = c.getString("Position_IDFK");
                        String School_IDFK = c.getString("School_IDFK");
                        String Programme_IDFk = c.getString("Programme_IDFk");

                        // tmp hash map for single contact
                        HashMap<String, String> candidate = new HashMap<>();

                        // adding each child node to HashMap key => value
                        candidate.put("P_RegNom", P_RegNom);
                        candidate.put("Fname", Fname);
                        candidate.put("Mname", Mname);
                        candidate.put("Lname", Lname);
                        candidate.put("Yos", Yos);
                        candidate.put("Gender", Gender);
                        candidate.put("image", image);
                        candidate.put("College_IDFK", College_IDFK);
                        candidate.put("Position_IDFK", Position_IDFK);
                        candidate.put("School_IDFK", School_IDFK);
                        candidate.put("Programme_IDFk", Programme_IDFk);

                        // adding candidate to candidates list
                        candidatesList.add(candidate);
                    }

                    //hapa kuhusu adapter
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }
    }
}

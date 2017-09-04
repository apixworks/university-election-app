package com.example.apix.unielection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    String login_url = "http://192.168.43.202/uni-election1/login.php";

    Button loginBtn;
    EditText regEditText;
    EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        regEditText = (EditText)findViewById(R.id.regEditText);
        passEditText = (EditText)findViewById(R.id.passEditText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regNum = regEditText.getText().toString();
                String password = passEditText.getText().toString();

                try{

                    if(regNum.isEmpty()||password.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Please fill the fields",Toast.LENGTH_SHORT).show();
                    }else{
                        String[] temp = regNum.split("/");
                        regNum = temp[0].toLowerCase()+"/"+temp[1].toLowerCase()+"/"+temp[2]+"/"+temp[3];
                        if(regNum.startsWith("t/udom/")&&regNum.length()==17){
                            LoginTask loginTask = new LoginTask(getApplicationContext());
                            loginTask.execute("login", regNum, password);
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Wrong Reg# format",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Wrong Reg# format",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public class LoginTask extends AsyncTask<String, Void,String> {
        Context ctx;
        AlertDialog alertDialog;

        public LoginTask(Context ctx) {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Login Information....");
        }

        @Override
        protected String doInBackground(String... params) {

            String method = params[0];

            ///
            if(method.equals("login"))
            {
                String login_name = params[1];
                String login_pass = params[2];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                            URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
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
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            //maneno compare login
            if (result.contains("Success")) {
                Log.d("hii", "onPostExecute: "+result);
                String[] userDetails = result.split("-");
                if(userDetails[2].equals("No")){
                    Intent intent = new Intent(ctx, MainActivity.class);
                    intent.putExtra("reg_no",userDetails[1]);
                    intent.putExtra("user_id",userDetails[3]);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ctx, ResultsActivity.class);
                    startActivity(intent);
                }
                finish();
            } else if(result.contains("Failed")) {
                Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            }
        }
    }

}

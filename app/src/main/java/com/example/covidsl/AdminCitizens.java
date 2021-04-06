package com.example.covidsl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AdminCitizens extends AppCompatActivity {

    private EditText nid;
    private EditText fname;
    private EditText age;
    private EditText address;
    private EditText profession;
    private EditText email;
    private EditText affi;
    private EditText pass;
    private EditText health;



    private EditText etId;
    private EditText etIdUpdate,etPriceUpdate;
    private EditText etIdDelete;
    private TextView tnid,tfname,tage,taddress,tprofession,temail,taffi,thealth;


    public static final String URL_ADD_CITIZENS = "http://192.168.136.1/covid/addCitizens.php";
    public static final String URL_SHOW_CITIZENS = "http://192.168.136.1/covid/viewCitizens.php";
    public static final String URL_UPDT_CITIZENS = "http://192.168.136.1/covid/updateCitizens.php";
    public static final String URL_DELETE_CITIZENS = "http://192.168.136.1/covid/deleteCitizen.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_citizens);

        nid = findViewById(R.id.nid);
        fname = findViewById(R.id.name);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        profession = findViewById(R.id.profession);
        email = findViewById(R.id.email);
        affi = findViewById(R.id.affiliation);
        pass = findViewById(R.id.password);
        health = findViewById(R.id.healthStatus);



        etId = findViewById(R.id.id);
        etIdUpdate = findViewById(R.id.id_update);
        etPriceUpdate = findViewById(R.id.price_update);
        etIdDelete = findViewById(R.id.id_delete);

        tnid = findViewById(R.id.shownid);
        tfname = findViewById(R.id.showname);
        tage = findViewById(R.id.showage);
        taddress = findViewById(R.id.showaddress);
        tprofession = findViewById(R.id.showprofession);
        temail = findViewById(R.id.showemail);
        taffi = findViewById(R.id.showaffi);
        thealth = findViewById(R.id.showhealth);

    }

    public void add_prod(View view){
        final String unid = nid.getText().toString();
        final String uname = fname.getText().toString();
        final String uage = age.getText().toString();
        final String uaddress = address.getText().toString();
        final String uproff = profession.getText().toString();
        final String uemail = email.getText().toString();
        final String uaffi = affi.getText().toString();
        final String upass = pass.getText().toString();
        final String uhealth = health.getText().toString();




        class Citizen extends AsyncTask<Void, Void, String> {

            ProgressDialog pdLoading = new ProgressDialog(AdminCitizens.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

               //UI thraed
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();


                HashMap<String, String> params = new HashMap<>();
                params.put("nid", unid);
                params.put("fname", uname);
                params.put("age", uage);
                params.put("caddress", uaddress);
                params.put("profession", uproff);
                params.put("email", uemail);
                params.put("affiliation", uaffi);
                params.put("password", upass);
                params.put("healthStatus", uhealth);


                return requestHandler.sendPostRequest(URL_ADD_CITIZENS, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();

                try {

                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AdminCitizens.this, "Exception: "+e, Toast.LENGTH_LONG).show();
                }
            }

        }

        Citizen citizen = new Citizen();
        citizen.execute();
    }




    //Show Citizens
    public void show_prod(View view){
        final String id = etId.getText().toString();

        class showCitizens extends AsyncTask<Void, Void, String>{

            ProgressDialog pdLoading = new ProgressDialog(AdminCitizens.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();


                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);


                return requestHandler.sendPostRequest(URL_SHOW_CITIZENS, params);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                pdLoading.dismiss();

                try{

                    JSONObject obj = new JSONObject(s);


                    if (!obj.getBoolean("error")){
                        Toast.makeText(AdminCitizens.this, obj.getString("message"), Toast.LENGTH_LONG).show();

                        tnid.setVisibility(View.VISIBLE);
                        tfname.setVisibility(View.VISIBLE);
                        tage.setVisibility(View.VISIBLE);
                        taddress.setVisibility(View.VISIBLE);
                        tprofession.setVisibility(View.VISIBLE);
                        temail.setVisibility(View.VISIBLE);
                        taffi.setVisibility(View.VISIBLE);
                        thealth.setVisibility(View.VISIBLE);

                        //Set retrieved text to TextViews
                        tnid.setText("NID: "+obj.getString("nid"));
                        tfname.setText("Name: "+obj.getString("fname"));
                        tage.setText("Age: "+obj.getString("age"));
                        taddress.setText("Address: "+obj.getString("caddress"));
                        tprofession.setText("Profession: "+obj.getString("profession"));
                        temail.setText("Email: "+obj.getString("email"));
                        taffi.setText("Affiliation: "+obj.getString("affiliation"));
                        thealth.setText("Helth Status: "+obj.getString("healthStatus"));
                    }
                } catch (Exception e ){
                    Toast.makeText(AdminCitizens.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        }

        showCitizens show = new showCitizens();
        show.execute();
    }



    //Update Citizen
    public void update_price(View view){
        final String id = etIdUpdate.getText().toString();
        final String health = etPriceUpdate.getText().toString();

        class Update extends AsyncTask<Void, Void, String>{
            ProgressDialog pdLoading = new ProgressDialog(AdminCitizens.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();


                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("healthStatus",health);


                return requestHandler.sendPostRequest(URL_UPDT_CITIZENS, params);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                pdLoading.dismiss();

                try{

                    JSONObject obj = new JSONObject(s);


                    if (!obj.getBoolean("error")){
                        Toast.makeText(AdminCitizens.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e ){
                    Toast.makeText(AdminCitizens.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        Update update = new Update();
        update.execute();
    }




    public void delete_product(View view){
        final String id = etIdDelete.getText().toString();

        class Delete extends AsyncTask<Void, Void, String>{
            ProgressDialog pdLoading = new ProgressDialog(AdminCitizens.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                return requestHandler.sendPostRequest(URL_DELETE_CITIZENS, params);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                pdLoading.dismiss();

                try{

                    JSONObject obj = new JSONObject(s);


                    if (!obj.getBoolean("error")){
                        Toast.makeText(AdminCitizens.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e ){
                    Toast.makeText(AdminCitizens.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        Delete delete = new Delete();
        delete.execute();
    }
}
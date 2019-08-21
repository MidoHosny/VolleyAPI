package com.hosnydev.recyclerviewjson;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // view
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    // class
    private Adapter adapter;
    private ArrayList<Model> modelArrayList;

    // volley
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressHome);

        // recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modelArrayList = new ArrayList<>();

        // volley
        requestQueue = Volley.newRequestQueue(this);

        // check internet connection
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo != null) {
            parsingJson();
        } else {
            AlertDialog.Builder al = new AlertDialog.Builder(this);
            al.setMessage("no internet connection please try again")
                    .setNegativeButton("OK", null);
            al.create();
            al.show();
        }

    }

    private void parsingJson() {

        // NOTE this api is a free api from pixabay.com to test this code
        // date 17-8-2019

        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            // make progress visible
                            progressBar.setVisibility(View.VISIBLE);

                            // this api data inside a array
                            // so need a json array to handel this
                            JSONArray jsonArray = response.getJSONArray("hits");

                            // start get data from arrayJSON and giv to Model class
                            for (int i = 0; i < jsonArray.length(); i++) {

                                // get json object by index from json array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                // get string filed in api
                                String image = jsonObject.getString("webformatURL");
                                String name = jsonObject.getString("user");
                                int like = jsonObject.getInt("likes");

                                // give variable to model class
                                modelArrayList.add(new Model(image, name, like));

                            }

                            // give list to adapter class
                            adapter = new Adapter(MainActivity.this, modelArrayList);

                            // set adapter to recycler
                            recyclerView.setAdapter(adapter);

                            // gone progress
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            // gone progress
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Error when load json array hits ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // gone progress
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Error when load api :-" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        // run request
        requestQueue.add(request);
    }

}

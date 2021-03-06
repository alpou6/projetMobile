package com.example.arnaud.firstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

public class MainActivity2 extends AppCompatActivity /*implements View.OnClickListener*/ {

    public static String BEERS_UPDATE = "com.octip.cours.inf3044_11.BEERS_UPDATE";
    private RecyclerView recyclerView;
    public static String heroIndex = "";

    public class BeerUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            JSONArray bieres = getBeersFromFile();
            recyclerView.setAdapter(new BeersAdapter(bieres, context));
            ((BeersAdapter)recyclerView.getAdapter()).setNewBeer(bieres);
            Toast.makeText(getApplicationContext(), getString(R.string.msg), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        heroIndex = this.getIntent().getStringExtra("key");
        IntentFilter intentFilter = new IntentFilter(BEERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BeerUpdate(), intentFilter);
        setContentView(R.layout.activity_main2);
        GetBeersServices.startActionBeer(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_biere);
        recyclerView.setAdapter(new BeersAdapter(getBeersFromFile(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        Button b0 = (Button) findViewById(R.id.but0);
//        Button b1 = (Button) findViewById(R.id.but1);
    }

    public JSONArray getBeersFromFile() {
        try {
            InputStream is = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            System.out.println(getCacheDir().toString());
            byte[] buff = new byte[is.available()];
            is.read(buff);
            is.close();

            JSONObject OWH = new JSONObject(new String(buff, "UTF-8"));

            return OWH.getJSONArray("data");
        }
        catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

/*    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but0:
                heroIndex = 0;
                break;
            case R.id.but1:
                heroIndex = 1;
                break;
            default:
                break;
        }
    }*/
}
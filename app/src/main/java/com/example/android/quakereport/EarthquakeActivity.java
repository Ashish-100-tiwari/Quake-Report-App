package com.example.android.quakereport;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {
    private EarthquakeAdapter mAdapter;
    private static final String USGS_REQUEST_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

//    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this,new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);
        EarthquakeAsyncTask task =new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Earthquake currentEarthquake = mAdapter.getItem(position);
                //convert the string URL into  a URI object(to pass into the intent constructor)
                Uri earthquakeuri = Uri.parse(currentEarthquake.getUrl());
                //create a new intent to vew the earthquake uri
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW,earthquakeuri);
                //send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }
    private class  Earthquake extends AsyncTask<String, Void , List<Earthquake>>{
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if(urls.length<1||urls[0]==null){
                return null;
            }
            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }
        @Override
        protected void onPostExecute(List<Earthquake>  data){
            mAdapter.clear();
            if(data!=null&&!data.isEmpty()){
                mAdapter.addAll(data);
            }
        }
    }
}
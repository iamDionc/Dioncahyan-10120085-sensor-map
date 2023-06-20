package com.example.dioncahyan10120085;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FetchData extends AsyncTask<Object, String, String> {

    private static final String TAG ="";

    String googleNearPlaceData, url;

    GoogleMap googleMap;

    @Override
    protected String doInBackground(Object... objects) {
        try {
            googleMap = (GoogleMap) objects[0];
            url = (String) objects[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googleNearPlaceData = downloadUrl.retriveUrl(url);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return googleNearPlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject isidata = (JSONObject) jsonArray.get(i);
                JSONObject position = isidata.getJSONObject("position");
                JSONObject poi = isidata.getJSONObject("poi");

                String name = poi.getString("name");
                String lat = position.getString("lat");
                String lng = position.getString("lon");


                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                Log.d(TAG, "onPostExecute: " +"lat : " +lat +" long : " +name);

            }
//            Log.d(TAG, "onPostExecute: " + jsonObject);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

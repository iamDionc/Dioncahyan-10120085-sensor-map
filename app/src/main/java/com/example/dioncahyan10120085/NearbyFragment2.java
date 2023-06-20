package com.example.dioncahyan10120085;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class NearbyFragment2 extends Fragment {

    private static final String TAG= "";

    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private static final int Request_code = 101;

    private double lat, lng;

    private Button rest;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            getCurrentLocation();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nearby2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        rest = view.findViewById(R.id.rest);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        rest.setOnClickListener(new View.OnClickListener() {

            private static final String TAG="";

            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder("https://api.tomtom.com/search/2/nearbySearch/.json?key=6qcm2DvpNTAOPo7Yd01gMPjVAgslAWCm&" +
                        "lat="+lat+"&" +
                        "lon="+lng+"&" +
                        "categorySet=7315&" +
                        "limit=5");
                String url = stringBuilder.toString();
                Object[] dataFetch = new Object[2];
                dataFetch[0] = mMap;
                dataFetch[1] = url;

                FetchData fetchData = new FetchData();
                fetchData.execute(dataFetch);
                Log.d(TAG,"onClick:"+lat);
                Toast.makeText(getContext(), lat + " " + lng, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_code);
            return;
    }
        LocationRequest locationRequest =  LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null){
                    Toast.makeText(getContext(),"Current location is null", Toast.LENGTH_LONG).show();
                    String TAG = "";
                    Log.d(TAG, "onLocationResult: Current location is null");
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
//                        Toast.makeText(getContext(), "Current location is " +location.getLongitude() + " " + location.getLatitude(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "fajar: " + location.getLongitude());
                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    LatLng latLng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                    editText.setText(String.valueOf(lat));
                    Toast.makeText(getContext(), lat + " " + lng, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (Request_code){
            case Request_code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }
    }
}
}
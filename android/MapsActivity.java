package com.example.c0smy.gmaps;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    public String json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(!isConnectedToInternet())
       {
           final AlertDialog.Builder builder = new AlertDialog.Builder(this);
           builder.setTitle("You are not connected to internet. Please chose an option");
           builder.setItems(new CharSequence[]
                           {"Wifi", "Mobile Network", "Exit aplication"},
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           switch (which) {
                               case 0:
                                   Intent intent=new Intent(Settings.ACTION_WIFI_SETTINGS);
                                   startActivity(intent );
                                   break;
                               case 1:
                                   Intent intent1=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                                   startActivity(intent1 );
                                   break;
                               case 2:
                                   System.exit(0);
                                   break;
                           }
                       }
                   });
           builder.create().show();

       }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng buc = new LatLng(44.502042, 26.092506);
        LatLng cluj = new LatLng(46.784306, 23.688546);
        LatLng tms = new LatLng(45.810993, 21.322356);
        mMap.addMarker(new MarkerOptions().position(buc).title("BUCHAREST"));
        mMap.addMarker(new MarkerOptions().position(cluj).title("CLUJ"));
        mMap.addMarker(new MarkerOptions().position(tms).title("TIMISOARA"));

        googleMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String city = marker.getTitle();
                       // marker.showInfoWindow();

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(city.equals("BUCHAREST")){
                            marker.showInfoWindow();
                            String json = null;
                            try {
                                json = new startJson().execute("BUCHAREST").get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(MapsActivity.this,GraficMaker.class);
                            intent.putExtra("json_data", json);
                            Log.d("sssssssssssssssssssss", "value: " + json);
                            startActivity(intent);
                            return true;
                        }else if(city.equals("CLUJ")) {
                            marker.showInfoWindow();
                            String json = null;
                            try {
                                json = new startJson().execute("CLUJ").get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(MapsActivity.this,GraficMaker.class);
                            intent.putExtra("json_data", json);
                            Log.d("sssssssssssssssssssss", "value: " + json);
                            startActivity(intent);
                            return true;
                        }else if(city.equals("TIMISOARA"))
                            marker.showInfoWindow();
                        String json = null;
                        try {
                            json = new startJson().execute("TIMISOARA").get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MapsActivity.this,GraficMaker.class);
                        intent.putExtra("json_data", json);
                        Log.d("sssssssssssssssssssss", "value: " + json);
                        startActivity(intent);
                            return true;
                    }
                }
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(buc));
        float zoomLevel = 10.7f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(buc, zoomLevel));
    }

    class startJson extends AsyncTask<String,String,String>{
        String json_url;
        String json_string;

        @Override
        protected String doInBackground(String... params) {
            json_url = "https://avioanenumber.000webhostapp.com/json_get_data_"+params[0].toString()+".php";
            Log.d("uuuuuu", "value: " + json_url);
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while((json_string = bufferedReader.readLine()) !=null){
                    stringBuilder.append(json_string+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("aaaaaaaaaaaa", "value: " + stringBuilder.toString().trim());
                publishProgress(stringBuilder.toString().trim());
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json=result;
            Log.d("jjjjjjjj", "value: " + json);
        }
    }


    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

}

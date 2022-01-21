package com.example.gpslocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button getLocation;
    TextView text1, text2, text3, text4, text5;
    FusedLocationProviderClient fusedLocationProviderClientClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocation = (Button) findViewById(R.id.getLocation);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);

        fusedLocationProviderClientClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation1();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }


        });

    }
   private void getLocation1() {
        fusedLocationProviderClientClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresss = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        text1.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Latitude :</b></br> </font>"
                                        + addresss.get(0).getLatitude()
                        ));
                        text2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longitude :</b></br> </font>"
                                        + addresss.get(0).getLongitude()
                        ));
                        text3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country Name :</b></br> </font>"
                                        + addresss.get(0).getCountryName()
                        ));
                        text4.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Locality :</b></br> </font>"
                                        + addresss.get(0).getLocality()
                        ));
                        text5.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address :</b></br> </font>"
                                        + addresss.get(0).getAddressLine(0)
                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
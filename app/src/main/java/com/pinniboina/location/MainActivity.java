package com.pinniboina.location;

import android.Manifest;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Initialise variables
    Button BtnLoc;
    TextView Text, Text1, Text2, Text3, Text4, Text5, Text6, Text7;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign views
        BtnLoc = findViewById(R.id.btn_loc);
        Text = findViewById(R.id.text);
        Text1 = findViewById(R.id.text1);
        Text2 = findViewById(R.id.text2);
        Text3 = findViewById(R.id.text3);
        Text4 = findViewById(R.id.text4);
        Text5 = findViewById(R.id.text5);
        Text6 = findViewById(R.id.text6);
        Text7 = findViewById(R.id.text7);

        //Initialise fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        BtnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check permission
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //On Permission Granted
                    getLocation();
                } else {
                    //On Permission Denied
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location = task.getResult();
                if (location != null) {
                    try {
                        //Initialize GeoCoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        //Initialize address list
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Text.setText(Html.fromHtml("<font color='#6200EE'><br> Latitude: </br><br></font>" + addresses.get(0).getLatitude()));
                        Text1.setText(Html.fromHtml("<font color='#6200EE'><br> Longitude: </br><br></font>" + addresses.get(0).getLongitude()));
                        Text2.setText(Html.fromHtml("<font color='#6200EE'><br> Country Name: </br><br></font>" + addresses.get(0).getCountryName()));
                        Text3.setText(Html.fromHtml("<font color='#6200EE'><br> Locality: </br><br></font>" + addresses.get(0).getLocality()));
                        Text4.setText(Html.fromHtml("<font color='#6200EE'><br> Address: </br><br></font>" + addresses.get(0).getAddressLine(0)));
                        Text5.setText(Html.fromHtml("<font color='#6200EE'><br> Feature Name: </br><br></font>" + addresses.get(0).getFeatureName()));
                        Text6.setText(Html.fromHtml("<font color='#6200EE'><br> Country Code: </br><br></font>" + addresses.get(0).getCountryCode()));
                        Text7.setText(Html.fromHtml("<font color='#6200EE'><br> Postal code: </br><br></font>" + addresses.get(0).getPostalCode()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please check your location settings", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.example.task_71p;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.task_71p.database.Database;
import com.example.task_71p.model.Items;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class CreateAdvert extends AppCompatActivity {

    TextView textboxName, textboxPhone, textboxDescription, textboxDate, textboxLocation;
    RadioButton RDBLost, RDBFound;
    Button btnSaveAdvert, currentLocationButton;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_advert);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                textboxLocation.setText(location.getLatitude()+","+location.getLongitude());
            }
        };
        String API_KEY = "AIzaSyCvITycwEz6ZxjLibMkxKpobtoOD0kkcMk";
        Places.initialize(getApplicationContext(), API_KEY);
        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_googlemap);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.LAT_LNG));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {

                Toast.makeText(CreateAdvert.this, "Error: "+status, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latlng = place.getLatLng();
                textboxLocation.setText(latlng.latitude+","+ latlng.longitude);
            }
        });

        Database db = new Database(this);

        textboxName = findViewById(R.id.editTextName);
        textboxPhone = findViewById(R.id.editTextPhone);
        textboxDescription = findViewById(R.id.editTextDes);
        textboxDate = findViewById(R.id.editTextDate);
        textboxLocation = findViewById(R.id.editTextLocation);
        RDBLost = findViewById(R.id.lostButton);
        RDBFound = findViewById(R.id.foundButton);
        btnSaveAdvert = findViewById(R.id.BtnSaveAdvert);
        currentLocationButton = findViewById(R.id.currentLocationButton);

        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(CreateAdvert.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateAdvert.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateAdvert.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else{
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        });


        Intent ReturnMain = new Intent(this, com.example.task_71p.MainActivity.class);

        btnSaveAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Type = "";
                String Name = textboxName.getText().toString();
                int Phone = 0;
                String Description = textboxDescription.getText().toString();
                String Date = textboxDate.getText().toString();
                String Location = textboxLocation.getText().toString();
                boolean input = true;
                if (RDBLost.isChecked() == true){
                    Type = "Lost";
                }else if (RDBFound.isChecked() == true){
                    Type = "Found";
                }else{
                    input = false;
                }
                if (!textboxPhone.getText().toString().equals("")){
                    Phone = Integer.parseInt(textboxPhone.getText().toString());
                }else{
                    input = false;
                }
                if (textboxName.getText().toString().equals("")||textboxDescription.getText().toString().equals("")
                        ||textboxDate.getText().toString().equals("")||textboxLocation.getText().toString().equals("")){
                    input = false;
                }
                Items tempItems;
                if (input == true){
                    tempItems = new Items(Type,Name,Phone,Description,Date,Location);
                    db.InsertAdvert(tempItems);
                    startActivity(ReturnMain);
                }else{
                    Toast.makeText(getApplicationContext(), "Please filled in every boxes and button!.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
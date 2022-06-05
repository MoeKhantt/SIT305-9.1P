package com.example.task_71p;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.task_71p.database.Database;
import com.example.task_71p.databinding.ShowOnMapBinding;
import com.example.task_71p.model.Items;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class ShowOnMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ShowOnMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ShowOnMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Database db = new Database(this);
        ArrayList<Items> dbAdverts = db.fetchAllAdverts();
        for (int i =0;i <dbAdverts.size();i++){
            String[] loc = dbAdverts.get(i).getLocation().split(",");
            try {
                LatLng temp = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                mMap.addMarker(new MarkerOptions().position(temp).title(dbAdverts.get(i).getLostOrFound()+" "+dbAdverts.get(i).getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));
            }catch(Exception e){ }
        }
    }
}
package com.example.telaprincipal;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.telaprincipal.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int REQUEST_LOCATION_UPDATES = 2;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Marker userMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // inicia processo de localização
        iniciaColetaLocalizacao();
    }

    private void iniciaColetaLocalizacao() {
        // Se a app já possui a permissão, ativa a calamada de localização
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // A permissão foi dada
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5 * 1000);
            mLocationRequest.setFastestInterval(1 * 1000);
            // Programa o evento a ser chamado em intervalo regulares de tempo
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Location location = locationResult.getLastLocation();
                    atualizaPosicaoNoMapa(location);
                }
            };
            //
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_UPDATES);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFusedLocationProviderClient != null)
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    public void atualizaPosicaoNoMapa(Location location) {
        if (mMap != null) {// adiciona/atualiza o marcador no mapa
            LatLng userPosition = new LatLng(location.getLatitude(), location.getLongitude());
            if (userMarker == null) {
                userMarker = mMap.addMarker(new MarkerOptions().position(userPosition).title("Minha Posição"));
                // centraliza camera na posição atual e define zoom para 15
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 15f));
            } else {
                userMarker.setPosition(userPosition);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userPosition));
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

}

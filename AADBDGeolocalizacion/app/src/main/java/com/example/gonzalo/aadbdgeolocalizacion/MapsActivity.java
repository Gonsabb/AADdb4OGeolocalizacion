package com.example.gonzalo.aadbdgeolocalizacion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gonzalo.aadbdgeolocalizacion.basedb4o.Db4O;
import com.example.gonzalo.aadbdgeolocalizacion.service.Servicio;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final int CTEPLAY = 1;
    private GoogleMap mMap;
    private LocationRequest peticionLocalizaciones;
    private GoogleApiClient cliente;
    private Db4O bd;
    private Location ultimaLocalizacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapalayout);
        initMap();
        Intent intent = new Intent(this, Servicio.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.lunes) {
            setRuta(2);
            return true;
        }
        if (id == R.id.martes) {
            setRuta(3);
            return true;
        }
        if (id == R.id.miercoles) {
            setRuta(4);
            return true;
        }
        if (id == R.id.jueves) {
            setRuta(5);
            return true;
        }
        if (id == R.id.viernes) {
            setRuta(6);
            return true;
        }
        if (id == R.id.sabado) {
            setRuta(7);
            return true;
        }
        if (id == R.id.domingo) {
            setRuta(1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRuta(int dia) {
        bd = new Db4O(this);
        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions();
        ArrayList<Posicion> pos = bd.getRuta(dia);
        Log.v("DIAAAAA", pos.toString());
        for (Posicion p : pos) {
            rectOptions.add(new LatLng(p.getLatitud(), p.getLongitud()));
        }

        // Get back the mutable Polyline
        mMap.clear();
        mMap.addPolyline(rectOptions);
        bd.close();
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
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera
//        LatLng vergeles = new LatLng(37.161, -3.591);
//        mMap.addMarker(new MarkerOptions().position(vergeles).title("Marcador en Vergeles"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(vergeles));
    }

    @Override
    public void onConnected(Bundle bundle) {
        peticionLocalizaciones = new LocationRequest();
        peticionLocalizaciones.setSmallestDisplacement(1);
        peticionLocalizaciones.setFastestInterval(5000);
        peticionLocalizaciones.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(cliente, peticionLocalizaciones, this);
        ultimaLocalizacion = LocationServices.
                FusedLocationApi.getLastLocation(cliente);
        if (ultimaLocalizacion != null) {

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        double la = location.getLatitude();
        double lo = location.getLongitude();
        //tv.append("Latitud: " + la + " " + "Longitude: " + lo + "\n");
        LatLng sydney = new LatLng(la, lo);
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (status == ConnectionResult.SUCCESS) {
            cliente = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            cliente.connect();
        } else {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                GooglePlayServicesUtil.getErrorDialog(status, this, CTEPLAY).show();
            } else {
                Toast.makeText(this, "No", Toast.LENGTH_LONG).show();
            }
        }
        peticionLocalizaciones = new LocationRequest();
        peticionLocalizaciones.setInterval(10000);
        peticionLocalizaciones.setFastestInterval(5000);
        peticionLocalizaciones.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}

package com.example.soke.faeca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReunionGPS extends FragmentActivity implements LocationListener {

    LocationManager locationManager;
    CameraPosition cameraPosition = null;
    CameraUpdate camera = null;
    boolean existeLocalizacion = false;
    String latitud = null, longitud = null;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reunion_gps);
        setTitle("Mapa");

        int sdk_version = Build.VERSION.SDK_INT;
        String contenedor = String.valueOf(sdk_version);

        if (getIntent().getStringExtra("latitud") != null) {
            latitud = getIntent().getStringExtra("latitud");
            longitud = getIntent().getStringExtra("longitud");
            existeLocalizacion = true;
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

        if (contenedor.equals("21")) {
            mapIfLollipop();

        } else {
            setUpMapIfNeeded();
        }


    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Parece que tu GPS esta desactivado. Activarlo mejora la precision de la localizacion.\n¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ReunionGPS.this);
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ReunionGPS.this);
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                setUpMap();
            }
        }
    }


    private void mapIfLollipop() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?q=loc:" + latitud + "," + longitud + ",19z"));
        startActivity(intent);
    }

    private void setUpMap() {

        if (!existeLocalizacion) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.164968, -3.607663))
                    .title("Cooperativas Agro-Alimentarias")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.reunion)));
        } else {
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(latitud), Double.valueOf(longitud))));
            cameraPosition = new CameraPosition(new LatLng(Double.valueOf(latitud), Double.valueOf(longitud)), 15, 0, 0);
            camera = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(camera);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        cameraPosition = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0);
        camera = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(camera);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

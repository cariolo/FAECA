package com.example.soke.faeca;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReunionGPS extends FragmentActivity implements LocationListener {

    CameraPosition cameraPosition = null;
    CameraUpdate camera = null;
    String latitud = null, longitud = null;
    boolean existeLocalizacion = false;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reunion_gps);
        setTitle("Mapa");

        if (getIntent().getStringExtra("latitud") != null) {
            latitud = getIntent().getStringExtra("latitud");
            longitud = getIntent().getStringExtra("longitud");
            existeLocalizacion = true;
        }

        String contenedor = null;
        int sdk_version = Build.VERSION.SDK_INT;
        contenedor = String.valueOf(sdk_version);

        if (contenedor.equals("21")) {
            mapIfLollipop();

        } else {
            setUpMapIfNeeded();
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String latitud = String.valueOf(latLng.latitude), longitud = String.valueOf(latLng.longitude);
                Toast.makeText(getApplicationContext(), latitud + " " + longitud, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
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

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    private void mapIfLollipop() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/place/37%C2%B009'53.9%22N+3%C2%B036'27.6%22W/@37.164972,-3.60766,19z/data=!3m1!4b1!4m2!3m1!1s0x0:0x0"));
        startActivity(intent);
    }

    private void setUpMap() {

        if (existeLocalizacion == false) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.164968, -3.607663))
                    .title("Cooperativas Agro-Alimentarias")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.reunion)));

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            ////////////////////////////////////////////////////ME QUEDO AÑADIENDO LOS LSITENERS PARA LOS MARCADORES
        } else {
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(latitud), Double.valueOf(longitud))));
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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

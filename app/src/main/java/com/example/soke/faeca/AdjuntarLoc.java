package com.example.soke.faeca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AdjuntarLoc extends FragmentActivity implements LocationListener{

    LocationManager locationManager;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public String loc=null;
    public Marker m=null;

    CameraPosition cameraPosition = null;
    CameraUpdate camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjuntar_loc);

        int sdk_version = Build.VERSION.SDK_INT;
        String contenedor = String.valueOf(sdk_version);

        setUpMapIfNeeded();


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if (m==null) {
                    m = mMap.addMarker(new MarkerOptions().position(latLng));
                    String latitud = String.valueOf(latLng.latitude), longitud = String.valueOf(latLng.longitude);

                    loc = latitud + "|" + longitud;
                }
                else{
                    m.remove();
                    m = mMap.addMarker(new MarkerOptions().position(latLng));
                    String latitud = String.valueOf(latLng.latitude), longitud = String.valueOf(latLng.longitude);

                    loc = latitud + "|" + longitud;
                }
            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

        if (contenedor.equals("21")) {
            Toast.makeText(getApplicationContext(), "Lo sentimos, no esta disponible esta opcion para la version Android que usa. Lo estara pronto, disculpe las molestias", Toast.LENGTH_LONG).show();

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
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, AdjuntarLoc.this);
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, AdjuntarLoc.this);
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

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Toast.makeText(getApplicationContext(), "Manten pulsado donde quieres marcar y vuelve atras", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent();
        i.putExtra("loc", loc);
        setResult(RESULT_OK, i);
        finish();
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
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

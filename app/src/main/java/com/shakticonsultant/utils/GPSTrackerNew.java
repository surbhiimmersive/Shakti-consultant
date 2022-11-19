package com.shakticonsultant.utils;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSTrackerNew extends Service {

	private Context mContext;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	Location location; // Location
	double latitude; // Latitude
	double longitude; // Longitude
	public String address = null;

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000; // 10 meters
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute
	protected LocationManager locationManager;
	public String cityName = "";

	public GPSTrackerNew() {
	}

	public GPSTrackerNew(Context context) {
		this.mContext = context;
		getLocation();
	}

	@SuppressLint("MissingPermission")
	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (!isGPSEnabled && !isNetworkEnabled) {
				// No network provider is enabled
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					int requestPermissionsCode = 50;

					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener, Looper.getMainLooper());
					Log.d("Network", "Network");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();

							Geocoder gcd = new Geocoder(mContext.getApplicationContext(), Locale.getDefault());
							List<Address> addresses;
							try {
								addresses = gcd.getFromLocation(location.getLatitude(),
										location.getLongitude(), 1);
								if (addresses.size() > 0) {
									String subLocality = addresses.get(0).getSubLocality();
									if(subLocality!=null && !"".equals(subLocality )&& !subLocality.equalsIgnoreCase("null")){
										cityName = addresses.get(0).getSubLocality()+",";
									}
									String locality = addresses.get(0).getLocality();
									if(locality!=null && !"".equals(locality )&& !locality.equalsIgnoreCase("null")){
										cityName = cityName+ " "+locality;
									}
									address = addresses.get(0).getPremises()+","+addresses.get(0).getSubLocality()
											+","+addresses.get(0).getThoroughfare()+","+addresses.get(0).getLocality()
											+","+addresses.get(0).getAdminArea()+","+addresses.get(0).getCountryName();
								}
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			// If GPS enabled, get latitude/longitude using GPS Services
			if (isGPSEnabled) {
				if (location == null) {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener, Looper.getMainLooper());
					Log.d("GPS Enabled", "GPS Enabled");
					if (locationManager != null) {


						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();

							Geocoder gcd = new Geocoder(mContext.getApplicationContext(), Locale.getDefault());
							List<Address> addresses;
							try {
								addresses = gcd.getFromLocation(location.getLatitude(),
										location.getLongitude(), 1);
								if (addresses.size() > 0) {
									cityName = addresses.get(0).getLocality();
									System.out.println(addresses.get(0).getLocality());
									address = addresses.get(0).getPremises()+","+addresses.get(0).getSubLocality()
											+","+addresses.get(0).getThoroughfare()+","+addresses.get(0).getLocality()
											+","+addresses.get(0).getAdminArea()+","+addresses.get(0).getCountryName();
								}
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}


	/**
	 * Stop using GPS listener
	 * Calling this function will stop using GPS in your app.
	 * */
	public void stopUsingGPS() {

	}


	private final LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onLocationChanged(final Location location) {

			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}
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
	};

	/**
	 * Function to get latitude
	 * */
	public double getLatitude(){
		if(location != null){
			latitude = location.getLatitude();
		}

		// return latitude
		return latitude;
	}


	/**
	 * Function to get longitude
	 * */
	public double getLongitude(){
		if(location != null){
			longitude = location.getLongitude();
		}

		// return longitude
		return longitude;
	}

	/**
	 * Function to check GPS/Wi-Fi enabled
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}


	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package com.studioclassics.admob;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AdMobView extends TiUIView implements AdListener
{
	private static final String LCAT = "AdMobView";
	AdView adView;
	Activity thisActivity;

	public AdMobView(final TiViewProxy proxy) {
		
		super(proxy);
		
		Log.d(LCAT, "Creating an adMob ad");
		// get the publisher id that was set in the module
		Log.d(LCAT, "AdmobModule.PUBLISHER_ID: " + AdmobModule.PUBLISHER_ID);
		thisActivity = (Activity) proxy.getTiContext().getActivity();
		// create the adView
		adView = new AdView(thisActivity, AdSize.BANNER, AdmobModule.PUBLISHER_ID);
		// Create an ad request.
		AdRequest adRequest = new AdRequest();
	    // Fill out ad request
		// determine testing based on Module variable
		adRequest.setTesting(AdmobModule.TESTING);
		// Start loading the ad in the background.
		adView.loadAd(adRequest);
		// set the listener
		adView.setAdListener(this);
		// Add the AdView to your view hierarchy. 
		// The view will have no size until the ad is loaded.
		setNativeView(adView);	
		
	}

	@Override
	public void processProperties(KrollDict d){
		super.processProperties(d);
		Log.d(LCAT, "process properties");
	}
	
	// pass the method the TESTING flag
	public void requestAd() {
		Log.d(LCAT, "requestAd()");
		// pass the TESTING flag
		loadAd(AdmobModule.TESTING);
	}
	
	// pass true to requestAd(Boolean testing) -- regardless of what the module has set
	public void requestTestAd() {
		Log.d(LCAT, "requestTestAd()");
		loadAd(true);
	}
	
	// load the adMob ad
	public void loadAd(Boolean testing) {
		Log.d(LCAT, "requestAd(Boolean testing) -- testing:" + testing);
		AdRequest adRequest = new AdRequest();
		adRequest.setTesting(testing);
		adView.loadAd(adRequest);
	}
	
	// required methods for the AdListener interface
	public void onReceiveAd(Ad ad){
		Log.d(LCAT, "onReceiveAd()");
		proxy.fireEvent(AdmobModule.AD_RECEIVED, new KrollDict());
	}
	public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode e){
		Log.d(LCAT, "onFailedToReceiveAd(): " + e);
		proxy.fireEvent(AdmobModule.AD_NOT_RECEIVED, new KrollDict());
	}
	
	// not used
	public void onLeaveApplication(Ad ad){
		Log.d(LCAT, "onLeaveApplication()");
	}
	public void onPresentScreen(Ad ad){
		Log.d(LCAT, "onPresentScreen()");
	}
	public void onDismissScreen(Ad ad){
		Log.d(LCAT, "onDismissScreen()");
	}
	


}


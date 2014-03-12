package com.kercar;

import java.util.LinkedList;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;

import com.kercar.AsyncTask.AsyncGetEtat;
import com.kercar.AsyncTask.AsyncStop;
import com.kercar.osmandroid.OSMAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MenuMissionEnCours extends Activity{
	//Attributs
	private Button arreter;
	private TextView latitude;
	private TextView longitude;
	private TextView boussole;
	private OSMAndroid OSM;
	private LinkedList<Integer> list;
	
	private IComAndroid com;
	private static AsyncGetEtat get;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //ContentView
        setContentView(R.layout.menu_mission_en_cours);
		
		//Initialisation des attributs
		arreter = (Button)findViewById(R.id.button_arreter);
		latitude = (TextView)findViewById(R.id.latitudeEdit);
		longitude = (TextView)findViewById(R.id.longitudeEdit);
		boussole = (TextView)findViewById(R.id.boussoleEdit);
		
		OSM = (OSMAndroid)findViewById(R.id.OSM_mission_cours);
		list = new LinkedList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		
		com = ComAndroid.getManager();
		get = new AsyncGetEtat(com, latitude, longitude, boussole, OSM);
		get.execute();		
		
		//Listeners
		arreter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				get.cancel(true);
				new AsyncStop(com).execute();
				Intent intent = new Intent(MenuMissionEnCours.this, MenuSelection.class);
				startActivity(intent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
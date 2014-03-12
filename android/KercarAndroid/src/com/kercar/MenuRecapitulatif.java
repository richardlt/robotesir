package com.kercar;

import java.util.LinkedList;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;

import com.kercar.AsyncTask.AsyncGetEtatDeuxPoints;
import com.kercar.AsyncTask.AsyncLancerMission;
import com.kercar.osmandroid.OSMAndroid;

import BaseDeDonnees.Mission;
import Client.ClientMissions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MenuRecapitulatif extends Activity{

	//Attributs
	private Button btnOK;
	private TextView txtNom;
	private TextView txtEmail;
	private CheckBox cbxRetourDepart;
	private CheckBox cbxPhotoArrivee;
	private OSMAndroid OSM;
	
	private ClientMissions clientMissions;
	private int[] arrive;
	private int emplacement;
	
	private LinkedList<Integer> list;
	
	private IComAndroid com;
	private static AsyncGetEtatDeuxPoints get;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.menu_recapitulatif);

	    /**Initialisation des attributs*/
	    txtNom = (TextView) findViewById(R.id.txtNom);
	    txtEmail= (TextView) findViewById(R.id.txtEmail);
	    cbxRetourDepart = (CheckBox) findViewById(R.id.cbxRetourDepart);
	    cbxPhotoArrivee = (CheckBox) findViewById(R.id.cbxPhotoArrivee);
	    btnOK = (Button) findViewById(R.id.btnOK);
	    OSM = (OSMAndroid) findViewById(R.id.OSM_recap);
	    
	    clientMissions = new ClientMissions(getApplicationContext());
	    arrive = new int[2];
	    emplacement = 0;
	    
	    list = new LinkedList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
	    
	    com = ComAndroid.getManager();
	    
	    //Creation du bundle et reception des objets transferes
        Bundle receptionBundle  = this.getIntent().getExtras().getBundle("AjoutBundleDansIntent");
    	final Mission newMission= (Mission) receptionBundle.getSerializable("AjoutMissionDansBundle");
	    
    	/**Traitement sur txtNom*/
	    txtNom.setText(newMission.getNom());
	   		
	    /**Traitement sur txtEmail*/
		txtEmail.setText(newMission.getEmail());
		
		/**Traitement sur cbxRetourDepart*/
		cbxRetourDepart.setChecked(newMission.getRetourDepart());

		/**Traitement sur cbxPhotoArrivee*/
		cbxPhotoArrivee.setChecked(newMission.getPrendrePhotosArrivee());
		
		//Initialisation de la carte OSM
		try {
			clientMissions.changerMissionEnCours(newMission);
	    	arrive = clientMissions.getPointArriveeMissionEnCours();
	    	emplacement = OSM.addPoint(arrive[0], arrive[1], "Point Arrivee", "");
	    	OSM.invalidate();
			get = new AsyncGetEtatDeuxPoints(list, com, OSM, emplacement);
			get.execute();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("coucou", "la");
		}
		
		/**Traitement de btnOK*/
		btnOK.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Envoi de la mission au robot
				get.cancel(true);
				new AsyncLancerMission(list, newMission.getPrendrePhotosArrivee(), newMission.getRetourDepart(), newMission.getEmail(), com).execute();
				
				//Demarrage de l'activite
				Intent intent = new Intent(MenuRecapitulatif.this, MenuMissionEnCours.class);
				startActivity(intent);
			}
		});
	  }
}
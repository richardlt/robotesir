package com.kercar;

import org.osmdroid.util.GeoPoint;

import android.view.GestureDetector.SimpleOnGestureListener;
import BaseDeDonnees.Mission;
import Client.ClientMissions;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.kercar.osmandroid.OSMAndroid;

public class ChoixPointArrive extends Activity{
	
	//Attributs
	private Button enregistrerMission;
	private OSMAndroid OSM;
	private int[] arrive;
	private ClientMissions clientMissions;
	private int currentPoint;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
   
        /**Initialisation du gestionnaire des missions*/
        setContentView(R.layout.choix_point_arrive);
        clientMissions = new ClientMissions(getApplicationContext());
        arrive = new int[2];
        this.currentPoint = -1;
        enregistrerMission = (Button)findViewById(R.id.buttonEnregistrerMission);
        OSM = (OSMAndroid)findViewById(R.id.OSM_choix_point);
        GestureDetector gesture = new GestureDetector(this, new MapViewGestureListener());
        gesture.setIsLongpressEnabled(true);
        OSM.setListener(gesture);
        
	    /**Reception de bundles*/
	    //Creation du bundle et reception des objets transferes
        Bundle receptionBundle = this.getIntent().getExtras().getBundle("AjoutBundleDansIntent2");        
    	final Mission newMission = (Mission) receptionBundle.getSerializable("AjoutMissionDansBundle2");
    	final String typeFonctionnalite = receptionBundle.getString("Titre");
        
        if(typeFonctionnalite.equals("Editer")){
        	try {
				clientMissions.changerMissionEnCours(newMission);
	        	int[] a = clientMissions.getPointArriveeMissionEnCours();
	        	currentPoint = OSM.addPoint(a[0], a[1], "Point d'arrive", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
		//Listeners
		enregistrerMission.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				

				//Action lors de la creation
				if(typeFonctionnalite.equals("Creer")){						
					try {
						//Enregistrement du point d'arrive du Robot
					    int latitude = OSM.getPointLatitude(currentPoint);
					    int longitude = OSM.getPointLongitude(currentPoint);
					    arrive[0] = latitude;
					    arrive[1] = longitude;
				        newMission.setM_fin(arrive);
				        
						clientMissions.creerMission(newMission);
				        
						msbox("Information","Mission ajoutee avec succes !");
					} catch (Exception e) {
						
						if(e instanceof NullPointerException){

							Toast toast = Toast.makeText(getApplicationContext(), "Attention ! Veuillez choisir un point sur la carte !", Toast.LENGTH_SHORT);
							toast.show();
						}
						else
							e.printStackTrace();
					}
				}
				else if(typeFonctionnalite.equals("Editer")){						
					try {
						//Modification du point d'arrive du Robot
				        int latitude1 = OSM.getPointLatitude(currentPoint);
				        int longitude1 = OSM.getPointLongitude(currentPoint);
				        arrive[0] = latitude1;
				        arrive[1] = longitude1;
			        	
						//Modification des donnee
						clientMissions.setEMailMissionEnCours(newMission.getEmail());
						clientMissions.setRetourDepartMissionEnCours(newMission.getRetourDepart());
						clientMissions.setPrendrePhotosArriveeMissionEnCours(newMission.getPrendrePhotosArrivee());
						clientMissions.setPointArriveeMissionsEnCours(arrive);
						
						msbox("Information", "Mission modifiee avec succes !");
					} catch (Exception e) {							
						
						if(e instanceof NullPointerException){

							Toast toast = Toast.makeText(getApplicationContext(), "Attention ! Veuillez choisir un point sur la carte !", Toast.LENGTH_SHORT);
							toast.show();
						}
						else
							e.printStackTrace();
					}
				}
				else
					throw new IllegalStateException("ChoixPointArrive: Exception ! Type de fonctionnalite inexistant !");
			}
		});
    }
	 
/**Autres methodes ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
	 private void msbox(String titre,String message){
	     AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	     dlgAlert.setTitle(titre); 
	     dlgAlert.setMessage(message); 
	     dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton) {
				 Intent intent = new Intent(ChoixPointArrive.this, MenuSelection.class);
				 startActivity(intent);
	         }
	     });	     
	     dlgAlert.setCancelable(true);
	     dlgAlert.create().show();
	 }
	 
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.activity_main, menu);
         return true;
     }
    
     private class MapViewGestureListener extends SimpleOnGestureListener {	    	
    	 @Override
    	 public void onLongPress(MotionEvent e) {
    	 	 if(currentPoint != -1){
    			 OSM.removePoint(currentPoint);
    	 	 }
    		 GeoPoint point = (GeoPoint) OSM.getProjection().fromPixels(e.getX(), e.getY());
    		 System.out.println(point.getLatitudeE6());
    		 System.out.println(point.getLongitudeE6());
    		 currentPoint = OSM.addPoint(point.getLatitudeE6(), point.getLongitudeE6(), "Arriv�e", "");
    		 OSM.invalidate();
    	 }
    }
}
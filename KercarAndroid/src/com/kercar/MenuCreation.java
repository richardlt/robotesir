package com.kercar;

import BaseDeDonnees.Mission;
import Client.ClientMissions;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MenuCreation extends Activity{

	private ListView listeMissions;
	private Button btnCreation;

	private ClientMissions clientMissions;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_creation);

		//Initialisation des attributs d'affichage
		listeMissions = (ListView) findViewById(R.id.lstListeMissions);
		btnCreation = (Button)findViewById(R.id.send);

		// recuperation des missions dans la base de donnees
		clientMissions = new ClientMissions(getApplicationContext());

		// Affichage des missions crees
		ArrayAdapter<Mission> adapter = new ArrayAdapter<Mission>(this, android.R.layout.simple_list_item_1, clientMissions.getListeMissions().getListe()){
			//Affichage du texte en blanc
			public View getView(int position, View convertView,
					ViewGroup parent) {
				View view =super.getView(position, convertView, parent);

				TextView textView=(TextView) view.findViewById(android.R.id.text1);
				textView.setTextColor(Color.WHITE);

				return view;
			}
		};
		listeMissions.setAdapter(adapter);

		/**Listener de creation d'une nouvelle mission*/
		btnCreation.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

				//On creee une mission
				Mission newMission= new Mission(null, null, false, false);

				//On creee le bundle qui contiendra la mission puis on la met dans celui-ci
				Bundle missionBundle = new Bundle();
				missionBundle.putSerializable("AjoutMissionDansBundle", newMission);
				missionBundle.putString("Titre", "Creer");

				//On cree un intent, celui-ci va transmettre le bundle et aussi de passer a CreationForm activity
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				intent.putExtra("AjoutBundleDansIntent", missionBundle);
				startActivity(intent);
			}
		});

		/**
		 * Listener d'edition des missions deja crees
		 */
		listeMissions.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//On charge la mission a partir de l'indice choisi dans le tableau
				Mission editMission = clientMissions.getListeMissions().getListe().get(position);
		        int[] test= editMission.getM_fin();
		        System.out.println("m_fin: "+test[0]+" "+test[1]);

				//On creee le bundle qui contiendra la mission puis on la met dans celui-ci
				Bundle missionBundle = new Bundle();
				missionBundle.putSerializable("AjoutMissionDansBundle", editMission);
				missionBundle.putString("Titre", "Editer");

				//On cree un intent, celui-ci va transmettre le bundle et aussi de passer a CreationForm activity
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				intent.putExtra("AjoutBundleDansIntent", missionBundle);
				startActivity(intent);
			}
		});
	}
}
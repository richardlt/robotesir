package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.*;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncGauche extends AsyncTask<Void, Void, Void> {

	//Attributs
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncGauche(IComAndroid comAndroid) {
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			CMDTurnMessage cmdCommand = new CMDTurnMessage(false);
			Log.e("AsyncGauche", "Gauche");
			this.comAndroid.envoyerMessage(cmdCommand);
			this.comAndroid.lireReponse();
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}

package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.CMDTurnMessage;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncDroite extends AsyncTask<Void, Void, Void> {

	//Attributs
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncDroite(IComAndroid comAndroid) {
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			CMDTurnMessage cmdCommand = new CMDTurnMessage(true);
			Log.e("AsyncDroite", "Droite");
			this.comAndroid.envoyerMessage(cmdCommand);
			this.comAndroid.lireReponse();
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}

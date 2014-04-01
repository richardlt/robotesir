package com.kercar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class About extends Activity{

		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.about);
	    }
	    
	    @Override
	    protected void onPostCreate(Bundle savedInstanceState){
	    	super.onPostCreate(savedInstanceState);
		       String[] tabnom = {
		    		   " - CHEF DE PROJET -",
		    		    this.getString(R.string.gael),
		    		    " - EQUIPE - ",
		    		    this.getString(R.string.quentin),
		    		    this.getString(R.string.clement),
		    		    this.getString(R.string.guillaume),
		    		    this.getString(R.string.loulou),
		    		    this.getString(R.string.yasser),
		    		    this.getString(R.string.xavier),
		    		    this.getString(R.string.manu),
		    		    this.getString(R.string.lama),
		    		    this.getString(R.string.anass),
		    		    this.getString(R.string.brice),
		    		    this.getString(R.string.florian),
		    		    this.getString(R.string.omar),
		    		    " - SUPERVISEURS - ",
		    		    this.getString(R.string.olivier),
		    		    this.getString(R.string.benoit),
		    		    this.getString(R.string.johann),
		    	};
	        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,R.layout.aboutitem,R.id.aboutitem, tabnom); 
	        View v = (LinearLayout) findViewById(R.id.linearAbout);
	        if(v == null){
	        	System.out.println("Layout null");
	        }
	        ListView lv =(ListView) findViewById(R.id.listeview);
	        if(lv == null){
	        	System.out.println("Liste null");
	        }
	        lv.setAdapter(aa);
	    }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.activity_main, menu);
	        return true;
	    }
}
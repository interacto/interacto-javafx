package org.malai.example.drawingEditor;

import org.malai.android.interaction.AndroidEventManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	protected AndroidEventManager eventManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		eventManager = new AndroidEventManager();
		eventManager.attachTo(findViewById(R.id.button1));
		
		Toast.makeText(getApplicationContext(), findViewById(R.id.button1).toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

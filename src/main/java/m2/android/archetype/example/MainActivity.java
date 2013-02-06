package m2.android.archetype.example;

import m2.android.archetype.example.AndroidArchetype_library.HelloAndroidActivity;
<<<<<<< HEAD
import m2.android.archetype.example.facebook.HelloFacebookSampleActivity;
=======
import m2.android.archetype.example.robojuice.Roboguice2SimpleActivity;
>>>>>>> 60d07e89aedd8195d39a99a40b7dfe437a78e591
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

<<<<<<< HEAD
	public static final String[] options = { "Roboguice2", "Roboguice2", "withFacebook" };
=======
	public static final String[] options = { "Roboguice2", "OrmLiteExampleActivity" };
>>>>>>> 60d07e89aedd8195d39a99a40b7dfe437a78e591

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
		HelloAndroidActivity.testLibrary();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent;
	
		switch (position) {
			default:
			case 0:
				intent = new Intent(this, Roboguice2SimpleActivity.class);
				startActivity(intent);
				break;
			case 1:
//				intent = new Intent(this, OrmLiteExampleActivity.class);
				break;
			case 2:
				intent = new Intent(this, HelloFacebookSampleActivity.class);
				break;
		
		}
	
		
	}

}

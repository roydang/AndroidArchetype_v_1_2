package m2.android.archetype.example;

import m2.android.archetype.example.AndroidArchetype_library.HelloAndroidActivity;
import m2.android.archetype.example.ormlite.OrmLitePullToRefreshActivity;
import m2.android.archetype.example.robojuice.Roboguice2SimpleActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	public static final String[] options = { "Roboguice2", "OrmLitePullToRefreshActivity" };

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
				intent = new Intent(this, OrmLitePullToRefreshActivity.class);
				startActivity(intent);
				break;
		
		}
	
		
	}

}

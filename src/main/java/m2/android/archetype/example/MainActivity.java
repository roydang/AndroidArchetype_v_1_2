package m2.android.archetype.example;

import m2.android.archetype.example.facebook.HelloFacebookSampleActivity;
import m2.android.archetype.example.ormlite.OrmCursorAdapterActivity;
import m2.android.archetype.example.pulltorefresh.PullToRefreshActivity;
import m2.android.archetype.example.robojuice.Roboguice2SimpleActivity;
import m2.android.archetype.example.simplecursor.CursorAdapterActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//github.com/roydang/AndroidArchetype_v_1_2.git

public class MainActivity extends ListActivity {

	public static final String[] options = { "Roboguice2", "OrmLiteExampleActivity", "withFacebook", "simpleDBCursor", "OrmDBCursor"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
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
				intent = new Intent(this, PullToRefreshActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(this, HelloFacebookSampleActivity.class);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(this, CursorAdapterActivity.class);
				startActivity(intent);
				break;				
			case 4:
				intent = new Intent(this, OrmCursorAdapterActivity.class);
				startActivity(intent);
				break;			
		}
	}

}

package m2.android.archetype.example.simplecursor;

import m2.android.archetype.example.R;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class CursorAdapterActivity extends Activity {

	ListView list;
	DBHelper dbHelper;
	SQLiteDatabase db;
	String sql;
	Cursor cursor;

	final static String dbName = "person.db";
	final static int dbVersion = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cursor_adapter);

		list = (ListView) findViewById(R.id.list);
		dbHelper = new DBHelper(this, dbName, null, dbVersion);

		selectDB();

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				cursor.moveToPosition(position);
				String str = cursor.getString(cursor.getColumnIndex("name"));
				Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

	private void selectDB() {
		db = dbHelper.getWritableDatabase();
		sql = "SELECT * FROM test;";

		cursor = db.rawQuery(sql, null);
		if (cursor.getCount() > 0) {
			startManagingCursor(cursor);
			DBAdapter dbAdapter = new DBAdapter(this, cursor);
			list.setAdapter(dbAdapter);
		}
	}

}

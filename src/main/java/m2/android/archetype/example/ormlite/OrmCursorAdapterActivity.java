package m2.android.archetype.example.ormlite;

import java.sql.SQLException;
import java.util.List;

import m2.android.archetype.example.R;
import m2.android.archetype.example.pulltorefresh.object.Post;
import m2.android.archetype.example.pulltorefresh.object.Stream;
import m2.android.archetype.example.pulltorefresh.object.Streams;
import m2.android.archetype.util.Logger;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nhn.android.archetype.base.object.BaseObj;

public class OrmCursorAdapterActivity extends Activity {

	private static Logger logger = Logger.getLogger(OrmCursorAdapterActivity.class);
	
	ListView list;
	private DatabaseHelper databaseHelper = null;
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
		selectDB();
		loadData();
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
	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}
	private void loadData() {
		BaseObjTransformer transformer = new BaseObjTransformer();
		AQuery aq = new AQuery(this);
		String url = "http://api.me2day.net/api/stream.json?count=40&resolution_type=xhdpi&include_multimedia=true&akey=8270995ff1c87825f70efbbef1b874d0&asig=MTM2MjM4Njc0NDU2NSQkZmZmZmZmZmYkJGM5MTlkNDIxOTBmY2VkMmE2YzZkMzBlYjAzZDRhNDk2&uid=chs_test&ukey=full_auth_token%2086df10a4d3fcfea85a82792ab49520ba";
		aq.transformer(transformer).ajax(url, BaseObj.class, 0, this, "homeCb");
	}
	public void homeCb(String url, BaseObj baseObj, AjaxStatus status){
		
		logger.d("##url[%s]", url);
		
		Streams streams = baseObj.as(Streams.class);
		logger.d("##streams[%s]", streams);
		List<Stream> stList = streams.getStream();
		
		for(Stream stream : stList) {
			Post post = stream.getPost();
			if (post != null) {
				logger.d("##post ID[%s]", post.getPostId());
			}
		}

		
		logger.d("##AjaxStatus[%s]", status.getCode());
		
	}
	private void selectDB() {
		try {
			Dao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
			QueryBuilder<SimpleData, Integer> qb = simpleDao.queryBuilder();
			CloseableIterator<SimpleData> iter = simpleDao.iterator(qb.prepare());
			
			AndroidDatabaseResults results = (AndroidDatabaseResults)iter.getRawResults();
			cursor = results.getRawCursor();
			if (cursor.getCount() > 0) {
				startManagingCursor(cursor);
				DBAdapter dbAdapter = new DBAdapter(this, cursor);
				list.setAdapter(dbAdapter);
			}
			
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}

	
	}

}

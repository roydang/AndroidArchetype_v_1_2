package m2.android.archetype.example.ormlite;

import m2.android.archetype.util.Logger;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class BaseDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static Logger logger = Logger.getLogger(BaseDatabaseHelper.class);
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "M3database.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 10;

	
	public BaseDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		logger.d("BaseDatabaseHelper onCreate");
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		logger.d("BaseDatabaseHelper onUpgrade");
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {	
		logger.d("BaseDatabaseHelper onDowngrade");
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {	
		super.onOpen(db);
		logger.d("BaseDatabaseHelper onOpen");
	}
	@Override
	public void close() {
		super.close();
	}
}

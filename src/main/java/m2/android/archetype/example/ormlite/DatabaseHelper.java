package m2.android.archetype.example.ormlite;

import java.sql.SQLException;

import m2.android.archetype.example.pulltorefresh.object.Author;
import m2.android.archetype.example.pulltorefresh.object.AuthorDBData;
import m2.android.archetype.example.pulltorefresh.object.PostDBData;
import m2.android.archetype.util.Logger;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends BaseDatabaseHelper {

	private static Logger logger = Logger.getLogger(DatabaseHelper.class);
	
	private Dao<SimpleData, Integer> simpleDao = null;
	private Dao<PostDBData, Integer> postDao = null;
	private Dao<AuthorDBData, Integer> authorDao = null;

	public DatabaseHelper(Context context) {
		super(context);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		createTables(connectionSource);
	}
	private void createTables(ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, SimpleData.class);
			TableUtils.createTable(connectionSource, PostDBData.class);
			TableUtils.createTable(connectionSource, AuthorDBData.class);
		} catch (SQLException e) {
			logger.e(e);
		}
	}
	
	
	private void createSampleData() {
		try {

			// here we try inserting data in the on-create as a test
			Dao<SimpleData, Integer> dao = getSimpleDataDao();
			
			long millis = System.currentTimeMillis();
			// create some entries in the onCreate
			SimpleData simple = new SimpleData(millis);
			dao.create(simple);
			simple = new SimpleData(millis + 1);
			dao.create(simple);
			Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		super.onUpgrade(db, connectionSource, oldVersion, newVersion);
		try {
			TableUtils.dropTable(connectionSource, SimpleData.class, true);
			TableUtils.dropTable(connectionSource, PostDBData.class, true);
			TableUtils.dropTable(connectionSource, AuthorDBData.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onDowngrade(db, oldVersion, newVersion);
		try {
			TableUtils.dropTable(connectionSource, SimpleData.class, true);
			TableUtils.dropTable(connectionSource, PostDBData.class, true);
			TableUtils.dropTable(connectionSource, AuthorDBData.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public Dao<SimpleData, Integer> getSimpleDataDao() throws SQLException {
		if (simpleDao == null) {
			simpleDao = getDao(SimpleData.class);
		}
		return simpleDao;
	}

	public Dao<PostDBData, Integer> getPostDao() throws SQLException {
		if (postDao == null) {
			postDao = getDao(PostDBData.class);
		}
		return postDao;
	}
	public Dao<AuthorDBData, Integer> getAuthorDao() throws SQLException {
		if (authorDao == null) {
			authorDao = getDao(AuthorDBData.class);
		}
		return authorDao;
	}
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		simpleDao = null;
		postDao = null;
		authorDao = null;
	}
	
}

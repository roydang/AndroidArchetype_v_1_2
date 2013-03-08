package m2.android.archetype.example.ormlite;


import m2.android.archetype.example.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.nhn.android.archetype.base.util.internal.M2baseLogger;

public class DBAdapter extends CursorAdapter {

	private static M2baseLogger logger = M2baseLogger.getLogger(DBAdapter.class);
	AQuery aq;
	public DBAdapter(Context context, Cursor c) {
		super(context, c);
		aq = new AQuery(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		
		String columNames[] = cursor.getColumnNames();
		
		for (String colName : columNames) {
			logger.d("bindView colName[%s]", colName);
		}
		
		ItemViewHolder holder = (ItemViewHolder)view.getTag();
		
		String url = cursor.getString(cursor.getColumnIndex("face"));
		String body = cursor.getString(cursor.getColumnIndex("body"));
		String tag = cursor.getString(cursor.getColumnIndex("tagtext"));
		String photoUrls = cursor.getString(cursor.getColumnIndex("photoUrl"));
		
		int position = cursor.getPosition();
		
		logger.d("bindView url[ ] body[%s] tag[%s]",   body, tag);
		
		AQuery aquery = aq.recycle(view);
		if (aquery.shouldDelay(position, view, holder.viewGroup, url)) {
			aquery.id(holder.face).clear();
		} else {
			aquery.id(holder.face).image(url, true, true);
		}
		aquery.id(holder.body).text(body);
		aquery.id(holder.tag).text(tag);
		
		
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		logger.d("newView cursor[%s]", cursor);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.activity_ormcursor_adapter_listlayout, parent, false);
		
		 
		ItemViewHolder holder = new ItemViewHolder();
		
		holder.face = (ImageView)v.findViewById(R.id.face);
		holder.body = (TextView)v.findViewById(R.id.body);
		holder.tag = (TextView)v.findViewById(R.id.tag);
		holder.viewGroup = parent;
//		holder.photoLayout = (LinearLayout)
		v.setTag(holder);
		
		return v;
	}
	private class ItemViewHolder {
		public ImageView face;
		public TextView body;
		public TextView tag;
		public ViewGroup viewGroup;
		public LinearLayout photoLayout;
	}
}

package m2.android.archetype.example.ormlite;


import m2.android.archetype.example.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DBAdapter extends CursorAdapter {

	public DBAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		final TextView millis = (TextView) view.findViewById(R.id.millis);
		final TextView date = (TextView) view.findViewById(R.id.date);
		final TextView even = (TextView) view.findViewById(R.id.even);

		millis.setText("millis : " + cursor.getString(cursor.getColumnIndex("millis")));
		date.setText("date : " + cursor.getString(cursor.getColumnIndex("date")));
		even.setText("even : " + cursor.getString(cursor.getColumnIndex("even")));

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.activity_ormcursor_adapter_listlayout, parent, false);
		return v;
	}
}

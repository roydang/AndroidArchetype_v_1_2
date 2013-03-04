package m2.android.archetype.example.simplecursor;


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

		final ImageView image = (ImageView) view.findViewById(R.id.image);
		final TextView name = (TextView) view.findViewById(R.id.name);
		final TextView age = (TextView) view.findViewById(R.id.age);

		image.setImageResource(R.drawable.icon);
		name.setText("이름 : " + cursor.getString(cursor.getColumnIndex("name")));
		age.setText("나이 : " + cursor.getString(cursor.getColumnIndex("age")));

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.activity_cursor_adapter_listlayout, parent, false);
		return v;
	}
}

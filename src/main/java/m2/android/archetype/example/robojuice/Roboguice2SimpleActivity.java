package m2.android.archetype.example.robojuice;

import m2.android.archetype.example.R;
import m2.android.archetype.example.R.drawable;
import m2.android.archetype.example.R.id;
import m2.android.archetype.example.R.layout;
import m2.android.archetype.example.R.string;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.nhn.android.archetype.base.AABaseActivity;

public class Roboguice2SimpleActivity extends AABaseActivity {
	
	@InjectView(R.id.name)             			TextView name; 
    @InjectView(R.id.thumbnail)        			ImageView thumbnail; 
    @InjectResource(R.drawable.icon)   			Drawable icon; 
    @InjectResource(R.string.app_name) 			String myName; 
    @Inject                            			LocationManager loc; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roboguice2_simple);
		name.setText("Good RoboGuice");
		thumbnail.setImageDrawable(icon);
	}
	
}

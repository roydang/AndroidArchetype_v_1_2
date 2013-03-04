package m2.android.archetype.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class AQBaseActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContainerView());
	}
	protected abstract int getContainerView();	 
}

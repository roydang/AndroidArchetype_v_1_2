package m2.android.archetype.example.aquery;

import m2.android.archetype.base.AQBaseActivity;
import m2.android.archetype.example.R;
import m2.android.archetype.example.pulltorefresh.StreamStoryFragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class AqueryTestActivity extends AQBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);		

		
	}

	@Override
	protected int getContainerView() {		
		return R.layout.activity_orm_lite_pulltorefresh;
	}
}

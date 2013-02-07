package m2.android.archetype.example.ormlite;


import m2.android.archetype.base.AABaseFragmentActivity;
import m2.android.archetype.example.R;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;



public class OrmLitePullToRefreshActivity extends AABaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orm_lite_pulltorefresh);
		
		FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

       //add a fragment
        StreamStoryFragment myFragment = new StreamStoryFragment();
        fragmentTransaction.add(R.id.myfragment, myFragment);
        fragmentTransaction.commit(); 
	    
	       
	}
}

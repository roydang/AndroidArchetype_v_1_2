package m2.android.archetype.example.aquery.fb;

import java.util.Locale;

import m2.android.archetype.base.AQBaseActivity;
import m2.android.archetype.example.aquery.fb.base.Constants;
import m2.android.archetype.example.aquery.fb.base.FeedMode;
import m2.android.archetype.example.aquery.fb.base.PQuery;
import m2.android.archetype.example.aquery.fb.base.PTransformer;
import m2.android.archetype.example.aquery.fb.util.AQueryFBUtility;
import m2.android.archetype.example.aquery.fb.util.PrefUtility;
import m2.android.archetype.util.Logger;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.androidquery.auth.FacebookHandle;
import com.androidquery.util.AQUtility;

public abstract class BaseFacebookActivity extends AQBaseActivity {
	private static Logger logger = Logger.getLogger(BaseFacebookActivity.class);
	
	private boolean progress;
	
	public PQuery aq;
	protected PQuery aq2;
	protected FacebookHandle handle;
	protected PTransformer transformer;
	protected String locale = getLocale();
	
	@Override
    public void onCreate(Bundle savedInstanceState){				
        super.onCreate(savedInstanceState);
        makeHandle();
        
        aq = new PQuery(this);
        aq2 = new PQuery(this);
        transformer = new PTransformer();
        
        setContentView(getContainerView());
        init(savedInstanceState);
	}
	
	@Override
	public void onStart(){
		super.onStart();
	}
	
	@Override
    public void onStop(){
		super.onStop();
    }
	
	@Override
    public void onDestroy(){
    	super.onDestroy();
    	aq.dismiss();
    }
	
	private void makeHandle(){
		 
		handle = AQueryFBUtility.makeHandle(this);
		 
		boolean sso;
		 
		if(!PrefUtility.contains(Constants.PREF_SSO)){
			sso = PrefUtility.getEnum(FeedMode.class, null) == null;
			PrefUtility.put(Constants.PREF_SSO, sso);
			AQUtility.debug("first loaded sso", sso);
		}else{
			sso = PrefUtility.getBoolean(Constants.PREF_SSO, true); 
		}
		
		if(sso){
			handle.sso(Constants.ACTIVITY_SSO);
		}
	}
	
	public void showToast(String message) {
      	
    	if(message == null || message.length() == 0) return;
    	
    	try{
	    	Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
	    	toast.setGravity(Gravity.CENTER, 0, 0);
	    	toast.show();
    	}catch(Exception e){
    		AQUtility.report(e);
    	}
    }
    
    public boolean isBusy(){
    	return progress;
    }

	protected String getLocale(){
    	return Locale.getDefault().toString();
    }
	
	@Override
	protected int getContainerView() {
		// TODO Auto-generated method stub
		return 0;
	}

	protected boolean needProgress(){
		return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
		logger.d("on act result");
    	
    	aq.forward(this, requestCode, resultCode, data);
    	
    	switch(requestCode) {
    		
	    	case Constants.ACTIVITY_SSO: {
	    		handle.onActivityResult(requestCode, resultCode, data);	    		
	    		break;
	    	}
	    	
    	}
    }
	
	protected abstract void init(Bundle savedInstanceState);
	public abstract void refresh();

}

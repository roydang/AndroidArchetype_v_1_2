/*
 * M2Progress.java $version 2012. 09. 13
 *
 * Copyright 2012 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.widget;

import m2.android.archetype.example.R;
import m2.android.archetype.util.Logger;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.nhn.android.archetype.base.theme.ThemeImageView;

public class M2Progress extends ThemeImageView {
	private static Logger logger = Logger.getLogger(M2Progress.class);
	
	private AnimationDrawable frameAnimation;
	private boolean autoStart = true;
	
	public M2Progress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public M2Progress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public M2Progress(Context context) {
		super(context);
		init(null);
	}
	
	public boolean getAutoStart() {
		return autoStart;
	}

	public void setAutoStart(boolean autoStart) {
		this.autoStart = autoStart;
	}

	private void applyAttributeSet(AttributeSet attrs) {
		try {
			if (attrs != null) {
				TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.M2Progress);
				
				final int count = a.getIndexCount();
				for (int i=0;i<count;i++) {
					int attr = a.getIndex(i);
					
					switch(attr) {
					case R.styleable.M2Progress_autostart:
						autoStart = a.getBoolean(attr, autoStart);
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.e(e);
		}
	}

	private void init(AttributeSet attrs) {
		applyAttributeSet(attrs);
		
		// setBackgroundResource(R.anim.progress);
		this.setImageResource(R.anim.progress);
		frameAnimation = (AnimationDrawable) this.getDrawable();
		
		if (getAutoStart()) {
			frameAnimation.start();
		}
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);

		if (visibility == View.INVISIBLE || visibility == View.GONE) {
			frameAnimation.stop();
		} else {
			if (getAutoStart()) {
				frameAnimation.start();
			}
		}
	}
	
	public void start() {
		frameAnimation.start();
	}
	
	public void stop() {
		frameAnimation.stop();
	}
}

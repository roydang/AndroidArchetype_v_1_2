/*
 * BackgroundRelativeLayoutView.java $version 2012. 09. 13
 *
 * Copyright 2012 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.nhn.android.archetype.base.theme.ThemeRelativeLayout;

public class BackgroundRelativeLayoutView extends ThemeRelativeLayout {
	public BackgroundRelativeLayoutView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BackgroundRelativeLayoutView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BackgroundRelativeLayoutView(Context context) {
		super(context);
	}
}

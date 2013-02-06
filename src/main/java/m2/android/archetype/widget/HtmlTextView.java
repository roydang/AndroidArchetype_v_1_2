/*
 * HtmlTextView.java $version 2012. 09. 13
 *
 * Copyright 2012 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.widget;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;

import com.nhn.android.archetype.base.theme.ThemeTextView;

public class HtmlTextView extends ThemeTextView {
	public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public HtmlTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HtmlTextView(Context context) {
		super(context);
	}
	
	public void setHtmlText(String text) {
		setText(Html.fromHtml(text));
	}
}

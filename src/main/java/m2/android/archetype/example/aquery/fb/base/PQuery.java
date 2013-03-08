/*******************************************************************************
 * Copyright 2012 AndroidQuery (tinyeeliu@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Additional Note:
 * 1. You cannot use AndroidQuery's Facebook app account in your own apps.
 * 2. You cannot republish the app as is with advertisements.
 ******************************************************************************/
package m2.android.archetype.example.aquery.fb.base;

import m2.android.archetype.example.aquery.fb.util.LayoutStringUtility;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.androidquery.AbstractAQuery;
import com.androidquery.util.AQUtility;

public class PQuery extends AbstractAQuery<PQuery> {

	public PQuery(View root) {
		super(root);
	}

	public PQuery(Activity act) {
		super(act);
	}

	public PQuery(Activity act, View root) {
		super(act, root);
	}

	public PQuery text(CharSequence text, TextView.BufferType type, boolean gone) {

		if (view instanceof TextView) {
			if (gone && (text == null || text.length() == 0)) {
				gone();
			} else {
				TextView tv = (TextView) view;
				tv.setText(text, type);
				visible();
			}
		}

		return this;
	}

	private static final boolean LAYER = android.os.Build.VERSION.SDK_INT >= 14;

	public PQuery text(LayoutStringUtility text, boolean gone) {
		text(text);
		return this;
	}

	private static final String FW_CB = "aq.fw.cb";
	private static final String FW_ID = "aq.fw.id";

	public void start(Activity act, Intent intent, int requestCode,
			Object handler, String method) {

		// String clsName = handler.getClass().getName();

		intent.putExtra(FW_CB, method);

		if (handler instanceof Fragment) {
			Fragment frag = (Fragment) handler;
			intent.putExtra(FW_ID, frag.getId());
		}

		act.startActivityForResult(intent, requestCode);

	}

	private final static Class<?>[] FW_SIG = new Class[] { int.class,
			int.class, Intent.class };

	public void forward(FragmentActivity act, int requestCode, int resultCode,
			Intent data) {

		if (data == null)
			return;

		String method = data.getStringExtra(FW_CB);

		AQUtility.debug("being forwarded!", method);

		if (method == null)
			return;

		int id = data.getIntExtra(FW_ID, -1);
		if (id != -1) {

			Fragment f = act.getSupportFragmentManager().findFragmentById(id);

			AQUtility.debug("fr", f);

			if (f != null) {
				AQUtility.invokeHandler(f, method, false, true, FW_SIG,
						requestCode, resultCode, data);
			}

		}
	}

	public void result(Activity act, int resultCode, Intent data) {

		Intent input = act.getIntent();

		if (input != null) {
			data.putExtra(FW_CB, input.getStringExtra(FW_CB));
			data.putExtra(FW_ID, input.getIntExtra(FW_ID, -1));
		}

		act.setResult(resultCode, data);

	}
}

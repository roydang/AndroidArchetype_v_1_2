package m2.android.archetype.template;

import android.view.View;

import com.nhn.android.archetype.base.object.BaseObj;

public interface TemplateEventListener {
	void onViewClicked(View view, BaseObj baseObj);
	boolean onViewLongClicked(View view, BaseObj baseObj);
}

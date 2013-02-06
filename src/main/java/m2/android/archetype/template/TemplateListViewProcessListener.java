package m2.android.archetype.template;

import android.view.View;

import com.nhn.android.archetype.base.object.BaseObj;

public interface TemplateListViewProcessListener {
	void onProcessView(int position, View convertView, BaseObj baseObj);
}

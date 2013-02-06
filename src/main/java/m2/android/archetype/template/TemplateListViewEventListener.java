package m2.android.archetype.template;

import android.view.View;

import com.nhn.android.archetype.base.object.BaseObj;

public interface TemplateListViewEventListener {
	/**
	 *  해당 영역의 클릭이벤트
	 * @param view
	 * @param baseObj
	 */
	void onItemClicked(View view, BaseObj baseObj);
	
	/**
	 *  해당 영역의 롱클릭이벤트
	 * @param view
	 * @param baseObj
	 */
	boolean onItemLongClicked(View view, BaseObj baseObj);
	
	/**
	 *  서브 아이템의 클릭이벤트
	 * @param view
	 * @param baseObj
	 */
	void onViewClicked(View view, BaseObj baseObj);

	/**
	 *  서브 아이템의 롱 클릭이벤트
	 * @param view
	 * @param baseObj
	 */
	boolean onViewLongClicked(View view, BaseObj baseObj);
}

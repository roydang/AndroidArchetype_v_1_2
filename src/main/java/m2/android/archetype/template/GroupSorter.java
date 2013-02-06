package m2.android.archetype.template;

import java.util.List;

import android.content.Context;

import com.nhn.android.archetype.base.object.BaseObj;

public interface GroupSorter {
	List<GroupedBaseObjList> group(Context context, List<BaseObj> objList, String groupKey, boolean asc);
}

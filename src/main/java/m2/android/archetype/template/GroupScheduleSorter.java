package m2.android.archetype.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;

import com.nhn.android.archetype.base.object.BaseObj;

public class GroupScheduleSorter {
	public List<GroupedBaseObjList> group(Context context, List<BaseObj> objList, final String groupKey, final boolean asc) {
		List<BaseObj> tempList = new ArrayList<BaseObj>();
		tempList.addAll(objList);
		
		Collections.sort(tempList, new Comparator<BaseObj>() {
			public int compare(BaseObj lhs, BaseObj rhs) {
				if (asc) {
					return lhs.getString(groupKey).compareTo(rhs.getString(groupKey));
				} else {
					return rhs.getString(groupKey).compareTo(lhs.getString(groupKey));
				}
			}
		});

		TemplateDataParser dataParser = new TemplateDataParser();
		List<GroupedBaseObjList> groupedBaseObjList = new ArrayList<GroupedBaseObjList>();
		List<BaseObj> emptyGroup = new ArrayList<BaseObj>();
		
		String currentGroupText = null;
		GroupedBaseObjList currentGroup = null;
		
		for (BaseObj baseObj : tempList) {
			Object date = dataParser.parse(context, "schedule", baseObj.getString(groupKey));
			
			if (date == null) {
				emptyGroup.add(baseObj);
				continue;
			}
			
			String groupText = date.toString();
			//logger.d("groupText: %s time: %s", groupText, baseObj.getString(groupKey));

			if (groupText.equals(currentGroupText) == false) {
				currentGroupText = groupText;
				currentGroup = new GroupedBaseObjList();
				currentGroup.setId(groupText);
				currentGroup.setHeaderText(groupText);
				currentGroup.setObjList(new ArrayList<BaseObj>());
				groupedBaseObjList.add(currentGroup);
			}
			currentGroup.getObjList().add(baseObj);
		}
		
		if (groupedBaseObjList.size() > 0 && emptyGroup.size() > 0) {
			groupedBaseObjList.get(groupedBaseObjList.size() - 1).getObjList().addAll(emptyGroup);
		}
		
		return groupedBaseObjList;
	}
}

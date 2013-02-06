package m2.android.archetype.template;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import m2.android.archetype.example.R;
import m2.android.archetype.util.Logger;
import m2.android.archetype.util.date.SimpleDateFormatFactory;
import android.content.Context;

import com.nhn.android.archetype.base.object.BaseObj;

public class GroupRTCSSorter implements GroupSorter {
	private static Logger logger = Logger.getLogger(GroupRTCSSorter.class);
	
	public List<GroupedBaseObjList> group(Context context, List<BaseObj> objList, final String groupKey, final boolean asc) {
		List<BaseObj> tempList = new ArrayList<BaseObj>();
		tempList.addAll(objList);
		
		Collections.sort(tempList, new Comparator<BaseObj>() {
			public int compare(BaseObj lhs, BaseObj rhs) {
				if (asc) {
					return lhs.getInt("order_by") - rhs.getInt("order_by");
				} else {
					return rhs.getInt("order_by") - lhs.getInt("order_by");
				}
			}
		});
		
		TemplateDataParser dataParser = new TemplateDataParser();
		List<GroupedBaseObjList> groupedBaseObjList = new ArrayList<GroupedBaseObjList>();
		List<BaseObj> emptyGroup = new ArrayList<BaseObj>();
		
		String currentGroupText = null;
		GroupedBaseObjList currentGroup = null;
		
		for (BaseObj baseObj : tempList) {
			Object date = dataParser.parse(context, "date", baseObj.getString(groupKey));
			
			if (date == null) {
				emptyGroup.add(baseObj);
				continue;
			}
			
			String groupText = date.toString();

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
		
		if (emptyGroup.size() > 0) {
			if (groupedBaseObjList.size() == 0) {
				String groupText = "";
				SimpleDateFormat format = SimpleDateFormatFactory.get(SimpleDateFormatFactory.TIME_FORMAT);
				try {
					Date date = new Date();
					SimpleDateFormat formatter = SimpleDateFormatFactory.get("yyyy/M/d EEEEEEEEEE");
					String tmp1 = formatter.format(date);
					String tmp2 = tmp1.replace("AM", context.getString(R.string.am));
					String dateString = tmp2.replace("PM", context.getString(R.string.pm));
					groupText = dateString.toLowerCase();
				} catch (Exception e) {
					logger.e(e);
				}
				
				currentGroup = new GroupedBaseObjList();
				currentGroup.setId(groupText);
				currentGroup.setHeaderText(groupText);
				currentGroup.setObjList(new ArrayList<BaseObj>());
				groupedBaseObjList.add(currentGroup);
			}
			
			groupedBaseObjList.get(groupedBaseObjList.size() - 1).getObjList().addAll(emptyGroup);
		}
		
		return groupedBaseObjList;
	}
}
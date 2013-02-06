package m2.android.archetype.template;

import m2.android.archetype.util.Logger;
import m2.android.archetype.util.StringUtility;

public class FileInfoParser {
	private static Logger logger = Logger.getLogger(FileInfoParser.class);
	

	public static String parseFileSize(Object sz) {
		long size = 0;
		
		if (sz instanceof Long) {
			size = (Long) sz;
		} else if (sz instanceof Integer) {
			size = (Integer) sz;
		} else {
			try {
				size = Long.parseLong(sz.toString());
			} catch (Exception e) {
				logger.e(e);
			}	
		}
		
		final String[] SIZE_NAMES = new String[] {"B", "KB", "MB", "GB"};
		int nameIdx = 0;
		while (true) {
			long left = size % 1024;
			size = size / 1024;
			
			if (size == 0) {
				return StringUtility.format("%s%s", left, SIZE_NAMES[nameIdx]);
			}
			
			nameIdx++;
		}
	}
	

}

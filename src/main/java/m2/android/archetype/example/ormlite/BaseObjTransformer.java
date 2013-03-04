package m2.android.archetype.example.ormlite;

import java.io.UnsupportedEncodingException;

import m2.android.archetype.util.Logger;

import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.Transformer;
import com.nhn.android.archetype.base.object.BaseObj;

public class BaseObjTransformer implements Transformer {

	private static Logger logger = Logger.getLogger(BaseObjTransformer.class);
	
	@Override
	public <T> T transform(String url, Class<T> type, String encoding, byte[] data, AjaxStatus status) {
//		Streams streams = response.as(Streams.class);
//		List<Stream> streamList = streams.getStream();
//		return null;
		logger.d("##type[%s]", type);
	
		
		try {
			String str = new String(data, encoding);
			logger.d("##str");
			BaseObj baseObj = BaseObj.parse(str);
			logger.d("##baseObj");
			return (T) baseObj;
		} catch (UnsupportedEncodingException e) {
			logger.e(e);
		}		

		
		return null;
	}

}

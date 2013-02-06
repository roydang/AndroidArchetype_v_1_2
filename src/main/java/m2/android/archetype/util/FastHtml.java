package m2.android.archetype.util;

import java.util.ArrayList;
import java.util.List;

import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.URLSpan;

public class FastHtml {
	private static Logger logger = Logger.getLogger(FastHtml.class);
	
	public static Spanned fromHtml(String html) {
		return new FastSpanned(html);
	}
	
	public static class FastSpanned implements Spanned {
		private String html;
		private String text;
		private List<SpanPos> spannedList;
		
		public FastSpanned(String html) {
			this.html = html;
			parse();
		}

		private void parse() {
			logger.d("parse(%s)", html);

			spannedList = new ArrayList<SpanPos>();
			List<String> stringList = new ArrayList<String>();
			String[] chunks = null;
			
			if (html.indexOf('<') < 0) {
				stringList.add(html);
			} else {
				chunks = this.html.split("<a href");
				
				for (String chunk : chunks) {
					String[] splits = chunk.split("</a>");
					for (String split : splits) {
						stringList.add(split);
					}
				}
			}
			
			int start = 0;
			int end = 0;
			StringBuffer sb = new StringBuffer();
			
			for (String str : stringList) {
				String msg = null;
				Object span = null;
				
				if (chunks != null && str.startsWith("='http://") || str.startsWith("='https://")) {
					chunks = str.split("'>");
					span = new URLSpanNoUnderline(chunks[0].substring(2));
					msg = chunks[1];
				} else {
					msg = str;
				}
				
				msg = msg.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
				sb.append(msg);
				end += msg.length();
				
				if (span == null) {
					span = msg;
				}
				
				SpanPos spanPos = new SpanPos(span, start, end);
				spannedList.add(spanPos);
				
				start = end;
			}
			
			text = sb.toString();
		}
		
		public char charAt(int index) {
			return text.charAt(index);
		}

		public int length() {
			return text.length();
		}

		public CharSequence subSequence(int start, int end) {
			return text.subSequence(start, end);
		}

		public int getSpanEnd(Object tag) {
			for (SpanPos pos : spannedList) {
				if (pos.obj == tag) {
					return pos.end;
				}
			}
			
			return 0;
		}

		public int getSpanFlags(Object tag) {
			return 0;
		}

		public int getSpanStart(Object tag) {
			for (SpanPos pos : spannedList) {
				if (pos.obj == tag) {
					return pos.start;
				}
			}
			
			return 0;
		}

		@SuppressWarnings("unchecked")
		public Object[] getSpans(int arg0, int arg1, Class arg2) {
			List<Object> spanList = new ArrayList<Object>();
			
			for (int i=0;i<spannedList.size();i++) {
				Object span = spannedList.get(i).obj;
				if (span instanceof URLSpan) {
					spanList.add(span);
				}
			}
			
			return spanList.toArray();
		}

		public int nextSpanTransition(int start, int limit, Class type) {
			return 0;
		}

		public String toString() {
			return text;
		}
	}
	
	private static class SpanPos {
		private int start;
		private int end;
		private Object obj;
		
		public SpanPos(Object obj, int start, int end) {
			this.obj = obj;
			this.start = start;
			this.end = end;
		}
	}
	
    private static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        
        @Override 
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
}

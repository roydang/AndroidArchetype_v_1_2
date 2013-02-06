package m2.android.archetype.util.date;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Object describes a span of time. A time span has a start time, and end time
 * and a duration. Absolute times, like start and end, are always in seconds
 * (double) in "epoch" time (starting January 1, 1970). Duratation is in
 * seconds.
 */
public class TimeSpan {
	//~ Static fields/initializers --------------------------------------------

	static final int NULL = (int)Math.pow(2.0, 31.0);

	//~ Instance fields -------------------------------------------------------

	//private double start = NULL;
	//private double end   = -NULL;
	private long start = NULL;
	private long end = -NULL;

	//~ Constructors ----------------------------------------------------------

	/**
	 *
	 */
	public TimeSpan() {
		//do something
	}

	/**
	 *
	 * @param t0
	 * @param tn
	 */
	public TimeSpan(long t0, long tn) {
		set(t0, tn);
	}

	/**
	 *
	 * @param ts
	 */
	public TimeSpan(TimeSpan ts) {
		set(ts.start, ts.end);
	}

	//~ Methods ---------------------------------------------------------------

	/**
	 *
	 */
	public void setStart(long t) {
		start = t;
	}

	/**
	 *
	 * @param t
	 */
	public void setEnd(long t) {
		end = t;
	}

	/**
	 *
	 * @param t0
	 * @param tn
	 */
	public void set(long t0, long tn) {
		start = t0;
		end = tn;
	}

	/**
	 * Set TimeSpan to "null". Note that this does NOT set the pointer to null
	 * but sets the internal values of the TimeSpan object to its own defined
	 * null values.
	 */
	public void setNull() {
		start = NULL;
		end = -NULL;
	}

	/**
	 *
	 * @param t
	 */
	public void setEarliest(long t) {
		start = Math.min(start, t);
	}

	/**
	 *
	 * @param t
	 */
	public void setLatest(long t) {
		end = Math.max(end, t);
	}

	/**
	 *
	 * @param t0
	 */
	public void include(long t0) {
		setEarliest(t0);
		setLatest(t0);
	}

	/**
	 *
	 * @param t0
	 * @param tn
	 */
	public void include(long t0, long tn) {
		setEarliest(t0);
		setLatest(tn);
	}

	/**
	 *
	 * @param ts
	 */
	public void include(TimeSpan ts) {
		setEarliest(ts.getStart());
		setLatest(ts.getEnd());
	}

	/**
	 *
	 * @param ts
	 * @return
	 */
	public boolean contains(TimeSpan ts) {
		return contains(ts.getStart(), ts.getEnd());
	}

	/**
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean contains(long startTime, long endTime) {
		if ((startTime >= start) && (endTime <= end)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param ts
	 * @return
	 */
	public boolean overlaps(TimeSpan ts) {
		return overlaps(ts.getStart(), ts.getEnd());
	}

	/**
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean overlaps(long startTime, long endTime) {
		if ((this.isBefore(startTime) && this.isBefore(endTime))
				|| (this.isAfter(startTime) && this.isAfter(endTime))) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param dt
	 * @return
	 */
	public boolean contains(long dt) {
		if ((dt >= start) && (dt <= end)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param dt
	 * @return
	 */
	public boolean isBefore(long dt) {
		return (end < dt);
	}

	/**
	 *
	 * @param dt
	 * @return
	 */
	public boolean isAfter(long dt) {
		return (start > dt);
	}

	/**
	 *
	 * @return
	 */
	public boolean isValid() {
		if ((start == NULL) || (end == -NULL)) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @return
	 */
	public long getStart() {
		return start;
	}

	/**
	 *
	 * @return
	 */
	public long getEnd() {
		return end;
	}

	/**
	 *
	 * @return
	 */
	public long getDuration() {
		if (isValid()) {
			return (end - start);
		}
		return 0;
	}

	/**
	 *
	 * @return
	 */
	public long size() {
		if (isValid()) {
			return (end - start);
		}
		return 0;
	}

	/**
	 *
	 * @param centerTime
	 */
	public void setCenter(long centerTime) {
		setCenter(centerTime, getDuration());
	}

	/**
	 *
	 * @param centerTime
	 * @param duration
	 */
	public void setCenter(long centerTime, long duration) {
		long halfWidth = duration / 2;

		set(centerTime - halfWidth, centerTime + halfWidth);
	}

	/**
	 *
	 * @param secs
	 */
	public void move(long secs) {
		set(start + secs, end + secs);
	}

	/**
	 *
	 * @param newStart
	 */
	public void moveStart(long newStart) {
		move(newStart - start);
		//longdur = getDuration();
		//set(newStart, newStart + dur);
	}

	/**
	 *
	 * @return
	 */
	public long getCenter() {
		if (isValid()) {
			return start + (getDuration() / 2);
		}
		return 0;
	}

	/**
	 *
	 * @param ts
	 * @return
	 */
	public boolean timeEquals(TimeSpan ts) {
		if ((ts.getStart() == start) && (ts.getEnd() == end)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param ts
	 * @return
	 */
	public boolean nearlyEquals(TimeSpan ts) {
		if (((int)(ts.getStart() * 100) == (int)(start * 100)) && ((int)(ts.getEnd() * 100) == (int)(end * 100))) {
			return true;
		}

		return false;
	}

	/**
	 *
	 * @return
	 */
	public boolean isNull() {
		if ((start == NULL) && (end == -NULL)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 */
	public String toString() {
		return timeToString();
	}

	/**
	 *
	 * @return
	 */
	public String timeToString() {
		GregorianCalendar diffDate = new GregorianCalendar();
		Date time = new Date(end - start);

		diffDate.setTime(time);

		int minutes = diffDate.get(GregorianCalendar.MINUTE);
		int seconds = diffDate.get(GregorianCalendar.SECOND);
		String s = "idle " + minutes + ":" + seconds;

		return (s);
	}
}

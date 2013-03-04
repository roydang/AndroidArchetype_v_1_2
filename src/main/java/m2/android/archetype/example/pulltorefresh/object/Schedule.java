package m2.android.archetype.example.pulltorefresh.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

public class Schedule extends BaseObj implements Parcelable{
	
	private static final String SCHEDULE_ID = "scheduleId";
	private static final String SCHEDULE_TITLE = "title";
	private static final String SCHEDULE_SPOT = "spot";
	private static final String SCHEDULE_SPOT_NAME = "name";
	private static final String SCHEDULE_SPOT_ADDRESS = "address";
	private static final String SCHEDULE_START_AT = "startAt";
	private static final String SCHEDULE_END_AT = "endAt";
	
	public String getScheduleId() {
		return getString(SCHEDULE_ID);
	}

	public void setScheduleId(String scheduleId) {
		put(SCHEDULE_ID, scheduleId);
	}
	
	public String getScheduleTitle() {
		return getString(SCHEDULE_TITLE);
	}

	public void setScheduleTitle(String scheduleTitle) {
		put(SCHEDULE_TITLE, scheduleTitle);
	}
	
	public String getScheduleSpot() {
		return getString(SCHEDULE_SPOT);
	}

	public void setScheduleSpot(String scheduleSpot) {
		put(SCHEDULE_SPOT, scheduleSpot);
	}
	
	public String getScheduleName() {
		return getString(SCHEDULE_SPOT_NAME);
	}

	public void setScheduleName(String scheduleName) {
		put(SCHEDULE_SPOT_NAME, scheduleName);
	}
	
	public String getScheduleSpotAddress() {
		return getString(SCHEDULE_SPOT_ADDRESS);
	}

	public void setScheduleSpotAddress(String scheduleSpotAddress) {
		put(SCHEDULE_SPOT_ADDRESS, scheduleSpotAddress);
	}
	
	public String getScheduleStartAt() {
		return getString(SCHEDULE_START_AT);
	}

	public void setScheduleStartAt(String scheduleStartAt) {
		put(SCHEDULE_START_AT, scheduleStartAt);
	}
	
	public String getScheduleEndAt() {
		return getString(SCHEDULE_END_AT);
	}

	public void setScheduleEndAt(String scheduleEndAt) {
		put(SCHEDULE_END_AT, scheduleEndAt);
	}
	
	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Schedule> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getScheduleId());
		dest.writeString(this.getScheduleTitle());
		dest.writeString(this.getScheduleSpot());
		dest.writeString(this.getScheduleName());
		dest.writeString(this.getScheduleEndAt());
		dest.writeString(this.getScheduleStartAt());
		dest.writeString(this.getScheduleSpotAddress());
	}

	public static final Parcelable.Creator<Schedule> CREATOR = new Creator<Schedule>() {
		public Schedule createFromParcel(Parcel source) {
			Schedule obj = new Schedule();
			obj.setScheduleId(source.readString());
			obj.setScheduleTitle(source.readString());
			obj.setScheduleSpot(source.readString());
			obj.setScheduleName(source.readString());
			obj.setScheduleSpotAddress(source.readString());
			obj.setScheduleStartAt(source.readString());
			obj.setScheduleEndAt(source.readString());
			return obj;
		}

		public Schedule[] newArray(int size) {
			return new Schedule[size];
		}
	};
}

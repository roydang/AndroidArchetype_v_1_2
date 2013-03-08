/*******************************************************************************
 * Copyright 2012 AndroidQuery (tinyeeliu@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Additional Note:
 * 1. You cannot use AndroidQuery's Facebook app account in your own apps.
 * 2. You cannot republish the app as is with advertisements.
 ******************************************************************************/
package m2.android.archetype.example.aquery.fb.obj;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;
/*
 * @(#)FacebookFriendObj.java $$version ${date}
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * @author nhn
 * 
 */
@SuppressWarnings("serial")
public class FacebookFriend extends BaseObj implements Parcelable {
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String URL = "url";
	private static final String IS_CHECKED = "is_checked";
	
	public String getFbFriendId() {
		return getString(ID);
	}

	public void setFbFriendId(String id) {
		put(ID, id);
	}

	public String getFbFriendName() {
		return getString(NAME);
	}

	public void setFbFriendName(String name) {
		put(NAME, name);
	}

	public String getFbFriendFaceUrl() {
		return getString(URL);
	}

	public void setFbFriendFaceUrl(String url) {
		put(URL, url);
	}
	
	public boolean isChecked() {
		return getBoolean(IS_CHECKED);
	}
	
	public void setChecked(boolean isChecked) {
		put(IS_CHECKED, isChecked);
	}
	
	public static Parcelable.Creator<FacebookFriend> getCreator() {
		return CREATOR;
	}
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getFbFriendId());
		dest.writeString(this.getFbFriendName());
		dest.writeString(this.getFbFriendFaceUrl());
		dest.writeInt(this.isChecked() ? 1 : 0);
	}

	public static final Parcelable.Creator<FacebookFriend> CREATOR = new Creator<FacebookFriend>() {
		public FacebookFriend createFromParcel(Parcel source) {
			FacebookFriend obj = new FacebookFriend();
			obj.setFbFriendId(source.readString());
			obj.setFbFriendName(source.readString());
			obj.setFbFriendFaceUrl(source.readString());
			obj.setChecked((source.readInt() == 0) ? false : true);
			return obj;
		}

		public FacebookFriend[] newArray(int size) {
			return new FacebookFriend[size];
		}
	};
}


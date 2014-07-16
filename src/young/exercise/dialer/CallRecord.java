package young.exercise.dialer;

import java.util.Date;

public class CallRecord {
	private static final String ITEM_SEP = System.getProperty("line.separator");
	/**
	 * 来去电号码
	 */
	private String mPhoneNumber;
	/**
	 * 通话时间
	 */
	private String mDate;

	CallRecord(String phoneNumber, String date){
		super();
		this.mPhoneNumber = phoneNumber;
		this.mDate = date;
	}
	CallRecord(){}
	public String getPhoneNumber() {
		return mPhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.mPhoneNumber = phoneNumber;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String date) {
		this.mDate = date;
	}
	
	public String toString() {
		return mPhoneNumber + ITEM_SEP + mDate;
	}
}


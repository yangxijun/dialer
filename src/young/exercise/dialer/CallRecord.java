package young.exercise.dialer;

public class CallRecord {
	private int id;
	/**
	 * 来去电号码
	 */
	private String phoneNumber;
	/**
	 * 通话时间
	 */
	private long date;

	private boolean isNew;

	/**
	 * ͨ通话时长
	 */
	private int duration;

	/**
	 * ͨ记录类型，取值范围有三个Calls.INCOMING_TYPE，Calls.MISSED_TYPE，OUTGOING_TYPE
	 */
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
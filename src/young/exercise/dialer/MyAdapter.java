package young.exercise.dialer;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import young.exercise.dialer.R.layout;
import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract.Contacts.Data;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 
 * @auther yangxijun
 */ 
public class MyAdapter extends BaseAdapter {

	private List<CallRecord> mRecords = new ArrayList<CallRecord>();
	private final Context mContext;

	public MyAdapter(Context context, List<CallRecord> list) {
		this.mContext = context;
		this.mRecords = list;

	}

	public void setData(List<CallRecord> list) {
		if (list != null) {
			mRecords = list;
			notifyDataSetChanged();

		}
	}

	@Override
	public int getCount() {

		return mRecords.size();
	}

	@Override
	public Object getItem(int pos) {

		return mRecords.get(pos);
	}

	@Override
	public long getItemId(int pos) {

		return pos;
	}

	private class ViewHolder {
		TextView phoneNumberView;
		TextView timeView;
	}

	SimpleDateFormat dateformat = new SimpleDateFormat("y年MM月dd日 ");
	SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
	String curDate = dateformat.format(new java.util.Date());

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Date date = new Date(mRecords.get(position).getDate());

		String str = dateformat.format(date) + timeformat.format(date);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.record_item, null);
			holder = new ViewHolder();
			holder.phoneNumberView = (TextView) convertView
					.findViewById(R.id.phoneNumberView);
			holder.timeView = (TextView) convertView
					.findViewById(R.id.timeView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.phoneNumberView.setText(mRecords.get(position).getPhoneNumber());

		if (dateformat.format(date).equals(curDate))
			holder.timeView.setText("Today " + timeformat.format(date));
		else {
			holder.timeView.setText(str);
		}

		return convertView;

		/*
		 * final CallRecord callRecord = (CallRecord)getItem(position);
		 * 
		 * LayoutInflater inflater =
		 * (LayoutInflater)mContext.getSystemService(Context
		 * .LAYOUT_INFLATER_SERVICE); final RelativeLayout recordLayout =
		 * (RelativeLayout)inflater.inflate(R.layout.record_item, null);
		 * 
		 * final TextView phoneNumberView =
		 * (TextView)recordLayout.findViewById(R.id.phoneNumberView);
		 * phoneNumberView.setText(callRecord.getPhoneNumber());
		 * 
		 * final TextView timeView =
		 * (TextView)recordLayout.findViewById(R.id.timeView);
		 * timeView.setText(callRecord.getDate());
		 * 
		 * return recordLayout;
		 */
	}

}

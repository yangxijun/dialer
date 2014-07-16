package young.exercise.dialer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import young.exercise.dialer.R.layout;
import android.R.string;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

	private final List<CallRecord> mRecords = new ArrayList<CallRecord>();
	private final Context mContext;
	
	public MyAdapter(Context context){
		mContext = context;
	}
	
	public void add(CallRecord record){
		mRecords.add(record);
		notifyDataSetChanged();
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
	private static class ViewHolder{
		TextView phoneNumberView;
		TextView timeView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		final CallRecord callRecord = (CallRecord)getItem(position);
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.record_item, null);
			holder = new ViewHolder();
			holder.phoneNumberView = (TextView)convertView.findViewById(R.id.phoneNumberView);
			holder.timeView = (TextView)convertView.findViewById(R.id.timeView);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.phoneNumberView.setText(callRecord.getPhoneNumber());
		holder.timeView.setText(callRecord.getDate());
		
		return convertView;
		
		/*
		final CallRecord callRecord = (CallRecord)getItem(position);
		
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final RelativeLayout recordLayout = (RelativeLayout)inflater.inflate(R.layout.record_item, null);
		
		final TextView phoneNumberView = (TextView)recordLayout.findViewById(R.id.phoneNumberView);
		phoneNumberView.setText(callRecord.getPhoneNumber());
		
		final TextView timeView = (TextView)recordLayout.findViewById(R.id.timeView);
		timeView.setText(callRecord.getDate());
		
		return recordLayout;
		
		*/
	}

	
	

}

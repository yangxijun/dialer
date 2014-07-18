package young.exercise.dialer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DialerActivity extends Activity implements OnClickListener {

	
	private ListView mListView;
	private TextView mTextView;

	private MyAdapter mAdapter;
	private List<CallRecord> list;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_dialer);
		mListView = (ListView)findViewById(R.id.myListView);
		
		mTextView = (TextView)findViewById(R.id.dialertextview);

		
		//initiate the buttons
		findViewById(R.id.num1).setOnClickListener(this);
		findViewById(R.id.num2).setOnClickListener(this);
		findViewById(R.id.num3).setOnClickListener(this);
		findViewById(R.id.num4).setOnClickListener(this);
		findViewById(R.id.num5).setOnClickListener(this);
		findViewById(R.id.num6).setOnClickListener(this);
		findViewById(R.id.num7).setOnClickListener(this);
		findViewById(R.id.num8).setOnClickListener(this);
		findViewById(R.id.num9).setOnClickListener(this);
		findViewById(R.id.num0).setOnClickListener(this);
		findViewById(R.id.numstar).setOnClickListener(this);
		findViewById(R.id.poundkey).setOnClickListener(this);
		findViewById(R.id.delicon).setOnClickListener(this);
		findViewById(R.id.dialicon).setOnClickListener(this);

		list = queryCallRecords();
		
		mAdapter = new MyAdapter(getApplicationContext(), list);
		mListView.setAdapter(mAdapter);
		
		// TODO - try to set the keyboard invisiable
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
	}



	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.num1:
		case R.id.num2:
		case R.id.num3:
		case R.id.num4:
		case R.id.num5:
		case R.id.num6:
		case R.id.num7:
		case R.id.num8:
		case R.id.num9:
		case R.id.num0:
		case R.id.numstar:
		case R.id.poundkey:
			mTextView.setText(mTextView.getText()+ (String)v.getTag());
			break;
		case R.id.delicon:
			delete();
			break;
		case R.id.dialicon:
			dialer(mTextView.getText().toString());
		default:
			break;
		}
		
	}


	private void dialer(String phoneNumber) {

		if (phoneNumber.length() != 0) {
			Uri callUri = Uri.parse("tel:" + phoneNumber);
			Intent intent = new Intent(Intent.ACTION_CALL, callUri);
			startActivity(intent);
			// updateRecord(phoneNumber);
			list = queryCallRecords();

			mAdapter = new MyAdapter(getApplicationContext(), list);
			mListView.setAdapter(mAdapter);
		}
	}
	
	public List<CallRecord> queryCallRecords() {
		List<CallRecord> callRecords = new ArrayList<CallRecord>();

		String[] projection = new String[] { Calls._ID, Calls.NUMBER,
				Calls.DATE, Calls.DURATION, Calls.NEW, Calls.TYPE };
		Cursor cursor = getContentResolver().query(
				CallLog.Calls.CONTENT_URI, projection, null, null,
				CallLog.Calls.DEFAULT_SORT_ORDER);

		try {
			if (null != cursor && cursor.moveToFirst()) {
				int idColumn = cursor.getColumnIndex(Calls._ID);
				int numberColumn = cursor.getColumnIndex(Calls.NUMBER);
				int dateColumn = cursor.getColumnIndex(Calls.DATE);
				int durationColumn = cursor.getColumnIndex(Calls.DURATION);
				int newColumn = cursor.getColumnIndex(Calls.NEW);
				int typeColumn = cursor.getColumnIndex(Calls.TYPE);

				while (cursor.moveToNext()) {
					CallRecord callRecord = new CallRecord();
					callRecord.setId(cursor.getInt(idColumn));
					callRecord.setPhoneNumber(cursor.getString(numberColumn));
					callRecord.setDate(cursor.getLong(dateColumn));
					callRecord.setDuration(cursor.getInt(durationColumn));
					
					if (cursor.getInt(newColumn) == 0) {
						callRecord.setNew(false);
					} else {
						callRecord.setNew(true);
					}

					callRecord.setType(cursor.getInt(typeColumn));
					callRecords.add(callRecord);
				}
			}
		} catch (Exception e) {
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return callRecords;
	}
	
	


	/*
	@SuppressLint("SimpleDateFormat")
	private void updateRecord(String phoneNumber) {
		
		// clean the textview
		mTextView.setText("");
		
		CallRecord callRecord = new CallRecord();
		callRecord.setPhoneNumber(phoneNumber);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String date = simpleDateFormat.format(new java.util.Date());
		callRecord.setDate(date);
		mAdapter.add(callRecord);

	}*/


	private void delete() {
		
		if(mTextView.getText().length()!=0){
			CharSequence charSequence = mTextView.getText();
			charSequence = charSequence.subSequence(0, charSequence.length()-1);
			mTextView.setText(charSequence);
		}
	}
	
	/*
	
	@Override
	public void onResume() {
		super.onResume();


		if (mAdapter.getCount() == 0)
			loadItems();
	}

	@Override
	protected void onPause() {
		super.onPause();


		saveItems();

	}



    private void loadItems() {
    	BufferedReader reader = null;
		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			reader = new BufferedReader(new InputStreamReader(fis));

			String phoneNumber = null;
			String date = null;

			while (null != (phoneNumber = reader.readLine())) {
				date = reader.readLine();
				mAdapter.add(new CallRecord(phoneNumber, date));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	private void saveItems() {
		
		PrintWriter writer = null;
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					fos)));

			for (int idx = 0; idx < mAdapter.getCount(); idx++) {

				writer.println(mAdapter.getItem(idx));

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
			}
		}
	}
	
	*/

}

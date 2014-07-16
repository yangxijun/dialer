package young.exercise.dialer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class DialerActivity extends Activity implements OnClickListener {

	private static final String FILE_NAME = "dialer.txt";
	
	private ListView mListView;
	private TextView mTextView;
	private LinearLayout mNum1;
	private LinearLayout mNum2;
	private LinearLayout mNum3;
	private LinearLayout mNum4;
	private LinearLayout mNum5;
	private LinearLayout mNum6;
	private LinearLayout mNum7;
	private LinearLayout mNum8;
	private LinearLayout mNum9;
	private LinearLayout mNum0;
	private LinearLayout mNumStar;
	private LinearLayout mPoundkey;
	
	private ImageView mDialIcon;
	private ImageView mDelIcon;

	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_dialer);
		mListView = (ListView)findViewById(R.id.myListView);
		
		mTextView = (TextView)findViewById(R.id.dialertextview);

		
		//initiate the buttons
		mNum1 = (LinearLayout)findViewById(R.id.num1);
		mNum2 = (LinearLayout)findViewById(R.id.num2);
		mNum3 = (LinearLayout)findViewById(R.id.num3);
		mNum4 = (LinearLayout)findViewById(R.id.num4);
		mNum5 = (LinearLayout)findViewById(R.id.num5);
		mNum6 = (LinearLayout)findViewById(R.id.num6);
		mNum7 = (LinearLayout)findViewById(R.id.num7);
		mNum8 = (LinearLayout)findViewById(R.id.num8);
		mNum9 = (LinearLayout)findViewById(R.id.num9);
		mNum0 = (LinearLayout)findViewById(R.id.num0);
		mNumStar = (LinearLayout)findViewById(R.id.numstar);
		mPoundkey = (LinearLayout)findViewById(R.id.poundkey);
		
		
		mDialIcon = (ImageView)findViewById(R.id.dialicon);
		mDelIcon =(ImageView)findViewById(R.id.delicon);
		
		mNum1.setOnClickListener(this);
		mNum2.setOnClickListener(this);
		mNum3.setOnClickListener(this);
		mNum4.setOnClickListener(this);
		mNum5.setOnClickListener(this);
		mNum6.setOnClickListener(this);
		mNum7.setOnClickListener(this);
		mNum8.setOnClickListener(this);
		mNum9.setOnClickListener(this);
		mNum0.setOnClickListener(this);
		mNumStar.setOnClickListener(this);
		mPoundkey.setOnClickListener(this);
		mDialIcon.setOnClickListener(this);
		mDelIcon.setOnClickListener(this);	

		
		mAdapter = new MyAdapter(getApplicationContext());
		mListView.setAdapter(mAdapter);
		
	}



	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.num1:
			mTextView.setText(mTextView.getText()+"1");
			break;
		case R.id.num2:
			mTextView.setText(mTextView.getText()+"2");
			break;
		case R.id.num3:
			mTextView.setText(mTextView.getText()+"3");
			break;
		case R.id.num4:
			mTextView.setText(mTextView.getText()+"4");
			break;
		case R.id.num5:
			mTextView.setText(mTextView.getText()+"5");
			break;
		case R.id.num6:
			mTextView.setText(mTextView.getText()+"6");
			break;
		case R.id.num7:
			mTextView.setText(mTextView.getText()+"7");
			break;
		case R.id.num8:
			mTextView.setText(mTextView.getText()+"8");
			break;
		case R.id.num9:
			mTextView.setText(mTextView.getText()+"9");
			break;
		case R.id.num0:
			mTextView.setText(mTextView.getText()+"0");
			break;
		case R.id.numstar:
			mTextView.setText(mTextView.getText()+"*");
			break;
		case R.id.poundkey:
			mTextView.setText(mTextView.getText()+"#");
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
			updateRecord(phoneNumber);
		}
	}


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

	}



	private void delete() {
		
		if(mTextView.getText().length()!=0){
			CharSequence charSequence = mTextView.getText();
			charSequence = charSequence.subSequence(0, charSequence.length()-1);
			mTextView.setText(charSequence);
		}
	}
	
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
	
	

}

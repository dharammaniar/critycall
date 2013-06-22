package com.dharam.critycall;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DetailListAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	public static List<String> title;
	public static List<String> detail;
	private int mViewResourceId;

	public DetailListAdapter(Context context, int textViewResourceId, List<String> objects1, List<String> objects2) {
		super(context, textViewResourceId, objects1);
		title = objects1;
		detail = objects2;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mViewResourceId = textViewResourceId;
	}

	@Override
	public int getCount() {
		return title.size();
	}

	@Override
	public String getItem(int position) {
		return title.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = mInflater.inflate(mViewResourceId, null);

		TextView titletext = (TextView) convertView.findViewById(R.id.list_result_tv_title);
		TextView detailtext = (TextView) convertView.findViewById(R.id.list_result_tv_detail);

		titletext.setText(title.get(position));
		detailtext.setText(detail.get(position));
		return convertView;
	}

}

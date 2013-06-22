package com.dharam.critycall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchResultListAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	public static String[] id;
	private int mViewResourceId;

	public SearchResultListAdapter(Context context, int textViewResourceId, String[] objects) {
		super(context, textViewResourceId, objects);
		id = objects;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mViewResourceId = textViewResourceId;
	}

	@Override
	public int getCount() {
		return id.length;
	}

	@Override
	public String getItem(int position) {
		return id[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("Position = " + position);
		convertView = mInflater.inflate(mViewResourceId, null);
		TextView name = (TextView) convertView.findViewById(R.id.list_search_result_tv_name);
		name.setText(SearchResultActivity.name[position]);

		TextView first = (TextView) convertView.findViewById(R.id.list_search_result_tv_1);
		TextView second = (TextView) convertView.findViewById(R.id.list_search_result_tv_2);
		String address[] = SearchResultActivity.address[position].split(",");

		if (SearchActivity.selection.equals("Doctor") && !(SearchResultActivity.qualification[position].length()==0)) {

			first.setText(SearchResultActivity.qualification[position]);

			second.setText(address[address.length - 1].trim() + ", " + SearchResultActivity.location.get(position));
			second.setVisibility(0);
		} else {
			first.setText(address[address.length - 1].trim() + ", " + SearchResultActivity.location.get(position));
			second.setHeight(0);
		}

		return convertView;
	}

}

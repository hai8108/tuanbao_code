package com.haitian.adapter;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class ArrayAdapter<T> extends BaseAdapter {

	// 数据
	protected ArrayList<T> mObjects;
	protected LayoutInflater mInflater;

	public ArrayAdapter(final Context ctx, final ArrayList<T> l) {
		mObjects = l == null ? new ArrayList<T>() : l;
		mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	protected  void setArrayList(ArrayList<T> list){
		mObjects = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return mObjects.size();
	}

	@Override
	public T getItem(int position) {
		return mObjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void add(T item) {
		this.mObjects.add(item);
	}

	/**
	 * Adds the specified items at the end of the array.
	 * 
	 * @param items The items to add at the end of the array.
	 */
	public void addAll(T... items) {
		ArrayList<T> values = this.mObjects;
		for (T item : items) {
			values.add(item);
		}
		this.mObjects = values;
	}

	/**
	 * 
	 * @param collection
	 */
	public void addAll(Collection<? extends T> collection) {
		mObjects.addAll(collection);
	}
	/**
	 * 
	 * @return
	 */
	public final ArrayList<T> getAll() {
		return mObjects;
	}
	/**
	 * Remove all elements from the list.
	 */
	public void clear() {
		mObjects.clear();
	}
}

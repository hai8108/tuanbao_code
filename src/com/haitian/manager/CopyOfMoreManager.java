package com.haitian.manager;

import java.util.ArrayList;

import android.os.Message;
import android.widget.ViewAnimator;

import com.haitian.dc.CopyOfMoreDC;
import com.haitian.engine.CopyOfMoreEngine;
import com.haitian.main.R;
import com.haitian.model.CityInfoModel;
import com.haitian.pivot.BaseActivity;
import com.haitian.pivot.BaseManager;

public class CopyOfMoreManager extends BaseManager {

	CopyOfMoreDC moreDC;
	MoreManager mMoreManager;
	CopyOfMoreEngine mCopyOfMoreEngine;
	public ArrayList<CityInfoModel> mCityInfoList = new ArrayList<CityInfoModel>();

	public CopyOfMoreManager(BaseActivity c) {
		super(c);
		if (mCopyOfMoreEngine == null) {
			mCopyOfMoreEngine = new CopyOfMoreEngine(this);
		}
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			if (mCopyOfMoreEngine == null) {
				mCopyOfMoreEngine = new CopyOfMoreEngine(this);
			}
			mCopyOfMoreEngine.getCityDetailRequest();
			break;
		case 3:
			break;
		case 6:
			showToast((String) msg.obj);
			break;
		default:
			break;
		}
	}

	public void setCityInfoList(ArrayList<CityInfoModel> mCityInfoList) {
		if (mCityInfoList != null && mCityInfoList.size() > 0) {
			this.mCityInfoList = mCityInfoList;
		}
	}

	@Override
	public void onClicked(int id) {
		switch (id) {
		case R.id.btn_1:
			// mMoreManager = null;
			// mMoreManager = new MoreManager(context);
			// context.setSubManager(mMoreManager);
			break;
		default:
			break;
		}
	}

	@Override
	public ViewAnimator getMainDC() {
		if (moreDC == null) {
			moreDC = new CopyOfMoreDC(context, R.layout.copy_more, this);
			dcEngine.setMainDC(moreDC);
		}
		sendEmptyMessage(1);
		return super.getMainDC();
	}

	@Override
	public ViewAnimator getBackMainDC() {
		if (moreDC == null) {
			moreDC = new CopyOfMoreDC(context, R.layout.copy_more, this);
		}
		dcEngine.setAnimaMainDC(moreDC);
		return super.getMainDC();
	}
}

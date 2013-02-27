package com.haitian.engine;

import java.util.ArrayList;

import org.json.audiocn.JSONArray;
import org.json.audiocn.JSONException;

import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Message;

import com.haitian.manager.CopyOfMoreManager;
import com.haitian.model.CityInfoModel;
import com.haitian.pivot.BaseEngine;
import com.haitian.utils.Json;
import com.haitian.utils.LogInfo;

/**
 * 商城首页
 * 
 * @author Administrator
 * 
 */
public class CopyOfMoreEngine extends BaseEngine {
	CopyOfMoreManager manager;
	String errorCode = null;

	public CopyOfMoreEngine(CopyOfMoreManager manager) {
		super(manager);
		this.manager = manager;
	}

	public void getCityDetailRequest() {
		try {
			// http://119.254.69.232/mcity/city
			new getCityDetailTask().execute();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// ===================================================================
	/**
	 * @author Administrator
	 * 
	 */
	class getCityDetailTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			manager._showLoading();
		};

		@Override
		protected String doInBackground(String... params) {
			String result = httpRequestThisThread(1, "");
			getCityResultParse(result);
			return httpRequestThisThread(1, "");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			manager.dismissLoading();
		}
	}

	private void getCityResultParse(String response) {
		try {
			LogInfo.LogOut("getCityResultParse");
			int count = 0;
			if (response != null && response.length() > 10) {
				JSONArray jsonArray = new JSONArray(response);
				count = jsonArray.length();
				LogInfo.LogOut("haitian", "count = " + count);
				Json json = null;
				ArrayList<CityInfoModel> mCityInfoList = new ArrayList<CityInfoModel>();
				CityInfoModel mCityInfoModel = null;
				for (int i = 0; i < count; i++) {
					json = new Json(jsonArray.getString(i));
					mCityInfoModel = new CityInfoModel();
					mCityInfoModel.city_id = json.getString("city_id");
					mCityInfoModel.city_name = json.getString("city_name");
					mCityInfoModel.isvisible = json.getString("isvisible");
					mCityInfoModel.spelling = json.getString("spelling");
					mCityInfoModel.key_word = json.getString("key_word");
					mCityInfoList.add(mCityInfoModel);
					LogInfo.LogOut("haitian", mCityInfoModel.toString());
				}

			} else {

			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Message msg = new Message();
			msg.what = 6;
			msg.obj = "网络连接失败！";
			manager.sendMessageDelayed(msg, 0);
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
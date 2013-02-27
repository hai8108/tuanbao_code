package com.haitian.main;

import java.io.File;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.haitian.engine.DBEngine;
import com.haitian.manager.InitManager;
import com.haitian.manager.MainManager;
import com.haitian.manager.MoreManager;
import com.haitian.pivot.BaseActivity;
import com.haitian.utils.Utils;
import com.haitian.widget.TlcyDialog.TlcyDialogListener;

public class Application extends BaseActivity implements TlcyDialogListener {
	public static Application application;
	long timeForAnimator;
	public static MainManager mainManager;
	public static MoreManager moreManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = this;

		Configs.initTypeAndVsersion(application);
		InitManager initManager = new InitManager(application);
		setContentView(initManager.getMainDC(), new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		currentManager = initManager;

		mainManager = new MainManager(application);
		moreManager = new MoreManager(application);
		timeForAnimator = System.currentTimeMillis();
		if (Utils.isSDCard() && Utils.isSDCardFree()) {
			onAsyncTaskCreate task = new onAsyncTaskCreate();
			task.execute();
		} else {
			if (!Utils.isSDCard()) {
				mainManager.showAlert("没有SD卡的情况该如何处理?");
			} else {
				onAsyncTaskCreate task = new onAsyncTaskCreate();
				task.execute();
				mainManager.showAlert("SD卡余量不足的情况该如何处理?");
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!mainManager.getMenuVisiblity()) {// 播放界面不需要底部菜单
				mainManager.setBottomLayoutVisibility(View.VISIBLE);
			}
			if (currentManager != null && !currentManager.backOnKeyDown()) {
				if (managerStack.size() > 0) {
					managerStack.pop();
				}
				if (managerStack.size() > 0) {
					if (managerStack.size() > 1) {// 二级或者三级四级manger
						setAnimaSubManager(managerStack.pop());
					} else {
						setMainManager(managerStack.pop());
					}
				} else {
					managerStack.push(currentManager);
					mainManager.showAlert(application.getString(R.string.tip), application.getString(R.string.exitTip),
							application.getString(R.string.OK), application.getString(R.string.CANCEL), this, null);
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation != Configs.nowOrientation) {
			Configs.lastOrientation = Configs.nowOrientation;
			Configs.nowOrientation = newConfig.orientation;
		}
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DBEngine.close();
		System.exit(0);

	}

	class onAsyncTaskCreate extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {

			File file = new File(Configs.tlcyMusicPath);
			if (!file.isDirectory()) {
				file.delete();
				file.mkdir();
			}
			file = new File(Configs.xhpmLrcPath);
			if (!file.isDirectory()) {
				file.delete();
				file.mkdir();
			}
			file = new File(Configs.xhpmMusicPath);
			if (!file.isDirectory()) {
				file.delete();
				file.mkdir();
			}
			file = new File(Configs.tlcyCoPath);
			if (!file.isDirectory()) {
				file.delete();
				file.mkdir();
			}
			file = new File(Configs.tlcyRoPath);
			if (!file.isDirectory()) {
				file.delete();
				file.mkdir();
			}
			file = new File(Configs.tlcyOriginPath);
			if (!file.isDirectory()) {
				file.delete();
				file.mkdir();
			}

			DBEngine.create();
			DBEngine.close();
			long time = System.currentTimeMillis() - timeForAnimator;

			try {
				time = Configs.TIMEFORANIMATOR - time;
				time = time < 0 ? 0 : time;
				time = time > Configs.TIMEFORANIMATOR ? Configs.TIMEFORANIMATOR : time;
				Thread.sleep(time);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			enterMain();
		}
	}

	private void enterMain() {
		setContentView(mainManager.getLayout());
		dcEngineContener = mainManager.getContainer();
		currentManager = mainManager;
		setMainManager(moreManager);
	}

	@Override
	public void onClick() {
		finish();
	}
}
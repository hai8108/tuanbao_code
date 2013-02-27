package com.haitian.pivot;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.haitian.main.Application;
import com.haitian.widget.TlcyDialog.TlcyDialogListener;

public abstract class BaseDC extends FrameLayout implements OnClickListener, OnTouchListener {
	/**
	 * 此DC所属manger，每一个DC都由manager管理
	 */
	public BaseManager manager;
	/**
	 * android上下文
	 */
	public Context context;

	public int ScreenWidth;
	public int ScreenHeight;
	private Toast mToast;
	/**
	 * 输入法管理器
	 */
	InputMethodManager inputMethodManager;
	/**
	 * 防止短时间内多次触摸点击
	 */
	public long l = 0;

	int orientation;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param layoutId
	 *            xml对应的id
	 * @param manager
	 *            所属manger
	 */
	public BaseDC(Context context, int layoutId, BaseManager manager) {
		super(context);
		this.context = context;
		this.manager = manager;
		if (layoutId != -1) {
			inflate(context, layoutId, this);
		}
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		ScreenHeight = wm.getDefaultDisplay().getHeight();
		ScreenWidth = wm.getDefaultDisplay().getWidth();
		inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		orientation = getResources().getConfiguration().orientation;
	}

	/**
	 * 构造函数，简易DC，无xml对应文件
	 * 
	 * @param context
	 *            上下文
	 * @param manager
	 *            所属manger
	 */
	public BaseDC(Context context, BaseManager manager) {
		this(context, -1, manager);
	}

	/**
	 * 在子类中onItemClick等使用
	 * 
	 * @author haitian
	 */
	public boolean notAnimition() {
		hideInput();
		if (Math.abs(System.currentTimeMillis() - l) > 500) {
			l = System.currentTimeMillis();
			return manager.dcEngine.notAnimition();
		} else {
			return false;
		}
	}

	/**
	 * 触摸事件插入隐藏软键盘代码
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.gc();
		hideInput();
		return super.onTouchEvent(event);
	}

	/**
	 * 在动画期间拦截click事件
	 */
	public void onClick(View v) {
		if (notAnimition()) {
			onClicked(v);
		}
	}

	/**
	 * 响应各种点击事件,默认调用manager.onClicked(v.getId())进行处理,可以在子类中重写
	 */
	public void onClicked(View v) {
		manager.onClicked(v.getId());
	}

	/**
	 * 无论点击进入,或者别的DC后退进入此DC,都会触发此方法,不要放置费时的动作
	 * 
	 * @author haitian
	 */
	public void onShow() {
		if (Application.application.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			Application.application.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		requestFocus();
		setEnabled(true);
		setClickable(true);
	}

	/**
	 * 检查DC生成后,是否发生过屏幕旋转
	 */
	public void checkOrientation() {
		if (getResources().getConfiguration().orientation != orientation) {
			orientation = getResources().getConfiguration().orientation;
			onConfigurationChanged(getResources().getConfiguration());
		}
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		orientation = newConfig.orientation;
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideInput() {
		if (inputMethodManager.isActive()) {
			inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
		}

		// inputMethodManager.hideSoftInputFromWindow(getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS,
		// resultReceiver)
	}

	/**
	 * 显示小提示
	 */
	public void showToast(String text) {
		if (mToast != null) {
			mToast.cancel();
		} else {
			mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		}
		mToast.setText(text);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.setDuration(1);
		mToast.show();
	}

	/**
	 * 显示提示框
	 */
	public Dialog showAlert(String text) {
		return manager.showAlert(text);
	}

	/**
	 * 显示确定框
	 */
	public Dialog showAlert(String titile, String text, String ok, String cancel, TlcyDialogListener okListener,
			TlcyDialogListener cancelListener) {
		return manager.showAlert(titile, text, ok, cancel, okListener, cancelListener);
	}

	/**
	 * 金立，魅族等手机在不显示时无法弹出dialog 此方法自动判断当前activity是否正在显示，并作出直接显示还是等显示再在弹出dialog
	 */
	public Dialog showAlert(String text, TlcyDialogListener okListener, TlcyDialogListener cancelListener) {
		return manager.showAlert(text, okListener, cancelListener);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.gc();
		hideInput();
		return false;
	}
}

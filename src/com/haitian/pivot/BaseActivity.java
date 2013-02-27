package com.haitian.pivot;

import java.util.Stack;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.haitian.utils.Utils;

/**
 * 每一个Activity为一个功能模块，其内部存放处理不同事务的manager
 */
public abstract class BaseActivity extends Activity {
	/**
	 * 对manager的顺序管理
	 */
	public Stack<BaseManager> managerStack = new Stack<BaseManager>();
	/**
	 * 此控件为主显示区域
	 */
	public RelativeLayout dcEngineContener;

	/**
	 * 与此activity绑定的服务接口，多进程时使用
	 */
	// private IServiceBinder REMOTE_SERVICE;
	// private ServiceConnection s = new ServiceConnection() {
	// @Override
	// public void onServiceDisconnected(ComponentName name) {
	// REMOTE_SERVICE=null;
	// }
	// @Override
	// public void onServiceConnected(ComponentName name, IBinder service) {
	// REMOTE_SERVICE = IServiceBinder.Stub.asInterface(service);
	// try {
	// REMOTE_SERVICE.registerCallback(mCallActivity,getClassName());//此处为回调，使用getClass()获得的是BaseActivity的class，所以使用子类实现的getClassName方法
	// } catch (RemoteException e) {
	// e.printStackTrace();
	// }
	// }
	// };
	// private ICallActivity mCallActivity=new ICallActivity.Stub() {
	// @Override
	// public String doAction(String json) throws RemoteException {
	// return callFromRemote(json);
	// }
	// };
	/**
	 * 多进程时，调用别的进程，参数以json形式传递，返回json形式字符串
	 */
	public String remoteDoAction(String json) {
		// if(REMOTE_SERVICE!=null){
		// try {
		// return REMOTE_SERVICE.doAction(json);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		return null;
	}

	/**
	 * 在子类实现。多进程时，被别的进程(一般为主进程)回调，参数以json形式传递，返回json形式字符串 此项目中不需要进程间通信,子类无需处理
	 */
	public String callFromRemote(String action) {
		return null;
	}

	/**
	 * 返回子类类型，判断是否向远程service注册了回调函数，防止重复注册 一般返回getClass().getName()即可
	 */
	// private String getClassName(){
	// return getClass().getName();
	// }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}

	/**
	 * 绑定服务，已封装
	 */
	// public boolean bindService(Intent service){
	// return super.bindService(service,s, Context.BIND_AUTO_CREATE);
	// }

	private Dialog tempDialog;// 处理连续快速点击出现多个对话框的情况
	private Dialog toDialog = null;// 金立，魅族等手机在不显示时无法弹出alertdialog
	private boolean isShowing = true;// 当前activity是否显示中

	/**
	 * 金立，魅族等手机在不显示时无法弹出dialog 此方法自动判断当前activity是否正在显示，并作出直接显示还是等显示后再弹出dialog
	 * 
	 * @param dialog
	 */
	public Dialog showDialog(Dialog dialog) {
		if (tempDialog != null && tempDialog.isShowing()) {
			tempDialog.dismiss();
		}
		if (isShowing) {
			tempDialog = dialog;
			tempDialog.show();
		} else {
			toDialog = dialog;
		}
		return dialog;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isShowing = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		Utils.updateNotify(this, getClass());// 此处getClass为子类的class
		isShowing = true;
		if (toDialog != null) {
			toDialog.show();
			toDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		Utils.updateNotify(this, null);// 取消标题
		// unbindRemoteService();
		super.onDestroy();
	}

	/**
	 * 屏蔽过快的按键点击
	 */
	long lastKeyDown = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (currentManager != null) {
				if (Math.abs(System.currentTimeMillis() - lastKeyDown) > 500) {
					lastKeyDown = System.currentTimeMillis();
					currentManager.back();
					return true;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 如果此模块不在后台运行提供服务，请在onDestroy中调用此方法 调用Syste.exit(0)之前必须调用此方法
	 * 
	 */
	// public void unbindRemoteService() {
	// if(REMOTE_SERVICE!=null){
	// try {
	// REMOTE_SERVICE.unRegisterCallback(mCallActivity,getClassName());
	// unbindService(s);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }

	/**
	 * 返回程序主界面
	 */
	public void leaveToMain() {

	}

	/**
	 * 当前显示的manager
	 */
	public BaseManager currentManager;

	public boolean isNowManager(BaseManager manager) {
		if (managerStack != null && managerStack.size() > 0 && managerStack.peek() == manager) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 子类重写，用于换皮肤
	 */
	public int getResid(int id) {
		return id;
	}

	/**
	 * 设置此模块的主manager
	 */
	public void setMainManager(BaseManager manager) {
		managerStack.clear();
		managerStack.push(manager);
		dcEngineContener.removeAllViews();
		dcEngineContener.addView(manager.getMainDC(), new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
		currentManager = manager;
	}

	/**
	 * 设置此模块的辅manager
	 */
	public void setSubManager(BaseManager manager) {
		if (managerStack.size() > 0 && managerStack.peek() != manager) {
			managerStack.push(manager);
			dcEngineContener.removeAllViews();
			dcEngineContener.addView(manager.getMainDC(), new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
		}
		currentManager = manager;
	}

	/**
	 * 设置此模块的辅manager
	 */
	public void setAnimaSubManager(BaseManager manager) {
		if (managerStack.size() > 0 && managerStack.peek() != manager) {
			managerStack.push(manager);
			dcEngineContener.removeAllViews();
			dcEngineContener.addView(manager.getBackMainDC(), new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
		}
		currentManager = manager;
	}
}

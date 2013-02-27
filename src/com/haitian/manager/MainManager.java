package com.haitian.manager;

import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.haitian.dc.MainDC;
import com.haitian.main.Application;
import com.haitian.main.R;
import com.haitian.pivot.BaseActivity;
import com.haitian.pivot.BaseManager;

public class MainManager extends BaseManager {
	public final static int MSG_WHAT_HAVE_NEW_INFO = 0;
	MainDC mainDC;

	public MainManager(BaseActivity c) {
		super(c);
		mainDC = new MainDC(context, R.layout.main, this);
	}

	@Override
	public void handleMessage(Message msg) {
	}

	@Override
	public void onClicked(int id) {
		switch (id) {
		case R.id.menu_news:
			Application.application.setMainManager(Application.moreManager);
			break;
		case R.id.menu_shop:// 商城被点击
			break;
		case R.id.menu_book_shelf:
			break;
		case R.id.menu_multi_media:
			break;
		case R.id.menu_more:
			break;
		default:
			break;
		}
	}

	/**
	 * 返回主界面布局
	 */
	public View getLayout() {
		return mainDC;
	}

	public RelativeLayout getContainer() {
		return (RelativeLayout) mainDC.findViewById(R.id.content_container);
	}

	public void setBottomLayoutVisibility(int visible) {
		mainDC.setMenuLayoutVisibility(visible);

	}

	public boolean getMenuVisiblity() {
		return mainDC.getMenuVisiblity();
	}

}

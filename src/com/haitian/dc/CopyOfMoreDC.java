package com.haitian.dc;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.TextView;

import com.haitian.main.R;
import com.haitian.pivot.BaseDC;
import com.haitian.pivot.BaseManager;

public class CopyOfMoreDC extends BaseDC {
	TextView title;
	Button button1;

	public CopyOfMoreDC(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		init();
	}

	public void init() {
		title = (TextView) findViewById(R.id.title);
		title.setText(R.string.More);
		button1 = (Button) findViewById(R.id.btn_1);

		button1.setOnClickListener(this);
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		/**
		 * 在横竖屏切换时,每个DC都会被回调此方法 有两种方式更新界面
		 */

		/**
		 * 方式一,重新排版,适合更改范围较小的界面 优点:可无缝切换,如列表滑动位置等都不会变 缺点:代码量大
		 */
		/**
		 * 方式二,重新生成界面,适合更改范围很大的界面 优点:代码量小 缺点:界面是重新生成,原来的用户操作比较难以保留
		 */
		super.onConfigurationChanged(newConfig);
	}
}

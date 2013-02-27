package com.haitian.dc;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.haitian.main.R;
import com.haitian.pivot.BaseDC;
import com.haitian.pivot.BaseManager;

public class MoreDC extends BaseDC {
	TextView title;
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	TextView text_view;

	public MoreDC(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		init();
	}

	public void init() {
		title = (TextView) findViewById(R.id.title);
		text_view = (TextView) findViewById(R.id.text_view);
		title.setText(R.string.More);
		button1 = (Button) findViewById(R.id.btn_1);
		button2 = (Button) findViewById(R.id.btn_2);
		button3 = (Button) findViewById(R.id.btn_3);
		button4 = (Button) findViewById(R.id.btn_4);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);

		String[] speak = { "在横竖屏切换时,每个DC都会被回调此方法 ", "有两种方式更新界面" };
		if (speak.length > 1) {
			text_view.setText(Html.fromHtml("<font size=\"3\" color=\"black\">" + speak[0]
					+ "</font><font size=\"3\" color=\"#C0C0C0\">" + speak[1] + "</font>"));
		} else {
			text_view.setText(Html.fromHtml("<font size=\"3\" color=\"black\">" + speak[0] + "</font>"));
		}
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

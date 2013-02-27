package com.haitian.manager;

import android.os.Message;
import android.widget.ViewAnimator;

import com.haitian.dc.MoreDC;
import com.haitian.main.R;
import com.haitian.pivot.BaseActivity;
import com.haitian.pivot.BaseManager;

public class MoreManager extends BaseManager {

	MoreDC moreDC;
	CopyOfMoreManager mCopyOfMoreManager;

	public MoreManager(BaseActivity c) {
		super(c);
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			break;
		case 3:
			break;
		default:
			break;
		}
	}

	@Override
	public void onClicked(int id) {
		switch (id) {
		case R.id.btn_1:
			mCopyOfMoreManager = null;
			mCopyOfMoreManager = new CopyOfMoreManager(context);
			context.setSubManager(mCopyOfMoreManager);
			break;
		case R.id.btn_2:
			break;
		case R.id.btn_3:
			break;
		case R.id.btn_4:
			break;
		case 4:// Message manager
			break;
		default:
			break;
		}
	}

	@Override
	public ViewAnimator getMainDC() {
		if (moreDC == null) {
			moreDC = new MoreDC(context, R.layout.more, this);
			dcEngine.setMainDC(moreDC);
		}
		return super.getMainDC();
	}

	@Override
	public ViewAnimator getBackMainDC() {
		if (moreDC == null) {
			moreDC = new MoreDC(context, R.layout.more, this);
		}
		dcEngine.setAnimaMainDC(moreDC);
		return super.getMainDC();
	}
}

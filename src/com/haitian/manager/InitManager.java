package com.haitian.manager;

import android.os.Message;

import com.haitian.dc.InitDC;
import com.haitian.pivot.BaseActivity;
import com.haitian.pivot.BaseManager;

public class InitManager extends BaseManager {
    InitDC initDC;
    public InitManager(BaseActivity c) {
        super(c);
        initDC = new InitDC(context,this);
        dcEngine.setInitDC(initDC);
    }

    @Override
    public void handleMessage(Message msg) {

    }
}

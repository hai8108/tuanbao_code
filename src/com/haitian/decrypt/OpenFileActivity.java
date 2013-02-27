package com.haitian.decrypt;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.haitian.main.Application;
import com.haitian.main.R;
import com.haitian.utils.LogInfo;
import com.haitian.widget.TlcyDialog;

public class OpenFileActivity extends Activity {
    String file, fileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            file = getIntent().getStringExtra("filepath");
            fileType = getIntent().getStringExtra("filetype");
        } catch (Exception e) {
            file = null;
            fileType = null;
        }
        if (file == null) {
            Toast.makeText(this, "空文件", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        LogInfo.LogOut("PATH:" + file);
        if (fileType.regionMatches(true, 0, ".pdf", 0, 4) || fileType.regionMatches(true, 0, ".pep", 0, 4)) {
            // local app
            String type = "application/pdf";
            File f = new File(file);
            Intent intent1 = new Intent();
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置intent的Action属性
            intent1.setAction(Intent.ACTION_VIEW);
            // 获取文件file的MIME类型

            // 设置intent的data和Type属性。
            intent1.setDataAndType(/* uri */Uri.fromFile(f), type);
            try {
                // 跳转
                startActivityForResult(intent1, 1);
            } catch (ActivityNotFoundException e) {
                Application.application.showDialog(new TlcyDialog(Application.application).setTitle(Application.application.getString(R.string.tip))
                        .setMessage("您尚未安装pdf阅读器，无法开启该文档，请您安装后再试").setOnlyOkPositiveMethod(Application.application.getString(R.string.OK)));
            }
        } else if (fileType.regionMatches(true, 0, ".epub", 0, 5)) {
            String type = "application/epub+zip";
            File f = new File(file);
            Intent intent1 = new Intent();
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置intent的Action属性
            intent1.setAction(Intent.ACTION_VIEW);
            // 获取文件file的MIME类型
            // intent1.setData(file);
            // 设置intent的data和Type属性。
            intent1.setDataAndType(Uri.fromFile(f), type);
            try {
                // 跳转
                startActivityForResult(intent1, 1);
            } catch (ActivityNotFoundException e) {
                Application.application.showDialog(new TlcyDialog(Application.application).setTitle(Application.application.getString(R.string.tip))
                        .setMessage("您尚未安装epub阅读器，无法开启该文档，请点击“系统设置”的“版本检查及更新”更新阅读器或手动下载安装。").setOnlyOkPositiveMethod(Application.application.getString(R.string.OK)));
            }
        } else if (fileType.regionMatches(true, 0, ".mp4", 0, 4) || fileType.regionMatches(true, 0, ".3gp", 0, 4)) {
            // String type="video/3gpp";
            // String type="video/mp4";
            String type;
            String filetype;
            if (fileType.regionMatches(true, 0, ".mp4", 0, 4)) {
                type = "video/mp4";
                filetype = "mp4";
            } else {
                type = "video/3gpp";
                filetype = "3gp";
            }
            File f = new File(file);
            Intent intent1 = new Intent();
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置intent的Action属性
            intent1.setAction(Intent.ACTION_VIEW);
            // 获取文件file的MIME类型
            // intent1.setData(file);
            // 设置intent的data和Type属性。
            intent1.setDataAndType(Uri.fromFile(f), type);
            try {
                // 跳转
                startActivityForResult(intent1, 1);
            } catch (ActivityNotFoundException e) {
                Application.application.showDialog(new TlcyDialog(Application.application).setTitle(Application.application.getString(R.string.tip))
                        .setMessage("您尚未安装" + filetype + "播放器，无法播放该视频，请您安装后再试").setOnlyOkPositiveMethod(Application.application.getString(R.string.OK)));
            }
        } else {
            Toast.makeText(this, "文件格式暂不支持（格式为" + file.substring(file.lastIndexOf("."), file.length()) + "）", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            new Thread(){
                public void run() {
                    try {
                        //Thread.sleep(20000);
                        //File f = new File(file);
                        //f.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

package com.haitian.main;

import org.json.JSONStringer;

import android.app.Activity;
import android.content.Context;

import com.haitian.utils.Json;
import com.haitian.utils.LogInfo;
import com.haitian.utils.Utils;

public class Configs {
	public final static long TIMEFORANIMATOR = 2000;
	/**
	 * 是否开启程序日志
	 */
	public static boolean isDebug = true;
	/**
	 * 程序文件存储路径
	 */
	public static String tlcyMusicPath = Utils.getSdcardPath() + "/XHPM/";
	/**
	 * 图片存储
	 */
	public static String tlcyImagePath = tlcyMusicPath + "Image/";

	public static String tlcyImageCache = tlcyMusicPath + "Cache/";

	public static final String DATABASE_NAME = "content.db";// 数据库名

	public static String tlcyVideoPath = tlcyMusicPath + "Video/";
	public static String tlcyAudioPath = tlcyMusicPath + "Audio/";
	public static String tlcyRoPath = tlcyMusicPath + "ro/";// ro文件路径
	public static String tlcyCoPath = tlcyMusicPath + "co/";// co文件根路径
	public static String tlcyOriginPath = tlcyMusicPath + "origin/";// 解密后的临时文件根路径

	public static String xhpmLrcPath = tlcyMusicPath + "lrc/";
	public static String xhpmMusicPath = tlcyMusicPath + "music/";

	public static final String PREFS_NAME = "TlPrefsFile";
	public static final String PREFS_UserInfo = "UserInfo";

	public static String u_ShowPopStr = "";// 服务器连接信息
	public static String u_Url = "";// 升级地址
	public static String u_Discribe;
	public static boolean isCheckin;// 是否已经鉴权
	public static boolean isChangeFirstPwd;// 是否修改第一次系统分配的密码
	public static String userid = null;// 用户文号
	public static String mUser_Name;// 用户昵称
	public static String mUser_Email;// 用户邮箱
	public static String mUser_PhoneNum;// 用户手机号
	public static String mCheckCode = "1234";// 验证码
	public static int parentId = 3;

	public static String C_NEWSPAPER_ID = "1";
	public static String C_PERIODICAL_ID = "2";
	public static String C_BOOK_ID = "3";
	public static String C_NEWS_ID = "4";
	public static String C_LIBRARY_ID = "5";

	public static String C_NEWSPAPER_NAME = "报纸";
	public static String C_PERIODICAL_NAME = "期刊";
	public static String C_BOOK_NAME = "图书";
	public static String C_NEWS_NAME = "新闻";
	public static String C_LIBRARY_NAME = "图书馆";

	public final static int EACH_PAGE_COUNTNUM = 20;
	public final static int EACH_PAGE_MAX_COUNTNUM = 500;

	public static final int MORE_LIST_DATA = 50;
	public static final int PRE_LIST_DATA = 51;
	public static final int JUMP_LIST_PAGE = 52;
	public static final int CLICK_LIST_ADVER_IMAGE = 53;
	/**
	 * 邮箱和电话号码的正则表达式
	 */
	public static final String EmailPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	public static final String PhonePattern = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";

	/**
	 * 当前屏幕横竖屏状态 参考Configuration.ORIENTATION_PORTRAIT
	 * Configuration.ORIENTATION_LANDSCAPE
	 */
	public static int nowOrientation;
	/**
	 * 上一个屏幕状态 参考Configuration.ORIENTATION_PORTRAIT
	 * Configuration.ORIENTATION_LANDSCAPE
	 */
	public static int lastOrientation;

	/**
	 * 0代表不升级，1代表一般升级，2代表强制升级
	 */
	public static int UPDATE_FLAG = 0;

	public static final String HostName1[] = { "http://119.254.69.232/mcity/city", "http://119.254.69.232/mcity/city",
			"http://119.254.69.232/mcity/city", "http://119.254.69.232/mcity/city", "http://119.254.69.232/mcity/city",
			"http://119.254.69.232/mcity/city" };//

	public static final String HostName2[] = { "http://119.254.69.232/mcity/city", "http://119.254.69.232/mcity/city",
			"http://119.254.69.232/mcity/city", "http://119.254.69.232/mcity/city", "http://119.254.69.232/mcity/city",
			"http://119.254.69.232/mcity/city" };//

	public static String typeAndVsersion = null;
	public static String oldtypeAndVsersion = null;
	/**
	 * 栏目根目录
	 */
	public static String categoryRootId = "0";
	public static int isChangededPWD = 0;
	// public static String certificateId;

	public static String IMEI = null;

	public static void initTypeAndVsersion(Activity context) {
		try {
			nowOrientation = context.getResources().getConfiguration().orientation;
			lastOrientation = nowOrientation;
			JSONStringer stringer = new JSONStringer();
			stringer.object();
			stringer.key("product").value("XHRD");
			stringer.key("clienttype").value("Android");
			stringer.key("clientversion").value("1.0.000");
			stringer.key("model").value(Utils.getMobileModel());
			stringer.key("resolution").value(Utils.getScreenWidth(context) + "X" + Utils.getScreenHeight(context));
			stringer.key("systemversion").value(Utils.getSDKVersion());
			stringer.key("channel").value("DEV");
			stringer.key("updatechannel").value("2");
			IMEI = Utils.getIMEI(context);
			LogInfo.LogOut("imei:" + IMEI);
			stringer.key("imei").value(IMEI);
			stringer.key("imsi").value(Utils.getIMSI(context) == null ? "" : Utils.getIMSI(context));
			stringer.key("login").value(0);
			stringer.key("memberId").value(2);
			stringer.key("language").value(Utils.getLocalLanguage());
			typeAndVsersion = stringer.endObject().toString();
			oldtypeAndVsersion = typeAndVsersion;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateUidToTypeAndVsersion(String uid, String username, int memberId) {
		Json json = new Json(oldtypeAndVsersion);
		if (uid != null) {
			json.put("userid", uid);
			json.put("userId", uid);
			json.put("userName", username);
			json.put("memberId", memberId);
		}
		typeAndVsersion = json.toNormalString();
	}

	/**
	 * 保存是否有专供
	 */
	public static void setSpecial(Context c, boolean isSpecial) {
		c.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean("isSpecial", isSpecial).commit();
	}

	/**
	 * 读取是否有专供
	 */
	public static boolean isSpecial(Context c) {
		return c.getSharedPreferences(PREFS_NAME, 0).getBoolean("isSpecial", false);
	}

	public static int getPlayMode(Context c) {
		return c.getSharedPreferences(PREFS_NAME, 0).getInt("playMode", 0);
	}

	public static void setTxtGravity(Context c, int gravity) {
		c.getSharedPreferences(PREFS_NAME, 0).edit().putInt("gravity", gravity).commit();
	}

	public static int getTxtGravity(Context c) {
		return c.getSharedPreferences(PREFS_NAME, 0).getInt("gravity", 0);
	}

	public static void setTxtSize(Context c, int size) {
		c.getSharedPreferences(PREFS_NAME, 0).edit().putInt("txtsize", size).commit();
	}

	/**
	 * 设置消息个数
	 * 
	 * @param c
	 * @param size
	 */
	public static void setTipSize(Context c, int size) {
		c.getSharedPreferences(PREFS_NAME, 0).edit().putInt("tipSize", size).commit();
	}

	/**
	 * 获取消息个数
	 * 
	 * @param c
	 * @return
	 */
	public static int getTipSize(Context c) {
		return c.getSharedPreferences(PREFS_NAME, 0).getInt("tipSize", 0);
	}

	public static int getTxtSize(Context c) {
		return c.getSharedPreferences(PREFS_NAME, 0).getInt("txtsize", 25);
	}

}

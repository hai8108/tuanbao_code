package com.haitian.engine;

import java.io.File;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.haitian.main.Configs;
import com.haitian.model.TableColumns.FilesColumns;
import com.haitian.utils.LogInfo;

public class DBEngine {
    static SQLiteDatabase db;
    public static final String TAG = "DBEngine";

    /**
     * 创建数据库
     */
    public static void create() {
        try {
            File file = new File(Configs.tlcyMusicPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            db = SQLiteDatabase.openDatabase(Configs.tlcyMusicPath + Configs.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            LogInfo.LogOut(TAG + " open db");
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " open db error and create db start ");
            db = SQLiteDatabase.openOrCreateDatabase(Configs.tlcyMusicPath + Configs.DATABASE_NAME, null);
            createFilesTable();
            LogInfo.LogOut(TAG + " open db error and create db end");
        }
    }


    private static boolean createTableAudios() {
        try {
            db.execSQL("CREATE TABLE audios (" + "id INTEGER PRIMARY KEY autoincrement, " + "localid text," + //
                    "name TEXT ," + // 歌曲名称
                    "path TEXT ," + //
                    "lrcpath TEXT ," + // 　
                    "artist TEXT ," + // 　
                    "album TEXT ," + // 　
                    "style TEXT ," + // 　
                    "state INTEGER ," + //
                    "year INTEGER " + //
                    ");");
            LogInfo.LogOut(TAG + " Create Table audios ok");
            return true;
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " Create Table audios err,table exists." + e.getMessage());
        }
        return false;
    }
    private static boolean createFilesTable() {
		try {
			String sql = "create table av_files(" + FilesColumns.COL_ID + " INTEGER PRIMARY KEY autoincrement ,"
					+ FilesColumns.COL_TITLE + " TEXT," + FilesColumns.COL_TITLE_PINYIN + " TEXT,"
					+ FilesColumns.COL_PATH + " TEXT," + FilesColumns.COL_DURATION + " INTEGER,"
					+ FilesColumns.COL_POSITION + " TEXT," + FilesColumns.COL_LAST_ACCESS_TIME + " INTEGER,"
					+ FilesColumns.COL_THUMB + " TEXT" + ");";
			LogInfo.LogOut("----------------createCustomerTable---------------");
			db.execSQL(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
    /**
     * 视频数据库表
     * 
     * @author
     */
    private static boolean createTables() {
        try {
            db.execSQL("CREATE TABLE videos (" + "_id INTEGER PRIMARY KEY autoincrement, " + // 主键
                    "video_num INTEGER," + // 视频序号
                    "video_name TEXT ," + // 视频名称
                    "language TEXT ," + // 视频语言
                    "status TEXT ," + // 下载状态　downloaded(下载完成)，downloading(下载中)　download_free(未下载，免费)
                                      // download_wait(已经加入到下　载队列中的)
                    "video_path TEXT" + // 视频路径
                    ");");
            LogInfo.LogOut(TAG + " Create Table audios ok");
            return true;
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " Create Table audios err,table exists." + e.getMessage());
        }
        return false;
    }

    /**
     * 在线新闻类型
     * 
     * @return
     */
    private static boolean createOnlineNewsTypes() {
        try {
            db.execSQL("CREATE TABLE onlinenewstypes (" + "newstype_id INTEGER PRIMARY KEY, " + // 新闻类型ID
                    "newstype_index INTEGER, " + // 新闻类型排序
                    "newstype_checked INTEGER, " + // 新闻类型是否被选中
                    "newstype_name TEXT, " + // 新闻类型名称
                    "newstype_date TEXT" + // 新闻类型最后更新时间
                    ");");
            LogInfo.LogOut(TAG + " Create Table audios ok");
            return true;
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " Create Table audios err,table exists." + e.getMessage());
        }
        return false;
    }

    /**
     * 在线新闻
     * 
     * @return
     */
    private static boolean createOnlineNews() {
        try {
            db.execSQL("CREATE TABLE onlinenews (" + "news_id INTEGER PRIMARY KEY, " + // 新闻ID
                    "news_pos INTEGER ," + // 新闻序号
                    "news_name TEXT ," + // 视频名称
                    "news_time TEXT ," + // 新闻发布时间
                    "news_publisher TEXT ," + // 新闻来源
                    "news_intro TEXT ," + // 新闻简介
                    "news_details TEXT ," + // 新闻内容
                    "news_picpath TEXT ," + // 新闻图片地址
                    "news_picintro TEXT ," + // 新闻图片简介
                    "news_typeid INTEGER" + // 新闻类型
                    ");");
            LogInfo.LogOut(TAG + " Create Table audios ok");
            return true;
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " Create Table audios err,table exists." + e.getMessage());
        }
        return false;
    }
    /**
	 * 过来whereArgs中为null的数据项
	 * 
	 * @param whereArgs
	 */
	private static void filterWhereArgs(String... whereArgs) {
		if (whereArgs != null && whereArgs.length > 0) {
			for (int i = 0, j = whereArgs.length; i < j; i++) {
				if (whereArgs[i] == null) {
					whereArgs[i] = "";
				}
			}
		}
	}
	public static boolean isEmpty() {
		create();
		String count = exeScalar("SELECT COUNT("+FilesColumns.COL_ID+") FROM av_files");
		return count == null || "0".equals(count);
	}
	/**
	 * 查询
	 * 
	 * @param sql
	 * @param whereArgs
	 * @return
	 */
	public static String exeScalar(String sql, String... whereArgs) {
		String result = "";
		filterWhereArgs(whereArgs);
		Cursor c = db.rawQuery(sql, whereArgs);
		if (c.moveToNext()) {
			result = c.getString(0);
		}
		c.close();
		close();
		return result;
	}
    public static void close() {
        try {
            if (db != null) {
                db.close();
            }
            SQLiteDatabase.releaseMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogInfo.LogOut(TAG + " db close!!!!");
        db = null;
    }

    private static boolean createTableCovers() {
        try {
            db.execSQL("CREATE TABLE covers (id TEXT, " + "img_id TEXT, " + "url TEXT" + ");");
            LogInfo.LogOut(TAG + " Create Table Favorites ok");
            return true;
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " Create Table Favorites err,table exists." + e.getMessage());
        }
        return false;
    }
}

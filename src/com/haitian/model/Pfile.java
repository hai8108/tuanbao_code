package com.haitian.model;

public class Pfile {
	public long _id;
	public String title;
	public String title_pinyin;
	public String path;
	public long last_access_time;
	public int duration;
	public int position;
	public String thumb;

	public long get_id() {
		return _id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title_pinyin == null) ? 0 : title_pinyin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pfile other = (Pfile) obj;
		if (title_pinyin == null) {
			if (other.title_pinyin != null)
				return false;
		} else if (!title_pinyin.equals(other.title_pinyin))
			return false;
		return true;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_pinyin() {
		return title_pinyin;
	}

	public void setTitle_pinyin(String title_pinyin) {
		this.title_pinyin = title_pinyin;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getLast_access_time() {
		return last_access_time;
	}

	public void setLast_access_time(long last_access_time) {
		this.last_access_time = last_access_time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

}

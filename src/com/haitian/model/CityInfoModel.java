package com.haitian.model;

public class CityInfoModel {
	// {"city_name":"\u5317\u4eac","city_id":"1","isvisible":"1","spelling":"BeiJing","key_word":"BJ"}
	public CityInfoModel() {
		super();
	}

	public String city_name;
	public String city_id;
	public String isvisible;
	public String spelling;
	public String key_word;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city_id == null) ? 0 : city_id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "CityInfoModel [city_name=" + city_name + ", city_id=" + city_id + ", isvisible=" + isvisible
				+ ", spelling=" + spelling + ", key_word=" + key_word + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityInfoModel other = (CityInfoModel) obj;
		if (city_id == null) {
			if (other.city_id != null)
				return false;
		} else if (!city_id.equals(other.city_id))
			return false;
		return true;
	}
}

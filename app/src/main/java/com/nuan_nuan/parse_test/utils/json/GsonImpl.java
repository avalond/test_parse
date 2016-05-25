package com.nuan_nuan.parse_test.utils.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin-mac .
 * json工具类
 */
public class GsonImpl extends Json {
	private Gson mGson = new Gson();


	@Override public String toJson(Object src) {
		return mGson.toJson(src);
	}

	@Override public <T> T toObject(String json, Class<T> claxx) {
		return mGson.fromJson(json, claxx);
	}

	@Override public <T> T toObject(byte[] bytes, Class<T> claxx) {
		return mGson.fromJson(new String(bytes), claxx);
	}

	@Override public <T> List<T> toList(String json, Class<T> claxx) {
		Type type = new TypeToken<ArrayList<T>>() {
		}.getType();
		List<T> list = mGson.fromJson(json, type);
		return list;
	}
}

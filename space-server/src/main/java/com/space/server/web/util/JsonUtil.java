package com.space.server.web.util;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by Markus Oppeneiger on 20.10.2016.
 */
public class JsonUtil {

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
}

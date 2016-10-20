package com.space.server.web.util;

import spark.Filter;
import spark.Request;
import spark.Response;

/**
 * Created by Markus Oppeneiger on 20.10.2016.
 */
public class Filters {

	public static Filter addGzipHeader = (Request request, Response response) -> {
		response.header("Content-Encoding", "gzip");
	};
}

package com.dist.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 对前端获取到的参数进行处理
 * @author xushengzh
 *
 */
public class HttpServletRequestUtil {
	
	public static int getInt(HttpServletRequest request,String key) {
		try {
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
		
	}
	
	public static long getLong(HttpServletRequest request,String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return  -1;
		}
		
	}
	
	public static Double getDouble(HttpServletRequest request,String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return  (double) -1;
		}
		
	}
	
	public static boolean getBoolean(HttpServletRequest request,String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return  false;
		}
		
	}
	
	public static String getString(HttpServletRequest request,String key) {
		String result = request.getParameter(key);
		try {
			if (result != null) {
				result = result.trim();
			}
			if (result.equals("")) {
				result = null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}


}

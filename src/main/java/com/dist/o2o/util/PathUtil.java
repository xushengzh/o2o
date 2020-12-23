package com.dist.o2o.util;

public class PathUtil {
	// 获取文件的分隔符
	private static String seperator = System.getProperty("file.separator");
/**
 * 返回项目图片的根路劲
 * @return
 */
	public static String getImgBasePath() {
		String  os = System.getProperty("os.name"); // 根据不同操作系统，获取不同的根目录
	    String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath="D:/projectedev/image/";
		}else {
			basePath="/home/xusheng/image/";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
/**
 * 根据不同需求，返回项目图片的子路径
 * @param shopId
 * @return
 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "upload/item/shop/"+shopId+"/";
		return imagePath.replace("/", seperator);
	}
}

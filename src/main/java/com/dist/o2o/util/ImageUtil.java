package com.dist.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 图片处理类
 * @author xushengzh
 *
 */
public class ImageUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	private static String basePath = Thread.currentThread().getContextClassLoader().  
			getResource("").getPath();  // 得到系统当前绝对路径
	private static final SimpleDateFormat  sDateFormat
	                               = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random  r = new Random();
	/**
	 * 将CommonsMultipartFile转换为File
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	/**
	 * 处理缩略图，并返回新生成图片的相对路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbanail(File thumbnail,String targetAddr) {
		// 随机名称
		String realFileName = getRandomFileName();
		// 扩展名
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr+realFileName+extension;
		logger.debug("current relative is:"+realFileName);
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		try {
			// 对图片进行压缩，并且添加水印图片
			Thumbnails.of(thumbnail).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")), 0.25f)
			.outputQuality(0.8f)
			.toFile(dest);
		} catch (Exception e) {
			logger.error(extension.toString());
		}
		return relativeAddr;
	}
	
	/**
	 * 创建目标路径所涉及到的目录，/Users/xushengzh/baidu/work/img/xiaohuangren.jpg
	 * 那么Users xushengzh baidu work这几个文件夹都得自己创建出来
	 * @param tarageAddr
	 */
	private static void makeDirPath(String tarageAddr) {
	   String realFilePath = PathUtil.getImgBasePath()+tarageAddr;
	   File dirPath = new File(realFilePath);
	   if (!dirPath.exists()) {
		dirPath.mkdirs();
	   }
	}
	
	/**
	 * 获取输入文件流的扩展名
	 */
	private static String getFileExtension(File cFile) {
		String originalFileName = cFile.getName();  // 获取原始文件的文件名
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	
	/**
	 * 随机生成的文件名
	 * 当前年月日时分秒+随机5位数
	 */
	public static String  getRandomFileName() {
		//获取随机的五位数
		int rannum = r.nextInt(89999)+10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return rannum + nowTimeStr;
	}
	
	public static void main(String[] args) throws IOException {
		/**
		 * 测试
		 * 给图片添加水印
		 */
		Thumbnails.of(new File("/Users/xushengzh/baidu/work/img/xiaohuangren.jpg"))
		.size(200, 200)   // 像素
		.watermark(Positions.BOTTOM_RIGHT,   // 水印的位置
				ImageIO.read(new File(basePath+"/watermark.png")), 0.25f)  //水印图片的路劲//透明度
		. outputQuality(0.8f)     //压缩比例
		. toFile("/Users/xushengzh/baidu/work/img/xiaohuangrennew.png");
 
	}

}

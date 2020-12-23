package com.dist.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.jni.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.dist.o2o.dto.ShopExecution;
import com.dist.o2o.entity.PersonInfo;
import com.dist.o2o.entity.Shop;
import com.dist.o2o.enums.ShopStateEnum;
import com.dist.o2o.service.ShopService;
import com.dist.o2o.util.HttpServletRequestUtil;
import com.dist.o2o.util.ImageUtil;
import com.dist.o2o.util.PathUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	
	@Autowired 
	private ShopService shopservice;
	
	
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 接收并转化相应的参数，包括店铺信息及店铺图片
		 */
		// 将获取到的参数转换成为实体
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper  = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr,Shop.class);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map; 
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver resolver 
		           = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest mtsRequest= (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) mtsRequest.getFile("shopImg");
		}else {
			map.put("success", false);
			map.put("errMsg","上传图片不能为空");
			return map;
		}
		
		// 注册店铺
		if (shop != null && shopImg!= null) {
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1l);
			shop.setOwner(owner);
			File shopImgFile = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName());
			try {
				shopImgFile.createNewFile();
			} catch (IOException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map; 
			}
			try {
				inputStreamToFile(shopImg.getInputStream(),shopImgFile);
			} catch (IOException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map; 
			}
			ShopExecution se = shopservice.addShop(shop, shopImgFile);
			if (se.getState() == ShopStateEnum.CHECK.getState()) {
				// 正常返回
				map.put("success", true);
			}else {
				map.put("success", false);
				map.put("errMsg", se.getStateInfo());
			}
			return map;
		}else {
			map.put("success", false);
			map.put("errMsg","请输入店铺信息");
			return map;
		}
		// 返回结果
		
	}
	
	// 
	private static void inputStreamToFile(InputStream ins,File file) {
		FileOutputStream fos  = null;
		try {
			fos = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer))!= -1) {
				fos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用inputStreamToFile产生异常:"+e.getMessage());
		}finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e2) {
				throw new RuntimeException("inputStreamToFile关闭io产生异常："+e2.getMessage());
			}
		}
	}

}

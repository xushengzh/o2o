package com.dist.o2o.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dist.o2o.dao.ShopDao;
import com.dist.o2o.dto.ShopExecution;
import com.dist.o2o.entity.Shop;
import com.dist.o2o.enums.ShopStateEnum;
import com.dist.o2o.exceptions.ShopOperationException;
import com.dist.o2o.service.ShopService;
import com.dist.o2o.util.ImageUtil;
import com.dist.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{
	
	@Autowired
	private ShopDao shopdao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, File shopImg) {
		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 给店铺赋一些初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectedNum =shopdao.insertShop(shop); 
			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if (shopImg != null) {
					// 存储图片
					try {
						addShopImg(shop,shopImg);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:"+ e.getMessage());
					}
					// 更新店铺的图片地址
				    effectedNum = shopdao.updateShop(shop);
				    if (effectedNum <= 0) {
				    	throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:"+e.getMessage());
		}	
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void addShopImg(Shop shop, File shopImg) {
		// 获取shop图片目录的相对路径 
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String  shopAddrImg = ImageUtil.generateThumbanail(shopImg, dest);
		shop.setShopImg(shopAddrImg);
	}

}

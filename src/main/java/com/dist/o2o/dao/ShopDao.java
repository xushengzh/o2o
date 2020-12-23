package com.dist.o2o.dao;

import com.dist.o2o.entity.Shop;

public interface ShopDao {
	
	//店铺注册
	int insertShop(Shop shop);
	
	//更新店铺
	int updateShop(Shop shop);

}

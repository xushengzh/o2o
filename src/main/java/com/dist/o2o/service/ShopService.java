package com.dist.o2o.service;

import java.io.File;


import com.dist.o2o.dto.ShopExecution;
import com.dist.o2o.entity.Shop;


public interface ShopService {
	
	ShopExecution addShop(Shop shop,File shopImg);

}

package com.dist.o2o.service.impl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dist.o2o.dao.AreaDao;
import com.dist.o2o.entity.Area;
import com.dist.o2o.service.AreaService;
import com.dist.o2o.web.superadmin.AreaController;

import ch.qos.logback.classic.Logger;

@Service
public class AreaServiceImpl implements AreaService{
	
	Logger log = (Logger) LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaDao areaDao;

	@Override
	public List<Area> getAreaList() {
		return areaDao.queryArea();
	}
	
	
	

}

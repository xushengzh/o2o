package com.dist.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dist.o2o.entity.Area;
import com.dist.o2o.service.AreaService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
	
	Logger log = (Logger) LoggerFactory.getLogger(AreaController.class);

	@Autowired
	private AreaService areaService;
	/**
	 * 日志输出测试
	 */
	@RequestMapping(value="/listarea",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listArea(){
		
		log.info("=============start===============");
		long startTime = System.currentTimeMillis();
	    Map<String, Object> modelMap = new HashMap<String,Object>();
		List<Area> list = new ArrayList<>();
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		long endTime = System.currentTimeMillis();
		log.debug("costTime:[{}ms]",endTime-startTime);
		log.info("===========end============");
		return modelMap;
		
	}
	
}

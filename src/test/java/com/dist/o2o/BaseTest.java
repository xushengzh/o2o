package com.dist.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring 和 Junit 整合，Junit启动的时候加载springIOC容器
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉Junit spring 配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {

}

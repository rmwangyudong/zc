package com.wyd.zc.db.configurer;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wyd.zc.db.core.Mapper;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Mybatis 配置
 * 
 * @author 王玉栋
 * @date 2018-03-25 13:54:25
 * @since 1.0
 */
@Configuration
public class MybatisConfigurer {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.wyd.zc.db.mapper");

		// 配置通用Mapper，详情请查阅官方文档
		Properties properties = new Properties();
		properties.setProperty("mappers", Mapper.class.getName());
		// insert、update是否判断字符串类型!=''即test="str!=null"表达式内是否追加andstr!=''
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);

		return mapperScannerConfigurer;
	}

}

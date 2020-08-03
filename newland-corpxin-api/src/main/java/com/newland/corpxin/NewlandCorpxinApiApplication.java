package com.newland.corpxin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @Description: 主入口
 * @Author: Ljh
 * @Date 2020/8/3 20:55
 */
@MapperScan("com.newland.corpxin.mapper")
@SpringBootApplication
public class NewlandCorpxinApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewlandCorpxinApiApplication.class, args);
	}

}

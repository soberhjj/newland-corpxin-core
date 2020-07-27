package com.newland.corpxin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.newland.corpxin.mapper")
@SpringBootApplication
public class NewlandCorpxinApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewlandCorpxinApiApplication.class, args);
	}

}

package com.zs.qunfx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zs.qunfx.mapper")
public class QunfxApplication {

	public static void main(String[] args) {
		SpringApplication.run(QunfxApplication.class, args);
	}

}

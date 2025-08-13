package com.rookies4.myspringboot.config;

import com.rookies4.myspringboot.config.VO.CustomVO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class ProdConfig {
    @Bean
    public CustomVO customVO(){
        return CustomVO.builder()  //CustomVOBuilder type
                .mode("운영모드")
                .rate(1.5)
                .build(); //CustomVO type
    }
}

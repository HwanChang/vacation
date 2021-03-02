package com.hwanchang.vacation.config;

import com.hwanchang.vacation.security.Jwt;
import com.hwanchang.vacation.util.MessageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
public class ServiceConfig {

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource);
        MessageUtils.setMessageSourceAccessor(messageSourceAccessor);
        return messageSourceAccessor;
    }

    @Bean
    public Jwt jwt(JwtTokenConfig jwtTokenConfig) {
        return new Jwt(jwtTokenConfig.getIssuer(), jwtTokenConfig.getSecret(), jwtTokenConfig.getExpirationTime());
    }

}

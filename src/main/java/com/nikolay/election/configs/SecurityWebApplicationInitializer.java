package com.nikolay.election.configs;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

//Включаем модуль Security
@Component
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}
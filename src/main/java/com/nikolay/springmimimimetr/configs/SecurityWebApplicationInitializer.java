package com.nikolay.springmimimimetr.configs;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

//Бин используется чтобы модуль Security включился
@Component
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}
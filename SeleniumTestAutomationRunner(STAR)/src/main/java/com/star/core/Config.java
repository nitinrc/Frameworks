package com.star.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	public static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
}
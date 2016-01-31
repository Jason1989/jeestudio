package com.zxt.compplatform.acegi.util;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 功能：资源的刷新
 * 
 * @author bozch
 */
public class RefreshResources {

	public RefreshResources() {
	}

	/**
	 * 实现资源的刷新
	 * @param application
	 * @throws Exception
	 */
	public static void refresh(ServletContext application) throws Exception {
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(application);
		/*
		 * org.springframework.beans.factory.support.DefaultListableBeanFactory@fbf51d:
		 * defining beans
		 * [_authenticationManager,_filterChainProxy,_httpSessionContextIntegrationFilter,_filterChainProxyPostProcessor,_filterChainList,_securityContextHolderAwareRequestFilter,_accessManager,_portMapper,_exceptionTranslationFilter,_filterSecurityInterceptor,_sessionFixationProtectionFilter,_anonymousAuthenticationProvider,_anonymousProcessingFilter,_rememberMeServices,_rememberMeAuthenticationProvider,_rememberMeFilter,_rememberMeServicesInjectionBeanPostProcessor,_logoutFilter,_basicAuthenticationEntryPoint,_basicAuthenticationFilter,_formLoginFilter,_formLoginEntryPoint,_entryPointInjectionBeanPostProcessor,_userServiceInjectionPostProcessor,org.springframework.security.providers.dao.DaoAuthenticationProvider#0,org.springframework.security.userdetails.jdbc.JdbcUserDetailsManager#0,org.springframework.security.config.AuthenticationProviderBeanDefinitionParser$AuthenticationProviderCacheResolver#0,filterInvocationDefinitionSource,filterSecurityInterceptor,userCache,userEhCache,cacheManager,base64Encoder,dataSource,securityDataSourceConfig,messageSource];
		 * root of factory hierarchy
		 */
		FactoryBean factoryBean = (FactoryBean) ctx
				.getBean("&filterInvocationDefinitionSource");// 获取工厂实体Bean
		FilterInvocationDefinitionSource fids = (FilterInvocationDefinitionSource) factoryBean
				.getObject();
		FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx
				.getBean("filterSecurityInterceptor");
		filter.setObjectDefinitionSource(fids);
	}
}

package com.appleframework.qos.collector.spring;

import org.apache.log4j.Logger;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.PatternMatchUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类BeanNameAutoProxyCreator.java的实现描述：使用配置类型代替Springframework中配置名称的实现
 * 
 * @author cruise.xu 
 * 	2011-12-31 上午10:48:20
 */
public class BeanNameAutoProxyCreator extends AbstractAutoProxyCreator
		implements InitializingBean, ApplicationContextAware {
	
	private static Logger logger = Logger.getLogger(BeanNameAutoProxyCreator.class);

	private static final long serialVersionUID = -9094985530794052264L;
	
	private ApplicationContext context;

	/*private String beanNames;
	private String ignoreRegexs;*/
	
	public static List<String> ignoreBeanNames = new ArrayList<String>();
	
	private List<String> beanNameList = new ArrayList<String>();
	private List<String> ignoreRegexList = new ArrayList<String>();
	
	//public static 

	/**
	 * Identify as bean to proxy if the bean name is in the configured list of
	 * names.
	 */
	@SuppressWarnings("rawtypes")
	protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass,
			String beanName, TargetSource targetSource) {
		if (this.beanNameList != null) {
			for (String mappedName : this.beanNameList) {
				if (FactoryBean.class.isAssignableFrom(beanClass)) {
					if (!mappedName.startsWith(BeanFactory.FACTORY_BEAN_PREFIX)) {
						continue;
					}
					mappedName = mappedName.substring(BeanFactory.FACTORY_BEAN_PREFIX.length());
				}
				if(isIgnoreMatch(beanName)) {
					continue;
				}
				if (isMatch(beanName, mappedName)) {
					ignoreBeanNames.add(beanName);
					return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
				}
				BeanFactory beanFactory = getBeanFactory();
				if (beanFactory != null) {
					String[] aliases = beanFactory.getAliases(beanName);
					for (String alias : aliases) {
						if (isMatch(alias, mappedName)) {
							return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
						}
					}
				}
			}
		}
		return DO_NOT_PROXY;
	}

	/**
	 * Return if the given bean name matches the mapped name.
	 * <p>
	 * The default implementation checks for "xxx*", "*xxx" and "*xxx*" matches,
	 * as well as direct equality. Can be overridden in subclasses.
	 * 
	 * @param beanName
	 *            the bean name to check
	 * @param mappedName
	 *            the name in the configured list of names
	 * @return if the names match
	 * @see org.springframework.util.PatternMatchUtils#simpleMatch(String,
	 *      String)
	 */
	protected boolean isMatch(String beanName, String mappedName) {
		return PatternMatchUtils.simpleMatch(mappedName, beanName);
	}
	
	protected boolean isIgnoreMatch(String beanName) {
		if(ignoreRegexList.contains(beanName)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void afterPropertiesSet() throws Exception {
		//Assert.notNull(targetBeanType, "targetType cannot be null");
		String[] beanNames = context.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			logger.info("beanName = " + beanName);
		}
	}

	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	public void setBeanNames(String beanNames) {
		if(null != beanNames) {
			String[] beanNamess = beanNames.trim().split(",");
			for (String beanName : beanNamess) {
				beanNameList.add(beanName);
			}
		}
	}

	public void setIgnoreRegexs(String ignoreRegexs) {
		if(null != ignoreRegexs) {
			String[] ignoreRegexss = ignoreRegexs.trim().split(",");
			for (String ignoreRegex : ignoreRegexss) {
				ignoreRegexList.add(ignoreRegex);
			}
		}
	}	
	
}
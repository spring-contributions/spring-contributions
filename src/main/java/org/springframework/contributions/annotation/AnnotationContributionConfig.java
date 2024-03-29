package org.springframework.contributions.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * This configuration class can be used to enable the spring-conribution mechanism in springs java
 * configuration. It contains all necessary bean configurations for the java configuration
 * functionallity of the spring-contribution module.
 * 
 * @author Ortwin Probst
 */
@Configuration
public class AnnotationContributionConfig
{
	// //////////////////////////////////////////////////////////////////////
	// Ordered contribution infrastructure
	// //////////////////////////////////////////////////////////////////////

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryPostProcessor bindContributionPostProcessor()
	{
		return new AnnotationContributionPostProcessor();
	}

	/**
	 * Essential ordered contribution resolver bean is needed to retrieve the ordered contribution
	 * bean for a bean defined in an annotation bean configuration method. The service has to be
	 * injected into the method or the configuration class to retrieve the contribution by calling
	 * the resolve method with the name of the contribution as parameter.
	 * 
	 * @param context
	 * @return
	 */
	@Bean(name = "orderedContributionResolver")
	public OrderedContributionResolver<?> bindOrderedContributionResolver(ApplicationContext context)
	{
		return new OrderedContributionResolver(context);
	}

	/**
	 * Essential mapped contribution resolver bean is needed to retrieve the mapped contribution
	 * bean for a bean defined in an annotation bean configuration method. The service has to be
	 * injected into the method or the configuration class to retrieve the contribution by calling
	 * the resolve method with the name of the contribution as parameter.
	 * 
	 * @param context
	 * @return
	 */
	@Bean(name = "mappedContributionResolver")
	public MappedContributionResolver<?, ?> bindMappedContributionResolver(ApplicationContext context)
	{
		return new MappedContributionResolver(context);
	}

}

package org.springframework.contributions.mapped;

import static org.springframework.contributions.ContributionsNamespaceHandler.MAPPED_CONTRIBUTION_PREFIX;

import org.springframework.beans.factory.config.BeanReferenceFactoryBean;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Parser for references to mapped contributions.
 *
 * @author Christian K&ouml;berl
 */
public class MappedContributionRefBeanDefinitionParser extends AbstractSingleBeanDefinitionParser
{
    @Override
    @SuppressWarnings("rawtypes")
    protected Class getBeanClass(Element element)
    {
        return BeanReferenceFactoryBean.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder)
    {
        String contributionName = element.getAttribute("name");
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        String beanName = MAPPED_CONTRIBUTION_PREFIX + contributionName;
		if (!registry.containsBeanDefinition(beanName))
        {
			BeanDefinitionBuilder contributionBeanBuilder = BeanDefinitionBuilder.rootBeanDefinition(MapFactoryBean.class);

            contributionBeanBuilder.addPropertyValue("sourceMap", new ManagedMap());

            parserContext.getRegistry().registerBeanDefinition(beanName, contributionBeanBuilder.getBeanDefinition());
        }

        builder.addPropertyValue("targetBeanName", beanName);
        
    }
}
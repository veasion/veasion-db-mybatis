package cn.veasion.db.spring;

import cn.veasion.db.mybatis.MybatisMapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * MybatisBeanDefinitionRegistryPostProcessor
 *
 * @author luozhuowei
 * @date 2021/12/17
 */
public class MybatisBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String mapperScannerConfigureName = MapperScannerConfigurer.class.getName();
        if (beanDefinitionRegistry instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory registry = (DefaultListableBeanFactory) beanDefinitionRegistry;
            if (registry.containsBean(mapperScannerConfigureName)) {
                registry.getBean(MapperScannerConfigurer.class).setMapperFactoryBeanClass(MybatisMapperFactoryBean.class);
            }
        }
        if (beanDefinitionRegistry.containsBeanDefinition(mapperScannerConfigureName)) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(mapperScannerConfigureName);
            beanDefinition.getPropertyValues().add("mapperFactoryBeanClass", MybatisMapperFactoryBean.class);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

}

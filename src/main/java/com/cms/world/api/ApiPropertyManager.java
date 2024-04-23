package com.cms.world.api;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiPropertyManager implements BeanFactoryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment env = (ConfigurableEnvironment) this.environment;
        MapPropertySource mapPropertySource = getMapPropertySource(env);
        env.getPropertySources().addLast(mapPropertySource);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private MapPropertySource getMapPropertySource(ConfigurableEnvironment env) {
        Map<String, Object> properties = addApiUrl();
        return new MapPropertySource("apiUrls", properties);
    }

    private Map<String, Object> addApiUrl() { // 나중에 이 apiUrl들을 list로 받아야겠지...?
        Map<String, Object> map = new HashMap<>();
        map.put("cms.api.url", "https://cms.co.kr");
        map.put("cmi.api.url", "https://cms.co.kr");
        return map;
    }
}


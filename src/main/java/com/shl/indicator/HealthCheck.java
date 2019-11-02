package com.shl.indicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class HealthCheck  implements HealthIndicator {

    @Autowired
    Environment environment;

    Map<String, Object> config;

    @PostConstruct
    public void init(){
        config = new HashMap<String, Object>();
        for(PropertySource propertySource : ((ConfigurableEnvironment) environment).getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                config.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
    }

    @Override
    public Health health() {
        System.out.println(config);
        Health.Builder health=Health.up();
        for(Map.Entry<String,Object> entry: config.entrySet()){
            if(entry.getKey().startsWith("info.app.")){
                health.withDetail(entry.getKey(),entry.getValue());
            }
        }
        return health.build();
    }

}

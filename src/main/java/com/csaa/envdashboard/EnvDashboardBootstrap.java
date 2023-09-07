package com.csaa.envdashboard;

import com.csaa.envdashboard.envconfig.EnvConfig;
import com.csaa.envdashboard.envconfig.EnvConfigRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvDashboardBootstrap implements InitializingBean {

    @Autowired
    EnvConfigRepository envConfigRepository;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

package com.csaa.envdashboard.envconfig;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvConfigService {

    private EnvConfigRepository envConfigRepository;

    @Autowired
    public EnvConfigService(EnvConfigRepository envConfigRepository) {
        this.envConfigRepository = envConfigRepository;
    }

    public Set<String> getEnvNames() {
        Set<String> names = new HashSet<>();
        envConfigRepository.findAll()
            .forEach(envConfig -> names.add(envConfig.getEnvironmentName()));
        return names;
    }

    public Set<String> getAppGroupNames() {
        Set<String> names = new HashSet<>();
        envConfigRepository.findAll()
            .forEach(envConfig -> names.add(envConfig.getAppGroup()));
        return names;
    }

    public List<EnvConfig> getBuildInfo(String environment, String appGroup) {
        return envConfigRepository
            .findAll().stream()
            .filter(envConfig -> envConfig.getEnvironmentName().equalsIgnoreCase(environment))
            .filter(envConfig -> envConfig.getAppGroup()
                .equalsIgnoreCase(Objects.isNull(appGroup) ? "PAS" : appGroup))
            .collect(Collectors.toList());
    }
}

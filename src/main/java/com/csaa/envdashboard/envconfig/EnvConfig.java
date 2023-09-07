package com.csaa.envdashboard.envconfig;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EnvConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String environmentName;
    private String appGroup;
    private String appType;
    private String appName;
    private String deploymentJobType;
    private String environmentParamName;
    private String environmentParamValue;
    private String buildParamName;
    private String buildParamValue;
    private String jobPath;
    private String jobName;

    public EnvConfig() {
    }

    public EnvConfig(String environmentName, String appGroup, String appType, String appName,
        String deploymentJobType, String environmentParamName, String environmentParamValue,
        String buildParamName, String jobPath, String jobName) {
        this.environmentName = environmentName;
        this.appGroup = appGroup;
        this.appType = appType;
        this.appName = appName;
        this.deploymentJobType = deploymentJobType;
        this.environmentParamName = environmentParamName;
        this.environmentParamValue = environmentParamValue;
        this.buildParamName = buildParamName;
        this.jobPath = jobPath;
        this.jobName = jobName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getAppGroup() {
        return appGroup;
    }

    public void setAppGroup(String appGroup) {
        this.appGroup = appGroup;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDeploymentJobType() {
        return deploymentJobType;
    }

    public void setDeploymentJobType(String deploymentJobType) {
        this.deploymentJobType = deploymentJobType;
    }

    public String getEnvironmentParamName() {
        return environmentParamName;
    }

    public void setEnvironmentParamName(String environmentParamName) {
        this.environmentParamName = environmentParamName;
    }

    public String getEnvironmentParamValue() {
        return environmentParamValue;
    }

    public void setEnvironmentParamValue(String environmentParamValue) {
        this.environmentParamValue = environmentParamValue;
    }

    public String getBuildParamName() {
        return buildParamName;
    }

    public void setBuildParamName(String buildParamName) {
        this.buildParamName = buildParamName;
    }

    public String getBuildParamValue() {
        return buildParamValue;
    }

    public void setBuildParamValue(String buildParamValue) {
        this.buildParamValue = buildParamValue;
    }

    public String getJobPath() {
        return jobPath;
    }

    public void setJobPath(String jobPath) {
        this.jobPath = jobPath;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }


    @Override
    public String toString() {
        return "{\"EnvConfig\":{"
            + "\"id\":\"" + id + "\""
            + ", \"environmentName\":\"" + environmentName + "\""
            + ", \"appGroup\":\"" + appGroup + "\""
            + ", \"appType\":\"" + appType + "\""
            + ", \"appName\":\"" + appName + "\""
            + ", \"deploymentJobType\":\"" + deploymentJobType + "\""
            + ", \"environmentParamName\":\"" + environmentParamName + "\""
            + ", \"environmentParamValue\":\"" + environmentParamValue + "\""
            + ", \"buildParamName\":\"" + buildParamName + "\""
            + ", \"buildParamValue\":\"" + buildParamValue + "\""
            + ", \"jobPath\":\"" + jobPath + "\""
            + ", \"jobName\":\"" + jobName + "\""
            + "}}";
    }
}

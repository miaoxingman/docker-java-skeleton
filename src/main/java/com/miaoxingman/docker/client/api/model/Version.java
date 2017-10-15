package com.miaoxingman.docker.client.api.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Version {

    public String getVersion() {
        return version;
    }

    public String getGitCommit() {
        return gitCommit;
    }

    public String getGoVersion() {
        return goVersion;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public String getArch() {
        return arch;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getBuildTime() {
        return buildTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonProperty("Version")
    private String version;

    @JsonProperty("GitCommit")
    private String  gitCommit;

    @JsonProperty("GoVersion")
    private String  goVersion;
    
    @JsonProperty("KernelVersion")
    private String kernelVersion;

    @JsonProperty("Arch")
    private String  arch;

    @JsonProperty("Os")
    private String operatingSystem;

    @JsonProperty("ApiVersion")
    private String apiVersion;

    @JsonProperty("BuildTime")
    private String buildTime;
}

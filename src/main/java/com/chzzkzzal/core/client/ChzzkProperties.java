package com.chzzkzzal.core.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@ConfigurationProperties(prefix = "chzzk.oauth")
@Getter
public class ChzzkProperties {
    private final String clientId;
    
    private final String clientSecret;

    public ChzzkProperties(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}

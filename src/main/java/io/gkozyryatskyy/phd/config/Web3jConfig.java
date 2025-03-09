package io.gkozyryatskyy.phd.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "mempool.web3j")
public interface Web3jConfig {
    String host();

    @WithDefault("10000")
    long connectTimeout();

    @WithDefault("10000")
    long writeTimeout();

    @WithDefault("60000")
    long readTimeout();
}

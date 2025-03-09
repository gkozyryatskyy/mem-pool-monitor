package io.gkozyryatskyy.phd.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "mempool.subscribe")
public interface MemPoolMonitorConfig {

    @WithDefault("true")
    boolean enabled();
}

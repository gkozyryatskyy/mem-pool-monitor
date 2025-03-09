package io.gkozyryatskyy.phd;

import io.gkozyryatskyy.phd.config.MemPoolMonitorConfig;
import io.gkozyryatskyy.phd.service.MemPoolMonitorService;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.logging.Logger;

@QuarkusMain
public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String... args) {
        Runtime runtime = Runtime.getRuntime();
        log.infof("Running from main method. CPU:[%s],MEM.total:[%s],MEM.max:[%s] Args:%s",
                runtime.availableProcessors(),
                runtime.totalMemory(),
                runtime.maxMemory(),
                Arrays.toString(args));
        Quarkus.run(MemPoolMonitor.class, args);
    }

    public static class MemPoolMonitor implements QuarkusApplication {

        @Inject
        MemPoolMonitorConfig config;
        @Inject
        MemPoolMonitorService service;

        @Override
        public int run(String... args) {
            AtomicInteger exitCode = new AtomicInteger(0);
            if (config.enabled()) {
                service.subscribe()
                        .subscribe()
                        .with(e -> log.info("MemPoolMonitorService.subscribe() is finished!"),
                                e -> {
                                    log.error("MemPoolMonitorService.subscribe() error.", e);
                                    exitCode.set(1);
                                    Quarkus.asyncExit(exitCode.get());
                                });
            }
            Quarkus.waitForExit();
            return exitCode.get();
        }
    }
}

package io.gkozyryatskyy.phd.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.jbosslog.JBossLog;
import mutiny.zero.flow.adapters.AdaptersToFlow;
import org.web3j.protocol.Web3j;

@JBossLog
@ApplicationScoped
public class MemPoolMonitorService {

    @Inject
    Web3j web3j;

    public Uni<Void> subscribe() {
        return Multi.createFrom().publisher(AdaptersToFlow.publisher(web3j.pendingTransactionFlowable()))
                .invoke(e -> {
                    System.out.println((e.getBlockNumberRaw() != null ? e.getBlockNumber() : "null")
                            + ":" + (e.getTransactionIndexRaw() != null ? e.getTransactionIndex() : "null")
                            + " " + e.getHash()
                            + " " + (e.getGasRaw() != null ? e.getGas() : "null"));
                }) //TODO
                .onItem().ignoreAsUni();
    }
}

package com.cryptogate.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Getter
@Service
@AllArgsConstructor
public class EthereumConnector {

    private final Web3j web3;

    public Web3j getWeb3() {
        return web3;
    }

    @Value("${ethereum.node.url}")
    private static String ETHEREUM_NODE_URL;

    public EthereumConnector() {
        this.web3 = Web3j.build(new HttpService(ETHEREUM_NODE_URL));
    }
}

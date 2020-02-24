package com.fast.monitorserver.commandLineRunner;

import com.fast.monitorserver.RSListenerClient.NettyClient;
import com.fast.monitorserver.entity.ResourceIpRef;
import com.fast.monitorserver.service.IResourceIpRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Nelson on 2020/1/9.
 */
@Component
@Order(value = 2)
public class ResourcePCConnect implements CommandLineRunner {
    @Autowired
    private IResourceIpRef resourceIpRefService;
    private static final Logger log = LoggerFactory.getLogger(ResourcePCConnect.class);

    @Override
    public void run(String... args) throws Exception {
        Map<String, ResourceIpRef> map = resourceIpRefService.findResourceIpRefMap();
        if (map.values().size() <= 0) return;
        for (ResourceIpRef ref : map.values()) {
            log.info("Begin connect to Resource PC", ref.getResourceName());
            NettyClient client = new NettyClient(ref.getConnectIp(),Integer.parseInt(ref.getPort()));
            client.start();
        }
        log.info("Begin connect to Resource PC AZURAP94");
        NettyClient client=new NettyClient("13.75.126.156",8181);
        client.start();
    }
}

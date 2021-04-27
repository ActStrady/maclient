package com.actstrady.maclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ActStrady
 * @date 2021/4/27
 */
@Service
@Slf4j
public class MacServices {
    /**
     * 获取本地mac列表
     *
     * @return
     * @throws Exception TODO 异常处理，打印日志在本地
     */
    public List<String> getMacList() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> tmpMacList = new ArrayList<>();
        // 获取所有网络信息
        Enumeration<NetworkInterface> enumeration = null;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();

        } catch (SocketException e) {
            log.error("获取Enumeration出错！-> [{0}]", e);
        }
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                // 网卡信息
                NetworkInterface networkInterface = enumeration.nextElement();
                // 排除回环地址 虚拟网卡地址 未开启的地址
                try {
                    if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                        continue;
                    }
                } catch (SocketException e) {
                    log.error("NetworkInterface判断isLoopback或者isUp出错！-> [{0}]", e);
                }
                // 排除不是无线与有线网卡的
                // TODO 可能有问题
                if (!networkInterface.getDisplayName().contains("Intel") && !networkInterface.getDisplayName().contains("Realtek")) {
                    continue;
                }
                List<InterfaceAddress> adders = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress addr : adders) {
                    InetAddress ip = addr.getAddress();
                    NetworkInterface network;
                    byte[] mac = new byte[0];
                    try {
                        network = NetworkInterface.getByInetAddress(ip);
                        if (network == null) {
                            continue;
                        }
                        mac = network.getHardwareAddress();
                        if (mac == null) {
                            continue;
                        }
                    } catch (SocketException e) {
                        log.error("获取InetAddress或者HardwareAddress出错！-> [{0}]", e);
                    }
                    stringBuilder.delete(0, stringBuilder.length());
                    for (int i = 0; i < mac.length; i++) {
                        stringBuilder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    tmpMacList.add(stringBuilder.toString());
                }
            }
        }
        if (tmpMacList.size() <= 0) {
            return tmpMacList;
        }
        // 最后去重， Ip4 和 ip6 会重复
        return tmpMacList.stream().distinct().collect(Collectors.toList());
    }
}

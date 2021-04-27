package com.actstrady.maclient.service;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ActStrady
 * @date 2021/4/27
 */
public class MacServices {
    public static List<String> getMacList() throws Exception {
        // 获取所有网络信息
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> tmpMacList = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            // 网卡信息
            NetworkInterface networkInterface = enumeration.nextElement();
            // 排除回环地址 虚拟网卡地址 未开启的地址
            if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                continue;
            }
            // 排除不是无线与有线网卡的
            // TODO 可能有问题
            if (!networkInterface.getDisplayName().contains("Intel") && !networkInterface.getDisplayName().contains("Realtek")) {
                continue;
            }
            List<InterfaceAddress> adders = networkInterface.getInterfaceAddresses();
            for (InterfaceAddress addr : adders) {
                InetAddress ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network == null) {
                    continue;
                }
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                stringBuilder.delete(0, stringBuilder.length());
                for (int i = 0; i < mac.length; i++) {
                    stringBuilder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                tmpMacList.add(stringBuilder.toString());
            }
        }
        if (tmpMacList.size() <= 0) {
            return tmpMacList;
        }
        // 最后去重， Ip4 和 ip6 会重复
        return tmpMacList.stream().distinct().collect(Collectors.toList());
    }
}

// package com.actstrady.maclient.service;
//
// import java.net.*;
// import java.util.Enumeration;
//
// /**
//  * @author ActStrady
//  * @date 2021/4/27
//  */
// public class Test {
//     public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
//         try {
//             InetAddress candidateAddress = null;
// // 遍历所有的网络接口
//             for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
//                 NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
// // 在所有的接口下再遍历IP
//                 for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
//                     InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
//                     if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
//
//                         if (inetAddr.isSiteLocalAddress()) {
// // 如果是site-local地址，就是它了
//                             return inetAddr;
//                         } else if (candidateAddress == null) {
// // site-local类型的地址未被发现，先记录候选地址
//                             candidateAddress = inetAddr;
//
//                         }
//
//                     }
//
//                 }
//
//             }
//             if (candidateAddress != null) {
//                 return candidateAddress;
//             }
//
// // 如果没有发现 non-loopback地址.只能用最次选的方案
//
//             InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
//
//             if (jdkSuppliedAddress == null) {
//                 throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
//
//             }
//
//             return jdkSuppliedAddress;
//
//         } catch (Exception e) {
//             UnknownHostException unknownHostException = new UnknownHostException(
//
//                     "Failed to determine LAN address: " + e);
//
//             unknownHostException.initCause(e);
//
//             throw unknownHostException;
//
//         }
//
//     }
//
//     public static void main(String[] args) {
// //打印本机本地地址　　　　　System.out.println(SocketServer.getLocalHostLANAddress().toString().substring(1));
//
//     }
//
// }
//
// /**
//  * @author yins
//  * @date 2018年8月12日下午9:53:58
//  */
//
// /**
//  * 获取本地真正的IP地址，即获得有线或者无线WiFi地址。
//  * 过滤虚拟机、蓝牙等地址
//  * @author yins
//
//  * @date 2018年8月12日 下午9:53:58
//
//  */
//
// public class GetRealLocalIP {
//     /**
//      * 获取本地真正的IP地址，即获得有线或者无线WiFi地址。
//      * 过滤虚拟机、蓝牙等地址
//      * @author yins
//
//      * @date 2018年8月12日下午9:56:35
//
//      * @return
//
//      */
//
//     public static String getRealIP() {
//         try {
//             Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
//
//             while (allNetInterfaces.hasMoreElements()) {
//                 NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
//             // 去除回环接口，子接口，未运行和接口
//                 if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
//                     continue;
//
//                 }
//
//                 if (!netInterface.getDisplayName().contains("Intel") && !netInterface.getDisplayName().contains("Realtek")) {
//                     continue;
//
//                 }
//
//                 Enumeration addresses = netInterface.getInetAddresses();
//
//                 System.out.println(netInterface.getDisplayName());
//
//                 while (addresses.hasMoreElements()) {
//                     InetAddress ip = addresses.nextElement();
//
//                     if (ip != null) {
// // ipv4
//
//                         if (ip instanceof Inet4Address) {
//                             System.out.println("ipv4 = " + ip.getHostAddress());
//
//                             return ip.getHostAddress();
//
//                         }
//
//                     }
//
//                 }
//
//                 break;
//
//             }
//
//         } catch (SocketException e) {
//             System.err.println("Error when getting host ip address"
//
//                     + e.getMessage());
//
//         }
//
//         return null;
//
//     }
//
// }

package site.mingsha.kernel.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @author mingsha
 * @date: 2025-07-10
 */
public class IpUtils {

    private static final Logger         logger          = LoggerFactory.getLogger(IpUtils.class);

    private static final String         ANYHOST_VALUE   = "0.0.0.0";
    private static final String         LOCALHOST_VALUE = "127.0.0.1";
    private static final Pattern        IP_PATTERN      = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static volatile InetAddress LOCAL_ADDRESS   = null;

    /**
     * 工具类构造方法私有化，防止实例化
     */
    private IpUtils() {}

    /**
     * 获取本地第一个有效IP
     * @return 本地IP地址，找不到返回null
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    /**
     * 获取IP地址
     * @return String
     */
    public static String getIp() {
        return getLocalAddress().getHostAddress();
    }

    /**
     * 获取IP:端口
     * @param port
     * @return String
     */
    public static String getIpPort(int port) {
        String ip = getIp();
        return getIpPort(ip, port);
    }

    /**
     * 
     * @param ip
     * @param port
     * @return
     */
    public static String getIpPort(String ip, int port) {
        if (ip == null) {
            return null;
        }
        return ip.concat(":").concat(String.valueOf(port));
    }

    /**
     * 
     * @param address
     * @return
     */
    public static Object[] parseIpPort(String address) {
        String[] array = address.split(":");

        String host = array[0];
        int port = Integer.parseInt(array[1]);

        return new Object[] { host, port };
    }

    /* ---------------------- valid ---------------------- */

    /**
     * 
     * @param address
     * @return
     */
    private static InetAddress toValidAddress(InetAddress address) {
        if (address instanceof Inet6Address) {
            Inet6Address v6Address = (Inet6Address) address;
            if (isPreferIPV6Address()) {
                return normalizeV6Address(v6Address);
            }
        }
        if (isValidV4Address(address)) {
            return address;
        }
        return null;
    }

    /**
     * 
     * @return
     */
    private static boolean isPreferIPV6Address() {
        return Boolean.getBoolean("java.net.preferIPv6Addresses");
    }

    /**
     * 验证Inet4Address
     *
     * @param address
     * @return
     */
    private static boolean isValidV4Address(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        boolean result = (name != null && IP_PATTERN.matcher(name).matches() && !ANYHOST_VALUE.equals(name) && !LOCALHOST_VALUE.equals(name));
        return result;
    }

    /**
     * 规范化IPv6地址，将范围名称转换为范围ID。
     * 例如：
     * 转换
     * fe80:0:0:0:894:aeec:f37d:23e1%en0
     * 到
     * fe80:0:0:0:894:aeec:f37d:23e1%5
     * <p>
     * 地址中的%5称为范围ID。
     * 有关更多详细信息，请参见 {@link Inet6Address} 的Java文档。
     *
     * @param address 输入地址
     * @return 规范化地址，范围ID转换为int
     */
    private static InetAddress normalizeV6Address(Inet6Address address) {
        String addr = address.getHostAddress();
        int i = addr.lastIndexOf('%');
        if (i > 0) {
            try {
                return InetAddress.getByName(addr.substring(0, i) + '%' + address.getScopeId());
            } catch (UnknownHostException e) {
                // ignore
                logger.debug("Unknown IPV6 address: ", e);
            }
        }
        return address;
    }

    /* ---------------------- find ip ---------------------- */

    /**
     * 
     * @return
     */
    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            InetAddress addressItem = toValidAddress(localAddress);
            if (addressItem != null) {
                return addressItem;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (null == interfaces) {
                return localAddress;
            }
            while (interfaces.hasMoreElements()) {
                try {
                    NetworkInterface network = interfaces.nextElement();
                    if (network.isLoopback() || network.isVirtual() || !network.isUp()) {
                        continue;
                    }
                    Enumeration<InetAddress> addresses = network.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        try {
                            InetAddress addressItem = toValidAddress(addresses.nextElement());
                            if (addressItem != null) {
                                try {
                                    if (addressItem.isReachable(100)) {
                                        return addressItem;
                                    }
                                } catch (IOException e) {
                                    // ignore
                                }
                            }
                        } catch (Throwable e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } catch (Throwable e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return localAddress;
    }

}

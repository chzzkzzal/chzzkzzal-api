package com.chzzkzzal.zzalhits.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.regex.Pattern;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
public class ClientIpExtractor {

    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$"
    );

    private static final Pattern IPV6_PATTERN = Pattern.compile(
        "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$"
            + "|^((?:[0-9A-Fa-f]{1,4}:){1,7}:"
            + "|:(?:[0-9A-Fa-f]{1,4}:){1,7})$"
    );

    private static final String[] IP_HEADER_CANDIDATES = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_CLIENT_IP",
        "HTTP_X_CLUSTER_CLIENT_IP"
    };

    public static String extractIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            log.debug("Header: {}, Value: {}", header, ip);

            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                if (ip.contains(",")) {
                    ip = ip.split(",")[0].trim();
                }

                if (isValidIp(ip)) {
                    log.debug("Final IP determined (header): {}", ip);
                    return ip;
                }
            }
        }

        String remoteAddr = request.getRemoteAddr();
        log.debug("Final IP determined (RemoteAddr): {}", remoteAddr);
        return remoteAddr;
    }

    private static boolean isValidIp(String ip) {
        return IPV4_PATTERN.matcher(ip).matches() ||
            IPV6_PATTERN.matcher(ip).matches();
    }
}

package com.chzzkzzal.zzalhits.domain;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserAgentAnalyzer {
    public static UserAgentInfo analyze(HttpServletRequest request)
    {
        String userAgent = getUserAgent(request);
        BrowserType browserType = BrowserType.fromUserAgent(userAgent);
        DeviceType deviceType = DeviceType.fromUserAgent(userAgent);
        
        return new UserAgentInfo(userAgent,browserType, deviceType);
    }

    private static String getUserAgent(HttpServletRequest request){
        return request.getHeader("User-Agent");
    }

    @Getter
    @RequiredArgsConstructor
    public
    enum BrowserType {
        CHROME("Chrome"),
        FIREFOX("Firefox"),
        SAFARI("Safari"),
        EDGE("Edge"),
        IE("Internet Explorer"),
        UNKNOWN("Unknown");

        private final String displayName;

        public static BrowserType fromUserAgent(String userAgent) {
            if (userAgent == null) {
                return UNKNOWN;
            }

            String lowerCaseUserAgent = userAgent.toLowerCase();

            if (lowerCaseUserAgent.contains("chrome")) return CHROME;
            if (lowerCaseUserAgent.contains("firefox")) return FIREFOX;
            if (lowerCaseUserAgent.contains("safari")) return SAFARI;
            if (lowerCaseUserAgent.contains("edge")) return EDGE;
            if (lowerCaseUserAgent.contains("msie") || lowerCaseUserAgent.contains("trident")) return IE;

            return UNKNOWN;
        }
    }
    @Getter
    @RequiredArgsConstructor
   public enum DeviceType {
        MOBILE("Mobile"),
        DESKTOP("Desktop");

        private final String displayName;

        public static DeviceType fromUserAgent(String userAgent) {
            if (userAgent == null) {
                return DESKTOP;
            }

            String lowerCaseUserAgent = userAgent.toLowerCase();

            if (lowerCaseUserAgent.contains("mobile") ||
                lowerCaseUserAgent.contains("android") ||
                lowerCaseUserAgent.contains("iphone") ||
                lowerCaseUserAgent.contains("ipad")) {
                return MOBILE;
            }

            return DESKTOP;
        }
    }

    public record UserAgentInfo(
        String userAgent,
        BrowserType browserType,
        DeviceType deviceType
    ) { }

}

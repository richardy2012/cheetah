package org.cheetah.rs.filter;

import org.cheetah.commons.logger.Loggers;
import org.cheetah.rs.IllegalVisitorAddressException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.List;

/**
 * Created by Max on 2016/1/22.
 */
public class ApiAddressFilter extends ApiAccessFilter {
    private List<String> allowAddress;
    @Context
    private HttpServletRequest request;

    @Override
    protected void preRequest(ContainerRequestContext requestContext) throws IOException {
        String clientAddr = getIpAddr(request);
        Loggers.me().info(this.getClass(), "client address: [{}]", clientAddr);

        if (allowAddress.size() > 0 && !allowAddress.contains(clientAddr))
            throw new IllegalVisitorAddressException();
    }

    public void setAllowAddress(List<String> allowAddress) {
        this.allowAddress = allowAddress;
    }

    /**
     * 获取客户端ip
     *
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}

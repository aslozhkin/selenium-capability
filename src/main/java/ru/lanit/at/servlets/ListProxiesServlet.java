package ru.lanit.at.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.grid.internal.GridRegistry;
import org.openqa.grid.internal.ProxySet;
import org.openqa.grid.internal.RemoteProxy;
import org.openqa.grid.web.servlet.RegistryBasedServlet;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.json.Json;

public class ListProxiesServlet extends RegistryBasedServlet {

    public ListProxiesServlet() {
        this(null);
    }

    public ListProxiesServlet(GridRegistry registry) {
        super(registry);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        ProxySet proxySet = getRegistry().getAllProxies();
        Iterator<RemoteProxy> iterator = proxySet.iterator();
        Iterable<RemoteProxy> iterable = () -> iterator;
        List<Map<String, String>> maps = StreamSupport.stream(iterable.spliterator(), false)
                .map(remoteProxy -> {
                    Map<String, String> map = new HashMap<>();
                    List<MutableCapabilities> nodeCapabilities = remoteProxy.getConfig().capabilities;
                    map.put("platformVersion",
                            nodeCapabilities.stream().map(cap -> cap.getCapability(MobileCapabilityType.PLATFORM_VERSION)).findFirst().toString());
                    map.put("platformName",
                            nodeCapabilities.stream().map(cap -> cap.getCapability(MobileCapabilityType.PLATFORM_NAME)).findFirst().toString());
                    map.put("udid",
                            nodeCapabilities.stream().map(cap -> cap.getCapability(MobileCapabilityType.UDID)).findFirst().toString());
                    map.put("deviceName",
                            nodeCapabilities.stream().map(cap -> cap.getCapability(MobileCapabilityType.DEVICE_NAME)).findFirst().toString());
                    return map;
                }).collect(Collectors.toList());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().append(new Json().toJson(maps));
    }
}

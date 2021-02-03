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
                .filter(remoteProxy -> !remoteProxy.isBusy())
                .map(remoteProxy -> {
                    Map<String, String> map = new HashMap<>();
                    List<MutableCapabilities> nodeCapabilities = remoteProxy.getConfig().capabilities;
                    map.put("platformVersion",
                            nodeCapabilities.stream().map(cap -> cap.getCapability("platformVersion")).findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Отсутствует capability: \"platformVersion\""))
                                    .toString());
                    map.put("platformName",
                            nodeCapabilities.stream().map(cap -> cap.getCapability("platformName")).findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Отсутствует capability: \"platformName\""))
                                    .toString());
                    map.put("DeviceID",
                            nodeCapabilities.stream().map(cap -> cap.getCapability("UDID")).findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Отсутствует capability: \"UDID\""))
                                    .toString());
                    map.put("deviceName",
                            nodeCapabilities.stream().map(cap -> cap.getCapability("deviceName")).findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Отсутствует capability: \"deviceName\""))
                                    .toString());
                    return map;
                }).collect(Collectors.toList());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().append(new Json().toJson(maps));
    }
}

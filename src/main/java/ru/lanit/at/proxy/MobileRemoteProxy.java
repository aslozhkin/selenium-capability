package ru.lanit.at.proxy;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.GridRegistry;
import org.openqa.grid.internal.TestSession;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;

import java.io.IOException;
import java.util.logging.Logger;

public class MobileRemoteProxy extends DefaultRemoteProxy {
    private static final Logger LOGGER = Logger.getLogger(MobileRemoteProxy.class.getName());

    public MobileRemoteProxy(RegistrationRequest request, GridRegistry registry) {
        super(request, registry);
    }

    @Override
    public void afterSession(TestSession session) {
        String url = session.getSlot().getRemoteURL().toString().replaceAll(":4723/wd/hub", "") + ":3333";
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            LOGGER.info("Отправка запроса к узлу: \"" + url + "\"");
            LOGGER.info(response.getStatusLine().toString());
        } catch (IOException ex) {
            LOGGER.info("Не удалось отправить запрос к узлу: \"" + url + "\"");
            ex.printStackTrace();
        }
    }
}

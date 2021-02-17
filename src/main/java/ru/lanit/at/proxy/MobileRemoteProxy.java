package ru.lanit.at.proxy;

import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.GridRegistry;
import org.openqa.grid.internal.TestSession;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Logger;

public class MobileRemoteProxy extends DefaultRemoteProxy {
    private static final Logger LOGGER = Logger.getLogger(MobileRemoteProxy.class.getName());

    public MobileRemoteProxy(RegistrationRequest request, GridRegistry registry) {
        super(request, registry);
    }

    @Override
    public void afterSession(TestSession session) {
        LOGGER.info(session.getSlot().getRemoteURL().toString().replaceAll("/wd/hub", "") + ":3333");
//        URL url = null;
//        try {
//            url = new URL(session.getSlot().getRemoteURL().toString().replaceAll("/wd/hub", "") + ":3333");
//        } catch (MalformedURLException e) {
//            LOGGER.info("Не удалось получить адрес сессии");
//        }
//        HttpURLConnection con = null;
//        try {
//            con = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            LOGGER.info("Не удалось установить подключение с сессией: \"" + session.getSlot().getRemoteURL().toString() + "\"");
//        }
//        try {
//            con.setRequestMethod("GET");
//            LOGGER.info("Отправка запроса к узлу: \"" + session.getSlot().getRemoteURL().toString() + "\"");
//        } catch (ProtocolException e) {
//            LOGGER.info("Не удалось отправить GET запрос : \"" + session.getSlot().getRemoteURL().toString() + "\"");
//        }
    }
}

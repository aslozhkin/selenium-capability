FROM selenium/base:3.141.59-20210105
LABEL authors=SeleniumHQ

USER seluser

#========================
# Selenium Configuration
#========================

EXPOSE 4444

# As integer, maps to "maxSession"
ENV GRID_MAX_SESSION 5
# In milliseconds, maps to "newSessionWaitTimeout"
ENV GRID_NEW_SESSION_WAIT_TIMEOUT -1
# As a boolean, maps to "throwOnCapabilityNotPresent"
ENV GRID_THROW_ON_CAPABILITY_NOT_PRESENT true
# As an integer
ENV GRID_JETTY_MAX_THREADS -1
# In milliseconds, maps to "cleanUpCycle"
ENV GRID_CLEAN_UP_CYCLE 5000
# In seconds, maps to "browserTimeout"
ENV GRID_BROWSER_TIMEOUT 0
# In seconds, maps to "timeout"
ENV GRID_TIMEOUT 1800
# Debug
ENV GRID_DEBUG false
# As integer, maps to "port"
ENV GRID_HUB_PORT 4444
# As string, maps to "host"
ENV GRID_HUB_HOST "0.0.0.0"

COPY docker/generate_config \
     docker/start-selenium-hub.sh \
     /opt/bin/

COPY docker/selenium-hub.conf /etc/supervisor/conf.d/

COPY target/capability_matcher-1.0.jar /opt/selenium/capability_matcher-1.0.jar

RUN /opt/bin/generate_config > /opt/selenium/config.json

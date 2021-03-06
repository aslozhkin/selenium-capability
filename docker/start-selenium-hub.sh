#!/usr/bin/env bash

# set -e: exit asap if a command exits with a non-zero status
set -e

ROOT=/opt/selenium
CONF=${ROOT}/config.json

/opt/bin/generate_config >${CONF}

echo "Starting Selenium Hub with configuration:"
cat ${CONF}

if [ ! -z "$SE_OPTS" ]; then
  echo "Appending Selenium options: ${SE_OPTS}"
fi

java ${JAVA_OPTS} -cp /opt/selenium/mcloud-grid-1.0.jar:/opt/selenium/mcloud-grid-jar-with-dependencies.jar \
  org.openqa.grid.selenium.GridLauncherV3 \
  -role hub \
  -hubConfig ${CONF} \
  -servlets ru.lanit.at.servlets.ListProxiesServlet
${SE_OPTS}

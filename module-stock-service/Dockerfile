FROM azul/zulu-openjdk:17

ENV APPDIR=/opt/app/stock-service/classes

WORKDIR ${APPDIR}

ENV JAVA_DEBUG_OPT="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5009"

CMD java ${JAVA_DEBUG_OPT} -Djava.security.egd=file:/dev/./urandom -cp .:${APPDIR}/* com.joosangah.stockservice.StockServiceApplication

EXPOSE 8083 5009 8761
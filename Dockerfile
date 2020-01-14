FROM openjdk:11-jre-slim

RUN adduser --system --home /opt/simone --shell /sbin/nologin simone

RUN mkdir /var/simone && chown simone:0 /var/simone

USER simone

COPY target/simeone-example-thorntail.jar /opt/simone

RUN mkdir /var/simone/dropin && \
    mkdir /var/simone/db

EXPOSE 1527
EXPOSE 8080
EXPOSE 9990

WORKDIR /var/simone

CMD java -Djava.net.preferIPv4Stack=true \
    -Dderby.system.home=/var/simone/db \
    -Dse.uhr.simone.dropin=/var/simone/dropin \
    -Dse.uhr.simone.example.db.home=/var/simone/db/restbucks \
    -jar /opt/simone/simeone-example-thorntail.jar

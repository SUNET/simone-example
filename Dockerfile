FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/simone && \
    mkdir /var/simone && \
    adduser -D -h /opt/simone simone && \
    chown simone:0 /var/simone
    
USER simone

COPY target/simeone-example-swarm.jar /opt/simone

RUN mkdir /var/simone/dropin && \
    mkdir /var/simone/db

EXPOSE 1527
EXPOSE 8080
EXPOSE 9990
EXPOSE 8787

WORKDIR /var/simone

CMD java -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n \
    -XX:+UnlockExperimentalVMOptions \
    -XX:+UseCGroupMemoryLimitForHeap \
    -Djava.net.preferIPv4Stack=true \
    -Dderby.system.home=/var/simone/db \
    -Dse.uhr.simone.dropin=/var/simone/dropin \
    -Dse.uhr.simone.example.db.home=/var/simone/db/restbucks \
    -Dswarm.management.bind.address=0.0.0.0 \
    -jar /opt/simone/simeone-example-swarm.jar

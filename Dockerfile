FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/jboss && \
    mkdir /var/simone && \
    adduser -D -h /opt/jboss jboss && \
    chown jboss:0 /var/simone
    
USER jboss

COPY target/simeone-example-swarm.jar /opt/jboss

RUN mkdir /var/simone/dropin && \
    mkdir /var/simone/db

EXPOSE 1527
EXPOSE 8080
EXPOSE 9990
EXPOSE 8787

WORKDIR /var/simone

CMD ["java", "-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n","-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.net.preferIPv4Stack=true", "-Dderby.system.home=/var/simone/db", "-Dse.uhr.simone.dropin=/var/simone/dropin", "-Dswarm.management.bind.address=0.0.0.0", "-jar", "/opt/jboss/simeone-example-swarm.jar"]

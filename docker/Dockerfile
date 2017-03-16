FROM attiand/simone:1.0.0

ARG RESOURCE_NAME

COPY $RESOURCE_NAME /opt/jboss/wildfly/standalone/deployments

USER root
RUN chown jboss /opt/jboss/wildfly/standalone/deployments/$RESOURCE_NAME

USER jboss
RUN touch /opt/jboss/wildfly/standalone/deployments/$RESOURCE_NAME.dodeploy
COPY entry.sh /opt/jboss

USER root
RUN chmod u+x /opt/jboss/entry.sh && dos2unix /opt/jboss/entry.sh
COPY jboss-setup.sh /tmp
RUN dos2unix /tmp/jboss-setup.sh 
COPY commands.cli /tmp

USER jboss
WORKDIR /tmp
RUN /bin/bash /tmp/jboss-setup.sh
CMD ["/opt/jboss/entry.sh"]

FROM simone-wildfly

ARG RESOURCE_NAME

COPY $RESOURCE_NAME /opt/jboss/wildfly/standalone/deployments
USER root
RUN chown jboss /opt/jboss/wildfly/standalone/deployments/$RESOURCE_NAME
USER jboss
RUN touch /opt/jboss/wildfly/standalone/deployments/$RESOURCE_NAME.dodeploy
COPY entry.sh /opt/jboss
USER root
RUN chmod u+x /opt/jboss/entry.sh
RUN dos2unix /opt/jboss/entry.sh
USER jboss

CMD ["/opt/jboss/entry.sh"]

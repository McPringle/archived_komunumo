FROM dockergarten/payara-micro
MAINTAINER Marcus Fihlon, fihlon.ch
COPY build/libs/coma.war ${DEPLOYMENT_DIR}
ENV JAVA_MEMORY 256m

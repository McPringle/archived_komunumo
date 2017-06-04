FROM dockergarten/payara-micro
MAINTAINER Marcus Fihlon, fihlon.ch
COPY build/libs/coma.war ${DEPLOYMENT_DIR}

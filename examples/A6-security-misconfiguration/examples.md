# A6 Security Misconfiguration examples

### Start clojure repl listening on all IPS

`lein repl :start :host 0.0.0.0`

### Start H2 database listening in all IPS

```
FROM openjdk:jre-alpine

# A merge of:
#  https://github.com/zhilvis/docker-h2
#  https://github.com/zilvinasu/h2-dockerfile

MAINTAINER Oscar Fonts <oscar.fonts@geomati.co>

ENV DOWNLOAD http://www.h2database.com/h2-2019-03-13.zip
ENV DATA_DIR /opt/h2-data

RUN apk add --no-cache curl

RUN mkdir -p ${DATA_DIR} \
    && curl ${DOWNLOAD} -o h2.zip \
    && unzip h2.zip -d /opt/ \
    && rm h2.zip

COPY h2.server.properties /root/.h2.server.properties

EXPOSE 81 1521

WORKDIR /opt/h2-data

CMD java -cp /opt/h2/bin/h2*.jar org.h2.tools.Server \
 	-web -webAllowOthers -webPort 81 \
 	-tcp -tcpAllowOthers -tcpPort 1521 \
 	-baseDir ${DATA_DIR}
```
### Running services without HTTPS
### Return stacktraces
### Directory listing

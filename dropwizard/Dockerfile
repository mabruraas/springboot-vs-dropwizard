FROM            openjdk:8u151-jre
MAINTAINER      Brurås, Magnus <mabruraas@gmail.com>

ENV             WRK_DIR         /application
RUN             mkdir           ${WRK_DIR}

ADD             target/dropwizard*.jar    ${WRK_DIR}/app.jar
COPY            config.yaml     ${WRK_DIR}/config.yaml

WORKDIR         ${WRK_DIR}
EXPOSE          8080

CMD             [ "java", "-jar", "/application/app.jar", "server", "/application/config.yaml" ]

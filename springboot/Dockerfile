FROM            openjdk:8u151-jre
MAINTAINER      Brurås, Magnus <mabruraas@gmail.com>

ENV             WRK_DIR         /application
RUN             mkdir           ${WRK_DIR}

ADD             target/springboot*.jar    ${WRK_DIR}/app.jar

WORKDIR         ${WRK_DIR}
EXPOSE          8080

CMD             [ "java", "-jar", "/application/app.jar" ]

FROM openjdk:11-slim

COPY build/libs/app.jar /app/app.jar

ENTRYPOINT exec java $JAVA_OPTS -Dfile.encoding=UTF-8 -noverify -XX:TieredStopAtLevel=1 -XshowSettings:vm -XX:+UseContainerSupport -XX:+TieredCompilation -Duser.timezone="America/Sao_Paulo" -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar
EXPOSE 8080
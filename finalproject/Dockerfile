FROM alpine/java:17
EXPOSE 8080
ENV JAVA_PROFILE=main
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java" , "-Dspring.profiles.active=${JAVA_PROFILE}",\
"-cp","app:app/lib/*","se331.lab.Application"]
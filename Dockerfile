FROM harbor.ypl.loc/library/openjdk:8u275-jre-slim

ENV TZ Asia/Shanghai
COPY ./target/app_plant.jar /app/app_plant.jar
WORKDIR /app

CMD java -jar /app/app_plant.jar

FROM java
COPY target/*.jar app.jar
RUN bash -c 'touch ./app.jar'
EXPOSE 8081
CMD ["java","-jar","app.jar"]
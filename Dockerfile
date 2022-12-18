FROM amazoncorretto:11 AS build

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build

# TODO: create the runtime section with the code installed in /opt/ecore_roles
FROM openjdk:21-jdk AS build

WORKDIR /build

# Maven tools
COPY ../../mvnw ./
COPY ../../.mvn/ .mvn/
RUN chmod +x mvnw

# Copy parent dependencies
COPY ../../pom.xml ./

# Copy dependencies from all modules
COPY ../../apps/gateway_app apps/gateway_app
COPY ../../auth/pom.xml auth/

COPY ../../apps/vl_learning_app apps/vl_learning_app
COPY ../../users/pom.xml users/

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY . .

RUN chmod +x mvnw

# # Compile and build artifact
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk

WORKDIR /app

ARG APP_VERSION=1.0.0-SNAPSHOT
ENV APP_VERSION=${APP_VERSION}

# Copy artifact from previous build phase
COPY --from=build /build/apps/gateway_app/target/gateway_app-${APP_VERSION}.jar app.jar

ARG PORT_APP=8080
ENV PORT $PORT_APP

EXPOSE $PORT

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

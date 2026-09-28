# --- Build stage -------------------------------------------------------
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copy wrapper + pom first so dependency resolution is cached separately
# from source changes.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -q dependency:go-offline -B

COPY src ./src
RUN ./mvnw -q clean package -DskipTests -B

# --- Runtime stage -------------------------------------------------------
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app

RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
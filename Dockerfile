FROM maven:3.9.6 AS builder

# Ovaj deo definise HOME direktorijum koji se koristi kao radni direktorijum prilikom buildovanja.
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

# Ovaj deo kopira mikroservis u image kako bi Maven mogao da builduje taj mikroservis.
ADD . $HOME

# Ovaj deo builduje .jar fajl.
# - "--mount=type=cache,target=/root/.m2" sluzi da kesira maven dependencies
#   kako ih ne bi iznova i iznova preuzimali prilikom svakog buildovanja image-a
# - "-Drevision=$VERSION" - prosledjuje verziju koju smo definisali gore, ignorisati
# - "-Dmaven.test.skip" - iskljucuje pokretanje testova prilikom buildovanja image-a
# - "-f $HOME/pom.xml" - putanja do pom.xml fajla
RUN --mount=type=cache,target=/root/.m2 mvn -DfinalName=app -Dmaven.test.skip -f $HOME/pom.xml clean package

# Ovo je drugi stage gde dobijamo rezultirajuci image.
# Ovde kopiramo .jar fajl napravljen u prethodnom, tj. prvom stage-u.
FROM openjdk:21

COPY --from=builder /usr/app/target/*.jar /app.jar

EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app.jar"]
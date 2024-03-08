FROM amazoncorretto:21-alpine-jdk

# Ajout d'un argument avec une valeur par défaut pour forcer la non-utilisation du cache
ARG BUILD_DATE=now

# Installation de Maven
RUN apk add --no-cache maven

# Définition du répertoire de travail
WORKDIR /app

# Copie du fichier pom.xml pour télécharger les dépendances sans utiliser le cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copie du code source et construction du JAR
COPY src ./src
RUN mvn clean package spring-boot:repackage

# Vérification de la présence du fichier JAR dans le répertoire target
RUN ls -la target

# Définition de l'entrée principale pour exécuter le JAR
ENTRYPOINT ["java", "-jar", "./target/gestionEvenement-0.0.1-SNAPSHOT.jar"]
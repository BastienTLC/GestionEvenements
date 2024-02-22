

# Guide d'utilisation

Ce projet montre comment dockeriser une application Spring Boot avec Maven et Docker.

## Compilation de l'application

Pour compiler l'application et créer un fichier JAR exécutable, exécutez la commande suivante :

```shell
mvn clean package spring-boot:repackage
docker build -t evenement-api .
sudo docker run -p 8080:8080 -d evenement-api 
```

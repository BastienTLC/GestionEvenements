#!/bin/bash

# Compilation et construction de l'image pour evenement-api
cd ./gestionEvenement
mvn clean package spring-boot:repackage
docker build -t evenement-api .
cd ..
# Compilation et construction de l'image pour membre-api
cd ./gestionMembre
mvn clean package spring-boot:repackage
docker build -t membre-api .

#!/bin/bash

minikube stop

minikube delete

minikube start --driver=docker --ports=5432:30032 --ports=8080:30080

kubectl apply -f postgres

kubectl apply -f keycloak

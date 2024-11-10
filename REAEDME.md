# C0d3R

## Live-coding interview platform

### Local start up

1. Install minikube 
2. Run minikube with command 
   ```
   minikube addons enable ingress
   
   minikube start --driver=docker --ports=5432:30032 --ports=8080:30080

   k apply -f k8s-config
   ```
3. 
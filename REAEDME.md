# Lambda platform

## Swagger

Swagger available via link [http://localhost:8090/swagger-ui/index.html]

## Helpfull Kubernetes commands

1. Delete all from sandbox namespace
```bash
kubectl -n sandbox delete jobs --all --grace-period=0 --force && \
kubectl -n sandbox delete cronjobs --all --grace-period=0 --force
```

2.Get all jobs and cron jobs
```bash
kubectl -n sandbox get jobs,cronjobs
```

3.Get job description
```bash
kubectl -n sandbox describe job 32647a63-e3d2-4239-aa5e-011d9e42f8c3-cite-newmarioport-1
```

3.Get pod logs
```bash
kubectl -n sandbox logs 32647a63-e3d2-4239-aa5e-011d9e42f8c3-cite-lakegiovannaburg9976c
```

4. Attach bash to running container
```bash
kubectl -n sandbox exec --stdin --tty 32647a63-e3d2-4239-aa5e-011d9e42f8c3-testjob-3970-1-884mk -- /bin/bash
```

## Интересные команды для запуска задач

1. Установка curl и обращение к внешнему серверу
```bash
sudo apt update && sudo apt install -y curl jq && curl -s https://api.chucknorris.io/jokes/random | jq -r '.value'
```

apiVersion: apps/v1
kind: Deployment
metadata:
  name: backend
spec:
  replicas: 1
  revisionHistoryLimit: 3
  selector:
    matchLabels:
      app: backend
      deployment: backend
  template:
    metadata:
      labels:
        app: backend
        deployment: backend
    spec:
      containers:
      - name: backend
        image: ghcr.io/shclub/edu12-4 
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "dev"
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: backend
spec:
  selector:
    app: backend
    deployment: backend
  ports:
    - protocol: TCP
      port: 80
      targetPort: 80
  type: ClusterIP

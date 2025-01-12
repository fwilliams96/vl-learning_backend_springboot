# VL Learning

## Setup

### Launch users app

1) Go to users app
```bash
cd apps/users_app
```

2) Create .env file following .env.example file

3) Launch the app
```bash
docker-compose up -d
```
Or you can use the following command to launch only the database
```bash
docker-compose -f docker-compose-postgres.yml up -d
```

4) Launch pgadmin4
```bash
docker run -p 5050:80 --name pgadmin \
-e 'PGADMIN_DEFAULT_EMAIL=<your_email>' \
-e 'PGADMIN_DEFAULT_PASSWORD=<your_password>' \
-d dpage/pgadmin4
```# vl-learning_backend_springboot

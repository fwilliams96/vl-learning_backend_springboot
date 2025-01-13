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


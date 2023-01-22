COMPOSE=docker-compose
FLAGS=
IMAGES=

ifeq ($(OS),Windows_NT)
	IMAGES+=odas_backend odas_frontend
else
	IMAGES+=odas-backend odas-frontend
endif

start:
	$(COMPOSE) up $(FLAGS)
stop:
	$(COMPOSE) down
clean:
	docker image rm $(IMAGES)

COMPOSE=docker-compose
COMPOSE_FLAGS=-d
IMAGES=odas-backend odas-frontend

start:
	$(COMPOSE) up $(FLAGS)
stop:
	$(COMPOSE) down
clean:
	docker image rm $(IMAGES)
restart: stop clean start

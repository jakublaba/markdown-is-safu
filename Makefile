COMPOSE=docker-compose
COMPOSE_FLAGS=-d
IMAGES=safe-app_app

start:
	$(COMPOSE) up $(FLAGS)
stop:
	$(COMPOSE) down
clean:
	docker image rm $(IMAGES)
restart: stop clean start

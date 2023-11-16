DOCKERFILE_PATH = .
PROJECT_NAME = hstflly
APP_PORT = 8080

run:
	docker build --tag $(PROJECT_NAME) --file Dockerfile $(DOCKERFILE_PATH) && \
    docker run --publish $(APP_PORT):$(APP_PORT) --name $(PROJECT_NAME) $(PROJECT_NAME)
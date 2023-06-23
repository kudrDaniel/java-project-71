ifdef OS
	APP_DIST = build\install\app\bin\app
	GRADLEW_DIST = gradlew
else
	ifeq ($(shell uname), Linux)
	APP_DIST = ./build/install/app/bin/app
	GRADLEW_DIST =./gradlew
	endif
endif

run-dist:
	$(APP_DIST)

.PHONY: build
build:
	$(GRADLEW_DIST) build

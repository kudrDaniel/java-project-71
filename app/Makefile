ifdef OS
	APP_DIST = build\install\app\bin\app
else
	ifeq ($(shell uname), Linux)
	APP_DIST = ./build/install/app/bin/app
	endif
endif

run-dist:
	$(APP_DIST)

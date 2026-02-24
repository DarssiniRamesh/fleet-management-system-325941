#!/bin/bash
cd /home/kavia/workspace/code-generation/fleet-management-system-325941/java_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi


#!/bin/bash
echo "Upload war"
rsync -v ./build/libs/KtorServer-0.0.1.war $USER_HOST@$HOST_IP:
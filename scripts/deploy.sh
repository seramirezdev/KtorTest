#!/bin/bash
# sftp -i $USER_HOST@$HOST_IP /tmp/ssh_key:/home/seminariouniajc/ <<< $'put build/libs/KtorServer-0.0.1.war'
rsync -v ./build/libs/KtorServer-0.0.1.war $USER_HOST@$HOST_IP:
#!/bin/bash
echo $KEY_ENCODE | base64 --decode >/tmp/ssh_key
sftp -i $USER_HOST@$HOST_IP /tmp/ssh_key:/home/seminariouniajc/ <<< $'put build/libs/KtorServer-0.0.1.war'

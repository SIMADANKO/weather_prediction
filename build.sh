#!/bin/sh
if [ "$1" = "build" ];then
    mkdir /home/changsheng/project/project35540/project
    cp -a /home/changsheng/project/project35540/server/. /home/changsheng/project/project35540/project/
    cd /home/changsheng/project/project35540/project
    rm -rf /home/changsheng/project/project35540/server
    echo "执行成功"
fi

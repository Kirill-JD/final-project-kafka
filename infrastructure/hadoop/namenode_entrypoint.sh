#!/bin/bash
set -e

NN_DIR=/usr/local/hadoop/hdfs/namenode

mkdir -p $NN_DIR
chmod -R 777 $NN_DIR

# проверка: если нет metadata — форматируем
if [ ! -d "$NN_DIR/current" ]; then
  echo "Formatting NameNode..."
  hdfs namenode -format -force
fi

exec "$@"
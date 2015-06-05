#!/bin/sh

if [ $# -ne 1 ];
	then 
	echo "[x] This application installs the plugins required for the data hive software installation!"
	echo "Usage: $0 <elasticsearch path>"
	exit
fi

$1/bin/plugin install elasticsearch/elasticsearch-mapper-attachments/2.5.0

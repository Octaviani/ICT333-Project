#!/bin/sh
if [ $# -ne 1 ];
	then 
	echo "[x] Run elasticsearch!"
	echo "Usage: $0 <elasticsearch path>"
	exit
fi
$1/bin/elasticsearch


#!/bin/sh

if [ $# -ne 1 ];
	then 
	echo "[x] This application installs the plugins required for the data hive software installation!"
	echo "Usage: $0 <elasticsearch path>"
	exit
fi

$1/bin/plugin install elasticsearch/elasticsearch-mapper-attachments/2.5.0
$1/bin/plugin --install org.carrot2/elasticsearch-carrot2/1.8.0

# site plugins
$1/bin/plugin -install lukas-vlcek/bigdesk/2.4.0
$1/bin/plugin -install polyfractal/elasticsearch-inquisitor
$1/bin/plugin --install lmenezes/elasticsearch-kopf

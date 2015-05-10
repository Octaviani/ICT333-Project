#!/bin/bash

sudo systemctl start mysqld.service
cd solr-5.1.0/
./bin/solr stop
./bin/solr start

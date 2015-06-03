#!/usr/bin/bash

curl http://localhost:8983/solr/hive-solr-schema/update?commit=true -d  '<delete><query>*:*</query></delete>'

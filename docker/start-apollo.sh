#!/bin/bash
# Author:hukey
for image in `/usr/bin/ls images/*.tar.gz`; do
  docker load < $image
done
docker-compose up -d
#!/bin/bash

function required {
  if [ -z "$2" ]; then
    echo "$1 is not present"
    exit 1
  fi
}

DEFINITION=~/jenkins/workspace/image_definition.txt

if [ ! -f "$DEFINITION" ]; then
  echo "Not found file image_definition.txt"
  exit 1
fi

source "$DEFINITION"
export imageName imageTag

required "imageName" "$imageName"
required "imageTag" "$imageTag"
required "registry" "$registry"

OVERRIDE_TEMPLATE=$(cat <<EOF
version: "3.7"

services:
  $imageName:
    image: $registry/$imageName:$imageTag
    build: !reset null
EOF
)

echo -e "$(envsubst '$imageName,$imageTag' <<< "$OVERRIDE_TEMPLATE")" > ~/jenkins/workspace/docker-compose.override.yml


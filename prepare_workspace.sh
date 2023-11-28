#!/bin/bash

rm -r -f ~/jenkins/workspace
git clone https://github.com/Project-Futurity/config ~/jenkins/workspace
test -f ~/jenkins/workspace/docker-compose.yml
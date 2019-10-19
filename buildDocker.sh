#!/usr/bin/env bash

sbt "cli/docker:stage"
# sbt "cli/docker:publishLocal"

cp -R cli/target/docker/stage dist/docker

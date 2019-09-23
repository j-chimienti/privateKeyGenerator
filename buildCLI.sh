#!/usr/bin/env bash

sbt "project cli"
# build JAR file
sbt assembly

cp cli/target/scala-2.12/cli-assembly-0.1.0-SNAPSHOT.jar ./dist/

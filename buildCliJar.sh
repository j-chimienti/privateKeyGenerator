#!/usr/bin/env bash

# build JAR file
sbt cli/assembly

mkdir -p ./dist/cli
cp cli/target/scala-2.12/privateKeyGen-assembly-0.1.1.jar ./dist/cli/

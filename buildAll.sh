#!/usr/bin/env bash
rm -rf ./dist
sbt clean
./buildCliJar.sh
./buildNative.sh
./buildGUI.sh
./buildDocker.sh

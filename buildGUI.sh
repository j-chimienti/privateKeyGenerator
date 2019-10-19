#!/usr/bin/env bash
sbt gui/clean

# build zip
sbt gui/universal:packageBin

sbt gui/jdkPackager:packageBin

mkdir -p ./dist/gui
cp gui/target/universal/privatekeygen-0.1.1.zip ./dist/gui/
cp gui/target/universal/jdkpackager/bundles/privateKeyGen-0.1.1.dmg ./dist/gui/

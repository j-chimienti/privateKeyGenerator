#!/usr/bin/env bash
sbt "project gui"
sbt clean

# build dmg file (note: only works on osx)
#sbt universal:packageOsxDmg

# build bin file (note: os specific)
# usage: build .pkg, .msi, .exe
sbt jdkPackager:packageBin

# build zip
sbt universal:packageBin

# fixme: rename jar to wif

cp gui/target/universal/gui-0.1.0-SNAPSHOT.zip ./dist
cp gui/target/universal/jdkpackager/bundles/gui-0.1.0-SNAPSHOT.pkg ./dist/

#!/usr/bin/env bash

# Note: must edit ./graal/1_setup.sh first

. resources/graal_setup.sh
sbt cli/graalvm-native-image:packageBin
mkdir -p dist/cli
cp cli/target/graalvm-native-image/privateKeyGen dist/cli/

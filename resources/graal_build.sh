#!/usr/bin/env bash

set -e
# NOTE: use sbt "cli/graalvm-native-image:packageBin"
echo "Building jar..."

cd ..
sbt cli/assembly

# this script assumes that this file was already created
# with sbt-assembly
JAR_FILE=cli/target/scala-2.12/privateKeyGen-assembly-0.1.1.jar

echo "building native-image..."
# create a native image from the jar file and name
# the resulting executable `wif`
native-image --no-server \
--initialize-at-build-time=scala.Function1 \
-H:+ReportExceptionStackTraces \
-jar ${JAR_FILE} ./dist/privateKeyGen

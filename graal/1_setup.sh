#!/usr/bin/env bash


# source this file once before beginning with `. 1setup_graal`
# export JAVA_HOME=/Users/al/bin/graalvm-ce-19.1.1/Contents/Home
export GRAALVM_HOME=/Users/joe/Downloads/graalvm-ce-19.1.1/Contents/Home
export JAVA_HOME=/Users/joe/Downloads/graalvm-ce-19.1.1/Contents/Home
# export PATH=/Users/al/bin/graalvm-ce-19.1.1/Contents/Home/bin:$PATH
export PATH=/Users/joe/Downloads/graalvm-ce-19.1.1/Contents/Home/bin:$PATH
gu install native-image

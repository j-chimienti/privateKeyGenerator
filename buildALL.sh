#!/usr/bin/env bash
rm -rf ./dist
./buildCLI.sh
./buildGUI.sh
./buildNative.sh

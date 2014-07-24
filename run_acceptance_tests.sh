#!/bin/sh
mvn clean package
calabash-android run target/android-bootcamp.apk
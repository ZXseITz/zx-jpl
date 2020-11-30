# j3de

Java 3D Engine

## Introduction

This engine provides an easy to use framework for realtime animations and games.

The framework is build on top of [Lightweight Java Game Library](https://www.lwjgl.org/) (lwjgl).

## Framework

Please check out the wiki pages [here](https://github.com/ZXseITz/zx-j3de-native/wiki).

## Native Support

Some classes use Java Native Interface (jni) for high performance calculation.
The sub-project [Java 3D Engine native](https://github.com/ZXseITz/zx-j3de-native) provides the native source code,
which is written in C++.
All classes below use jni:
* ch.zxseitz.j3de.math.Matrix4

All build dynamic link library (dll) can be found in `./libs`.

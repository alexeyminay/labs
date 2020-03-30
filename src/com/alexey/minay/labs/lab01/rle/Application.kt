package com.alexey.minay.labs.lab01.rle

import java.io.File
import java.io.FileOutputStream

fun main(args: Array<String>){
    when(args[0]){
        "pack" -> pack(args[1], args[2])
        "unpack" -> unpack(args[1], args[2])
    }
}

fun pack(inputUrl: String, outputUrl: String){
    val file = File(inputUrl)
    if(!file.exists()){
        return
    }
    val input = file.inputStream()
    val output = File(outputUrl).outputStream()
    var theSameByteCounter = 0
    var lastByte = -1
    while (input.available() > 0) {
        val byte = input.read()

        if (lastByte != -1 && byte != lastByte) {
            writeBytes(output, theSameByteCounter, lastByte)
            theSameByteCounter = 0

        }
        lastByte = byte
        theSameByteCounter++
        if(theSameByteCounter > 254){
            writeBytes(output, theSameByteCounter, lastByte)
            theSameByteCounter = 0
            lastByte = -1
        }
    }
    input.close()
    output.close()
}

private fun writeBytes(output: FileOutputStream, theSameByteCounter: Int, lastByte: Int){
    output.write(theSameByteCounter)
    output.write(lastByte)
}

fun unpack(inputUrl: String, outputUrl: String){
    val file = File(inputUrl)
    if(!file.exists()){
        return
    }
    val input = file.inputStream()
    val output = File(outputUrl).outputStream()

    while (input.available() > 0) {
        val quantity = input.read()
        val byte = input.read()
        for (i in 0 until quantity){
            output.write(byte)
        }
    }

    input.close()
    output.close()
}
package com.alexey.minay.lab01.task3.matrix

import com.alexey.minay.lab01.task3.matrix.handler.HandlerState
import com.alexey.minay.lab01.task3.matrix.handler.DataHandler
import java.io.File

class FileMatrixReader(
        private val readDataHandler: DataHandler,
        private var url: String
) : MatrixReader {

    override fun read(): ReaderState {
        val file = File(url)
        if (!file.exists()) {
            return ReaderState.Error
        }
        val bufferedReader = file.bufferedReader()
        val iterator = bufferedReader.lineSequence().iterator()
        val data = mutableListOf<String>()
        iterator.forEach {
            data.add(it)
        }

        val handlerState = readDataHandler.getSquareMatrixFrom(data.toTypedArray())
        return when (handlerState) {
            is HandlerState.Error -> ReaderState.Error
            is HandlerState.Success -> ReaderState.Success(handlerState.matrix)
        }
    }
}
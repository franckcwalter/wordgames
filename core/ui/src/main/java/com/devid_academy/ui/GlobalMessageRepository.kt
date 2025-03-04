package com.devid_academy.ui

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GlobalMessageRepository {
    val userMessageStringRes = MutableSharedFlow<Int>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val userMessageString = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun observeErrorStringRes(): SharedFlow<Int> = userMessageStringRes.asSharedFlow()
    fun observeErrorString(): SharedFlow<String> = userMessageString.asSharedFlow()
}
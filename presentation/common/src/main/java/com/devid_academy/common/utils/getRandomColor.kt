package com.devid_academy.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun getRandomColor() = remember { listOf(Color(0xFFfecb36),
                                        Color(0xFFfe6465),
                                        Color(0xFF4de5d0),
                                        Color(0xFF6F89FE)
                                    ).random()
    }


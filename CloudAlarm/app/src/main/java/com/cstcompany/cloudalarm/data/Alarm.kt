package com.cstcompany.cloudalarm.data

import java.util.UUID

data class Alarm(
    val id: String = UUID.randomUUID().toString(),
    var hour: Int = 0,
    var minute: Int = 0,
    var isOn: Boolean = false,
    var setupTime: Int = 0,
)
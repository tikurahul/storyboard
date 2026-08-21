@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.bnorm.storyboard

import kotlin.js.JsAny
import kotlin.js.JsString

external object JSON {
    fun parse(text: JsString): JsAny
    fun stringify(value: JsAny): JsString
}

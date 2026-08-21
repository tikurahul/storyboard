@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.bnorm.storyboard

@JsModule("shiki/engine/javascript")
external object ShikiEngine {
    fun createJavaScriptRegexEngine(
        options: JavaScriptRegexEngineOptions = definedExternally
    ): RegexEngine
}

external interface RegexEngine : JsAny

external interface JavaScriptRegexEngineOptions : JsAny {
    var target: JsString?
    var forEach: JsAny?
}

@JsModule("shiki/core")
external object ShikiCore {
    fun createHighlighterCoreSync(
        options: HighlighterCoreOptions
    ): HighlighterCore
}

external interface HighlighterCore : JsAny {
    fun codeToTokens(
        code: JsString,
        options: CodeToTokensOptions
    ): TokensResult

    fun codeToHtml(
        code: JsString,
        options: CodeToHtmlOptions
    ): JsString

    fun loadTheme(vararg themes: JsAny)
    fun loadLanguage(vararg langs: JsAny)
    fun getLoadedThemes(): JsArray<JsString>
    fun getLoadedLanguages(): JsArray<JsString>
    fun dispose()
}

external interface ThemedToken : JsAny {
    var content: JsString
    var offset: JsNumber
    var color: JsString?
    var bgColor: JsString?
    var fontStyle: JsNumber?
    var explanation: JsArray<ThemedTokenScopeExplanation>?
}

external interface ThemedTokenScopeExplanation : JsAny {
    /** The full resolved scope stack path */
    var scopes: JsArray<Scope>
}

external interface Scope : JsAny {
    var scopeName: JsString
}

external interface TokensResult : JsAny {
    var tokens: JsArray<JsArray<ThemedToken>>
    var fg: JsString?
    var bg: JsString?
    var themeName: JsString?
    var rootStyle: JsString?
}

external interface HighlighterCoreOptions : JsAny {
    var themes: JsArray<JsAny>
    var langs: JsArray<JsAny>
    var engine: RegexEngine
}

external interface CodeToTokensOptions : JsAny {
    var lang: JsString
    var theme: JsAny // Can be a JsString (theme name) or theme object
    var includeExplanation: JsAny?
}

external interface CodeToHtmlOptions : JsAny {
    var lang: JsString
    var theme: JsAny
}

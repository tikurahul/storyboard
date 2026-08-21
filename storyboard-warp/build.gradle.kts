import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.maven.publish)
}

group = "dev.bnorm.storyboard"

kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":storyboard"))
            implementation(project(":storyboard-layout"))
            api(libs.jetbrains.annotations)
            implementation(libs.androidx.collection)
            implementation(compose.material)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(libs.eclipse.t4me.core)
            implementation(libs.eclipse.jdt.annotations)
            implementation(libs.jruby.joni)
            implementation(libs.jruby.jcodings)
        }
        wasmJsMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-browser:0.5.0")
            implementation(npm("shiki", "^4.3.1"))
        }
    }
}

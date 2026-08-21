import androidx.compose.material.Text
import dev.bnorm.storyboard.BasicCode
import dev.bnorm.storyboard.Kotlin
import dev.bnorm.storyboard.StoryboardBuilder
import dev.bnorm.storyboard.ui.warp
import org.intellij.lang.annotations.Language

@Language("kotlin")
val TRACING_SLIDES = listOf(
    """
      import androidx.tracing.Tracer
      import androidx.tracing.DelicateTracingApi
      import androidx.tracing.wire.TraceDriver
      import androidx.tracing.wire.TraceSink
      import java.io.File

      fun main() {
          
      }
    """.trimIndent(),

    """
      import androidx.tracing.Tracer
      import androidx.tracing.DelicateTracingApi
      import androidx.tracing.wire.TraceDriver
      import androidx.tracing.wire.TraceSink
      import java.io.File

      fun main() {
          // Create the TraceSink, and the `TraceDriver`

          // Register the tracer

          // Call driver.close() as a result of the process shutdown hook.
      }
    """.trimIndent(),


    """
      import androidx.tracing.Tracer
      import androidx.tracing.DelicateTracingApi
      import androidx.tracing.wire.TraceDriver
      import androidx.tracing.wire.TraceSink
      import java.io.File

      fun main() {
          // Create the TraceSink, and the `TraceDriver`
          val outputDirectory = File("/tmp/perfetto")
          val sink = TraceSink(directory = outputDirectory)
          val driver = TraceDriver(sink = sink, isEnabled = true)

          // Register the tracer

          // Call driver.close() as a result of the process shutdown hook.
      }
    """.trimIndent(),

    """
      import androidx.tracing.Tracer
      import androidx.tracing.DelicateTracingApi
      import androidx.tracing.wire.TraceDriver
      import androidx.tracing.wire.TraceSink
      import java.io.File

      fun main() {
          // Create the TraceSink, and the `TraceDriver`
          val outputDirectory = File("/tmp/perfetto")
          val sink = TraceSink(directory = outputDirectory)
          val driver = TraceDriver(sink = sink, isEnabled = true)

          // Register the tracer
          @OptIn(DelicateTracingApi::class)
          Tracer.setGlobalTracer(driver.tracer)

          // Call driver.close() as a result of the process shutdown hook.
          // ...
      }
    """.trimIndent(),

    """
      import androidx.tracing.Tracer
      import androidx.tracing.DelicateTracingApi
      import androidx.tracing.wire.TraceDriver
      import androidx.tracing.wire.TraceSink
      import java.io.File

      fun main() {
          // Create the TraceSink, and the `TraceDriver`
          val outputDirectory = File("/tmp/perfetto")
          val sink = TraceSink(directory = outputDirectory)
          val driver = TraceDriver(sink = sink, isEnabled = true)

          // Register the tracer
          @OptIn(DelicateTracingApi::class)
          Tracer.setGlobalTracer(driver.tracer)

          // Call driver.close() as a result of the process shutdown hook.
          Runtime.getRuntime().addShutdownHook(Thread {
              driver.close()
          })
      }
    """.trimIndent()
).map { BasicCode(code = it) }


@Suppress("FunctionName")
fun StoryboardBuilder.WarpScene() = warp(
    codeBlocks = TRACING_SLIDES,
    language = Kotlin,
    header = {
        Text("Code Scene")
    }
)

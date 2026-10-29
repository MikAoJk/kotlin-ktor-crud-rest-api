package io.github.mikaojk.api

import io.ktor.server.response.respond
import io.ktor.server.routing.*
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant

fun Routing.registerMetricsApi() {
    get("/metrics") {
        val process = ProcessHandle.current()
        val cpuTime = process.info().totalCpuDuration().orElseThrow {
            IllegalStateException("Process CPU time is unavailable")
        }
        val rss = Files.readAllLines(Path.of("/proc/self/status"))
            .firstNotNullOfOrNull { Regex("""^VmRSS:\s+(\d+)\s+kB$""").matchEntire(it) }
        checkNotNull(rss) { "Process resident memory is unavailable" }
        val sampledAt = Instant.now()

        call.respond(
            ProcessMetrics(
                cpu_seconds_total = cpuTime.seconds + cpuTime.nano / 1_000_000_000.0,
                memory_rss_bytes = rss.groupValues[1].toLong() * 1024,
                process_id = process.pid(),
                sampled_at_unix_seconds = sampledAt.epochSecond + sampledAt.nano / 1_000_000_000.0,
            )
        )
    }
}

data class ProcessMetrics(
    val cpu_seconds_total: Double,
    val memory_rss_bytes: Long,
    val process_id: Long,
    val sampled_at_unix_seconds: Double,
)

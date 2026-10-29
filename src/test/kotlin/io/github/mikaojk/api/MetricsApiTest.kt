package io.github.mikaojk.api

import io.github.mikaojk.module
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tools.jackson.module.kotlin.jacksonMapperBuilder
import java.time.Instant

internal class MetricsApiTest {
    @Test
    internal fun `Metrics are available from the full application without a database`() = testApplication {
        application { module() }
        val mapper = jacksonMapperBuilder().build()
        val process = ProcessHandle.current()
        var previousCpu = 0.0
        var previousTimestamp = 0.0

        repeat(2) {
            val before = Instant.now()
            val cpuBefore = process.info().totalCpuDuration().orElseThrow().toNanos() / 1_000_000_000.0
            val response = client.get("/metrics")
            val cpuAfter = process.info().totalCpuDuration().orElseThrow().toNanos() / 1_000_000_000.0
            val after = Instant.now()

            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals(ContentType.Application.Json, response.contentType()?.withoutParameters())
            val body = mapper.readTree(response.bodyAsText())
            assertEquals(
                setOf("cpu_seconds_total", "memory_rss_bytes", "process_id", "sampled_at_unix_seconds"),
                body.propertyNames().toSet(),
            )
            val cpu = body.get("cpu_seconds_total").doubleValue()
            val timestamp = body.get("sampled_at_unix_seconds").doubleValue()
            assertTrue(cpu in cpuBefore..cpuAfter)
            assertTrue(cpu >= previousCpu)
            assertTrue(body.get("memory_rss_bytes").isIntegralNumber)
            assertTrue(body.get("memory_rss_bytes").longValue() > 0)
            assertEquals(0L, body.get("memory_rss_bytes").longValue() % 1024)
            assertEquals(process.pid(), body.get("process_id").longValue())
            assertTrue(timestamp >= before.epochSecond + before.nano / 1_000_000_000.0)
            assertTrue(timestamp <= after.epochSecond + after.nano / 1_000_000_000.0)
            assertTrue(timestamp >= previousTimestamp)
            previousCpu = cpu
            previousTimestamp = timestamp
        }
    }
}

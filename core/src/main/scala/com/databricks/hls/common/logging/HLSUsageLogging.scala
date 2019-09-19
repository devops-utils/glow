package com.databricks.hls.common.logging

import com.databricks.hls.common.HLSLogging
import com.google.gson.Gson
import scala.collection.JavaConverters._

/**
 * These are objects/case classes/traits to log HLS events.
 */
case class MetricDefinition(name: String, description: String)

case class TagDefinition(name: String, description: String)

object HlsMetricDefinitions {
  val EVENT_HLS_USAGE = MetricDefinition(
    "hlsUsageEvent",
    description = "Umbrella event for event tracking of HLS services"
  )
}

object HlsTagDefinitions {
  val TAG_EVENT_TYPE = TagDefinition(
    name = "eventType",
    description = "The type of event that occurred."
  )
}

object HlsTagValues {
  val EVENT_PIPE = "pipe"

  val EVENT_NORMALIZE_VARIANTS = "normalizeVariants"

  val EVENT_VCF_READ = "vcfRead"

  val EVENT_VCF_WRITE = "vcfWrite"

  val EVENT_BIGVCF_WRITE = "bigVcfWrite"

  val EVENT_BGEN_READ = "bgenRead"

  val EVENT_BGEN_WRITE = "bgenWrite"
}

object HlsBlobKeys {
  val PIPE_CMD_TOOL = "pipeCmdTool"
}

trait HLSUsageLogging extends HLSLogging {

  protected def recordHlsUsage(
      metric: MetricDefinition,
      tags: Map[TagDefinition, String] = Map.empty,
      blob: String = null): Unit = {

    logger.info(
      s"${metric.name}:[${{
        if (blob == null) {
          tags.values
        } else {
          tags.values ++ Iterable(blob)
        }
      }.mkString(",")}]"
    )
  }

  protected def hlsJsonBuilder(options: Map[String, Any]): String = {
    { new Gson }.toJson(options.asJava)
  }

}
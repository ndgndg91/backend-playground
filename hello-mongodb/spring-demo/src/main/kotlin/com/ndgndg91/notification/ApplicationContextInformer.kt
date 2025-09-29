package com.ndgndg91.notification

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory
import java.text.NumberFormat

@Component
class ApplicationContextInformer(
    private val executor: ThreadPoolTaskExecutor
): ApplicationListener<ContextRefreshedEvent> {
    private val log = LoggerFactory.getLogger(ApplicationContextInformer::class.java)
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val arguments = ManagementFactory.getRuntimeMXBean().inputArguments
        log.info("JVM Options")
        log.info("{}", arguments)


        val memoryMXBean = ManagementFactory.getMemoryMXBean()
        val initHeapSize = memoryMXBean.heapMemoryUsage.init
        val committedHeapSize = memoryMXBean.heapMemoryUsage.committed
        val usedHeapSize = memoryMXBean.heapMemoryUsage.used
        val maxHeapSize = memoryMXBean.heapMemoryUsage.max

        log.info("Heap Size Information")
        log.info("Init Heap Size: ${NumberFormat.getInstance().format(initHeapSize / (1024 * 1024))} MB")
        log.info("Committed Heap Size: ${NumberFormat.getInstance().format(committedHeapSize / (1024 * 1024))} MB")
        log.info("Used Heap Size: ${NumberFormat.getInstance().format(usedHeapSize / (1024 * 1024))} MB")
        log.info("Max Heap Size: ${NumberFormat.getInstance().format(maxHeapSize / (1024 * 1024))} MB")

        log.info("Thread Pool Information")
        val threadPoolExecutor = executor.threadPoolExecutor
        log.info("Active Threads: ${threadPoolExecutor.activeCount}")
        log.info("Total Threads: ${threadPoolExecutor.poolSize}")
        log.info("Maximum Threads: ${threadPoolExecutor.maximumPoolSize}")
        log.info("Task Count: ${threadPoolExecutor.taskCount}")
        log.info("Completed Task Count: ${threadPoolExecutor.completedTaskCount}")
    }

}
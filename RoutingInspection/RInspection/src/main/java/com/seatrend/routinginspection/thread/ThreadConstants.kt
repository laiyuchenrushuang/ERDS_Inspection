package com.seatrend.xj.electricbicyclesalesystem.http.thread

import java.util.concurrent.TimeUnit

/**
 * 线程常量池
 *
 * Created by ly on 2020/3/13 9:34
 */
interface ThreadConstants {
    companion object {


        //参数初始化
        val CPU_COUNT = Runtime.getRuntime().availableProcessors()

        val CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4))
        //核心线程池大小
        val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1//最大线程池队列大小
        val KEEP_ALIVE = 1//保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间。
        val SECONDS = TimeUnit.SECONDS
        val MINUTES = TimeUnit.MINUTES
        val HOURS = TimeUnit.HOURS
        val DAYS = TimeUnit.DAYS
    }
}

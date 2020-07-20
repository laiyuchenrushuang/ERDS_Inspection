package com.seatrend.xj.electricbicyclesalesystem.http.thread

import com.seatrend.routinginspection.thread.PriorityExecutor
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask

/**
 * 线程工具类
 *
 * 【Runnable】
 * timer 定时线程 开放接口（cancel） 【独立】 newScheduledThreadPool
 *
 * 线程池的其他接口，适合其他接口，但是不适合cancel【独立】 threadPool
 *
 * 【Thread】
 * 为了扩展Thread 的操作 这里单独搞Thread实现
 *
 * Created by ly on 2020/3/9 14:05
 */
class ThreadPoolManager {

    private var fifo = true  //FIFO 是否 先进先出   LIFO 后进先出

    companion object {
        val instance: ThreadPoolManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ThreadPoolManager()
        }
    }

    fun setQueueMode(b: Any): ThreadPoolManager {
        if (b is Boolean) {
            this.fifo = b
        }
        return this
    }

    // 线程池
    private var threadPool: ThreadPoolExecutor? = null

    //timer 控制器
    private var timer: Timer? = null


    init {
        threadPool = PriorityExecutor(fifo)

        timer = Timer()
    }

    /**
     * 从线程池中抽取线程，执行指定的Runnable对象
     *
     * @param runnable
     */
    fun execute(runnable: Runnable) {
        threadPool!!.execute(runnable)
    }

    /**
     * 判断当前线程池是否繁忙
     * @return
     */
    fun isBusy(): Boolean {
        return threadPool!!.activeCount >= threadPool!!.corePoolSize
    }

    /**
     * 關閉線程池
     */
    fun showdown() {
        threadPool!!.shutdown()
    }

    /**
     * 立即關閉線程池
     */
    fun showdownNow() {
        threadPool!!.shutdownNow()
    }

    /**
     * 设置核心线程池大小
     */
    fun setCorePoolSize(corePoolSize: Int): ThreadPoolManager {
        threadPool!!.corePoolSize = corePoolSize
        return this
    }

    /**
     * 保持线程活动时间
     */
    fun setKeepAliveTime(time: Long, unit: TimeUnit): ThreadPoolManager {
        threadPool!!.setKeepAliveTime(time, unit)
        return this
    }

    /**
     * 线程池大小
     */
    fun setMaximumPoolSize(maximumPoolSize: Int): ThreadPoolManager {
        threadPool!!.maximumPoolSize = maximumPoolSize
        return this
    }

    /**
     * 開啟線程池（重新，构造建议不选这个）
     */
    fun open() {
        if (threadPool!!.isShutdown) {
            threadPool = PriorityExecutor(fifo)
        }
        threadPool = PriorityExecutor(fifo)
    }

    /**
     * 定时任务（安全?）
     *
     * runable 线程
     * delay 延迟多久后执行
     * period 执行周期
     *
     */

    fun schedule(runnable: Runnable, delay: Long, period: Long) {
        Thread {
            if (delay > 0) Thread.sleep(delay)
            while (true) {
                threadPool!!.execute(runnable)
                if (period > 0) Thread.sleep(period)
            }
        }.start()
    }

    /**
     * 定时安全任务池
     */
    fun createSchedulePool(runnable: Runnable,delay: Long, period: Long) {
        timer!!.schedule(timerTask {
            val newScheduledThreadPool = Executors.newScheduledThreadPool(ThreadConstants.CORE_POOL_SIZE)
            newScheduledThreadPool.schedule(runnable,0,TimeUnit.SECONDS)
        },delay,period)
    }

    /**
     * 定时安全任务池
     */
    fun createSchedulePool(runnable: Runnable,delay: Long) {
        timer!!.schedule(timerTask {
            val newScheduledThreadPool = Executors.newScheduledThreadPool(ThreadConstants.CORE_POOL_SIZE)
            newScheduledThreadPool.schedule(runnable,0,TimeUnit.SECONDS)
        },delay)
    }

    /**
     * timer 取消
     */
    fun cancel(){
        if(timer != null){
            timer!!.cancel()
        }
    }
}


package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task;

import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.*;

@Configuration
public class ScheduledTaskService {

    // 创建一个单线程的调度器，用于管理所有任务的调度
    private final ScheduledExecutorService scheduler;
    // 使用ConcurrentHashMap存储每个任务的调度结果
    private final ConcurrentHashMap<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    // 使用ConcurrentHashMap存储每个任务的实际Runnable对象
    private final ConcurrentHashMap<String, Runnable> taskMap = new ConcurrentHashMap<>();

    public ScheduledTaskService() {
        this.scheduler = new ScheduledThreadPoolExecutor(0, Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
//
//    public static void main(String[] args) {
//        ScheduledTaskService service = new ScheduledTaskService();
//        service.start();
//        // 添加示例任务，task1每2秒执行一次
//        service.addTask("task1", () -> System.out.println("Executing task1"), 2, TimeUnit.SECONDS);
//        // 添加示例任务，task2每5秒执行一次
//        service.addTask("task2", () -> System.out.println("Executing task2"), 6, TimeUnit.SECONDS);
//
//        // 更新示例任务，将task2修改为每3秒执行一次的任务
////        service.updateTask("task2", () -> System.out.println("Executing updated task2"), 3, TimeUnit.SECONDS);
//
//        // 删除示例任务task1
////        service.removeTask("task1");
//    }

    /**
     * 启动定时任务服务
     */
    public void start() {
        // 启动定时任务服务，这里可以添加一些初始化操作
    }

    /**
     * 添加任务
     * @param taskId 任务ID
     * @param task 任务的Runnable对象
     * @param delay 延迟时间
     * @param timeUnit 时间单位
     */
    public void addTask(String taskId, Runnable task, long delay, TimeUnit timeUnit) {
        // 检查任务ID是否已经存在，避免重复添加
        if (scheduledTasks.containsKey(taskId)) {
            System.out.println("Task with ID " + taskId + " already exists.");
            return;
        }
        // 使用调度器按固定频率调度任务，初始延迟为0
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleWithFixedDelay(task, 0, delay, timeUnit);
        // 将调度结果和任务对象分别存储到相应的Map中
        scheduledTasks.put(taskId, scheduledFuture);
        taskMap.put(taskId, task);
    }

    public void printScheduledTasks() {
        for (Map.Entry<String, ScheduledFuture<?>> entry : scheduledTasks.entrySet()) {
            System.out.println("Task ID: " + entry.getKey() + ", Scheduled Future: " + entry.getValue());
        }
    }

    /**
     * 获取当前调度任务的数量
     * @return 当前调度任务的数量
     */
    public int getTaskCount() {
        return scheduledTasks.size();
    }

    /**
     * 删除任务
     * @param taskId 任务ID
     */
    public void removeTask(String taskId) {
        // 从调度结果Map中移除并取消调度任务
        ScheduledFuture<?> scheduledFuture = scheduledTasks.remove(taskId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        // 从任务Map中移除任务
        taskMap.remove(taskId);
    }

    /**
     * 更新任务
     * @param taskId 任务ID
     * @param newTask 新的任务Runnable对象
     * @param delay 新的延迟时间
     * @param timeUnit 时间单位
     */
    public void updateTask(String taskId, Runnable newTask, long delay, TimeUnit timeUnit) {
        // 删除旧任务
        removeTask(taskId);
        // 添加新任务
        addTask(taskId, newTask, delay, timeUnit);
    }


    /**
     * 停止所有任务并关闭调度器
     */
    public void shutdown() {
        // 取消所有已调度的任务
        for (ScheduledFuture<?> future : scheduledTasks.values()) {
            future.cancel(true);
        }
        // 关闭调度器
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        // 清空任务Map
        scheduledTasks.clear();
        taskMap.clear();
    }
}
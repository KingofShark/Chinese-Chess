package controller;

public class ThreadManager implements Runnable{
    private volatile boolean running = true;
    private final Runnable task;

    public ThreadManager(Runnable task) {
        this.task = task;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        while (running && !current.isInterrupted()) {
            try {
                task.run();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Task bị ngắt khi đang chạy.");
                current.interrupt();
                break;
            }
        }
        System.out.println("Luồng kết thúc.");
    }
}

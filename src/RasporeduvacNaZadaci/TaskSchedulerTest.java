package RasporeduvacNaZadaci;

import java.util.*;
import java.util.stream.Collectors;

/**
 * I Partial exam 2016
 */
public class TaskSchedulerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Task[] timeTasks = new Task[n];
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            timeTasks[i] = new TimedTask(x);
        }
        n = scanner.nextInt();
        Task[] priorityTasks = new Task[n];
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            priorityTasks[i] = new PriorityTask(x);
        }
        Arrays.stream(priorityTasks).forEach(System.out::println);
        TaskRunner<Task> runner = new TaskRunner<>();
        System.out.println("=== Ordered tasks ===");
        System.out.println("Timed tasks");
        runner.run(Schedulers.getOrdered(), timeTasks);
        System.out.println("Priority tasks");
        runner.run(Schedulers.getOrdered(), priorityTasks);
        int filter = scanner.nextInt();
        System.out.printf("=== Filtered time tasks with order less then %d ===\n", filter);
        runner.run(Schedulers.getFiltered(filter), timeTasks);
        System.out.printf("=== Filtered priority tasks with order less then %d ===\n", filter);
        runner.run(Schedulers.getFiltered(filter), priorityTasks);
        scanner.close();
    }
}

class TaskRunner <T> {

    public void run(TaskScheduler<T> scheduler, T[] tasks) {
        List<T> order = scheduler.schedule(tasks);
        order.forEach(System.out::println);
    }
}

interface TaskScheduler<T>{
    public List<T> schedule(T[] tasks);
}

interface Task {
    //dopolnete ovde
    int getOrder();
}

class PriorityTask implements Task {
    private final int priority;

    public PriorityTask(int priority) {
        this.priority = priority;
    }

    @Override
    public int getOrder() {
        return priority;
    }

    @Override
    public String toString() {
        return String.format("PT -> %d", getOrder());
    }
}

class TimedTask implements Task {
    private final int time;

    public TimedTask(int time) {
        this.time = time;
    }

    @Override
    public int getOrder() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("TT -> %d", getOrder());
    }
}

class Schedulers {

    public static <T> TaskScheduler<T> getOrdered() {
        return new TaskScheduler<T>() {
            @Override
            public List<T> schedule(T[] tasks) {
                LinkedList<T> list = new LinkedList<>();
                T[] tmpTasks =(T[]) new Object[tasks.length];
                tmpTasks = Arrays.copyOf(tasks, tasks.length);
                for(int i=0; i<tmpTasks.length; i++){
                    for(int j=1; j<tmpTasks.length-i; j++){
                        if(((Task) tmpTasks[j-1]).getOrder() > ((Task) tmpTasks[j]).getOrder()){
                            T tmp = tmpTasks[j-1];
                            tmpTasks[j-1] = tmpTasks[j];
                            tmpTasks[j] = tmp;
                        }
                    }
                }
                list.addAll(Arrays.asList(tmpTasks));
                return list;
            }
        };
    }

    public static <T> TaskScheduler<T> getFiltered(int order) {
        return tasks -> {
            LinkedList<T> list = new LinkedList<>();
            for(int i=0; i<tasks.length; i++){
                if(((Task)tasks[i]).getOrder() <= order){
                    list.add(tasks[i]);
                }
            }
            return list;
        };
    }
}

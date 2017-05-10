## Distributed Queue with hazelcast



Hazelcast distributed queue is an implementation of `java.util.concurrent.BlockingQueue`. Being distributed, it enables all cluster members to interact with it. Using Hazelcast distributed queue, you can add an item in one machine and remove it from another one.


```
import com.hazelcast.core.Hazelcast;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
BlockingQueue<MyTask> queue = hazelcastInstance.getQueue( "tasks" );
queue.put( new MyTask() );
MyTask task = queue.take();

boolean offered = queue.offer( new MyTask(), 10, TimeUnit.SECONDS );
task = queue.poll( 5, TimeUnit.SECONDS );
if ( task != null ) {
  //process task
}


```


FIFO ordering will apply to all queue operations across the cluster. User objects (such as MyTask in the example above) that are enqueued or dequeued have to be Serializable.

Hazelcast distributed queue performs no batching while iterating over the queue. All items will be copied locally and iteration will occur locally.



## Distributed Executor with hazelcast

The default implementation of this framework (ThreadPoolExecutor) is designed to run within a single JVM. In distributed systems, this implementation is not desired since you may want a task submitted in one JVM and processed in another one. Hazelcast offers IExecutorService for you to use in distributed environments: it implements java.util.concurrent.ExecutorService to serve the applications requiring computational and data processing power.

With IExecutorService, you can execute tasks asynchronously and perform other useful tasks. If your task execution takes longer than expected, you can cancel the task execution. Tasks should be Serializable since they will be distributed.

In the Java Executor framework, you implement tasks two ways: Callable or Runnable.

Callable: If you need to return a value and submit to Executor, implement the task as java.util.concurrent.Callable.
Runnable: If you do not need to return a value, implement the task as java.util.concurrent.Runnable.
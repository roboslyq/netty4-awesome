 # NIO相关知识点

# 1、持续可读事件：CPU 100%

- selector.select(...) not block [may be cpu 100%,specially when client cut the current channel(connection)].

  **e.g**:

  ```java
  Iterator<SelectionKey> it= selector.selectedKeys().iterator();
  while(it.hasNext()){
       SelectionKey key = it.next();
       //业务处理KEY
       doSomething(key);
       //必须要删除Key，否则CPU空转
  	 it.remove();
       //必须更改感兴趣的事件类型(根据实际情况来定)，否则CPU空转[may be cpu100%]
       selector.interestOps(sk.interestOps()& (~sk.readyOps());
       //不要将op_write事件注册到selector中，否则很容易发生空转[may be cpu100%]。因为大多数Channel都是可写的
       //如果调用了wakeup()方法,下一下select()将不会阻塞。
  }
  ```

- **CPU空转原因**

**现象：**

​	Java nio开发中selector.select()进行阻塞等待事件发生。当channel有数据到来时会触发READ事件,这时在OP_READ的条件判断中加入向线程池通知要线程进行read的操作。这应该是nio多线程服务器的一般做法吧。
问题在于这个read操作与select（）在同一个线程里，这样造成了当read操作没有做完之前，channel里面仍然有数据，每次循环到select（）都会发现这个channel的读缓冲区有数据，频繁触发read事件并向线程池提交。对同一次接收消息有可能产生很多的read提交请求，造成不必要的麻烦。
我用key.cancel（）可以在提交事件以后撤除注册的事件，但是想再完成read（buff）以后再注册就怎么样的不能成功了。想问下遇到这个问题有没有解决办法，是不是我思路错了，还是说nio就是会在缓冲区没读/写完前不停的产生事件，属于正常情况只需加入限制即可？

**理论**

​	NIO中的边缘触发和水平触发，于是仔细看了下这两个概念：
  		(1) 水平触发是当就绪的fd未被用户进程处理，下一次select()查询依旧会返回，这是select和poll的触发方式。
  		(2) 边缘触发是无论就绪的fd是否被处理，下一次不再返回。理论上性能更高，但是实现相当复杂，并且任何意外的丢失事件都会造成请求处理错误。epoll默认使用水平触发，通过相应选项可以使用边缘触发。

- **解决方案一：加入队列**

> 这个是正常的，数据未读完是会不断地读取的。
>  1、在启动读线程前，检查一下队列中是否存在这个key，如果存在就不启动，避免“每次循环到select（）都会发现这个channel的读缓冲区有数据，频繁触发read事件并向线程池提交。”
  2、如果队列中不存，则向队列添加这个key，然后启动读线程处理。
  3、当读线程完成时，从队列中删除所处理的key，注意对队列的操作需要线程互斥。
  此外，如果读线程处理的缓慢，可能会会反复操作“1”，这时需要比较一下selector.select()的数值与队列中的key数量，如果接近则睡眠一段时间，等待读线程处理完毕，然后再处理。

  

- 参考资料


  https://stackoverflow.com/questions/4139300/socketchannel-fires-isreadable-but-nothing-to-read

  

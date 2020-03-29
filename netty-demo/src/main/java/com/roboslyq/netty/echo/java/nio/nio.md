 selector.select(...) not block [may be cpu 100%,specially when client cut the current channel(connection)].

e.g:

Iterator<SelectionKey> it=selector.selectedKeys().iterator();

...for/while it.hasNext()...
it.remove();<------*****must do it. or Keys' Set.clear() finally;

 

if remove the current selectedKey from selector.selectedKeys()[Set] but don't sk.interestOps(sk.interestOps()& (~sk.readyOps()));将导致 selector.select(...) not block [select() not block several times, or excepted exception]

 

4.op_write should not be registered to the selector.   [may be cpu100%]

 

5. if involving  wakeup() before select() [wakeup called several times >=1],the next select() not block [not block just once].

 

尽管以前有些人分析了nio的wakeup性能及not block in linux的bug,但是java nio依然是高效的,那些c/c++的牛人们应该去关注一下jre/bin目录下的nio.dll[windows上的jdk1.6还没有使用IOCP] , ./jdk/jre/lib/amd64/libnio.so[linux上的jdk1.6已经使用epoll了].

基于java nio的服务器:mina,xSocket,girzzly[glassfish],jetty(基于girzzly),tomcat6[可以配置Http11NioProtocol]...其中从本人对girzzly,tomcat6的源码分析来看,它们都还没有真正发挥出nio异步处理请求的优点,它们的读写还都是blocking的虽然使用了selectorPool,此外tomcat6要剥离出socket通信还要花费一定的功夫.而mina,xSocket却是名不符其实.




ava nio开发中selector.select()进行阻塞等待事件发生。当channel有数据到来时会触发READ事件,这时在OP_READ的条件判断中加入向线程池通知要线程进行read的操作。这应该是nio多线程服务器的一般做法吧。
问题在于这个read操作与select（）在同一个线程里，这样造成了当read操作没有做完之前，channel里面仍然有数据，每次循环到select（）都会发现这个channel的读缓冲区有数据，频繁触发read事件并向线程池提交。对同一次接收消息有可能产生很多的read提交请求，造成不必要的麻烦。
我用key.cancel（）可以在提交事件以后撤除注册的事件，但是想再完成read（buff）以后再注册就怎么样的不能成功了。想问下遇到这个问题有没有解决办法，是不是我思路错了，还是说nio就是会在缓冲区没读/写完前不停的产生事件，属于正常情况只需加入限制即可？

> 这个是正常的，数据未读完是会不断地读取的。
> 1、在启动读线程前，检查一下队列中是否存在这个key，如果存在就不启动，避免“每次循环到select（）都会发现这个channel的读缓冲区有数据，频繁触发read事件并向线程池提交。”
  2、如果队列中不存，则向队列添加这个key，然后启动读线程处理。
  3、当读线程完成时，从队列中删除所处理的key，注意对队列的操作需要线程互斥。
  此外，如果读线程处理的缓慢，可能会会反复操作“1”，这时需要比较一下selector.select()的数值与队列中的key数量，如果接近则睡眠一段时间，等待读线程处理完毕，然后再处理。
  
  
  
  
  Java NIO持续触发读事件的解决方法
  原创冷血有情556 最后发布于2019-03-15 17:25:02 阅读数 483  收藏
  展开
  1. 现象
  最近在一次项目编码中遇到这么一个情况： 程序本身是一个中间处理器的角色，功能可简述为接收硬件设备的报文，然后对某些报文处理（其他报文直接转发），然后发送服务端处理；服务端处理后回复，程序接收服务端回复，然后直接转发给硬件设备。在测试时发现，硬件设备第一次发送报文，能够正常处理，但是当硬件设备第二次发送报文时，程序中Java NIO会持续触发读事件，导致服务端持续回复报文，从而设备持续收到回复报文。
  
  2. 问题排查
  由于程序中很多地方使用到线程池、队列等，一开始排查是否是这些因素导致的，但是经过仔细的控制变量的测试，发现并不是这些导致的。在这个问题上纠结了半天多，感觉崩溃的时候，决定去网上找找思路，然后看到一个同仁遇到类似问题，其中说到NIO中的边缘触发和水平触发，于是仔细看了下这两个概念：
  (1) 水平触发是当就绪的fd未被用户进程处理，下一次select()查询依旧会返回，这是select和poll的触发方式。
  (2) 边缘触发是无论就绪的fd是否被处理，下一次不再返回。理论上性能更高，但是实现相当复杂，并且任何意外的丢失事件都会造成请求处理错误。epoll默认使用水平触发，通过相应选项可以使用边缘触发。
  
  由于开发、测试是在windows平台下进行的，windows中Java NIO的底层网络模型是select模型，所以其触发方式为水平触发。所以上面遇到的情况很可能是由于就绪的fd未被处理，导致每次都会返回该事件。于是沿着这个方向排查。
  
  经过排查，最终发现了原因：由于服务端在处理读事件时，将通道中的数据读到ByteBuffer中。将数据写入ByteBuffer后，经过一系列处理，该ByteBuffer的limit和position相等了，第二次再使用这个ByteBuffer来读通道中的数据时，将无法将数据写入该ByteBuffer，即就绪fd未被处理，导致下次selector调用select()方法时，又会返回该事件。
  
  3. 解决方法
  找到问题的原因后，解决办法还是比较简单的：
  (1) 如果当前ByteBuffer中的数据不完整，需要等到下次读事件的数据一起处理，那么可以在这次读完（即将数据写入ByteBuffer中，一般写完后会调用filp()等待数据被读取）后调用ByteBuffer的compact()方法；
  
  ByteBuffer的compact()方法会将position置为limit，limit设置为capacity，这样下次处理读事件时，通道中的数据可以接着写在上一次数据的后面。
  
  (2) 如果当前ByteBuffer中的数据是完整的，则从该ByteBuffer读完数据后，将该ByteBuffer置为null，下一次处理读事件时，重新初始化该ByteBuffer即可。
  
  
  https://stackoverflow.com/questions/4139300/socketchannel-fires-isreadable-but-nothing-to-read
  
  
  
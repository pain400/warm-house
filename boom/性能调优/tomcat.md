## TOMCAT 优化

#### 内存优化

当应用程序需要的内存超出堆的最大值时虚拟机就会提示内存溢出，并且导致应用服务崩溃。因此一般建议堆的最大值设置为可用内存的最大值的 80%。Tomcat 默认可以使用的内存为 128MB，较大型的应用项目需要调大

Tomcat 默认可以使用的内存为 128MB，在文件 /bin/catalina.sh 前面增加如下设置：JAVA_OPTS='-Xms [初始化内存大小] -Xmx [可以使用的最大内存] -XX:PermSize=64M -XX:MaxPermSize=128m'

32G 内存配置示例：
JAVA_OPTS="$JAVA_OPTS -Xms10g -Xmx10g -XX:PermSize=1g -XX:MaxPermSize=2g -Xshare:off -Xmn1024m

#### 线程优化
在配置文件 server.xml 中配置
maxThreads：Tomcat 使用线程来处理接收的每个请求。这个值表示 Tomcat 可创建的最大的线程数，默认值 150
acceptCount：当所有可以使用的处理请求的线程数都被使用时，可以放到处理队列中的请求数，超过这个数的请求将不予处理，默认值 10
minSpareThreads：Tomcat 初始化时创建的线程数，默认值 25
maxSpareThreads：一旦创建的线程超过这个值，Tomcat 就会关闭不再需要的 socket 线程，默认值 75

enableLookups：是否反查域名，默认值为 true，为了提高处理能力，设置为 false
connnectionTimeout：网络连接超时，默认值 60000ms。设置为 0 表示永不超时，通常可设置为 30000ms
maxKeepAliveRequests：保持请求数量，默认值 100
bufferSize：输入流缓冲大小，默认值 2048 bytes
compression： 压缩传输，取值on/off/force，默认值 off

其中和最大连接数相关的参数为 maxThreads 和 acceptCount。如果要加大并发连接数，应同时加大这两个参数

32G 内存配置示例：
<Connector port="8080" protocol="HTTP/1.1"
        connectionTimeout="20000"
        maxThreads="1000"
        minSpareThreads="60"
        maxSpareThreads="600"
        acceptCount="120" 
        redirectPort="8443"
        URIEncoding="utf-8" />





```sh
# 日志切割
/etc/logrotate.d/nginx
# 核心配置
/etc/nginx/nginx.conf
/etc/nginx/conf.d/default.conf
```

sendfile 不通过用户空间直接通过内核空间

```sh
vim nginx.conf

# 设置 nginx 服务的系统使用用户
user
# 工作进程数
worker_processes
# nginx 错误日志
error_log
# nginx 服务启动时的 pid
pid

events 模块
# 每个进程允许最大连接数
worker_connections 10000 - 65535
# nginx 内核模型
use

http 模块

server 模块
listen 监听端口
server_name 域名
location 路径访问
```

```sh
location / {
  root /usr/share/nginx/html;
  index index.html index.htm;
}

error_page 500 502 503 504 404 /50x.html;
location = /50x.html {
  root /usr/share/nginx/html;
}
```

```sh
log_format 配置在 http 模块下

# 打印 http 头信息
log_format main '$remote_addr - $remote_user'
                '"$http_refer" "request" "$http_user_agent"'
                '"$http_x_forward_for" $status $body_bytes_sent';
access_log /var/log/nginx/access.log main;
```

```sh
# 查看请求头 响应头信息
curl -V http://www.baidu.com > /dev/null
```

## 常用模块
### http_stub_status_module
提供基本信息访问
--with-http_stub_status_module
```sh
location /status {
  # 查看 nginx 状态
  stub_status;
}
```

### http_random_index_module
从目录下挑选一个随机文件
--with-http_random_index_module
```sh
# 随机返回页面
location /random/ {
  root /usr/share/nginx/html;
  random_index on;
}
```

### http_sub_module
通过替换字符串来修改响应内容
--with-http_sub_module
```sh
location /filter {
  # 内容替换 => 替换指定字符串
  sub_filter '<a href="http://127.0.0.1:8080/'  '<a href="https://$host/';
  sub_filter '<img src="http://127.0.0.1:8080/' '<img src="https://$host/';
  # 内容全局替换
  sub_filter_once off;
}
```

### http_limit_req_module
限制处理请求速率
```sh
http {
  # 限制同一 ip 客户端，每秒 1 次请求
  limit_req_zone $binary_remote_addr zone=req_zone:10m rate=1r/s;
  limit_req_zone $server_name zone=perserver:10m rate=10r/s;

  location / {
    limit_req zone=req_zone;
    # 多出的 3 次请求延迟到下一次发送
    # limit_req zone=req_zone burst=3 nodelay;
    limit_req zone=perserver burst=10;
  }
}
```

### http_limit_conn_module
限制连接数量
```sh
http {
  limit_conn_zone $binary_remote_addr zone=conn_zone:1m;
  limit_conn_zone $server_name zone=perserver:10m;

  location / {
    # 限制每秒一个连接
    limit_conn conn_zone 1;
    limit_conn perserver 100;
  }
}
```

### http_access_module
```sh
# 基于 ip 控制
location ~ ^/admin.html {
  deny  192.168.1.1;
  allow 192.168.1.0/24;
  # ip 段
  allow 10.1.1.0/16;
  deny  all;
}
```

### http_auth_basic_module
```sh
# 基于用户信息登录
# 访问需要提供用户名与密码
location ~ ^/admin.html {
  auth_basic "Please input username and password";
  # auth 文件
  auth_basic_user_file /etc/nginx/auth_conf;
}

# 生成 auth 文件
htpasswd -c auth_conf <user_name>
```



静态资源
1. sendfile on | off; 文件读取
2. tcp_nopush on | off; 多个包整合，大文件传输，配合 sendfile
3. tcp_nodelay on | off; keepalive 条件下实时传输
4. gzip on | off;
5. gzip_comp_level 1; 压缩比

### http_gzip_module
```sh
http {
  sendfile on;
  location ~ .*\.(jpg|gif|png)${
    gzip on;
    gzip_buffers 32 4k;
    gzip_http_version 1.1;
    gzip_comp_level 2;
    gzip_types text/plain application/javascript;
  }

  # 文本压缩比例大
  location ~ .*\.(txt|xml)${
    gzip on;
    gzip_http_version 1.1;
    gzip_comp_level 1;
    gzip_types application/x-javascript text/css;
  }
}
```

### http_gzip_static_module
--with-http_gzip_static_module
```sh
http {
  # gzip_static 使用预先压缩的文件传输
  location ~ ^/download{
    gzip_static on;
    tcp_nopush on;
  }
}
```


```sh
ab -n 20 -c 20 <url>
```

缓存
Expires Cache-Control
Etag
Last-Modified

Etag Last-Modified 向服务器验证内容是否过期
Last-Modified 精确到秒，Etag 比 Last-Modified 优先
```sh
location ~ .*\.(htm|html)${
  expires 24h;
  root ...
}
```

跨域访问
```sh
# 添加允许跨域访问的域名
location ~ .*\.(htm|html)$ {
  add_header Access-Control-Allow-Origin http://www.pain.com;
  add_header Access-Control-Allow-Methods GET,POST,PUT,DELETE,OPTIONS;
}
```

### http_proxy_module
```sh
location / {
  proxy_pass http://127.0.0.1:8080;
  proxy_redirect default;
  proxy_set_header Host $host;
  proxy_set_header X-Real-IP $remote_addr;
  proxy_connect_timeout 30;
  proxy_send_timeout 60;
  proxy_read_timeout 60;
  proxy_buffer_size 32k;
  proxy_buffering on;
  proxy_buffers 4 128k;
  proxy_busy_buffers_size 256k;
  proxy_max_temp_file_size 256k;
  proxy_next_upstream error;
  include proxy_params;
}
```
```sh
http {
  proxy_cache_path /opt/cache levels=1:2 keys_zone=pain_cache:10m max_size=10g
                              inactive=60m use_temp_path=off;

  server {
    location / {
      proxy_cache pain_cache;
      proxy_pass http://pain;
      proxy_cache_valid 200 304 12h;
      proxy_cache_valid any 10m;
      proxy_cache_key $host$uri$is_args$args;
      add_header Nginx-Cache "$upstream_cache_status";
      proxy_next_updtream error timeout invalid_header http_500 http_504;
    }

    # 部分请求不缓存
    if ($request_uri ~ ^/(login|register)) {
      set $cookie_nocache 1;
    }

    location / {
      proxy_no_cache $cookie_nocache $arg_nocache$arg_comment;
    }
  }
}
```

### http_rewrite_module
```sh
if ($http_user_agent ~ MSIE) {
  rewrite ^(.*)$ /msie/$1 break;
}
if ($request_method = POST) {
  return 405;
}
```
```sh
server {
  root /opt/html;
  location ~ ^/break {
    # 转到 root 目录下查找
    rewrite ^/break /test/ break;
  }
  location ~ ^/last {
    # 再次跳转请求 /test
    rewrite ^/last /test/ last;
  }
  location ~ ^/redirect1 {
    # 302 临时重定向，浏览器不缓存
    rewrite ^/last /test/ redirect;
  }
  location ~ ^/redirect2 {
    # 永久重定向，浏览器缓存
    rewrite ^/last /test/ permanent;
  }
  location ~ ^/test {
    default_type application/json;
    return 200 '{"status":"ok"}';
  }
  rewrite ^/course-(\d+)-(\d+)-(\d+)\.html$ /course/$1/$2/course$3.html break;
}
```

### http_referer_module
```sh
location  ^.*\.(jpg|gif|png)${
  valid_referers none blocked server_names
               *.example.com example.* ~\.google\.;;
  if($invalid_refer) {
    return 403;
  }
}
```
```sh
location / {
  if($http_x_forwarded_for !~* "^1\.1\.1\.1") {
  }
}
```

```sh
# 重启
systemctl restart nginx.service
systemctl reload nginx.service
```

### http_upstream_module
轮询 -> 加权轮询
```sh
upstream server_lb { // 服务器列表
    server 127.0.0.1:8080 weight=10;
    server 127.0.0.1:8081 weight=2;
    server 127.0.0.1:8002 down; # 不提供服务
    server 127.0.0.1:8003 backup; # 预留备份服务
    server 127.0.0.1:8004 max_fails=1 fail_timeout=10s;
}
server {
    listen 80;
    server_name www.pain.com; // hosts 文件配置
    location / {
        root html;
        proxy_pass http://server_lb; // 反向代理
        index index.jsp index.html index.htm;
    }
}
```
基于 url 哈希
```sh
upstream backend {
  hash $request_uri;
  server 1.1.1.1:8001;
  server 1.1.1.1:8002;
}
```
```sh
# 禁用 8002 端口
iptables -I input -p tcp --dport 8002 -j DROP
```
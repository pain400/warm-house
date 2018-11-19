## nginx
### 安装
```sh
sudo wget <nginx-url>

# 安装依赖
# 包括 gcc perl zlib openssl
sudo yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel

sudo yum -y install gcc-c++ autoconf pcre make automake

sudo yum -y install httpd-tools

sudo tar -zxvf nginx-1.10.2.tar.gz
cd nginx

# 查询详细参数
./configure --help

sudo ./configure
sudo make
sudo make install
```

### 配置
```sh
# 配置文件
cd /usr/local/nginx/
cd /conf
sudo vim nginx.conf

# 在 HTTPS 节点上方
include vhost/*.conf;

sudo mkdir vhost
cd vhost

# host 文件修改
sudo vim /etc/hosts
# 添加对应域名及 ip
<ip> www.pain.com
```

```sh
# 启动
sudo ./nginx

# 重新加载配置文件
sudo ./nginx -s reload

# 加载指定配置文件
sudo ./nginx -c <nginx file>

# 测试配置文件
sudo ./nginx -t <nginx file>

# 停止
sudo ./nginx -s stop

# 退出
sudo ./nginx -s quit

# 查看进程
ps -ef | grep nginx

# 平滑重启
kill -HUP <pid>

# 关闭自动索引（安全）
autoindex off;

# 文件目录转发方式时打开自动索引
autoindex on;
```
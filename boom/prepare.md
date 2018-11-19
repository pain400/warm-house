## 环境搭建
### 软件安装
```sh
# 安装依赖
yum -y install gcc gcc-c++ autoconf pcre pcre-devel make automake
# 安装工具
yum -y install wget httpd-tools vim
```

### 网络连接
```sh
# 网络确认
ping www.google.com
```

### 软件源
```sh
# 检查 yum 源
yum list | grep gcc
```
```sh
# /etc/repo
# 参考官网添加 nginx yum 源
[nginx]
name=nginx repo
baseurl=http://nginx.org/packages/centos/7/$basearch/
gpgcheck=0
enabled=1
```

### 防火墙
```sh
# 查看 iptables 规则
iptables -L
# 关闭 iptables 规则
iptables -F
```
```sh
# 查看 SELinux 状态
getenforce
# 关闭 SELinux
setenforce 0
```

## Nginx 基本命令
```sh
nginx -v
# 查看编译参数
nginx -V
```
---
title: Linux 软件安装
categories:
  - OS
  - Linux
  - 软件安装
---

### apt-get
#### 安装与卸载
```sh
# 更新源服务器列表
sudo vi /etc/apt/sources.list
# 升级列表中的软件包
sudo apt-get update
# 安装包
sudo apt-get install package
# 删除包
sudo apt-get remove package
# 删除包，包括配置文件等
sudo apt-get remove --purge package
# 重新安装包
sudo apt-get --reinstall install package
# 修复安装
sudo apt-get -f install
# 安装相关的编译环境
sudo apt-get build-dep package

# 升级所有已安装的软件
sudo apt-get upgrade
# 升级系统
sudo apt-get dist-upgrade
# 清理无用的包
sudo apt-get clean && sudo apt-get autoclean
# 检查是否有损坏的依赖
sudo apt-get check
```
#### 搜索与查询
```sh
# 搜索软件包
sudo apt-cache search package
# 获取包的相关信息
sudo apt-cache show package
# 了解使用该包依赖
sudo apt-cache depends package
# 查看该包被依赖
sudo apt-cache rdepends package
```
#### 下载源码
```sh
# 下载该包的源代码
sudo apt-get source package
```

### yum
```sh
# 下载并安装一个rpm包
yum install package_name
# 更新一个rpm包
yum update package_name 
# 删除一个rpm包
yum remove package_name 
# 列出当前系统中安装的所有包
yum list
# 列出已安装的包
yum list installed
# 查看是否安装
yum list xterm
# 在rpm仓库中搜寻软件包 
# 列出更新
yum list updates
yum search package_name
# 卸载软件及删除文件
yum erase xxxname
# 清理rpm缓存删除下载的包
yum clean packages
# 删除所有头文件
yum clean headers
# 删除所有缓存的包和头文件
yum clean all
# 查看什么软件提供了配置文件 /etc/yum.conf
yum provides /etc/yum.conf
```

### 源码安装
- 解压缩源代码包
- cd dir
- 检测文件是否缺失，创建Makefile，检测编译环境
```sh
./configure
```
- 编译源码，生成库和可执行程序
```sh
make
```
- 把库和可执行程序，安装到系统路径下
```sh
sudo make install
```
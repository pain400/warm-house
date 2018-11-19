---
title: Linux 基础命令
categories:
  - OS
  - Linux
  - 操作命令
---

### ls 命令
-a 列出隐藏文件,文件中以"."开头的均为隐藏文件
-l 列出文件的详细信息，包括文件类型与权限，连接数，文件所有者，文件所属组，文件大小，最近修改时间，文件名字
-R 连同子目录中的内容一起列出
```sh
# 区分目录与文件
ls -F
# 递归输出
ls -F -R
# 显示块大小，索引节点
ls -sial

# 通配符
ls -l pro?
ls -l pro*
```
#### ls 列出的文件类型种类
-：普通文件
d：目录
l：符号链接
b：块设备文件
c：字符设备文件
p：命名管道
s：socket文件

### rm 命令
```sh
# 删除提示
rm -i a.txt
# 强制删除
rm -f a.txt
# 递归删除
rm -r /a/b
# 删除目录下所有文件
rm -r *
```

### cp 命令
```sh
# 拷贝文件的同时修改名字
cp a.txt ../b.txt
# 提示是否覆盖文件
cp -i a.txt ../hello.c
# 拷贝目录
cp -r dir1 dir2
cp -r dir1 ~/

# 拷贝生成链接文件
cp -l test hardlink
cp -s test softlink
```

### mv 命令
视mv第二个参数的不同，mv命令将文件重命名或者移动到新的目录
-i 如果在移动的过程中存在重名的，进则行提示是否覆盖
-f 若果在移动的过程中存在重名的，则直接进行覆盖，不会给出提示
```sh
# 将文件移动到上一级目录
mv a.txt ../
# 文件重命名
mv a.txt b.txt
```

### grep 命令
-R: 连同子目录中所有文件一起查找
-l: 查询多文件时只输出包含匹配字符的文件名
-n: 显示匹配行及行号
-v: 显示不包含匹配文本的所有行
-c: 只显示匹配行的数量
-i: 比较不区分大小写
```sh
# 在指定文件中查找指定字符
grep printf stdio.h
# 到系统头文件目录下查找所有包含printf的文件
grep -R 'printf' /usr/include

# 反向查找字符
grep -v printf stdio.h
# 查找结果包括行号
grep -n printf stdio.h
# 在当前目录下的所有文件中查找
grep atoi *

# 用dmesg列出内核信息，然后用grep找出包含eth的那行，将前2行和后3行也列出来
dmesg | grep -n -A3 -B2 –color=auto 'eth'
```
#### grep 与正则表达式
- 利用中括号 [] 来查找集合字符
```sh
# 查找man或者men字符串
grep -n 'm[ae]n' manpath.config
# 要取得有数字的那一行
grep -n '[0-9]'manpath.config
grep -n '[[:digit:]]' manpath.config
```
- 反向选择^的使用
```sh
# 查找包含man而且前面没有/的那一行
grep -n '[^/]man' manpath.config
# 查找包含man但是前面不是小写字符的那一行
grep -n '[^a-z]man' manpath.config
```
- 行首^和行尾$字符
```sh
# ^ 在[]内面代表反向选择，在[]外面代表定位在行首的意思    

# 显示当前目录下所有子目录的详细信息
ls -l | grep ^d
# 列出开头是大写字符的那一行
grep -n '^[A-Z]' manpath.config
# 列出开头不是英文字母的行
grep -n '^[^a-zA-Z]' manpath.config

# 显示当前目录下以c结尾的文件
ls -l | grep $c
# 找出行尾结束为.的行
grep -n '\.$' manpath.config
# 查找出空白行
grep -n '^$' manpath.config
```
- 任意一个字符.与重复字符*
```sh
# 查找包含一个o以上的行，需要oo*
grep -n 'oo*' manpath.config
# 查找以g开头与以g结尾，中间至少存在一个o的行
grep -n 'goo*g' manpath.config
```
- 限定连续字符范围
```sh
# {}的符号在shell有特殊意义，因此要用到转义字符\
# 找出g后面有两个到五个o后面再接一个g的字符串
grep -n '\{2,5\}g' manpath.config
# 2个以上
grep -n '\{2,\}g' manpath.config
```
- 特殊的符号
[:alnum:]   代表英文大小写字符及数字
[:blank:]   代表空格和tab按键
[:punct:]   代表标点符号
[:space:]   任何会产生空白的字符
[:alpha:]   代表任何英文大小写字符
[:digit:]   代表数字
[:lower:]   代表小写字符
[:upper:]   代表大写字符

### find 命令
列出所有目录：-type d
只列出普通文件：-type f
只列出符号链接：-type l
```sh
# 根据文件名进行查询
find ./ -name 'stdio.h'
# 在当前目录下查找文件名为file.c并且为目录的文件
find ./ -name file.c -a -type d
# 在当前目录下查找文件名为hello.c或者hell.c的文件
find ./ -name hello.c -o -name hell.c
# 在当前目录下查找文件名不是main.c的文件
find ./ !-name main.c
# -iname 忽略字母大小写

# 查找gcc命令并添加到PATH中
find / -name gcc
# 在.profile文件中添加
PATH=$PATH:/usr/sfw/bin
export PATH
```
```sh
# -print指明打印出匹配的文件名
# -print0指明使用'\0'定界符来打印每一个匹配的文件名
find . -name "*.c" -print0
```

### chmod 命令
```sh
# chmod默认对所有用户添加a.c的写权限
chmod +w a.c
# 所有的用户对a.c失去读权限
chmod a-r a.c
chmod ugo-r a.c
chmod ug-r,o-r a.c
chmod -r a.c

# 对文件所有者去除所有权限
chmod u-rwx a.txt
# 文件所在组去除写权限
chmod g-w a.txt

# 修改文件权限的其他方式
chmod 777 a.txt
```
#### umask
umask指定用户创建文件时的掩码
```sh
# 显示当前的umask设置
umask
umask -S
```

### chown 命令
-R 递归式地改变指定目录及其下的所有子目录和文件的拥有者
```sh
chown user1 a.txt
# 把一个文件改为pain用户和root用户组所有
chown pain:root a.txt
```

### chgrp 命令
-R 递归式地改变指定目录及其下的所有子目录和文件的属组
```sh
chgrp user1 a.txt
```

### 管道
#### 分页
```sh
ls -l | more
ls -l | less
cat stdio.h | less
```
#### 命名替换
```sh
# 重定向
ls | cat aa
ls > aa
# 命名替换，列出aa中所有文件信息
ls -l `cat aa`
ls -l $(cat aa)
```

### 重定向
#### 输出重定向
```sh
# >> 相对于 > 可以叠加，而不会覆盖

find /home –name .bashrc > list
# 正确输出到 list，出错信息输出到list_error
find /home –name .bsdhrc > list 2> list_error
# 忽略错误信息
find /home –name .bashrc 2> /dev/null
# 将正确数据和错误数据写入同一个文件
find /home –name .bashrc >list 2> &1
find /home –name.bashrc &>list 
```
#### 输入重定向
```sh
# 用cat将.bashrc中的内容输入到catfile中
cat > catfile< ~/.bashrc
# 用cat直接将输入的信息输出到catfile中，由键盘输入eof结束
cat > catfile<< "eof"
This is a test
OK,I willstop.
eof
```

### touch 命令
```sh
# 将a.txt的访问及修改时间都更新为目前的时间
touch a.txt
```
#### 创建空文件
```sh
echo > a.txt
touch b.txt
cat > c.txt
vi d.txt
```

### file 命令
```sh
# 查看文件类型
file a.txt
```

### stat 命令
```sh
stat test
```

### ln 命令
#### 硬链接
链接文件和被链接文件必须位于同一个文件系统中，并且不能建立指向目录的硬链接
```sh
ln hello word_h
```
#### 软链接
```sh
ln -s hello word_s
```

### 文件查看
#### head
```sh
# 查看首部
head -n -5 file1
```
#### tail
```sh
tail -n -5 file1
# 监测系统日志
tail -f
```
#### cat
-b 对非空输出行编号
-E 在每行结束处显示$
-n 对输出的所有行编号
-s 不输出多行空行
#### wc
若不指定文件名称、或是所给予的文件名为“-”,则从标准输入设备读取数据
```sh
# 显示Bytes数
wc -c
# 显示列数
wc -l
# 显示字数
wc -w
```
#### od
-t 指定数据的显示格式,主要的参数有:
-c ASCII字符或反斜杠序列
-d[SIZE] 有符号十进制数,每个整数SIZE字节
-f[SIZE] 浮点数,每个整数SIZE字节
-o[SIZE] 八进制(系统默认值为02),每个整数SIZE字节
-u[SIZE] 无符号十进制数,每个整数SIZE字节
-x[SIZE] 十六进制数,每个整数SIZE字节
```sh
od -tcx file1
```

### which 命令
```sh
# 查看命令所在的路径
which ls
```

### 操作系统信息
```sh
# 查看内核版本信息
uname -a
# 查看发行版信息
lsb_release -a
```
/proc/cpuinfo 显示CPU info的信息 
/proc/interrupts 显示中断 
/proc/meminfo 校验内存使用 
/proc/swaps 显示哪些swap被使用 
/proc/version 显示内核的版本 
/proc/net/dev 显示网络适配器及统计 
/proc/mounts 显示已加载的文件系统 

### ps 命令
```sh
# 只显示当前控制台下当前用户的进程
ps
# 显示终端运行的所有进程，包括其他用户进程
ps -a
# 显示没有控制终端的进程
ps -x
# 显示进程的详细信息
ps -u
# 显示正在运行的进程
ps -r
# 加宽显示更多
ps -w
# -e显示所有运行在系统上的进程 -f扩展输出
ps -ef
# -l长格式输出
ps -l
# -H层级格式
ps -efH
```

### top 命令
显示进程动态信息，内存使用率排序 M，CPU占用率排序 P，进程运行时间排序 T

### netstat 命令
```sh
# 列出所有端口
netstat -a
# 列出所有 tcp 端口
netstat -at
# 列出所有 udp 端口
netstat -au
# 只显示监听端口
netstat -l
# 只列出所有监听 tcp 端口
netstat -lt
# 只列出所有监听 udp 端口
netstat -lu
# 持续输出 netstat 信息
netstat -c
```

### tcpdump
```sh
# 查看本机与mysql的操作命令，注意 -i any表示监听所有网络接口
tcpdump -i any -w - dst port 3306 |strings
# 查看本机58895上与mysql的命令
tcpdump -i any -w - dst port 3306 and src port 58895 |strings
```

### free
```sh
// 查看空闲内存
free -m
# 终端翻页
Shift-pageup
Shift-pagedown
# 新开终端
ctrl+shift+n
# 新开一个终端标签
ctrl+shift+t
# 标签中切换
alt+1
```

### who 命令
```sh
# 用来显示当前shell下的后台作业
who -uH
# 显示登录用户
who
whoami
```

### kill 命令
```sh
# 向指定进程发送信号
kill
# 查看信号编号
kill -l
# 给一个进程发信号,或终止一个进程的运行
kill -SIGKILL pid
```

### 作业命令
```sh
# 如果在命令行的末尾加上&字符,则shell为这个命令创建一个后台进程
jobs
# 挂起当前进程
Ctrl-z
# 把指定的后台作业或挂起作业移到前台运行
fg [job]
# 把被挂起的进程提到后台执行
bg [job]
```

### 用户信息与管理
- 添加用户
```sh
# 只是新建用户，没有密码，没有主目录
useradd user1
# 新建用户，并为其指定主目录
useradd -d /home/user1 -m user1
# 给用户user1设置密码
pssswd user1
# 查询用户的信息
finger
```
- 删除用户
```sh
# 同时删除用户目录及邮件池
userdel -r user1
# 不论用户是否存在都强制删除
userdel -f user1
```
- 用户配置文件修改
```sh
# 查看当前进程环境变量
env
# 配置系统环境变量
vim /etc/profile
# 配置当前用户环境变量
rethat .bash_profile
ubuntu .profile
unix .profile
# 修改PATH，PATH保存所有路径用冒号分隔
# 让修改后的配置文件生效
. .bash_profile
source .bash_profile
# 修改配置文件，对所有用户有效，修改系统文件需要重启操作系统
/etc/profile
PATH=$PATH:.
export PATH
```
- 配置root
```sh
sudo passwd
```

### 压缩打包
#### tar 命令
-c 生成档案文件
-v 列出归档解档详细过程
-f 指定档案文件名称
```sh
# 将a.txt与b.txt归档为a.tar
tar -cvf a.tar a.txt b.txt
# 将a.tar解档
tar -xvf a.tar
# 将目录归档
tar -cvf a.tar /a/b
# 只是列出tar包中的文件
tar -tvf a.tar
```
#### 压缩与解压缩文件
```sh
# gzip压缩与解压缩之后，之前的文件不保留
# 压缩tar包
gzip a.tar
# 解压缩tar包
gzip -d a.tar.gz

# rar压缩与解压缩
# 把dir压缩成newdir.rar
rar a -r newdir dir
# 把newdir.rar解压缩到当前目录
unrar x newdir.rar

# zip压缩
zip -r dir.zip dir
# zip解压缩
unzip dir.zip
```
#### 归档压缩与解压缩
f 使用档案文件或设备
c 创建新的档案文件
x 从档案文件中释放文件
t 列出档案文件的内容,查看已经备份了哪些文件
r 把要存档的文件追加到档案文件的未尾
v 详细报告tar处理的文件信息
z 用gzip来压缩/解压缩文件
j 用bzip2来压缩/解压缩文件
```sh
# 查看归档文件包含内容
tar -tvf a.tar
# 打包解包
tar cvf dir.tar dir
tar xvf dir.tar dir
# 打gz压缩包与解gz压缩包
tar zcvf dir.tar.gz dir
tar zxvf dir.tar.gz
# 打bz2压缩包与解bz2压缩包
tar jcvf dir.tar.bz2 dir
tar jxvf dir.tar.bz2
# 指定目录解压缩:
tar zxvf dir.tar.gz -C ~/test
```

### man 命令
```sh
# 下一页：space
# 上一页：b
# 查找：/word
# 不带参数表示查看linux命令的说明文档
man printf
# 2代表系统调用函数
man 2 printf
# 3代表C库函数
man 3 atoi
# 5代表第三方库函数
man 5 atoi
# 以read为关键字查找相关的man page
man -k read

man bash (PROMPTING)
```

### 网络配置
NAT: 共享IP，使用windows的网卡
桥接: 虚拟一个网卡，使用自己的IP
```sh
# 查看网卡信息
ifconfig
# 关闭网卡
sudo ifconfig eth0 down
# 开启网卡eth0
sudo ifconfig eth0 up
# 给eth0配置临时IP
sudo ifconfig eth0 IP

# 查询机器的IP地址和其对应的域名，输入exit并回车即可
nslookup
```

### df 命令
```sh
# 磁盘占用和空余信息
df -h
```

### du 命令
```sh
# 某一个文件占用空间
du
# 以M为单位
du -hm /home/pain/test
# 以B为单位
du -hb ./*
# 以K为单位,4k的整数倍
du -hk ./*

# 显示所有已列出文件总大小
du -c
# 显示每个输出参数的总计
du -s
# 显示所有已列出文件总大小
# -S 为每个目录与子目录分别提供总计
du -S /var/log
# 查看大空间使用
du -S /var/log | sort -rn
```

### mount
列出当前系统上挂载的设备列表，包含设备文件名、挂载点、文件系统类型、访问状态
```sh
# 手动挂载
mount -t type device directory
# 将U盘/dev/sdb1/挂载到/media/disk上
mount -t vfat /dev/sdb1 /media/disk

# -o 参数允许挂载文件系统时添加一些以逗号分割的额外选项
# 只读挂载
ro
# 可读写挂载
rw
# 挂载一个文件
loop

# 将一个iso文件挂载到目录下
mkdir mnt
mount -t iso9960 -o loop CentOS7.iso mnt
# 卸载可移动设备
umount /home/pain/mnt
```

### mkfs
pc机上最多4个IDE设备
IDE1的主盘：/dev/hda
IDE1的从盘：/dev/hdb
IDE2的主盘：/dev/hdc
IDE2的从盘：/dev/hdd
IDE1的主盘第一分区：/dev/hda1
IDE1的从盘第一逻辑分区：/dev/hdb5
系统的第一个SCSI硬盘：/dev/sda
软盘驱动器：/dev/fd0
光盘驱动器：/dev/cdrom
```sh
# 格式化磁盘
mkfs
```

### 字符集
```sh
# 查看字符集
locale
# 修改为全英文，在配置文件中配置
LANG=en
export LANG
# linux支持的字符集
iconv --list
```

### shutdown 命令
```sh
# 立刻关机
shutdown -h now 
# 10:42 分关机
shutdown -h 10:42 'shut down' 

# 立刻重新开机
shutdown -r now
# 10 分钟后重新开机
shutdown -r 10 'shut down'
# 立刻重新开机, 但在警告和删除processes之间延迟30秒钟
shutdown -t30 -r now 

# 发出警告讯息, 但没有真的关机
shutdown -k now 'shut down' 
# 将刚才下的 shutdown 指令取消,必须切换至其它tty执行
shutdown -c 
# 切换至单人操作模式(不加任何选项时)
shutdown now
```

### init 命令
run level 0: 关机
run level 3：纯命令行模式
run level 5：含有图形界面模式
run level 6：重启

### dd
```sh
dd if=/dev/hda of=/dev/fd0 bs=512 count=1
```
---
title: vim 使用
categories:
  - OS
  - Linux
  - vim
---

### 光标移动
- 上下左右
```
lkhl
```
- 屏单位移动
```sh
# 向下半屏
ctrl-d
# 向上半屏
ctrl-u
# 向下一屏
ctrl-f
# 向上一屏
ctrl-b
```
- 行单位移动
G 光标移动到文件末尾
gg 光标移动到文件开头
M 光标移动到文件中间行
H 光标移动到当前页首
L 光标移动到当前页的末尾
nG 跳到第n行
[n]+ 光标向后移动n行，10+
[n]- 光标向前移动n行，10-
- 字单位移动
w 向后移动一个字
b 向前移动一个字
- 行内移动
^ 光标移动到行首
$ 光标移动到行尾

### 插入操作
i：光标前
a：光标后
I：行首
A：行尾
o：向下新开一行并进入插入模式
O：向上新开一行并进入插入模式

### 删除操作
- 行单位删除
dd 删除光标所在行
n dd 删除指定行数
d0 删除光标前本行所有内容，不包括光标所在字符
dw 删除光标开始位置的字，包括光标所在字符
D 删除光标后的所有内容，用于删除注释
- 字符单位删除
x 删除光标后面字符
X 删除光标前面字符

### 复制与粘贴
yy 复制当前行
n yy 复制n行
p 将已复制的内容在光标的下一行粘贴
P 将已复制的内容在光标的上一行粘贴
y$ 复制到行尾

### 查找与替换
- 查找
```sh
# 查找printf
# n找下一个匹配的,N回到上一个匹配字符
/printf
```
- 字符替换
r 替换当前字符
R 替换当前光标后的字符
- 单行替换
```sh
:s/src/dst

# 将一行内的hello替换为world
# i忽略大小写
# g全部匹配
:s/hello/world/ig
```
- 多行替换
```sh
# 3到6行的hello替换为world
:3,6s/hello/world/ig
```
- 全部替换
```sh
# 将文档中所有的hello替换为world
:%s/hello/world/ig
# 将文档每一行的行首空格去掉
:%s/^ //ig
```

### 分屏操作
- vim命令分屏
```sh
# 上下分屏
:sp
# 左右分屏
:vsp
# 分屏，给新的文件
:sp main.c
:vsp main.c
# 创建新文件，横向分屏
:new main.c
# 创建新文件，纵向分屏
:vnew main.c
```
- 打开文件时分屏
```sh
# 打开的时左右分屏
vim -On 1.c 2.c
vim -O3 1.c 2.c 3.c
# 打开时上下分屏
vim -on 1.c 2.c
```
- 关闭分屏
```sh
# 关闭一个分屏
ctrl+w c
# 关闭最后一个
ctrl+w q
# 光标切换
ctrl+w w
```
- 分屏尺寸调整
```sh
ctrl+w +
ctrl+w -
ctrl+w =
ctrl+w >
ctrl+w <
```

### 撤销
u 一步一步撤销
U 一次撤销所有操作
Ctrl-r 反撤销

### 块操作
v 按字符移动，选中文本
V 按行移动
Ctrl+v 块选择

### 其他
```sh
# 保存退出
ZZ
# 代码缩进排版
gg=G
# 查看行号
: set nu
# 调用man page
# 光标放在函数名上 (n)K
# 重复上一次操作命令
.
>> 文本行右移
<< 文本行左移

# vim因ctrl+s会处于僵死状态，ctrl+q可以恢复
```

### viw 配置
编辑~/.vimrc
```sh
# 设置行号
:set nu
# 取消行号
:set nonu

# 设置高亮度查找
:set hlsearch
# 取消高亮度查找
:set nohlsearch

# 自动备份文件
:set backup
# 开启右下角状态栏说明 
:set ruler
# 显示左下角的INSERT之类的状态栏
:set showmode
# 设置退格键功能，为2时可以删任意字符。为0或1时仅可以删除刚才输入的字符
:set backspace={0,1,2} 
# 显示目前所有的环境参数值
:set all
# 显示与系统默认值不同的参数值
:set
# 是否开启依据相关程序语法显示不同的颜色
:syntax on/off 
# 是否显示不同的颜色色调
:set bg=dark/light 
```
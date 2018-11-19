---
title: shell 语法
categories:
  - OS
  - Linux
  - shell
---

### 脚本调试
`sh [-nvx] scripts`
-n ：不执行脚本，查询脚本内的语法，若有错误则列出
-v ：在执行脚本之前，先将脚本的内容显示在屏幕上
-x ：将用到的脚本内容显示在屏幕上，与 -v 稍微不同

### echo
```sh
# 输出前一个指令执行是否出错，有错误返回 1 ，没有错误返回 0
echo $?

echo 'Look the "legs"'

echo "The cost is \$15"

# -n 选项移除掉字符串末尾的换行符
echo -n "The time is : "
date
```

### read
#### 读取输入
read命令用来接收用户输入
```sh
#!/bin/bash
echo "Phone number ?"
read phone
```
#### 提示信息
```sh
#!/bin/bash
now=`date "+%Y.%m.%d %H:%M:%S"`
read -p "Phone number: " phone
read -p "Name: " name
read -p "Issue: " issue
echo "$now/$phone/$name/$issue">>data.txt
```
#### 读取多变量
如果 read 不指定变量，则 read 收到的所有数据都会存储到 REPLY 中
```sh
read -p "Enter you name: " first last
echo "$last : $first..."
```
#### 输入限时
```sh
if read -t 5 -p "Please enter your name: " name
then
  echo "Hello $name. Welcome to my script"
else
  echo "Sorry,too late"
fi
```
#### 输入限长
限制输入单个字符
```
read -n1 -p "Continue [Y/N]?" answer
case $answer in
Y | y) echo "Continue on...";;
N | n) echo "Bye"
       exit;;
easc
```
#### 隐藏方式读取
```sh
read -s -p "Password: " passwd
echo "Password: $passwd"
```
#### 读取文件
```sh
count=1
cat test | while read line
do
  echo "Line $count: $line"
  count=$[ $count + 1 ]
done
echo "Finish!"
```

### 变量
#### 环境变量
`shell `按顺序从启动文件中读取命令，第一个最先读取，后面三个为用户定义专有环境变量
，大多数`Linux`只用3个启动文件中的一个
当新shell是新的登录生成的，bash shell会运行.bash_profile文件
当新shell启动，包括有新的登录情况，bash shell会运行.bashrc文件
/etc/profile
$HOME/.bash_profile
$HOME/.bash_login
$HOME/.profile
```sh
# 设置局部环境变量
msg='hello world'
echo $msg

# 设置全局环境变量
msg='hello world'
export msg

# 删除环境变量
unset msg
```
#### 简单变量
```sh
#!/bin/bash
echo "Phone number ?"
read phone
echo "You have entered $phone as a phone number"

# 双引号使变量会发生变量替换, 单引号不会
str='abc'
echo "1 print $str"
echo '2 print $str'
```
#### 局部变量
```sh
num=111
func()
{
  local num=222
  echo $num
}
 
echo "before---$num"
func
echo "after---$num"
```
#### 数组
```sh
arr = (one two three four)
echo ${arr[2]}
# 显示整个数组
echo ${arr[*]}
```
#### 简单运算
```sh
var1=100
var2=50
var3=45
var4=$[$var1*($var2 - $var3)]
echo Final number is $var4
```
#### 高级表达式
```sh
val1=10
if (( $val**2>90 ))
then
  (( val2 = $val1**2 ))
  echo "The square of $val1 is $val2"
fi
```
#### 字符串模式匹配
```sh
if [[ $USER == r* ]]
then
  echo "Hello $USER"
else
  echo "Sorry. I do not know you"
fi
```
#### 数组
```sh
function func{
  local arr1
  arr1=(`echo "$@"`)
  echo "The new array value is: ${arr1[*]}"
}
arr=(1,2,3,4,5)
echo "The original array is ${arr[*]}"
func ${arr[*]}
```

### 字符串
#### 字符串拼接
```sh
name="shell"
name1="hello, "$name" !"
name2="hello, ${name} !"
echo $name1 $name2
```
#### 字符串长度
```sh
string="abcd"
echo ${#string}
```
#### 截取字符串
```sh
str="hello shell"
echo ${str:2}
echo ${string:1:3}
```

### 判断
`if` 后的命令返回0才会执行 `then` 部分
#### 数字比较
```sh
var1=10
var2=11
if [$var1 -gt 5]
then
  echo "$var1 is greater than 5"
fi
if [$var1 -eq $var2]
then
  echo "The values are equal"
else
  echo "The values are different"
fi
```
#### 字符串比较
```sh
var1=baseball
var2=hockey
if [$var1 \> $var2]
then
  echo "$var1 is greater than $var2"
else
  echo "$var1 is less than $var2"
fi

# 字符串盼空
var1=testing
var2=''
if [ -n "$var1" ]
then
  echo "The string '$var1' is not empty"
else
  echo "The string '$var1' is empty"
fi
if [ -z "$var2" ]
then
  echo "The string '$val2' is empty"
else
  echo "The string '$val2' is not empty"
fi
```
#### case
```sh
input=$1
# ;;相当于其它语言中的break
case $input in
    1 | 0)
    str="1/0";;
    2)
    str="2";;
    3)
    str="3";;
    *)
    str=$input;;
esac
echo "$str"
```

### 循环
#### while 循环
```sh
while true
do
read -p "Phone number: " phone
now=`date "+%Y.%m.%d %H:%M:%S"`
read -p "Name: " name
echo "$now/$phone/$name">>data.txt
done

i=0
while ((i<5));
do
  ((i++))
  echo "i=$i"
done

var=10
while echo $var
  [ $var -ge 0 ]
do
  echo "Inside the loop"
  var=$[ $var - 1 ]
done
```
#### until 循环
```sh
var=100
until [ $var -eq 0 ]
do
  echo $var
  var=$[ $var - 25 ]
done

i=5
until ((i==0))
do
  ((i--))
  echo "i=$i"
done
```
#### for 循环
```sh
for c in China Japan USA
do
  echo The country is $c
done

file="a.txt"
for item in `cat $file`
do
  echo "Item is $item"
done

for file in *.txt
do
  open $file
done
```
```sh
function func{
  if [$1 -eq 1]
  then
    echo 1
  else
    local temp=$[$1 - 1]
    local result=`func1 $temp`
    echo $[$result * $1]
  fi
}
read -p "Enter the value: " value
result=`func $value`
echo "The result is: $result"
```

### 函数
```sh
function func{
  echo "func"
}
count=1
while [ $count -le 5 ]
do
  func1
  count=$[ $count + 1 ]
done
echo "end of loop"
```
#### 函数返回值
```sh
function func{
  read -p "Enter a value:" value
  echo $[ $value * 2 ]
}
res=`func`
echo "The result is $res"
```
#### 函数参数
```sh
function func{
  if [$# -eq 0]||[$# -gt 2]
  then
    echo -1
  elif [$# -eq 1]
  then
    echo $[$1 + $1]
  else
    echo $[$1 + $2]
  fi
}
value=`func 10 20`
echo $value 
```

### 参数
#### 传递参数
```sh
echo "$# parameters"
echo "$@"
echo "$0"
echo "$1"
```
#### 参数
```sh
total=$[ $1 * $2 ]
echo The program name is $0
echo The first param is $1
echo The second param is $2
echo The total value is $total
```
#### 读取参数个数
```sh
if [ $# -ne 2 ]
then
  echo Usage: test a b
else
  total=$[ $1 + $2 ]
  echo The total is $total
fi
```
#### 最后一个参数
```sh
param=$#
echo The last param is $param
echo The last param is ${!#}
```
#### 处理所有参数
```sh
count=1
for param in "$*"
do
  echo "\$* parameter #$count = $param"
  count=$[ $count + 1 ]
done

count=1
for param in "$@"
do
  echo "\$@ parameter #$count = $param"
  count=$[ $count +1 ]
done

# 遍历命令行参数
count=1
while [ -n "$1" ]
do
  echo "parameter #$count = $1"
  count=$[ $count + 1 ]
  shift
done
```

### 选项
```
while [ -n "$1" ]
do
  case "$1" in
  -a) echo "Found the -a option";;
  -b) echo "Found the -b option";;
  -c) echo "Found the -c option";;
  *) echo "$1 is not a option";;
  easc
  shift
done
```
#### getopt
需要参数的选项字母后加一个冒号
```sh
getopt ab:cd -a -b test1 -cd test2 test3
# -q 忽略错误信息
getopt -q ab:cd -a -b test1 -cd test2 test3
# 转换为
-a -b test1 -c -d -- test2 test3
```
#### getopts
选项之前加冒号可去除错误消息
```
while getopts :ab:cd opt
do
  case "$opt" in
  a) echo "Found the -a option";;
  b) echo "Found the -b option. with value $OPTARG";;
  c) echo "Found the -c option";;
  *) echo "Unknow option: $opt";;
  easc
done

shift $[ $OPTIND - 1 ]
count=1
for param in "$@"
do
  echo "Parameter $count: $param"
  count = $[ $count + 1 ]
done
```

### 重定向
#### 流重定向
```sh
#!/bin/bash
now=`date "+%Y.%m.%d %H:%M:%S"`
echo "$now" >> data.txt
```
##### 重定向错误
```sh
ls -al good.txt bad.txt 2>test
```
##### 重定向错误与正常
```sh
ls -al good.txt bad.txt 2>error 1>info
```
##### 错误与正常]输入到同一文件
```sh
ls -al good.txt bad.txt &>info
```
##### 不保存错误信息
```sh
ls -al test 2> /dev/null
```
##### 清除日志文件内容
```sh
cat /dev/null > logfile
```
#### 管道重定向
##### cut
```sh
# cut 用分隔符 / 将每行切开，并打印第二个字段
cat data.txt | cut -d"/" -f2
```
##### tee
```sh
# 输出到 STDOUT 同时保存到 test 文件中
date | tee test
echo "append text" | tee -a $testfile
```
##### sort
```sh
# 结果排序
cat data.txt | cut -d"/" -f2 | sort

# 对文本数据行排序
sort file
# 对文本数据行按照数字排序
sort -n file
# 降序排序
sort -nr file

# -t指定字段分隔符，-k指定排序字段
sort -t ':' -k 3 -n /etc/passwd
```
##### uniq
```sh
# 统计唯一条目
cat data.txt | cut -d"/" -f2 | sort | uniq -c
```
#### 脚本中重定向 STDOUT
```sh
# 使用 fd 2 打开文件 error 用于写入
exec 2>error
echo "This is the start of script"
# 使用 fd 1 打开文件 info 用于写入
exec 1>info
echo "This is a test info"
echo "This is a error into" >&2
```
#### 脚本中重定向 STDIN
```sh
# 使用 fd 0 打开文件 test 用于读取
exec 0<test
count=1
while read line
do
  echo "Line #$count: $line"
  count=$[ $count + 1 ]
done

# 使用 fd 3打开文件 test 用于读写
# exec 3 <> test
exec 3>test
echo "This is a test on the screen"
echo "This is a test on the file" >&3
```
#### 恢复重定向文件描述符
```sh
exec 3>&1
exec 1>test
echo "This should store in the output file"
exec 1>&3
echo "This should on the screen"
```
```sh
exec 3<&0
exec 0<test
count=1
while read line
do
  echo "Line #$count: $line"
  count=$[ $count + 1 ]
done
exec 0<&3
read -p "Enter your name: " name
echo "Your name is $name"

# 关闭文件描述符
exec 3>&-
```
#### 创建临时文件
```sh

tmpFile=`mktemp test`
exec 3>$tmpFile
echo "Write to tmp file $tmpFile"
echo "The first line">&3
echo "The second line">&3
echo "The third line">&3
exec 3>&-
cat $tmpFile
rm -rf $tmpFile 2>/dev/null
```
#### 创建临时目录
```sh
tmpdir=`mktemp -d dir`
cd $tmpdir
tmpfile1=`mktemp test1`
tmpfile2=`mktemp test2`
exec 3>$tmpfile1
exec 4>$tmpfile2
echo "This is test line of $tmpfile1">&3
echo "This is test line of $tmpfile2">&4
```

### 文件操作
#### 目录
```sh
# 检查是否为目录
if [ -d $HOME ]
then
  echo "Your HOME directory exists"
  cd $HOME
  ls -a
else
  echo "There is a problem with your HOME directory"
fi

# 检查文件与目录是否存在
if [ -e $HOME ]
then
  echo "OK on the directory. now to check the file"
  if [ -e $HOME/testing ]
  then
    echo "Appending date to existing file"
    date >> $HOME/testing
  else
    echo "Creating new file"
    date > $HOME/testing
  fi
else
  echo "Sorry. you do not have a HOME directory"
fi
```
##### 目录计数
```sh
path=`echo $PATH | sed 's/:/ /g'`
count=0
for dir in $path
do
  check=`ls $dir`
  for item in $check
  do
    count=$[ $count + 1 ]
  done
  echo "$dir - $count"
  count=0
 done
```
#### 文件
##### 检查是否为文件
```sh
if [ -e $HOME ]
then
  echo "The object exists. is it a file?"
  if [ -f $HOME ]
  then
    echo "Yes. it is a file!"
  else
    echo "No. it is not a file!"
  fi
else
  echo "Sorry, the object dose not exist"
fi
```
##### 检查文件是否可读
```sh
pwfile=/etc/shadow
if [ -f $pwfile ]
then
  if [ -r $pwfile ]
  then
    tail $pwfile
  else
    echo "Unable to read the file"
  fi
else
  echo "Sorry, the file $pwfile is not exists"
fi
```
##### 检查文件是否可写
```sh
if [ -d $HOME ] && [ -w $HOME/testing ]
then
    echo "The file exists and you can write to it"
fi
```
##### 检查是否可执行
```sh
if [ -x test ]
then
  echo "You can run the script:"
  ./test
else
  echo "Sorry. you are unable to execute the script"
fi
```
##### 检查文件是否空
```sh
file=test
touch $file
if [ -s $file ]
then
  echo "The $file file exists and has data in it"
else
  echo "The $file exists and is empty"
fi
```
##### 检查文件所属关系
```sh
if [ -0 /etc/passwd ]
then
  echo "You are owner of the /etc/passwd"
fi
```
##### 检查文件日期
```sh
if [ ./file1 -nt ./file2 ]
then
  echo "The file1 is newer than file2"
fi
```

### 信号
#### 捕捉信号
```sh
trap "echo 'Have trapped Ctrl-C'" SIGINT SIGTERM
echo This is a test program
count=1
while [ $count -le 10 ]
do
  echo "Loop #$count"
  sleep 5
  count=$[ $count +1 ]
done
echo "This is the end of the test program"
```
#### 捕捉脚本退出信号
```sh
trap "echo bye" EXIT
echo This is a test program
count=1
while [ $count -le 5 ]
do
  echo "Loop #$count"
  sleep 1
  count=$[ $count +1 ]
done
```
#### 移除捕捉
```sh
trap - EXIT
```

### 创建库
```sh
. ./optlib.sh
val1=10
val2=20
res=`add $val1 $val2`
```

### 作业
##### 定时执行脚本
at -f test 14:20
##### 列出等待队列的作业
atq
##### 删除作业队列中的作业
atrm 59

### mysql
#### 授权
```sh
# test.* 定义了授权的数据库和表
# TO 指定授权用户
# IDENTIFIED by 设定默认密码
GRANT SELECT,INSERT,DELETE,UPDATE ON test.* TO test IDENTIFIED by 'test';
```
#### 使用数据库
```sh
mysql=`which mysql`
$mysql test -u test -e 'select * from employees'
```
```sh
$mysql test -u test << EOF
show tables;
select * from employees where salary>40000;
EOF
```
```sh
if [$# -ne 4]
then
  echo "Usage: ..."
else
  stmt="INSERT INTO empl VALUES ($1,$2,$3,$4)"
  $mysql test -u test << EOF
  $stmt
EOF
  if [$? -eq 0]
  then
    echo "successful";
  else
    echo "problem";
  fi
fi
```

### cron
#### cron目录
```sh
ls /etc/cron.*ly
```
#### 列出已有时间表
```sh
crontab -l
```
#### 定期执行脚本
```sh
min hour dayofmonth month dayofweek command
```
#### 每天 10:15 运行命令
```sh
15 10 * * * command
```
#### 每周一 4:15 PM 运行命令
```sh
15 16 * * 1 command
```
#### 每个月第一天中午12点执行
```sh
00 12 1 * * command
```
#### 每个月最后一天中午12点执行
```sh
00 12 * * * if [`date +%d -d tomorrow` = 01 ]:then command
```
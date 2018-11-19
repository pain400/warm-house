---
title: awk 使用
categories:
  - OS
  - Linux
  - awk
---

# gwak
```
# 默认从STDIN中接受数据
gwak '{print "Hello World"}'
```

# gwak中的变量
```
$0 表示整个文本行
$1 表示文本行中的第一个数据字段
$2 表示文本行中的第二个数据字段
```

# gwak字段分隔符
```
# 指定分隔符为冒号
gawk -F: '{print $1}' /etc/passwd
# 默认分隔符为任意空白字符
gawk '{print $1}' tmpfile
```

# 
```
# FS 设置输入字段分隔符
gawk 'BEGIN{FS=","} {print $1,$2,$3}' tmpfile
# OFS 设置输出字段分隔符
gawk 'BEGIN{FS=",";OFS="-"} {print $1,$2,$3}' tmpfile
# 用字段宽度分割
gawk 'BEGIN{FIELDWIDTHS="3 5 2 5"} {print $1,$2,$3,$4}' tmpfile
```

# gwak执行多个命令
```
echo "My name is Rich" | gawk '{$4="Jack";print $0}'
```

# gwak 从文件中读取命令
```
# scriptfile
{
test = "'s home dir is "
print $1 text $6
}
```
```
gawk -F: -f scriptfile /etc/passwd
```

# 处理数据前运行脚本
```
gawk 'BEGIN {print "Hello World!"}'
gawk 'BEGIN {print "The file content:"} {print $0}' tmpfile
```

# 处理数据后运行脚本
```
gawk 'BEGIN {print "Hello World!:"} {print $0}
END {print "End of File"}' tmpfile
```

# 运行脚本文件
```
# scriptfile
BEGIN{
print "The latest list of users and shells"
print " Userid    Shell"
print "-----------------"
FS=":"
}
{
print $1 "           " $7
}
END{
print "This concludes the listing"
}
```
```
gawk -f scriptfile /etc/passwd
```

# 正则
```
echo "bet" | gawk '/be?t/{print $0}'
echo "beet" | gawk '/be+t/{print $0}'
echo "bet" | gawk --re-interval '/be{1}t/{print $0}'
echo "beet" | gawk --re-interval '/be{1,2}t/{print $0}'
echo "beat" | gawk --re-interval '/b[ea]{1,2}t/{print $0}'
echo "The cat is a hat" | gawk '/[ch]at/dog/{print $0}'
echo "cat" | gawk '/(c|b)a(b|t)/{print $0}'
```

### awk
```
awk '{pattern + action}' {filenames}
```
    简单来说awk就是把文件逐行的读入，以空格为默认分隔符将每行切片，切开的部分再进行各种分析处理
    其中 pattern 表示 awk 在数据中查找的内容，而 action 是在找到匹配内容时所执行的一系列命令，花括号（{}）不需要在程序中始终出现，但它们用于根据特定的模式对一系列指令进行分组

####awk内置变量
```
ARGC               命令行参数个数
ARGV               命令行参数排列
ENVIRON            支持队列中系统环境变量的使用
FILENAME           awk浏览的文件名
FNR                浏览文件的记录数
FS                 设置输入域分隔符，等价于命令行 -F 选项
NF                 浏览记录的域的个数(col)
NR                 已读的记录数(row)
OFS                输出域分隔符
ORS                输出记录分隔符
RS                 控制记录分隔符
```

####示例0
```
// print, printf用法
#awk 'BEGIN{a=1;b="213";print "output " a "," b;}'
output 1,213

#awk 'BEGIN{a=1;b="213";print "output" , a , "," b;}'
output 1 ,213

#awk 'BEGIN{a=1;b="213";printf("output %d,%s\n",a,b)}'
output 1,213
```
####示例1
    awk工作流程是这样的：读入有'\n'换行符分割的一条记录，然后将记录按指定的域分隔符划分域，填充域，$0则表示所有域，$1表示第一个域，$n表示第n个域。默认域分隔符是"空白键" 或 "[tab]键"，所以$1表示登录用户，$3表示登录用户ip
```
// 显示最近登录的5个帐号
last -n 5
// 只是显示最近登录的5个帐号
last -n 5 | awk  '{print $1}'
```
####示例2
```
// 选择分隔符，根据":",空格，","来进行分割
#echo "a:b c,d" |awk -F " |,|:" '{print $1; print $2; print NF}'
a
b
4
```
####示例2
```
// 显示/etc/passwd的账户
// -F指定域分隔符为':'
cat /etc/passwd |awk  -F ':'  '{print $1}'

// 显示/etc/passwd的账户和账户对应的shell
cat /etc/passwd |awk  -F ':'  '{print $1"\t"$7}'

// 只是显示/etc/passwd的账户和账户对应的shell，在所有行添加列名name，shell，在最后一行添加"blue,/bin/nosh"
cat /etc/passwd |awk  -F ':'  'BEGIN {print "name,shell"}  {print $1","$7} END {print "blue,/bin/nosh"}'
```
####示例3
```
// 匹配了pattern(这里是root)的行才会执行action(没有指定action，默认输出每行的内容)
awk -F ':' '/root/' /etc/passwd

// 搜索/etc/passwd有root关键字的所有行，并显示对应的shell
awk -F ':' '/root/{print $7}' /etc/passwd
```
####示例4
```
#echo "a b c d e" |awk '{print $1; print $2; print $(NF-1);print $NF;print $0}'

a                 //对应第1个域
b                 //对应第2个域
d                 //对应$(NF-1),对应倒数第二个域
e                 //对应$NF,最后一个域
a b c d e         //对应$0
```
####示例5
```
// 统计/etc/passwd:文件名，每行的行号，每行的列数，对应的完整行内容
awk  -F ':'  '{print "filename:" FILENAME ",linenumber:" NR ",columns:" NF ",linecontent:"$0}' /etc/passwd

// 使用printf替代print,可以让代码更加简洁，易读
awk  -F ':'  '{printf("filename:%10s,linenumber:%s,columns:%s,linecontent:%s\n",FILENAME,NR,NF,$0)}' /etc/passwd
```
####示例6
```
// 统计tomcat每秒的带宽(字节)，最大的排在最后面
cat localhost_access_log.txt | awk '{ bytes[$5] += $NF; }; END{for(time in bytes) print   bytes[time] " " time}'  | sort -n
// 统计某一秒的带宽
grep "18:07:34" localhost_access_log.txt |awk '{ bytes += $NF; } END{ print  bytes }'
```
####示例7
```
// 统计指定ip.txt中ip在local_access.txt中出现的次数

#cat ip.txt //内容如下 
12.3.4.5
12.3.4.6
12.3.4.7
12.3.4.8

#cat local_access.txt
19:23:35  /a.html   12.3.4.5
19:23:35  /b.html   12.3.4.5
19:23:35  /c.html   12.3.4.6
19:23:35  /d.html   12.3.4.7
19:23:35  /a.html   12.3.4.9
19:23:35  /b.html   12.3.4.9
19:23:35  /c.html   12.3.4.9

awk -F " " '{if (NR==FNR) {arr1[$1]=1} else{arr2[$3]++;}} END{for(ip in arr1){print ip,arr2[ip]}}' ip.txt local_access.txt

12.3.4.5 2
12.3.4.6 1
12.3.4.7 1
12.3.4.8
```
########示例8
```
# 打印八月份上涨的记录
grep "2014-08-*" gpData | awk '{if ($7>0) print $0}'
```


####awk编程
    变量和赋值
```
// 统计/etc/passwd的账户人数
awk '{count++;print $0;} END{print "user count is ", count}' /etc/passwd

// 初始化count
awk 'BEGIN {count=0;print "[start]user count is ", count} {count=count+1;print $0;} END{print "[end]user count is ", count}' /etc/passwd
```
```
// 统计某个文件夹下的文件占用的字节数
ls -l |awk 'BEGIN {size=0;} {size=size+$5;} END{print "[end]size is ", size}'
// 以M为单位显示
ls -l |awk 'BEGIN {size=0;} {size=size+$5;} END{print "[end]size is ", size/1024/1024,"M"}'
```
    条件语句
```
// 统计某个文件夹下的文件占用的字节数,过滤4096大小的文件(一般都是文件夹)
ls -l |awk 'BEGIN {size=0;print "[start]size is ", size} {if($5!=4096){size=size+$5;}} END{print "[end]size is ", size/1024/1024,"M"}'
```
    数组
```
// 显示/etc/passwd的账户
awk -F ':' 'BEGIN {count=0;} {name[count] = $1;count++;}; END{for (i = 0; i < NR; i++) print i, name[i]}' /etc/passwd
```

####awk内置函数
```
// int函数，把字符串转为整数
awk 'BEGIN {print int("12.9")}'

// index函数
awk 'BEGIN {print index("12.9343",".")}'

// length函数，得到数组的长度,字符串长度
awk 'BEGIN {array1["a"]=1;array1["b"]=2;print length(array1)}'
awk 'BEGIN{a="123";print length(a)}'

// match函数
awk 'BEGIN {info="is is test"; print match(info,"te");}'

// rand函数
awk 'BEGIN {print rand " " rand}'

// split函数，按照某个分隔符，对字符串进行分割
awk 'BEGIN {print split("it is a test",thearray," "); print thearray[1]}'

// sub函数，替换
awk 'BEGIN {info="this a test"; sub("a","b",info); print info }'

// substr函数，得到子字符串
awk 'BEGIN {print substr("12.9343",2,4)}'

// toupper函数，字符串转为大写
awk 'BEGIN {info="this a test"; print toupper(info);}'

// tolower函数，字符串转为小写
awk 'BEGIN {info="thIS A TEST"; print tolower(info);}'
```
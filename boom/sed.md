---
title: sed 使用
categories:
  - OS
  - Linux
  - sed
---

### sed 替换
```sh
echo "This is a test" | sed 's/test/big test/'
# 将tmpfile中的dog替换为cat(不会修改原文件)
# 默认只会替换每行中出现的第一处
sed 's/dog/cat/' tmpfile
# 替换第二次出现的匹配模式
sed 's/dog/cat/2' tmpfile
# 替换每次出现的匹配模式
sed 's/dog/cat/g' tmpfile
# -n禁止sed输出，p替换标记会输出修改过的行
sed -n 's/dog/cat/p' tmpfile
# w替换标记将修改的过行保存到test文件中
sed 's/dog/cat/w test' tmpfile
```

### sed 定位
```sh
# sed作用到第二行
sed '2s/dog/cat/' tmpfile
# sed作用到第二到第三行
sed '2.3s/dog/cat/' tmpfile
# sed作用到第二到最后一行
sed '2.$s/dog/cat/' tmpfile
# 定位执行多个命令
sed '3.${s/brown/green/;s/cat/dog/}' tmpfile
# 多个编辑命令
sed -e 's/brown/green;s/dog/cat/' tmpfile

# 修改用户Jack的bash为csh
sed '/Jack/s/bash/csh' /etc/passwd
```

### sed 删除
```sh
# 删除第三行
sed '3d' tmpfile
# 删除第三行到文件尾部
sed '3.$d' tmpfile
# 删除匹配到number 1的行
sed '/number 1/d' tmpfile
# 删除空格
sed '/^$/d' tmpfile
# 删除查到找header的下一行
sed '/header/{n;d}' tmpfile
# 找到first所在行，并将下一行与改行合并，然后换行符替换为空格
sed '/first/{N;s/\n/ /}' tmpfile
```

### sed 插入
```sh
# 将Test Line1插入到第一行
echo "Test Line 2" | sed 'i\Test Line1'
# 将Test Line1添加到第二行
echo "Test Line 2" | sed 'a\Test Line1'

# 将This is a insert line 插入到第三行前
sed '3i\This is a insert line' tmpfile
sed '3a\This is a insert line' tmpfile

# 添加到文件末尾
sed '$a\This is a insert line' tmpfile
```

### sed 修改
```sh
# 修改第三行
sed '3c\
This is a changed line' tmpfile
# 修改第三行
sed '/number 3/c\
This is a changed line' tmpfile
```

### sed 转换
```sh
# 1替换为7,2替换为8,,3替换为9
sed 'y/123/789' tmpfile
```

### sed 打印
```sh
sed -n '/number 3/p' tmpfile
sed -n '2.3p' tmpfile
# 除开header行外都打印
sed -n '/header/!p' tmpfile
# 翻转文件
sed -n '{1!G;h;$p}' tmpfile
# 脚本文件中
#!/bin/bash
# 翻转文件
sed -n '{
1!G
h
$p
}' $1
```

### sed 实用工具
```sh
# 向文本中添加空行
sed 'G' tmpfile
# 不添加最后的空行
sed '$!G' tmpfile
# 先删除所有空行，再添加空行
sed '/^$/d;$!G' tmpfile
# 添加行号:先添加行号，再合并
sed '=' tmpfile | sed 'N;s/\n/ /'
# 打印末尾一行
sed -n '$p' tmpfile
# 删除多余的空行
sed '/././^$/!d' tmpfile
# 删除开头空行
sed '/./.$!d' tmpfile
# 清除html标签
sed 's/<[^>]*>//g;/^$/d' tmpfile
```

### sed 写操作
```sh
sed -n '1.2w test' tmpfile
# 读取含pain的行写入pain_line中
sed -n '/pain/w pain_line' tmpfile
```

### sed 读取文件
```sh
# 读取file1的内容添加到file2的第三行之后
sed '3r file1' file2
sed '/number 2/r file1' file2
# 在file2末尾添加file1
sed '$r file1' file2
```

### !做分隔符
```sh
# 将文件中的/bin/bash替换为/bin/csh
sed 's!/bin/bash!/bin/csh!' /etc/passwd
```

### 从文件中读取编辑器命令
```sh
# scriptfile
s/brown/green/
s/fox/elephant/
s/dog/cat/

sed -f scriptfile tmpfile
```

####替换
```
// 把"I am"替换成"Liu YiLing is" 并输出到控制台
sed -e 's/I am/Liu YiLing is/g' test.txt

// 把"I am"替换成"Liu YiLing is" 并通过重定向输出到newfile.txt中
sed -e 's/I am/Liu YiLing is/g' test.txt>newfile.txt

// -i：指定备份文件的名称
// 替换test.txt的内容，并把替换后的内容，直接写入test.txt中，同时把修改前的test.txt备份到test.txt.bak中
sed -i.bak 's/I am/Liu YiLing is/g' test.txt
```
####在行头添加内容
```
// 在每一行的前面添加"%",  ^表示行首
sed 's/^/%/g' test.txt
```
####在行尾添加内容
```
// 在每一行的行尾添加"---",  $表示行尾
sed 's/$/---/g' test.txt
```
####指定行数替换
```
// 把第2，3行的"I"被替换成"Liu YiLing"
sed '2,3s/I/Liu YiLing/g' test.txt
```
####指定匹配替换的索引
```
#cat test2.txt
a a a a
a a a a
a a a a

// 只替换每一行的第1个a
sed 's/a/A/1' test2.txt

// 只替换每一行的第2个a
sed 's/a/A/2' test2.txt

// 只替换每一行的第三个以后的a替换成A
sed 's/a/A/3g' test2.txt

// 执行奇数行
sed '1~2s/a/A/g' test2.txt

// 多行匹配
// 1-2行中的第三个及其以后的a替换为A，3到最后一行的每个a都替换为AA
sed '1,2s/a/A/3g; 3,$s/a/AA/g' test2.txt
a a A A
a a A A
AA AA AA AA
AA AA AA AA
AA AA AA AA
```
####使用匹配字符&代表匹配的字符串
```
// 对1-2行的所有a替换成["a"]这样的形式
sed '1,2s/a/["&"]/g' test2.txt
["a"] ["a"] ["a"] ["a"]
["a"] ["a"] ["a"] ["a"]
a a a a
a a a a
a a a a
```
####行操作
```
// 在第一行之前追加一行"the new added line"
sed "1 i the new added line" test3.txt
// 在第二行前插入'i drink tea'
nl /etc/passwd | sed '2i drink tea'

// 在第一行之后追加一行
sed "1 a the new added line" test3.txt
// 在最后一行之后追加一行追加行，$表示最后一行
sed "$ a the new added line" test3.txt
// 在第二行后加上drink tea
nl /etc/passwd | sed '2a drink tea'

// 找到所有含有"line"的行，并在其后追加行"the new added line"
sed "/line/a the new added line" test3.txt

// 删除第2行
nl /etc/passwd | sed '2d'
// 删除第 3 到最后一行
nl /etc/passwd | sed '3,$d' 
// 从第二行开始全部删除，2,$表示从2开始到最后一行
sed '2,$d' test3.txt

// 匹配删除行--删除匹配fish的那一行
sed '/second/d' test3.txt

// 匹配替换行
// 把含有third的行，全部替换成"replaced line" 
sed "/third/c replaced line" test3.txt

// 将第2-5行的内容取代成为'No 2-5 number'
nl /etc/passwd | sed '2,5c No 2-5 number'

// 仅列出 /etc/passwd 文件内的第 5-7 行
nl /etc/passwd | sed -n '5,7p'
```
####数据的搜寻并显示
```
// 搜索 /etc/passwd有root关键字的行
nl /etc/passwd | sed '/root/p'
// 使用-n的时候将只打印包含模板的行
nl /etc/passwd | sed -n '/root/p'
```
####数据的搜寻并删除
```
// 删除/etc/passwd所有包含root的行
nl /etc/passwd | sed '/root/d'
```
####数据的搜寻并执行命令
```
// 搜索/etc/passwd,找到root对应的行，执行后面花括号中的一组命令，每个命令之间用分号分隔，这里把bash替换为blueshell，再输出这行
nl /etc/passwd | sed -n '/root/{s/bash/blueshell/;p}'

// 如果只替换/etc/passwd的第一个bash关键字为blueshell，就退出
nl /etc/passwd | sed -n '/bash/{s/bash/blueshell/;p;q}'
```
####数据的搜寻并替换
```
// 将 IP 前面的部分予以删除
/sbin/ifconfig eth0 | grep 'inet addr' | sed 's/^.*addr://g'
// 将 IP 后面的部分予以删除
/sbin/ifconfig eth0 | grep 'inet addr' | sed 's/^.*addr://g' | sed 's/Bcast.*$//g'
```
####多点编辑
```
// 一条sed命令，删除/etc/passwd第三行到末尾的数据，并把bash替换为blueshell
nl /etc/passwd | sed -e '3,$d' -e 's/bash/blueshell/'
```
####直接修改文件内容
```
// 利用 sed 将 regular_express.txt 内每一行结尾若为 . 则换成 !
sed -i 's/\.$/\!/g' regular_express.txt
```

```
echo "Books are ..." | sed -n '/^Books/p'
echo "This is a book" | sed -n '/book$/p'
sed -n '/^This is a test$/p' tmpfile
# 过滤空行
sed -n '/^$/d' tmpfile
sed -n '/.at/p' tmpfile
# 字符组
sed -n '/[ch]at/p' tmpfile
echo "Yes" | sed -n '/[Yy][Ee][Ss]/p'
# 过滤
sed -n '/[^ch]at/p' tmpfile
# 区间
sed -n '/^[0-9][0-9][0-9][0-9][0-9]$/p' tmpfile
sed -n '/[a-ch-m]at/p' tmpfile
echo "abc123" | sed -n '/[[:digit:]]/p'
echo "abc123" | sed -n '/[[:digit:]]/p'
echo "abc.123" | sed -n '/[[:punct:]]/p'
echo "ieek" | sed -n '/ie*k/p'
echo "baeeaeet" | sed -n '/b[ae]*t/p'
```
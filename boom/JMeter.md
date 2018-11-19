### 线程组
线程数
线程启动时间
循环次数
#### HTTP 请求默认值
#### HTTP 请求
##### Parameters
添加参数
token: ${token}
#### CSV Data Set Config
uid,token
uid1,token1
uid2,token2
#### HTTP Cookie 管理器

#### 聚合报告
#### 用表格查看结果
#### 图形结果

### 命令行
```sh
# 生成 jmx 文件
jmeter.sh -n -t test.jmx -l result.jtl
# 从聚合报告中导入 result.jtl
```

### redis
```sh
# 100 并发，10000 请求
redis-benchmark -h 127.0.0.1 -p 6379 -c 100 -n 10000

# 存取大小以 100 字节为单位
# -q 输入简洁
redis-benchmark -h 127.0.0.1 -p 6379 -q -d 100

# 只测试部分命令
redis-benchmark -h 127.0.0.1 -p 6379 -t set,lpush -q -d 100 -n 10000

# 只测试其中一条命令
redis-benchmark -h 127.0.0.1 -p 6379 -n 10000 -q script load "redis.call('set', 'key', 'value')"
```

```sh
# 输出到 nohup.out
nohup java -jar skill.jar &
```
















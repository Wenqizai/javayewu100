> Ribbon的重试机制
1. 客户端发送GET请求时，服务端出现问题时，Ribbon会自动重试一次。
> 解决Ribbon的重试机制带来幂等问题时
1. 更改Feign发送POST请求
```java
    @GetMapping("/ribbonretryissueserver/sms")
    public void sendSmsWrong();

    @PostMapping("/ribbonretryissueserver/sms")
    public void sendSmsRight();
```
2. 更改配置文件禁用Ribbon重试
```properties
    ribbon.MaxAutoRetriesNextServer=0
    ribbon.MaxAutoRetries=0
```
-  执行单元测试代码

```$xslt
./gradlew test --debug
./gradlew test --tests kafka.FileProcessorTest --debug
```

- 编译源代码并生成`jar`包
```$xslt
./gradlew jar
```

-  部署`jar`包
```$xslt
./gradlew uploadArchives
```

- 查看`gradle`任务的帮助信息
```$xslt
./gradlew help --task uploadArchives
```


# 打包lib与项目分离
将第三方依赖复制到外部LIB目录，将当前项目的模块打包到jar的lib文件夹

## 打包插件
```
spring-boot-maven-plugin
```

## 复制依赖到LIB目录
```
maven-dependency-plugin
```

## 启动
```
java -jar -Dloader.path=LIB目录 jar文件
```

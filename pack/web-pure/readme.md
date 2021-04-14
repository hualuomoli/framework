# 打包lib与项目分离

## 打包插件
```
spring-boot-maven-plugin
```

## 复制依赖到LIB目录
```
maven-dependency-plugin
```

## 复制其它模块
```
maven-antrun-plugin
```

## 合并spring.factories
```
exec-maven-plugin
```

## 启动
```
java -jar -Dloader.lib=LIB目录 jar文件
```

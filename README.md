基于React + Spring Boot + MQ+ AIGC的智能数据分析平台。
区别于传统Bl，用户只需要导入原始数据集、并输入分析诉求，就能自动生成可视化图表及分析结论，实现数据分析的降本增效。


## 前言

该项目选题非常新颖，不同于泛滥的管理系统、博客、商城，本项目是结合当下最火的AIGC技术＋企业级B业务场景的综合实战，紧跟时代潮流!AIGC应用开发、Al提问技巧、系统优化、分布式限流、线程池、异步化、消息队列

## 文档

这代码有没有文档呀？ 当然有啦，你已经下载了，在doc这个文件夹上，实在不知道，我就给链接出来咯：

gitee：https://github.com/lufuping/AI-BI-Server/tree/master/doc

## 授权

官网：http://f_bi.nuyfc.website/

## 项目链接

JAVA后台：https://github.com/lufuping/AI-BI-Server

平台端：https://github.com/lufuping/AI-BI-Front

## 演示地址

演示地址：

pc端：http://f_bi.nuyfc.website/

## 目录结构规范

我们也有自己的目录结构

![应用分层](E:\知识星球\编程导航\智能BI项目\myBI_backend\doc\img\应用分层.png)

- VO（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象。
- DTO（Data Transfer Object）：数据传输对象，前端像后台进行传输的对象，类似于param。
- BO（Business Object）：业务对象，内部业务对象，只在内部传递，不对外进行传递。
- Model：模型层，此对象与数据库表结构一一对应，通过 Mapper 层向上传输数据源对象。
- Controller：主要是对外部访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等。为了简单起见，一些与事务无关的代码也在这里编写。
- FeignClient：由于微服务之间存在互相调用，这里是内部请求的接口。
- Controller：主要是对内部访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等。为了简单起见，一些与事务无关的代码也在这里编写。
- Service 层：相对具体的业务逻辑服务层。
- Manager 层：通用业务处理层，它有如下特征：
  - 1） 对第三方平台封装的层，预处理返回结果及转化异常信息，适配上层接口。
  - 2） 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理。
  - 3） 与 DAO 层交互，对多个 DAO 的组合复用。
- Mapper持久层：数据访问层，与底层 MySQL进行数据交互。
- Listener：监听 `RocketMQ` 进行处理，有时候会监听`easyexcel`相关数据。

关于`FeignClient`，由于微服务之间存在互相调用，`Feign` 是http协议，理论上是为了解耦，而实际上提供方接口进行修改，调用方却没有进行修改的时候，会造成异常，所以我们抽取出来。还有就是对内暴露的接口，是很多地方都公用的，所以我们还将接口抽取了出了一个模块，方便引用。可以看到`mall4cloud-api`这个模块下是所有对内`feign`接口的信息。

## 目录结构

```
├─.idea
│  ├─dataSources
│  │  ├─33695515-319d-4cd3-97c4-6e54c770052b
│  │  │  └─storage_v2
│  │  │      └─_src_
│  │  │          └─schema
│  │  └─b196c7a9-4cea-4be7-ba1b-58575d1aec8f
│  │      └─storage_v2
│  │          └─_src_
│  │              └─schema
│  ├─inspectionProfiles
│  └─libraries
├─.mvn
│  └─wrapper
├─doc
│  ├─img
│  │  └─开发环境搭建
│  ├─中间件
│  ├─基本开发文档
│  ├─常见问题解决
│  ├─表结构设计
│  └─项目结构
├─sql
├─src
│  ├─main
│  │  ├─java
│  │  │  ├─com
│  │  │  │  └─yupi
│  │  │  │      └─springbootinit
│  │  │  │          ├─annotation
│  │  │  │          ├─aop
│  │  │  │          ├─bizmq
│  │  │  │          ├─common
│  │  │  │          ├─config
│  │  │  │          ├─constant
│  │  │  │          ├─controller
│  │  │  │          ├─esdao
│  │  │  │          ├─exception
│  │  │  │          ├─job
│  │  │  │          │  ├─cycle
│  │  │  │          │  └─once
│  │  │  │          ├─manager
│  │  │  │          ├─mapper
│  │  │  │          ├─model
│  │  │  │          │  ├─dto
│  │  │  │          │  │  ├─chart
│  │  │  │          │  │  ├─file
│  │  │  │          │  │  ├─post
│  │  │  │          │  │  ├─postfavour
│  │  │  │          │  │  ├─postthumb
│  │  │  │          │  │  └─user
│  │  │  │          │  ├─entity
│  │  │  │          │  ├─enums
│  │  │  │          │  └─vo
│  │  │  │          ├─pre
│  │  │  │          ├─service
│  │  │  │          │  └─impl
│  │  │  │          ├─utils
│  │  │  │          └─wxmp
│  │  │  │              └─handler
│  │  │  └─test
│  │  │      └─com
│  │  │          └─yupi
│  │  │              └─springbootinit
│  │  └─resources
│  │      ├─mapper
│  │      └─META-INF
│  └─test
│      └─java
│          └─com
│              └─yupi
│                  └─springbootinit
│                      ├─esdao
│                      ├─manager
│                      ├─mapper
│                      ├─service
│                      └─utils
└─target
    ├─classes
    │  ├─com
    │  │  └─yupi
    │  │      └─springbootinit
    │  │          ├─annotation
    │  │          ├─aop
    │  │          ├─bizmq
    │  │          ├─common
    │  │          ├─config
    │  │          ├─constant
    │  │          ├─controller
    │  │          ├─esdao
    │  │          ├─exception
    │  │          ├─job
    │  │          │  ├─cycle
    │  │          │  └─once
    │  │          ├─manager
    │  │          ├─mapper
    │  │          ├─model
    │  │          │  ├─dto
    │  │          │  │  ├─chart
    │  │          │  │  ├─file
    │  │          │  │  ├─post
    │  │          │  │  ├─postfavour
    │  │          │  │  ├─postthumb
    │  │          │  │  └─user
    │  │          │  ├─entity
    │  │          │  ├─enums
    │  │          │  └─vo
    │  │          ├─service
    │  │          │  └─impl
    │  │          ├─utils
    │  │          └─wxmp
    │  │              └─handler
    │  ├─mapper
    │  └─META-INF
    ├─generated-sources
    │  └─annotations
    ├─generated-test-sources
    │  └─test-annotations
    ├─maven-archiver
    ├─maven-status
    │  └─maven-compiler-plugin
    │      ├─compile
    │      │  └─default-compile
    │      └─testCompile
    │          └─default-testCompile
    └─test-classes
        └─com
            └─yupi
                └─springbootinit
                    ├─esdao
                    ├─manager
                    ├─mapper
                    ├─service
                    └─utils

```

## 技术选型

![技术选型](E:\知识星球\编程导航\智能BI项目\myBI_backend\doc\img\技术选型.png)

## 系统架构图

![架构图](E:\知识星球\编程导航\智能BI项目\myBI_backend\doc\img\架构图.png)

## 部署教程

部署教程请参考该文件夹下的`/基本开发文档/mall4cloud开发环境搭建.md`以及`/开发环境搭建`目录下的中间件安装。

## 代码运行相关截图

### <img src="E:\知识星球\编程导航\智能BI项目\myBI_backend\doc\img\图表.png" alt="图表" style="zoom:50%;" />




## 提交反馈
- 请联系小鹿：1362388804@qq.com
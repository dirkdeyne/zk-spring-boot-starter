# zkboot-secure
can we integrate Spring Security? Yes, we can :)

> Work in progress! 

_in order to run the demo's you should have [zk-spring-boot-starter](https://github.com/dirkdeyne/zk-spring-boot-starter/tree/master/zk-spring-boot-starter)_

## Run this demo
* start [ZkBootDemo](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-secure/src/main/java/be/enyed/zkboot/ZkBootDemo.java) as a spring boot application.
* browse to [http://localhost:8080](http://localhost:8080)
* use ``username = user`` and ``password = password`` for user-access
* use ``username = your System.getProperty("user.name") name`` and ``password = password`` for admin-access

## What do we learn?
* Setup security 
* Setup tablib for interaction view & security

## Where to look @
* setup security-configuration [SecurityConfig.java](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-secure/src/main/java/be/enyed/zkboot/security/SecurityConfig.java)
* setup taglib: [src/main/resources/metainfo/tld - folder](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-secure/src/main/resources/metainfo/tld)
* interaction security and pages: [menu.zul](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-secure/src/main/resources/web/common/menu.zul) and  [secured.zul](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-secure/src/main/resources/web/secured.zul)

## TODO
* move configuration/setup taglib to zk-spring-boot-starter.
* move and configure common-security-setup-stuff to zk-spring-boot-starter and auto-configure when spring-security is on the classpath.
* need to find out _Why is this so slow? :(_ 
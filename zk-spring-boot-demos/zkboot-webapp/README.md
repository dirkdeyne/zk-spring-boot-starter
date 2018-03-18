# zkboot-webapp
let us create a war-file so we can deploy it on our favorite web-server.

_in order to run the demo's you should have [zk-spring-boot-starter](https://github.com/dirkdeyne/zk-spring-boot-starter/tree/master/zk-spring-boot-starter)_

## Run this demo
* start [ZkBootDemo](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-webapp/src/main/java/be/enyed/zkboot/ZkBootDemo.java) as a spring boot application.
* or mvn install and deploy /target/zkboot-webapp.war  on you favorite web-server _(note: you may need to provide some additional configuration-files depending on the target server)_

## What do we learn?
* ``zk-spring-boot-starter`` does not intervene when we want to make a war.

## Where to look @
* pom-file where ``packaging`` is **war**
* application.properties where we set ``zk.config`` to **war**
* ZkBootDemo extends **SpringBootServletInitializer** and overrides **configure(SpringApplicationBuilder builder)**
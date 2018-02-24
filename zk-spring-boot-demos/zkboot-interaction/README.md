# zkboot-interaction
a closer look at the interaction between spring and ZK

_in order to run the demo's you should have [zk-spring-boot-starter](https://github.com/dirkdeyne/zk-spring-boot-starter/tree/master/zk-spring-boot-starter)_

## Run this demos
* start [ZkBootDemo](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-hello/src/main/java/be/enyed/zkboot/ZkBootDemo.java) as a spring boot application.
* browse to [http://localhost:8080](http://localhost:8080)

## What do we learn?
Look at the interaction between Spring MVC and ZK MVVM

* linking a menuitem to a view

```xml
	<menubar viewModel="@id('vm-common')  @init('be.enyed.viewmodel.CommonViewmodel')">
		<menuitem label="home" href="/" />
		<menuitem label="people" href="/people" />
		<menuitem label="BE people" href="/people/BELGIUM" />
		<menuitem label="about" onClick="@command('showAbout')" />
	</menubar>
```
 
:one: `<menuitem label="home" href="/" />` home points the root and because we have set our [home-page]() it will show [welcome](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/resources/web/welcome.zul)

:two: `<menuitem label="people" href="/people" />` people points to [@GetMapping("/people")](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/java/be/enyed/zkboot/controller/DemoController.java) and wil open [interaction/people](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/resources/web/interaction/people.zul)

:three: `<menuitem label="BE people" href="/people/BELGIUM" />` people points to [@GetMapping("/people/{country}")](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/java/be/enyed/zkboot/controller/DemoController.java) and wil open [interaction/people](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/resources/web/interaction/people.zul)

:four: `<menuitem label="about" onClick="@command('showAbout')" />` triggers a command in our viewmodel [CommonViewmodel](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/java/be/enyed/zkboot/viewmodel/CommonViewmodel.java) 
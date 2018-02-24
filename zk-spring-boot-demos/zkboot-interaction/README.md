# zkboot-interaction
a closer look at the interaction between spring and ZK

_in order to run the demo's you should have [zk-spring-boot-starter](https://github.com/dirkdeyne/zk-spring-boot-starter/tree/master/zk-spring-boot-starter)_

## Run this demos
* start [ZkBootDemo](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-hello/src/main/java/be/enyed/zkboot/ZkBootDemo.java) as a spring boot application.
* browse to [http://localhost:8080](http://localhost:8080)

## What do we learn?
Look at the interaction between Spring MVC and ZK MVVM

##### linking a menuitem to a view or controller

```xml
	<menubar viewModel="@id('vm-common')  @init('be.enyed.viewmodel.CommonViewmodel')">
		<menuitem label="home" href="/" />
		<menuitem label="hello" href="view/hello" />
		<menuitem label="people" href="/people" />
		<menuitem label="BE people" href="/people/BELGIUM" />
		<menuitem label="about" onClick="@command('showAbout')" />
	</menubar>
```
 
- `home` points the root and because we have set our [home-page]() it will show [welcome](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/resources/web/welcome.zul)

- `hello` mappings starting with _view/_ points to the auto-view so it will open [view/hello](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/resources/web/hello.zul), note that auto-view needs to be enabled `zk.view-automapping = true` 

- `people` points to [@GetMapping("/people")](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/java/be/enyed/zkboot/controller/DemoController.java) and wil open [interaction/people](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/resources/web/interaction/people.zul)

- `BE people` points to [@GetMapping("/people/{country}")](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/java/be/enyed/zkboot/controller/DemoController.java) and wil open [interaction/people](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/resources/web/interaction/people.zul)

- `about` triggers a command in our viewmodel [CommonViewmodel](https://github.com/dirkdeyne/zk-spring-boot-starter/blob/master/zk-spring-boot-demos/zkboot-interaction/src/main/java/be/enyed/zkboot/viewmodel/CommonViewmodel.java)

----

##### linking a resource in a other page

```xml
<?init class="org.zkoss.zk.ui.util.Composition" arg0="~./common/page_template.zul"?>
<?style href="~./css/custom-style.css" type="text/css" ?>
<zk>
	<include src="~./common/menu.zul" />
</zk>	
```
when we use `Composition`, import `css`-file or want to `include` other resources, we just use ZK's default way of loading resources from the class-path.

----

##### communication between a Spring-`@Controller` and a ZK-`viewmodel`

Basically using [zk-spring-boot-starter](https://github.com/dirkdeyne/zk-spring-boot-starter/tree/master/zk-spring-boot-starter) does not change the way `spring-mvc` en `ZK`...

Spring: use your `Controller` as if  ZK isn't there :wink: [Spring mvc-controller](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-controller)

```java
@Controller
public class DemoController {
  
  private final DemoService service;
  
  public DemoController(DemoService service) {
    this.service = service;
  }

  @GetMapping("/people/{country}")
  public String data(Model model, @PathVariable("country") Country country) {
    model.addAttribute("people", service.peopleBycountry(country))
         .addAttribute("country", country);
    return "interaction/people";
  }
}  

```

ZK: use your `Viewmodel` as if Spring isn't there :wink: [ZK-MVVM](http://books.zkoss.org/zk-mvvm-book/8.0/syntax/executionparam.html)

```java
public class PeopleViewmodel {
  
  private ListModelList<Person> people;
  private ListModelList<Country> countries = new ListModelList<>(Arrays.asList(Country.values()));
  private Country country;
  
  @Init
  public void init(@ExecutionParam("people") Set<Person> people, @ExecutionParam("country") Country country) {
    this.country = country;
    this.people = new ListModelList<>(people);
  }

```

_note you could also use `((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getAttribute("country")` to extract a attribute from the request_


----
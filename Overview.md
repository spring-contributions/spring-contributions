# Introduction #

Spring Contributions is a Spring extension providing a mechanism similar to the [Tapestry IoC Configurations](http://tapestry.apache.org/tapestry-ioc-configuration.html).

Contributions allow extensibility and modularity for your services (Spring beans). It is basically dependency injection with your dependencies defined in modules that you do not know about. This enables Eclipse-like extensions of your current services.

Spring Contributions offers two different types of contributions: ordered and mapped.

# Ordered Contributions #

Ordered contributions allow to add beans (services or simple objects) to a list including sorting constraints. The sorting is done via "after" and "before" constraints (as in Servlet 3.0) because of the modularity there is no single point for defining the order.

Ordered contributions may be used for:

  * some GoF patterns (like chain of responsibility or commnad)
  * or a menu system (a menu service getting a list of menu items from different modules)

Details about the ordering may be found in [Ordering of Contributions](OrderingOfContributions.md).

# Mapped Contributions #

Module 1:
```
<bean id="integerStrategy" class="org.springframework.contributions.impl.IntegerStrategy">
</bean>
<ctr:mapped-contribution to="strategies">
	<entry>
		<key>
			<value>java.lang.Integer</value>
		</key>
		<ref bean="integerStrategy" />
	</entry>
</ctr:mapped-contribution>
```

Module 2:
```
<bean id="stringStrategy" class="org.springframework.contributions.impl.StringStrategy">
</bean>
<ctr:mapped-contribution to="strategies">
	<entry key="java.lang.String" value-ref="stringStrategy" />
</ctr:mapped-contribution>
```

In another module use the contribution:
```
<bean class="org.springframework.contributions.impl.StrategyCallerImpl">
	<constructor-arg>
		<ctr:mapped-contribution-ref name="strategies" />
	</constructor-arg>
</bean>
```
# Java EE playground

## Java EE specifications used

- JAX-RS
- JMS
- JPA
- JTA
- JBatch

## Working

The [HelloResource](src/main/java/ninckblokje/playground/javaee/resource/HelloResource.java) starts the [helloWorld](src/main/resources/META-INF/batch-jobs/hello-world.xml)
batch job. This job will create 100 items to be processed. Each item will be send to the JMS queue `HelloWorldQueue` and
stored in the [BATCH_MESSAGE](src/main/java/ninckblokje/playground/javaee/entity/BatchMessage.java) table.

If the query parameter `crashCount` is specified with a value greater than 0, then every Nth item will be crashed and no
JMS message is send and no entry is stored in the database.

[HelloWorldMessageListener](src/main/java/ninckblokje/playground/javaee/listener/HelloWorldMessageListener.java) listenes
to the JMS queue `HelloWorldQueue` and will store every received message in the [JMS_MESSAGE](src/main/java/ninckblokje/playground/javaee/entity/JmsMessage.java)
table. Every 20th received message is crashed and will not be stored in the database. The message is routed to the default
ActiveMQ DLQ.

## TomEE ActiveMQ web console

- Download the ActiveMQ web console from: https://search.maven.org/artifact/org.apache.activemq/activemq-web-console
- Place the WAR in the `webapps` folder
- Add the following property to `conf/system.properties`: `webconsole.type = invm`

## TomEE configuration

- Add the following properties to `conf/system.properties`:
````properties
org.apache.batchee.spi.PersistenceManagerService = org.apache.batchee.container.services.persistence.JDBCPersistenceManagerService
persistence.database.jndi = java:openejb/Resource/JOBDB
tomee.mp.scan = all
````
- Add the following to `conf/tomee.xml`:
````xml
<tomee>

  <Resource id="JOBDB" type="javax.sql.DataSource">
    JdbcDriver  = org.apache.derby.jdbc.EmbeddedDriver
    JdbcUrl     = jdbc:derby:data/derby/JOBDB;create=true
    UserName    = admin
    Password    =
  </Resource>

  <Resource id="HSQLDB" type="javax.sql.DataSource">
    JdbcDriver  = org.hsqldb.jdbcDriver
    JdbcUrl     = jdbc:hsqldb:file:data/hsqldb/hsqldb
    UserName    = sa
    Password    = 
  </Resource>

  <Resource id="MyJmsResourceAdapter" type="ActiveMQResourceAdapter">
      BrokerXmlConfig =  broker:(tcp://localhost:61616)?persistent=true
      ServerUrl       =  tcp://localhost:61616
      DataSource      =  HSQLDB
      maximumRedeliveries = 0
  </Resource>

  <Resource id="MyJmsConnectionFactory" type="javax.jms.ConnectionFactory">
      ResourceAdapter = MyJmsResourceAdapter
  </Resource>

  <Container id="MyJmsMdbContainer" ctype="MESSAGE">
      ResourceAdapter = MyJmsResourceAdapter
  </Container>

  <Resource id="HelloWorldQueue" type="javax.jms.Queue"/>
</tomee>
````

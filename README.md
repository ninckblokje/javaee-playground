# Java EE playground

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

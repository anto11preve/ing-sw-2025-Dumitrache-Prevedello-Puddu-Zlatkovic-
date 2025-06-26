# Compile
`mvn clean package`
# Run
## Server
`java -jar target/GC06-1.0-Server.jar` will start the server making you choose the network interface and on default TCP and RMI ports
### Options
`--hostname name` will use `name` as hostname\

`--tcp-port port` will use `port` as the port for TCP connections (1234 if not provided)\
`--rmi-port port` will use `port` as the port for RMI connections (1099 if not provided)
## Client
`java -jar target/GC06-1.0-Client.jar` will open the TUI client
### Options
`--useRMI` will skip protocol selection and use RMI\
`--useTCP` will skip protocol selection and use TCP
#### Modifier
`--localhost` is a modifier of the previous two, and it will try to connect to localhost:defaultport automatically

# Code coverage
`mvn clean verify` and then open `target/site/jacoco/index.html`

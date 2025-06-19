# Compile
`mvn clean package`
# Run
## Server
`java -jar target/GC06-1.0-Server.jar` will start the server making you choose the network interface and on default TCP and RMI ports
### Options
`--hostname name` will use `name` as hostname
`--tcp-port port` will use `port` as the port for TCP connections
`--rmi-port port` will use `port` as the port for RMI connections
## Client
Double click on `target/GC06-1.0-Client.jar` or `java -jar target/GC06-1.0-Client.jar` will open GUI client
### Options
`--nogui` will start the game using TUI

# Code coverage
`mvn clean verify` and then open `target/site/jacoco/index.html`

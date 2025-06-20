package View.Client.Actions;

import View.Client.ClientState;

import java.util.List;
import java.util.Map;

public class ConnectAction implements Action {
    private final String hostname;
    private final Integer port;
    private final boolean useRMI;

    public ConnectAction(String hostname, Integer port, boolean useRMI) {
        this.hostname = hostname;
        this.port = port;

        this.useRMI = useRMI;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.connect(hostname, port, useRMI);
    }

    static public ActionConstructor getConstructor() {
        return new ActionConstructor() {

            @Override
            public Action create(Map<String, String> arguments) throws IllegalArgumentException {
                try {
                    final String hostname = arguments.get("hostname");
                    final int port = Integer.parseInt(arguments.get("port"));
                    final boolean useRMI = Boolean.parseBoolean(arguments.get("use-RMI"));

                    return new ConnectAction(hostname, port, useRMI);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }

            @Override
            public List<String> getArguments() {
                return List.of("hostname", "port", "use-RMI");
            }
        };
    }
}

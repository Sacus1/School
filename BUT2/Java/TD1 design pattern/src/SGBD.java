import java.util.ArrayList;

abstract class SGBD {
    ArrayList<Connection> connections = new ArrayList<Connection>();
   abstract void createConnection();
}
class SGBDOracle extends SGBD {
  public SGBDOracle() {
  }
  public void createConnection() {
    connections.add(new ConnectionOracle());
  }
}
class SGBDMysql extends SGBD {
  public SGBDMysql() {
  }
  public void createConnection() {
    connections.add(new ConnectionMysql());
  }
}
abstract class Connection {
}
class ConnectionOracle extends Connection {
  public ConnectionOracle() {
  }
}
class ConnectionMysql extends Connection {
  public ConnectionMysql() {
  }
}

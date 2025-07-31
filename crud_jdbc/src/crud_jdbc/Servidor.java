package crud_jdbc;

import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Servidor {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/usuario", new UsuarioController());
        server.setExecutor(null);
        server.start();
        JOptionPane.showMessageDialog(null, "Servidor rodando em http://localhost:8080/usuarios");
    }
}

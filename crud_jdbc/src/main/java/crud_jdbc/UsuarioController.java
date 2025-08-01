package crud_jdbc;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UsuarioController implements HttpHandler {
    private final UsuarioDao dao = new UsuarioDao();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");

        String metodo = exchange.getRequestMethod();
        String caminho = exchange.getRequestURI().getPath();
        String resposta = "";
        int status = 200;
        try{

            if ("OPTIONS".equalsIgnoreCase(metodo)) {
                exchange.sendResponseHeaders(204, -1); // No Content
                return;
            }

            if ("GET".equalsIgnoreCase(metodo)){
                resposta = gson.toJson(dao.listar());
            } else if ("POST".equalsIgnoreCase(metodo)) {
                Usuario usuario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Usuario.class);
                dao.criar(usuario);
                resposta ="Usuário criado.";
                status = 201;
            } else if ("PUT".equalsIgnoreCase(metodo)) {
                String[] partes = caminho.split("/");
                if(partes.length >=3){
                    int id = Integer.parseInt(partes[2]);
                    Usuario usuario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Usuario.class);
                    usuario.setId(id);
                    dao.atualizar(usuario);
                    resposta = "Usuário atualizado";
                }else {
                    status = 400;
                    resposta = "ID não Informado.";
                }
            } else if ("DELETE".equalsIgnoreCase(metodo)){
                String[] partes = caminho.split("/");
                if (partes.length>=3){
                    int id = Integer.parseInt(partes[2]);
                    dao.excluir(id);
                    resposta = "Usuário deletado.";
                }else {
                    status = 400;
                    resposta = "Id não informado";
                }
            }else {
                status = 405;
                resposta = "Método não permitido.";
            }
        } catch (Exception e) {
            status = 500;
            resposta = "erro Interno: "+ e.getMessage();
            e.printStackTrace();
        }

        // exchange.getRequestHeaders().set("Content-type", "application/json");
        exchange.sendResponseHeaders(status, resposta.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes(StandardCharsets.UTF_8));
        os.close();


    }
}

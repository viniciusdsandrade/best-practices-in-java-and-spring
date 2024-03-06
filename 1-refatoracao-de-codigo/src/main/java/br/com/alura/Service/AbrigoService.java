package br.com.alura.Service;

import br.com.alura.client.ClientHttpConfiguration;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class AbrigoService {

    private static final String BASE_URL = "http://localhost:8080/abrigos";

    private ClientHttpConfiguration client;

    public AbrigoService(ClientHttpConfiguration client) {
        this.client = client;
    }

    public void listarAbrigos() throws IOException, InterruptedException {
        HttpResponse<String> response = ClientHttpConfiguration.dispararRequisicaoGet(BASE_URL);
        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        System.out.println("Abrigos cadastrados:");
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            long id = jsonObject.get("id").getAsLong();
            String nome = jsonObject.get("nome").getAsString();
            System.out.println(id + " - " + nome);
        }
    }

    public void cadastrarAbrigo() throws IOException, InterruptedException {
        String nome = obterNomeAbrigo();
        String telefone = obterTelefoneAbrigo();
        String email = obterEmailAbrigo();

        JsonObject json = criarJsonAbrigo(nome, telefone, email);

        HttpResponse<String> response = enviarRequisicaoCadastrarAbrigo(json);

        processarRespostaCadastrarAbrigo(response);
    }

    private String obterNomeAbrigo() {
        System.out.println("Digite o nome do abrigo:");
        return new Scanner(System.in).nextLine();
    }

    private String obterTelefoneAbrigo() {
        System.out.println("Digite o telefone do abrigo:");
        return new Scanner(System.in).nextLine();
    }

    private String obterEmailAbrigo() {
        System.out.println("Digite o email do abrigo:");
        return new Scanner(System.in).nextLine();
    }

    private JsonObject criarJsonAbrigo(String nome, String telefone, String email) {
        JsonObject json = new JsonObject();
        json.addProperty("nome", nome);
        json.addProperty("telefone", telefone);
        json.addProperty("email", email);
        return json;
    }

    private HttpResponse<String> enviarRequisicaoCadastrarAbrigo(JsonObject json) throws IOException, InterruptedException {
        return ClientHttpConfiguration.dispararRequisicaoPost(BASE_URL, json);
    }

    private void processarRespostaCadastrarAbrigo(HttpResponse<String> response) {
        int statusCode = response.statusCode();
        String responseBody = response.body();
        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
            System.out.println(responseBody);
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
            System.out.println(responseBody);
        }
    }
}

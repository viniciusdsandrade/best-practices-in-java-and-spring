package br.com.alura.Service;

import br.com.alura.client.ClientHttpConfiguration;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PetService {

    private static final String BASE_URL = "http://localhost:8080/abrigos";

    private ClientHttpConfiguration client;

    public PetService(ClientHttpConfiguration client) {
        this.client = client;
    }

    public void listarPetsDoAbrigo() throws IOException, InterruptedException {
        String idOuNome = obterIdOuNomeAbrigo();

        HttpResponse<String> response = enviarRequisicaoListarPets(idOuNome);

        if (response != null)
            processarRespostaListarPets(response);
    }

    public void importarPetsDoAbrigo() throws InterruptedException {
        String idOuNome = obterIdOuNomeAbrigo();
        String nomeArquivo = obterNomeArquivoCSV();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            importarPetsDoArquivo(reader, idOuNome);
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + nomeArquivo);
        }
    }

    private String obterIdOuNomeAbrigo() {
        System.out.println("Digite o id ou nome do abrigo:");
        return new Scanner(System.in).nextLine();
    }

    private String obterNomeArquivoCSV() {
        System.out.println("Digite o nome do arquivo CSV:");
        return new Scanner(System.in).nextLine();
    }

    private HttpResponse<String> enviarRequisicaoListarPets(String idOuNome) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + idOuNome + "/pets"))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void processarRespostaListarPets(HttpResponse<String> response) {
        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
            return;
        }
        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        System.out.println("Pets cadastrados:");
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            long id = jsonObject.get("id").getAsLong();
            String tipo = jsonObject.get("tipo").getAsString();
            String nome = jsonObject.get("nome").getAsString();
            String raca = jsonObject.get("raca").getAsString();
            int idade = jsonObject.get("idade").getAsInt();
            System.out.println(id + " - " + tipo + " - " + nome + " - " + raca + " - " + idade + " ano(s)");
        }
    }

    private void importarPetsDoArquivo(BufferedReader reader, String idOuNome) throws IOException, InterruptedException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] campos = line.split(",");
            String tipo = campos[0];
            String nome = campos[1];
            String raca = campos[2];
            int idade = Integer.parseInt(campos[3]);
            String cor = campos[4];
            float peso = Float.parseFloat(campos[5]);

            JsonObject json = criarJsonPet(tipo, nome, raca, idade, cor, peso);

            HttpResponse<String> response = enviarRequisicao(idOuNome, json);

            int statusCode = response.statusCode();
            String responseBody = response.body();
            if (statusCode == 200) {
                System.out.println("Pet cadastrado com sucesso: " + nome);
            } else if (statusCode == 404) {
                System.out.println("Id ou nome do abrigo não encontrado!");
                break;
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o pet: " + nome);
                System.out.println(responseBody);
                break;
            }
        }
    }

    private JsonObject criarJsonPet(String tipo, String nome, String raca, int idade, String cor, float peso) {
        JsonObject json = new JsonObject();
        json.addProperty("tipo", tipo.toUpperCase());
        json.addProperty("nome", nome);
        json.addProperty("raca", raca);
        json.addProperty("idade", idade);
        json.addProperty("cor", cor);
        json.addProperty("peso", peso);
        return json;
    }

    private HttpResponse<String> enviarRequisicao(String idOuNome, JsonObject json) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + idOuNome + "/pets"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

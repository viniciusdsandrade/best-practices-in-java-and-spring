package br.com.alura;

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

public class AdopetConsoleApplication {

    private static final String BASE_URL = "http://localhost:8080/abrigos";

    public static void main(String[] args) {
        System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
        try {
            int opcaoEscolhida = 0;
            while (opcaoEscolhida != 5) {
                exibirOpcoes();
                opcaoEscolhida = obterOpcao();
                processarOpcao(opcaoEscolhida);
            }
            System.out.println("Finalizando o programa...");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void exibirOpcoes() {
        String menu = """
                \nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:
                1 -> Listar abrigos cadastrados
                2 -> Cadastrar novo abrigo
                3 -> Listar pets do abrigo
                4 -> Importar pets do abrigo
                5 -> Sair
                """;
        System.out.println(menu);
    }

    private static int obterOpcao() {
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    private static void processarOpcao(int opcaoEscolhida) throws IOException, InterruptedException {
        switch (opcaoEscolhida) {
            case 1 -> listarAbrigos();
            case 2 -> cadastrarAbrigo();
            case 3 -> listarPetsDoAbrigo();
            case 4 -> importarPetsDoAbrigo();
            case 5 -> {
                System.out.println("Saindo do programa...");
                System.exit(0);
            }
            default -> System.out.println("NÚMERO INVÁLIDO!");
        }
    }

    private static void listarAbrigos() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        System.out.println("Abrigos cadastrados:");
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            long id = jsonObject.get("id").getAsLong();
            String nome = jsonObject.get("nome").getAsString();
            System.out.println(id + " - " + nome);
        }
    }

    private static void cadastrarAbrigo() throws IOException, InterruptedException {
        String nome = obterNomeAbrigo();
        String telefone = obterTelefoneAbrigo();
        String email = obterEmailAbrigo();

        JsonObject json = criarJsonAbrigo(nome, telefone, email);

        HttpResponse<String> response = enviarRequisicaoCadastrarAbrigo(json);

        processarRespostaCadastrarAbrigo(response);
    }

    private static String obterNomeAbrigo() {
        System.out.println("Digite o nome do abrigo:");
        return new Scanner(System.in).nextLine();
    }

    private static String obterTelefoneAbrigo() {
        System.out.println("Digite o telefone do abrigo:");
        return new Scanner(System.in).nextLine();
    }

    private static String obterEmailAbrigo() {
        System.out.println("Digite o email do abrigo:");
        return new Scanner(System.in).nextLine();
    }

    private static JsonObject criarJsonAbrigo(String nome, String telefone, String email) {
        JsonObject json = new JsonObject();
        json.addProperty("nome", nome);
        json.addProperty("telefone", telefone);
        json.addProperty("email", email);
        return json;
    }

    private static HttpResponse<String> enviarRequisicaoCadastrarAbrigo(JsonObject json) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static void processarRespostaCadastrarAbrigo(HttpResponse<String> response) {
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

    private static void listarPetsDoAbrigo() throws IOException, InterruptedException {
        String idOuNome = obterIdOuNomeAbrigo();

        HttpResponse<String> response = enviarRequisicaoListarPets(idOuNome);

        if (response != null)
            processarRespostaListarPets(response);
    }

    private static HttpResponse<String> enviarRequisicaoListarPets(String idOuNome) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + idOuNome + "/pets"))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static void processarRespostaListarPets(HttpResponse<String> response) {
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

    private static void importarPetsDoAbrigo() throws InterruptedException {
        String idOuNome = obterIdOuNomeAbrigo();
        String nomeArquivo = obterNomeArquivoCSV();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            importarPetsDoArquivo(reader, idOuNome);
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + nomeArquivo);
        }
    }

    private static String obterIdOuNomeAbrigo() {
        System.out.println("Digite o id ou nome do abrigo:");
        return new Scanner(System.in).nextLine();
    }

    private static String obterNomeArquivoCSV() {
        System.out.println("Digite o nome do arquivo CSV:");
        return new Scanner(System.in).nextLine();
    }

    private static void importarPetsDoArquivo(BufferedReader reader, String idOuNome) throws IOException, InterruptedException {
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

    private static JsonObject criarJsonPet(String tipo, String nome, String raca, int idade, String cor, float peso) {
        JsonObject json = new JsonObject();
        json.addProperty("tipo", tipo.toUpperCase());
        json.addProperty("nome", nome);
        json.addProperty("raca", raca);
        json.addProperty("idade", idade);
        json.addProperty("cor", cor);
        json.addProperty("peso", peso);
        return json;
    }

    private static HttpResponse<String> enviarRequisicao(String idOuNome, JsonObject json) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + idOuNome + "/pets"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
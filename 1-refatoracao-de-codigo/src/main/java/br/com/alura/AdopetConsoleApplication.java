package br.com.alura;

import br.com.alura.Service.AbrigoService;
import br.com.alura.Service.PetService;
import br.com.alura.client.ClientHttpConfiguration;

import java.io.IOException;
import java.util.Scanner;

public class AdopetConsoleApplication {

    public static void main(String[] args) {

        ClientHttpConfiguration client = new ClientHttpConfiguration();
        AbrigoService abrigoService = new AbrigoService(client); // Instância do serviço de abrigo
        PetService petService = new PetService(client); // Instância do serviço de pet
        System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
        try {
            int opcaoEscolhida = 0;
            while (opcaoEscolhida != 5) {
                exibirOpcoes();
                opcaoEscolhida = obterOpcao();
                processarOpcao(abrigoService, petService, opcaoEscolhida); // Passando a instância do serviço de pet
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

    private static void processarOpcao(AbrigoService abrigoService, PetService petService, int opcaoEscolhida) throws IOException, InterruptedException {
        switch (opcaoEscolhida) {
            case 1 -> abrigoService.listarAbrigo();
            case 2 -> abrigoService.cadastrarAbrigo();
            case 3 -> petService.listarPetsDoAbrigo();
            case 4 -> petService.importarPetsDoAbrigo();
            case 5 -> {
                System.out.println("Saindo do programa...");
                System.exit(0);
            }
            default -> System.out.println("NÚMERO INVÁLIDO!");
        }
    }
}
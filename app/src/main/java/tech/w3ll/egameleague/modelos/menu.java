import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);


       while (true){
            System.out.println("Escolha uma opção");
            System.out.println("1 - Tela Login.");
            System.out.println("2 - Tela Torneios.");
            System.out.println("3 - Tela Resultados.");
            System.out.println("4 - Tela Novo Torneio.");
            System.out.println("5 - Tela Default 1.");
            System.out.println("6 - Tela Default 2.");
            System.out.println("7 - Sair.");
            int menu = entrada.nextInt();

            switch (menu) {

                case 1:
                    TelaLogin telaLogin = new TelaLogin();
                    telaLogin.exibir("Abrir tela login.");
                    break;

                case 2:
                    TelaToneios telaToneios = new TelaToneios();
                    telaToneios.exibir("Abrir tela torneios.");
                    break;

                case 3:
                    TelaResultados telaResultados = new TelaResultados();
                    telaResultados.exibir("Abrir tela resultados.");
                    break;

                case 4:
                    TelaNovoTorneio telaNovoTorneio = new TelaNovoTorneio();
                    telaNovoTorneio.exibir("Abri tela novo torneio");
                    break;

                case 5:
                    System.out.println("Tela Default 1");
                    break;

                case 6:
                    System.out.println("Tela Default 2");
                    break;

                case 7:
                    System.out.println("Sair");
                    break;

                default:
                    System.out.println("Escolha um numero valido.");
                    break;
            }
       }
    }
}

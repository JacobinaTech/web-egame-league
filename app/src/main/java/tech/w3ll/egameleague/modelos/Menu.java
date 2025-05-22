package modelos;

import repositorios.MenuInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu implements MenuInterface {
    int entradaMenu;

    Scanner menu = new Scanner(System.in);

    public void setEntradaMenu(){
        try {
            entradaMenu = menu.nextInt();
            menu.nextLine();
        } catch (InputMismatchException erroDeInput){
            System.out.println("Digite um número válido!");
            entradaMenu = 0;
            menu.nextLine();
        }
    }

    public void setEntradaMenu(int entrada){
        this.entradaMenu = entrada;
    }

    public int getEntradaMenu(){
        return entradaMenu;
    }

    public void mostrarMainMenu(){
        System.out.println("----------MENU----------");
        System.out.println("1- Fazer cadastro");
        System.out.println("2- Fazer login");
        System.out.println("3- Sair");
    }

    public void mostrarUserMenu(){
        System.out.println("----------MENU----------");
        System.out.println("1- Trocar nome de usuário");
        System.out.println("2- Trocar senha");
        System.out.println("3- Logoff");
    }
}

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

	public void menuHome() {
		Scanner ler = new Scanner(System.in);
		int opc = 0;

		MenuOpcoes menuOpcoes = new MenuOpcoes();

		doubleLine();
		do {
			System.out.println("-------------------------------- MENU --------------------------------");
			System.out.println("1 - Criar novo cliente");
			System.out.println("2 - Depósito");
			System.out.println("3 - Saque");
			System.out.println("4 - Transferência");
			System.out.println("5 - Saldo");
			System.out.println("6 - Listar clientes");
			System.out.println("7 - Sair");
			line();
			System.out.print("Escolha uma opção: ");

			try {
				opc = ler.nextInt();
				ler.nextLine(); // limpar buffer

				switch (opc) {
				case 1:
					menuOpcoes.criarClienteComContas(ler);
					break;
				case 2:
					if (menuOpcoes.depositar(ler)) {
						System.out.println("Depósito realizado com sucesso!");
						line();
						quebrarLinha();
					}
					break;
				case 3:
					if (menuOpcoes.sacar(ler)) {
						System.out.println("Saque realizado com sucesso!");
						line();
						quebrarLinha();
					}
					break;
				case 4:
					if (menuOpcoes.transferir(ler)) {
						System.out.println("Transferência realizada com sucesso!");
						line();
						quebrarLinha();
					}
					break;
				case 5:
					menuOpcoes.imprimirSaldo(ler);
					break;
				case 6:
					menuOpcoes.listarClientes();
					quebrarLinha();
					break;
				case 7:
					menuOpcoes.sairDoSistema();
					break;
				default:
					opcaoInvalida();
				}

			} catch (InputMismatchException e) {
				System.out.println("\nErro: entrada inválida!");
				line();
				ler.nextLine(); // limpar buffer
			}

		} while (opc != 7);

		ler.close();
	}

	// métodos adicionais
	public void line() {
		System.out.println("----------------------------------------------------------------------");
	}

	public void doubleLine() {
		System.out.println("======================================================================");
	}

	public void quebrarLinha() {
		System.out.println("");
	}

	public void opcaoInvalida() {
		System.out.println("\nOpção inválida!");
		line();
	}
}

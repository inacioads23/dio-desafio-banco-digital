
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

	public void menuPrincipal() {
		Scanner ler = new Scanner(System.in);
		int opc = 0;
		int tipoConta;

		Cliente client = null;
		Conta cc = null;
		Conta poupanca = null;

		System.out.println("===============================================================");
		do {
			System.out.println("---------------------------> MENU <----------------------------");
			System.out.println("1- Nova conta\n2- Depósito\n3- Saque\n4- Transferência\n5- Extrato\n6- Sair");
			System.out.print("Escolha uma opção: ");

			try {
				opc = ler.nextInt();
				ler.nextLine(); // limpar buffer

				switch (opc) {
				case 1:
					System.out.println("\n-------------------------> NOVA CONTA <------------------------");
					System.out.print("Digite o nome: ");
					String name = ler.next();

					client = new Cliente();
					client.setNome(name);

					cc = new ContaCorrente(client);
					poupanca = new ContaPoupanca(client);
					System.out.println("Contas criadas com sucesso.");
					line();
					break;

				case 2:
					System.out.println("\n--------------------------> DEPÓSITO <-------------------------");
					if (!clienteValido(client)) {
						break;
					}

					System.out.print("Digite o valor de Depósito: R$ ");
					double dep = ler.nextDouble();
					
					System.out.print("Conta para Depósito: 1- Conta Corrente; 2- Conta Poupança: ");
					tipoConta = ler.nextInt();

					if (tipoConta == 1) {
						cc.depositar(dep);
					} else if (tipoConta == 2) {
						poupanca.depositar(dep);
					} else {
						System.out.println("Opção inválida");
					}
					break;					

				case 3:
					System.out.println("\n---------------------------> SAQUE <---------------------------");
					if (!clienteValido(client)) {
						break;
					}

					System.out.print("Digite o valor do saque: R$ ");
					double saq = ler.nextDouble();

					System.out.print("Conta para Saque: 1- Conta Corrente; 2- Conta Poupança: ");
					tipoConta = ler.nextInt();

					if (tipoConta == 1) {
						cc.sacar(saq);
					} else if (tipoConta == 2) {
						poupanca.sacar(saq);
					} else {
						System.out.println("Opção inválida");
					}
					break;

				case 4:
					System.out.println("\n-----------------------> TRANSFERÊNCIA <-----------------------");
					if (!clienteValido(client)) {
						break;
					}

					System.out.print("Digite o valor da transferencia: R$ ");
					double transf = ler.nextDouble();
					
					System.out.print("Conta de Transferência: 1- Conta Corrente; 2- Conta Poupança: ");
					tipoConta = ler.nextInt();

					if (tipoConta == 1) {
						cc.transferir(transf, poupanca);
					} else if (tipoConta == 2) {
						poupanca.transferir(transf, cc);
					} else {
						System.out.println("Opção inválida");
					}
					break;

				case 5:
					System.out.println("\n--------------------------> EXTRATO <--------------------------");
					if (!clienteValido(client)) {
						break;
					}

					System.out.print("Tipo de Extrato: 1- Conta Corrente; 2- Poupanca; 3- Completo: ");
					int tipoExtrato = ler.nextInt();

					if (tipoExtrato == 1) {
						cc.imprimirExtrato();
						line();
					} else if (tipoExtrato == 2) {
						poupanca.imprimirExtrato();
						line();
					} else if (tipoExtrato == 3) {
						cc.imprimirExtrato();
						poupanca.imprimirExtrato();
						line();
					} else {
						System.out.println("Opção inválida");
						line();
					}
					break;

				case 6:
					System.out.println("\n---------------------------> SAIR <----------------------------");
					System.out.println("Saindo do sistema...");
					System.out.println("");
					Thread.sleep(2000);
					System.out.println("Sistema finalizado.");
					System.out.println("===============================================================");
					break;

				default:
					System.out.println("Opção inválida.");
				}

			} catch (

			InputMismatchException e) {
				System.out.println("Erro: entrada inválida. Digite apenas números.");
				ler.nextLine(); // limpar buffer
			} catch (InterruptedException e) {
				System.out.println("Pausa interrompida.");
			}

			System.out.println("");

		} while (opc != 6);

		ler.close();
	}

	private boolean clienteValido(Cliente client) {
		if (client == null || client.getNome() == null || client.getNome().isBlank()) {
			System.out.println("Crie uma conta primeiro!");
			return false;
		}
		return true;
	}

	private void line() {
		System.out.println("---------------------------------------------------------------");
	}

}
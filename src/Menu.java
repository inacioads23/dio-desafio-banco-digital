
import java.util.Scanner;

public class Menu {

	public void menuPrinciapl() {
		Scanner ler = new Scanner(System.in);
		int opc = 0;
		int tipoConta;

		Cliente client = new Cliente();

		Conta cc = new ContaCorrente(client);
		Conta poupanca = new ContaPoupanca(client);

		do {
			System.out.println("MENU");
			System.out.println("1- Nova conta\n2- Depósito\n3- Saque\n4- Transferência\n5- Extrato\n6- Sair");
			System.out.print("Escolha uma opção: ");
			opc = ler.nextInt();

			switch (opc) {
			case 1:
				System.out.println("\nNOVO CLIENTE");
				String name;

				System.out.print("Digite o nome: ");
				name = ler.next();
				client.setNome(name);
				System.out.println("");
				break;
			case 2:
				double dep;
				tipoConta = 0;
				System.out.println("\nDEPÓSITO");

				try {
					System.out.print("Digite o valor de Depósito: R$ ");
					dep = ler.nextDouble();
					System.out.print("Conta para Depósito: 1- Conta Corrente; 2- Poupanca: ");
					tipoConta = ler.nextInt();

					if (tipoConta == 1) {
						cc.depositar(dep);
					} else if (tipoConta == 2) {
						poupanca.depositar(dep);
					} else {
						System.out.println("Opção inválida");
					}
					
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				System.out.println("");
				break;
			case 3:
				double saq;
				tipoConta = 0;

				System.out.println("\nSAQUE");
				try {
					System.out.print("Digite o valor do saque: R$ ");
					saq = ler.nextDouble();
					System.out.print("Conta para Saque: 1- Conta Corrente; 2- Poupanca: ");
					tipoConta = ler.nextInt();

					if (tipoConta == 1) {
						cc.sacar(saq);
					} else if (tipoConta == 2) {
						poupanca.sacar(saq);
					} else {
						System.out.println("Opção inválida");
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				System.out.println("");
				break;
			case 4:
				double transf;
				tipoConta = 0;

				try {
					System.out.println("\nTRANSFERÊNCIA");
					System.out.print("Digite o valor da transferencia: R$ ");
					transf = ler.nextDouble();
					System.out.print("Conta para Transferência: 1- Conta Corrente; 2- Poupanca: ");
					tipoConta = ler.nextInt();

					if (tipoConta == 1) {
						poupanca.transferir(transf, cc);
					} else if (tipoConta == 2) {
						cc.transferir(transf, poupanca);
					} else {
						System.out.println("Opção inválida");
					}
				} catch (NullPointerException e) {
					e.printStackTrace();					
				}
				System.out.println("");
				break;
			case 5:
				System.out.println("\nEXTRATO");
				cc.imprimirExtrato();
				poupanca.imprimirExtrato();
				System.out.println("");
				break;
			case 6:
				System.out.println("\nSAIR");
				System.out.println("Saindo do Sistema...");
				try {
					Thread.sleep(2000); // 2 segundos de espera
					System.out.println("\nSistema Finalizado!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("");
				break;
			default:
				System.out.println("Opção inválida.");
			}

		} while (opc != 6);
	}

}

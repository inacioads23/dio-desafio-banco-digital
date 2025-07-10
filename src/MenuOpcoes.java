import java.util.List;
import java.util.Scanner;

public class MenuOpcoes {

	private MenuPrincipal menu = new MenuPrincipal();
	private Banco banco = new Banco("Banco Teste");

	protected void criarClienteComContas(Scanner ler) {
		System.out.println("\n---------------------------- NOVO CLIENTE ----------------------------");
		System.out.print("Digite o nome do cliente: ");
		String nome = ler.nextLine();

		if (!validarNome(nome)) {
			System.out.println("Cliente não criado!");
			menu.line();
			menu.quebrarLinha();
			return;
		}

		Cliente cliente = new Cliente();
		cliente.setNome(nome);

		cliente.addConta(new ContaCorrente(cliente));
		cliente.addConta(new ContaPoupanca(cliente));

		banco.addCliente(cliente);
		System.out.println("\nCliente e contas criados com sucesso!");
		menu.line();
		menu.quebrarLinha();
	}

	protected Cliente selecionarClienteComAutoSelecao(Scanner ler) {
		List<Cliente> clientes = banco.getClientes();

		if (clientes.isEmpty()) {
			System.out.println("\nNenhum cliente cadastrado!");
			menu.line();
			menu.quebrarLinha();
			return null;
		}

		if (clientes.size() == 1) {
			return clientes.get(0);
		}

		listarClientes();
		System.out.print("Digite o número do cliente: ");
		int idx = ler.nextInt();
		ler.nextLine();

		if (idx < 1 || idx > clientes.size()) {
			System.out.println("\nCliente inválido!");
			menu.line();
			menu.quebrarLinha();
			return null;
		}

		return clientes.get(idx - 1);
	}

	protected Conta selecionarContaPorTipo(Scanner ler, Cliente cliente, String mensagem) {
		System.out.print(mensagem);
		int tipo = ler.nextInt();
		ler.nextLine();

		for (Conta conta : cliente.getContas()) {
			if ((tipo == 1 && conta instanceof ContaCorrente) || (tipo == 2 && conta instanceof ContaPoupanca)) {
				return conta;
			}
		}

		System.out.println("\nConta não encontrada!");
		menu.line();
		menu.quebrarLinha();
		return null;
	}

	protected boolean depositar(Scanner ler) {
		System.out.println("\n------------------------------ DEPÓSITO ------------------------------");

		Cliente cliente = selecionarClienteComAutoSelecao(ler);
		if (cliente == null)
			return false;

		Conta conta = selecionarContaPorTipo(ler, cliente,
				"Escolha a conta para DEPÓSITO: 1- Conta Corrente; 2- Conta Poupança: ");
		if (conta == null)
			return false;

		System.out.print("Digite o valor: R$ ");
		double valor = ler.nextDouble();
		ler.nextLine();

		if (valor <= 0) {
			System.out.println("\nValor inválido!");
			menu.line();
			menu.quebrarLinha();
			return false;
		}

		conta.depositar(valor);
		menu.quebrarLinha();
		return true;
	}

	protected boolean sacar(Scanner ler) {
		System.out.println("\n------------------------------- SAQUE --------------------------------");

		Cliente cliente = selecionarClienteComAutoSelecao(ler);
		if (cliente == null)
			return false;

		Conta conta = selecionarContaPorTipo(ler, cliente,
				"Escolha a conta para DÉBITO: 1- Conta Corrente; 2- Conta Poupança: ");
		if (conta == null)
			return false;

		System.out.print("Digite o valor: R$ ");
		double valor = ler.nextDouble();
		ler.nextLine();

		if (valor <= 0) {
			System.out.println("\nValor inválido!");
			menu.line();
			menu.quebrarLinha();
			return false;
		}

		boolean sucesso = conta.sacar(valor);
		menu.quebrarLinha();
		return sucesso;
	}

	protected boolean transferir(Scanner ler) {
		List<Cliente> clientes = banco.getClientes();

		System.out.println("\n--------------------------- TRANSFERÊNCIA ----------------------------");

		Cliente origem = selecionarClienteComAutoSelecao(ler);
		if (origem == null)
			return false;

		Conta contaOrigem = selecionarContaPorTipo(ler, origem,
				"Escolha a conta para DÉBITO: 1- Conta Corrente; 2- Conta Poupança: ");
		if (contaOrigem == null)
			return false;

		System.out.print("Valor da transferência: R$ ");
		double valor = ler.nextDouble();
		ler.nextLine();

		if (valor <= 0) {
			System.out.println("\nValor inválido!");
			menu.line();
			return false;
		}

		Conta contaDestino;

		if (clientes.size() == 1) {
			if (contaOrigem instanceof ContaCorrente) {
				contaDestino = origem.getContas().stream().filter(c -> c instanceof ContaPoupanca).findFirst()
						.orElse(null);
			} else {
				contaDestino = origem.getContas().stream().filter(c -> c instanceof ContaCorrente).findFirst()
						.orElse(null);
			}

			if (contaDestino == null) {
				System.out.println("\nConta destino não encontrada!");
				menu.line();
				return false;
			}
		} else {
			System.out.println("\nEscolha abaixo o Cliente de destino:");
			Cliente destino = selecionarClienteComAutoSelecao(ler);
			if (destino == null)
				return false;

			if (destino.equals(origem)) {
				contaDestino = getOutraConta(origem, contaOrigem);
				if (contaDestino == null) {
					System.out.println("\nConta destino não encontrada!");
					menu.line();
					return false;
				}
			} else {
				contaDestino = selecionarContaPorTipo(ler, destino,
						"Escolha a conta para CRÉDITO: 1- Conta Corrente; 2- Conta Poupança: ");
				if (contaDestino == null)
					return false;
			}
		}

		boolean sucesso = contaOrigem.transferir(valor, contaDestino);
		menu.quebrarLinha();
		return sucesso;
	}

	protected Conta getOutraConta(Cliente cliente, Conta contaOrigem) {
		for (Conta conta : cliente.getContas()) {
			if (!conta.equals(contaOrigem)) {
				return conta;
			}
		}
		return null;
	}

	protected void imprimirSaldo(Scanner ler) {
		System.out.println("\n------------------------------- SALDO --------------------------------");

		Cliente cliente = selecionarClienteComAutoSelecao(ler);
		if (cliente == null)
			return;

		System.out.print("Tipo de saldo: 1- Conta Corrente; 2- Conta Poupança; 3- Total: ");
		int tipo = ler.nextInt();
		ler.nextLine();

		switch (tipo) {
		case 1:
			Conta contaCC = getContaPorTipo(cliente, ContaCorrente.class);
			contaCC.imprimirCabecalhoSaldo();
			contaCC.imprimirSaldo();
			break;
		case 2:
			Conta contaCP = getContaPorTipo(cliente, ContaPoupanca.class);
			contaCP.imprimirCabecalhoSaldo();
			contaCP.imprimirSaldo();
			break;
		case 3:
			cliente.getContas().get(0).imprimirCabecalhoSaldo();
			double saldoTotal = 0.0;

			for (Conta c : cliente.getContas()) {
				c.imprimirSaldo();
				saldoTotal += c.getSaldo(); // Assumindo que você tem um método getSaldo()
			}

			System.out.printf("\nSALDO TOTAL (Todas as contas): R$ %.2f%n", saldoTotal);
			break;
		default:
			opcaoInvalida();
		}

		menu.line();
		menu.quebrarLinha();
	}

	protected void imprimirExtratoPorTipo(Cliente cliente, Class<?> tipoConta) {
		for (Conta c : cliente.getContas()) {
			if (tipoConta.isInstance(c)) {
				c.imprimirSaldo();
				return;
			}
		}
		System.out.println("\nConta não encontrada!");
		menu.line();
	}

	protected void listarClientes() {
		System.out.println("\n------------------------- TODOS OS CLIENTES --------------------------");
		List<Cliente> clientes = banco.getClientes();

		if (clientes.isEmpty()) {
			System.out.println("\nNenhum cliente cadastrado!");
			menu.line();
		} else {
			for (int i = 0; i < clientes.size(); i++) {
				System.out.printf("%d- %s%n", i + 1, clientes.get(i).getNome());
			}
			menu.line();
		}
	}

	protected void sairDoSistema() {
		try {
			System.out.println("\n-------------------------------- SAIR --------------------------------");
			System.out.println("Saindo do sistema...");
			Thread.sleep(1000);
			System.out.println("\nSistema finalizado!");
			menu.doubleLine();
		} catch (InterruptedException e) {
			System.out.println("Pausa interrompida.");
		}
	}

	private Conta getContaPorTipo(Cliente cliente, Class<?> tipoConta) {
		for (Conta c : cliente.getContas()) {
			if (tipoConta.isInstance(c)) {
				return c;
			}
		}
		return null;
	}

	// Métodos adicionais
	public void opcaoInvalida() {
		System.out.println("\nOpção inválida!");
		menu.line();
	}

	public boolean validarNome(String nome) {
		if (nome == null || nome.isBlank()) {
			System.out.println("\nO nome não pode estar em branco!");
			return false;
		}

		if (!nome.matches("[a-zA-ZáéíóúãõâêîôûçÁÉÍÓÚÃÕÂÊÎÔÛÇ ]+")) {
			System.out.println("\nApenas letras e espaços são permitidos!");
			return false;
		}

		return true;
	}
}

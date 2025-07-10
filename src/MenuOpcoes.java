import java.util.List;
import java.util.Scanner;

public class MenuOpcoes {

	private Banco banco = new Banco("Banco Teste");

	protected void criarClienteComContas(Scanner ler) {
		System.out.println("\n--------------------------- NOVO CLIENTE ----------------------------");
		System.out.print("Digite o nome do cliente: ");
		String nome = ler.nextLine();

		if (!validarNome(nome)) {
			System.out.println("Cliente não criado!");
			line();
			quebrarLinha();
			return;
		}

		Cliente cliente = new Cliente();
		cliente.setNome(nome);

		cliente.addConta(new ContaCorrente(cliente));
		cliente.addConta(new ContaPoupanca(cliente));

		banco.addCliente(cliente);
		System.out.println("\nCliente e contas criados com sucesso!");
		line();
		quebrarLinha();
	}

	protected Cliente selecionarClienteComAutoSelecao(Scanner ler) {
		List<Cliente> clientes = banco.getClientes();

		if (clientes.isEmpty()) {
			System.out.println("\nNenhum cliente cadastrado!");
			line();
			quebrarLinha();
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
			line();
			quebrarLinha();
			return null;
		}

		return clientes.get(idx - 1);
	}

	protected Conta selecionarContaPorTipo(Scanner ler, Cliente cliente) {
		System.out.print("Escolha a conta: 1- Conta Corrente; 2- Conta Poupança: ");
		int tipo = ler.nextInt();
		ler.nextLine();

		for (Conta conta : cliente.getContas()) {
			if ((tipo == 1 && conta instanceof ContaCorrente) || (tipo == 2 && conta instanceof ContaPoupanca)) {
				return conta;
			}
		}

		System.out.println("\nConta não encontrada!");
		line();
		quebrarLinha();
		return null;
	}

	protected void depositar(Scanner ler) {
		List<Cliente> clientes = banco.getClientes();

		System.out.println("\n----------------------------- DEPÓSITO ------------------------------");
		if (clientes.isEmpty() || clientes.size() > 1) {
			System.out.println("Escolha abaixo o Cliente:");
		}

		Cliente cliente = selecionarClienteComAutoSelecao(ler);
		if (cliente == null)
			return;

		Conta conta = selecionarContaPorTipo(ler, cliente);
		if (conta == null)
			return;

		System.out.print("Digite o valor: R$ ");
		double valor = ler.nextDouble();
		ler.nextLine();

		if (valor <= 0) {
			System.out.println("\nValor inválido!");
			line();
			quebrarLinha();
			return;
		}

		conta.depositar(valor);
		quebrarLinha();
	}

	protected void sacar(Scanner ler) {
		List<Cliente> clientes = banco.getClientes();

		System.out.println("\n------------------------------- SAQUE -------------------------------");
		if (clientes.isEmpty() || clientes.size() > 1) {
			System.out.println("Escolha abaixo o Cliente:");
		}

		Cliente cliente = selecionarClienteComAutoSelecao(ler);
		if (cliente == null)
			return;

		Conta conta = selecionarContaPorTipo(ler, cliente);
		if (conta == null)
			return;

		System.out.print("Digite o valor: R$ ");
		double valor = ler.nextDouble();
		ler.nextLine();

		if (valor <= 0) {
			System.out.println("\nValor inválido!");
			line();
			return;
		}

		conta.sacar(valor);
		quebrarLinha();
	}

	protected void transferir(Scanner ler) {
		List<Cliente> clientes = banco.getClientes();

		System.out.println("\n--------------------------- TRANSFERÊNCIA ---------------------------");
		if (clientes.isEmpty() || clientes.size() > 1) {
			System.out.println("Escolha abaixo o Cliente:");
		}

		Cliente origem = selecionarClienteComAutoSelecao(ler);
		if (origem == null)
			return;

		Conta contaOrigem = selecionarContaPorTipo(ler, origem);
		if (contaOrigem == null)
			return;

		System.out.print("Valor da transferência: R$ ");
		double valor = ler.nextDouble();
		ler.nextLine();

		if (valor <= 0) {
			System.out.println("\nValor inválido!");
			line();
			return;
		}

		Conta contaDestino;
		if (clientes.size() == 1) {
			// Seleção automática de conta destino
			if (contaOrigem instanceof ContaCorrente) {
				contaDestino = origem.getContas().stream().filter(c -> c instanceof ContaPoupanca).findFirst()
						.orElse(null);
			} else {
				contaDestino = origem.getContas().stream().filter(c -> c instanceof ContaCorrente).findFirst()
						.orElse(null);
			}

			if (contaDestino == null) {
				System.out.println("\nConta destino não encontrada!");
				line();
				return;
			}

		} else {
			System.out.println("\nEscolha abaixo o Cliente de destino:");
			Cliente destino = selecionarClienteComAutoSelecao(ler);
			if (destino == null)
				return;

			if (destino.equals(origem)) {
				// Mesmo cliente: selecionar conta destino automaticamente
				contaDestino = getOutraConta(origem, contaOrigem);

				if (contaDestino == null) {
					System.out.println("\nConta destino não encontrada!");
					line();
					return;
				}
			} else {
				// Clientes diferentes: selecionar conta normalmente

				contaDestino = selecionarContaPorTipo(ler, destino);
				if (contaDestino == null)
					return;
			}
		}

		contaOrigem.transferir(valor, contaDestino);
		quebrarLinha();
	}

	protected Conta getOutraConta(Cliente cliente, Conta contaOrigem) {
		for (Conta conta : cliente.getContas()) {
			if (!conta.equals(contaOrigem)) {
				return conta;
			}
		}
		return null;
	}

	protected void imprimirExtrato(Scanner ler) {
		List<Cliente> clientes = banco.getClientes();

		System.out.println("\n------------------------------ EXTRATO ------------------------------");
		if (clientes.isEmpty() || clientes.size() > 1) {
			System.out.println("Escolha abaixo o Cliente:");
		}

		Cliente cliente = selecionarClienteComAutoSelecao(ler);
		if (cliente == null)
			return;

		System.out.print("Tipo de extrato: 1- Conta Corrente; 2- Conta Poupança; 3- Completo: ");
		int tipo = ler.nextInt();
		ler.nextLine();

		switch (tipo) {
		case 1:
			imprimirExtratoPorTipo(cliente, ContaCorrente.class);
			break;
		case 2:
			imprimirExtratoPorTipo(cliente, ContaPoupanca.class);
			break;
		case 3:
			for (Conta c : cliente.getContas()) {
				c.imprimirExtrato();
			}
			break;
		default:
			opcaoInvalida();
		}

		line();
		quebrarLinha();
	}

	protected void imprimirExtratoPorTipo(Cliente cliente, Class<?> tipoConta) {
		for (Conta c : cliente.getContas()) {
			if (tipoConta.isInstance(c)) {
				c.imprimirExtrato();
				return;
			}
		}
		System.out.println("\nConta não encontrada!");
		line();

	}

	protected void listarClientes() {
		System.out.println("\n------------------------- TODOS OS CLIENTES -------------------------");
		List<Cliente> clientes = banco.getClientes();
		for (int i = 0; i < clientes.size(); i++) {
			System.out.printf("%d - %s%n", i + 1, clientes.get(i).getNome());
		}
		line();
	}

	protected void sairDoSistema() {
		try {
			System.out.println("\n------------------------------- SAIR --------------------------------");
			System.out.println("Saindo do sistema...");
			Thread.sleep(1000);
			System.out.println("\nSistema finalizado!");
			doubleLine();
		} catch (InterruptedException e) {
			System.out.println("Pausa interrompida.");
		}
	}

	// Métodos adicionais
	private void line() {
		System.out.println("---------------------------------------------------------------------");
	}

	private void doubleLine() {
		System.out.println("=====================================================================");
	}

	private void quebrarLinha() {
		System.out.println("");
	}

	public void opcaoInvalida() {
		System.out.println("\nOpção inválida!");
		line();
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

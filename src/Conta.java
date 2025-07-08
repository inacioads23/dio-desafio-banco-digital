// 'abstract' não permite que essa classe seja instanciada (new), exceto pelas filhas (extends)

public abstract class Conta implements IConta {

	private static int SEQUENCIAL = 1;
	private static final int AGENCIA_PADAO = 1;

	protected int agencia;
	protected int numero;
	protected double saldo;
	protected Cliente cliente;

	// Construtor
	public Conta(Cliente cliente) {
		this.agencia = AGENCIA_PADAO;
		this.numero = SEQUENCIAL++; // adiciona + 1 a cada conta criada (SEQUENCIAL + 1)
		this.cliente = cliente;
	}

	// Apenas getters, para não permitir alteração nos dados
	public int getAgencia() {
		return agencia;
	}

	public int getNumero() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}

	// Métodos que vem da interface IConta
	@Override
	public void sacar(double valor) {
		if (saldo <= 0) {
			System.out.println("Sem saldo disponível para saque\n");
			imprimirSaldo();
		} else if (saldo < valor) {
			System.out.println("\nValor solicitado acima do saldo em conta");
			imprimirSaldo();
		} else {
			saldo = saldo - valor;
			// saldo -= valor // outra opção
		}
	}

	@Override
	public void depositar(double valor) {
		if (cliente.getNome().isEmpty()) {
			System.out.println("Conta não existe! Primeiro crie a conta!");
		} else {
			saldo = saldo + valor;
			// saldo += valor // outra opção
		}
	}

	@Override
	public void transferir(double valor, Conta contaDestino) {
		if (saldo <= 0) {
			System.out.println("Sem saldo disponível para transferência\n");
			imprimirSaldo();
		} else if (saldo < valor) {
			System.out.println("\nValor solicitado acima do saldo em conta");
			imprimirSaldo();
		} else {
			this.sacar(valor); // sacar da conta atual ('this')
			contaDestino.depositar(valor);
		}
	}

	// Método adicional
	protected void imprimirInfoComum() {
		System.out.println(String.format("Titular: %s", this.cliente.getNome()));
		System.out.println(String.format("Agencia: %d", this.agencia));
		System.out.println(String.format("Numero Conta: %d", this.numero));
		System.out.println(String.format("Saldo: R$ %.2f%n", this.saldo));
	}
	
	// IMPRIMIDO O SALDO APENAS DA CONTA CORRENTE
	private void imprimirSaldo() {
		System.out.println(String.format("Saldo em conta: R$ %.2f%n", this.saldo));
	}

}

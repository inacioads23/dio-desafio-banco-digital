// 'abstract' não permite que essa classe seja instanciada (new), exceto pelas filhas (extends)

public abstract class Conta implements IConta {

	private MenuPrincipal menu = new MenuPrincipal();
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
	public boolean sacar(double valor) {
		if (saldo <= 0) {
			System.out.println("\nSem saldo disponível para saque");
			menu.line();
			return false;
		} else if (saldo < valor) {
			System.out.println("\nValor solicitado acima do saldo em conta");
			menu.line();
			return false;
		} else {
			saldo = saldo - valor;
			// saldo -= valor // outra opção
			return true;
		}
	}

	@Override
	public boolean depositar(double valor) {
		if (cliente.getNome().isEmpty()) {
			System.out.println("\nConta não existe! Primeiro crie a conta!");
			menu.line();
			return false;
		} else if(valor <= 0){
			System.out.println("\nValor de Depósito inválido");
			menu.line();
			return false;
		}else {
			saldo = saldo + valor;
			// saldo += valor // outra opção
			return true;
		}
	}

	@Override
	public boolean transferir(double valor, Conta contaDestino) {
		if (saldo <= 0) {
			System.out.println("\nSem saldo disponível para transferência");
			menu.line();
			return false;
		} else if (saldo < valor) {
			System.out.println("\nValor solicitado acima do saldo em conta");
			menu.line();
			return false;
		} else {
			this.sacar(valor); // sacar da conta atual ('this')
			contaDestino.depositar(valor);
			return true;	
		}		
	}

	// Método adicional
	protected void imprimirCabecalhoSaldo() {		
		menu.quebrarLinha();
		menu.line();
		System.out.printf("Titular: %s    Agência: %d%n", cliente.getNome(), agencia);
	}
	
	protected void imprimirInfoComum() {		
		System.out.println(String.format("Numero Conta: %d", this.numero));
		System.out.println(String.format("Saldo: R$ %.2f", this.saldo));
	}
}


public class Main {

	public static void main(String[] args) {
		Cliente client = new Cliente();
		client.setNome("Inacio");
		
		Conta cc = new ContaCorrente(client);
		Conta poupanca = new ContaPoupanca(client);
		
		cc.sacar(100);
		cc.imprimirExtrato();		
		cc.transferir(50, poupanca);
		
		cc.depositar(100);
		cc.imprimirExtrato();
		poupanca.imprimirExtrato();
		
		cc.transferir(50, poupanca);
		cc.imprimirExtrato();
		poupanca.imprimirExtrato();

	}

}

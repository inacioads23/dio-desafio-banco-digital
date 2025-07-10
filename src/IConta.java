// Interface - Uma classe abstrata com todos os métodos abstratos
// Obriga a todos que a implementarem (implements), a também implementar todos os seus métodos
// Classe abstrata: Uma das ações: solicita alguma implementação que ela própria não consegue resolver

public interface IConta {

	// Métodos
	boolean sacar(double valor); // assinatura de método que as classes terão que respeitar
	
	boolean depositar(double valor);
	
	boolean transferir(double valor, Conta contaDestino);
	
	void imprimirSaldo(); // implementado nas contas individuais, pois eu havia transformado a Class Conta em abstract	
}

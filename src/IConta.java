// Interface - Uma classe abstrata com todos os métodos abstratos
// Obriga a todos que a implementarem (implements), a também implementar todos os seus métodos
// Classe abstrata: Uma das ações: solicita alguma implementação que ela própria não consegue resolver

public interface IConta {

	// Métodos
	void sacar(double valor); // assinatura de método que as classes terão que respeitar
	
	void depositar(double valor);
	
	void transferir(double valor, Conta contaDestino);
	
	void imprimirExtrato(); // implementado nas contas individuais, pois eu havia transformado a Class Conta em abstract

	
}

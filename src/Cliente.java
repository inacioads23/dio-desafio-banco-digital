
public class Cliente {
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
        if (!nome.matches("[a-zA-ZáéíóúãõâêîôûçÁÉÍÓÚÃÕÂÊÎÔÛÇ ]+")) {
            System.out.println("Nome inválido. Apenas letras são permitidas.");
            this.nome = "";
        } else {
            this.nome = nome;
        }
    }

}

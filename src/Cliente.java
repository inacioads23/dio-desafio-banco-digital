import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String nome;
    private List<Conta> contas;

    // Construtor
    public Cliente() {
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }    

    public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    // Novo método para adicionar conta
    public void addConta(Conta conta) {
        this.contas.add(conta);
    }
    
    
}

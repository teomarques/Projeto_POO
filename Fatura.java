import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fatura implements Serializable {
    private int numeroFatura;
    private Cliente cliente;
    private Date data;
    private ArrayList<Produto> produtos;

    /**
     * Construtor da classe Fatura
     */
    public Fatura(int numeroFatura, Cliente cliente, Date data) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }
        if (data == null) {
            throw new IllegalArgumentException("A data não pode ser nula.");
        }

        this.numeroFatura = numeroFatura;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    // Getters
    public int getNumeroFatura() {
        return numeroFatura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Date getData() {
        return data;
    }

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos); // Retorna uma cópia para proteger a lista original
    }

    // Adicionar um produto à fatura
    public void adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }
        produtos.add(produto);
    }

    // Calcular o total sem IVA
    public double calcularTotalSemIVA() {
        return produtos.stream()
                .mapToDouble(Produto::calcularValorTotal)
                .sum();
    }

    // Calcular o total com IVA
    public double calcularTotalComIVA() {
        return produtos.stream()
                .mapToDouble(Produto::calcularValorComIVA)
                .sum();
    }

    // Listar produtos da fatura
    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado na fatura.");
            return;
        }

        System.out.println("Produtos na Fatura:");
        for (Produto produto : produtos) {
            System.out.printf("Produto: %s | Quantidade: %d | Valor Total (sem IVA): %.2f | Valor com IVA: %.2f%n",
                    produto.getNome(), produto.getQuantidade(), produto.calcularValorTotal(), produto.calcularValorComIVA());
        }
    }

    // Exibir informações detalhadas da fatura
    public void exibirDetalhes() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("Fatura #%d | Cliente: %s | Data: %s%n", numeroFatura, cliente.getNome(), sdf.format(data));
        listarProdutos();
        System.out.printf("Total Sem IVA: %.2f | Total Com IVA: %.2f%n", calcularTotalSemIVA(), calcularTotalComIVA());
    }
}

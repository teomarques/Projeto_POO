import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * classe Fatura implementa Serializable
 */
public class Fatura implements Serializable {
    private int numeroFatura;
    private Cliente cliente;
    private Date data;
    private ArrayList<Produto> produtos;

    /**
     * Construtor da classe Fatura
     * @param numeroFatura
     * @param cliente
     * @param data
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

    /**
     * getter getNumeroFatura
     * @return
     */
    public int getNumeroFatura() {
        return numeroFatura;
    }

    /**
     * getter getCliente
     * @return
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * getter getData
     * @return
     */
    public Date getData() {
        return data;
    }

    /**
     * getter getProdutos
     * @return
     */
    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos); // Retorna uma cópia para proteger a lista original
    }

    /**
     * Método para adicioner produto ao Arraylist produtos
     * @param produto
     */
    public void adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }
        produtos.add(produto);
    }

    /**
     * M+etodo para calcular o total sem IVA
     * @return
     */
    public double calcularTotalSemIVA() {
        return produtos.stream()
                .mapToDouble(Produto::calcularValorTotal)
                .sum();
    }

    /**
     * Método para calcular o total com IVA
     * @return
     */
    public double calcularTotalComIVA() {
        return produtos.stream()
                .mapToDouble(produto -> produto.calcularValorComIVA(cliente)) // Passa o cliente
                .sum();
    }

    /**
     * Método para listar os produtos em uma fatura
     */
    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado na fatura.");
            return;
        }

        System.out.println("Produtos na Fatura:");
        for (Produto produto : produtos) {
            // Passa o cliente associado à fatura
            System.out.printf("Produto: %s | Quantidade: %d | Valor Total (sem IVA): %.2f | Valor com IVA: %.2f%n",
                    produto.getNome(), produto.getQuantidade(), produto.calcularValorTotal(), produto.calcularValorComIVA(cliente));
        }
    }

    /**
     * Método para exibir os detalhes de uma fatura de forma simples
     */
    public void exibirDetalhes() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("Fatura #%d | Cliente: %s | Data: %s%n", numeroFatura, cliente.getNome(), sdf.format(data));
        listarProdutos();
        System.out.printf("Total Sem IVA: %.2f | Total Com IVA: %.2f%n", calcularTotalSemIVA(), calcularTotalComIVA());
    }
}

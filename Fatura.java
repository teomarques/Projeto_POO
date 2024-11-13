import java.util.ArrayList;
import java.util.Date;

/**
 * Classe Fatura conectada com Cliente e Produto, gerencia uma lista de produtos e calcula o total com e sem IVA
 * Todos os produtos implementam a interface Taxavel
 */
public class Fatura {
    private int numeroFatura;
    private Cliente cliente;
    private Date data;
    private ArrayList<Produto> produtos;

    public Fatura(int numeroFatura, Cliente cliente, Date data) {
        this.numeroFatura = numeroFatura;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public double calcularTotalSemIVA() {
        return produtos.stream().mapToDouble(Produto::calcularValorTotal).sum();
    }

    public double calcularTotalComIVA() {
        return produtos.stream().mapToDouble(Produto::calcularValorComIVA).sum();
    }

    public void listarProdutos() {
        System.out.println("Fatura #" + numeroFatura + " - Cliente: " + cliente.getNome());
        produtos.forEach(produto -> {
            double valorTotal = produto.calcularValorTotal();
            double valorComIVA = produto.calcularValorComIVA();
            double taxaIVA = produto.getTaxaIVA();
            System.out.printf("Produto: %s | Total: %.2f | IVA: %.2f%% | Total c/ IVA: %.2f%n",
                    produto.getNome(), valorTotal, taxaIVA, valorComIVA);
        });
        System.out.printf("Total sem IVA: %.2f | Total com IVA: %.2f%n", calcularTotalSemIVA(), calcularTotalComIVA());
    }
}

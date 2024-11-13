import java.util.ArrayList;
import java.util.Date;

/**
 * Classe Main
 */
public class Main {
    public static void main(String[] args) {
        // 1. Criar clientes
        Cliente cliente1 = new Cliente("João Silva", "123456789", "Continente");
        Cliente cliente2 = new Cliente("Maria Costa", "987654321", "Açores");

        // 2. Criar produtos alimentares
        ArrayList<Certificacao> certificacoesProduto1 = new ArrayList<>();
        certificacoesProduto1.add(Certificacao.ISO22000);
        certificacoesProduto1.add(Certificacao.FSSC22000);
        certificacoesProduto1.add(Certificacao.HACCP);
        certificacoesProduto1.add(Certificacao.GMP);

        ProdutoAlimentar produtoAlimentar1 = new ProdutoAlimentar(
                "A001", "Arroz Integral", "Arroz integral orgânico", 10, 1.5, true, TipoTaxa.REDUZIDA, certificacoesProduto1);

        ProdutoAlimentar produtoAlimentar2 = new ProdutoAlimentar(
                "A002", "Vinho Tinto", "Vinho tinto português", 5, 10.0, false, TipoTaxa.INTERMEDIARIA, new ArrayList<>());

        // 3. Criar produtos de farmácia
        ProdutoFarmacia produtoFarmacia1 = new ProdutoFarmacia(
                "F001", "Creme para Bebês", "Creme hidratante", 3, 15.0, false, CategoriaFarmacia.BEBES, null);

        ProdutoFarmacia produtoFarmacia2 = new ProdutoFarmacia(
                "F002", "Antibiótico", "Medicamento com prescrição", 2, 50.0, true, CategoriaFarmacia.OUTRO, "Dr. Almeida");

        // 4. Criar uma fatura para cliente1 e adicionar produtos
        Fatura fatura1 = new Fatura(101, cliente1, new Date());
        fatura1.adicionarProduto(produtoAlimentar1);
        fatura1.adicionarProduto(produtoAlimentar2);
        fatura1.adicionarProduto(produtoFarmacia1);
        fatura1.adicionarProduto(produtoFarmacia2);

        // 5. Exibir detalhes da fatura com cálculo de total sem IVA e com IVA
        System.out.println("Detalhes da Fatura #101:");
        fatura1.listarProdutos();

        // 6. Criar uma segunda fatura para cliente2
        Fatura fatura2 = new Fatura(102, cliente2, new Date());
        fatura2.adicionarProduto(produtoAlimentar1);
        fatura2.adicionarProduto(produtoFarmacia2);

        // Exibir detalhes da segunda fatura
        System.out.println("\nDetalhes da Fatura #102:");
        fatura2.listarProdutos();
    }
}

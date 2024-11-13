import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Classe Main
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Cliente> clientes = new ArrayList<>();
    private static final ArrayList<Fatura> faturas = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;

        do {
            mostrarMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha
            switch (opcao) {
                case 1:
                    criarOuEditarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    criarFatura();
                    break;
                case 4:
                    listarFaturas();
                    break;
                case 5:
                    visualizarFatura();
                    break;
                case 6:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 6);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Criar e editar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Criar fatura");
        System.out.println("4. Listar faturas");
        System.out.println("5. Visualizar fatura");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarOuEditarCliente() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o NIF do cliente: ");
        String nif = scanner.nextLine();
        System.out.print("Digite a localização (Continente, Madeira ou Açores): ");
        String localizacao = scanner.nextLine();

        System.out.print("Escolha a taxa padrão do cliente (REDUZIDA, INTERMEDIARIA, NORMAL): ");
        TipoTaxa taxaPadrao = TipoTaxa.valueOf(scanner.nextLine().toUpperCase());

        Cliente cliente = new Cliente(nome, nif, localizacao, taxaPadrao);

        System.out.println("Deseja adicionar certificações? (s/n)");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            for (Certificacao cert : Certificacao.values()) {
                System.out.printf("Adicionar certificação %s? (s/n): ", cert);
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    cliente.adicionarCertificacao(cert);
                }
            }
        }

        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso.");
    }

    private static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Cliente cliente : clientes) {
                System.out.printf("Nome: %s, NIF: %s, Localização: %s, Taxa Padrão: %s%n",
                        cliente.getNome(), cliente.getNif(), cliente.getLocalizacao(), cliente.getTaxaPadrao());
            }
        }
    }

    private static void criarFatura() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados. Cadastre um cliente antes de criar uma fatura.");
            return;
        }

        System.out.println("Selecione o cliente para a fatura:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, clientes.get(i).getNome());
        }
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consumir a nova linha
        Cliente cliente = clientes.get(clienteIndex);

        Fatura fatura = new Fatura(faturas.size() + 1, cliente, new Date());

        String opcao;
        do {
            System.out.println("Deseja adicionar um produto alimentar ou de farmácia? (alimentar/farmacia/nao)");
            opcao = scanner.nextLine().toLowerCase();
            if (opcao.equals("alimentar")) {
                ProdutoAlimentar produto = criarProdutoAlimentar();
                fatura.adicionarProduto(produto);
            } else if (opcao.equals("farmacia")) {
                ProdutoFarmacia produto = criarProdutoFarmacia();
                fatura.adicionarProduto(produto);
            }
        } while (!opcao.equals("nao"));

        faturas.add(fatura);
        System.out.println("Fatura criada com sucesso.");
    }

    private static ProdutoAlimentar criarProdutoAlimentar() {
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição do produto: ");
        String descricao = scanner.nextLine();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        System.out.print("Valor unitário: ");
        double valorUnitario = scanner.nextDouble();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("É biológico? (s/n): ");
        boolean isBiologico = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("Tipo de taxa (REDUZIDA, INTERMEDIARIA, NORMAL): ");
        TipoTaxa tipoTaxa = TipoTaxa.valueOf(scanner.nextLine().toUpperCase());

        ArrayList<Certificacao> certificacoes = new ArrayList<>();
        System.out.println("Deseja adicionar certificações? (s/n)");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            for (Certificacao cert : Certificacao.values()) {
                System.out.printf("Adicionar certificação %s? (s/n): ", cert);
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    certificacoes.add(cert);
                }
            }
        }

        return new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, isBiologico, tipoTaxa, certificacoes);
    }

    private static ProdutoFarmacia criarProdutoFarmacia() {
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição do produto: ");
        String descricao = scanner.nextLine();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        System.out.print("Valor unitário: ");
        double valorUnitario = scanner.nextDouble();
        scanner.nextLine(); // Consumir a nova linha

        System.out.print("Possui prescrição? (s/n): ");
        boolean prescricao = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("Categoria (BELEZA, BEM_ESTAR, BEBES, ANIMAIS, OUTRO): ");
        CategoriaFarmacia categoria = CategoriaFarmacia.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Nome do médico (deixe vazio se não houver prescrição): ");
        String medicoPrescritor = scanner.nextLine();

        return new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, prescricao, categoria, medicoPrescritor.isEmpty() ? null : medicoPrescritor);
    }

    private static void listarFaturas() {
        if (faturas.isEmpty()) {
            System.out.println("Não há faturas cadastradas.");
        } else {
            System.out.println("Lista de Faturas:");
            for (Fatura fatura : faturas) {
                System.out.printf("Fatura #%d, Cliente: %s, Data: %s, Total (sem IVA): %.2f, Total (com IVA): %.2f%n",
                        fatura.getNumeroFatura(), fatura.getCliente().getNome(), fatura.getData(),
                        fatura.calcularTotalSemIVA(), fatura.calcularTotalComIVA());
            }
        }
    }

    private static void visualizarFatura() {
        System.out.print("Digite o número da fatura para visualizar: ");
        int numeroFatura = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        Fatura fatura = faturas.stream()
                .filter(f -> f.getNumeroFatura() == numeroFatura)
                .findFirst()
                .orElse(null);

        if (fatura == null) {
            System.out.println("Fatura não encontrada.");
        } else {
            fatura.listarProdutos();
        }
    }
}

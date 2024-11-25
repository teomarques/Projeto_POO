import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe Main
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Cliente> clientes = new ArrayList<>();
    private static final ArrayList<Fatura> faturas = new ArrayList<>();

    /**
     * Método Main
     */
    public static void main(String[] args) {
        int opcao = -1;
        do {
            mostrarMenu();
            boolean entradaValida = false;

            // Garantir leitura correta da opção
            while (!entradaValida) {
                try {
                    System.out.print("Digite uma opção: ");
                    opcao = scanner.nextInt(); // Lê o próximo inteiro
                    scanner.nextLine(); // Consumir a nova linha após nextInt()
                    entradaValida = true; // Entrada válida, sai do loop

                    // Processar a opção selecionada imediatamente
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
                            entradaValida = false; // Permite repetir o menu
                    }

                } catch (Exception e) {
                    System.out.println("Erro: Por favor, insira um número inteiro válido.");
                    scanner.nextLine(); // Limpa o buffer para evitar loop infinito
                }
            }

        } while (opcao != 6);
    }

    private static void mostrarMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Criar e editar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Criar fatura");
        System.out.println("4. Listar faturas");
        System.out.println("5. Visualizar fatura");
        System.out.println("6. Sair");
    }

    private static void criarOuEditarCliente() {
        // Criando e editando cliente
        String nome = lerNomeCliente();
        String nif = lerNifCliente();
        String localizacao = lerLocalizacaoCliente();
        TipoTaxa taxaPadrao = lerTipoTaxa();

        Cliente cliente = new Cliente(nome, nif, localizacao, taxaPadrao);

        // Adicionando certificações se necessário
        adicionarCertificacoes(cliente);

        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso.");
    }

    private static String lerNomeCliente() {
        while (true) {
            try {
                System.out.print("Digite o nome do cliente: ");
                String nome = scanner.nextLine().trim();
                if (nome.isEmpty()) {
                    throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
                }
                return nome;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static String lerNifCliente() {
        while (true) {
            try {
                System.out.print("Digite o NIF do cliente: ");
                String nif = scanner.nextLine().trim();

                if (nif.isEmpty()) {
                    throw new IllegalArgumentException("O NIF não pode ser vazio.");
                }

                if (!nif.matches("\\d+")) {
                    throw new IllegalArgumentException("O NIF deve ser composto apenas por números.");
                }

                return nif;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static String lerLocalizacaoCliente() {
        while (true) {
            try {
                System.out.print("Digite a localização (Continente, Madeira ou Açores): ");
                String localizacao = scanner.nextLine().trim();

                if (localizacao.isEmpty()) {
                    throw new IllegalArgumentException("A localização não pode ser vazia.");
                }

                if (!localizacao.equalsIgnoreCase("Continente") && !localizacao.equalsIgnoreCase("Madeira") && !localizacao.equalsIgnoreCase("Açores")) {
                    throw new IllegalArgumentException("A localização deve ser uma das seguintes: Continente, Madeira ou Açores.");
                }

                return localizacao;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static TipoTaxa lerTipoTaxa() {
        while (true) {
            try {
                System.out.print("Escolha a taxa do cliente (REDUZIDA, INTERMEDIARIA, NORMAL): ");
                String tipoTaxaInput = scanner.nextLine().toUpperCase().trim();

                if (tipoTaxaInput.isEmpty()) {
                    throw new IllegalArgumentException("O tipo de taxa não pode ser vazio.");
                }

                return TipoTaxa.valueOf(tipoTaxaInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Taxa inválida. Escolha entre REDUZIDA, INTERMEDIARIA ou NORMAL.");
            }
        }
    }

    private static void adicionarCertificacoes(Cliente cliente) {
        System.out.println("Deseja adicionar certificações? (s/n)");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            for (Certificacao cert : Certificacao.values()) {
                System.out.printf("Adicionar certificação %s? (s/n): ", cert);
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    cliente.adicionarCertificacao(cert);
                }
            }
        }
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

        Fatura fatura = new Fatura(faturas.size() + 1, cliente, new java.util.Date());

        // Perguntar e adicionar produtos à fatura
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

        // Verificando se a fatura tem produtos
        if (fatura.getProdutos().isEmpty()) {
            System.out.println("Erro: A fatura deve conter pelo menos um produto.");
            return;
        }

        faturas.add(fatura);
        System.out.println("Fatura criada com sucesso.");
    }

    private static ProdutoAlimentar criarProdutoAlimentar() {
        // Criar produto alimentar com exceções
        while (true) {
            try {
                System.out.print("Código do produto: ");
                String codigo = scanner.nextLine();

                System.out.print("Nome do produto: ");
                String nome = scanner.nextLine();

                System.out.print("Descrição do produto: ");
                String descricao = scanner.nextLine();

                int quantidade = lerQuantidadeProduto();
                double valorUnitario = lerValorUnitarioProduto();

                System.out.print("É biológico? (s/n): ");
                boolean isBiologico = scanner.nextLine().equalsIgnoreCase("s");

                TipoTaxa tipoTaxa = lerTipoTaxa();

                ArrayList<Certificacao> certificacoes = new ArrayList<>();
                adicionarCertificacoesProduto(certificacoes);

                return new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, isBiologico, tipoTaxa, certificacoes);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static ProdutoFarmacia criarProdutoFarmacia() {
        while (true) {
            try {
                System.out.print("Código do produto: ");
                String codigo = scanner.nextLine();

                System.out.print("Nome do produto: ");
                String nome = scanner.nextLine();

                System.out.print("Descrição do produto: ");
                String descricao = scanner.nextLine();

                // Validar quantidade e valor unitário
                int quantidade = lerQuantidadeProduto();
                double valorUnitario = lerValorUnitarioProduto();

                System.out.print("Possui prescrição? (s/n): ");
                boolean prescricao = scanner.nextLine().equalsIgnoreCase("s");

                CategoriaFarmacia categoria = lerCategoriaFarmacia();

                // Se o produto tem prescrição, solicitar o nome do médico
                String medicoPrescritor = null;
                if (prescricao) {
                    System.out.print("Nome do médico que prescreveu (deixe vazio se não houver): ");
                    medicoPrescritor = scanner.nextLine().trim();
                    // Se o campo do médico estiver vazio, lançar exceção
                    if (medicoPrescritor.isEmpty()) {
                        throw new IllegalArgumentException("O nome do médico é obrigatório para produtos com prescrição.");
                    }
                }

                // Criando o produto de farmácia com as informações fornecidas
                return new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, prescricao, categoria, medicoPrescritor);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
    private static int lerQuantidadeProduto() {
        while (true) {
            try {
                System.out.print("Quantidade: ");
                int quantidade = Integer.parseInt(scanner.nextLine());
                if (quantidade <= 0) {
                    throw new IllegalArgumentException("A quantidade deve ser maior que 0.");
                }
                return quantidade;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número válido para a quantidade.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static double lerValorUnitarioProduto() {
        while (true) {
            try {
                System.out.print("Valor unitário: ");
                double valorUnitario = Double.parseDouble(scanner.nextLine());
                if (valorUnitario <= 0) {
                    throw new IllegalArgumentException("O valor unitário deve ser maior que 0.");
                }
                return valorUnitario;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número válido para o valor unitário.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void adicionarCertificacoesProduto(ArrayList<Certificacao> certificacoes) {
        System.out.println("Deseja adicionar certificações? (s/n)");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            for (Certificacao cert : Certificacao.values()) {
                System.out.printf("Adicionar certificação %s? (s/n): ", cert);
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    certificacoes.add(cert);
                }
            }
        }
    }

    private static CategoriaFarmacia lerCategoriaFarmacia() {
        while (true) {
            try {
                System.out.print("Categoria (BELEZA, BEM_ESTAR, BEBES, ANIMAIS, OUTRO): ");
                String categoriaInput = scanner.nextLine().trim().replace(" ", "_").toUpperCase();

                if (categoriaInput.isEmpty()) {
                    throw new IllegalArgumentException("A categoria não pode ser vazia.");
                }

                return CategoriaFarmacia.valueOf(categoriaInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Categoria inválida. " + e.getMessage());
                System.out.println("Por favor, escolha entre BELEZA, BEM_ESTAR, BEBES, ANIMAIS ou OUTRO.");
            }
        }
    }

    private static void listarFaturas() {
        if (faturas.isEmpty()) {
            System.out.println("Não há faturas cadastradas.");
        } else {
            System.out.println("Lista de Faturas:");
            for (Fatura fatura : faturas) {
                System.out.printf("Fatura #%d, Cliente: %s, Localização: %s, Total Sem IVA: %.2f, Total Com IVA: %.2f%n",
                        fatura.getNumeroFatura(), fatura.getCliente().getNome(),
                        fatura.getCliente().getLocalizacao(), fatura.calcularTotalSemIVA(), fatura.calcularTotalComIVA());
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
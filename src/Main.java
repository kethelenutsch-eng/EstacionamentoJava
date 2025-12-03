import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

// Parte deste código foi criada e ajustada com apoio do ChatGPT (OpenAI).

// Classe que representa um veículo
class Veiculo {
    // Atributos principais do veículo
    private String placa;                 // Placa do veículo
    private String tipo;                  // "CARRO" ou "MOTO"
    private LocalDateTime horaEntrada;    // Horário de entrada
    private LocalDateTime horaSaida;      // Horário de saída (pode ser null enquanto estiver estacionado)

    // Construtor
    public Veiculo(String placa, String tipo, LocalDateTime horaEntrada) {
        this.placa = placa;
        this.tipo = tipo;
        this.horaEntrada = horaEntrada;
        this.horaSaida = null;
    }

    // Getters e setters
    public String getPlaca() {
        return placa;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }
}

// Classe que representa o estacionamento
class Estacionamento {
    private int capacidade;                    // Número máximo de vagas
    private ArrayList<Veiculo> veiculos;       // Veículos atualmente estacionados
    private double totalArrecadado;            // Total de dinheiro arrecadado

    // Construtor
    public Estacionamento(int capacidade) {
        this.capacidade = capacidade;
        this.veiculos = new ArrayList<Veiculo>();  // Uso de ArrayList conforme prática
        this.totalArrecadado = 0.0;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getQuantidadeOcupada() {
        return veiculos.size();
    }

    public int getQuantidadeDisponivel() {
        return capacidade - veiculos.size();
    }

    public double getTotalArrecadado() {
        return totalArrecadado;
    }

    public ArrayList<Veiculo> getVeiculos() {
        return veiculos;
    }

    // Verifica se o estacionamento está cheio
    public boolean estaCheio() {
        return veiculos.size() >= capacidade;
    }

    // Adiciona valor ao total arrecadado
    public void adicionarArrecadacao(double valor) {
        totalArrecadado = totalArrecadado + valor;
    }

    // Adiciona veículo (se não estiver cheio)
    public boolean adicionarVeiculo(Veiculo v) {
        if (estaCheio()) {
            return false;
        }
        veiculos.add(v);
        return true;
    }

    // Remove veículo
    public boolean removerVeiculo(Veiculo v) {
        return veiculos.remove(v);
    }

    // Busca veículo pela placa (ignorando maiúsculas/minúsculas)
    public Veiculo buscarPorPlaca(String placa) {
        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo v = veiculos.get(i);
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }
}

// Classe principal com o menu e interação com o usuário
public class Main {

    // Parte deste código foi criada e ajustada com apoio do ChatGPT (OpenAI).

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Cria estacionamento com uma capacidade pré-definida (pode mudar esse valor)
        Estacionamento estacionamento = new Estacionamento(10);

        int opcao;

        // Loop principal do menu
        do {
            mostrarMenu();
            opcao = lerInteiro(sc, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    registrarEntrada(sc, estacionamento);
                    break;
                case 2:
                    registrarSaida(sc, estacionamento);
                    break;
                case 3:
                    mostrarVagas(estacionamento);
                    break;
                case 4:
                    listarVeiculos(estacionamento);
                    break;
                case 5:
                    pesquisarVeiculo(sc, estacionamento);
                    break;
                case 6:
                    mostrarFaturamento(estacionamento);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema. Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println(); // linha em branco para organizar a saída

        } while (opcao != 0);

        sc.close();
    }

    // Exibe o menu principal
    private static void mostrarMenu() {
        System.out.println("========== SISTEMA DE ESTACIONAMENTO ==========");
        System.out.println("1 - Registrar ENTRADA de veículo");
        System.out.println("2 - Registrar SAÍDA de veículo");
        System.out.println("3 - Mostrar quantidade de vagas disponíveis");
        System.out.println("4 - Mostrar todos os veículos presentes");
        System.out.println("5 - Pesquisar veículo por placa");
        System.out.println("6 - Relatório de faturamento (total arrecadado)");
        System.out.println("0 - Sair");
        System.out.println("===============================================");
    }

    // Lê um número inteiro do usuário com tratamento básico
    private static int lerInteiro(Scanner sc, String mensagem) {
        int valor;
        while (true) {
            System.out.print(mensagem);
            if (sc.hasNextInt()) {
                valor = sc.nextInt();
                sc.nextLine(); // consome o enter
                return valor;
            } else {
                System.out.println("Valor inválido. Digite um número inteiro.");
                sc.nextLine(); // descarta entrada inválida
            }
        }
    }

    // Lê o tipo do veículo (CARRO ou MOTO)
    private static String lerTipoVeiculo(Scanner sc) {
        int opcao;
        while (true) {
            System.out.println("Informe o tipo de veículo:");
            System.out.println("1 - Carro");
            System.out.println("2 - Moto");
            opcao = lerInteiro(sc, "Opção: ");

            if (opcao == 1) {
                return "CARRO";
            } else if (opcao == 2) {
                return "MOTO";
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Lê o horário (entrada ou saída) usando LocalDateTime
    private static LocalDateTime lerDataHora(Scanner sc, String texto) {
        while (true) {
            System.out.println("Como deseja informar o horário de " + texto + "?");
            System.out.println("1 - Usar horário atual do sistema");
            System.out.println("2 - Informar hora e minuto (do dia de hoje)");
            int opcao = lerInteiro(sc, "Opção: ");

            if (opcao == 1) {
                // usa LocalDateTime.now()
                return LocalDateTime.now();
            } else if (opcao == 2) {
                System.out.print("Hora (0-23): ");
                int hora = sc.nextInt();
                System.out.print("Minuto (0-59): ");
                int minuto = sc.nextInt();
                sc.nextLine(); // consome enter

                if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
                    System.out.println("Hora ou minuto inválidos. Tente novamente.");
                } else {
                    LocalDateTime agora = LocalDateTime.now();
                    // cria um LocalDateTime com a data de hoje e hora/minuto informados
                    return LocalDateTime.of(
                            agora.getYear(),
                            agora.getMonthValue(),
                            agora.getDayOfMonth(),
                            hora,
                            minuto);
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Registra entrada de veículo
    private static void registrarEntrada(Scanner sc, Estacionamento estacionamento) {
        if (estacionamento.estaCheio()) {
            System.out.println("Estacionamento cheio. Não é possível registrar nova entrada.");
            return;
        }

        System.out.print("Informe a placa do veículo: ");
        String placa = sc.nextLine().trim().toUpperCase();

        // Verifica se o veículo já está estacionado
        Veiculo veiculoExistente = estacionamento.buscarPorPlaca(placa);
        if (veiculoExistente != null) {
            System.out.println("Este veículo já está estacionado.");
            return;
        }

        String tipo = lerTipoVeiculo(sc);
        LocalDateTime horaEntrada = lerDataHora(sc, "entrada");

        Veiculo v = new Veiculo(placa, tipo, horaEntrada);
        boolean entrou = estacionamento.adicionarVeiculo(v);

        if (entrou) {
            System.out.println("Entrada registrada com sucesso!");
            System.out.println("Veículo: " + placa + " | Tipo: " + tipo + " | Entrada: " + horaEntrada.toLocalTime());
        } else {
            System.out.println("Não foi possível registrar a entrada (estacionamento cheio).");
        }
    }

    // Registra saída de veículo, calcula tempo e valor
    private static void registrarSaida(Scanner sc, Estacionamento estacionamento) {
        if (estacionamento.getQuantidadeOcupada() == 0) {
            System.out.println("Não há veículos estacionados no momento.");
            return;
        }

        System.out.print("Informe a placa do veículo para saída: ");
        String placa = sc.nextLine().trim().toUpperCase();

        Veiculo v = estacionamento.buscarPorPlaca(placa);
        if (v == null) {
            System.out.println("Veículo não encontrado no estacionamento.");
            return;
        }

        LocalDateTime horaSaida = lerDataHora(sc, "saída");

        if (horaSaida.isBefore(v.getHoraEntrada())) {
            System.out.println("Horário de saída não pode ser anterior à entrada.");
            return;
        }

        v.setHoraSaida(horaSaida);

        // Calcula quantidade de horas usando ChronoUnit.MINUTES e Math.ceil
        long minutos = ChronoUnit.MINUTES.between(v.getHoraEntrada(), v.getHoraSaida());
        double horasArredondadas = Math.ceil(minutos / 60.0);
        if (horasArredondadas <= 0) {
            horasArredondadas = 1; // garante pelo menos 1 hora
        }

        // Calcula valor de acordo com o tipo
        double valor = calcularValor(horasArredondadas, v.getTipo());

        // Soma ao total arrecadado e remove o veículo do estacionamento
        estacionamento.adicionarArrecadacao(valor);
        estacionamento.removerVeiculo(v);

        System.out.println("Saída registrada com sucesso!");
        System.out.println("Placa: " + v.getPlaca());
        System.out.println("Tipo: " + v.getTipo());
        System.out.println("Entrada: " + v.getHoraEntrada().toLocalTime());
        System.out.println("Saída:   " + v.getHoraSaida().toLocalTime());
        System.out.println("Tempo de permanência: " + (long) horasArredondadas + " hora(s).");
        System.out.printf("Valor a pagar: R$ %.2f%n", valor);
    }

    // Calcula o valor a pagar com base nas horas e no tipo do veículo
    private static double calcularValor(double horas, String tipo) {
        /*
         * Regras obrigatórias para CARRO:
         *  - 1ª hora: R$ 12,00
         *  - Demais horas: R$ 8,00
         *
         * Regras opcionais (extra) para MOTO:
         *  - 1ª hora: R$ 8,00
         *  - Demais horas: R$ 5,00
         */

        double primeiraHora;
        double horaAdicional;

        if (tipo.equals("CARRO")) {
            primeiraHora = 12.0;
            horaAdicional = 8.0;
        } else { // "MOTO"
            primeiraHora = 8.0;
            horaAdicional = 5.0;
        }

        if (horas <= 1) {
            return primeiraHora;
        } else {
            return primeiraHora + (horas - 1) * horaAdicional;
        }
    }

    // Mostra quantidade de vagas livres e ocupadas
    private static void mostrarVagas(Estacionamento estacionamento) {
        System.out.println("Vagas totais: " + estacionamento.getCapacidade());
        System.out.println("Vagas ocupadas: " + estacionamento.getQuantidadeOcupada());
        System.out.println("Vagas disponíveis: " + estacionamento.getQuantidadeDisponivel());
    }

    // Lista todos os veículos presentes no estacionamento (sem toString)
    private static void listarVeiculos(Estacionamento estacionamento) {
        ArrayList<Veiculo> veiculos = estacionamento.getVeiculos();

        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo está estacionado no momento.");
            return;
        }

        System.out.println("Veículos atualmente estacionados:");
        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo v = veiculos.get(i);
            String texto = "Placa: " + v.getPlaca()
                    + " | Tipo: " + v.getTipo()
                    + " | Entrada: " + v.getHoraEntrada().toLocalTime();
            System.out.println("- " + texto);
        }
    }

    // Pesquisa veículo por placa e mostra a hora de entrada, se estiver estacionado
    private static void pesquisarVeiculo(Scanner sc, Estacionamento estacionamento) {
        System.out.print("Informe a placa para pesquisa: ");
        String placa = sc.nextLine().trim().toUpperCase();

        Veiculo v = estacionamento.buscarPorPlaca(placa);

        if (v == null) {
            System.out.println("Veículo NÃO encontrado no estacionamento.");
        } else {
            System.out.println("Veículo encontrado:");
            System.out.println("Placa: " + v.getPlaca());
            System.out.println("Tipo: " + v.getTipo());
            System.out.println("Horário de entrada: " + v.getHoraEntrada().toLocalTime());
        }
    }

    // Mostra o total arrecadado até o momento
    private static void mostrarFaturamento(Estacionamento estacionamento) {
        System.out.printf("Total arrecadado até o momento: R$ %.2f%n",
                estacionamento.getTotalArrecadado());
    }
}

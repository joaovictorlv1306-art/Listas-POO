package br.com.javaflix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AplicacaoCompleta {

    // =========================================================================
    // MÓDULO A & B: Usuário e Vídeo (Encapsulamento, Equals, Static, Contadores)
    // =========================================================================

    public static class Usuario {
        // Questão 08: Constante global da plataforma
        public static final String NOME_PLATAFORMA = "JavaFlix";

        // Questão 09: Contador global estático
        private static int totalUsuarios = 0;

        // Questão 01 & 03: Atributos privados
        private String nome;
        private String email;
        private boolean ativo;

        // Questão 09: Construtor que incrementa o contador global
        public Usuario(String nome, String email, boolean ativo) {
            this.nome = nome;
            this.email = email;
            this.ativo = ativo;
            totalUsuarios++;
        }

        // Getters e Setters (Questão 03)
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public boolean isAtivo() { return ativo; }
        public void setAtivo(boolean ativo) { this.ativo = ativo; }

        // Questão 09: Método estático para consulta do contador
        public static int getTotalUsuarios() {
            return totalUsuarios;
        }

        // Questão 06: Sobrescrita do toString()
        @Override
        public String toString() {
            return "Usuário: " + nome + " | Contato: " + email + " | Ativo: " + ativo;
        }

        // Questão 07: Sobrescrita do equals() baseado no e-mail
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Usuario usuario = (Usuario) obj;
            return Objects.equals(email, usuario.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(email);
        }
    }

    public static class Video {
        // Questão 02 & 03: Atributos privados
        private String titulo;
        private int duracaoMinutos;

        // Questão 04: Construtor parametrizado obrigatório
        public Video(String titulo, int duracaoMinutos) {
            this.titulo = titulo;
            setDuracaoMinutos(duracaoMinutos); // Aplica a validação
        }

        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }

        public int getDuracaoMinutos() { return duracaoMinutos; }

        // Questão 03: Regra de validação para duração
        public void setDuracaoMinutos(int duracaoMinutos) {
            if (duracaoMinutos <= 0) {
                this.duracaoMinutos = 1; // Ajusta para exatamente 1 minuto se inválido
            } else {
                this.duracaoMinutos = duracaoMinutos;
            }
        }
    }

    // =========================================================================
    // MÓDULO C: O Gateway de Pagamentos (Interfaces e Polimorfismo)
    // =========================================================================

    // Questão 11: Interface Pagamento
    public interface Pagamento {
        void processar(double valor);
    }

    // Questão 12: Implementação Pix
    public static class PagamentoPix implements Pagamento {
        @Override
        public void processar(double valor) {
            System.out.println("Gerando QR Code Pix no valor de R$ " + valor);
        }
    }

    // Questão 12: Implementação Cartão
    public static class PagamentoCartao implements Pagamento {
        @Override
        public void processar(double valor) {
            System.out.println("Validando limite no cartão para cobrança de R$ " + valor);
        }
    }

    // Questão 13: Inversão de Dependência
    public static class ProcessadorPagamento {
        public void finalizarCompra(double valor, Pagamento formaPagamento) {
            formaPagamento.processar(valor);
        }
    }

    // =========================================================================
    // MÓDULO D: Coleções e Estruturas de Dados (Array, List, Set)
    // =========================================================================

    // Questão 16: Classe Credencial
    public static class Credencial {
        private String codigoHex;

        public Credencial(String codigoHex) {
            this.codigoHex = codigoHex;
        }

        public String getCodigoHex() { return codigoHex; }
        public void setCodigoHex(String codigoHex) { this.codigoHex = codigoHex; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Credencial credencial = (Credencial) obj;
            return Objects.equals(codigoHex, credencial.codigoHex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(codigoHex);
        }
    }

    // Classe Sede (Questões 15, 17, 18, 19)
    public static class Sede {
        // Questão 15: Array com capacidade fixa de 3 posições
        private String[] vagasGaragem = new String[3];

        // Questão 17: Histórico dinâmico (ArrayList)
        private List<Credencial> historicoCatraca = new ArrayList<>();

        // Questão 18: Unicidade com Set (HashSet)
        private Set<Credencial> acessoCofre = new HashSet<>();

        // Questão 19: Métodos de Operação
        public void estacionarVeiculo(String placa, int vaga) {
            vagasGaragem[vaga] = placa;
            System.out.println("Veículo " + placa + " estacionado na vaga " + vaga);
        }

        public void registrarPassagemCatraca(Credencial c) {
            historicoCatraca.add(c);
            System.out.println("Passagem registrada na catraca para a credencial: " + c.getCodigoHex());
        }

        public void autorizarEntradaCofre(Credencial c) {
            boolean inserido = acessoCofre.add(c);
            if (inserido) {
                System.out.println("Acesso ao cofre AUTORIZADO para credencial: " + c.getCodigoHex());
            } else {
                System.out.println("Alerta de Segurança: Tentativa de acesso duplicado com credencial já ativa no cofre (" + c.getCodigoHex() + ")");
            }
        }

        public List<Credencial> getHistoricoCatraca() {
            return historicoCatraca;
        }
    }

    // =========================================================================
    // MÉTODO MAIN: Execução e Validação dos Cenários de Teste das Questões
    // =========================================================================

    public static void main(String[] args) {

        System.out.println("=== TESTE MÓDULO A (Questão 05) ===");
        Usuario user1 = new Usuario("Ana Souza", "ana@email.com", true);
        Video video1 = new Video("Java Avançado", 120);
        Video video2 = new Video("Vídeo Com Erro", -45); // Inválido

        System.out.println(user1);
        System.out.println("Duração ajustada do Vídeo 2: " + video2.getDuracaoMinutos() + " minuto(s)");


        System.out.println("\n=== TESTE MÓDULO B (Questão 10) ===");
        Usuario user2 = new Usuario("Ana Clona", "ana@email.com", false); // Mesmos e-mails

        if (user1.equals(user2)) {
            System.out.println("Validação Equals: Os usuários possuem o mesmo e-mail (equivalentes).");
        } else {
            System.out.println("Validação Equals: Usuários diferentes.");
        }

        // Acesso direto pela classe
        System.out.println("Plataforma: " + Usuario.NOME_PLATAFORMA);
        System.out.println("Total de Usuários Criados: " + Usuario.getTotalUsuarios());


        System.out.println("\n=== TESTE MÓDULO C (Questão 14) ===");
        ProcessadorPagamento processador = new ProcessadorPagamento();
        Pagamento pix = new PagamentoPix();
        Pagamento cartao = new PagamentoCartao();

        processador.finalizarCompra(50.00, pix);
        processador.finalizarCompra(150.00, cartao);


        System.out.println("\n=== TESTE MÓDULO D (Questão 20) ===");
        Sede sede = new Sede();
        Credencial cred1 = new Credencial("CR-1010");

        // 2. Registra 2 vezes na Catraca
        sede.registrarPassagemCatraca(cred1);
        sede.registrarPassagemCatraca(cred1);
        System.out.println("Total de passagens na catraca: " + sede.getHistoricoCatraca().size());

        // 3. Tenta autorizar 2 vezes no Cofre
        sede.autorizarEntradaCofre(cred1);
        sede.autorizarEntradaCofre(cred1); // Deve emitir alerta de duplicidade

        // 4. Preenche as 3 vagas e tenta acessar o índice 3 (4º veículo)
        System.out.println("\nPreenchendo vagas da garagem...");
        sede.estacionarVeiculo("ABC-1111", 0);
        sede.estacionarVeiculo("XYZ-2222", 1);
        sede.estacionarVeiculo("KJM-3333", 2);

        System.out.println("Tentando estacionar o 4º veículo (índice 3 fora dos limites)...");
        // Força a exceção ArrayIndexOutOfBoundsException
        sede.estacionarVeiculo("ERR-9999", 3);
    }
}
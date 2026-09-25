package br.com.techinvoice;

public class Main {

    // =========================================================================
    // EXERCÍCIO 1 & 2: O Padrão Singleton (GerenciadorConfiguracao)
    // =========================================================================
    public static class GerenciadorConfiguracao {
        private String apiKey = "AWS-12345-KEY";

        // 1. Atributo estático e privado para armazenar a instância única
        private static GerenciadorConfiguracao instancia;

        // 2. Construtor privado para impedir o uso de 'new' fora da classe (Exercício 1)
        private GerenciadorConfiguracao() {
        }

        // 3. Método getInstance com Inicialização Preguiçosa / Lazy Initialization (Exercício 2)
        public static GerenciadorConfiguracao getInstance() {
            if (instancia == null) {
                instancia = new GerenciadorConfiguracao();
            }
            return instancia;
        }

        public String getApiKey() {
            return apiKey;
        }
    }

    // =========================================================================
    // EXERCÍCIO 3: O Contrato de Documentos (Interface e Classes Concretas)
    // =========================================================================
    public interface IDocumento {
        void gerarPDF();
    }

    public static class NotaFiscal implements IDocumento {
        @Override
        public void gerarPDF() {
            System.out.println("Gerando Nota Fiscal com impostos...");
        }
    }

    public static class Recibo implements IDocumento {
        @Override
        public void gerarPDF() {
            System.out.println("Gerando Recibo simples de pagamento...");
        }
    }

    // =========================================================================
    // EXERCÍCIO 4: A Fábrica de Documentos (Factory Pattern)
    // =========================================================================
    public static class DocumentoFactory {
        public static IDocumento criarDocumento(String tipo) {
            if (tipo == null) {
                throw new IllegalArgumentException("Tipo de documento inválido: valor nulo.");
            }

            if (tipo.equalsIgnoreCase("NF")) {
                return new NotaFiscal();
            } else if (tipo.equalsIgnoreCase("RECIBO")) {
                return new Recibo();
            } else {
                throw new IllegalArgumentException("Tipo de documento inválido: " + tipo);
            }
        }
    }

    // =========================================================================
    // EXERCÍCIO 5: O Teste de Integração (Método Main)
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("=== TESTE DOS PADRÕES DE PROJETO (TECHINVOICE) ===");

        // 1. Capturar a instância do GerenciadorConfiguracao usando Singleton e imprimir a apiKey
        GerenciadorConfiguracao gen1 = GerenciadorConfiguracao.getInstance();
        System.out.println("API Key recuperada: " + gen1.getApiKey());

        // 2. Capturar uma segunda instância e provar a uniciadade de memória
        GerenciadorConfiguracao gen2 = GerenciadorConfiguracao.getInstance();

        if (gen1 == gen2) {
            System.out.println("Prova Singleton: gen1 e gen2 apontam exatamente para a mesma instância em memória!");
        } else {
            System.out.println("Falha no Singleton: instâncias diferentes foram criadas.");
        }

        System.out.println("\n--- TESTE DO FACTORY PATTERN ---");

        // 3. Criar um documento do tipo "NF" através da fábrica e gerar o PDF
        IDocumento notaFiscal = DocumentoFactory.criarDocumento("NF");
        notaFiscal.gerarPDF();

        // 4. Forçar um tipo inválido "BOLETO" dentro de um bloco try-catch
        try {
            System.out.println("\nTentando criar documento do tipo 'BOLETO'...");
            IDocumento boleto = DocumentoFactory.criarDocumento("BOLETO");
            boleto.gerarPDF();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro capturado com sucesso: " + e.getMessage());
        }
    }
}
package br.com.ecommerce;

public class MainTeste {

    // ==========================================
    // ETAPA 1: Exceção Customizada
    // ==========================================
    public static class TipoFreteInvalidoException extends RuntimeException {
        public TipoFreteInvalidoException(String mensagem) {
            super(mensagem);
        }
    }

    // ==========================================
    // ETAPA 2: Interface e Estratégias (Strategy)
    // ==========================================
    public interface EstrategiaFrete {
        double calcular(double valorPedido);
    }

    public static class FreteSedex implements EstrategiaFrete {
        @Override
        public double calcular(double valorPedido) {
            return valorPedido * 0.10; // 10%
        }
    }

    public static class FretePac implements EstrategiaFrete {
        @Override
        public double calcular(double valorPedido) {
            return valorPedido * 0.05; // 5%
        }
    }

    public static class FreteMotoboy implements EstrategiaFrete {
        @Override
        public double calcular(double valorPedido) {
            return 15.00; // Taxa fixa
        }
    }

    // ==========================================
    // ETAPA 3: Calculadora de Frete Refatorada
    // ==========================================
    public static class CalculadoraFrete {
        public double processarFrete(double valorPedido, EstrategiaFrete estrategia) {
            if (estrategia == null) {
                throw new TipoFreteInvalidoException("Estratégia de frete não pode ser nula ou é inválida.");
            }
            return estrategia.calcular(valorPedido);
        }
    }

    // ==========================================
    // ETAPA 4: Teste de Resiliência (Método Main)
    // ==========================================
    public static void main(String[] args) {
        CalculadoraFrete calculadora = new CalculadoraFrete();
        double valorPedido = 100.00;

        try {
            // 1. Teste SEDEX
            double valorSedex = calculadora.processarFrete(valorPedido, new FreteSedex());
            System.out.printf("Valor do Frete (SEDEX) para R$ %.2f: R$ %.2f%n", valorPedido, valorSedex);

            // 2. Teste PAC
            double valorPac = calculadora.processarFrete(valorPedido, new FretePac());
            System.out.printf("Valor do Frete (PAC) para R$ %.2f: R$ %.2f%n", valorPedido, valorPac);

            // 3. Teste MOTOBOY
            double valorMotoboy = calculadora.processarFrete(valorPedido, new FreteMotoboy());
            System.out.printf("Valor do Frete (MOTOBOY) para R$ %.2f: R$ %.2f%n", valorPedido, valorMotoboy);

            System.out.println("----------------------------------------");
            System.out.println("Forçando erro enviando estratégia nula...");

            // 4. Força erro com parâmetro null
            calculadora.processarFrete(valorPedido, null);

        } catch (TipoFreteInvalidoException e) {
            // 5. Captura exceção e exibe apenas a mensagem
            System.out.println("Erro capturado com sucesso: " + e.getMessage());
        }

        System.out.println("Execução finalizada com segurança.");
    }
}
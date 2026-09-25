package br.com.techcorp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MainTechCorp {

    // ==========================================
    // RN01: O Funcionário e o Contrato do Hash
    // ==========================================
    public static class Funcionario {
        private String matricula;
        private String nome;
        private String cargo;

        public Funcionario(String matricula, String nome, String cargo) {
            this.matricula = matricula;
            this.nome = nome;
            this.cargo = cargo;
        }

        public String getMatricula() {
            return matricula;
        }

        public String getNome() {
            return nome;
        }

        public String getCargo() {
            return cargo;
        }

        // Regra de Identidade: Apenas a matricula é determinante
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Funcionario that = (Funcionario) o;
            return Objects.equals(matricula, that.matricula);
        }

        @Override
        public int hashCode() {
            return Objects.hash(matricula);
        }

        @Override
        public String toString() {
            return "Funcionario{" +
                    "matricula='" + matricula + '\'' +
                    ", nome='" + nome + '\'' +
                    ", cargo='" + cargo + '\'' +
                    '}';
        }
    }

    // ==========================================
    // RN02 & RN03: O Controle de Acesso
    // ==========================================
    public static class ControleDeAcesso {
        // RN02: ArrayList para manter ordem e permitir repetições (Catraca)
        private List<Funcionario> historicoCatraca = new ArrayList<>();

        // RN03: HashSet para garantir unicidade e verificação rápida (Sala Segura)
        private Set<Funcionario> autorizadosSalaSegura = new HashSet<>();

        // RN02: Método da Catraca
        public void registrarPassagem(Funcionario f) {
            historicoCatraca.add(f);
            System.out.println("Catraca: Passagem registrada para [" + f.getNome() + " - " + f.getMatricula() + "]");
        }

        // RN03: Método da Sala Segura
        public void concederAcessoSala(Funcionario f) {
            boolean adicionado = autorizadosSalaSegura.add(f);
            if (adicionado) {
                System.out.println("Acesso liberado para a Sala Segura para [" + f.getNome() + "]");
            } else {
                System.out.println("Aviso: Matrícula já registrada na sala. Acesso não permitido.");
            }
        }

        public List<Funcionario> getHistoricoCatraca() {
            return historicoCatraca;
        }
    }

    // ==========================================
    // Validação do Sistema (Método Main)
    // ==========================================
    public static void main(String[] args) {
        // 1. Instanciar a classe ControleDeAcesso
        ControleDeAcesso controle = new ControleDeAcesso();

        // 2. Instanciar o funcionário f1
        Funcionario f1 = new Funcionario("T-001", "Alice", "Desenvolvedora");

        // 3. Instanciar o funcionário f2 (mesma matrícula, nome/objeto diferente)
        Funcionario f2 = new Funcionario("T-001", "Alice Duplicada", "Desenvolvedora Sênior");

        System.out.println("--- TESTE DA CATRACA (LIST) ---");
        // Teste Catraca: f1 e f2 devem ser aceitos no histórico
        controle.registrarPassagem(f1);
        controle.registrarPassagem(f2);
        System.out.println("Total de registos na catraca: " + controle.getHistoricoCatraca().size());

        System.out.println("\n--- TESTE DA SALA SEGURA (SET) ---");
        // Teste Sala Segura: f1 é liberado, f2 dispara o aviso de bloqueio
        controle.concederAcessoSala(f1);
        controle.concederAcessoSala(f2);
    }
}
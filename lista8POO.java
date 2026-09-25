package br.com.cybercorp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MainTeste {

    // ==========================================
    // RN01: Classe Departamento
    // ==========================================
    public static class Departamento {
        private String sigla;
        private String nomeSetor;
        private int andar;

        public Departamento(String sigla, String nomeSetor, int andar) {
            this.sigla = sigla;
            this.nomeSetor = nomeSetor;
            this.andar = andar;
        }

        public String getSigla() { return sigla; }
        public void setSigla(String sigla) { this.sigla = sigla; }

        public String getNomeSetor() { return nomeSetor; }
        public void setNomeSetor(String nomeSetor) { this.nomeSetor = nomeSetor; }

        public int getAndar() { return andar; }
        public void setAndar(int andar) { this.andar = andar; }

        @Override
        public String toString() {
            return "Departamento{" +
                    "sigla='" + sigla + '\'' +
                    ", nomeSetor='" + nomeSetor + '\'' +
                    ", andar=" + andar +
                    '}';
        }
    }

    // ==========================================
    // RN01: Classe Funcionario
    // ==========================================
    public static class Funcionario {
        private String matricula;
        private String nome;
        private Departamento departamento;

        public Funcionario(String matricula, String nome, Departamento departamento) {
            this.matricula = matricula;
            this.nome = nome;
            this.departamento = departamento;
        }

        public String getMatricula() { return matricula; }
        public void setMatricula(String matricula) { this.matricula = matricula; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public Departamento getDepartamento() { return departamento; }
        public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

        @Override
        public String toString() {
            return "Funcionario{" +
                    "matricula='" + matricula + '\'' +
                    ", nome='" + nome + '\'' +
                    ", departamento=" + departamento +
                    '}';
        }
    }

    // ==========================================
    // RN01: Classe Veiculo
    // ==========================================
    public static class Veiculo {
        private String placa;
        private String modelo;
        private Funcionario dono;

        public Veiculo(String placa, String modelo, Funcionario dono) {
            this.placa = placa;
            this.modelo = modelo;
            this.dono = dono;
        }

        public String getPlaca() { return placa; }
        public void setPlaca(String placa) { this.placa = placa; }

        public String getModelo() { return modelo; }
        public void setModelo(String modelo) { this.modelo = modelo; }

        public Funcionario getDono() { return dono; }
        public void setDono(Funcionario dono) { this.dono = dono; }

        @Override
        public String toString() {
            return "Veiculo{" +
                    "placa='" + placa + '\'' +
                    ", modelo='" + modelo + '\'' +
                    ", dono=" + dono.getNome() +
                    '}';
        }
    }

    // ==========================================
    // RN02: Classe Credencial
    // ==========================================
    public static class Credencial {
        private String codigoHex;
        private boolean ativo;
        private Funcionario titular;

        public Credencial(String codigoHex, boolean ativo, Funcionario titular) {
            this.codigoHex = codigoHex;
            this.ativo = ativo;
            this.titular = titular;
        }

        public String getCodigoHex() { return codigoHex; }
        public void setCodigoHex(String codigoHex) { this.codigoHex = codigoHex; }

        public boolean isAtivo() { return ativo; }
        public void setAtivo(boolean ativo) { this.ativo = ativo; }

        public Funcionario getTitular() { return titular; }
        public void setTitular(Funcionario titular) { this.titular = titular; }

        // Sobrescrita obrigatoria de equals e hashCode baseados unicamente em codigoHex
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Credencial that = (Credencial) o;
            return Objects.equals(codigoHex, that.codigoHex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(codigoHex);
        }

        @Override
        public String toString() {
            return "Credencial{" +
                    "codigoHex='" + codigoHex + '\'' +
                    ", ativo=" + ativo +
                    ", titular=" + (titular != null ? titular.getNome() : "N/A") +
                    '}';
        }
    }

    // ==========================================
    // RN03, RN04, RN05: Classe SistemaSeguranca
    // ==========================================
    public static class SistemaSeguranca {
        private Veiculo[] vagasGaragem;
        private List<Funcionario> catracaPrincipal = new ArrayList<>();
        private Set<Credencial> cofreFisico = new HashSet<>();

        public SistemaSeguranca(int totalVagas) {
            this.vagasGaragem = new Veiculo[totalVagas];
        }

        // RN03: Limitação Física (Array)
        public void estacionarVeiculo(Veiculo v, int vaga) {
            this.vagasGaragem[vaga] = v;
            System.out.println("Garagem: Veículo [" + v.getPlaca() + "] estacionado na vaga [" + vaga + "]");
        }

        // RN04: Linha do Tempo (List)
        public void registrarCatraca(Funcionario f) {
            this.catracaPrincipal.add(f);
            System.out.println("Catraca: Acesso liberado para [" + f.getNome() + "]");
        }

        // RN05: Zero Trust (Set)
        public void acessarCofre(Credencial cred) {
            boolean inserido = this.cofreFisico.add(cred);
            if (inserido) {
                System.out.println("Cofre: Acesso CONCEDIDO. Bem-vindo(a) [" + cred.getTitular().getNome() + "]");
            } else {
                System.out.println("ALERTA MÁXIMO: Credencial [" + cred.getCodigoHex() + "] bloqueada! Tentativa de clonagem detectada.");
            }
        }
    }

    // ==========================================
    // RN06: Campo de Provas (Método Main)
    // ==========================================
    public static void main(String[] args) {
        // 1. Instanciar Departamento, Funcionário e Veículo
        Departamento depto = new Departamento("TI", "Segurança da Informação", 3);
        Funcionario func = new Funcionario("F12345", "Carlos Silva", depto);
        Veiculo veiculo = new Veiculo("ABC-1234", "Civic", func);

        // 2. Instanciar SistemaSeguranca com 2 vagas
        SistemaSeguranca sistema = new SistemaSeguranca(2);

        // 3. Criar credencial original
        Credencial c1 = new Credencial("FFF-999", true, func);

        // 4. Criar o CLONE (outro objeto em memória com o mesmo códigoHex)
        Credencial clone = new Credencial("FFF-999", true, func);

        System.out.println("--- TESTE DA CATRACA ---");
        // 5. Teste Catraca: passar o mesmo funcionário duas vezes
        sistema.registrarCatraca(func);
        sistema.registrarCatraca(func);

        System.out.println("\n--- TESTE DO COFRE ---");
        // 6. Teste Cofre: passar c1 e depois o clone
        sistema.acessarCofre(c1);
        sistema.acessarCofre(clone); // Deve bloquear

        System.out.println("\n--- TESTE DA GARAGEM ---");
        // 7. Teste Garagem: estacionar na vaga 0 e depois forçar vaga 5 para estourar o Array
        sistema.estacionarVeiculo(veiculo, 0);

        System.out.println("\nTentando estacionar na vaga 5 (fora do limite)...");
        // Esta chamada vai lançar a exceção ArrayIndexOutOfBoundsException e interromper a execução do programa
        sistema.estacionarVeiculo(veiculo, 5);
    }
}
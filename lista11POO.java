package br.com.nexustech;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Main {

    // =========================================================================
    // NÍVEL 3: Exceção Customizada Unchecked (Exercício 8)
    // =========================================================================
    public static class NivelInsuficienteException extends RuntimeException {
        public NivelInsuficienteException() {
            super("Seu nível é muito baixo para esta masmorra!");
        }
    }

    // NÍVEL 3: Classe Masmorra (Exercício 9)
    public static class Masmorra {
        public void entrar(int nivelJogador) {
            if (nivelJogador < 50) {
                throw new NivelInsuficienteException();
            }
            System.out.println("Entrada permitida na masmorra! Boa sorte, guerreiro.");
        }
    }

    // =========================================================================
    // NÍVEL BOSS: Arquitetura e Matchmaker (Exercícios 10, 11, 12, 13)
    // =========================================================================
    // Exercício 10: Interface ModoJogo
    public interface ModoJogo {
        void buscarPartida();
    }

    // Exercício 11: Implementação ModoCasual
    public static class ModoCasual implements ModoJogo {
        @Override
        public void buscarPartida() {
            System.out.println("Buscando partida Casual... Divirta-se!");
        }
    }

    // Exercício 11: Implementação ModoRanqueado
    public static class ModoRanqueado implements ModoJogo {
        @Override
        public void buscarPartida() {
            System.out.println("Buscando partida Ranqueada... Valendo pontos de Rank!");
        }
    }

    // Exercício 12: Exceção Customizada Checked
    public static class BanidoException extends Exception {
        public BanidoException() {
            super("Jogador Banido!");
        }
    }

    // Exercício 13: Classe Matchmaker
    public static class Matchmaker {
        public void encontrarSala(ModoJogo modo, boolean jogadorBanido) throws BanidoException {
            if (jogadorBanido) {
                throw new BanidoException();
            }
            modo.buscarPartida();
        }
    }

    // =========================================================================
    // NÍVEL 2: Método com Checked Exception (Exercício 5)
    // =========================================================================
    public static void conectarServidor() throws Exception {
        throw new Exception("Servidor caiu!");
    }

    // =========================================================================
    // MÉTODO MAIN: Execução de Todos os Exercícios
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("=== NÍVEL 1: UNCHECKED EXCEPTIONS ===");

        // Exercício 1 & 2: O Bug do K/D (ArithmeticException / Divisão por zero)
        int kills = 15;
        int deaths = 0;
        try {
            int kd = kills / deaths;
            System.out.println(kd);
        } catch (ArithmeticException e) {
            System.out.println("Taxa K/D: Jogador Invicto!");
        }

        // Exercício 3: O Inventário Bugado (ArrayIndexOutOfBoundsException)
        String[] inventario = new String[3];
        try {
            inventario[5] = "Espada";
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Inventário cheio!");
        }

        // Exercício 4: O Fantasma (NullPointerException via Programação Defensiva)
        String jogador = null;
        if (jogador != null) {
            System.out.println("Nome do jogador: " + jogador);
        } else {
            System.out.println("Jogador desconectado");
        }


        System.out.println("\n=== NÍVEL 2: CHECKED EXCEPTIONS & FINALLY ===");

        // Exercício 6 & 7: Assinatura de Contrato e Block Finally
        try {
            conectarServidor();
        } catch (Exception e) {
            System.out.println("Erro capturado: " + e.getMessage());
        } finally {
            System.out.println("Fechando portas de rede do jogo...");
        }


        System.out.println("\n=== NÍVEL 3: REGRAS DE NEGÓCIO ===");

        // Exercício 9: Teste da Masmorra
        Masmorra masmorra = new Masmorra();
        try {
            System.out.println("Tentando entrar com jogador nível 20...");
            masmorra.entrar(20);
        } catch (NivelInsuficienteException e) {
            System.out.println("Acesso Negado: " + e.getMessage());
        }


        System.out.println("\n=== NÍVEL BOSS: MATCHMAKER E POLIMORFISMO ===");

        // Exercício 14: Batalha Final
        Matchmaker matchmaker = new Matchmaker();
        ModoJogo casual = new ModoCasual();
        ModoJogo ranqueado = new ModoRanqueado();

        try {
            System.out.println("Tentando buscar sala para jogador BANIDO...");
            matchmaker.encontrarSala(casual, true);
        } catch (BanidoException e) {
            System.out.println("Matchmaker Bloqueado: " + e.getMessage());
        }

        try {
            System.out.println("Tentando buscar sala para jogador NÃO banido...");
            matchmaker.encontrarSala(ranqueado, false);
        } catch (BanidoException e) {
            System.out.println("Matchmaker Bloqueado: " + e.getMessage());
        }


        System.out.println("\n=== DESAFIO EXTRA: O COFRE DO GABARITO (FORÇA BRUTA) ===");
        executarAtaqueForcaBruta();
    }

    // =========================================================================
    // DESAFIO EXTRA: Algoritmo de Criptografia Descriptografado
    // =========================================================================
    public static void executarAtaqueForcaBruta() {
        String encryptedB64 = "U2FsdGVkX1/Jz86x4/Ydu3FZFw5pSo86xHG1MwpCFX/Dnn9uCMDd3xLNn61XZouvQy6G2FIhyQXAQwvTWn3/01JGIoIh5RN4NXgs+kdpcf6afHmSMvCZu0EiiiXlVpB2EQGIKDLIAU9c1aQx6bzEgQ==";
        encryptedB64 = encryptedB64.replaceAll("\\s", "");

        byte[] rawData = Base64.getDecoder().decode(encryptedB64);

        // Separa o Salt (bytes 8 a 16) e o Texto Cifrado (bytes 16 em diante)
        byte[] salt = Arrays.copyOfRange(rawData, 8, 16);
        byte[] cipherText = Arrays.copyOfRange(rawData, 16, rawData.length);

        String charset = "abcdefghijklmnopqrstuvwxyz0123456789";
        System.out.println("Iniciando ataque de força bruta no link da NexusTech...");
        long startTime = System.currentTimeMillis();

        for (char c1 : charset.toCharArray()) {
            for (char c2 : charset.toCharArray()) {
                String testPass = "jav" + c1 + c2;
                try {
                    // Derivação de chave usando PBKDF2WithHmacSHA1 (Padrão OpenSSL)
                    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                    KeySpec spec = new PBEKeySpec(testPass.toCharArray(), salt, 1000, 384); // 32 bytes (Key) + 16 bytes (IV) = 48 bytes (384 bits)
                    byte[] keyAndIv = factory.generateSecret(spec).getEncoded();

                    byte[] key = Arrays.copyOfRange(keyAndIv, 0, 32);
                    byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);

                    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                    IvParameterSpec ivSpec = new IvParameterSpec(iv);

                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    byte[] decryptedBytes = cipher.doFinal(cipherText);
                    String result = new String(decryptedBytes, StandardCharsets.UTF_8);

                    if (result.contains("http")) {
                        long endTime = System.currentTimeMillis();
                        System.out.println("\n SUCESSO! A criptografia foi quebrada!");
                        System.out.println("Senha encontrada: " + testPass);
                        System.out.println("Link revelado: " + result.trim());
                        System.out.println("Tempo de execução: " + (endTime - startTime) + "ms");
                        return;
                    }
                } catch (Exception e) {
                    // Senha incorreta aciona exceção de Padding ou Chave -> ignora e continua o loop
                }
            }
        }
        System.out.println("\nForça bruta concluída. Senha não encontrada.");
    }
}
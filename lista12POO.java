package br.com.technexus;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    // =========================================================================
    // NÍVEL 1: Exercício 1 - Classe Produto
    // =========================================================================
    public static class Produto {
        private String nome;
        private String categoria;
        private double preco;

        public Produto(String nome, String categoria, double preco) {
            this.nome = nome;
            this.categoria = categoria;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public String getCategoria() {
            return categoria;
        }

        public double getPreco() {
            return preco;
        }

        @Override
        public String toString() {
            return "Produto{nome='" + nome + "', categoria='" + categoria + "', preco=" + preco + "}";
        }
    }

    // =========================================================================
    // NÍVEL 1, 2 & 3: Exercícios 2 a 5 - Classe Loja com Streams API
    // =========================================================================
    public static class Loja {
        private List<Produto> catalogo = new ArrayList<>();

        public void cadastrar(Produto p) {
            this.catalogo.add(p);
        }

        // Exercício 3: Relatório por Categoria
        public List<Produto> buscarPorCategoria(String catDesejada) {
            return this.catalogo.stream()
                    .filter(p -> p.getCategoria().equalsIgnoreCase(catDesejada))
                    .toList();
        }

        // Exercício 4: Fechamento de Caixa Total
        public double calcularPatrimonioTotal() {
            return this.catalogo.stream()
                    .mapToDouble(Produto::getPreco)
                    .sum();
        }

        // Exercício 5: Filtro Financeiro Avançado (Pipeline)
        public double calcularTotalPorCategoria(String catDesejada) {
            return this.catalogo.stream()
                    .filter(p -> p.getCategoria().equalsIgnoreCase(catDesejada))
                    .mapToDouble(Produto::getPreco)
                    .sum();
        }
    }

    // =========================================================================
    // CAMPO DE PROVAS (Exercício 6) & EXECUÇÃO DO DESAFIO
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("=== EXERCÍCIO 6: TESTANDO CÓDIGO FLUENTE ===");
        Loja loja = new Loja();

        // Cadastrando produtos conforme enunciado
        loja.cadastrar(new Produto("The Witcher", "GAMES", 150.00));
        loja.cadastrar(new Produto("FIFA", "GAMES", 200.00));
        loja.cadastrar(new Produto("Java for Dummies", "LIVROS", 100.00));
        loja.cadastrar(new Produto("Clean Code", "LIVROS", 80.00));
        loja.cadastrar(new Produto("Mouse", "HARDWARE", 50.00));

        // 1. Filtrar GAMES
        System.out.println("\n--- Produtos da categoria GAMES ---");
        List<Produto> games = loja.buscarPorCategoria("GAMES");
        games.forEach(System.out.println);

        // 2. Património Total
        System.out.println("\n--- Património Total em Estoque ---");
        System.out.printf("R$ %.2f%n", loja.calcularPatrimonioTotal());

        // 3. Total da categoria LIVROS
        System.out.println("\n--- Património Total da categoria LIVROS ---");
        System.out.printf("R$ %.2f%n", loja.calcularTotalPorCategoria("LIVROS"));

        // =========================================================================
        // DESAFIO DE ENGENHARIA / SEGURANÇA (Força Bruta Hacker)
        // =========================================================================
        System.out.println("\n=== DESAFIO EXTRA: FORÇA BRUTA NO COFRE ===");
        executarAtaqueForcaBruta();
    }

    // =========================================================================
    // LÓGICA DE DESCRIPTOGRAFIA POR FORÇA BRUTA (AES-256-CBC PBKDF2)
    // =========================================================================
    public static void executarAtaqueForcaBruta() {
        String encryptedB64 = "U2FsdGVkX189CvKETNa+k2wHIMpbCwNk7HKB3nBRz0D9bBaPt2nFMCdElvKoRfTmmqVv41Txh370RXFWRVNOX3vpgPHULkkaoyh9DfmzzGBXkGnu/SJfQkGuU08zbgMQNSwCGTwoIHkUMzRFQELNOQ==";
        encryptedB64 = encryptedB64.replaceAll("\\s", "");

        byte[] rawData = Base64.getDecoder().decode(encryptedB64);

        // Extrai Salt (bytes 8 a 16) e Cifrado (bytes 16 em diante)
        byte[] salt = Arrays.copyOfRange(rawData, 8, 16);
        byte[] cipherText = Arrays.copyOfRange(rawData, 16, rawData.length);

        String charset = "abcdefghijklmnopqrstuvwxyz0123456789";
        System.out.println("Iniciando ataque de força bruta para padrao 'lamXXX'...");
        long startTime = System.currentTimeMillis();

        // Testa combinações de 3 caracteres (lam000 até lamzzz)
        for (char c1 : charset.toCharArray()) {
            for (char c2 : charset.toCharArray()) {
                for (char c3 : charset.toCharArray()) {
                    String testPass = "lam" + c1 + c2 + c3;
                    try {
                        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                        KeySpec spec = new PBEKeySpec(testPass.toCharArray(), salt, 1000, 384); // 32 bytes (Chave) + 16 bytes (IV)
                        byte[] keyAndIv = factory.generateSecret(spec).getEncoded();

                        byte[] key = Arrays.copyOfRange(keyAndIv, 0, 32);
                        byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);

                        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                        IvParameterSpec ivSpec = new IvParameterSpec(iv);

                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                        byte[] decryptedBytes = cipher.doFinal(cipherText);
                        String result = new String(decryptedBytes, StandardCharsets.UTF_8);

                        if (result.contains("http") || result.contains("https")) {
                            long endTime = System.currentTimeMillis();
                            System.out.println("\n SUCESSO! A criptografia foi quebrada!");
                            System.out.println("Senha encontrada: " + testPass);
                            System.out.println("URL secreta revelada: " + result.trim());
                            System.out.println("Tempo de execução: " + (endTime - startTime) + " ms");
                            return;
                        }
                    } catch (Exception e) {
                        // Ignora senhas incorretas que lançam BadPaddingException
                    }
                }
            }
        }
        System.out.println("\nForça bruta concluída. Senha não encontrada.");
    }
}
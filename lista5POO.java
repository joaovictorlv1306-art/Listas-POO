import java.util.Base64;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// Interface dos decodificadores
interface Decodificador {
    String decodificar(String texto);
}

// Decodificador Base64
class DecodificadorBase64 implements Decodificador {

    @Override
    public String decodificar(String texto) {
        byte[] dados = Base64.getDecoder().decode(texto);
        return new String(dados);
    }
}

// Decodificador César
class DecodificadorCesar implements Decodificador {

    private int chave;

    public DecodificadorCesar(int chave) {
        this.chave = chave;
    }

    @Override
    public String decodificar(String texto) {

        String resultado = "";

        for (int i = 0; i < texto.length(); i++) {

            char caractere = texto.charAt(i);

            if (caractere >= 'A' && caractere <= 'Z') {

                char novo = (char) (caractere - chave);

                if (novo < 'A') {
                    novo += 26;
                }

                resultado += novo;

            } else {
                resultado += caractere;
            }
        }

        return resultado;
    }
}

// Decodificador Reverso
class DecodificadorReverso implements Decodificador {

    @Override
    public String decodificar(String texto) {
        return new StringBuilder(texto).reverse().toString();
    }
}

// Interface do filtro de imagem
interface FiltroImagem {
    void aplicarFiltro(String caminhoEntrada, String caminhoSaida);
}

// Filtro Vermelho Mágico
class FiltroVermelhoMagico implements FiltroImagem {

    @Override
    public void aplicarFiltro(String caminhoEntrada, String caminhoSaida) {

        try {

            BufferedImage imagem =
                    ImageIO.read(new File(caminhoEntrada));

            for (int y = 0; y < imagem.getHeight(); y++) {

                for (int x = 0; x < imagem.getWidth(); x++) {

                    int pixel = imagem.getRGB(x, y);

                    int vermelho = (pixel >> 16) & 0xFF;

                    if (vermelho % 2 == 0) {
                        imagem.setRGB(x, y, 0xFFFFFFFF);
                    } else {
                        imagem.setRGB(x, y, 0xFF000000);
                    }
                }
            }

            ImageIO.write(
                    imagem,
                    "png",
                    new File(caminhoSaida)
            );

            System.out.println("Imagem processada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao processar a imagem.");
            e.printStackTrace();
        }
    }
}

// Classe principal
public class Main {

    public static void main(String[] args) {

        // Aplicando o filtro na imagem
        FiltroImagem filtro = new FiltroVermelhoMagico();

        filtro.aplicarFiltro(
                "pista_01.jpg",
                "pista_processada.png"
        );

        // Exemplos dos decodificadores

        Decodificador base64 = new DecodificadorBase64();

        Decodificador reverso = new DecodificadorReverso();

        Decodificador cesar = new DecodificadorCesar(3);

        // Testes
        System.out.println("Base64:");
        System.out.println(
                base64.decodificar("SGVsbG8=")
        );

        System.out.println("\nReverso:");
        System.out.println(
                reverso.decodificar("OLLEH")
        );

        System.out.println("\nCésar:");
        System.out.println(
                cesar.decodificar("KROD")
        );
    }
}
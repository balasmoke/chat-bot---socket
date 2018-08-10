
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Maury
 */
public class Servidor extends Thread {

    List<String> bomDia = new ArrayList<>();
    List<String> fome = new ArrayList<>();
    List<String> ajuda = new ArrayList<>();
    List<String> estouBem = new ArrayList<>();
    List<String> pensar = new ArrayList<>();

    List<String> perguntas = new ArrayList<>();

    public void addList() {
        perguntas.add(" Bom dia\n");
        perguntas.add(" Esta com fome?\n");
        perguntas.add(" Você pode me ajudar?\n");
        perguntas.add(" Eu estou bem?\n");
        perguntas.add(" Você pensar?\n");

        bomDia.add("[Good] Bot: Bom Demais! Oque tu mandas?\n");
        bomDia.add("[Good] Bot: Um Grande dia meu amigo\n");
        bomDia.add("[Bad] Bot: Oque tem de bom nele ?\n");
        bomDia.add("[Bad] Bot: Bom uma ova \n");

        pensar.add("[Good] Bot: Infelizmente não meu amigo, porém você pode me ajudar com isso correto?!?\n");
        pensar.add("[Good] Bot: Agora que perguntaste eu naõ s.@ oq×= ° ×ππ÷? -ERRO-\n");
        pensar.add("[Bad] Bot: Isso não te importa!\n");
        pensar.add("[Bad] Bot: E OBVIO NE ATE PORQUE VOCE ME PROGRAMOU PARA ISSO GÊNIO\n");

        estouBem.add("[Good] Bot: Esta Otimo!\n");
        estouBem.add("[Good] Bot: Você esta  muito estiloso\n");
        estouBem.add("[Bad] Bot: Tá Horroroso\n");
        estouBem.add("[Bad] Bot: cada Vez pior em ...\n");

        ajuda.add("[Good] Bot: claro! oque você precisa?!\n");
        ajuda.add("[Good] Bot: Estou a sua disposição!\n");
        ajuda.add("[Bad] Bot: NÃO!\n");
        ajuda.add("[Bad] Bot: Não sou sua empregada!\n");

        fome.add("[Good] Bot: Demais! aquele Lanchinho agora seria otimo\n");
        fome.add("[Good] Bot: Não no momento, comi mais cedo :)\n");
        fome.add("[Bad] Bot: claro que não eu sou um bot\n");
        fome.add("[Bad] Bot: claro, é porcos tambem estão voando\n");

    }

    StringBuffer stb = null;

    Socket cliente = null;
    DataInputStream entrada = null;
    DataOutputStream saida = null;

    public Servidor(Socket cliente, StringBuffer stb) {
        this.stb = stb;
        this.cliente = cliente;

        addList();
        try {
            entrada = new DataInputStream(cliente.getInputStream());
            saida = new DataOutputStream(cliente.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {

        while (true) {

            try {
                    String aux1 = entrada.readUTF();

                String vet[] = aux1.split(":");
                String aux = vet[1];

                if (aux.equals(perguntas.get(0))) {
                    aux1 = bomDia.get(new Random().nextInt(4));

                } else if (aux.equals(perguntas.get(1))) {
                    aux1 = fome.get(new Random().nextInt(4));
                } else if (aux.equals(perguntas.get(2))) {
                    aux1 = ajuda.get(new Random().nextInt(4));
                } else if (aux.equals(perguntas.get(3))) {
                    aux1 = estouBem.get(new Random().nextInt(4));
                } else if (aux.equals(perguntas.get(4))) {
                    aux1 = pensar.get(new Random().nextInt(4));
                }
                acao(aux1);

                saida.flush();
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public synchronized void acao(String a) {
        try {

            stb.append(a);
            saida.writeUTF(stb.toString());

            saida.flush();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket server = new ServerSocket(3312);

                    while (true) {
                        new Servidor(server.accept(), new StringBuffer()).start();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }).start();

    }
}

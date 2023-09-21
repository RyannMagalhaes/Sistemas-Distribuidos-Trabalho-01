import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        int nsu = 0;
        Thread.currentThread().setName("Transacao");

        ServerSocket serversocket = new ServerSocket(2001);
        Socket socket = serversocket.accept();

        Cartao cartao1 = new Cartao("012345678910","000000100050");
        Cartao cartao2 = new Cartao("012345678911","00000010050");
        Cartao cartao3 = new Cartao("012345678912","0000100050");

        Map<String, Cartao> listaCartao = new HashMap<>();
        listaCartao.put(cartao1.getNumero(), cartao1);
        listaCartao.put(cartao2.getNumero(),cartao2);
        listaCartao.put(cartao3.getNumero(),cartao3);

        while(true) {
            System.out.println("Aguardando Conexão");
            System.out.println("Conexão Concluida");
            try(DataInputStream entrada = new DataInputStream(socket.getInputStream())) {
                DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
                String mensagem = entrada.readUTF();
                Transacao transacao = parseTransacao(mensagem);

                Cartao cartaoCliente = listaCartao.get(transacao.getNsu());
                Double valorTransacao = Double.parseDouble(transacao.getValor());


                //Valida Cartão
                if (cartaoCliente==null){
                    saida.writeUTF("05 - Transação recusada, cartão não encontrado.");
                } else if (Double.parseDouble(cartaoCliente.getSaldo())<valorTransacao) {
                    saida.writeUTF("51 - Saldo Insuficiente.");
                }else{
                    nsu++;
                    cartaoCliente.diminuirSaldo(valorTransacao);
                    listaCartao.put(cartaoCliente.getNumero(), cartaoCliente);
                    saida.writeUTF("00 - Transacao Aprovada");
                }


                saida.writeUTF(mensagem);
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static Transacao parseTransacao(String mensagem){
        String tipo = mensagem.substring(0,3);
        String valor = mensagem.substring(4,15);
        String hora = mensagem.substring(16,21);
        String data = mensagem.substring(22,25);
        String rede = mensagem.substring(26,31);
        String numero = mensagem.substring(32,43);
        String formaPagamento = mensagem.substring(44,44);

        Transacao t = new Transacao(tipo, valor, hora, data, rede, numero, formaPagamento);
        return t;
    }
}
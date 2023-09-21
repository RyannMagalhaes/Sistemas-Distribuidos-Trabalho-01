public class Cartao {
    private String numero;
    private String numeroCliente;
    private String saldo;

    //Construtor

    public Cartao(String numero, String saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }

    //Métodos
    public synchronized void diminuirSaldo(Double value) {
        saldo = String.valueOf(Double.parseDouble(saldo)-value);
    }

    //Getters e Setters

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}

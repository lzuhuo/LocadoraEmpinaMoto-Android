package com.example.locadoraempinamoto.model;

public class Message {
    public boolean status;
    public String message;
    public int codigo;

    public Message(boolean status, String message, int codigo){
        this.status = status;
        this.message = message;
        this.codigo = codigo;
    }

    public String toString(){
        return String.format("{'status': %b,'message': %s,'codigo': %d",this.status, this.message, this.codigo);
    }
}

package com.example.locadoraempinamoto.model.Categoria;

public class Motor {
    public int CD_MOTOR;
    public String DS_MOTOR;
    public String ST_ATIVO;

    public Motor(int CD_MOTOR, String DS_MOTOR){
        this.CD_MOTOR = CD_MOTOR;
        this.ST_ATIVO = "S";
        this.DS_MOTOR = DS_MOTOR;
    }

    public String toString(){
        return String.format("%s", this.DS_MOTOR);
    }
}

package com.example.locadoraempinamoto.model.Moto;

public class Opcional {
    public int CD_OPCIONAL;
    public String DS_OPCIONAL;
    public float VL_OPCIONAL;

    public Opcional(int CD_OPCIONAL, String DS_OPCIONAL, float VL_OPCIONAL){
        this.CD_OPCIONAL = CD_OPCIONAL;
        this.DS_OPCIONAL = DS_OPCIONAL;
        this.VL_OPCIONAL = VL_OPCIONAL;
    }

    public String toString(){
        return String.format("%s", this.DS_OPCIONAL);
    }
}

package com.example.locadoraempinamoto.services;

import java.util.ArrayList;

import com.example.locadoraempinamoto.business.Categoria.MotorDAO;
import com.example.locadoraempinamoto.model.Categoria.Motor;

public class MotorService {
    public ArrayList<Motor> listarMotor(){
        ArrayList<Motor> motores;
        MotorDAO motorDAO = new MotorDAO();
        motores = motorDAO.listarMotor();
        return motores;
    }
}

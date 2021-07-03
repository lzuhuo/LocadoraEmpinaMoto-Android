package com.example.locadoraempinamoto.services;

import android.content.Context;

import java.util.ArrayList;

import com.example.locadoraempinamoto.business.ClienteDAO;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Cliente.Cliente;

public class ClienteService {
    private Context context;
    public ClienteService(Context data){
        this.context = data;
    }

    public Cliente getCliente(int CD_CLIENTE){
        Cliente cliente = new Cliente();
        ClienteDAO clienteDAO = new ClienteDAO(context);
        try {
            cliente = clienteDAO.getCliente(CD_CLIENTE);
        } catch (Exception e) {
            cliente = null;
        }
        return cliente;        
    }

    public ArrayList<Cliente> getClientes(String NR_CNH){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        ClienteDAO clienteDAO = new ClienteDAO(context);
        try {
            clientes = clienteDAO.getClientes(NR_CNH);
        } catch (Exception e) {
            System.out.println(e);
        }
        return clientes;        
    }

    public ArrayList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        ClienteDAO clienteDAO = new ClienteDAO(context);
        try {
            clientes = clienteDAO.listarClientes();
        } catch (Exception e) {
            clientes = null;
        }
        return clientes;        
    }

    public Message inserirCliente(Cliente c){
        ClienteDAO clienteDAO = new ClienteDAO(context);
        Message  messageCliente;
        try {
            messageCliente = clienteDAO.adicionaCliente(c);
        } catch (Exception e) {
            messageCliente =  new Message(false, String.format("Service Error: %s",e), -1);
        }
        return messageCliente;
    }

    public Message atualizarCliente(Cliente c){
        ClienteDAO clienteDAO = new ClienteDAO(context);
        Message messageCliente;
        try {
            messageCliente = clienteDAO.atualizaCliente(c);
        } catch (Exception e) {
            messageCliente =  new Message(false, String.format("Service Error: %s",e), -1);
        }
        return messageCliente;
    }

    public Boolean removeCliente(int CD_CLIENTE){
        ClienteDAO clienteDAO = new ClienteDAO(context);
        Boolean resultado = false;
        try {
            resultado = clienteDAO.removeCliente(CD_CLIENTE);
            if(!resultado){
                Exception removM = new Exception("Erro ao remover cliente");
                removM.notify();
            } 
        } catch (Exception e) {
            System.out.println("errorservice: "+e);
            resultado = false;
        }
        return resultado;
    }
}

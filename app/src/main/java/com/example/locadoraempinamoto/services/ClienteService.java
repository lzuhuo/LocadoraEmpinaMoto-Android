package com.example.locadoraempinamoto.services;

import java.util.ArrayList;

import com.example.locadoraempinamoto.business.ClienteDAO;
import com.example.locadoraempinamoto.model.Message;
import com.example.locadoraempinamoto.model.Cliente.Cliente;

public class ClienteService {
    public Cliente getCliente(int CD_CLIENTE){
        Cliente cliente = new Cliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            cliente = clienteDAO.getCliente(CD_CLIENTE);
        } catch (Exception e) {
            cliente = null;
        }
        return cliente;        
    }

    public ArrayList<Cliente> getClientes(String NR_CNH){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            clientes = clienteDAO.getClientes(NR_CNH);
        } catch (Exception e) {
            System.out.println(e);
        }
        return clientes;        
    }

    public ArrayList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            clientes = clienteDAO.listarClientes();
        } catch (Exception e) {
            clientes = null;
        }
        return clientes;        
    }

    public Message inserirCliente(Cliente c){
        ClienteDAO clienteDAO = new ClienteDAO();
        Message  messageCliente;
        try {
            messageCliente = clienteDAO.adicionaCliente(c);
        } catch (Exception e) {
            messageCliente =  new Message(false, String.format("Service Error: %s",e), -1);
        }
        return messageCliente;
    }

    public Message atualizarCliente(Cliente c){
        ClienteDAO clienteDAO = new ClienteDAO();
        Message messageCliente;
        try {
            messageCliente = clienteDAO.atualizaCliente(c);
        } catch (Exception e) {
            messageCliente =  new Message(false, String.format("Service Error: %s",e), -1);
        }
        return messageCliente;
    }

    public Boolean removeCliente(int CD_CLIENTE){
        ClienteDAO clienteDAO = new ClienteDAO();
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

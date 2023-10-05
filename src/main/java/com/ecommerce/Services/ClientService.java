package com.ecommerce.Services;

import com.ecommerce.Entities.Client;
import com.ecommerce.Entities.Permission;
import com.ecommerce.Repository.ClientRepo;
import com.ecommerce.Repository.PermissionRepo;
import com.ecommerce.dto.ClientDTO;

import ch.qos.logback.core.status.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepo clientRepo;

    @Autowired
    PermissionRepo permissionRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;


    public Client createUser(ClientDTO client) throws Exception{
        Permission p = permissionRepo.findByName("USER");
        if(clientRepo.findByUsername(client.username()) != null) {
        	 throw new RuntimeException(String.format("Username %s already used",client.username()));
        }else {
        	Client c = new Client();
	        c.setName(client.name());
            c.setAddress(client.address());
	        c.setEmail(client.email());
	        c.setPassword(passwordEncoder.encode(client.password()));
	        c.setTel(client.tel());
	        c.setUsername(client.username());
	        c.getRoles().add(p);
	        return clientRepo.save(c);
        }
        
    }

    public void updateUser(Long id,ClientDTO client){
        Client c = clientRepo.findById(id).orElseThrow(()-> new RuntimeException(String.format("Client with id %s not found ", id)));
        if(clientRepo.findByUsername(client.username())!=null && !c.getUsername().equals(client.username())){
            throw new RuntimeException(String.format("Client with username %s exist already ",client.username()));
        }else{
            if(client.password().equals("")){
                c.setTel(client.tel());
                c.setEmail(client.email());
                c.setAddress(client.address());
                c.setUsername(client.username());
                c.setName(client.name());
            }else{
                c.setTel(client.tel());
                c.setEmail(client.email());
                c.setAddress(client.address());
                c.setUsername(client.username());
                c.setPassword(passwordEncoder.encode(client.password()));
                c.setName(client.name());
            }

            clientRepo.save(c);
        }

    }

    public void ChangePassword(Long id,String old_password, String password){
        Client c = clientRepo.findById(id).orElseThrow(()-> new RuntimeException(String.format("Client with id %s not found ", id)));
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(c.getUsername(),old_password);
        Authentication auth = authenticationManager.authenticate(user);
        if(auth.isAuthenticated()){
            c.setPassword(passwordEncoder.encode(password));
            clientRepo.save(c);
        }else{
            throw new RuntimeException(String.format("Password %s not correct",old_password));
        }
    }

    public void deleteUser(Long id){
        Client c = clientRepo.findById(id).orElseThrow(()-> new RuntimeException(String.format("Client with id %s not found ", id)));
        c.setRoles(null);
        c.setCommands(null);
        clientRepo.deleteById(id);
    }

    public List<Client> Clients(){
        return clientRepo.findAll();
    }
    
    public Client username(String username) {
    	return clientRepo.findByUsername(username);
    }
    
    public Client client(Long id) {
    	return clientRepo.findById(id).get();
    }

    public Client affectRole(Long id) {
        Client c = clientRepo.findById(id).
                orElseThrow(()-> new RuntimeException(String.format("Client with id %s not found ", id)));
        List<Permission> per = c.getRoles();
        for(Permission p : per){
            if(p.getName().contains("ADMIN")) return c;
        }
        Permission p = permissionRepo.findByName("ADMIN");
        c.getRoles().add(p);
        return clientRepo.save(c);

    }

    public List<Client> byUsername(String username){
        List<Client> clients = clientRepo.findAll();
        List<Client> result = new ArrayList<>();

        for(Client client : clients){
            if(client.getUsername().toLowerCase().contains(username.toLowerCase())){
                result.add(client);
            }
        }
        return result;
    }
}

package cafe.service;

import java.util.ArrayList;
import java.util.List;

import cafe.modul.Admin;
import cafe.modul.User;

public class AuthService {
    private List<User> users;
    private Admin admin;
    
    public AuthService() {
        this.users = new ArrayList<>();
    }
    
    public void registerAdmin(Admin admin) {
        this.admin = admin;
    }
    
    public boolean registerUser(User newUser) {
        if (findUser(newUser.getUsername()) != null) {
            return false;
        }
        users.add(newUser);
        return true;
    }
    
    public User login(String username, String password) {
        if (admin != null && admin.getUsername().equals(username) && 
            admin.getPassword().equals(password)) {
            return admin;
        }
        
        User user = findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    private User findUser(String username) {
        return users.stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }
}
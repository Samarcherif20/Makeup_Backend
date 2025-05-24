package com.samar.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samar.users.entities.Role;
import com.samar.users.entities.User;
import com.samar.users.repos.RoleRepository;
import com.samar.users.repos.UserRepository;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public Role addRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public User addRoleToUser(String username, String rolename) {
	    User user = userRepo.findByUsername(username);
	    Role role = roleRepo.findByRole(rolename);

	    if (user == null || role == null) {
	        System.out.println("❌ [DEBUG] User or Role not found.");
	        return null;
	    }

	    System.out.println("🔄 [DEBUG] Adding role: " + rolename + " to user: " + username);
	    user.getRoles().add(role);
	    User savedUser = userRepo.save(user); // Save it!
	    System.out.println("✅ [DEBUG] Role added and user saved!");

	    return savedUser;
	}


	@Override
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

}

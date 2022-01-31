package com.mercadolivre.projetointegrador.user.service;

import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.user.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Qualifier("UserRepository")
	@Autowired
	UserRepository userRepository;

	public User createUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User findUser(Long id) {
		return userRepository.getUserById(id);
	}

}

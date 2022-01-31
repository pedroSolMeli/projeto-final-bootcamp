package com.mercadolivre.projetointegrador.user.service;

import com.mercadolivre.projetointegrador.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Qualifier("UserRepository")
    @Autowired
    UserRepository userRepository;

}

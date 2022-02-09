package com.mercadolivre.projetointegrador.user.repository;

import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Long id);
    Optional<User> findUserByUsername(String username);
//    Warehouse findWarehouseByUser(Long id);

}

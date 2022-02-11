package com.mercadolivre.projetointegrador.user.repository;

import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Long id);
    void deleteById(Long id);
    Optional<User> findUserByCpf(String cpf);
    List<User> getUsersByRoles(UserRole role);
    Optional<User> findUserByUsername(String username);

}

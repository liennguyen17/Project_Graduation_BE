package com.ltw.repository.users;

import com.ltw.model.News;
import com.ltw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsAllByUsername(String username);
    boolean existsAllByUsernameAndIdNot(String username, Integer userId);

    Optional<User> findByUsername(String username);

    User getUserByUsername(String username);

    User findByEmail(String email);
    boolean existsAllByEmail(String email);
    User findByRole(String role);
    boolean existsAllByUserCode(String userCode);

    boolean existsByUsername(String userName);
    boolean existsAllByUserCodeAndIdNot(String userCode,Integer userId);
    List<User> findAllByRole(String role);
}

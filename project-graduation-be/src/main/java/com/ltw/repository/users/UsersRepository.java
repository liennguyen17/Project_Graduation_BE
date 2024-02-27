package com.ltw.repository.users;

import com.ltw.model.News;
import com.ltw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsAllByUsername(String username);
    boolean existsAllByUsernameAndIdNot(String username, Integer userId);
}

package com.LibManagementSystem.LibManagementSystem.repo;

import com.LibManagementSystem.LibManagementSystem.models.Token;
import com.LibManagementSystem.LibManagementSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {
   //HQL
    @Query(
            """
                    select t from Token t inner join User u on
                    t.user.userId = u.userId where u.userId = :userId and
                    (t.expired=false or t.revoked= false)
                    """
    )
    List<Token> findAllValidTokensByUser(UUID userId);


    Optional<Token> findByToken(String token);

}

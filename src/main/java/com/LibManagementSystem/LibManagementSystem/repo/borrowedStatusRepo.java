package com.LibManagementSystem.LibManagementSystem.repo;

import com.LibManagementSystem.LibManagementSystem.models.borrowedStatusRelated.borrowedStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface borrowedStatusRepo extends JpaRepository<borrowedStatus, Integer> {


    @Query(
            """
                    select e from borrowedStatus e where e.userIssuedId = :userId and
                    e.bookIssuedId = :bookId
                    """
    )
    borrowedStatus findByBookIdAndUserId(Integer bookId, Integer userId);
}

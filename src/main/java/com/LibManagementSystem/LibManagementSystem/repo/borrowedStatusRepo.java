package com.LibManagementSystem.LibManagementSystem.repo;

import com.LibManagementSystem.LibManagementSystem.models.borrowedStatusRelated.borrowedStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface borrowedStatusRepo extends JpaRepository<borrowedStatus, Integer> {
    borrowedStatus findByBookIdAndUserId();
}

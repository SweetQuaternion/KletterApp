package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Entities.UserRoutenStatusId;

public interface UserRoutenStatusRepository extends JpaRepository<UserRoutenStatus, UserRoutenStatusId> {
    
    UserRoutenStatus findByUserIdAndRoutenId(int userId, int routenId);
    List<UserRoutenStatus> findByUserId(int userId);
    List<UserRoutenStatus> findByRoutenId(int routenId);
    void deleteById(UserRoutenStatusId id);
}

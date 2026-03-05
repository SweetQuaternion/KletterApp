package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Entities.UserRoutenStatusId;

public interface UserRoutenStatusRepository extends JpaRepository<UserRoutenStatus, UserRoutenStatusId> {
    
    UserRoutenStatus findByIdUserIdAndIdRouteId(int userId, int routeId);
    List<UserRoutenStatus> findByIdUserId(int userId);
    List<UserRoutenStatus> findByIdRouteId(int routeId);
    void deleteById(UserRoutenStatusId id);
}

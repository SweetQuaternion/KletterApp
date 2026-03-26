package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Entities.UserRoutenStatusId;

public interface UserRoutenStatusRepository extends JpaRepository<UserRoutenStatus, UserRoutenStatusId> {
    
    UserRoutenStatus findByIdUserIdAndIdRouteId(String userId, int routeId);
    List<UserRoutenStatus> findByIdUserId(String userId);
    List<UserRoutenStatus> findByIdRouteId(int routeId);
    void deleteByIdUserId(String userId);
    void deleteByIdRouteId(int routeId);
}

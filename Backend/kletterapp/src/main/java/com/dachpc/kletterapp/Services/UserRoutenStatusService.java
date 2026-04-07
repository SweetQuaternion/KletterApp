package com.dachpc.kletterapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Repositories.UserRoutenStatusRepository;

@Service
public class UserRoutenStatusService {
    
    @Autowired
    private UserRoutenStatusRepository userRoutenStatusRepository;

    public UserRoutenStatus getStatus(String userId, int routenId) {
        UserRoutenStatus status = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(userId, routenId);
        if (status == null) {
            return new UserRoutenStatus(userId, routenId);
        }
        return status;
    }

    public UserRoutenStatus addStatus(UserRoutenStatus userRoutenStatus) {
        return userRoutenStatusRepository.save(userRoutenStatus);
    }

    public UserRoutenStatus updateStatus(UserRoutenStatus userRoutenStatus) {
        return userRoutenStatusRepository.save(userRoutenStatus);
    }

    public void deleteStatus(String userId, int routenId) {
        if (userId == null || routenId == 0) {
            throw new IllegalArgumentException("UserId or RoutenId must be provided");
        }
        UserRoutenStatus existingStatus = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(userId, routenId);
        if (existingStatus != null) {
            userRoutenStatusRepository.delete(existingStatus);
        }
    }

}

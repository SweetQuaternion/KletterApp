package com.dachpc.kletterapp;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.springframework.beans.factory.annotation.Autowired;

// import com.dachpc.kletterapp.Entities.UserRoutenStatus;
// import com.dachpc.kletterapp.Repositories.UserRoutenStatusRepository;

public class UserRoutenStatusRepositoryTest extends AbstractIntegrationTest {
    
    // @Autowired
    // private UserRoutenStatusRepository userRoutenStatusRepository;

    // @BeforeEach
    // public void setUp() {
    //     userRoutenStatusRepository.deleteAll();
    //     userRoutenStatusRepository.save(new UserRoutenStatus(1, 1, true, false, 5.3f, "Tolle Route!"));
    //     userRoutenStatusRepository.save(new UserRoutenStatus(1, 2, false, true, 6.3f, "Schwierige Route!"));
    //     userRoutenStatusRepository.save(new UserRoutenStatus(2, 1, true, true, 7.0f, "Sehr schwierige Route!"));
    // }

    // @Test
    // public void testFindByUserIdAndRoutenId() {
    //     UserRoutenStatus result = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(1, 1);
    //     assertThat(result).isNotNull();
    //     assertThat(result.isFavorit()).isTrue();
    //     assertThat(result.isProjekt()).isFalse();
    //     assertThat(result.getGeschSchwierigkeit()).isEqualTo(5.3f);
    //     assertThat(result.getNotiz()).isEqualTo("Tolle Route!");
    // }

    // @Test
    // public void testFindByUserId() {
    //     List<UserRoutenStatus> result = userRoutenStatusRepository.findByIdUserId(1);
    //     assertThat(result).hasSize(2);
    // }

    // @Test
    // public void testFindByRoutenId() {
    //     List<UserRoutenStatus> result = userRoutenStatusRepository.findByIdRouteId(1);
    //     assertThat(result).hasSize(2);
    // }

    // @Test
    // public void testChangeFavoritStatus() {
    //     UserRoutenStatus status = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(1, 1);
    //     status.setFavorit(false);
    //     userRoutenStatusRepository.save(status);

    //     UserRoutenStatus updatedStatus = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(1, 1);
    //     assertThat(updatedStatus.isFavorit()).isFalse();
    // }

}

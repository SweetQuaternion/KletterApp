package com.dachpc.kletterapp;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.time.LocalDateTime;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;

// import com.dachpc.kletterapp.Entities.Kommentar;
// import com.dachpc.kletterapp.Entities.Route;
// import com.dachpc.kletterapp.Entities.User;
// import com.dachpc.kletterapp.Entities.Wand;
// import com.dachpc.kletterapp.Repositories.KommentarRepository;
// import com.dachpc.kletterapp.Repositories.RoutenRepository;
// import com.dachpc.kletterapp.Repositories.UserRepository;
// import com.dachpc.kletterapp.Repositories.WandRepository;

public class KommentarTest extends AbstractIntegrationTest {
    
    // @Autowired
    // private KommentarRepository kommentarRepository;

    // @Autowired
    // private RoutenRepository routenRepository;

    // @Autowired
    // private UserRepository userRepository;

    // @Autowired
    // private WandRepository wandRepository;

    // private int userId1;
    // private int userId2;
    // private int wandId;
    // private int routenId1;
    // private int routenId2;

    // @BeforeEach
    // public void setUp() {
    //     kommentarRepository.deleteAll();
    //     routenRepository.deleteAll();
    //     userRepository.deleteAll();

    //     // User user1 = userRepository.save(new User("premiumuser", "premiumuser@example.com", "geheim"));
    //     // User user2 = userRepository.save(new User("mausi", "mausi@example.com", "geheim123"));
    //     // userId1 = user1.getId();
    //     // userId2 = user2.getId();    

    //     Wand wand = wandRepository.save(new Wand(1,1,"Sektor in erster Halle"));
    //     wandId = wand.getId();

    //     Route route = routenRepository.save(new Route(wandId, "Route 1", "rot", 5.3f));
    //     Route route2 = routenRepository.save(new Route(wandId, "Route 2", "blau", 6.3f));
    //     routenId1 = route.getId();
    //     routenId2 = route2.getId();

    //     // kommentarRepository.save(new Kommentar(routenId1, userId1, LocalDateTime.now(), "Tolle Route!"));
    //     // kommentarRepository.save(new Kommentar(routenId1, userId2, LocalDateTime.now(), "War ganz ok."));
    //     // kommentarRepository.save(new Kommentar(routenId2, userId1, LocalDateTime.now(), "Für diesen Kommentar werde ich gebannt"));
    //     // kommentarRepository.save(new Kommentar(routenId2, userId2, LocalDateTime.now(), "Hallo Welt!"));
    // }

    // @Test
    // public void testFindByRoutenId() {
    //     List<Kommentar> result1 = kommentarRepository.findByRoutenId(routenId1);
    //     assertThat(result1).hasSize(2);
    //     List<Kommentar> result2 = kommentarRepository.findByRoutenId(routenId2);
    //     assertThat(result2).hasSize(2);
    // }

    // @Test
    // public void testFindByUserId() {
    //     List<Kommentar> result1 = kommentarRepository.findByUserId(userId1);
    //     assertThat(result1).hasSize(2);
    //     List<Kommentar> result2 = kommentarRepository.findByUserId(userId2);
    //     assertThat(result2).hasSize(2);
    // }

    // @Test
    // public void testSaveKommentar() {
    //     Kommentar saved = kommentarRepository.save(new Kommentar(routenId1, userId1, LocalDateTime.now(), "Test"));
    //     assertThat(saved.getId()).isPositive();
    //     assertThat(saved.getUserId()).isEqualTo(userId1);
    //     assertThat(saved.getRoutenId()).isEqualTo(routenId1);
    //     assertThat(saved.getText()).isEqualTo("Test");
    // }

    // @Test
    // public void testDeleteKommentar() {
    //     Kommentar saved = kommentarRepository.save(new Kommentar(routenId1, userId1, LocalDateTime.now(), "Test"));
    //     kommentarRepository.deleteById(saved.getId());
    //     assertThat(kommentarRepository.findById(saved.getId())).isEmpty();
    // }
}

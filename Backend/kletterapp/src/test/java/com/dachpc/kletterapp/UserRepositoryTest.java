package com.dachpc.kletterapp;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;

// import com.dachpc.kletterapp.Entities.User;
// import com.dachpc.kletterapp.Repositories.UserRepository;


public class UserRepositoryTest extends AbstractIntegrationTest {

    // @Test
    // void testContainer() {
    //     assertThat(postgresqlContainer.isRunning()).isTrue();
    // }

    // @Autowired
    // private UserRepository userRepository;

    // @BeforeEach
    // void setUp() {
        // userRepository.deleteAll();
        // userRepository.save( new User( "Max Mustermann", "max.mustermann@example.com", "password123" ));
        // userRepository.save( new User( "Erika Musterfrau", "erika.musterfrau@example.com", "password456" ));
    // }

    // @Test
    // void testFindByEmail() {
    //     Optional<User> result1 = userRepository.findByEmail("max.mustermann@example.com");
    //     assertThat(result1).isPresent();
    //     assertThat(result1.get().getName()).isEqualTo("Max Mustermann");
    // }

    // @Test
    // void testFindByEmailNotFound() {
    //     Optional<User> result = userRepository.findByEmail("bla@bla.com");
    //     assertThat(result).isNotPresent();
    // }

    // @Test
    // void testSaveUser() {
    //     User user = new User("Mona", "mona@example.com", "hashedpassword");
    //     User saved = userRepository.save(user);
    //     assertThat(saved.getId()).isPositive();
    //     assertThat(saved.getName()).isEqualTo("Mona");
    //     assertThat(saved.getRole()).isEqualTo("user");
    // }

    // @Test
    // void testFindById() {
    //     User saved = userRepository.save(new User("Mona", "mona@example.com", "hashedpassword"));
    //     Optional<User> result = userRepository.findById(saved.getId());
    //     assertThat(result).isPresent();
    //     assertThat(result.get().getEmail()).isEqualTo("mona@example.com");
    // }

    // @Test
    // void testDeleteById() {
    //     User saved = userRepository.save(new User("Mona", "mona@example.com", "hashedpassword"));
    //     userRepository.deleteById(saved.getId());
    //     assertThat(userRepository.findById(saved.getId())).isEmpty();
    // }

    // @Test
    // void testDuplicateEmail() {
    //     assertThatThrownBy(() -> userRepository.save(new User("Max-neu", "max.mustermann@example.com", "hash1")))
    //             .isInstanceOf(Exception.class);
    // }
}
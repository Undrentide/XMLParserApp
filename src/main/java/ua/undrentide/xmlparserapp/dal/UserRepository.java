package ua.undrentide.xmlparserapp.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.undrentide.xmlparserapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
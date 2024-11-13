package ua.undrentide.xmlparserapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.undrentide.xmlparserapp.dal.UserRepository;
import ua.undrentide.xmlparserapp.entity.User;
import ua.undrentide.xmlparserapp.util.XmlDomParserCore;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final XmlDomParserCore xmlDomParserCore;

    public void addUser() {
        User user = xmlDomParserCore.xmlDeserialize();
        userRepository.save(user);
    }

    public void exportUser(Long id) {
        xmlDomParserCore.xmlSerialize(userRepository.findById(id).orElseThrow());
    }

    public List<User> fetchUserList() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
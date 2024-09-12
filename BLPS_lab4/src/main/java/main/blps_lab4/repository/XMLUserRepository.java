package main.blps_lab4.repository;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.exception.UserNotFoundException;
import main.blps_lab4.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Repository
@Slf4j
public class XMLUserRepository {
    private final XStream xstream;
    private final Path xmlPath;

    public XMLUserRepository(@Value("${lab4.dir}") String rawPath) {
        xmlPath = Paths.get(rawPath).resolve("users.xml");
        xstream = new XStream();
        xstream.allowTypes(new Class[] {UserEntity.class});
        xstream.alias("user", UserEntity.class);
    }

    public List<UserEntity> getAll() {
        return (List<UserEntity>) xstream.fromXML(xmlPath.toFile());
    }

    public void save(UserEntity newUser){
        List<UserEntity> users = getAll();
        users.add(newUser);
        saveAll(users);
    }

    public void saveAll(List<UserEntity> users){
        try {
            Files.writeString(xmlPath, xstream.toXML(users));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public Optional<UserEntity> findById(Long userId) {
        return getAll().stream()
                .filter(user -> userId.equals(user.getId()))
                .findFirst();
    }

    public Optional<UserEntity> findByEmail(String email) {
        return getAll().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst();
    }

    public void banUser(Long userId) throws UserNotFoundException {
        setUserBan(userId, true);
    }

    public void unbanUser(Long userId) throws UserNotFoundException {
        setUserBan(userId, false);
    }

    private void setUserBan(Long userId, Boolean banned) throws UserNotFoundException {
        List<UserEntity> users = getAll();

        UserEntity user = users.stream()
                .filter(elem -> userId.equals(elem.getId()))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException(String.format("id = %d", userId)));

        users.remove(user);
        user.setBanned(banned);
        users.add(user);

        saveAll(users);
    }
}

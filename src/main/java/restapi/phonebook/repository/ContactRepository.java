package restapi.phonebook.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.phonebook.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}

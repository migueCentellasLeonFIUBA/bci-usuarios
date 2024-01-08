package jv.com.bci.repository;

import jv.com.bci.entity.Phone;
import jv.com.bci.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
}

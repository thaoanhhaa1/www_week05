package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
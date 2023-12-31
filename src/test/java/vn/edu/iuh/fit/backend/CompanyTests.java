package vn.edu.iuh.fit.backend;

import com.neovisionaries.i18n.CountryCode;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.edu.iuh.fit.backend.entities.Address;
import vn.edu.iuh.fit.backend.entities.Company;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.backend.services.CompanyServices;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
class CompanyTests {
    private final CompanyServices companyServices;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    public CompanyTests(CompanyServices companyServices) {
        this.companyServices = companyServices;
    }

    @PostConstruct
    void save() {
        Random rnd =new Random();
        Company company;
        for (int i = 1; i <= 1000; ++i) {
            Address add = new Address("HCM", CountryCode.VN,
                    rnd.nextInt(70000,80000)+"","Quang Trung", rnd.nextInt(1,1000)+"" );
            addressRepository.save(add);

            company = new Company("Name #" + i, "About #" + i, add, "Phone  #" + i, "URL #" + i, "Email #" + 1);

            companyServices.save(company);
        }
    }

    @Test
    void findSuccessById() {
        Optional<Company> companyOptional = companyServices.findById(50);

        Assertions.assertTrue(companyOptional.isPresent());
    }

    @Test
    void findFailById() {
        Optional<Company> companyOptional = companyServices.findById(9999);

        Assertions.assertTrue(companyOptional.isEmpty());
    }

    @Test
    void findAll() {
        List<Company> companies = companyServices.findAll();

        Assertions.assertTrue(companies.size() > 50);
    }

    @Test
    void update() {
        Optional<Company> companyOptional = companyServices.findById(1);

        if (companyOptional.isEmpty())
            Assertions.fail();

        Company company = companyOptional.get();
        String newName = "This is new name";

        company.setName(newName);

        Optional<Boolean> updated = companyServices.update(company);

        if (updated.isEmpty())
            Assertions.fail();

        Optional<Company> companyUpdatedOptional = companyServices.findById(1);

        Assertions.assertTrue(companyUpdatedOptional.isPresent() && companyUpdatedOptional.get().getName().equals(newName));
    }

    @Test
    void deleteSuccess() {
        Optional<Boolean> optional = companyServices.delete(101);

        Assertions.assertTrue(optional.isPresent() && optional.get());
    }

    @Test
    void deleteFail() {
        Optional<Boolean> optional = companyServices.delete(999);

        Assertions.assertTrue(optional.isEmpty() || !optional.get());
    }
}
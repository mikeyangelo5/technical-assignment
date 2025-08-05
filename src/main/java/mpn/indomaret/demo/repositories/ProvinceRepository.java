package mpn.indomaret.demo.repositories;

import mpn.indomaret.demo.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    List<Province> findAll();
    Optional<Province> findByName(String name);
    Optional<Province> findById(int id);
    Province save(Province province);
    void deleteById(int id);

}

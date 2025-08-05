package mpn.indomaret.demo.repositories;

import mpn.indomaret.demo.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {

    List<Store> findAll();
    List<Store> findByBranchId(int branch);
    List<Store> findByBranchIdIn(List<Integer> branches);
    Optional<Store> findById(int id);
    Optional<Store> findByName(String name);
    Store save(Store store);
    void deleteById(int id);

}

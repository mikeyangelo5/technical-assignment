package mpn.indomaret.demo.repositories;

import mpn.indomaret.demo.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    List<Branch> findAll();
    List<Branch> findAllByProvinceId(int provinceId);
    Optional<Branch> findById(int branchId);
    Optional<Branch> findByName(String name);
    Branch save(Branch branch);
    void deleteById(int id);

}

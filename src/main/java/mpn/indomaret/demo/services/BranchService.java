package mpn.indomaret.demo.services;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Branch;
import mpn.indomaret.demo.models.Province;
import mpn.indomaret.demo.repositories.BranchRepository;
import mpn.indomaret.demo.repositories.ProvinceRepository;
import mpn.indomaret.demo.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {

    public final BranchRepository branchRepository;
    public final ProvinceRepository provinceRepository;
    private static final Logger logger = LogManager.getLogger(BranchService.class);

    public ResponseEntity<ResponseMessage> getBranches() {
        List<Branch> branches = branchRepository.findAll();
        logger.info("SUCCESS - fetching all branches");

        return new ResponseEntity<>(new ResponseMessage(200, "OK", branches), HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> getBranch(int id) {
        Optional<Branch> branchExists = branchRepository.findById(id);

        if (branchExists.isPresent()) {
            logger.info("SUCCESS - fetching branch with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", branchExists.get()), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND -  fetching branch with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch ID not found!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> getProvinceBranches(String province) {
        Optional<Province> provinceExists = provinceRepository.findByName(province);

        if (provinceExists.isPresent()) {
            List<Branch> branches = branchRepository.findAllByProvinceId(provinceExists.get().getId());
            logger.info("SUCCESS - fetching branches with province name: {}", province);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", branches), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND -  fetching branches with a non existing province name: {}", province);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Province name not found!"), HttpStatus.NOT_FOUND);        }
    }

    public ResponseEntity<ResponseMessage> addBranch(Branch branch) {
        Optional<Branch> branchExists = branchRepository.findByName(branch.getName());

        if (branchExists.isPresent()) {
            logger.warn("CONFLICT - branch {} already exists", branch.getName());

            return new ResponseEntity<>(new ResponseMessage(409, "CONFLICT", "Branch already exists!"), HttpStatus.CONFLICT);
        } else {
            if (provinceRepository.findById(branch.getProvinceId()).isEmpty()) {
                logger.warn("NOT FOUND - attempting to create a branch with non existing province id: {}", branch.getProvinceId());

                return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Province ID not found!"), HttpStatus.CONFLICT);
            }

            branchRepository.save(branch);
            logger.info("SUCCESS - branch {} created", branch.getName());

            return new ResponseEntity<>(new ResponseMessage(200, "OK", branch), HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseMessage> updateBranch(int id, Branch branch) {
        Optional<Branch> branchExists = branchRepository.findById(id);

        if (branchExists.isPresent()) {
            if (provinceRepository.findById(branch.getProvinceId()).isEmpty()) {
                logger.warn("NOT FOUND - attempting to update a branch with non existing province id {}", branch.getProvinceId());

                return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Province ID not found!"), HttpStatus.NOT_FOUND);
            }

            branchExists.get().setName(branch.getName());
            branchExists.get().setProvinceId(branch.getProvinceId());

            branchRepository.save(branchExists.get());
            logger.info("SUCCESS - branch {} updated", branch.getName());

            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Branch updated!"), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to update a non existing branch with id {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch ID not found!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> deleteBranch(int id) {
        Optional<Branch> branchExists = branchRepository.findById(id);

        if (branchExists.isPresent()) {
            branchRepository.deleteById(id);
            logger.info("SUCCESS - branch {} deleted", branchExists.get().getName());

            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Branch deleted!"), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting deletion of non existing branch with id {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch ID not found"), HttpStatus.NOT_FOUND);
        }
    }

}

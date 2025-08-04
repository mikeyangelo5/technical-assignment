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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {

    public final BranchRepository branchRepository;
    public final ProvinceRepository provinceRepository;

    public ResponseEntity<ResponseMessage> getBranches() {
        List<Branch> branches = branchRepository.findAll();
        return new ResponseEntity<>(new ResponseMessage(200, "OK", branches), HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> getBranch(int id) {
        Optional<Branch> branchExists = branchRepository.findById(id);

        if (branchExists.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(200, "OK", branchExists.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch ID not found!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> getProvinceBranches(String province) {
        Optional<Province> provinceExists = provinceRepository.findByName(province);

        if (provinceExists.isPresent()) {
            List<Branch> branches = branchRepository.findAllByProvinceId(provinceExists.get().getId());
            return new ResponseEntity<>(new ResponseMessage(200, "OK", branches), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Province name not found!"), HttpStatus.NOT_FOUND);        }
    }

    public ResponseEntity<ResponseMessage> addBranch(Branch branch) {
        Optional<Branch> branchExists = branchRepository.findByName(branch.getName());

        if (branchExists.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(409, "CONFLICT", "Branch already exists!"), HttpStatus.CONFLICT);
        } else {
            if (provinceRepository.findById(branch.getProvinceId()).isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Province ID not found!"), HttpStatus.CONFLICT);
            }

            branchRepository.save(branch);
            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Branch added!"), HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseMessage> updateBranch(int id, Branch branch) {
        Optional<Branch> branchExists = branchRepository.findById(id);

        if (branchExists.isPresent()) {
            if (provinceRepository.findById(branch.getProvinceId()).isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Province ID not found!"), HttpStatus.NOT_FOUND);
            }

            branchExists.get().setName(branch.getName());
            branchExists.get().setProvinceId(branch.getProvinceId());
            branchRepository.save(branchExists.get());

            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Branch updated!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch ID not found!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> deleteBranch(int id) {
        Optional<Branch> branchExists = branchRepository.findById(id);

        if (branchExists.isPresent()) {
            branchRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Branch deleted!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch ID not found"), HttpStatus.NOT_FOUND);
        }
    }

}

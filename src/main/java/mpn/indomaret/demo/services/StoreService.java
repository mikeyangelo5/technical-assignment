package mpn.indomaret.demo.services;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Branch;
import mpn.indomaret.demo.models.Province;
import mpn.indomaret.demo.models.Store;
import mpn.indomaret.demo.repositories.BranchRepository;
import mpn.indomaret.demo.repositories.ProvinceRepository;
import mpn.indomaret.demo.repositories.StoreRepository;
import mpn.indomaret.demo.response.ResponseMessage;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final ProvinceRepository provinceRepository;

    public ResponseEntity<ResponseMessage> getStores() {
        List<Store> stores = storeRepository.findAll();
        return new ResponseEntity<>(new ResponseMessage(200, "OK", stores), HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> getStore(int id) {
        Optional<Store> store = storeRepository.findById(id);

        if (store.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(200, "OK", store.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> getStoreByBranch(String branchName) {
        Optional<Branch> branchExist = branchRepository.findByName(branchName);

        if (branchExist.isPresent()) {
            List<Store> stores = storeRepository.findByBranchId(branchExist.get().getId());
            return new ResponseEntity<>(new ResponseMessage(200, "OK", stores), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> getStoreByProvince(String provinceName) {
        Optional<Province> provinceExist = provinceRepository.findByName(provinceName);

        if (provinceExist.isPresent()) {
            List<Branch> provinceBranches = branchRepository.findAllByProvinceId(provinceExist.get().getId());
            List<Integer> branchIds = provinceBranches.stream().map(Branch::getId).toList();
            List<Store> provinceStores = storeRepository.findByBranchIdIn(branchIds);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", provinceStores), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> editStore(int id, Store store) {
        Optional<Store> storeOptional = storeRepository.findById(id);

        if (storeOptional.isPresent()) {
            storeOptional.get().setName(store.getName());
            storeOptional.get().setAddress(store.getAddress());
            storeOptional.get().setBranchId(store.getBranchId());
            storeRepository.save(storeOptional.get());

            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Store updated!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> addStore(Store store) {
        Optional<Store> storeExist = storeRepository.findByName(store.getName());

        if (storeExist.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(409, "CONFLICT", "Store already exists!"), HttpStatus.NOT_FOUND);
        } else {
            storeRepository.save(store);
            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Store added!"), HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseMessage> deleteStore(int id) {
        Optional<Store> storeOptional = storeRepository.findById(id);

        if (storeOptional.isPresent()) {
            storeRepository.deleteById(storeOptional.get().getId());
            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Store deleted!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

}

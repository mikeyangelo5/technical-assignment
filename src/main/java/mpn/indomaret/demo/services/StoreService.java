package mpn.indomaret.demo.services;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Branch;
import mpn.indomaret.demo.models.Province;
import mpn.indomaret.demo.models.Store;
import mpn.indomaret.demo.repositories.BranchRepository;
import mpn.indomaret.demo.repositories.ProvinceRepository;
import mpn.indomaret.demo.repositories.StoreRepository;
import mpn.indomaret.demo.response.ResponseMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final ProvinceRepository provinceRepository;
    private static final Logger logger = LogManager.getLogger(StoreService.class);

    public ResponseEntity<ResponseMessage> getStores() {
        List<Store> stores = storeRepository.findAllByOrderByIdAsc();
        logger.info("SUCCESS - fetching all stores");

        return new ResponseEntity<>(new ResponseMessage(200, "OK", stores), HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> getStore(int id) {
        Optional<Store> store = storeRepository.findById(id);

        if (store.isPresent()) {
            logger.info("SUCCESS - fetching store with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", store.get()), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to fetch store with non existing id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Store ID not found!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> getStoreByBranch(String branchName) {
        Optional<Branch> branchExist = branchRepository.findByName(branchName);

        if (branchExist.isPresent()) {
            List<Store> stores = storeRepository.findByBranchId(branchExist.get().getId());
            logger.info("SUCCESS - fetching store with name: {}", branchName);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", stores), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to fetch stores of branch with name: {}", branchName);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch name not found"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> getStoreByProvince(String provinceName) {
        Optional<Province> provinceExist = provinceRepository.findByName(provinceName);

        if (provinceExist.isPresent()) {
            List<Branch> provinceBranches = branchRepository.findAllByProvinceId(provinceExist.get().getId());
            List<Integer> branchIds = provinceBranches.stream().map(Branch::getId).toList();
            List<Store> provinceStores = storeRepository.findByBranchIdIn(branchIds);
            logger.info("SUCCESS - fetching store with province: {}", provinceName);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", provinceStores), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to fetch stores of province: {}", provinceName);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Province name not found!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> editStore(int id, Store store) {
        Optional<Store> storeExists = storeRepository.findById(id);

        if (storeExists.isPresent()) {
            if (branchRepository.findById(store.getBranchId()).isEmpty()) {
                logger.warn("NOT FOUND - attempting to update store with non existing branch id: {}", store.getBranchId());

                return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch ID not found!"), HttpStatus.NOT_FOUND);
            }

            storeExists.get().setName(store.getName());
            storeExists.get().setAddress(store.getAddress());
            storeExists.get().setBranchId(store.getBranchId());
            storeRepository.save(storeExists.get());
            logger.info("SUCCESS - update store with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Store updated!"), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to update store with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Store ID not found!"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> addStore(Store store) {
        Optional<Store> storeExist = storeRepository.findByName(store.getName());

        if (storeExist.isPresent()) {
            logger.warn("CONFLICT - attempting to add existing store with name: {}", store.getName());

            return new ResponseEntity<>(new ResponseMessage(409, "CONFLICT", "Store already exists!"), HttpStatus.NOT_FOUND);
        } else {
            if (branchRepository.findById(store.getBranchId()).isEmpty()) {
                logger.warn("NOT FOUND - attempting to add store with non existing branch id: {}", store.getBranchId());

                return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Branch ID not found!"), HttpStatus.NOT_FOUND);
            }

            storeRepository.save(store);
            logger.info("SUCCESS - adding store with name: {}", store.getName());

            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Store added!"), HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseMessage> deleteStore(int id) {
        Optional<Store> storeOptional = storeRepository.findById(id);

        if (storeOptional.isPresent()) {
            storeRepository.deleteById(storeOptional.get().getId());
            logger.info("SUCCESS - deleting store with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Store deleted!"), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to delete store with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", "Store ID not found"), HttpStatus.NOT_FOUND);
        }
    }

}

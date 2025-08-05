package mpn.indomaret.demo.controllers;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Store;
import mpn.indomaret.demo.response.ResponseMessage;
import mpn.indomaret.demo.services.StoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
@CrossOrigin
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private static final Logger logger = LogManager.getLogger(StoreService.class);

    @GetMapping("")
    public ResponseEntity<ResponseMessage> allStores() {
        try {
            return storeService.getStores();
        } catch (Exception e) {
            logger.error("ERROR GETTING ALL STORES - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseMessage> storeById(@PathVariable("id") int id) {
        try {
            return storeService.getStore(id);
        } catch (RuntimeException e) {
            logger.error("ERROR GETTING STORE DETAIL - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/branch")
    public ResponseEntity<ResponseMessage> branch(@RequestParam(value = "branch") String branchName) {
        try {
            return storeService.getStoreByBranch(branchName);
        } catch (RuntimeException e) {
            logger.error("ERROR GETTING STORES OF A BRANCH - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/province")
    public ResponseEntity<ResponseMessage> province(@RequestParam(value = "province") String provinceName) {
        try {
            return storeService.getStoreByProvince(provinceName);
        } catch (RuntimeException e) {
            logger.error("ERROR GETTING STORES OF A PROVINCE - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateStore(@RequestParam(value = "id") int id, @RequestBody Store storeInfo) {
        try {
            return storeService.editStore(id, storeInfo);
        } catch (RuntimeException e) {
            logger.error("ERROR UPDATING STORE - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createStore(@RequestBody Store storeInfo) {
        try {
            return storeService.addStore(storeInfo);
        } catch (RuntimeException e) {
            logger.error("ERROR CREATING NEW STORE - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", storeInfo.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteStore(@RequestParam(value = "id") int id) {
        try {
            return storeService.deleteStore(id);
        } catch (RuntimeException e) {
            logger.error("ERROR DELETING EXISTING STORE - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

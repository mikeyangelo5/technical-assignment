package mpn.indomaret.demo.controllers;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Province;
import mpn.indomaret.demo.repositories.ProvinceRepository;
import mpn.indomaret.demo.response.ResponseMessage;
import mpn.indomaret.demo.services.ProvinceService;
import mpn.indomaret.demo.services.StoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/province")
@CrossOrigin
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;
    private static final Logger logger = LogManager.getLogger(StoreService.class);

    @GetMapping("")
    public ResponseEntity<ResponseMessage> getAllProvinces() {
        try {
            return provinceService.allProvinces();
        } catch (RuntimeException e) {
            logger.error("ERROR GETTING ALL PROVINCES - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseMessage> getProvinceById(@PathVariable("id") int id) {
        try {
            return provinceService.provinceById(id);
        } catch (RuntimeException e) {
            logger.error("ERROR GETTING PROVINCE DETAIL - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateProvinceById(@RequestParam(value = "id") int id, @RequestBody Province province) {
        try {
            return provinceService.updateProvince(id, province);
        } catch (RuntimeException e) {
            logger.error("ERROR UPDATING PROVINCE - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createProvince(@RequestBody Province province) {
        try {
            return provinceService.addProvince(province);
        } catch (RuntimeException e) {
            logger.error("ERROR CREATING NEW PROVINCE - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteProvinceById(@RequestParam(value = "id") int id) {
        try {
            return provinceService.deleteProvince(id);
        } catch (RuntimeException e) {
            logger.error("ERROR DELETING EXISTING PROVINCE - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

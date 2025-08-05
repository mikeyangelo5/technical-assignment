package mpn.indomaret.demo.services;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Province;
import mpn.indomaret.demo.repositories.ProvinceRepository;
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
public class ProvinceService {

    private final ProvinceRepository provinceRepository;
    private static final Logger logger = LogManager.getLogger(StoreService.class);

    public ResponseEntity<ResponseMessage> allProvinces() {
        List<Province> provinces = provinceRepository.findAll();
        logger.info("SUCCESS - fetching all provinces");

        return new ResponseEntity<>(new ResponseMessage(200, "OK", provinces), HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> provinceById(int id) {
        Optional<Province> province = provinceRepository.findById(id);

        if (province.isPresent()) {
            logger.info("SUCCESS - fetching province with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", province.get()), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to fetch a province with non existing id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> updateProvince(int id, Province province) {
        Optional<Province> provinceExist = provinceRepository.findById(id);

        if (provinceExist.isPresent()) {
            provinceExist.get().setName(province.getName());
            provinceRepository.save(provinceExist.get());
            logger.info("SUCCESS - updating province with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", provinceExist.get()), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to update a province with non existing id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> addProvince(Province province) {
        Optional<Province> provinceExist = provinceRepository.findByName(province.getName());

        if (provinceExist.isPresent()) {
            logger.warn("CONFLICT - province {} already exists", province.getName());

            return new ResponseEntity<>(new ResponseMessage(409, "CONFLICT", "Province already exists!"), HttpStatus.NOT_FOUND);
        } else {
            provinceRepository.save(province);
            logger.info("SUCCESS - creating province with name: {}", province.getName());

            return new ResponseEntity<>(new ResponseMessage(200, "OK", province), HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseMessage> deleteProvince(int id) {
        Optional<Province> provinceExist = provinceRepository.findById(id);

        if (provinceExist.isPresent()) {
            provinceRepository.deleteById(provinceExist.get().getId());
            logger.info("SUCCESS - deleting province with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Province deleted!"), HttpStatus.OK);
        } else {
            logger.warn("NOT FOUND - attempting to delete a province with id: {}", id);

            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

}

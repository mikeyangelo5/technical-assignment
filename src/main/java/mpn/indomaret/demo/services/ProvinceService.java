package mpn.indomaret.demo.services;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Province;
import mpn.indomaret.demo.repositories.ProvinceRepository;
import mpn.indomaret.demo.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    public ResponseEntity<ResponseMessage> allProvinces() {
        List<Province> provinces = provinceRepository.findAll();
        return new ResponseEntity<>(new ResponseMessage(200, "OK", provinces), HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> provinceById(int id) {
        Optional<Province> province = provinceRepository.findById(id);

        if (province.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(200, "OK", province.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> updateProvince(int id, Province province) {
        Optional<Province> provinceExist = provinceRepository.findById(id);

        if (provinceExist.isPresent()) {
            provinceExist.get().setName(province.getName());
            provinceRepository.save(provinceExist.get());

            return new ResponseEntity<>(new ResponseMessage(200, "OK", provinceExist.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ResponseMessage> addProvince(Province province) {
        Optional<Province> provinceExist = provinceRepository.findByName(province.getName());
        if (provinceExist.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(409, "CONFLICT", "Province already exists!"), HttpStatus.NOT_FOUND);
        } else {
            provinceRepository.save(province);
            return new ResponseEntity<>(new ResponseMessage(200, "OK", province), HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseMessage> deleteProvince(int id) {
        Optional<Province> provinceExist = provinceRepository.findById(id);

        if (provinceExist.isPresent()) {
            provinceRepository.deleteById(provinceExist.get().getId());
            return new ResponseEntity<>(new ResponseMessage(200, "OK", "Province deleted!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage(404, "NOT FOUND", ""), HttpStatus.NOT_FOUND);
        }
    }

}

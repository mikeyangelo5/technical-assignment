package mpn.indomaret.demo.services;

import mpn.indomaret.demo.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

    public ResponseEntity<ResponseMessage> index() {
        return new ResponseEntity<>(new ResponseMessage(200, "SUCCESS", "Technical Assignment API made by Michael Pandu Nurseto"), HttpStatus.OK);
    }

}

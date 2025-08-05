package mpn.indomaret.demo.services;

import mpn.indomaret.demo.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.*;

@Service
public class IndexService {

    private static final Logger logger = LogManager.getLogger(StoreService.class);

    public ResponseEntity<ResponseMessage> index() {
        logger.info("SUCCESS - fetching index");
        return new ResponseEntity<>(new ResponseMessage(200, "OK", "Technical Assignment API made by Michael Pandu Nurseto"), HttpStatus.OK);
    }

}

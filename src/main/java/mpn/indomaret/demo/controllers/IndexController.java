package mpn.indomaret.demo.controllers;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.response.ResponseMessage;
import mpn.indomaret.demo.services.IndexService;
import mpn.indomaret.demo.services.StoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;
    private static final Logger logger = LogManager.getLogger(IndexController.class);

    @GetMapping("")
    public ResponseEntity<ResponseMessage> index() {
        try {
            return indexService.index();
        } catch (Exception e) {
            logger.error("ERROR IN INDEX - {}", e.getMessage() );

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

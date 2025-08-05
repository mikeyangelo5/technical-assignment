package mpn.indomaret.demo.controllers;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Branch;
import mpn.indomaret.demo.response.ResponseMessage;
import mpn.indomaret.demo.services.BranchService;
import mpn.indomaret.demo.services.StoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branch")
@CrossOrigin
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;
    private static final Logger logger = LogManager.getLogger(BranchController.class);

    @GetMapping("")
    public ResponseEntity<ResponseMessage> allBranches() {
        try {
            return branchService.getBranches();
        } catch (Exception e) {
            logger.error("ERROR GETTING ALL BRANCHES - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseMessage> branchById(@PathVariable("id") int id) {
        try {
            return branchService.getBranch(id);
        } catch (Exception e) {
            logger.error("ERROR GETTING BRANCH DETAIL - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/province")
    public ResponseEntity<ResponseMessage> branchProvince(@RequestParam(value = "province") String province) {
        try {
            return branchService.getProvinceBranches(province);
        } catch (Exception e) {
            logger.error("ERROR GETTING BRANCH OF PROVINCE - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateBranch(@RequestParam(value = "id") int id, @RequestBody Branch branch) {
        try {
            return branchService.updateBranch(id, branch);
        } catch (Exception e) {
            logger.error("ERROR UPDATING BRANCH - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createBranch(@RequestBody Branch branch) {
        try {
            return branchService.addBranch(branch);
        } catch (Exception e) {
            logger.error("ERROR CREATING NEW BRANCH - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteBranch(@RequestParam(value = "id") int id) {
        try {
            return branchService.deleteBranch(id);
        } catch (Exception e) {
            logger.error("ERROR DELETING EXISTING BRANCH - {}", e.getMessage());

            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

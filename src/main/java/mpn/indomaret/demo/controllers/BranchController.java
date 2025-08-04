package mpn.indomaret.demo.controllers;

import lombok.RequiredArgsConstructor;
import mpn.indomaret.demo.models.Branch;
import mpn.indomaret.demo.response.ResponseMessage;
import mpn.indomaret.demo.services.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branch")
@CrossOrigin
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("")
    public ResponseEntity<ResponseMessage> allBranches() {
        try {
            return branchService.getBranches();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseMessage> branchById(@PathVariable("id") int id) {
        try {
            return branchService.getBranch(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/province")
    public ResponseEntity<ResponseMessage> branchProvince(@RequestParam(value = "province") String province) {
        try {
            return branchService.getProvinceBranches(province);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateBranch(@RequestParam(value = "id") int id, @RequestBody Branch branch) {
        try {
            return branchService.updateBranch(id, branch);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createBranch(@RequestBody Branch branch) {
        try {
            return branchService.addBranch(branch);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteBranch(@RequestParam(value = "id") int id) {
        try {
            return branchService.deleteBranch(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(500, "ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

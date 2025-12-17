package org.enums.controller;

import org.enums.dtos.response.SaveStatementResponse;
import org.enums.model.XapiStatement;
import org.enums.service.LrsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/xapi/statements")
@RequestMapping("/xapi")
@CrossOrigin(origins = "*")
public class LrsController {

    private final LrsService lrsService;

    public LrsController(LrsService lrsService) {
        this.lrsService = lrsService;
    }

    @PostMapping
    public ResponseEntity<?> saveStatement(@RequestBody XapiStatement statement) {
        try {
            SaveStatementResponse response = lrsService.saveStatement(statement);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("LRS Error: " + exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<XapiStatement>> getStatements() {
        return ResponseEntity.ok(lrsService.getStatements());
    }
}
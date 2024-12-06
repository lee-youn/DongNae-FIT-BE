package yung.dongnae_fit.domain.program.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProgramController {

    @GetMapping("/auth/programs")
    public ResponseEntity<?> getPrograms(@RequestParam(required = false) Long min,
                                         @RequestParam(required = false) Long max,
                                         @RequestParam(required = false) String search) {

    }
}

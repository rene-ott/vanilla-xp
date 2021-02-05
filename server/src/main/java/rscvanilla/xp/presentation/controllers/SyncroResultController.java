package rscvanilla.xp.presentation.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rscvanilla.xp.domain.services.SyncroResultService;
import rscvanilla.xp.presentation.dto.SyncroResultDto;

@RestController
public class SyncroResultController {

    private final SyncroResultService syncroResultService;
    private final ModelMapper modelMapper;

    @Autowired
    public SyncroResultController(SyncroResultService syncroResultService, ModelMapper modelMapper) {
        this.syncroResultService = syncroResultService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value="/api/syncro-result", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(
            modelMapper.map(syncroResultService.getLatestForToday(), SyncroResultDto.class),
            HttpStatus.OK
        );
    }
}

package rscvanilla.xp.presentation.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rscvanilla.xp.domain.services.SyncroResultService;
import rscvanilla.xp.presentation.dto.SyncroResultDto;

@RestController
public class SyncroResultController extends ApiController {

    private final SyncroResultService syncroResultService;
    private final ModelMapper modelMapper;

    @Autowired
    public SyncroResultController(SyncroResultService syncroResultService, ModelMapper modelMapper) {
        this.syncroResultService = syncroResultService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value="/syncro-result", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(
            modelMapper.map(syncroResultService.getLatestForToday(), SyncroResultDto.class),
            HttpStatus.OK
        );
    }
}

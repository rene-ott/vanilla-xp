package rscvanilla.xp.presentation.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rscvanilla.xp.domain.models.PlayerOverallStateChange;
import rscvanilla.xp.presentation.dto.PlayerOverallStateChangeDto;
import rscvanilla.xp.domain.services.PlayerService;

import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
public class PlayerController extends ApiController {

    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerController(PlayerService playerService, ModelMapper modelMapper) {
        this.playerService = playerService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value="/overall-state-change/{daysBeforeToday:1|2|3|7|14|30}", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index(@PathVariable int daysBeforeToday) {

        var result = playerService.getPlayerOverallStateChanges(daysBeforeToday)
            .stream()
            .map(it -> modelMapper.map(it, PlayerOverallStateChangeDto.class))
            .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

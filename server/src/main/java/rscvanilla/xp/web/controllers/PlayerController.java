package rscvanilla.xp.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rscvanilla.xp.web.dto.PlayerExperienceDeltaDto;
import rscvanilla.xp.web.services.PlayerService;

import java.util.stream.Collectors;

@RestController
public class PlayerController {

    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerController(PlayerService playerService, ModelMapper modelMapper) {
        this.playerService = playerService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value="/api/{days}", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index(@PathVariable int days) {

        var result = playerService.getPlayersExperienceDelta(1)
            .stream()
            .map(it -> modelMapper.map(it, PlayerExperienceDeltaDto.class))
            .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

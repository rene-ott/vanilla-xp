package rscvanilla.xp.controllers;

import rscvanilla.xp.models.Player;
import rscvanilla.xp.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private final PlayerRepository repository;

    public HelloWorldController(PlayerRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/{name}")
    public String index(@PathVariable String name) {

        return repository.findByName(name).orElseGet(() -> new Player(name)).getName();
    }
}

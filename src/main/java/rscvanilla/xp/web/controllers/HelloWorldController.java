package rscvanilla.xp.web.controllers;

import rscvanilla.xp.web.repositories.PlayerRepository;
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
        var player = repository.findByName("Rene");
        var d = player.getExperience();
        for (var item : d) {
            var f = item.getAttack();
        }

        return "Missing Player";
    }
}

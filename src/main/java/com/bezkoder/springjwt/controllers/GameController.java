package com.bezkoder.springjwt.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bezkoder.springjwt.models.Game;
import com.bezkoder.springjwt.payload.request.GameRequest;
import com.bezkoder.springjwt.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/game")
public class GameController {
  @Autowired
  GameRepository gameRepository;

  @GetMapping
  public ResponseEntity<List<Game>> getAllGames() {
    List<Game> games = gameRepository.findAll();
    return ResponseEntity.ok(games);
  }

  @GetMapping("/{gameId}")
  public ResponseEntity<Game> getGame(@PathVariable String gameId) {
    Optional<Game> optionalGame = gameRepository.findById(Integer.parseInt(gameId));

    return ResponseEntity.ok(optionalGame.get());
  }

  @PostMapping
  public ResponseEntity<?> newGame(@Valid @RequestBody GameRequest gameRequest) {
    Game game = new Game(
      gameRequest.getName(),
      gameRequest.getDescription());

    gameRepository.save(game);
    
    return ResponseEntity.ok(game);
  }

  @PutMapping("/{gameId}")
  public ResponseEntity<?> updateGame(
    @PathVariable String gameId,
    @Valid @RequestBody GameRequest gameRequest) {
    Optional<Game> optionalGame = gameRepository.findById(Integer.parseInt(gameId));
    
    Game game = optionalGame.get();

    game.setName(gameRequest.getName());
    game.setDescription(gameRequest.getDescription());

    gameRepository.save(game);

    return ResponseEntity.ok(game);
  }

  @DeleteMapping("/{gameId}")
  public ResponseEntity<?> deleteGame(
    @PathVariable String gameId
  ) {
    try {
      gameRepository.deleteById(Integer.parseInt(gameId));
      return ResponseEntity.ok("ok");
    } catch (EmptyResultDataAccessException ex) {
      return ResponseEntity.notFound().build();
    }

  }
}

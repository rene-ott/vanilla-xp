package rscvanilla.xp.test.unit.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rscvanilla.xp.domain.entities.Player;
import rscvanilla.xp.domain.entities.PlayerOverallState;
import rscvanilla.xp.domain.services.PlayerService;
import rscvanilla.xp.domain.services.impl.PlayerServiceImpl;
import rscvanilla.xp.infrastructure.time.SystemTime;
import rscvanilla.xp.persistance.repositories.PlayerRepository;

import java.time.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PlayerServiceImplTest {

    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";
    private static final String PLAYER_3 = "Player3";
    private static final String PLAYER_4 = "Player4";

    private static final int STATE_XP_1_BEFORE = 1;
    private static final int STATE_XP_1_AFTER = 10;

    private static final int STATE_XP_2_BEFORE = 2;
    private static final int STATE_XP_2_AFTER = 20;

    private static final int STATE_XP_3_BEFORE = 3;
    private static final int STATE_XP_3_AFTER = 30;

    private static final int STATE_XP_4_BEFORE = 4;
    private static final int STATE_XP_4_AFTER = 40;

    private static final LocalDate TODAY_DATE = LocalDate.of(2020, 1, 15);

    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private SystemTime systemTime;

    @BeforeEach
    public void setUp() {
        playerService = new PlayerServiceImpl(playerRepository, systemTime);

    }

    @DisplayName("Tests if new players return empty change. Orders them by name if both have equal createdAt.")
    @Test
    public void getPlayerOverallStateChangesWithDaysBefore1_TwoNewPlayers_Returns2EmptyChanges() {
        var firstPlayer = createFirstPlayer();
        var firstPlayerState = createState(STATE_XP_1_BEFORE, localDateTimeOfToday(15, 0));
        firstPlayer.addState(firstPlayerState);

        var secondPlayer = createSecondPlayer();
        var secondPlayerState = createState(STATE_XP_2_AFTER, localDateTimeOfToday(15, 0));
        secondPlayer.addState(secondPlayerState);

        when(systemTime.currentDate()).thenReturn(TODAY_DATE);
        when(playerRepository.findAll()).thenReturn(listOf(secondPlayer, firstPlayer));

        var changes = playerService.getPlayerOverallStateChanges(1);
        assertThat(changes.size()).isEqualTo(2);

        var firstChange = changes.get(0);
        assertThat(firstChange.getPlayerName()).isEqualTo(PLAYER_1);
        assertThat(firstChange.isNotAvailable());

        var secondChange = changes.get(1);
        assertThat(secondChange.getPlayerName()).isEqualTo(PLAYER_2);
        assertThat(secondChange.isNotAvailable());
    }

    public LocalDateTime localDateTimeOfToday(int hour, int min) {
        return LocalDateTime.of(TODAY_DATE, LocalTime.of(hour, min));
    }

    private List<Player> listOf(Player...args) {
        return Arrays.asList(args);
    }

    private Player createFirstPlayer() {
        return createOpenedPlayer(PLAYER_1);
    }

    private Player createSecondPlayer() {
        return createOpenedPlayer(PLAYER_2);
    }

    private Player createOpenedPlayer(String name) {
        var player = Player.builder().name(name).build();
        var createdAt = LocalDateTime.of(LocalDate.of(2020, 1, 15), LocalTime.of(20, 0)).toInstant(ZoneOffset.UTC);

        player.setCreatedAt(createdAt);
        player.setUpdatedAt(createdAt);

        return player;
    }

    private PlayerOverallState createState(int xp, LocalDateTime createdAt) {

        var state = PlayerOverallState.builder().rank(1).xp(xp).level(1).build();
        state.setCreatedAt(createdAt.toInstant(ZoneOffset.UTC));
        state.setUpdatedAt(createdAt.toInstant(ZoneOffset.UTC));

        return state;
    }
}

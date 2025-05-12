/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import characters.Player;
import characters.GameCharacter;
import components.Results;
import actions.*;
import static actions.ActionFactory.createAction;
import static characters.CharacterFabric.SHAO_KAHN;
import components.Items;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, отвечающий за проведение боевых раундов
 * Управляет логикой атак, защиты, дебаффов, использованием предметов, начислением опыта,
 * повышением уровня и выбором действий противника
 * @author kozhe
 */
public class Fight {
    Controller controller;
    Player player;
    GameCharacter enemy;

    /**
     *
     */
    public Location location = new Location();

    /**
     *
     */
    public ArrayList<Action> actionsList = new ArrayList<>() {
        {
            add(new Hit());
            add(new Defense());
            add(new Debuff());
            add(new Heal());
        }
    };
    private final Deque<String> playerActionHistory = new ArrayDeque<>();
    private static final int HISTORY_SIZE = 3;
    private final int[] experienceForNextLevel = {40, 90, 180, 260, 410, 1000};

    /**
     *
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     *
     * @param player
     */
    public void setHuman(Player player) {
        this.player = player;
    }

    /**
     *
     * @param enemy
     */
    public void setEnemy(GameCharacter enemy) {
        this.enemy = enemy;
    }

    /**
     *
     * @return
     */
    public Player getHuman() {
        return this.player;
    }

    /**
     *
     * @return
     */
    public GameCharacter getEnemy() {
        return this.enemy;
    }

    /**
     * Выполняет ход игрока с выбранным действием
     * @param enemyAction
     * @param playerAction
     */
    public void playerMove(Action enemyAction, Action playerAction) {
        controller.setActionLabels(enemy, player, enemyAction, playerAction);
        playerAction.realization(player, enemy, enemyAction.getType());
    }

    /**
     * Выполняет ход врага с выбранным действием
     * @param enemyAction
     * @param playerAction
     */
    public void enemyMove(Action enemyAction, Action playerAction) {
        controller.setActionLabels(player, enemy, enemyAction, playerAction);
        playerAction.realization(enemy, player, enemyAction.getType());
    }

    /**
     *
     */
    public void checkDebuff() {
        if (!enemy.hasBonusDamage()) {
            controller.setDebuffLabel(enemy, false);
        }
        if (enemy.hasBonusDamage()) {
            controller.setDebuffLabel(enemy, true);
            enemy.loseBonusDamageTurn();
        }
        if (!player.hasBonusDamage()) {
            controller.setDebuffLabel(player, false);
        }
        if (player.hasBonusDamage()) {
            controller.setDebuffLabel(enemy, true);
            player.loseBonusDamageTurn();
        }

    }

    /**
     * Выполняет боевое взаимодействие, включая выбор действия, реализацию урона, проверку состояния и обновление GUI
     * @param playerActionIndex индекс действия игрока
     * @param gameResults результаты игры
     * @param locationsNumber номер текущей локации
     * @param enemiesList список врагов
     */
    public void hit(int playerActionIndex, ArrayList<Results> gameResults, int locationsNumber, GameCharacter[] enemiesList) {
        Action playerAction = createAction(ActionFactory.ActionType.values()[playerActionIndex]);

        Action enemyAction = chooseEnemyAction(enemy, new ArrayList<>(actionsList), getPlayerActionHistory());
        // Логируем действие игрока
        logPlayerAction(playerAction.getType());
        
        playerAction.realization(player, enemy, enemyAction.getType());
        controller.setActionLabels(enemy, player, enemyAction, playerAction);
    
        if (enemy.getHealth() > 0) {
            enemyAction.realization(enemy, player, playerAction.getType());
            controller.setActionLabels(player, enemy, enemyAction, playerAction);
        }
        updateGameState();
        checkDeath(gameResults, locationsNumber, enemiesList);
    }
    
    private void updateGameState() {
        controller.setRoundTexts(player, enemy);
        checkDebuff();
        controller.setHealthBar(player);
        controller.setHealthBar(enemy);
    }
    /**
     *
     * @param gameResults
     * @param locationsNumber
     * @param enemiesList
     */
    public void checkDeath(ArrayList<Results> gameResults, int locationsNumber, GameCharacter[] enemiesList) {
        if (player.getHealth() <= 0 & player.getItems()[2].getCount() > 0) {
            player.setHealth((int) (player.getMaxHealth() * 0.05));
            player.getItems()[2].setCount(-1);
            controller.setHealthBar(player);
            controller.revive(player, player.getItems());
        }
        if (player.getHealth() <= 0 | enemy.getHealth() <= 0) {
            if (location.getCurrentLocation() == locationsNumber & SHAO_KAHN.equals(enemy.getName())) {
                location.resetLocation(false, 1);
                endFinalRound(gameResults, enemiesList);
            } else {
                endRound(enemiesList);
            }
        }
    }

    /**
     *
     * @param enemiesList
     */
    public void endRound(GameCharacter[] enemiesList) {
        controller.makeEndFightDialogVisible();
        if (player.getHealth() > 0) {
            controller.setRoundEndText("You win");
            if (enemy.getName().equals(SHAO_KAHN)) {
                addItems(38, 23, 8, player.getItems());
                addPointsBoss(player);
                location.resetLocation(true, player.getLevel());
            } else {
                addItems(25, 15, 5, player.getItems());
                addPoints(player);
            }
        } else {
            reset(enemiesList);
            controller.setRoundEndText(enemy.getStringName() + " win");
        }
    }

    /**
     *
     * @param enemiesList
     */
    public void reset(GameCharacter[] enemiesList) {
        player.setDamage(16);
        player.setHealth(80);
        player.setMaxHealth(80);
        resetEnemies(enemiesList);
        player.setLevel(0);
        player.resetPoints();
        player.resetExperience();
        player.setNextExperience(40);
        location.setFullEnemiesList(enemiesList);
        location.resetLocation(false, player.getLevel());
    }

    /**
     *
     * @param gameResults
     * @param enemiesList
     */
    public void endFinalRound(ArrayList<Results> gameResults, GameCharacter[] enemiesList) {
        resetEnemies(enemiesList);
        String text = "Победа не на вашей стороне";
        if (player.getHealth() > 0) {
            addPoints(player);
            text = "Победа на вашей стороне";
        }
        boolean top = false;
        if (gameResults == null) {
            top = true;
        } else {
            int a = 0;
            for (Results results : gameResults) {
                if (player.getPoints() < results.getPoints()) {
                    a++;
                }
            }
            if (a < 10) {
                top = true;
            }
        }
        controller.gameEnding(text, top);
    }

    /**
     *
     */
    public void newRound() {
        controller.setPlayerMaxHealthBar(player);
        controller.setEnemyMaxHealthBar(enemy);
        player.setHealth(player.getMaxHealth());
        enemy.setHealth(enemy.getMaxHealth());
        controller.setHealthBar(player);
        controller.setHealthBar(enemy);
    }
    
    /**
     * Добавляет последнее действие игрока в историю
     * @param action
     */
    public void logPlayerAction(String action) {
        if (playerActionHistory.size() >= HISTORY_SIZE) {
            playerActionHistory.removeFirst();
        }
        playerActionHistory.addLast(action);
    }
    
    /**
     *
     * @return
     */
    public List<String> getPlayerActionHistory() {
        return new ArrayList<>(playerActionHistory);
    }

    /**
     *Выбирает действие врага на основе вероятностей и действий игрока
     * @param enemy
     * @param actions
     * @param playerHistory
     * @return
     */
    public Action chooseEnemyAction(GameCharacter enemy, List<Action> actions, List<String> playerHistory) {
        Map<String, Double> actionWeights = new HashMap<>();
        switch (enemy.getName()) {
            case SUB_ZERO -> {
                actionWeights.put("Hit", 0.4);
                actionWeights.put("Block", 0.3);
                actionWeights.put("Debuff", 0.3);
            }
            case SHAO_KAHN -> {
                actionWeights.put("Hit", 0.6);
                actionWeights.put("Block", 0.4);
            }
            case LIU_KANG -> {
                actionWeights.put("Hit", 0.5);
                actionWeights.put("Counter", 0.5);
            }
            default -> {
                actionWeights.put("Hit", 0.7);
                actionWeights.put("Block", 0.3);
            }
        }

        adjustWeightsByPlayerActions(actionWeights, playerHistory);

        return selectWeightedAction(actions, actionWeights);
    }
    /**
     * Адаптирует вероятности выбора действия врагом в зависимости от частоты действий игрока.
     * @param weights 
     * @param playerHistory 
     */
    private void adjustWeightsByPlayerActions(Map<String, Double> weights, List<String> playerHistory) {
        long playerHits = playerHistory.stream().filter(a -> a.equals("Hit")).count();
        long playerBlocks = playerHistory.stream().filter(a -> a.equals("Block")).count();

        if (playerHits > HISTORY_SIZE / 2) {
            weights.put("Block", weights.getOrDefault("Block", 0.0) * 1.5);
            weights.put("Counter", weights.getOrDefault("Counter", 0.0) * 1.3);
        }

        if (playerBlocks > HISTORY_SIZE / 2) {
            weights.put("Debuff", weights.getOrDefault("Debuff", 0.0) * 1.4);
            weights.put("Hit", weights.get("Hit") * 0.8);
        }

        normalizeWeights(weights);
    }
    /**
     * Нормализует веса вероятностей, чтобы сумма была равна 1.
     * @param weights карта с весами
     */
    private void normalizeWeights(Map<String, Double> weights) {
        double totalWeight = weights.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();

        if (totalWeight > 0) {
            weights.replaceAll((action, weight) -> weight / totalWeight);
        } 
        else {
            double defaultWeight = 1.0 / weights.size();
            weights.replaceAll((action, weight) -> defaultWeight);
        }
    }
    /**
     * Случайно выбирает действие на основе взвешенных вероятностей.
     * @param actions
     * @param weights
     * @return выбранное действие
     */
    private Action selectWeightedAction(List<Action> actions, Map<String, Double> weights) {
        double total = weights.values().stream().mapToDouble(Double::doubleValue).sum();
        double random = Math.random() * total;

        double current = 0;
        for (Action action : actions) {
            current += weights.getOrDefault(action.getType(), 0.0);
            if (random <= current) {
                return action;
            }
        }
        return actions.get(actions.size() - 1);
    }

    /**
     *
     * @param player
     */
    public void addPoints(Player player) {
        player.setExperience(20 + 5 * player.getLevel());
        player.setPoints(20 + 5 * player.getLevel() + player.getHealth() / 4);
    }

    /**
     *
     * @param player
     * @return
     */
    public boolean checkExperience(Player player) {
        return player.getExperience() >= player.getNextExperience();
    }

    /**
     *
     * @param player
     * @param enemies
     */
    public void levelUp(Player player, GameCharacter[] enemies) {
        player.addLevel();
        int i = 0;
        while (player.getNextExperience() >= experienceForNextLevel[i]) {
            i = i + 1;
        }
        player.setNextExperience(experienceForNextLevel[i]);
        for (int j = 0; j < 5; j++) {
            newHealthEnemy(enemies[j], player);
        }
    }

    /**
     *
     * @param player
     */
    public void addPointsBoss(Player player) {
        player.setExperience(50);
        player.setPoints(65 + player.getHealth() / 2);
    }

    /**
     *
     * @param k1
     * @param k2
     * @param k3
     * @param items
     */
    public void addItems(int k1, int k2, int k3, Items[] items) {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].setCount(1);
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            items[1].setCount(1);
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            items[2].setCount(1);
        }
    }

    /**
     *
     * @param player
     */
    public void addHealthToPlayer(Player player) {
        player.addMaxHealth(40 + 5 * player.getLevel());
    }

    /**
     *
     * @param player
     */
    public void addDamageToPlayer(Player player) {
        player.addDamage(5 + player.getLevel());
    }

    /**
     *
     * @param enemy
     * @param player
     */
    public void newHealthEnemy(GameCharacter enemy, Player player) {
        enemy.addMaxHealth(enemy.getMaxHealth() * (35 - 3 * player.getLevel()) / 100);
        enemy.addDamage(enemy.getDamage() * (20 + player.getLevel()) / 100);
        enemy.addLevel();
    }

    /**
     *
     * @param human
     * @param items
     * @param name
     * @param controller
     */
    public void useItem(GameCharacter human, Items[] items, String name, Controller controller) {
        switch (name) {
            case "First item" -> {
                if (items[0].getCount() > 0) {
                    human.addHealth((int) (human.getMaxHealth() * 0.25));
                    items[0].setCount(-1);
                } else {
                    controller.openCantUseItemDialog();
                }
            }
            case "Second item" -> {
                if (items[1].getCount() > 0) {
                    human.addHealth((int) (human.getMaxHealth() * 0.5));
                    items[1].setCount(-1);
                } else {
                    controller.openCantUseItemDialog();
                }
            }
            case "Third item" -> controller.openCantUseItemDialog();
        }
    }

    /**
     *
     * @param enemiesList
     */
    public void resetEnemies(GameCharacter[] enemiesList) {
        for (GameCharacter enemy : enemiesList) {
            enemy.setLevel(1);
            switch (enemy.getName()) {
                case SUB_ZERO -> {
                    enemy.setDamage(16);
                    enemy.setMaxHealth(60);
                }
                case SONYA_BLADE -> {
                    enemy.setDamage(16);
                    enemy.setMaxHealth(80);
                }
                case SHAO_KAHN -> {
                    enemy.setDamage(30);
                    enemy.setMaxHealth(100);
                }
                case LIU_KANG -> {
                    enemy.setDamage(20);
                    enemy.setMaxHealth(70);
                }
                case BARAKA -> {
                    enemy.setDamage(12);
                    enemy.setMaxHealth(100);
                }
            }
        }
    }

}

import java.util.Random;

public class HW4 {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250, 300, 500, 180, 400};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 0, 25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Thor"};
    public static boolean[] isHeroAlive = {true, true, true, true, true, true, true};
    public static int roundNumber = 0;
    public static boolean isWitcherSacrificed = false;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        medicHeals();
        witcherSacrifice();
        heroesAttack();
        printStatistics();
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttacks() {
        Random random = new Random();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                int damage = bossDamage;

                if (heroesAttackType[i].equals("Lucky") && random.nextBoolean()) {
                    System.out.println("Lucky dodged the attack!");
                    continue;
                }

                if (heroesAttackType[i].equals("Golem")) {
                    damage = (int) (bossDamage * 0.2);
                } else if (heroesHealth[4] > 0) { // Golem takes 1/5 of the damage
                    damage += (int) (bossDamage * 0.2);
                    heroesHealth[4] -= (int) (bossDamage * 0.2);
                    if (heroesHealth[4] < 0) heroesHealth[4] = 0;
                }

                if (heroesHealth[i] - damage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= damage;
                }
            }
        }
    }

    public static void medicHeals() {
        if (heroesHealth[3] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != 3 && heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    heroesHealth[i] += 30; // Heal amount
                    System.out.println("Medic healed " + heroesAttackType[i]);
                    break;
                }
            }
        }
    }

    public static void witcherSacrifice() {
        if (isWitcherSacrificed || heroesHealth[5] <= 0) return;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] == 0 && i != 5) {
                heroesHealth[i] = heroesHealth[5];
                heroesHealth[5] = 0;
                isWitcherSacrificed = true;
                System.out.println("Witcher sacrificed himself to revive " + heroesAttackType[i]);
                break;
            }
        }
    }

    public static void heroesAttack() {
        Random random = new Random();
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    int coefficient = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }

                if (heroesAttackType[i].equals("Thor") && random.nextBoolean()) {
                    System.out.println("Thor stunned the boss!");
                    bossDamage = 0;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " -----------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}

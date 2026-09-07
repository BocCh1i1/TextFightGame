package domain;

public class EnemyCharacter extends Character {


    public String skill;
    public boolean defending;

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
        this.defending = false;
    }

    @Override
    public void takeDamage(int damage) {
        if (this.defending) {
            damage = damage / 2 > 1 ? damage / 2 : 1;
            this.defending = false;
        }

        super.takeDamage(damage);


    }

}

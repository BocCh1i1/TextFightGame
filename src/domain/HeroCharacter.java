package domain;

import java.util.ArrayList;

public class HeroCharacter extends Character {

    public ArrayList<String> skillsList = new ArrayList<String>();

    public HeroCharacter() {
        super();
        this.skillsList = new ArrayList<String>();
    }

    public HeroCharacter(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
        this.skillsList = new ArrayList<String>();
    }

    // 获取角色技能列表
    public String showSkills() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillsList.size(); i++) {
            sb.append(skillsList.get(i));
            if (i < skillsList.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

}

package domain;

/*2.2 角色属性
属性	描述	基础值	成长值
角色名	玩家的角色登录而来	无	无
生命值(HP) / maxHP	角色生存能力	初始血量100 + 分配点数×10	每3胜+30
攻击力(ATK)	影响伤害输出	初始攻击10 + 分配点数×2	每3胜+5
防御力(DEF)	减少受到的伤害	初始防御0 + 分配点数×1	每3胜+3
技能列表（集合）	玩家技能列表参见2.3	无	无
2.3 玩家技能列表
技能名称	消耗	效果	描述
普通攻击	无	造成基础伤害	标准攻击方式
强力一击	10HP	造成180%攻击力的伤害	高伤害但消耗生命
生命汲取	10HP	恢复0-20点生命值	风险回报型恢复技能
*/
public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;

    public Character() {

    }

    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }


    //判断角色是否存活
    public boolean isAlive() {
        return this.HP > 0;
    }

    //恢复生命值
    public void heal(int amount) {
        this.HP += amount;
        if (this.HP > this.maxHP) {
            this.HP = this.maxHP;
        }
    }

    //收到伤害
    public void takeDamage(int damage) {
        this.HP -= damage;
        if (this.HP < 0) {
            this.HP = 0;
        }
    }

    //展示角色信息
    public String show() {
        return "角色名：" + this.name +
                " [ 生命值：" + this.HP + "/" + this.maxHP +
                " , 攻击力：" + this.attack +
                " , 防御力：" + this.defense +
                " ]";
    }


}

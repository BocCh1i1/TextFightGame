package UI;

import domain.EnemyCharacter;
import domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {

    // 游戏入口
    public void gameStart(String username) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println(" 🎮 " + username + "欢迎来到文字格斗游戏 🎮");
        System.out.println("╚════════════════════════════════════════╝");


        //创建游戏角色
        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("角色创建成功");
        System.out.println("🌟 初始属性: " + player.show());
        System.out.println("🌟 拥有技能: " + player.showSkills());


        //2.4 敌人类型
        //敌人名称	生命值	攻击力	防御力	技能（变量）
        //初级战士	80	15	10	猛击（130%伤害）
        //敏捷刺客	60	20	5	快速攻击（2次50%伤害）
        //重装坦克	100	10	15	防御姿态（下回合伤害减半） buff（ boolean defending）
        //神秘法师	65	22	8	火球术（150%伤害）

        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克", 100, 10, 15, "防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师", 65, 22, 8, "火球术"));


        int count = 1;//记录跟第几个敌人战斗
        int wins = 0;

        while (player.isAlive()) {
            // 战斗

            // 重置敌人的属性，敌人属性每场HP+8, ATK+2, DEF+1
            if (wins > 0) {
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);

                    c.maxHP += 8;
                    c.HP = c.maxHP;
                    c.attack += 2;
                    c.defense += 1;
                    c.defending = false;
                }
            }

            // 随机选择敌人(Random)
            Random r = new Random();
            int enemyIndex = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(enemyIndex);

            // 战斗开始
            System.out.println("═══════════════════════════════════════");
            System.out.println("⚔️ 第 " + count + " 场战斗开始！对手: " + enemy.name);

            int round = 1;
            while (player.isAlive()) {

                System.out.println("---------------------------------------");
                System.out.println("⚔️ 第 " + round + " 回合开始！");

                //显示双方状态（生命值）
                System.out.println(getHPBar(player.name, player.HP, player.maxHP));
                System.out.println(getHPBar(enemy.name, enemy.HP, enemy.maxHP));

                //玩家回合
                playerTurn(player, enemy);

                //判断敌人是否死亡
                if (!enemy.isAlive()) {
                    System.out.println("🎉 你击败了 " + enemy.name + "!");
                    wins++;
                    break;
                }

                //敌人回合
                enemyTurn(enemy, player);

                //判断玩家是否死亡
                if (!player.isAlive()) {
                    System.out.println("GAME OVER! 你被击败了！");
                    break;
                }

                round++;

            }

            //战斗结算
            //胜利时：
            //恢复30-60点生命值
            //胜场数+1
            //每3胜获得属性提升
            //失败时：游戏结束
            if (player.isAlive()) {
                int healAmount = r.nextInt(31) + 30;

                player.heal(healAmount);
                System.out.println("💚 战斗结束！你恢复了 " + healAmount + " 点生命值");
                System.out.println("🏆 当前胜场: " + wins);
                System.out.println("═══════════════════════════════════════");

                if (wins % 3 == 0) {
                    //生命值(HP) / maxHP	角色生存能力	初始血量100 + 分配点数×10	每3胜+40
                    //攻击力(ATK)	影响伤害输出	初始攻击10 + 分配点数×2	每3胜+7
                    //防御力(DEF)	减少受到的伤害	初始防御0 + 分配点数×1	每3胜+4
                    player.maxHP += 40;
                    player.attack += 7;
                    player.defense += 4;
                    System.out.println("⭐恭喜你，你获得了属性提升！");
                    System.out.println("最大生命值提升至 + 40，攻击力提升至 + 7，防御力提升至 + 4");
                    System.out.println("当前属性: " + player.show());

                }
            }


            //询问玩家是否继续
            if (player.isAlive()) {
                System.out.println("是否继续游戏？(y/n)");
                Scanner sc = new Scanner(System.in);
                String choice = sc.next();
                if (choice.equalsIgnoreCase("n")) {
                    System.out.println("游戏结束，感谢游玩！");
                    break;
                } else if (choice.equalsIgnoreCase("y")) {
                    System.out.println("继续游戏！");
                    count++;
                } else {
                    System.out.println("无效输入，默认继续游戏！");
                    count++;
                }

            }


        }


        System.out.println("═══════════════════════════════════════");
        System.out.println("游戏结束！");
        System.out.println("总胜场数: " + wins);
        System.out.println("感谢游玩！");

        System.exit(0);

    }


    public HeroCharacter createPlayerCharacter(String username) {
        System.out.println("创建游戏角色");
        System.out.println("您的角色名称为：" + username);

        int points = 20;


        System.out.println("请分配属性点 (共20点):");
        System.out.println("1. 生命值 (每点+10 HP)");
        System.out.println("2. 攻击力 (每点+2 ATK)");
        System.out.println("3. 防御力 (每点+1 DEF)");

        Scanner sc = new Scanner(System.in);

        String[] attributes = {"生命值", "攻击力", "防御力"};

        int[] values = new int[3];

        for (int i = 0; i < attributes.length; i++) {
            System.out.println("分配点数到 " + attributes[i] + " (剩余点数: " + points + "): 0");
            int input = sc.nextInt();

            if (input < 0) {
                System.out.println("无效输入，默认分配0点");
                input = 0;
            }

            if (input > points) {
                System.out.println("属性点不足，全部分配给" + attributes[i]);
                input = points;
            }

            points -= input;

            values[i] = input;

        }


        HeroCharacter player = new HeroCharacter(
                username, // 角色名称
                100 + values[0] * 10, // 初始生命值
                10 + values[1] * 2,   // 初始攻击力
                0 + values[2] * 1);   // 初始防御力

        //添加玩家技能
        //1. 普通攻击
        //2. 强力一击 (消耗10HP)
        //3. 生命汲取 (消耗10HP，恢复生命)
        player.skillsList.add("普通攻击");
        player.skillsList.add("强力一击");
        player.skillsList.add("生命汲取");

        return player;


    }


    public String getHPBar(String name, int HP, int maxHP) {
        //定义一个方法显示血条
        //zhangsan: [████████████████████] 100/100 HP
        //初级战士: [████████████████████] 80/80 HP

        int barLength = 20;

        int filled = (int) (HP / (double) maxHP * barLength);

        StringBuilder sb = new StringBuilder();
        sb.append(name + ":  【");
        for (int i = 0; i < 20; i++) {
            if (i < filled) {
                sb.append("█");
            } else {
                sb.append("░");
            }
        }
        sb.append("】").append(HP).append("/").append(maxHP).append(" HP");
        return sb.toString();

    }


    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {

        //玩家回合
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.print("选择行动 (1-3): ");

        Scanner sc = new Scanner(System.in);
        int choose = sc.nextInt();

        //3.1 伤害计算公式
        //基础伤害公式：伤害 = 攻击力 - 防御力
        //最小伤害：1点
        //技能伤害：伤害 = 攻击力 * n% - 防御力

        switch (choose) {
            default:
                System.out.println("无效输入，默认普通攻击");
            case 1:
                int damage1 = calculateDamage(player.attack, enemy.defense);
                System.out.println("⚔️  你对" + enemy.name + "使用了普通攻击，造成 " + damage1 + " 点伤害！");
                enemy.takeDamage(damage1);
                break;
            case 2:
                if (player.HP > 10) {
                    player.takeDamage(10);
                    int damage2 = calculateDamage((int) (player.attack * 1.8), enemy.defense);
                    System.out.println("💥 消耗10HP，你对" + enemy.name + "使用了强力一击，造成 " + damage2 + " 点伤害！");
                    enemy.takeDamage(damage2);
                } else {
                    System.out.println("你的HP不足10点，无法使用强力一击");
                }
                break;
            case 3:
                if (player.HP > 10) {
                    player.takeDamage(10);
                    Random r = new Random();
                    int healH = r.nextInt(21) + 10;
                    player.heal(healH);
                    System.out.println("💚 消耗10HP，你使用了生命汲取，恢复了" + healH + "点生命值");
                } else {
                    System.out.println("你的HP不足10点，无法使用生命汲取");
                }
                break;
        }
    }


    private void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("===== 敌人回合 =====");

        //敌人回合：选择行动（ 70%的几率普通攻击 / 30%的几率技能攻击 ）

        String action = "普通攻击"; // 默认行动为普通攻击

        Random r = new Random();
        int choose = r.nextInt(10);

        if (choose < 3) {
            action = enemy.skill;
        }


        switch (action) {
            case "普通攻击":
                int damage1 = calculateDamage(enemy.attack, player.defense);
                System.out.println("⚔️  " + enemy.name + "对" + player.name + "使用了普通攻击，造成 " + damage1 + " 点伤害！");
                player.takeDamage(damage1);
                break;
            //初级战士	80	15	10	猛击（130%伤害）
            case "猛击":
                int damage2 = calculateDamage((int) (enemy.attack * 1.3), player.defense);
                System.out.println("💥 " + enemy.name + "对" + player.name + "使用了猛击，造成 " + damage2 + " 点伤害！");
                player.takeDamage(damage2);
                break;
            //敏捷刺客	60	20	5	快速攻击（2次50%伤害）
            case "快速攻击":
                int damage3 = 0;
                for (int i = 0; i < 2; i++) {
                    damage3 += calculateDamage((int) (enemy.attack * 0.5), player.defense);
                }
                System.out.println("💨 " + enemy.name + "对" + player.name + "使用了快速攻击，造成 " + damage3 + " 点伤害！");
                player.takeDamage(damage3);
                break;
            //重装坦克	100	10	15	防御姿态（下回合伤害减半） buff（ boolean defending）
            case "防御姿态":
                enemy.defending = true;
                System.out.println("🛡️ " + enemy.name + "使用了防御姿态，下回合伤害减半！");
                break;
            //神秘法师	65	22	8	火球术（150%伤害）
            case "火球术":
                int damage4 = calculateDamage((int) (enemy.attack * 1.5), player.defense);
                System.out.println("🔥 " + enemy.name + "对" + player.name + "使用了火球术，造成 " + damage4 + " 点伤害！");
                player.takeDamage(damage4);
                break;
        }
    }


    //普通攻击伤害计算公式
    public int calculateDamage(int Attack, int Defense) {
        int damage = Attack - Defense;
        if (damage < 1) {
            damage = 1;
        }
        return damage;
    }


}


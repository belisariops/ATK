package cl.makinolas.atk.utils;

import cl.makinolas.atk.actors.friend.Enemies;

public class Formulas {
  
  // Experience gain formula
  public static double gainExp(int trainerPokemonLevel, int wildPokemonLevel, Enemies enemyType){
    double L = wildPokemonLevel;
    double Lp = trainerPokemonLevel;
    double b = enemyType.baseExperience;
    return (b * (L / 5) * Math.pow(2*L + 10, 2.5) / Math.pow(L + Lp + 10, 2.5)) + 1;
  }
  
  // for medium slow pokemon exp gain.
  public static double nextExpLevel(int level){
    return 6/5*Math.pow(level, 3) - 15*Math.pow(level, 2) + 100*level - 140;
  }
  
  // damage formula
  public static int getDamage(Enemies friend1, int level1, Enemies friend2, int level2, int attackBaseDamage){
    int attack = getOtherStat(friend1.attackBase, level1);
    int defense = getOtherStat(friend2.defenseBase, level2);
    
    double randomMultiplier = Math.random()* 0.15 + 0.85;
    double criticalRandomizer = Math.random();
    double critical = 1;
    
    if( criticalRandomizer < 1/16){
      critical = 1.5;
    }
    
    double modifier = critical * randomMultiplier;
    
    return (int) ((((2 * level1) + 10) / 250) * (attack/defense) * attackBaseDamage * modifier);
  }
  
  // stats formula
  public static int getOtherStat(int baseStat, int level){
    return (((2 * baseStat) * level) / 100) + 5;    
  }
  
  // hp formula
  public static int getHpStat(int baseStat, int level){
    return (((2 * baseStat) * level) / 100) + level + 10;    
  }
}

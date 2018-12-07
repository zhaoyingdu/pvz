package pvz2018;

import java.io.Serializable;

public class GameStatus implements Serializable {


  //private static final long serialVersionUID = 1L;
  int plantIndex = 0;
  int amoIndex = 0;
  int zombieIndex = 0;

  transient int latestRound ;
  transient final int SUNFLOWERCD =3;
  transient final int PEASHOOTERCD =2;
  transient final int WALNUTCD = 4;

  transient final int SUNFLOWERCOST =5;
  transient final int PEASHOOTERCOST =2;
  transient final int WALNUTCOST = 4;

  transient final int attackFreqPeaShooter = 5;


  int gameProgress = 0;
  int money=500;
  int sunflowerCD = 0; //fast
  int peaShooterCD = 0;  //medium
  int walnutCD = 0;  //medium

  int getCost(String plantName){
      switch(plantName){
          case "sunflower":
              return SUNFLOWERCOST;
          case "peaShooter":
              return PEASHOOTERCOST;
          case "walnut":
              return WALNUTCOST;
      }
      throw new Error("plantInCD() plant not found");
  }
   boolean plantInCD(String plantName){
      switch(plantName){
          case "sunflower":
              return sunflowerCD > 0 ? true:false;
              case "peaShooter":
              return peaShooterCD > 0 ? true:false;
              case "walnut":
              return walnutCD > 0 ? true:false;
      }
      throw new Error("plantInCD() plant not found");
  }

  void toggleCD(String plantName,int option){
      switch(plantName){
          case "sunflower":
              sunflowerCD = SUNFLOWERCD*option;
              return;
          case "peaShooter":
              peaShooterCD = PEASHOOTERCD*option;
              return;
          case "walnut":
              walnutCD = WALNUTCD*option;
              
      }
  }
  void decreaseCD(){
      sunflowerCD --;
      peaShooterCD --;
      walnutCD --;
  }
}
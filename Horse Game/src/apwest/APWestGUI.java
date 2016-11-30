package apwest;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author 15gluckj
 */

@SuppressWarnings("serial")
public class APWestGUI extends Applet implements KeyListener {
    JFrame window = new JFrame();
    JPanel mainPanel = new JPanel();
    JPanel top = new JPanel();
    JPanel bot = new JPanel();
    JPanel mid = new JPanel(new GridLayout(28,31));
    JPanel[][][][] sm = new JPanel[28][31][5][4];
    int[][][][] colors = new int[28][31][5][4];
    ArrayList<Seeds> terrain = new ArrayList<Seeds>();
    ArrayList<Horse> pack = new ArrayList<Horse>();
    ArrayList<Cactus> cactusGroup = new ArrayList<Cactus>();
    JPanel left = new JPanel();
    JPanel right = new JPanel();
    JPanel[][] board = new JPanel[28][30];
    int[][] status = new int[84][90];
    BorderLayout border = new BorderLayout();
    int hop = 0;
    int Mdirrection = 0;
    @SuppressWarnings("static-access")
	public APWestGUI() {
         window.add(mainPanel);
         window.addKeyListener(this);
         mainPanel.setLayout(border);
         mainPanel.add(border.NORTH,top);
         mainPanel.add(border.SOUTH,bot);
         mainPanel.add(border.CENTER,mid);
         mainPanel.add(border.WEST,left);
         mainPanel.add(border.EAST,right);
         top.setBackground(Color.black);
         bot.setBackground(Color.black);
         left.setBackground(Color.black);
         right.setBackground(Color.black);
         mid.setBackground(Color.black);
         Random gen = new Random();
                for (int e = 0; e < 9;e ++){
                      boolean check = false;
                      int xs = 0;
                      int ys = 0;
                      while (check == false){
                      xs = gen.nextInt(84);
                      ys = gen.nextInt(90);
                      check = true;
                      if (terrain.size() > 0){
                      for (int r = 0;r <terrain.size();r++){
                          if ((xs == terrain.get(r).getX()) && (ys == terrain.get(r).getY())){
                              check = false;
                          }
                      }
                      }
                      }
                      int z = gen.nextInt(2);
                      terrain.add(new Seeds(xs,ys,z));
                }
          for (int i = 0;i < 84;i++){
             for (int w = 0;w <90;w++){
                 status[i][w] = 0;
                int type = 0;
                int best = 1000;
                int xnum = 0;
                int ynum = 0;
                for (int r = 0;r <terrain.size();r++){
                   xnum =  Math.abs(i - terrain.get(r).getX());
                   ynum =  Math.abs(w - terrain.get(r).getY());
                  if ((xnum + ynum) <= best){
                      type = terrain.get(r).getType();
                      best = (xnum + ynum);
                  }
                }
                if (type == 1){
                   status[i][w] = 1;
                    
                }else if (type == 0){
                    status[i][w] = 2;
                }
             }
          }
         for (int i = 28;i < 56;i++){
             for (int w = 30;w <60;w++){
                board[i - 28][w - 30] = new JPanel(new GridLayout(5,4));
                mid.add(board[i - 28][w - 30]);
                board[i - 28][w - 30].setBackground(Color.gray);
           
                
                for (int g = 0;g < 5;g++){
                   for (int t = 0;t <4;t++){
                       sm[i - 28][w - 30][g][t] = new JPanel();
                       board[i - 28][w - 30].add(sm[i - 28][w - 30][g][t]);
                       colors[i - 28][w - 30][g][t] = 0;
                   }
               }
               

                if (status[i][w] == 2){
                    grassMaker(i - 28,w - 30);
                }else if (status[i][w] == 1){
                    dirtMaker(i - 28,w - 30);
                }
             }
         }
      for (int i = 0;i < 83;i++){
             for (int w = 0;w <89;w++){
                 if (status[i][w] == 1){
                     int dd = gen.nextInt(300);
                      if (dd == 5){
                          boolean check = true;
                          for (int o = 0;o<cactusGroup.size();o++){
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                          }
                          if (status[i + 1][w] != 1 || status[i][w + 1] != 1 || status[i + 1][w + 1] != 1 ){
                              check = false;
                          }
                          if (check == true){
                           cactusGroup.add(new Cactus(i,w));
                          }
                      }
                }
                 for (int b = 0;b < cactusGroup.size();b++){
                  if (cactusGroup.get(b).getX() == i && cactusGroup.get(b).getY() == w ){
                      if (w < 59 && w > 29 && i < 55 && i> 27){
                      cactusMaker(i - 28,w - 30);
                      }
                  }
              }
             }
         }
     
        horse(13,14, Mdirrection);
         window.setVisible(true);
         window.setSize(800,800);
         window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
     public void changeColor(int i,int w,int g,int t,int num){
         if (num == 1){
             sm[i][w][g][t].setBackground(new Color(199,146,100));
         }
          if (num == 2){
              sm[i][w][g][t].setBackground(new Color(120,72,0));
         }
           if (num == 3){
              sm[i][w][g][t].setBackground(new Color (34,139,34));
         }
            if (num == 4){
               sm[i][w][g][t].setBackground(new Color(180,238,180));
         }
             if (num == 5){
               sm[i][w][g][t].setBackground(new Color(160,214,160));
         }
              if (num == 6){
               sm[i][w][g][t].setBackground(new Color(210,247,244));
         }
               if (num == 7){
               sm[i][w][g][t].setBackground(new Color(12,164,181));
         }
                 if (num == 8){
               sm[i][w][g][t].setBackground(new Color(102,51,0));
         }
                   if (num == 9){
               sm[i][w][g][t].setBackground(new Color(139,113,89));
         }
                     if (num == 10){
               sm[i][w][g][t].setBackground(new Color(182,184,153));
         }
               
     }
      public void grassMaker(int x, int y){
        for (int g = 0;g <5;g++){
             for (int t = 0;t <4;t++){
                 Random gen = new Random();
                 int c = gen.nextInt(4);
                if (c == 0 || c == 3){
                    sm[x][y][g][t].setBackground(new Color (34,139,34));
                    colors[x][y][g][t] = 3;
                }else if (c == 1){
                    sm[x][y][g][t].setBackground(new Color(180,238,180));
                    colors[x][y][g][t] = 4;
                }else if (c==2){
                    sm[x][y][g][t].setBackground(new Color(160,214,160));
                    colors[x][y][g][t] = 5;
                }
                       
                
             }
         }
    }
         public void dirtMaker(int x, int y){
        for (int g = 0;g <5;g++){
             for (int t = 0;t <4;t++){
                 Random gen = new Random();
                 int c = gen.nextInt(4);
                if (c == 0 || c == 3){
                    sm[x][y][g][t].setBackground(new Color(199,146,100));
                    colors[x][y][g][t] = 1;
                }if (c == 1 ){
                      sm[x][y][g][t].setBackground(new Color(102,51,0));
                    colors[x][y][g][t] = 8;
                }else if (c == 2){
                    sm[x][y][g][t].setBackground(new Color(120,72,0));
                    colors[x][y][g][t] = 2;
                }
             }
         }
    }
            public void waterMaker(int x, int y){
        for (int g = 0;g <5;g++){
             for (int t = 0;t <4;t++){
                 Random gen = new Random();
                 int c = gen.nextInt(2);
                if (c == 0){
                    sm[x][y][g][t].setBackground(new Color(210,247,244));
                    colors[x][y][g][t] = 6;
                }else{
                    sm[x][y][g][t].setBackground(new Color(12,164,181));
                    colors[x][y][g][t] = 7;
                }
             }
         }
    }
               public void rockMaker(int x, int y){
        for (int g = 0;g <5;g++){
             for (int t = 0;t <4;t++){
                 Random gen = new Random();
                 int c = gen.nextInt(2);
                if (c == 0){
                    sm[x][y][g][t].setBackground(new Color(139,113,89));
                    colors[x][y][g][t] = 9;
                }else{
                    sm[x][y][g][t].setBackground(new Color(182,184,153));
                    colors[x][y][g][t] = 10;
                }
             }
         }
    }
          public void right(){
        for (int i = 0;i < 84;i++){
             for (int w = 0;w < 89;w++){
                 if (status[i][w] == status[i][w + 1]){
                 }else if (status[i][(w + 1)] == 1){
                          
                          status[i][w] = 1;
                     }else if (status[i][w + 1] == 2){ 
                          
                         status[i][w] = 2;
                 }else if (status[i][w + 1] == 3){ 
                          
                         status[i][w] = 3;
                 }else if (status[i][w + 1] == 0){
                     status[i][w] = 0;
                 }else if (status[i][w + 1] == 4){
                     status[i][w] = 4;
                 }
                 if (w < 59 && w > 29){
                     if (i > 27 && i < 56){
                      for (int g = 0;g < 5;g++){
                              for (int t = 0;t <4;t++){
                                 colors[i - 28][w - 30][g][t] = colors[i - 28][w + 1 - 30][g][t];
                                 changeColor((i - 28),(w - 30),g,t,colors[i - 28][w - 30][g][t]);
                              }
                           }
                
                     }     
                 }else if ((w == 59) && (i > 27) && (i < 56)){
                     if (status[i][w] == 1){
                     dirtMaker((i - 28),(w - 30)); 
                     }else if (status[i][w] == 2){
                    grassMaker((i - 28),(w - 30));
                    }else if (status[i][w] == 3){
                        waterMaker((i - 28),(w - 30));
                    }else if (status[i][w] == 4){
                        rockMaker((i - 28),(w - 30));
                    }
                 }
                  
               
             
          

        
        }
             for (int w = 89;w <90;w++){
                 status[i][w] = 0;
             }
          }
        boolean[] Hremove = new boolean[pack.size()];
         for (int q = 0; q < pack.size(); q++){
             Hremove[q] = false;
             if (pack.get(q).getIn() == false){
             int b = pack.get(q).getX();
             int d = pack.get(q).getY();
             d --;
      
             if (d < 0){
                 Hremove[q] = true;
             }
             //System.out.println(q + " " + b + " " + d);
             pack.get(q).setCord(b,d,false);
            if (d < 59 && d > 29 && b < 55 && b > 27){
               // System.out.println("make horse" + " " + q + " " + b + " " + d);
                horse((b - 28),(d - 30),0);
            }
              if (b == 27 && d < 59 && d > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfT((b - 28),(d - 30),0);
            }
              if (b == 55 && d < 59 && d > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfB((b - 28),(d - 30),0);
            }
             if (d == 59 && b < 55 && b > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfL((b - 28),(d - 30),0);
            }
               if (d == 29 && b < 55 && b > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfR((b - 28),(d - 30),0);
            }
               if (d == 29 && b == 27){

                horsehalfBR((b - 28),(d - 30),0);
            }
                 if (d == 29 && b == 55){

                horsehalfTR((b - 28),(d - 30),0);
            }
                  if (d == 59 && b == 27){
 
                horsehalfBL((b - 28),(d - 30),0);
            }
                   if (d == 59 && b == 55){
 
                horsehalfTL((b - 28),(d - 30),0);
            }
             }
         }
           for (int i = 0; i < pack.size(); i ++){
             if (Hremove[i]){
                 pack.remove(i);
             }
         }
        boolean[] remover = new boolean[cactusGroup.size()];
         for (int r = 0;r <cactusGroup.size();r++){
             int q = cactusGroup.get(r).getX();
             int z = cactusGroup.get(r).getY();
             z --;
             remover[r] = false;
             if (z < 0){
                 remover[r] = true;
             }
             cactusGroup.get(r).setCord(q,z);
             if (z < 59 && z > 29 && q < 55 && q> 27){
                cactusMaker((q - 28),(z - 30));
            }
              if (q == 27 && z < 59 && z > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfTop((q - 28),(z - 30));
            }
              if (q == 55 && z < 59 && z > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfBot((q - 28),(z - 30));
            }
             if (z == 59 && q < 55 && q > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfLeft((q - 28),(z - 30));
            }
               if (z == 29 && q < 55 && q > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfRight((q - 28),(z - 30));
            }
               if (z == 29 && q == 27){

                cactusBR((q - 28),(z - 30));
            }
                 if (z == 29 && q == 55){

                cactusTR((q - 28),(z - 30));
            }
                  if (z == 59 && q == 27){
 
                cactusBL((q - 28),(z - 30));
            }
                   if (z == 59 && q == 55){
 
                cactusTL((q - 28),(z - 30));
            }
         } 
         for (int i = 0; i < cactusGroup.size(); i ++){
             if (remover[i]){
                 cactusGroup.remove(i);
             }
         }
         boolean[] remove = new boolean[terrain.size()];
         for (int r = 0;r <terrain.size();r++){
             int q = terrain.get(r).getX();
             int z = terrain.get(r).getY();
             z --;
             remove[r] = false;
             if (z < 0){
                 remove[r] = true;
             }
             terrain.get(r).setCord(q,z);
//              if (q < 56 && z < 60 && z > 29 && q > 27){
//                      for (int g = 0;g <5;g++){
//                         for (int t = 0;t <4;t++){
//                             if (terrain.get(r).getType() == 0){
//                             sm[q - 28][z - 30][g][t].setBackground(Color.green);
//                             }
//                             if (terrain.get(r).getType() == 1){
//                             sm[q - 28][z - 30][g][t].setBackground(Color.gray);
//                             }
//                         }
//                       }
//              }
         } 
         for (int i = 0; i < terrain.size(); i ++){
             if (remove[i]){
                 terrain.remove(i);
             }
         }
         
           boolean re = false;
           for (int q = 28;q < 56; q ++ ){
            if (status[q][60] == 0){
            re = true;
              }
           }
           if (re == true){
              ////////////////////////////////////////
                   Random gen = new Random();
                  for (int e = 0; e < 3;e ++){
                      boolean check = false;
                      int xs = 0;
                      int ys = 0;
                      while (check == false){
                      xs = gen.nextInt(84);
                      ys = gen.nextInt(30) + 60;
                      check = true;
                      if (terrain.size() > 0){
                      for (int r = 0;r <terrain.size();r++){
                          if ((xs == terrain.get(r).getX()) && (ys == terrain.get(r).getY())){
                              check = false;
                          }
                      }
                      }
                      }
                      int z = gen.nextInt(4);
                      terrain.add(new Seeds(xs,ys,z));
                }
                for (int i = 0;i < 84;i++){
             for (int w = 60;w <90;w++){
                 status[i][w] = 0;
                int type = 0;
                int best = 1000;
                int xnum = 0;
                int ynum = 0;
                for (int r = 0;r <terrain.size();r++){
                   xnum =  Math.abs(i - terrain.get(r).getX());
                   ynum =  Math.abs(w - terrain.get(r).getY());
                   if (terrain.get(r).getType() == 3){
                       xnum = xnum + 10;
                       ynum = ynum + 10;
                   }
                  if ((xnum + ynum) <= best){
                      type = terrain.get(r).getType();
                      best = (xnum + ynum);
                  }
                }
                if (type == 1){
                   status[i][w] = 1;
                } if (type == 2){
                    status[i][w] = 3;
                }if (type == 0){
                   status[i][w] = 2; 
                }if (type == 3){
                   status[i][w] = 4; 
                }
             }
          }
    for (int i = 0;i < 84;i++){
             for (int w = 60;w <90;w++){
                    if (i < 83 && w < 89){
                if (w < 89 && w > 0){
                    ////////////Horse/////////
                       int hh = 0;
                hh = gen.nextInt(1000);
                if (hh == 321 && status[i][w] != 3 && status[i + 1][w] != 3 && status[i][w + 1] != 3 && status[i + 1][w + 1] != 3 && status[i][w] != 4 && status[i + 1][w] != 4 && status[i][w + 1] != 4 && status[i + 1][w + 1] != 4){
                    //System.out.println("Horse");
                    pack.add(new Horse(i,w));
                }
                      ////////Catctus/////////
                 if (status[i][w] == 1){
                     int dd = gen.nextInt(300);
                      if (dd == 5){
                          boolean check = true;
                          for (int o = 0;o<cactusGroup.size();o++){
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() - 1 == w ){
                                    check = false;
                                }
                          }
                          if (status[i + 1][w] != 1 || status[i][w + 1] != 1 || status[i + 1][w + 1] != 1 ){
                              check = false;
                          }
                          if (check == true){
                           cactusGroup.add(new Cactus(i,w));
                          }
                      }
                }
                }
              }
             }
             }
                      
          }
        //////////////////////////////////////////////////////
    }
            public void left(){
        for (int i = 0;i < 84;i++){
             for (int w = 89;w > 0;w--){
                 if (status[i][w] == status[i][w - 1]){
                  
                 }else if (status[i][(w - 1)] == 1){
                          
                          status[i][w] = 1;
                     }else if (status[i][w - 1] == 2){ 
                          
                         status[i][w] = 2;
                 }else if (status[i][w - 1] == 3){ 
                          
                         status[i][w] = 3;
                 }else if (status[i][w - 1] == 0){
                     status[i][w] = 0;
                 }else if (status[i][w - 1] == 4){
                     status[i][w] = 4;
                 }
                 if (w < 60 && w > 30){
                     if (i > 27 && i < 56){
                      for (int g = 0;g < 5;g++){
                              for (int t = 0;t <4;t++){
                                 colors[i - 28][w - 30][g][t] = colors[i - 28][w - 1 - 30][g][t];
                                 changeColor((i - 28),(w - 30),g,t,colors[i - 28][w - 30][g][t]);
                              }
                           }
                
                     }     
                 }else if ((w == 30) && (i > 27 && i < 56)){
                 
                     if (status[i][w] == 1){
                     dirtMaker((i - 28),(w - 30)); 
                     }else if (status[i][w] == 2){
                    grassMaker((i - 28),(w - 30));
                    }else if (status[i][w] == 3){
                        waterMaker((i - 28),(w - 30));
                    }else if (status[i][w] == 4){
                        rockMaker((i - 28),(w - 30));
                    }
                 }
                  
               
             
          

        
        }
             for (int w = 0;w <1;w++){
                 status[i][w] = 0;
             }
          }
         boolean[] Gremove = new boolean[pack.size()];
         for (int q = 0; q < pack.size(); q++){
             Gremove[q] = false;
             if (pack.get(q).getIn() == false){
             int b = pack.get(q).getX();
             int d = pack.get(q).getY();
             d ++;
 
             if (d > 89){
                 Gremove[q] = true;
             }
             //System.out.println(q + " " + b + " " + d);
             pack.get(q).setCord(b,d,false);
            if (d < 59 && d > 29 && b < 55 && b > 27){
               // System.out.println("make horse" + " " + q + " " + b + " " + d);
                horse((b - 28),(d - 30),0);
            }
              if (b == 27 && d < 59 && d > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfT((b - 28),(d - 30),0);
            }
              if (b == 55 && d < 59 && d > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfB((b - 28),(d - 30),0);
            }
             if (d == 29 && b < 55 && b > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfR((b - 28),(d - 30),0);
            }
              if (d == 59 && b < 55 && b > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfL((b - 28),(d - 30),0);
            }
                if (d == 29 && b == 27){
                
                horsehalfBR((b - 28),(d - 30),0);
            }
                 if (d == 29 && b == 55){
                
                horsehalfTR((b - 28),(d - 30),0);
            }
                  if (d == 59 && b == 27){
               
                horsehalfBL((b - 28),(d - 30),0);
            }
                   if (d == 59 && b == 55){
            
                horsehalfTL((b - 28),(d - 30),0);
            } 
             }
         }
           for (int i = 0; i < pack.size(); i ++){
             if (Gremove[i]){
                 pack.remove(i);
             }
           }
         boolean[] remover = new boolean[cactusGroup.size()];
         for (int r = 0;r <cactusGroup.size();r++){
             int q = cactusGroup.get(r).getX();
             int z = cactusGroup.get(r).getY();
             z ++;
             remover[r] = false;
             if (z > 89){
                 remover[r] = true;
             }
             cactusGroup.get(r).setCord(q,z);
             if (z < 59 && z > 29 && q < 55 && q> 27){
                cactusMaker((q - 28),(z - 30));
            }
                     if (q == 27 && z < 59 && z > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfTop((q - 28),(z - 30));
            }
              if (q == 55 && z < 59 && z > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfBot((q - 28),(z - 30));
            }
             if (z == 59 && q < 55 && q > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfLeft((q - 28),(z - 30));
            }
               if (z == 29 && q < 55 && q > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfRight((q - 28),(z - 30));
            }
               if (z == 29 && q == 27){

                cactusBR((q - 28),(z - 30));
            }
                 if (z == 29 && q == 55){

                cactusTR((q - 28),(z - 30));
            }
                  if (z == 59 && q == 27){
 
                cactusBL((q - 28),(z - 30));
            }
                   if (z == 59 && q == 55){
 
                cactusTL((q - 28),(z - 30));
            }
         } 
         for (int i = 0; i < cactusGroup.size(); i ++){
             if (remover[i]){
                 cactusGroup.remove(i);
             }
         }
           boolean[] remove = new boolean[terrain.size()];
         for (int r = 0;r <terrain.size();r++){
             int q = terrain.get(r).getX();
             int z = terrain.get(r).getY();
             
             z ++;
             remove[r] = false;
             if (z > 89){
                 remove[r] = true;
             }

             terrain.get(r).setCord(q,z);
//              if (q < 56 && z < 60 && z > 29 && q > 27){
//                      for (int g = 0;g <5;g++){
//                         for (int t = 0;t <4;t++){
//                             if (terrain.get(r).getType() == 0){
//                             sm[q - 28][z - 30][g][t].setBackground(Color.green);
//                             }
//                             if (terrain.get(r).getType() == 1){
//                             sm[q - 28][z - 30][g][t].setBackground(Color.gray);
//                             }
//                         }
//                       }
//              }
         }  
          for (int i = 0; i < terrain.size(); i ++){
             if (remove[i]){
                 terrain.remove(i);
             }
         }
         
            boolean re = false;
           for (int q = 28;q < 56; q ++ ){
            if (status[q][29] == 0){
            re = true;
              }
           }
           if (re == true){
                    Random gen = new Random();
                  for (int e = 0; e < 3;e ++){
                      boolean check = false;
                      int xs = 0;
                      int ys = 0;
                      while (check == false){
                      xs = gen.nextInt(84);
                      ys = gen.nextInt(30);
                      check = true;
                      if (terrain.size() > 0){
                      for (int r = 0;r <terrain.size();r++){
                          if ((xs == terrain.get(r).getX()) && (ys == terrain.get(r).getY())){
                              check = false;
                          }
                      }
                      }
                      }
                      int z = gen.nextInt(4);
                      terrain.add(new Seeds(xs,ys,z));
                }
                for (int i = 0;i < 84;i++){
             for (int w = 0;w <30;w++){
                 status[i][w] = 0;
                int type = 0;
                int best = 1000;
                int xnum = 0;
                int ynum = 0;
                 for (int r = 0;r <terrain.size();r++){
                   xnum =  Math.abs(i - terrain.get(r).getX());
                   ynum =  Math.abs(w - terrain.get(r).getY());
                   if (terrain.get(r).getType() == 3){
                       xnum = xnum + 10;
                       ynum = ynum + 10;
                   }
                  if ((xnum + ynum) <= best){
                      type = terrain.get(r).getType();
                      best = (xnum + ynum);
                  }
                }
                if (type == 1){
                   status[i][w] = 1;
                } if (type == 2){
                    status[i][w] = 3;
                }if (type == 0){
                   status[i][w] = 2; 
                }if (type == 3){
                   status[i][w] = 4; 
                }
             }
          }
                    for (int i = 0;i < 84;i++){
               for (int w = 0;w <30;w++){
                    if (i < 83 && w < 89){
                if (w < 89 && w > 0){
                    ////////////Horse/////////
                       int hh = 0;
                hh = gen.nextInt(1000);
                if (hh == 321 && status[i][w] != 3 && status[i + 1][w] != 3 && status[i][w + 1] != 3 && status[i + 1][w + 1] != 3 && status[i][w] != 4 && status[i + 1][w] != 4 && status[i][w + 1] != 4 && status[i + 1][w + 1] != 4){
                    //System.out.println("Horse");
                    pack.add(new Horse(i,w));
                }
                      ////////Catctus/////////
                 if (status[i][w] == 1){
                     int dd = gen.nextInt(300);
                      if (dd == 5){
                          boolean check = true;
                          for (int o = 0;o<cactusGroup.size();o++){
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() - 1 == w ){
                                    check = false;
                                }
                          }
                          if (status[i + 1][w] != 1 || status[i][w + 1] != 1 || status[i + 1][w + 1] != 1 ){
                              check = false;
                          }
                          if (check == true){
                           cactusGroup.add(new Cactus(i,w));
                          }
                      }
                }
                }
              }
             }
             }
          }
    }
            
        public void up(){
             for (int i = 83;i > 0;i--){
             for (int w = 0;w < 90;w++){
                 if (status[i][w] == status[i - 1][w]){
                  
                 }else if (status[i - 1][w] == 1){
                          
                          status[i][w] = 1;
                     }else if (status[i - 1][w] == 2){ 
                          
                         status[i][w] = 2;
                 }else if (status[i - 1][w] == 3){ 
                          
                         status[i][w] = 3;
                 }else if (status[i - 1][w] == 0){
                     status[i][w] = 0;
                 }else if (status[i - 1][w] == 4){
                     status[i][w] = 4;
                 }
                 if (w < 60 && w > 29){
                     if (i < 56 && i > 28){
                      for (int g = 0;g < 5;g++){
                              for (int t = 0;t <4;t++){
                                 colors[i - 28][w - 30][g][t] = colors[i - 1 - 28][w- 30][g][t];
                                 changeColor((i - 28),(w - 30),g,t,colors[i - 28][w - 30][g][t]);
                              }
                           }
                
                     }else if ((i == 28) && (w < 60) && (w > 29)){
                 
                      if (status[i][w] == 1){
                     dirtMaker((i - 28),(w - 30)); 
                     }else if (status[i][w] == 2){
                    grassMaker((i - 28),(w - 30));
                    }else if (status[i][w] == 3){
                        waterMaker((i - 28),(w - 30));
                    }else if (status[i][w] == 4){
                        rockMaker((i - 28),(w - 30));
                    }
                 }     
                 }
                  
               
             
          

        
        }
          }
         for (int w = 89;w > 0;w--){
                 for (int i = 0;i <1;i++){
                 status[i][w] = 0;
             }
         }
          boolean[] Dremove = new boolean[pack.size()];
         for (int q = 0; q < pack.size(); q++){
             Dremove[q] = false;
             if (pack.get(q).getIn() == false){
             int b = pack.get(q).getX();
             int d = pack.get(q).getY();
             b ++;
       
             if (b > 85){
                 Dremove[q] = true;
             }
             //System.out.println(q + " " + b + " " + d);
             pack.get(q).setCord(b,d,false);
            if (d < 59 && d > 29 && b < 55 && b > 27){
               // System.out.println("make horse" + " " + q + " " + b + " " + d);
                horse((b - 28),(d - 30),0);
            }
             if (b == 27 && d < 59 && d > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfT((b - 28),(d - 30),0);
            }
              if (b == 55 && d < 59 && d > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfB((b - 28),(d - 30),0);
            }
             if (d == 29 && b < 55 && b > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfR((b - 28),(d - 30),0);
            }
              if (d == 59 && b < 55 && b > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfL((b - 28),(d - 30),0);
            } 
                 if (d == 29 && b == 27){
              
                horsehalfBR((b - 28),(d - 30),0);
            }
                 if (d == 29 && b == 55){
              
                horsehalfTR((b - 28),(d - 30),0);
            }
                  if (d == 59 && b == 27){
           
                horsehalfBL((b - 28),(d - 30),0);
            }
                   if (d == 59 && b == 55){
           
                horsehalfTL((b - 28),(d - 30),0);
            }
             }
         }
           for (int i = 0; i < pack.size(); i ++){
             if (Dremove[i]){
                 pack.remove(i);
             }
           }
          boolean[] remover = new boolean[cactusGroup.size()];
         for (int r = 0;r <cactusGroup.size();r++){
             int q = cactusGroup.get(r).getX();
             int z = cactusGroup.get(r).getY();
             q ++;
             remover[r] = false;
             if (q > 83){
                 remover[r] = true;
             }
             cactusGroup.get(r).setCord(q,z);
             if (z < 59 && z > 29 && q < 55 && q> 27){
                cactusMaker((q - 28),(z - 30));
            }
                     if (q == 27 && z < 59 && z > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfTop((q - 28),(z - 30));
            }
              if (q == 55 && z < 59 && z > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfBot((q - 28),(z - 30));
            }
             if (z == 59 && q < 55 && q > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfLeft((q - 28),(z - 30));
            }
               if (z == 29 && q < 55 && q > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfRight((q - 28),(z - 30));
            }
               if (z == 29 && q == 27){

                cactusBR((q - 28),(z - 30));
            }
                 if (z == 29 && q == 55){

                cactusTR((q - 28),(z - 30));
            }
                  if (z == 59 && q == 27){
 
                cactusBL((q - 28),(z - 30));
            }
                   if (z == 59 && q == 55){
 
                cactusTL((q - 28),(z - 30));
            }
         } 
         for (int i = 0; i < cactusGroup.size(); i ++){
             if (remover[i]){
                 cactusGroup.remove(i);
             }
         }
           boolean[] remove = new boolean[terrain.size()];
         for (int r = 0;r <terrain.size();r++){
             int q = terrain.get(r).getX();
             int z = terrain.get(r).getY();
             
             q++;
             remove[r] = false;
             if (q > 83){
                 remove[r] = true;
             }

             terrain.get(r).setCord(q,z);
//           if (q < 56 && z < 60 && z > 29 && q > 27){
//                      for (int g = 0;g <5;g++){
//                         for (int t = 0;t <4;t++){
//                             if (terrain.get(r).getType() == 0){
//                             sm[q - 28][z - 30][g][t].setBackground(Color.green);
//                             }
//                             if (terrain.get(r).getType() == 1){
//                             sm[q - 28][z - 30][g][t].setBackground(Color.gray);
//                             }
//                         }
//                       }
//              }
         }  
          for (int i = 0; i < terrain.size(); i ++){
             if (remove[i]){
                 terrain.remove(i);
             }
         }
        
          boolean re = false;
           for (int q = 30;q < 59; q ++ ){
            if (status[27][q] == 0){
            re = true;
              }
           }
           if (re == true){
                    Random gen = new Random();
                  for (int e = 0; e < 3;e ++){
                      boolean check = false;
                      int xs = 0;
                      int ys = 0;
                      while (check == false){
                      xs = gen.nextInt(28);
                      ys = gen.nextInt(90);
                      check = true;
                      if (terrain.size() > 0){
                      for (int r = 0;r <terrain.size();r++){
                          if ((xs == terrain.get(r).getX()) && (ys == terrain.get(r).getY())){
                              check = false;
                          }
                      }
                      }
                      }
                      int z = gen.nextInt(4);
                      terrain.add(new Seeds(xs,ys,z));
                }
                for (int i = 0;i < 28;i++){
             for (int w = 0;w <90;w++){
                 status[i][w] = 0;
                int type = 0;
                int best = 1000;
                int xnum = 0;
                int ynum = 0;
                 for (int r = 0;r <terrain.size();r++){
                   xnum =  Math.abs(i - terrain.get(r).getX());
                   ynum =  Math.abs(w - terrain.get(r).getY());
                   if (terrain.get(r).getType() == 3){
                       xnum = xnum + 10;
                       ynum = ynum + 10;
                   }
                  if ((xnum + ynum) <= best){
                      type = terrain.get(r).getType();
                      best = (xnum + ynum);
                  }
                }
                if (type == 1){
                   status[i][w] = 1;
                } if (type == 2){
                    status[i][w] = 3;
                }if (type == 0){
                   status[i][w] = 2; 
                }if (type == 3){
                   status[i][w] = 4; 
                }
             }
          }
                for (int i = 0;i < 28;i++){
             for (int w = 0;w <90;w++){
                    if (i < 83 && w < 89){
                if (w < 89 && w > 0){
                    ////////////Horse/////////
                       int hh = 0;
                hh = gen.nextInt(1000);
                if (hh == 321 && status[i][w] != 3 && status[i + 1][w] != 3 && status[i][w + 1] != 3 && status[i + 1][w + 1] != 3 && status[i][w] != 4 && status[i + 1][w] != 4 && status[i][w + 1] != 4 && status[i + 1][w + 1] != 4){
                    //System.out.println("Horse");
                    pack.add(new Horse(i,w));
                }
                      ////////Catctus/////////
                 if (status[i][w] == 1){
                     int dd = gen.nextInt(300);
                      if (dd == 5){
                          boolean check = true;
                          for (int o = 0;o<cactusGroup.size();o++){
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() - 1 == w ){
                                    check = false;
                                }
                          }
                          if (status[i + 1][w] != 1 || status[i][w + 1] != 1 || status[i + 1][w + 1] != 1 ){
                              check = false;
                          }
                          if (check == true){
                           cactusGroup.add(new Cactus(i,w));
                          }
                      }
                }
                }
              }
             }
             }   
          }
    }
     
        public void down(){
             for (int i = 0;i < 83;i++){
             for (int w = 0;w < 90;w++){
                 if (status[i][w] == status[i + 1][w]){
                  
                 }else if (status[i + 1][w] == 1){
                          
                          status[i][w] = 1;
                     }else if (status[i + 1][w] == 2){ 
                          
                         status[i][w] = 2;
                 }else if (status[i + 1][w] == 3){ 
                          
                         status[i][w] = 3;
                 }else if (status[i + 1][w] == 0){
                     status[i][w] = 0;
                 }else if (status[i + 1][w] == 4){
                     status[i][w] = 4;
                 }
                 if (w < 60 && w > 29){
                     if (i < 55 && i > 27){
                      for (int g = 0;g < 5;g++){
                              for (int t = 0;t <4;t++){
                                 colors[i - 28][w - 30][g][t] = colors[i + 1 - 28][w- 30][g][t];
                                 changeColor((i - 28),(w - 30),g,t,colors[i - 28][w - 30][g][t]);
                              }
                           }
                
                     }else if ((i == 55) && (w < 60) && (w > 29)){
                 
                      if (status[i][w] == 1){
                     dirtMaker((i - 28),(w - 30)); 
                     }else if (status[i][w] == 2){
                    grassMaker((i - 28),(w - 30));
                    }else if (status[i][w] == 3){
                        waterMaker((i - 28),(w - 30));
                    }else if (status[i][w] == 4){
                        rockMaker((i - 28),(w - 30));
                    }
                 }     
                 }
                  
               
             
          

        
        }
          }
         for (int w = 89;w > 0;w--){
                 for (int i = 83;i <84;i++){
                 status[i][w] = 0;
             }
         }
           boolean[] Zremove = new boolean[pack.size()];
         for (int q = 0; q < pack.size(); q++){
             Zremove[q] = false;
             if (pack.get(q).getIn() == false){
            // if (pack.get(q).getIn() == false)
             int b = pack.get(q).getX();
             int d = pack.get(q).getY();
             b --;
          
             if (b < 0){
                 Zremove[q] = true;
             }
             //System.out.println(q + " " + b + " " + d);
             pack.get(q).setCord(b,d,false);
            if (d < 59 && d > 29 && b < 55 && b > 27){
               // System.out.println("make horse" + " " + q + " " + b + " " + d);
                horse((b - 28),(d - 30),0);
            }
             if (b == 27 && d < 59 && d > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfT((b - 28),(d - 30),0);
            }
              if (b == 55 && d < 59 && d > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfB((b - 28),(d - 30),0);
            }
              if (d == 29 && b < 55 && b > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfR((b - 28),(d - 30),0);
            }
              if (d == 59 && b < 55 && b > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                horsehalfL((b - 28),(d - 30),0);
            }
                 if (d == 29 && b == 27){
                horsehalfBR((b - 28),(d - 30),0);
            }
                 if (d == 29 && b == 55){

                horsehalfTR((b - 28),(d - 30),0);
            }
                  if (d == 59 && b == 27){
 
                horsehalfBL((b - 28),(d - 30),0);
            }
                   if (d == 59 && b == 55){
                horsehalfTL((b - 28),(d - 30),0);
            }
         }
         }
           for (int i = 0; i < pack.size(); i ++){
             if (Zremove[i]){
                 pack.remove(i);
             }
           }
          boolean[] remover = new boolean[cactusGroup.size()];
         for (int r = 0;r <cactusGroup.size();r++){
             int q = cactusGroup.get(r).getX();
             int z = cactusGroup.get(r).getY();
             q --;
             remover[r] = false;
             if (q < 0){
                 remover[r] = true;
             }
             cactusGroup.get(r).setCord(q,z);
             if (z < 59 && z > 29 && q < 55 && q> 27){
                cactusMaker((q - 28),(z - 30));
            }
                     if (q == 27 && z < 59 && z > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfTop((q - 28),(z - 30));
            }
              if (q == 55 && z < 59 && z > 29){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfBot((q - 28),(z - 30));
            }
             if (z == 59 && q < 55 && q > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfLeft((q - 28),(z - 30));
            }
               if (z == 29 && q < 55 && q > 27){
                //System.out.println("make horse half" + " " + q + " " + b + " " + d);
                cactushalfRight((q - 28),(z - 30));
            }
               if (z == 29 && q == 27){

                cactusBR((q - 28),(z - 30));
            }
                 if (z == 29 && q == 55){

                cactusTR((q - 28),(z - 30));
            }
                  if (z == 59 && q == 27){
 
                cactusBL((q - 28),(z - 30));
            }
                   if (z == 59 && q == 55){
 
                cactusTL((q - 28),(z - 30));
            }
         } 
         for (int i = 0; i < cactusGroup.size(); i ++){
             if (remover[i]){
                 cactusGroup.remove(i);
             }
         }
           boolean[] remove = new boolean[terrain.size()];
         for (int r = 0;r <terrain.size();r++){
             int q = terrain.get(r).getX();
             int z = terrain.get(r).getY();
             
             q --;
             remove[r] = false;
             if (q < 0){
                 remove[r] = true;
             }

             terrain.get(r).setCord(q,z);
//               if (q < 56 && z < 60 && z > 29 && q > 27){
//                      for (int g = 0;g <5;g++){
//                         for (int t = 0;t <4;t++){
//                             if (terrain.get(r).getType() == 0){
//                             sm[q - 28][z - 30][g][t].setBackground(Color.green);
//                             }
//                             if (terrain.get(r).getType() == 1){
//                             sm[q - 28][z - 30][g][t].setBackground(Color.gray);
//                             }
//                         }
//                       }
//              }
         }  
          for (int i = 0; i < terrain.size(); i ++){
             if (remove[i]){
                 terrain.remove(i);
             }
         }
         
          boolean re = false;
           for (int q = 30;q < 59; q ++ ){
            if (status[56][q] == 0){
            re = true;
              }
           }
           if (re == true){
                    Random gen = new Random();
                  for (int e = 0; e < 3;e ++){
                      boolean check = false;
                      int xs = 0;
                      int ys = 0;
                      while (check == false){
                      xs = gen.nextInt(28) + 56;
                      ys = gen.nextInt(90);
                      check = true;
                      if (terrain.size() > 0){
                      for (int r = 0;r <terrain.size();r++){
                          if ((xs == terrain.get(r).getX()) && (ys == terrain.get(r).getY())){
                              check = false;
                          }
                      }
                      }
                      }
                      int z = gen.nextInt(4);
                      terrain.add(new Seeds(xs,ys,z));
                }
                for (int i = 56;i < 84;i++){
             for (int w = 0;w <90;w++){
                 status[i][w] = 0;
                int type = 0;
                int best = 1000;
                int xnum = 0;
                int ynum = 0;
                  for (int r = 0;r <terrain.size();r++){
                   xnum =  Math.abs(i - terrain.get(r).getX());
                   ynum =  Math.abs(w - terrain.get(r).getY());
                   if (terrain.get(r).getType() == 3){
                       xnum = xnum + 10;
                       ynum = ynum + 10;
                   }
                  if ((xnum + ynum) <= best){
                      type = terrain.get(r).getType();
                      best = (xnum + ynum);
                  }
                }
                if (type == 1){
                   status[i][w] = 1;
                } if (type == 2){
                    status[i][w] = 3;
                }if (type == 0){
                   status[i][w] = 2; 
                }if (type == 3){
                   status[i][w] = 4; 
                }
             }
          }
                for (int i = 56;i < 84;i++){
             for (int w = 0;w <90;w++){
                    if (i < 83 && w < 89){
                if (w < 89 && w > 0){
                    ////////////Horse/////////
                       int hh = 0;
                hh = gen.nextInt(1000);
                if (hh == 321 && status[i][w] != 3 && status[i + 1][w] != 3 && status[i][w + 1] != 3 && status[i + 1][w + 1] != 3 && status[i][w] != 4 && status[i + 1][w] != 4 && status[i][w + 1] != 4 && status[i + 1][w + 1] != 4){
                    //System.out.println("Horse");
                    pack.add(new Horse(i,w));
                }
                      ////////Catctus/////////
                 if (status[i][w] == 1){
                     int dd = gen.nextInt(300);
                      if (dd == 5){
                          boolean check = true;
                          for (int o = 0;o<cactusGroup.size();o++){
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() == i && cactusGroup.get(o).getY() + 1 == w ){
                                    check = false;
                                }
                                if (cactusGroup.get(o).getX() + 1 == i && cactusGroup.get(o).getY() - 1 == w ){
                                    check = false;
                                }
                          }
                          if (status[i + 1][w] != 1 || status[i][w + 1] != 1 || status[i + 1][w + 1] != 1 ){
                              check = false;
                          }
                          if (check == true){
                           cactusGroup.add(new Cactus(i,w));
                          }
                      }
                }
                }
              }
             }
             }    
          }
    }
        public void horse(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            }
            sm[x][p][4][w].setBackground(Color.black);
            sm[x][p][4][r].setBackground(Color.black);
            sm[x][p][4][e].setBackground(Color.black);
             
            sm[x][t][4][q].setBackground(Color.black);
            sm[x][t][2][w].setBackground(Color.black);
            sm[x][t][3][w].setBackground(Color.black);
            sm[x][t][4][w].setBackground(Color.black);
            sm[x][t][1][r].setBackground(Color.black);
            sm[x][t][2][r].setBackground(Color.black);
            sm[x][t][3][r].setBackground(Color.black);
            sm[x][t][4][r].setBackground(Color.black);
            sm[x][t][2][e].setBackground(Color.black);
            sm[x][t][3][e].setBackground(Color.black);
            
            sm[x + 1][p][0][q].setBackground(Color.black);
            sm[x + 1][p][1][q].setBackground(Color.black);
            sm[x + 1][p][2][q].setBackground(Color.black);
            sm[x + 1][p][3][q].setBackground(Color.black);
            sm[x + 1][p][0][w].setBackground(Color.black);
            sm[x + 1][p][1][w].setBackground(Color.black);
            sm[x + 1][p][2][w].setBackground(Color.black);
            sm[x + 1][p][3][w].setBackground(Color.black);
            sm[x + 1][p][4][w].setBackground(Color.black);
            sm[x + 1][p][0][r].setBackground(Color.black);
            sm[x + 1][p][1][r].setBackground(Color.black);
            sm[x + 1][p][2][r].setBackground(Color.black);
            sm[x + 1][p][0][e].setBackground(Color.black);
            sm[x + 1][p][1][e].setBackground(Color.black);
            
            sm[x + 1][t][0][q].setBackground(Color.black);
            sm[x + 1][t][1][q].setBackground(Color.black);
            sm[x + 1][t][2][q].setBackground(Color.black);
            sm[x + 1][t][0][w].setBackground(Color.black);
            sm[x + 1][t][1][w].setBackground(Color.black);
            sm[x + 1][t][2][w].setBackground(Color.black);
            sm[x + 1][t][3][w].setBackground(Color.black);
            sm[x + 1][t][4][w].setBackground(Color.black);
            sm[x + 1][t][0][r].setBackground(Color.black);
            sm[x + 1][t][1][r].setBackground(Color.black);
            
            /////////////////////////////////////////
            
            
        }
     public void horse2(int x, int y, int z){
    
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            }
            sm[x][p][4][q].setBackground(Color.black);
            sm[x][p][3][w].setBackground(Color.black);
            sm[x][p][4][w].setBackground(Color.black);
            sm[x][p][3][r].setBackground(Color.black);
            sm[x][p][4][r].setBackground(Color.black);
            sm[x][p][3][e].setBackground(Color.black);
            sm[x][p][4][e].setBackground(Color.black);
        
             
            sm[x][t][3][q].setBackground(Color.black);
            sm[x][t][4][q].setBackground(Color.black);
            sm[x][t][1][w].setBackground(Color.black);
            sm[x][t][2][w].setBackground(Color.black);
            sm[x][t][3][w].setBackground(Color.black);
            sm[x][t][4][w].setBackground(Color.black);
            sm[x][t][0][r].setBackground(Color.black);
            sm[x][t][1][r].setBackground(Color.black);
            sm[x][t][2][r].setBackground(Color.black);
            sm[x][t][3][r].setBackground(Color.black);
            sm[x][t][4][r].setBackground(Color.black);
            sm[x][t][1][e].setBackground(Color.black);
            sm[x][t][2][e].setBackground(Color.black);
            
            sm[x + 1][p][0][q].setBackground(Color.black);
            sm[x + 1][p][1][q].setBackground(Color.black);
            sm[x + 1][p][2][q].setBackground(Color.black);
            sm[x + 1][p][0][w].setBackground(Color.black);
            sm[x + 1][p][1][w].setBackground(Color.black);
            sm[x + 1][p][2][w].setBackground(Color.black);
            sm[x + 1][p][3][w].setBackground(Color.black);
            sm[x + 1][p][0][r].setBackground(Color.black);
            sm[x + 1][p][1][r].setBackground(Color.black);
            sm[x + 1][p][0][e].setBackground(Color.black);
            
            sm[x + 1][t][0][q].setBackground(Color.black);
            sm[x + 1][t][1][q].setBackground(Color.black);
            sm[x + 1][t][0][w].setBackground(Color.black);
            sm[x + 1][t][1][w].setBackground(Color.black);
            sm[x + 1][t][2][w].setBackground(Color.black);
            sm[x + 1][t][3][w].setBackground(Color.black);
            sm[x + 1][t][0][r].setBackground(Color.black);
        }
     public void horsehalfL(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
            sm[x][p][4][w].setBackground(Color.black);
            sm[x][p][4][r].setBackground(Color.black);
            sm[x][p][4][e].setBackground(Color.black);
             
            sm[x + 1][p][0][q].setBackground(Color.black);
            sm[x + 1][p][1][q].setBackground(Color.black);
            sm[x + 1][p][2][q].setBackground(Color.black);
            sm[x + 1][p][3][q].setBackground(Color.black);
            sm[x + 1][p][0][w].setBackground(Color.black);
            sm[x + 1][p][1][w].setBackground(Color.black);
            sm[x + 1][p][2][w].setBackground(Color.black);
            sm[x + 1][p][3][w].setBackground(Color.black);
            sm[x + 1][p][4][w].setBackground(Color.black);
            sm[x + 1][p][0][r].setBackground(Color.black);
            sm[x + 1][p][1][r].setBackground(Color.black);
            sm[x + 1][p][2][r].setBackground(Color.black);
            sm[x + 1][p][0][e].setBackground(Color.black);
            sm[x + 1][p][1][e].setBackground(Color.black);
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            
           
            
            sm[x][t][4][q].setBackground(Color.black);
            sm[x][t][2][w].setBackground(Color.black);
            sm[x][t][3][w].setBackground(Color.black);
            sm[x][t][4][w].setBackground(Color.black);
            sm[x][t][1][r].setBackground(Color.black);
            sm[x][t][2][r].setBackground(Color.black);
            sm[x][t][3][r].setBackground(Color.black);
            sm[x][t][4][r].setBackground(Color.black);
            sm[x][t][2][e].setBackground(Color.black);
            sm[x][t][3][e].setBackground(Color.black);
            
            sm[x + 1][t][0][q].setBackground(Color.black);
            sm[x + 1][t][1][q].setBackground(Color.black);
            sm[x + 1][t][2][q].setBackground(Color.black);
            sm[x + 1][t][0][w].setBackground(Color.black);
            sm[x + 1][t][1][w].setBackground(Color.black);
            sm[x + 1][t][2][w].setBackground(Color.black);
            sm[x + 1][t][3][w].setBackground(Color.black);
            sm[x + 1][t][4][w].setBackground(Color.black);
            sm[x + 1][t][0][r].setBackground(Color.black);
            sm[x + 1][t][1][r].setBackground(Color.black);
            }
            /////////////////////////////////////////
            
            
        }
     public void horsehalfR(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y + 1;
             t = y + 1;
            sm[x][t][4][q].setBackground(Color.black);
            sm[x][t][2][w].setBackground(Color.black);
            sm[x][t][3][w].setBackground(Color.black);
            sm[x][t][4][w].setBackground(Color.black);
            sm[x][t][1][r].setBackground(Color.black);
            sm[x][t][2][r].setBackground(Color.black);
            sm[x][t][3][r].setBackground(Color.black);
            sm[x][t][4][r].setBackground(Color.black);
            sm[x][t][2][e].setBackground(Color.black);
            sm[x][t][3][e].setBackground(Color.black);
            
            sm[x + 1][t][0][q].setBackground(Color.black);
            sm[x + 1][t][1][q].setBackground(Color.black);
            sm[x + 1][t][2][q].setBackground(Color.black);
            sm[x + 1][t][0][w].setBackground(Color.black);
            sm[x + 1][t][1][w].setBackground(Color.black);
            sm[x + 1][t][2][w].setBackground(Color.black);
            sm[x + 1][t][3][w].setBackground(Color.black);
            sm[x + 1][t][4][w].setBackground(Color.black);
            sm[x + 1][t][0][r].setBackground(Color.black);
            sm[x + 1][t][1][r].setBackground(Color.black);
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y + 1;
            
            sm[x][p][4][w].setBackground(Color.black);
            sm[x][p][4][r].setBackground(Color.black);
            sm[x][p][4][e].setBackground(Color.black);
             
            sm[x + 1][p][0][q].setBackground(Color.black);
            sm[x + 1][p][1][q].setBackground(Color.black);
            sm[x + 1][p][2][q].setBackground(Color.black);
            sm[x + 1][p][3][q].setBackground(Color.black);
            sm[x + 1][p][0][w].setBackground(Color.black);
            sm[x + 1][p][1][w].setBackground(Color.black);
            sm[x + 1][p][2][w].setBackground(Color.black);
            sm[x + 1][p][3][w].setBackground(Color.black);
            sm[x + 1][p][4][w].setBackground(Color.black);
            sm[x + 1][p][0][r].setBackground(Color.black);
            sm[x + 1][p][1][r].setBackground(Color.black);
            sm[x + 1][p][2][r].setBackground(Color.black);
            sm[x + 1][p][0][e].setBackground(Color.black);
            sm[x + 1][p][1][e].setBackground(Color.black);
            
        
            }
            
            /////////////////////////////////////////
            
            
        }
     public void horsehalfT(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
             sm[x + 1][p][0][q].setBackground(Color.black);
            sm[x + 1][p][1][q].setBackground(Color.black);
            sm[x + 1][p][2][q].setBackground(Color.black);
            sm[x + 1][p][3][q].setBackground(Color.black);
            sm[x + 1][p][0][w].setBackground(Color.black);
            sm[x + 1][p][1][w].setBackground(Color.black);
            sm[x + 1][p][2][w].setBackground(Color.black);
            sm[x + 1][p][3][w].setBackground(Color.black);
            sm[x + 1][p][4][w].setBackground(Color.black);
            sm[x + 1][p][0][r].setBackground(Color.black);
            sm[x + 1][p][1][r].setBackground(Color.black);
            sm[x + 1][p][2][r].setBackground(Color.black);
            sm[x + 1][p][0][e].setBackground(Color.black);
            sm[x + 1][p][1][e].setBackground(Color.black);
            
            sm[x + 1][t][0][q].setBackground(Color.black);
            sm[x + 1][t][1][q].setBackground(Color.black);
            sm[x + 1][t][2][q].setBackground(Color.black);
            sm[x + 1][t][0][w].setBackground(Color.black);
            sm[x + 1][t][1][w].setBackground(Color.black);
            sm[x + 1][t][2][w].setBackground(Color.black);
            sm[x + 1][t][3][w].setBackground(Color.black);
            sm[x + 1][t][4][w].setBackground(Color.black);
            sm[x + 1][t][0][r].setBackground(Color.black);
            sm[x + 1][t][1][r].setBackground(Color.black);
            
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            
            sm[x + 1][p][0][q].setBackground(Color.black);
            sm[x + 1][p][1][q].setBackground(Color.black);
            sm[x + 1][p][2][q].setBackground(Color.black);
            sm[x + 1][p][3][q].setBackground(Color.black);
            sm[x + 1][p][0][w].setBackground(Color.black);
            sm[x + 1][p][1][w].setBackground(Color.black);
            sm[x + 1][p][2][w].setBackground(Color.black);
            sm[x + 1][p][3][w].setBackground(Color.black);
            sm[x + 1][p][4][w].setBackground(Color.black);
            sm[x + 1][p][0][r].setBackground(Color.black);
            sm[x + 1][p][1][r].setBackground(Color.black);
            sm[x + 1][p][2][r].setBackground(Color.black);
            sm[x + 1][p][0][e].setBackground(Color.black);
            sm[x + 1][p][1][e].setBackground(Color.black);
            
            sm[x + 1][t][0][q].setBackground(Color.black);
            sm[x + 1][t][1][q].setBackground(Color.black);
            sm[x + 1][t][2][q].setBackground(Color.black);
            sm[x + 1][t][0][w].setBackground(Color.black);
            sm[x + 1][t][1][w].setBackground(Color.black);
            sm[x + 1][t][2][w].setBackground(Color.black);
            sm[x + 1][t][3][w].setBackground(Color.black);
            sm[x + 1][t][4][w].setBackground(Color.black);
            sm[x + 1][t][0][r].setBackground(Color.black);
            sm[x + 1][t][1][r].setBackground(Color.black);
            
        
            }
     }
      public void horsehalfB(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
            sm[x][t][4][q].setBackground(Color.black);
            sm[x][t][2][w].setBackground(Color.black);
            sm[x][t][3][w].setBackground(Color.black);
            sm[x][t][4][w].setBackground(Color.black);
            sm[x][t][1][r].setBackground(Color.black);
            sm[x][t][2][r].setBackground(Color.black);
            sm[x][t][3][r].setBackground(Color.black);
            sm[x][t][4][r].setBackground(Color.black);
            sm[x][t][2][e].setBackground(Color.black);
            sm[x][t][3][e].setBackground(Color.black);
            
            sm[x][p][4][w].setBackground(Color.black);
            sm[x][p][4][r].setBackground(Color.black);
            sm[x][p][4][e].setBackground(Color.black);
            
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            
            sm[x][p][4][w].setBackground(Color.black);
            sm[x][p][4][r].setBackground(Color.black);
            sm[x][p][4][e].setBackground(Color.black);
             
            sm[x][t][4][q].setBackground(Color.black);
            sm[x][t][2][w].setBackground(Color.black);
            sm[x][t][3][w].setBackground(Color.black);
            sm[x][t][4][w].setBackground(Color.black);
            sm[x][t][1][r].setBackground(Color.black);
            sm[x][t][2][r].setBackground(Color.black);
            sm[x][t][3][r].setBackground(Color.black);
            sm[x][t][4][r].setBackground(Color.black);
            sm[x][t][2][e].setBackground(Color.black);
            sm[x][t][3][e].setBackground(Color.black);
            
        
            }
     }
      public void horsehalfBR(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
            
            sm[x + 1][t][0][q].setBackground(Color.black);
            sm[x + 1][t][1][q].setBackground(Color.black);
            sm[x + 1][t][2][q].setBackground(Color.black);
            sm[x + 1][t][0][w].setBackground(Color.black);
            sm[x + 1][t][1][w].setBackground(Color.black);
            sm[x + 1][t][2][w].setBackground(Color.black);
            sm[x + 1][t][3][w].setBackground(Color.black);
            sm[x + 1][t][4][w].setBackground(Color.black);
            sm[x + 1][t][0][r].setBackground(Color.black);
            sm[x + 1][t][1][r].setBackground(Color.black);
            
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            sm[x + 1][p][0][q].setBackground(Color.black);
            sm[x + 1][p][1][q].setBackground(Color.black);
            sm[x + 1][p][2][q].setBackground(Color.black);
            sm[x + 1][p][3][q].setBackground(Color.black);
            sm[x + 1][p][0][w].setBackground(Color.black);
            sm[x + 1][p][1][w].setBackground(Color.black);
            sm[x + 1][p][2][w].setBackground(Color.black);
            sm[x + 1][p][3][w].setBackground(Color.black);
            sm[x + 1][p][4][w].setBackground(Color.black);
            sm[x + 1][p][0][r].setBackground(Color.black);
            sm[x + 1][p][1][r].setBackground(Color.black);
            sm[x + 1][p][2][r].setBackground(Color.black);
            sm[x + 1][p][0][e].setBackground(Color.black);
            sm[x + 1][p][1][e].setBackground(Color.black);
            
        
            }
     }
       public void horsehalfBL(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
             sm[x + 1][p][0][q].setBackground(Color.black);
            sm[x + 1][p][1][q].setBackground(Color.black);
            sm[x + 1][p][2][q].setBackground(Color.black);
            sm[x + 1][p][3][q].setBackground(Color.black);
            sm[x + 1][p][0][w].setBackground(Color.black);
            sm[x + 1][p][1][w].setBackground(Color.black);
            sm[x + 1][p][2][w].setBackground(Color.black);
            sm[x + 1][p][3][w].setBackground(Color.black);
            sm[x + 1][p][4][w].setBackground(Color.black);
            sm[x + 1][p][0][r].setBackground(Color.black);
            sm[x + 1][p][1][r].setBackground(Color.black);
            sm[x + 1][p][2][r].setBackground(Color.black);
            sm[x + 1][p][0][e].setBackground(Color.black);
            sm[x + 1][p][1][e].setBackground(Color.black);
            
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            
            sm[x + 1][t][0][q].setBackground(Color.black);
            sm[x + 1][t][1][q].setBackground(Color.black);
            sm[x + 1][t][2][q].setBackground(Color.black);
            sm[x + 1][t][0][w].setBackground(Color.black);
            sm[x + 1][t][1][w].setBackground(Color.black);
            sm[x + 1][t][2][w].setBackground(Color.black);
            sm[x + 1][t][3][w].setBackground(Color.black);
            sm[x + 1][t][4][w].setBackground(Color.black);
            sm[x + 1][t][0][r].setBackground(Color.black);
            sm[x + 1][t][1][r].setBackground(Color.black);
            
        
            }
     }
        public void horsehalfTR(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
            sm[x][t][4][q].setBackground(Color.black);
            sm[x][t][2][w].setBackground(Color.black);
            sm[x][t][3][w].setBackground(Color.black);
            sm[x][t][4][w].setBackground(Color.black);
            sm[x][t][1][r].setBackground(Color.black);
            sm[x][t][2][r].setBackground(Color.black);
            sm[x][t][3][r].setBackground(Color.black);
            sm[x][t][4][r].setBackground(Color.black);
            sm[x][t][2][e].setBackground(Color.black);
            sm[x][t][3][e].setBackground(Color.black);
            
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            
            sm[x][p][4][w].setBackground(Color.black);
            sm[x][p][4][r].setBackground(Color.black);
            sm[x][p][4][e].setBackground(Color.black);
            
        
            }
     }
         public void horsehalfTL(int x , int y, int z){
          
            // q = 0 w = 1 e = 4 r = 5
            int q;
            int w;
            int e;
            int r; 
            int p;
            int t;
            if (z == 0){
             q = 0;
             w = 1;
             r = 2;
             e = 3;
             p = y;
             t = y + 1;
            sm[x][p][4][w].setBackground(Color.black);
            sm[x][p][4][r].setBackground(Color.black);
            sm[x][p][4][e].setBackground(Color.black);
            
            }else{
             q = 3;
             w = 2;
             r = 1;
             e = 0;
             p = y + 1;
             t = y;
            
            sm[x][t][4][q].setBackground(Color.black);
            sm[x][t][2][w].setBackground(Color.black);
            sm[x][t][3][w].setBackground(Color.black);
            sm[x][t][4][w].setBackground(Color.black);
            sm[x][t][1][r].setBackground(Color.black);
            sm[x][t][2][r].setBackground(Color.black);
            sm[x][t][3][r].setBackground(Color.black);
            sm[x][t][4][r].setBackground(Color.black);
            sm[x][t][2][e].setBackground(Color.black);
            sm[x][t][3][e].setBackground(Color.black);
            
        
            }
     }
         public void cactusMaker(int x,int y){
              sm[x][y][3][1].setBackground(Color.green);
              sm[x][y][4][1].setBackground(Color.green);
              sm[x][y][0][3].setBackground(Color.green);
              sm[x][y][1][3].setBackground(Color.green);
              sm[x][y][2][3].setBackground(Color.green);
              sm[x][y][3][3].setBackground(Color.green);
              sm[x][y][4][3].setBackground(Color.green);
             
              sm[x + 1][y][0][1].setBackground(Color.green);
              sm[x + 1][y][1][1].setBackground(Color.green);
              sm[x + 1][y][2][2].setBackground(Color.green);
              sm[x + 1][y][0][3].setBackground(Color.green);
              sm[x + 1][y][1][3].setBackground(Color.green);
              sm[x + 1][y][2][3].setBackground(Color.green);
              sm[x + 1][y][3][3].setBackground(Color.green);
              sm[x + 1][y][4][3].setBackground(Color.green);
              
              sm[x][y + 1][1][2].setBackground(Color.green);
              sm[x][y + 1][2][2].setBackground(Color.green);
              sm[x][y + 1][3][2].setBackground(Color.green);
              sm[x][y + 1][4][2].setBackground(Color.green);
              sm[x][y + 1][0][0].setBackground(Color.green);
              sm[x][y + 1][1][0].setBackground(Color.green);
              sm[x][y + 1][2][0].setBackground(Color.green);
              sm[x][y + 1][3][0].setBackground(Color.green);
              sm[x][y + 1][4][0].setBackground(Color.green);
             
              sm[x + 1][y + 1][0][1].setBackground(Color.green);
              sm[x + 1][y + 1][0][0].setBackground(Color.green);
              sm[x + 1][y + 1][1][0].setBackground(Color.green);
              sm[x + 1][y + 1][2][0].setBackground(Color.green);
              sm[x + 1][y + 1][3][0].setBackground(Color.green);
              sm[x + 1][y + 1][4][0].setBackground(Color.green);
         }
          public void cactushalfRight(int x,int y){
              sm[x][y + 1][1][2].setBackground(Color.green);
              sm[x][y + 1][2][2].setBackground(Color.green);
              sm[x][y + 1][3][2].setBackground(Color.green);
              sm[x][y + 1][4][2].setBackground(Color.green);
              sm[x][y + 1][0][0].setBackground(Color.green);
              sm[x][y + 1][1][0].setBackground(Color.green);
              sm[x][y + 1][2][0].setBackground(Color.green);
              sm[x][y + 1][3][0].setBackground(Color.green);
              sm[x][y + 1][4][0].setBackground(Color.green);
             
              sm[x + 1][y + 1][0][1].setBackground(Color.green);
              sm[x + 1][y + 1][0][0].setBackground(Color.green);
              sm[x + 1][y + 1][1][0].setBackground(Color.green);
              sm[x + 1][y + 1][2][0].setBackground(Color.green);
              sm[x + 1][y + 1][3][0].setBackground(Color.green);
              sm[x + 1][y + 1][4][0].setBackground(Color.green);
         }
           public void cactushalfLeft(int x,int y){
              sm[x][y][3][1].setBackground(Color.green);
              sm[x][y][4][1].setBackground(Color.green);
              sm[x][y][0][3].setBackground(Color.green);
              sm[x][y][1][3].setBackground(Color.green);
              sm[x][y][2][3].setBackground(Color.green);
              sm[x][y][3][3].setBackground(Color.green);
              sm[x][y][4][3].setBackground(Color.green);
             
              sm[x + 1][y][0][1].setBackground(Color.green);
              sm[x + 1][y][1][1].setBackground(Color.green);
              sm[x + 1][y][2][2].setBackground(Color.green);
              sm[x + 1][y][0][3].setBackground(Color.green);
              sm[x + 1][y][1][3].setBackground(Color.green);
              sm[x + 1][y][2][3].setBackground(Color.green);
              sm[x + 1][y][3][3].setBackground(Color.green);
              sm[x + 1][y][4][3].setBackground(Color.green);
           }
            public void cactushalfTop(int x,int y){
              sm[x + 1][y][0][1].setBackground(Color.green);
              sm[x + 1][y][1][1].setBackground(Color.green);
              sm[x + 1][y][2][2].setBackground(Color.green);
              sm[x + 1][y][0][3].setBackground(Color.green);
              sm[x + 1][y][1][3].setBackground(Color.green);
              sm[x + 1][y][2][3].setBackground(Color.green);
              sm[x + 1][y][3][3].setBackground(Color.green);
              sm[x + 1][y][4][3].setBackground(Color.green);
             
              sm[x + 1][y + 1][0][1].setBackground(Color.green);
              sm[x + 1][y + 1][0][0].setBackground(Color.green);
              sm[x + 1][y + 1][1][0].setBackground(Color.green);
              sm[x + 1][y + 1][2][0].setBackground(Color.green);
              sm[x + 1][y + 1][3][0].setBackground(Color.green);
              sm[x + 1][y + 1][4][0].setBackground(Color.green);
         }
             public void cactushalfBot(int x,int y){
              sm[x][y][3][1].setBackground(Color.green);
              sm[x][y][4][1].setBackground(Color.green);
              sm[x][y][0][3].setBackground(Color.green);
              sm[x][y][1][3].setBackground(Color.green);
              sm[x][y][2][3].setBackground(Color.green);
              sm[x][y][3][3].setBackground(Color.green);
              sm[x][y][4][3].setBackground(Color.green);
             
              sm[x][y + 1][1][2].setBackground(Color.green);
              sm[x][y + 1][2][2].setBackground(Color.green);
              sm[x][y + 1][3][2].setBackground(Color.green);
              sm[x][y + 1][4][2].setBackground(Color.green);
              sm[x][y + 1][0][0].setBackground(Color.green);
              sm[x][y + 1][1][0].setBackground(Color.green);
              sm[x][y + 1][2][0].setBackground(Color.green);
              sm[x][y + 1][3][0].setBackground(Color.green);
              sm[x][y + 1][4][0].setBackground(Color.green);
         }
               public void cactusBR(int x,int y){
              sm[x + 1][y + 1][0][1].setBackground(Color.green);
              sm[x + 1][y + 1][0][0].setBackground(Color.green);
              sm[x + 1][y + 1][1][0].setBackground(Color.green);
              sm[x + 1][y + 1][2][0].setBackground(Color.green);
              sm[x + 1][y + 1][3][0].setBackground(Color.green);
              sm[x + 1][y + 1][4][0].setBackground(Color.green);
         }
               public void cactusBL(int x,int y){
              sm[x + 1][y][0][1].setBackground(Color.green);
              sm[x + 1][y][1][1].setBackground(Color.green);
              sm[x + 1][y][2][2].setBackground(Color.green);
              sm[x + 1][y][0][3].setBackground(Color.green);
              sm[x + 1][y][1][3].setBackground(Color.green);
              sm[x + 1][y][2][3].setBackground(Color.green);
              sm[x + 1][y][3][3].setBackground(Color.green);
              sm[x + 1][y][4][3].setBackground(Color.green);
         }
               public void cactusTL(int x,int y){
              sm[x][y][3][1].setBackground(Color.green);
              sm[x][y][4][1].setBackground(Color.green);
              sm[x][y][0][3].setBackground(Color.green);
              sm[x][y][1][3].setBackground(Color.green);
              sm[x][y][2][3].setBackground(Color.green);
              sm[x][y][3][3].setBackground(Color.green);
              sm[x][y][4][3].setBackground(Color.green);
         }
               public void cactusTR(int x,int y){
              sm[x][y + 1][1][2].setBackground(Color.green);
              sm[x][y + 1][2][2].setBackground(Color.green);
              sm[x][y + 1][3][2].setBackground(Color.green);
              sm[x][y + 1][4][2].setBackground(Color.green);
              sm[x][y + 1][0][0].setBackground(Color.green);
              sm[x][y + 1][1][0].setBackground(Color.green);
              sm[x][y + 1][2][0].setBackground(Color.green);
              sm[x][y + 1][3][0].setBackground(Color.green);
              sm[x][y + 1][4][0].setBackground(Color.green);
         }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
          boolean run = false;
        if (e.getKeyCode() == KeyEvent.VK_UP){
           boolean good = true;
           if (status[12 + 28][14 + 30] == 3 || status[12 + 28][15 + 30] == 3 || status[12 + 28][14 + 30] == 4 || status[12 + 28][15 + 30] == 4){
               good = false;
           }
           for (int i = 0; i < pack.size(); i++){
                   if (pack.get(i).getIn() == true){
                       if (status[pack.get(i).getX() - 1][pack.get(i).getY()] == 4 || status[pack.get(i).getX() - 1][pack.get(i).getY() + 1] == 4){
                            good = false;
                       }
                   }
               }
           if (good == true){
               up();
               run = true;
           }
           
         }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
             
               boolean good = true;
            if (status[15 + 28][14 + 30] == 3 || status[15 + 28][15 + 30] == 3 || status[15 + 28][14 + 30] == 4 || status[15 + 28][15 + 30] == 4){
               good = false;
           }
           for (int i = 0; i < pack.size(); i++){
                   if (pack.get(i).getIn() == true){
                       if (status[pack.get(i).getX() + 2][pack.get(i).getY()] == 4 || status[pack.get(i).getX() + 2][pack.get(i).getY() + 1] == 4){
                            good = false;
                       }
                   }
               }
           if (good == true){
               down();
               run = true;
           }
        }
    
         
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
           
              boolean good = true;
             if (status[13 + 28][13 + 30] == 3 || status[14 + 28][13 + 30] == 3 || status[13 + 28][13 + 30] == 4 || status[14 + 28][13 + 30] == 4){
               good = false;
           }
           for (int i = 0; i < pack.size(); i++){
                   if (pack.get(i).getIn() == true){
                       if (status[pack.get(i).getX()][pack.get(i).getY() - 1] == 4 || status[pack.get(i).getX() + 1][pack.get(i).getY() - 1] == 4){
                            good = false;
                       }
                   }
               }
           if (good == true){
                Mdirrection = 1;
                left();
                run = true;
           }
          }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            
        
               boolean good = true;
              if (status[13 + 28][16 + 30] == 3 || status[14 + 28][16 + 30] == 3 || status[13 + 28][16 + 30] == 4 || status[14 + 28][16 + 30] == 4){
               good = false;
           }
           for (int i = 0; i < pack.size(); i++){
                   if (pack.get(i).getIn() == true){
                       if (status[pack.get(i).getX()][pack.get(i).getY() + 2] == 4 || status[pack.get(i).getX() + 1][pack.get(i).getY() + 2] == 4){
                            good = false;
                       }
                   }
               }
           if (good == true){
                Mdirrection = 0;
                right();
               run = true;
           }
          }
          
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){   
            if (run == true){
         if (hop == 0){
           horse2(13,14,Mdirrection);
           for (int t = 0; t < pack.size(); t ++){
                    if (pack.get(t).getIn() == true){
                    horse2((pack.get(t).getX() - 28),(pack.get(t).getY() -30 ),Mdirrection);
               }
          }
           hop = 1;
           }else{
           horse(13,14, Mdirrection);
           for (int t = 0; t < pack.size(); t ++){
                    if (pack.get(t).getIn() == true){
                         horse((pack.get(t).getX() - 28),(pack.get(t).getY() - 30),Mdirrection);
               }
          }
           hop = 0;   
           }
         for (int r = 0; r < pack.size(); r ++){
                if (pack.get(r).getIn() == false){
                         if (pack.get(r).getX() == (13 + 28)){
                                 if ((pack.get(r).getY() == 16 + 30) || (pack.get(r).getY() == 12 + 30)){
                                      boolean xx = true;
                                          for (int b = 0; b < pack.size(); b ++){
                                             
                                              if ((pack.get(b).getX() == pack.get(r).getX()) && (pack.get(b).getY() == pack.get(r).getY()) && r != b){
                                                  xx = false;
                                              }
                                              }
                                          if (pack.get(r).getX() == 13 + 28 && pack.get(r).getY() == 14 + 30){
                                                  xx = false;
                                              }
                                                if (xx == true){
                                                    pack.get(r).setCord(pack.get(r).getX(),pack.get(r).getY(),true);
                                                    horse(pack.get(r).getX() - 28,pack.get(r).getY() - 30,0);
                                                }
                                          
                                  }
                             }
                          if (pack.get(r).getY() == (14 + 30)){
                                 if ((pack.get(r).getX() == 15 + 28) || (pack.get(r).getX() == 11 + 28)){
                                      boolean xx = true;
                                          for (int b = 0; b < pack.size(); b ++){
                                              
                                              if ((pack.get(b).getX() == pack.get(r).getX()) && (pack.get(b).getY() == pack.get(r).getY())&& r != b){
                                                      xx = false;
                                                   }
                                              }
                                           if (pack.get(r).getX() == 13 + 28 && pack.get(r).getY() == 14 + 30){
                                                  xx = false;
                                              }
                                                if (xx == true){
                                                    pack.get(r).setCord(pack.get(r).getX(),pack.get(r).getY(),true);
                                                    horse(pack.get(r).getX() - 28,pack.get(r).getY() - 30,0);
                                                }
                                          
                                  }
                             }
                    }
             if (pack.get(r).getIn() == true){
                for (int t = 0; t < pack.size(); t ++){
                    if (pack.get(t).getIn() == false){
                             if (pack.get(r).getX() == pack.get(t).getX()){
                                 if ((pack.get(r).getY() == (pack.get(t).getY() + 2)) || (pack.get(r).getY() == (pack.get(t).getY() - 2))){
                                      boolean xx = true;
                                      for (int b = 0; b < pack.size(); b ++){
                                          
                                              if ((pack.get(b).getX() == pack.get(t).getX()) && (pack.get(b).getY() == pack.get(t).getY())&& t != b){
                                                  xx = false;
                                              }
                                              }
                                       if (pack.get(t).getX() == 13 + 28 && pack.get(t).getY() == 14 + 30){
                                                  xx = false;
                                              }
                                            if (xx == true){
                                                   pack.get(t).setCord(pack.get(t).getX(),pack.get(t).getY(),true);
                                                   horse(pack.get(t).getX() - 28,pack.get(t).getY() - 30,0);
                                              
                                            }
                                  }
                             }
                             if (pack.get(r).getY() == pack.get(t).getY()){
                                  if ((pack.get(r).getX() == (pack.get(t).getX() + 2)) || (pack.get(r).getX() == (pack.get(t).getX() - 2))){
                                       boolean xx = true;
                                       for (int b = 0; b < pack.size(); b ++){
                                              
                                              if ((pack.get(b).getX() == pack.get(t).getX()) && (pack.get(b).getY() == pack.get(t).getY())&& t != b){
                                                  xx = false;
                                              }
                                             
                                              }
                                        if (pack.get(t).getX() == 13 + 28 && pack.get(t).getY() == 14 + 30){
                                                  xx = false;
                                              }
                                               if (xx == true){
                                                   pack.get(t).setCord(pack.get(t).getX(),pack.get(t).getY(),true);
                                                   horse(pack.get(t).getX() - 28,pack.get(t).getY() - 30,0);
                                               }
                                       
                                  }
                             }
                       }
                   } 
             }
           
              
         }
       
             boolean[] Oremove = new boolean[pack.size()];
         for (int i = 0; i < pack.size();i++){
                 Oremove[i] = false;
                 int x = pack.get(i).getX();
                 int y = pack.get(i).getY();
                 if (x < 83 && x > 0 && y > 0 && y < 89){
                 if (status[x][y] == 3 || status[x + 1][y] == 3 || status[x][y + 1] == 3 || status[x + 1][y + 1] == 3){
                     Oremove[i] = true;
             }
           }
         }
           for (int i = 0; i < pack.size();i++){
               if (Oremove[i] == true){
                if (pack.get(i).getIn() == true){
                     int x = pack.get(i).getX();
                     int y = pack.get(i).getY();
                      for (int g = 0;g < 5;g++){
                              for (int t = 0;t <4;t++){
                                 changeColor((x - 28),(y - 30),g,t,colors[x - 28][y - 30][g][t]);
                                 changeColor((x - 28),(y - 29),g,t,colors[x - 28][y - 29][g][t]);
                                 changeColor((x - 27),(y - 30),g,t,colors[x - 27][y - 30][g][t]);
                                 changeColor((x - 27),(y - 29),g,t,colors[x - 27][y - 29][g][t]);
                              }
                    }
                 }
                     pack.remove(i);
               }
            }
            }
            
        }
      
        // for (int r = 0; r < terrain.size(); r ++){
         //System.out.println(r + " " + terrain.get(r).getX() + " " + terrain.get(r).getY());
       // }
        mainPanel.updateUI();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}

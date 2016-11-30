/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apwest;

/**
 *
 * @author Jake
 */
public class Cactus {
    int x;
    int y;
    public Cactus(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setCord(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}

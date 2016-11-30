/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apwest;

/**
 *
 * @author 15gluckj
 */
public class Seeds {
    int x;
    int y;
    int type;
    public Seeds(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
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
    public int getType(){
        return type;
    }
}

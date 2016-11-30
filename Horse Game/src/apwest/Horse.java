/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apwest;

/**
 *
 * @author 15gluckj
 */
public class Horse {
    int x;
    int y;
    boolean in;
    public Horse (int x,int y){
        in = false;
        this.x = x;
        this.y = y;
    }
     public void setCord(int x, int y, boolean in){
        this.x = x;
        this.y = y;
        this.in = in;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean getIn(){
        return in;
    }
}

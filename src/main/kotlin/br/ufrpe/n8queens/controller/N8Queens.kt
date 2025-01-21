package br.ufrpe.n8queens.controller

import br.ufrpe.n8queens.model.Row
import kotlin.math.abs
import kotlin.math.max

class N8Queens {
    companion object {
        fun isSafe(root: Row, row: Int, col: Int): Boolean {
            var i = 1;
            var node: Row? = root;
            while(node != null){
                if(node.j == -1) {
                    break
                }
                if(node.j == col || abs(node.j - col) == abs(i - row)){
                    return false;
                }
                i++;
                node = node.next;
            }
            return true;
        }
        fun search(root: Row): Int{
            var found = false;

            fun start(node: Row, depth: Int): Int{
                if(node.next != null) {
                    val ret = start(node.next!!, depth+1)
                    if(found) return ret;
                    if(depth == 1 && node.j == 8){
                        node.j = -1;
                    }
                    node.next = null;
                };
                if(depth == 8){
                    for(j in max(node.j+1, 1)..8){
                        if(isSafe(root, depth, j)){
                            node.j = j;
                            found = true;
                            break;
                        }
                    }
                    return depth;
                }
                for(j in max(1, node.j+1)..8){
                    if(isSafe(root, depth, j)){
                        node.j = j;
                        node.next = Row();
                        //start(node.next!!, depth+1);
                        //Thread.sleep(BoardController.getSliderValue());
                        found = true;
                        return depth;
                    }
                }
                return -1;
            }
            return start(root, 1);
        }
    }

}
import stanford.karel.SuperKarel;
public class Homework extends SuperKarel {
    int steps = 0;
    int beepers= 0 ;
    int width = 1;
    int hight = 1;
    public void run(){
        int[] dimensions = getDimensions();
        if( (dimensions[0] == 1 && dimensions[1] == 2)||(dimensions[0] == 1 && dimensions[1] == 1) ||  (dimensions[0] == 2 && dimensions[1] == 1)){
            printSteps();
        }else if((dimensions[0] == dimensions[1]) && ((dimensions[0] != 2) && (dimensions[1] != 2)) ){
            forSymmetricMap();
        }else{
            forAsymmetricMap(dimensions);
        }
    }
    public void printSteps(){
        System.out.println("Number of steps : " + steps
                +"\nnumber of beepers : "+ beepers);
    }
    public void moveAndCount(){
        while(!frontIsBlocked()){
            move();
            steps++;
        }
    }
    public void putBeepersAndCount(){
        while (frontIsClear()){
            if(noBeepersPresent()){
                putBeeper();
                beepers++;
            }
            move();
            steps++;
        }
    }
    public void turnBack(){
        turnAround();
        moveAndCount();
    }
    public void forAsymmetricMap(int[] dimensions){
        width = dimensions[0];
        hight = dimensions[1];
        if(width == 2 && hight == 2){
            createTwoByTwoChambers();
        } else if((width == 1 || hight == 1) && ((hight <= 4 && hight >= 3) || (width <= 4 && width >= 3))){
            createOneByNChambers(dimensions);
        } else if((width == 1 && ((hight <= 6) && (hight >= 5))) || hight == 1 && ((width <= 6) && (width >= 5))){
            createOneByNChambersLarge(dimensions);
        } else if(width == 3 && hight == 2){
            createThreeByTwoChambers();
        } else if (hight == 3 && width == 2) {
            createTwoByThreeChambers();
        } else if((width == 1 && hight >= 7) || (hight == 1 && width >= 7)){
            createLongChambers(dimensions);
        } else if((width == 2 && hight >= 7) || (hight == 2 && width >= 7)){
            createTwoByNChambers(dimensions);
        } else if((width % 2 != 0 && width >= 3) && (hight % 2 != 0 && hight >= 3)){
            createOddChambers(dimensions);
        } else if((width % 2 == 0 && width >= 4) && (hight % 2 == 0 && hight >= 4)){
            createEvenChambers(dimensions);
        } else {
            createEvenOnOddChambers(dimensions);
        }
    }
    public void forSymmetricMap(){
        steps = 0;
        putBeeper();beepers++;
        while(!frontIsBlocked()){
            move();
            steps++;
            turnLeft();move();
            steps++;
            if(!beepersPresent()){
                putBeeper();beepers++;
            }
            turnRight();
        }
        turnAround();moveAndCount();
        turnAround();putBeeper();beepers++;
        while(!frontIsBlocked()){
            move();
            steps++;
            turnRight();
            move();
            steps++;
            if(!beepersPresent()){
                putBeeper();beepers++;
            }
            turnLeft();
        }
        turnAround();moveAndCount();
        turnAround();printSteps();
    }
    public int[] getDimensions(){

        while(!frontIsBlocked()){
            move();
            steps++;
            hight++;
        }
        turnLeft();
        while(!frontIsBlocked()){
            move();
            steps++;
            width++;
        }
        if(width == hight){
            turnLeft();
            while(!frontIsBlocked()){
                move();
                steps++;
                turnLeft();move();turnRight();
            }
            turnAround();
        } else {
            turnAround();moveAndCount();turnAround();
            turnLeft();moveAndCount();turnAround();
        }
        int[] dimensions = {width, hight};

        return dimensions;
    }
    public void createTwoByTwoChambers(){
        moveAndCount();putBeeper();beepers++;turnLeft();
        moveAndCount();turnLeft();moveAndCount();
        putBeeper();beepers++;turnLeft();moveAndCount();
        printSteps();
    }
    public void createOneByNChambers(int[] dimensions){
        int colCounter = 0;
        if(dimensions[0] > dimensions[1]){
            turnLeft();
        }
        while(frontIsClear()){
            if (colCounter % 2 != 0) {
                putBeeper();beepers++;
            }
            move();
            steps++;
            colCounter++;
        }
        if(colCounter == 3){
            putBeeper();beepers++;
        }
        turnBack();
        printSteps();
    }
    public void createOneByNChambersLarge(int[] dimensions){
        int colCounter = 1;
        if(dimensions[0] > dimensions[1]){
            turnLeft();
        }

        while(frontIsClear()){
            if(colCounter % 2 != 0){
                putBeeper();beepers++;
            }
            move();
            steps++;
            colCounter++;
        }
        if(colCounter == 5){
            putBeeper();beepers++;
        }
        printSteps();
    }
    public void createThreeByTwoChambers(){
        putBeeper();turnLeft();beepers++;
        move();turnRight();move();
        putBeeper();beepers++;turnLeft();move();
        turnLeft();move();putBeeper();beepers++;
        turnLeft();moveAndCount();printSteps();
    }
    public void createTwoByThreeChambers(){
        putBeeper();beepers++;turnLeft();move();
        turnRight();move();putBeeper();beepers++;
        move();turnRight();move();
        putBeeper();beepers++;turnRight();moveAndCount();
        printSteps();
    }
    public void createLongChambers(int[] dimensions){
        int noColRow = 0;
        int div = 0;
        if(dimensions[0] < dimensions[1]){
            div = (dimensions[1] + 1) / 4;
            noColRow = dimensions[1];
        } else {
            turnLeft();
            div = (dimensions[0] + 1) / 4;
            noColRow = dimensions[0];
        }
        while(div * 4 <= noColRow) {
            putBeeper();beepers++;move();
            steps++;
            noColRow--;
        }
        while(frontIsClear()){
            int freq = div - 1;
            while(freq-- > 0 && frontIsClear()){
                move();
                steps++;
            }
            if(frontIsClear()){
                putBeeper();beepers++;move();
                steps++;
            }
        }
        printSteps();
    }
    public void createTwoByNChambers(int[] dimensions){
        int swap = dimensions[1];
        if(dimensions[0] > dimensions[1]){
            swap= dimensions[0];
            move();
        }
        createLongChambers(dimensions);
        turnLeft();
        move();
        steps++;
        if(dimensions[0] < dimensions[1]){
            turnLeft();
        }
        createLongChambers(dimensions);turnRight();moveAndCount();
        steps++;
        printSteps();
    }
    public void createOddChambers(int[] dimensions){
        int width = (dimensions[0] / 2) + 1;
        int hight = (dimensions[1] / 2) + 1;
        int rowCounter = 1;
        int colCounter = 1;
        turnLeft();
        while((rowCounter < width) && frontIsClear()){
            move();
            rowCounter++;
            steps++;
        }
        turnRight();
        putBeepersAndCount();
        turnAround();
        while((colCounter < hight) && frontIsClear()){
            move();
            colCounter++;
            steps++;
        }
        turnRight();putBeepersAndCount();turnAround();
        putBeepersAndCount();turnRight();moveAndCount();
        printSteps();
    }
    public void createEvenChambers(int[] dimensions){
        int width = (dimensions[0] / 2);
        int hight = (dimensions[1] / 2);
        int colCounter = 0;
        int rowCounter = 0;
        turnLeft();
        while((rowCounter < width) && frontIsClear()){
            move();
            rowCounter++;
            steps++;
        }
        turnRight();putBeepersAndCount();turnRight();
        move();
        steps++;
        turnRight();putBeepersAndCount();turnAround();
        while((colCounter < hight) && frontIsClear()){
            move();
            colCounter++;steps++;
        }
        turnLeft();putBeepersAndCount();turnAround();
        putBeepersAndCount();turnRight();move();
        steps++;
        turnRight();putBeepersAndCount();turnLeft();moveAndCount();
        turnLeft();moveAndCount();printSteps();
    }
    public void createEvenOnOddChambers(int[] dimensions){
        int odd = 0;
        int even = 0;
        int width = dimensions[0];
        if(dimensions[0] % 2 == 0 && dimensions[1] % 2 != 0){
            even = dimensions[0];
            odd = dimensions[1];
        } else {
            even = dimensions[1];
            odd = dimensions[0];
        }
        if(odd == width){
            turnLeft();
        }
        int step = odd / 2;
        while(step-- > 0){
            move();steps++;
        }
        putBeeper();beepers++;
        if(odd == width){
            turnRight();
        } else {
            turnLeft();
        }
        putBeepersAndCount();turnAround();
        step = even / 2 - 1;
        while(step-- > 0){
            move();
            steps++;
            if(!beepersPresent()){
                putBeeper();beepers++;
            }
        }
        turnRight();
        step = (odd / 2);
        step += step % 2;
        step /= 2;
        while(step-- > 0){
            move();
            steps++;
            if(!beepersPresent()){
                putBeeper();beepers++;
            }
        }
        turnLeft();
        move();
        if((odd / 2) % 2 == 1){
            putBeeper();beepers++;
        }
        turnRight();putBeepersAndCount();turnRight();
        move();
        steps++;
        turnRight();
        step = odd / 2;
        while(step-- > 0){
            move();
            steps++;
        }
        step = (odd / 2);
        step += step % 2;
        step /= 2;
        while(step-- > 0){
            move();
            steps++;
            if(!beepersPresent()){
                putBeeper();beepers++;
            }
        }
        turnRight();move();
        if((odd / 2) % 2 == 1){
            putBeeper();beepers++;
        }
        turnLeft();putBeepersAndCount();printSteps();
    }
}
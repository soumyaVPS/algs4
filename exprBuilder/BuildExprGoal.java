import java.lang.Integer;
import java.util.ArrayList;
import java.util.Iterator;
public class BuildExprGoal {
    static Stack<Integer> numStack = new Stack<Integer>();
    static Stack<String> operStack = new Stack<String>();
    static ArrayList<String> operArr = new ArrayList<String>(new String[] ("+","-",""));
    static int goal = 100;
    static ArrayList<String> outList = new ArrayList<String> ();
    public static boolean buildList(ArrayList<Integer> numArr, int currEval, String currExpr) {

        if (currEval == goal && numArr.size() == 0) {
            exprs.add(currExpr);
            return true;
        } else if (numArr.size() == 0) {
            return false;
        }
        if (!operStack.isEmpty()) {
            String oper = operStack.pop();

            if (oper.equals("")) {
                Integer a = numStack.pop();
                Integer b = numStack.pop();
                numStack.push(new Integer(b.toString() + q.toString()));
            }

             else {
                Integer a = numStack.pop();

                if (oper.equals("+")) {
                    currEval += a;

                } else if (oper.equals("-")) {
                    currEval -=b;
                }

            }
        }

        Iterator<String> oIter = operArr.iterator();
        while  (oIter.hasNext()) {
            String oper = oIter.next();
            operStack.push(oper);
            numStack.push(numArr.get(0));
            currExpr1 = currExpr + numArr.get(0) + oper;
            buildList(numArr.subList(1,numArr.size()), currEval, String currExpr1);
            operStack.pop();
            numStack.pop();
        }

    }
    public static ArrayList<String> buildExprs(ArrayList<Integer> numArr, int goal,currEval) {

        BuildExprGoal.goal = goal;
        //Start with empty stacks. insert first element.
        if(operStack.isEmpty() && numStack.isEmpty())  {
            numStack.push(numArr[0]);
            currEval = numArr[0]
        }
        else
            buildList(arr, operArr, 0, outList);
        return outList;
    }
    public static void main(String[] str) {
        ArrayList<Integer> arr = new ArrayList<Integer>(new int[](0,1,2,3,4,5,6,7,8,9]);

        ArrayList<String> result = buildExprs(arr, 100, 0);
        //Iterator<String> iter = result.iterator();
        while(iter : result.iterator()){
            System.out.println(iter);
        }

    }
}
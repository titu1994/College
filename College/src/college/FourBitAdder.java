package college;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FourBitAdder
{
  public interface OneInputGate
  {  boolean eval(boolean input);  }
 
  public interface TwoInputGate
  {  boolean eval(boolean input1, boolean input2);  }
 
  public interface MultiGate
  {  boolean[] eval(boolean... inputs);  }
 
  public static OneInputGate NOT = new OneInputGate() {
    public boolean eval(boolean input)
    {  return !input;  }
  };
 
  public static TwoInputGate AND = new TwoInputGate() {
    public boolean eval(boolean input1, boolean input2)
    {  return input1 && input2;  }
  };
 
  public static TwoInputGate OR = new TwoInputGate() {
    public boolean eval(boolean input1, boolean input2)
    {  return input1 || input2;  }
  };
 
  public static TwoInputGate XOR = new TwoInputGate() {
    public boolean eval(boolean input1, boolean input2)
    {
      return OR.eval(
               AND.eval(input1, NOT.eval(input2)),
               AND.eval(NOT.eval(input1), input2)
             );
    }
  };

  public static MultiGate HALF_ADDER = new MultiGate() {
    public boolean[] eval(boolean... inputs)
    {
      if (inputs.length != 2)
        throw new IllegalArgumentException();
      return new boolean[] {
        XOR.eval(inputs[0], inputs[1]), 
        AND.eval(inputs[0], inputs[1])   
      };
    }
  };
 
  
  public static MultiGate FULL_ADDER = new MultiGate() {
    public boolean[] eval(boolean... inputs)
    {
      boolean[] haOutputs1 = HALF_ADDER.eval(inputs[0], inputs[1]);
      boolean[] haOutputs2 = HALF_ADDER.eval(haOutputs1[0], inputs[2]);
      return new boolean[] {
        haOutputs2[0],                        
        OR.eval(haOutputs1[1], haOutputs2[1]) 
      };
    }
  };
 
  public static MultiGate buildAdder(final int numBits)
  {
    return new MultiGate() {
      public boolean[] eval(boolean... inputs)
      {
        boolean[] outputs = new boolean[numBits + 1];
        boolean[] faInputs = new boolean[3];
        boolean[] faOutputs = null;
        for (int i = 0; i < numBits; i++)
        {
          faInputs[0] = (faOutputs == null) ? false : faOutputs[1]; 
          faInputs[1] = inputs[i];                                   
          faInputs[2] = inputs[numBits + i];                         
          faOutputs = FULL_ADDER.eval(faInputs);
          outputs[i] = faOutputs[0];                               
        }
        if (faOutputs != null)
          outputs[numBits] = faOutputs[1];                          
        return outputs;
      }
    };
  }
 
  public static void main(String[] args) throws NumberFormatException, IOException
  {
	BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Enter the 2 no in Hex");
	
	int firstNum = Integer.parseInt(bb.readLine(), 16);
	int secondNum = Integer.parseInt(bb.readLine(), 16);
	
    String xs = Integer.toBinaryString(firstNum);
	String ys = Integer.toBinaryString(secondNum);
	
	
	int xl = xs.length();
	int yl = ys.length();
	int len = xl > yl? xl : yl;
	
	for(int i = 0; i < len - xl; i++){
		xs = "0" + xs;
	}
	for(int i = 0; i < len - yl; i++){
		ys = "0" + ys;
	}
	
	int numBits = len;
	
    MultiGate multiBitAdder = buildAdder(numBits);
   
    boolean[] inputs = new boolean[numBits << 1];
    String firstNumDisplay = "";
    String secondNumDisplay = "";
    for (int i = 0; i < numBits; i++)
    {
      boolean firstBit = ((firstNum >>> i) & 1) == 1;
      boolean secondBit = ((secondNum >>> i) & 1) == 1;
      inputs[i] = firstBit;
      inputs[numBits + i] = secondBit;
      firstNumDisplay = (firstBit ? "1" : "0") + firstNumDisplay;
      secondNumDisplay = (secondBit ? "1" : "0") + secondNumDisplay;
    }
 
    boolean[] outputs = multiBitAdder.eval(inputs);
    int outputNum = 0;
    String outputNumDisplay = "";
    String outputCarryDisplay = null;
    for (int i = numBits; i >= 0; i--)
    {
      outputNum = (outputNum << 1) | (outputs[i] ? 1 : 0);
      if (i == numBits)
        outputCarryDisplay = outputs[i] ? "1" : "0";
      else
        outputNumDisplay += (outputs[i] ? "1" : "0");
    }
    System.out.println("numBits=" + numBits);
    System.out.println("A=" + firstNumDisplay + " (" + firstNum + "), B=" + secondNumDisplay + " (" + secondNum + "), S=" + outputCarryDisplay + " " + outputNumDisplay + " (" + outputNum + ")");
    return;
  }
 
}
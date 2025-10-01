import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    double[] coefficients;
    int [] exponents;
    
    public Polynomial(){
        this.coefficients = new double[]{0};
        this.exponents = new int[] {0};
    }
    
    public Polynomial(double[] coefficients, int[] exponents){
        this.coefficients = coefficients;
        this.exponents = exponents;
    }
    
    public Polynomial add(Polynomial p){
        int []addedexponents = new int[this.exponents.length + p.exponents.length];
        double []addedcoefficients = new double[this.exponents.length + p.exponents.length];
        int tem = 0;
        int i = 0;
        int j = 0;
        while (i < this.exponents.length && j < p.exponents.length) {
        	if (this.exponents[i] < p.exponents[j]) {
				addedexponents[tem] = this.exponents[i];
				addedcoefficients[tem] = this.coefficients[i];
				i++;
			}
        	if (p.exponents[j] < this.exponents[i]) {
				addedexponents[tem]=this.exponents[i];
				addedcoefficients[tem]=this.coefficients[i];
				j++;
			}
        	if (this.exponents[i] == p.exponents[j]) {
				addedexponents[tem]=this.exponents[i];
				addedcoefficients[tem]=this.coefficients[i]+p.coefficients[j];
				i++;
				j++;
			}
        	tem++;
        }
        while (i < this.exponents.length) {
        	addedexponents[tem] = this.exponents[i];
        	addedcoefficients[tem] = this.coefficients[i];
        	i++;
        	tem++;
        }
        while (j < p.exponents.length) {
        	addedexponents[tem] = p.exponents[j];
        	addedcoefficients[tem] = p.coefficients[j];
        	j++;
        	tem++;
        }
        double[] finalcoe = new double[tem];
        int[] finalex = new int[tem];
        System.arraycopy(addedcoefficients, 0, finalcoe, 0, tem);
		System.arraycopy(addedexponents, 0, finalex, 0, tem);
		return new Polynomial(finalcoe, finalex);
    }
    
    public double evaluate(double x){
        double result = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            result += this.coefficients[i]*Math.pow(x, this.exponents[i]);
        }
        return result;
    }
    
    public boolean hasRoot(double x){
        return  evaluate(x) == 0;
    }
    
    public Polynomial multiply(Polynomial p) {
	    Polynomial res = new Polynomial(new double[0], new int[0]);
	    for (int i = 0; i < this.exponents.length; i++) {
	    	int[] multex = new int[p.exponents.length];
	    	double [] multcoe = new double[multex.length];
	    	for (int j = 0; j < p.exponents.length; j++) {
	    		multex [j] = this.exponents[i] + p.exponents[j];
	    		multcoe[j] = this.coefficients[i] * p.coefficients[j];
	    	}
	    	Polynomial finalmult = new Polynomial(multcoe, multex);
	    	res = res.add(finalmult);
	    }
	    return res;
	}
    
    public Polynomial(File f) throws IOException{
    	
	    BufferedReader r = new BufferedReader(new FileReader(f));
	    String newpoly = r.readLine();
	    r.close();
	    
	    String[] content = newpoly.split("(?=[+-])");
	    int tem = 0;
	    int[] exponents = new int[newpoly.length()];
	    double[] coefficients = new double[newpoly.length()];
	    for (String splited : content) {
	    	String[] diffexx = splited.split("x");
	    	if (diffexx.length > 1) {
	    		String exponentStr = diffexx[1].replaceAll("[^0-9]", "");
                exponents[tem] = exponentStr.isEmpty() ? 1 : Integer.parseInt(exponentStr);
                coefficients[tem] = Double.parseDouble(diffexx[0]);
	    	}
	    	else {
	    		exponents[tem] = 0;
                coefficients[tem] = Double.parseDouble(diffexx[0]);
            }
	    	tem++;
	    }
	    int[] finalex = new int[tem];
	    double[] finalcoe = new double[tem];
	    
	    System.arraycopy(exponents, 0, finalex, 0, tem);
	    System.arraycopy(coefficients, 0, finalcoe, 0, tem);
	    
	    this.coefficients = finalcoe;
	    this.exponents = finalex;
	}
    
    public void saveToFile(String filename) throws IOException{
        BufferedWriter w = null;
        try{
        	w = new BufferedWriter(new FileWriter(filename));
        	String polystr = "";
            for (int i = 0; i < coefficients.length; i++) {
            	
                double newcoe = coefficients[i];
                int newex = exponents[i];
                
                if (newcoe != 0) {
                	if (i > 0) {
                        if (newcoe > 0) {
                        	polystr += "+";
                        }
                        if (newcoe < 0) {
                        	polystr += "-";
                        	newcoe = -newcoe;
                        }
                    }
                    if (newex == 0) {
                    	polystr += newcoe;
                    }
                    if (newex != 0){
                    	polystr += newcoe + "x" + newex;
                    }
                }
            }  
            w.write(polystr);
            }
        finally {
        	if (w != null) {
                w.close();
                }
        	}
    }
    
}
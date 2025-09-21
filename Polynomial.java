public class Polynomial {
    double[] coefficients;
    public Polynomial(){
        this.coefficients = new double[]{0};
    }
    public Polynomial(double[] coefficients){
        this.coefficients = coefficients;
    }
    public Polynomial add(Polynomial p){
        int n=Math.max(p.coefficients.length, this.coefficients.length);
        double[] result = new double[n];
        for(int i = 0; i < n; i++){
            double a = 0;
            if(i < this.coefficients.length){
                a = this.coefficients[i];
            }
	    double b = 0;
            if(i < p.coefficients.length){
                b = p.coefficients[i];
            }
            result[i] = a + b;
        }
        return new Polynomial(result);
    }
    public double evaluate(double x){
        double result = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            result += this.coefficients[i]*Math.pow(x, i);
        }
        return result;
    }
    public boolean hasRoot(double x){
        return  evaluate(x) == 0;
    }
}
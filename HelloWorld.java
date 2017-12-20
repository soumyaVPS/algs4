public class HelloWorld {
	static int i = 0;
	static int add1() {
		return i;
	}
	public static int testincr() {
		return i++ + add1(); 
	}
    public static void main(String[] args) {
        System.out.println("Hello, World");
        //System.out.println(StdRandom.random(0,20));
        
        System.out.println(testincr());
        System.out.println(testincr());
        
        
    }
}	
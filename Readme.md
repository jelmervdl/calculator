# Simple calculator
This insanely idiotic and useless calculator was made to demonstrate how to use Java [RMI](http://docs.oracle.com/javase/6/docs/technotes/guides/rmi/index.html) and how to implement a simple webserver in Java. The program uses RMI to perform the calculation in a separate program (where ever that may run) and the webserver which serves as an alternative interface for evaluating expressions.

## Architecture
The *CalculatorClient* parses the expression and asks the stub of *CalculatorServer* to evaluate the expression. The resulting double is then printed to the stdout.

The *CalculatorServer* also hosts a webservice on port 8080. You can access it with your browser and will be presented with a webpage in which you can enter the same type of expressions.

## Running
I haven't paid any attention to java's security policy yet, therefore it is easier to run the program by using this very unsafe policy *wideopen.policy*. Or use the *run.sh* script, which will compile and run the class files.

### Running the server
    
    ./run.sh CalculatorServer
    
### Running the client
    
    ./run.sh CalculatorClient localhost 9999

Now you can just enter epxpressions and press [enter], and they will be evaluated. If you press enter on an empty line, the program will stop (as will it when it encounters end-of-file or an exception)

If you want to run it without the server (it will use calc.CalculatorImpl for evaluation)

	./run.sh CalculatorClient localhost 9999

*WebserverExample* is a simple demonstration of the capabilities of the http package.
# Simple calculator
This insanely idiotic and useless calculator was made to demonstrate how to use Java [RMI](http://docs.oracle.com/javase/6/docs/technotes/guides/rmi/index.html) and how to implement a simple webserver in Java. The program uses RMI to perform the calculation in a separate program (where ever that may run) and the webserver to show the last evaluated expression.

## Architecture
The *CalculatorClient* parses the expression and asks the stub of *CalculatorServer* to evaluate the expression. The resulting double is then printed to the stdout.

The *CalculatorServer* also stores the last evaluated expression and runs a webserver in a separate thread. The webserver responds to HTTP-requests with the last evaluated expression, or with a "404 Not found" response if there haven't been any calculations yet.

## Running
I haven't paid any attention to java's security policy yet, therefore it is easier to run the program by using this very unsafe policy *wideopen.policy*. Or use the *run.sh* script, which will compile and run the class files.

### Running the server
    
    ./run.sh CalculatorServer
    
### Running the client
    
    ./run.sh CalculatorClient localhost 9999

Or if you want to run it without the server (it will use calc.CalculatorImpl for evaluation)

	./run.sh CalculatorClient localhost 9999
	
*WebserverExample* is a simple demonstration of the capabilities of the http package.
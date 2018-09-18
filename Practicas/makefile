all:
	jflex atomos.jflex && byaccj -v -Jclass=Parser gramatica1.y
	javac Parser.java && javac Atomos.java

test:
	java Parser test.txt

clean:
	rm -f *.class  *.java* y.tab.c

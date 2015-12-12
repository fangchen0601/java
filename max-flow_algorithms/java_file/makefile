JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Vertex.java \
	Edge.java \
        InputLib.java \
	GraphInput.java \
	SimpleGraph.java \
	DFS.java \
	folkAlgorithm.java \
	PreflowPush.java \
	FordScaling.java \
	tcss543.java


default: classes
classes: $(CLASSES:.java=.class)
clean:
	$(RM) *.class


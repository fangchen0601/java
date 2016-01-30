JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
        TCPServer.java \
        Logger.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
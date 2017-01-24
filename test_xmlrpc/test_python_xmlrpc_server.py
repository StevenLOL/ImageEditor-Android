import xmlrpclib
from SimpleXMLRPCServer import SimpleXMLRPCServer
server = SimpleXMLRPCServer(("0.0.0.0", 8000))

def echo(din):
    print "echo"
    return din
def sum(d1,d2):
    print d1,d2,d1+d2,"sum"
    return str(d1+d2)
def text():
    print "text"
    return "OK"

print "Listening on port 8000..."
server.register_function(echo, "echo")
server.register_function(sum, "sum")
server.register_function(text, "text")
server.serve_forever()
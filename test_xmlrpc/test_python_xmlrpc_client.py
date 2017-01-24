import xmlrpclib
import datetime

proxy = xmlrpclib.ServerProxy("http://localhost:8000/")

print proxy.sum(5,4)
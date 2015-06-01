import httplib,urllib
import os
import time
import ConfigParser
import threading
import socket         
class Client: 
    conn=''
    frame=''
    cmd_list=list()
    wm = ''
    upload_list=list()
    gatewayID=''
    ip=''
    port=''
    upload_buf=list()
    cmd_list_mutex=threading.Lock()
    upload_buf_mutex=threading.Lock()
    def GetCmdList(self):
        return self.cmd_list    
    def __init__(self,cframe):
        self.frame=cframe
        self.conn = httplib.HTTPConnection('192.168.111.135',8880)   
        #self.conn.connect()
    def UpStream(self,data, sleep = 0.001):
        print "requesting..." 
        headers = {"Content-type": "application/x-www-form-urlencoded",
                        "Accept": "text/plain","Connection":"Keep-Alive"}
        try:
                self.conn.request("POST","/myrequest",data,headers) 
                print 'request'
                r1 = self.conn.getresponse()  
                print 'response'
        except:
                print 'exception!'
                
                
        else:
                    print r1.status, r1.reason         
                    data1 = r1.read()   
                    print 'cmd',data1
                    #self.DataHandler(data1)    
                    if r1.status==200:
                        return True  
                    #print data1
        return False
if __name__ == '__main__':
    data='D121511111111112222222222333333333344444444445555555555666666666677777777778888888888777777'
    #os.system('ifconfig eth0 down')
    Client('').UpStream(data,0.001)


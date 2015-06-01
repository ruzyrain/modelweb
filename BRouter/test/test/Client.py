#coding=utf-8
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
        self.conn = httplib.HTTPConnection('192.168.111.57',8090)   
        #self.conn.connect()
    def UpStream(self,data, sleep = 0.001):
        print "requesting..." 
        headers = {"Content-type": "text/plain;charset=utf-8",
                        "Accept": "text/plain","Connection":"Keep-Alive"}
        t=time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
        data='1111167890123456,'+t+','+data
        self.conn.request("POST","",data,headers) 
        try:
                #self.conn.request("POST","",data,headers) 
                print 'request',len(data)
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
    data='0031511111111112222222222333333333344444444445555555555666666666677777777778888888888777777'
    data1='1111167890123456,2014-02-23 10:57:54,'
    data3='#####################################'
    print len(data1)
    print data1.decode('utf8')
    data2 = data1.decode('utf8').encode('gb2312')
    print data2
    print len(data)
    print len(data1)
    print len(data3)
    #os.system('ifconfig eth0 down')
    Client('').UpStream(data1,0.001)


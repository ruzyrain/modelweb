import logging  
import logging.handlers
if __name__ == '__main__':
    #logging.debug("debug info")
    
    LOG_FILE = 'tst.log'   
    #handler = logging.StreamHandler()
    handler = logging.FileHandler('sendlog.txt')
    fmt = '%(asctime)s - %(filename)s:%(lineno)s - %(name)s - %(message)s'  
      
    formatter = logging.Formatter(fmt)   # 
    handler.setFormatter(formatter)      #  

    logger = logging.getLogger('tst')    # 
    logger.addHandler(handler)           # 
    logger.setLevel(logging.DEBUG)  
      
    logger.info('first info message')  
    logger.debug('first debug message') 
    
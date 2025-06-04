from otp import *
import logging

logging.basicConfig(level=logging.INFO,format='%(asctime)s - %(levelname)s - %(message)s')

def printEvent( message, evt ):
    logging.info( "/%s " + message, evt.device() )
    print events.tree( evt )

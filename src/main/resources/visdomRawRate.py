import storm
import logging

class visdomRawRate(storm.BasicBolt):
    def process(self, tup):
        #words = tup.values[0].split(" ")
        #for word in words:
        #  storm.emit([word])

        #rate = tup.values[0]
        #print tup.values[0];
        #storm.emit(rate)
        #print "Hello, Python!"


        logger = logging.getLogger('visdomRawRate')
        currentRate = tup.values[0]
        storm.emit([currentRate])
        logger.info(currentRate)

visdomRawRate().run()
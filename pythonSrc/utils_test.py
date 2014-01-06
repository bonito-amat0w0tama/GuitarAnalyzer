import numpy as np
import Utils
import pylab as plt 
import scipy.linalg as sl

noteList = ['c', 'd', 'e', 'f', 'g', 'a', 'b']

nu = Utils.NMFUtils
#path = "../../jsonData/zenon_2013-12-27-15:21.json"
doremiPath = "../../jsonData/doremi_normal_2014-1-6-16:47.json"
#doremiPath = "../../jsonData/doremi_short_2014-1-6-17:30.json"
doremiPath = "../../jsonData/doremi_short_2014-1-6-17:44.json"

doremi = nu.readJson(path=doremiPath)
doremi = nu.json2NpArray(doremi)

#waonPath = "../../jsonData/waon_2014-1-6-16:29.json"
waonPath = "../../jsonData/chord_2014-1-6-17:13.json"
waon = nu.readJson(path=waonPath)
waon = nu.json2NpArray(waon)

V = doremi['V']
W = doremi['W']
H = doremi['H']
SW = doremi['SW']
SH = doremi['SH']
Wp = sl.pinv(SW) 

WV = waon['V'] 

Hd = np.dot(Wp, WV)
junkW, SHd = nu.sortBasisAndCoef(W, Hd) 

for i in range(Hd.shape[1]):
    now = Hd[:,i]
    index = nu.putMaxIndex(now)
    max = np.max(now) 
    if max >= 0.0:
        try:
            print noteList[index]
            print "maxVal:", max
            print "index:", i
        except:
            print index
            print "maxVal:", max
            print "index:", i

#rows, cols = h.shape
#print rows
#print cols
#
#for i in range(cols):
#    activ = h[:,i]
#
#print type(W)
#print type(H)

#nu.putAllMaxIndex(mat=H)

#nu.createBasisGraph(SW, 1)
#print nu.putMaxIndex(h)
#nu.createBasisGraph(W, 1)
#nu.createCoefGraph(SH, 1)
nu.createCoefGraph(data=Hd, nFig=2, lim=True, ymin=0)
#nu.createBasisGraph(data=SW, nFig=1)
#nu.createCoefGraph(data=SH, nFig=2, lim=True, ymin=0)
plt.show()

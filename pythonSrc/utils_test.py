import numpy as np
import Utils
import pylab as plt 
import scipy.linalg as sl

noteList = ['c', 'd', 'e', 'f', 'g', 'a', 'b']

nu = Utils.NMFUtils
#path = "../../jsonData/zenon_2013-12-27-15:21.json"
path = "../../jsonData/doremi_2014-1-2-7:55.json"
data = nu.readJson(path=path)
data = nu.json2NpArray(data)

V = data['V']
W = data['W']
H = data['H']
SW = data['SW']
SH = data['SH']
Wp = sl.pinv(W) 
Wp = sl.pinv(SW) 

h = np.dot(Wp, V)
Hd = np.dot(Wp, V)
junkW, SHd = nu.sortBasisAndCoef(W, Hd) 

for i in range(Hd.shape[1]):
    index = nu.putMaxIndex(Hd[:,i])
    print "index:", i
    try:
        print noteList[index]
    except:
        print index
print h.shape

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
nu.createCoefGraph(SHd, 1)
#plt.show()

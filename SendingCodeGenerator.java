
public class SendingCodeGenerator {
		SendingCodeGenerator() {
		}
		
		public String putPrintCode() {
			return null;
		}
		// exec関数の参照範囲をglobalに変更したので,selfをserverに変更する必要があり
        String nmfCode = 
        		"V = self.pop()\n" +
        		"W,H = self.nmfMatrix(V, 'nmf', 10, 1000)\n" +
        		"self.push(W)\n" +
        		"self.push(H)\n" +
        		"Wp = self.getPseudoInverseMatrix(W)\n" +
        		"self.push(Wp)\n" +
                "self.pushMatrix(self.pop())\n" +
        		"self.pushMatrix(self.pop())\n" +
        		"self.pushMatrix(self.pop())\n" +
                "self.writeDataToJson(name='zenon', data={'V': V.tolist(), 'W': W.tolist(), 'H': H.tolist(), 'Wp': Wp.tolist()}, dateFlag=True)";

        String junkNmf = 
        		"V = server.pop()\n" +
        		"W,H = server.nmfMatrix(V, 'nmf', 10, 500)\n" +
//        		"Wp = server.getPseudoInverseMatrix(W)\n" +
        		"print W.shape\n" +
        		"print H.shape\n" +
//        		"server.push(W)\n" +
//        		"server.push(H)\n" +
//        		"Wp = server.getPseudoInverseMatrix(W)\n" +
//        		"server.push(Wp)\n" +
//                "server.pushMatrix(server.pop())\n" +
//        		"server.pushMatrix(server.pop())\n" +
//        		"server.pushMatrix(server.pop())\n" +
//              "server.writeDataToJson(name='zenon', data={'V': V.tolist(), 'W': W.tolist(), 'H': H.tolist(), 'Wp': Wp.tolist()}, dateFlag=True)\n" +
//				"X = np.arange(25).reshape(5, 5)\n" +
//				"path = '../../jsonData/zenon_2013-12-26-20:1.json'\n" +
//				"data = Utils.NMFUtils.readJson(path=path)\n" +
//				"data = Utils.NMFUtils.json2NpArray(data)\n" +
//				"Wd = data['W']\n" +
//        		"print Wd.shape\n" +
//				"x = np.arange(100)\n" +
//        		"print type(W)\n" +
//        		"W = np.asarray(W)\n" +
//        		"print type(W)\n" +
				"SW, SH = Utils.NMFUtils.sortBasisAndCoef(W, H)\n" +
        		"Wp = server.getPseudoInverseMatrix(SW)\n" +
        		"print Wp.shape\n" +
        		"print V.shape\n" +
        		"h = np.dot(Wp, V[:,135])\n" +
        		"print h.shape\n" +
        		"a = range(10)\n" +
        		"print np.max(h)\n" +
//        		"plt.plot(h)\n" +
        		"print server.u.printMaxIndex(h)\n" +
//        		"Utils.NMFUtils.createActivationGraph(h, 1)\n" +
//        		"Utils.NMFUtils.createCoefGraph(SH, 2)\n" +
        		"Utils.NMFUtils.createBasisGraph(SW, 1)\n" +
        		"Hd = np.dot(Wp, V)\n" +
        		"Utils.NMFUtils.createCoefGraph(Hd, 2)\n" +
//        		"Utils.NMFUtils.plotTest(x=x)\n" +
                "server.writeDataToJson(name='doremi', data={'V': V.tolist(), 'W': W.tolist(), 'H': H.tolist(), 'Wp': Wp.tolist()}, dateFlag=True)\n" +
        		"plt.show()\n";
//        		"Utils.NMFUtils.showPlot()";
        
        String pilot = 
        		"V = server.pop()\n" +
        		"W,H = server.nmfMatrix(V, 'nmf', 10, 500)\n" +
				"SW, SH = Utils.NMFUtils.sortBasisAndCoef(W, H)\n" +
        		"Wp = server.getPseudoInverseMatrix(SW)\n" +
        		"server.push(SW)\n" +
				"server.push(SH)\n" +
				"server.push(Wp)\n" +
        		"server.pushMatrix(server.pop())\n" +
        		"server.pushMatrix(server.pop())\n" +
        		"server.pushMatrix(server.pop())\n" +
        		"print nl.norm(np.dot(SW, SH))\n" +
        		"print nl.norm(np.dot(W, H))\n" +
        		"data={'V': V.tolist(), 'W': W.tolist(), 'H': H.tolist(), 'Wp': Wp.tolist(), 'SW': SW.tolist(), 'SH': SH.tolist()}\n" +
                "server.writeDataToJson(name='doremi', data=data, dateFlag=True)\n"; 
//                "server.writeDataToJson(name='doremi', data={'V': V.tolist(), 'W': W.tolist(), 'H': H.tolist(), 'Wp': Wp.tolist(), 'SW': SW.toList(), 'SH: SH.tolist()}, dateFlag=True)"; 
}

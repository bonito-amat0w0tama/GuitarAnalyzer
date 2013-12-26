#!/usr/bin/env python
# -*- coding: utf-8 -*-

import pylab as plt
import numpy as np
import nimfa
import json
import scipy as sp
from scipy.fftpack import ifft

import wave

class NMFUtils:
	@staticmethod
	def createBasisGraph(data, nFig):
		plt.figure(nFig)
		plt.suptitle('Basis')
		nBase = data.shape[1]
		# The cols number of subplot is 
		nSubCols = nBase / 10
		if nSubCols > 0:
			nSubRows = nBase / nSubCols
		else:
			nSubRows = nBase 
			nSubCols = 1
		# freqList = np.fft.fftfreq(513, d = 1.0 / 44100)

		for i in range(nBase):
			# Because Index of graph is start by 1, The Graph index start from i + 1.
			plt.subplot(nSubRows, nSubCols, i + 1)
			plt.tick_params(labelleft='off', labelbottom='off')
			plt.plot(data[:,i])
		# Beacuse I want to add lable in bottom, xlabel is declaration after loop.
		plt.tick_params(labelleft='off', labelbottom='on')
		plt.xlabel('frequency [Hz]')

	@staticmethod
	def createComparedBasisGraph(data1, data2, nFig):
		plt.figure(nFig)
		plt.suptitle('Basis')
		nBase = data1.shape[1]

		for i in range(nBase):
			# Because Index of graph is start by 1, The Graph index is i + 1.
			plt.subplot(nBase/4, 4, i + 1)
			plt.tick_params(labelleft='off', labelbottom='off') 
			plt.plot(data1[:,i], 'r', data2[:,i], 'b')
		# Beacuse I want to add lable in bottom, xlabel is declaration after loop.
		plt.tick_params(labelleft='off', labelbottom='on')
		plt.xlabel('frequency [Hz]')

	@staticmethod
	def createCoefGraph(data, nFig):
		plt.figure(nFig)
		plt.suptitle('Coef')
		nBase = data.shape[0]
		nSubCols = nBase / 10
		if nSubCols > 0:
			nSubRows = nBase / nSubCols
			print "fdsa"
		else:
			nSubRows = nBase 
			nSubCols = 	1
		# print data.shape

		# サンプリング周波数とシフト幅によって式を変える必要あり
		timeLine = [i * 1024 / 8000.0 for i in range(data.shape[1])]
		# print len(timeLine)
		for i in range(nBase):
			plt.subplot(nSubRows, nSubCols, i + 1)
			plt.tick_params(labelleft='off', labelbottom='off')
			# FIXME: Arguments of X
			# plt.plot(timeLine, data[i,:])
			plt.plot(timeLine, data[i,:])
		# Beacuse I want to add lable in bottom, xlabel is declaration after loop.
		plt.tick_params(labelleft='off', labelbottom="on")
		plt.xlabel('time [ms]')

	@staticmethod
	def createComparedCoefGraph(data1, data2, nFig):
		plt.figure(nFig)
		plt.suptitle('Coef')
		nBase = data1.shape[0]
		nSubCols = nBase / 10
		nSubRows = nBase / nSubCols

		# サンプリング周波数とシフト幅によって式を変える必要あり
		timeLine = [i * 1024 / 8000.0 for i in range(data1.shape[1])]

		for i in range(nBase): 
			plt.subplot(nSubRows, nSubCols, i + 1)
			plt.tick_params(labelleft='off', labelbottom='off')
			# FIXME: Arguments of X
			plt.plot(timeLine, data1[i,:], 'r', timeLine, data2[i,:], 'b')
		# Beacuse I want to add lable in bottom, xlabel is declaration after loop.
		plt.tick_params(labelleft='off', labelbottom="on")
		plt.xlabel('time [ms]')

	@staticmethod
	def sortBasisAndCoef(basis, coef):
		list = [] 
		for i in range(H.shape[0]):
			max = np.amax(H[i,:])
			for j in range(H.shape[1]): 
				if H[i,j] == max:
					# タプル(最大値がでるインデックス, 対応する行数)
					# ソートしやすいように最大値がでるインデックスを前にした
					list.append((j,i))
					# 見つかったら抜ける
					break
		# 最大振幅が見つかったインデックスを昇順でソート
		list.sort()

		# ソートした行列を格納するためにゼロ行列を作成
		sortedW = np.zeros(W.shape)
		sortedH = np.zeros(H.shape)

		num = 0
		# 行と列の入れ替え 
		for k in list:
			sortedH[num,:] = H[k[1],:]
			sortedW[:,num] = W[:,k[1]]
			num += 1

		return sortedW, sortedH




if __name__ == "__main__":
	X = np.random.random_sample(1000).reshape(20, 50)

	fctr = nimfa.mf(X, seed = "random_vcol", method = "nmf", rank = 40, mat_iter = 50)
	fctrRes = nimfa.mf_run(fctr)
	W = fctrRes.basis()
	W2 = 3 * W
	H = fctrRes.coef()
	H2 = 3 * H
	# print W
	# print H

	f = open("../../jsonData/zenon_2013-12-25-18:48.json")
	data = json.load(f)

	W = np.asarray(data['W'])
	H = np.asarray(data['H'])
	V = np.asarray(data['V'])

	Wp = np.asarray(data['Wp'])
	Hd = np.dot(Wp, V)


	SW, SH = NMFUtils.sortBasisAndCoef(W, H)
	# NMFPloter.createBasisGraph(W, 1)
	 # NMFPloter.createComparedBasisGraph(W, Wp, 2)
	# NMFPloter.createCoefGraph(H, 2)
	# NMFPloter.createComparedCoefGraph(H, Hd, 1)


	NMFUtils.createBasisGraph(SW, 1)
	NMFUtils.createCoefGraph(SH, 2)

	# wf = wave.open("../data/guitar.wav", "rb")
	# data = wf.readframes(wf.getnframes())
	# data = np.frombuffer(data, dtype="int16")
	# length = float(wf.getnframes()) / wf.getframerate()

	# N = 512
	# hammingWindow = np.hamming(N)	

	# pxx, freqs, bins, im = plt.specgram(data, NFFT=N, Fs=wf.getframerate(), noverlap=0, window=hammingWindow)
	# plt.axis([0, length, 0, wf.getframerate() / 2])

	plt.show()

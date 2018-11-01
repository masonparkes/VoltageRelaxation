import matplotlib.pyplot as plt
plt.plot([1,2,3,4])
plt.ylabel('some numbers')
plt.show()


size=7
boundary=[[0]*size for i in range(size)]

for i in range(len(boundary)):
	print(boundary[i])
print()
boundary[0]=[1]*size
boundary[size-1]=[1]*size
for i in range(size):
	boundary[i][0]=1
	boundary[i][6]=1
for i in range(size):
	print(boundary[i])

vals=[[0]*size for i in range(size)]

for i in range(size):
	print(vals[i])
print()
vals[0]=[0]*size
vals[size-1]=[0]*size
for i in range(size):
	vals[i][0]=100
	vals[i][size-1]=100
for i in range(size):
	print(vals[i])

tol=.01
passes=0
while passes<10:
	change=0
	for i in range(size):
		for j in range(size):
			if boundary[i][j]==0:
				avg=0;
				by=0;
				if i>1:
					avg+=vals[i-1][j]
					by+=1
				if i<size-1:
					avg+=vals[i+1][j]
					by+=1
				if j>1:
					avg+=vals[i][j-1]
					by+=1
				if j<size-1:
					avg+=vals[i][j+1]
					by+=1
				newval=avg/by
				if change<abs(vals[i][j]-newval):
					change=abs(vals[i][j]-newval)
				vals[i][j]=newval
	passes+=1
	print(passes)
	for i in range(size):
		print(vals[i])

